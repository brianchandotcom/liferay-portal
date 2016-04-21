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

package com.liferay.knowledge.base.web.lar;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.xstream.XStreamAliasRegistryUtil;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.model.impl.KBArticleImpl;
import com.liferay.knowledge.base.model.impl.KBCommentImpl;
import com.liferay.knowledge.base.model.impl.KBTemplateImpl;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBCommentLocalService;
import com.liferay.knowledge.base.service.KBTemplateLocalService;
import com.liferay.knowledge.base.util.comparator.KBArticleVersionComparator;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN},
	service = PortletDataHandler.class
)
public class AdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "knowledge_base";

	public AdminPortletDataHandler() {
		setDataLevel(DataLevel.SITE);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(KBArticle.class),
			new StagedModelType(KBComment.class),
			new StagedModelType(KBTemplate.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "kb-articles", true, true, null,
				KBArticle.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "kb-templates", true, true, null,
				KBTemplate.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "kb-comments", true, true, null,
				KBComment.class.getName()));

		XStreamAliasRegistryUtil.register(KBArticleImpl.class, "KBArticle");
		XStreamAliasRegistryUtil.register(KBCommentImpl.class, "KBComment");
		XStreamAliasRegistryUtil.register(KBTemplateImpl.class, "KBTemplate");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				AdminPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_kbArticleLocalService.deleteGroupKBArticles(
			portletDataContext.getScopeGroupId());

		_kbTemplateLocalService.deleteGroupKBTemplates(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery kbArticleActionableDynamicQuery =
			getKBArticleActionableDynamicQuery(portletDataContext);

		kbArticleActionableDynamicQuery.performActions();

		ActionableDynamicQuery kbTemplateActionableDynamicQuery =
			_kbTemplateLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		kbTemplateActionableDynamicQuery.performActions();

		ActionableDynamicQuery kbCommentActionableDynamicQuery =
			getKBCommentActionableDynamicQuery(portletDataContext);

		kbCommentActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(RESOURCE_NAME);

		Element kbArticlesElement =
			portletDataContext.getImportDataGroupElement(KBArticle.class);

		List<Element> kbArticleElements = kbArticlesElement.elements();

		for (Element kbArticleElement : kbArticleElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, kbArticleElement);
		}

		Element kbTemplatesElement =
			portletDataContext.getImportDataGroupElement(KBTemplate.class);

		List<Element> kbTemplateElements = kbTemplatesElement.elements();

		for (Element kbTemplateElement : kbTemplateElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, kbTemplateElement);
		}

		Element kbCommentsElement =
			portletDataContext.getImportDataGroupElement(KBComment.class);

		List<Element> kbCommentElements = kbCommentsElement.elements();

		for (Element kbCommentElement : kbCommentElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, kbCommentElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery kbArticleActionableDynamicQuery =
			_kbArticleLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		kbArticleActionableDynamicQuery.performCount();

		ActionableDynamicQuery kbTemplateActionableDynamicQuery =
			_kbTemplateLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		kbTemplateActionableDynamicQuery.performCount();

		ActionableDynamicQuery kbCommentActionableDynamicQuery =
			getKBCommentActionableDynamicQuery(portletDataContext);

		kbCommentActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getKBArticleActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws Exception {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_kbArticleLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddOrderCriteriaMethod
			addOrderCriteriaMethod =
				exportActionableDynamicQuery.getAddOrderCriteriaMethod();

		exportActionableDynamicQuery.setAddOrderCriteriaMethod(
			new ActionableDynamicQuery.AddOrderCriteriaMethod() {

				@Override
				public void addOrderCriteria(DynamicQuery dynamicQuery) {
					if (addOrderCriteriaMethod != null) {
						addOrderCriteriaMethod.addOrderCriteria(dynamicQuery);
					}

					OrderFactoryUtil.addOrderByComparator(
						dynamicQuery, new KBArticleVersionComparator(true));
				}

			});

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getKBCommentActionableDynamicQuery(
			PortletDataContext portletDataContext)
		throws Exception {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_kbCommentLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				_portal.getClassNameId(KBComment.class),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	@Reference(unbind = "-")
	protected void setKbArticleLocalService(
		KBArticleLocalService kbArticleLocalService) {

		_kbArticleLocalService = kbArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setKbCommentLocalService(
		KBCommentLocalService kbCommentLocalService) {

		_kbCommentLocalService = kbCommentLocalService;
	}

	@Reference(unbind = "-")
	protected void setKbTemplateLocalService(
		KBTemplateLocalService kbTemplateLocalService) {

		_kbTemplateLocalService = kbTemplateLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	protected static final String RESOURCE_NAME =
		"com.liferay.knowledge.base.admin";

	private KBArticleLocalService _kbArticleLocalService;
	private KBCommentLocalService _kbCommentLocalService;
	private KBTemplateLocalService _kbTemplateLocalService;
	private Portal _portal;

}