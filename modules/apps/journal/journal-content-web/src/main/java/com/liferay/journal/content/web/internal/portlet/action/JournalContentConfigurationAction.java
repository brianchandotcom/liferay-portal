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

package com.liferay.journal.content.web.internal.portlet.action;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.content.web.internal.display.context.JournalContentDisplayContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Douglas Wong
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
	service = ConfigurationAction.class
)
public class JournalContentConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		httpServletRequest.setAttribute(
			JournalWebKeys.ITEM_SELECTOR, _itemSelector);
		httpServletRequest.setAttribute(
			JournalWebKeys.JOURNAL_CONTENT, _journalContent);

		try {
			JournalContentDisplayContext.create(
				portletRequest, portletResponse, _CLASS_NAME_ID,
				_ddmTemplateModelResourcePermission);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		setPreference(actionRequest, "articleId", _getArticleId(actionRequest));

		String[] contentMetadataAssetAddonEntryKeys =
			ParamUtil.getParameterValues(
				actionRequest, "contentMetadataAssetAddonEntryKeys");

		setPreference(
			actionRequest, "contentMetadataAssetAddonEntryKeys",
			StringUtil.merge(contentMetadataAssetAddonEntryKeys));

		setPreference(
			actionRequest, "groupId",
			String.valueOf(_getArticleGroupId(actionRequest)));

		String[] userToolAssetAddonEntryKeys = ParamUtil.getParameterValues(
			actionRequest, "userToolAssetAddonEntryKeys");

		setPreference(
			actionRequest, "userToolAssetAddonEntryKeys",
			StringUtil.merge(userToolAssetAddonEntryKeys));

		_addDDMTemplateLinks(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.content.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private void _addDDMTemplateLinks(ActionRequest actionRequest)
		throws Exception {

		JournalArticle journalArticle =
			_journalArticleLocalService.fetchArticle(
				_getArticleGroupId(actionRequest),
				_getArticleId(actionRequest));

		if (journalArticle == null) {
			return;
		}

		String compositeClassName = ResourceActionsUtil.getCompositeModelName(
			JournalArticle.class.getName(), DDMTemplate.class.getName());

		_ddmTemplateLinkLocalService.deleteTemplateLink(
			_portal.getClassNameId(compositeClassName), journalArticle.getId());

		long ddmTemplateId = _getDDMTemplateId(actionRequest);

		if (ddmTemplateId == 0) {
			return;
		}

		_ddmTemplateLinkLocalService.addTemplateLink(
			_portal.getClassNameId(compositeClassName), journalArticle.getId(),
			ddmTemplateId);
	}

	private long _getArticleGroupId(PortletRequest portletRequest) {
		long assetEntryId = GetterUtil.getLong(
			getParameter(portletRequest, "assetEntryId"));

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetEntryId);

		if (assetEntry == null) {
			return 0;
		}

		return assetEntry.getGroupId();
	}

	private String _getArticleId(PortletRequest portletRequest)
		throws PortalException {

		long assetEntryId = GetterUtil.getLong(
			getParameter(portletRequest, "assetEntryId"));

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetEntryId);

		if (assetEntry == null) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<JournalArticle> articleAssetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		AssetRenderer<JournalArticle> articleAssetRenderer =
			articleAssetRendererFactory.getAssetRenderer(
				assetEntry.getClassPK());

		JournalArticle article = articleAssetRenderer.getAssetObject();

		return StringUtil.toUpperCase(article.getArticleId());
	}

	private long _getDDMTemplateId(PortletRequest portletRequest)
		throws PortalException {

		String ddmTemplateKey = getParameter(portletRequest, "ddmTemplateKey");

		if (Validator.isNull(ddmTemplateKey)) {
			return 0;
		}

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
			_getArticleGroupId(portletRequest), _CLASS_NAME_ID, ddmTemplateKey,
			true);

		if (ddmTemplate == null) {
			return 0;
		}

		return ddmTemplate.getTemplateId();
	}

	private static final long _CLASS_NAME_ID = PortalUtil.getClassNameId(
		DDMStructure.class);

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentConfigurationAction.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplate)"
	)
	private ModelResourcePermission<DDMTemplate>
		_ddmTemplateModelResourcePermission;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private Portal _portal;

}