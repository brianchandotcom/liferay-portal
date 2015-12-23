/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.comment.page.comments.web.lar;

import com.liferay.comment.page.comments.web.constants.PageCommentsPortletKeys;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.comment.DiscussionStagingHandler;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.ExportImportProcessCallbackRegistryUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerControl;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.List;
import java.util.concurrent.Callable;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + PageCommentsPortletKeys.PAGE_COMMENTS},
	service = PortletDataHandler.class
)
public class PageCommentsPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "comment";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public StagedModelType[] getDeletionSystemEventStagedModelTypes() {
		DiscussionStagingHandler discussionStagingHandler =
			CommentManagerUtil.getDiscussionStagingHandler();

		if (discussionStagingHandler == null) {
			return new StagedModelType[0];
		}

		Class<? extends StagedModel> clazz =
			discussionStagingHandler.getStagedModelClass();

		StagedModelType stagedModelType = new StagedModelType(
			clazz.getName(), StagedModelType.REFERRER_CLASS_NAME_ANY);

		return new StagedModelType[] {stagedModelType};
	}

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		DiscussionStagingHandler discussionStagingHandler =
			CommentManagerUtil.getDiscussionStagingHandler();

		if (discussionStagingHandler == null) {
			return new PortletDataHandlerControl[0];
		}

		PortletDataHandlerBoolean portletDataHandlerBoolean =
			new PortletDataHandlerBoolean(
				NAMESPACE, "comment", true, false, null,
				discussionStagingHandler.getClassName(),
				StagedModelType.REFERRER_CLASS_NAME_ANY);

		return new PortletDataHandlerControl[] {portletDataHandlerBoolean};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return getExportControls();
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataAlwaysStaged(true);
		setPublishToLiveByDefault(true);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				PageCommentsPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		CommentManagerUtil.deleteGroupComments(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		DiscussionStagingHandler discussionStagingHandler =
			CommentManagerUtil.getDiscussionStagingHandler();

		if (discussionStagingHandler == null) {
			return StringPool.BLANK;
		}

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "comment")) {
			return getExportDataRootElementString(rootElement);
		}

		ActionableDynamicQuery actionableDynamicQuery =
			discussionStagingHandler.getCommentExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		ExportImportProcessCallbackRegistryUtil.registerCallback(
			new ImportCommentsCallable(portletDataContext));

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		DiscussionStagingHandler discussionStagingHandler =
			CommentManagerUtil.getDiscussionStagingHandler();

		if (discussionStagingHandler == null) {
			return;
		}

		ActionableDynamicQuery actionableDynamicQuery =
			discussionStagingHandler.getCommentExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performCount();
	}

	private class ImportCommentsCallable implements Callable<Void> {

		public ImportCommentsCallable(PortletDataContext portletDataContext) {
			_portletDataContext = portletDataContext;
		}

		@Override
		public Void call() throws PortalException {
			DiscussionStagingHandler discussionStagingHandler =
				CommentManagerUtil.getDiscussionStagingHandler();

			if (discussionStagingHandler == null) {
				return null;
			}

			_portletDataContext.importPortletPermissions(
				discussionStagingHandler.getResourceName());

			if (!_portletDataContext.getBooleanParameter(
					NAMESPACE, "comment")) {

				return null;
			}

			Element messagesElement =
				_portletDataContext.getImportDataGroupElement(
					discussionStagingHandler.getStagedModelClass());

			List<Element> messageElements = messagesElement.elements();

			for (Element messageElement : messageElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					_portletDataContext, messageElement);
			}

			return null;
		}

		private final PortletDataContext _portletDataContext;

	}

}