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

package com.liferay.message.boards.web.lar;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;
import com.liferay.portlet.messageboards.constants.MBConstants;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBBanLocalService;
import com.liferay.portlet.messageboards.service.MBCategoryLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalService;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalService;
import com.liferay.portlet.messageboards.service.MBThreadLocalService;
import com.liferay.portlet.messageboards.service.permission.MBPermission;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Daniel Kocsis
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN
	},
	service = PortletDataHandler.class
)
public class MBPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "message_boards";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public String getServiceName() {
		return MBConstants.SERVICE_NAME;
	}

	@Activate
	protected void activate() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(MBBan.class),
			new StagedModelType(MBCategory.class),
			new StagedModelType(MBMessage.class),
			new StagedModelType(MBThread.class),
			new StagedModelType(MBThreadFlag.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "messages", true, false, null,
				MBMessage.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "thread-flags", true, false, null,
				MBThreadFlag.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "user-bans", true, false, null,
				MBBan.class.getName()));
		setImportControls(getExportControls());
		setPublishToLiveByDefault(
			PropsValues.MESSAGE_BOARDS_PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				MBPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_mbBanLocalService.deleteBansByGroupId(
			portletDataContext.getScopeGroupId());

		_mbCategoryLocalService.deleteCategories(
			portletDataContext.getScopeGroupId());

		_mbStatsUserLocalService.deleteStatsUsersByGroupId(
			portletDataContext.getScopeGroupId());

		_mbThreadLocalService.deleteThreads(
			portletDataContext.getScopeGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(MBPermission.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "messages")) {
			ActionableDynamicQuery categoryActionableDynamicQuery =
				_mbCategoryLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			categoryActionableDynamicQuery.performActions();

			ActionableDynamicQuery messageActionableDynamicQuery =
				getMessageActionableDynamicQuery(portletDataContext);

			messageActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "thread-flags")) {
			ActionableDynamicQuery threadFlagActionableDynamicQuery =
				_mbThreadFlagLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			threadFlagActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "user-bans")) {
			ActionableDynamicQuery banActionableDynamicQuery =
				_mbBanLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			banActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(MBPermission.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "messages")) {
			Element categoriesElement =
				portletDataContext.getImportDataGroupElement(MBCategory.class);

			List<Element> categoryElements = categoriesElement.elements();

			for (Element categoryElement : categoryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, categoryElement);
			}

			Element messagesElement =
				portletDataContext.getImportDataGroupElement(MBMessage.class);

			List<Element> messageElements = messagesElement.elements();

			for (Element messageElement : messageElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, messageElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "thread-flags")) {
			Element threadFlagsElement =
				portletDataContext.getImportDataGroupElement(
					MBThreadFlag.class);

			List<Element> threadFlagElements = threadFlagsElement.elements();

			for (Element threadFlagElement : threadFlagElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, threadFlagElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "user-bans")) {
			Element userBansElement =
				portletDataContext.getImportDataGroupElement(MBBan.class);

			List<Element> userBanElements = userBansElement.elements();

			for (Element userBanElement : userBanElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, userBanElement);
			}
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery banActionableDynamicQuery =
			_mbBanLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		banActionableDynamicQuery.performCount();

		ActionableDynamicQuery categoryActionableDynamicQuery =
			_mbCategoryLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		categoryActionableDynamicQuery.performCount();

		ActionableDynamicQuery messageActionableDynamicQuery =
			getMessageActionableDynamicQuery(portletDataContext);

		messageActionableDynamicQuery.performCount();

		ActionableDynamicQuery threadActionableDynamicQuery =
			_mbThreadLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		threadActionableDynamicQuery.performCount();

		ActionableDynamicQuery threadFlagActionableDynamicQuery =
			_mbThreadFlagLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		threadFlagActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getMessageActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery actionableDynamicQuery =
			_mbMessageLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Criterion modifiedDateCriterion =
						portletDataContext.getDateRangeCriteria("modifiedDate");
					Criterion statusDateCriterion =
						portletDataContext.getDateRangeCriteria("statusDate");

					if ((modifiedDateCriterion != null) &&
						(statusDateCriterion != null)) {

						Disjunction disjunction =
							RestrictionsFactoryUtil.disjunction();

						disjunction.add(modifiedDateCriterion);
						disjunction.add(statusDateCriterion);

						dynamicQuery.add(disjunction);
					}

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(classNameIdProperty.eq(0L));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					if (portletDataContext.isInitialPublication()) {
						dynamicQuery.add(
							statusProperty.ne(
								WorkflowConstants.STATUS_IN_TRASH));
					}
					else {
						StagedModelDataHandler<?> stagedModelDataHandler =
							StagedModelDataHandlerRegistryUtil.
								getStagedModelDataHandler(
									MBMessage.class.getName());

						dynamicQuery.add(
							statusProperty.in(
								stagedModelDataHandler.
									getExportableStatuses()));
					}
				}

			});

		return actionableDynamicQuery;
	}

	@Reference(unbind = "-")
	protected void setMBBanLocalService(MBBanLocalService mbBanLocalService) {
		_mbBanLocalService = mbBanLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBCategoryLocalService(
		MBCategoryLocalService mbCategoryLocalService) {

		_mbCategoryLocalService = mbCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBStatsUserLocalService(
		MBStatsUserLocalService mbStatsUserLocalService) {

		_mbStatsUserLocalService = mbStatsUserLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBThreadFlagLocalService(
		MBThreadFlagLocalService mbThreadFlagLocalService) {

		_mbThreadFlagLocalService = mbThreadFlagLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {

		_mbThreadLocalService = mbThreadLocalService;
	}

	private volatile MBBanLocalService _mbBanLocalService;
	private volatile MBCategoryLocalService _mbCategoryLocalService;
	private volatile MBMessageLocalService _mbMessageLocalService;
	private volatile MBStatsUserLocalService _mbStatsUserLocalService;
	private volatile MBThreadFlagLocalService _mbThreadFlagLocalService;
	private volatile MBThreadLocalService _mbThreadLocalService;

}