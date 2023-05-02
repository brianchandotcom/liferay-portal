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

package com.liferay.journal.content.web.internal.portlet;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.content.web.internal.display.context.JournalContentDisplayContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.ExportArticleHelper;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.service.TrashEntryService;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-journal-content",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.display-category=category.highlighted",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/journal_content.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Web Content Display",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
		"javax.portlet.portlet-mode=application/vnd.wap.xhtml+xml;view",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class JournalContentPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long articleGroupId = PrefsParamUtil.getLong(
			portletPreferences, renderRequest, "groupId",
			themeDisplay.getScopeGroupId());

		String articleId = PrefsParamUtil.getString(
			portletPreferences, renderRequest, "articleId");

		JournalArticle article = null;
		JournalArticleDisplay articleDisplay = null;

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		JournalContentDisplayContext journalContentDisplayContext =
			(JournalContentDisplayContext)renderRequest.getAttribute(
				JournalContentDisplayContext.getRequestAttributeName(
					portletDisplay.getId()));

		if (journalContentDisplayContext != null) {
			try {
				article = journalContentDisplayContext.getArticle();
				articleDisplay =
					journalContentDisplayContext.getArticleDisplay();
			}
			catch (PortalException portalException) {
				_log.error("Unable to get journal article", portalException);
			}
		}
		else if ((articleGroupId > 0) && Validator.isNotNull(articleId)) {
			String viewMode = ParamUtil.getString(renderRequest, "viewMode");
			String languageId = _language.getLanguageId(renderRequest);
			int page = ParamUtil.getInteger(renderRequest, "page", 1);

			article = _journalArticleLocalService.fetchLatestArticle(
				articleGroupId, articleId, WorkflowConstants.STATUS_APPROVED);

			try {
				if (article == null) {
					article = _journalArticleLocalService.getLatestArticle(
						articleGroupId, articleId,
						WorkflowConstants.STATUS_ANY);
				}

				String ddmTemplateKey = PrefsParamUtil.getString(
					portletPreferences, renderRequest, "ddmTemplateKey");

				if (Validator.isNull(ddmTemplateKey)) {
					ddmTemplateKey = article.getDDMTemplateKey();
				}

				articleDisplay = _journalContent.getDisplay(
					article, ddmTemplateKey, viewMode, languageId, page,
					new PortletRequestModel(renderRequest, renderResponse),
					themeDisplay);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				renderRequest.removeAttribute(WebKeys.JOURNAL_ARTICLE);
			}
		}

		if (article != null) {
			renderRequest.setAttribute(WebKeys.JOURNAL_ARTICLE, article);
		}

		if (articleDisplay != null) {
			renderRequest.setAttribute(
				WebKeys.JOURNAL_ARTICLE_DISPLAY, articleDisplay);
		}
		else {
			renderRequest.removeAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);
		}

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			JournalWebKeys.JOURNAL_CONTENT, _journalContent);

		try {
			JournalContentDisplayContext.create(
				renderRequest, renderResponse, _CLASS_NAME_ID,
				_ddmTemplateModelResourcePermission, _itemSelector,
				_trashHelper);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	public void restoreJournalArticle(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		_trashEntryService.restoreEntry(
			JournalArticle.class.getName(), classPK);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (resourceID.equals("exportArticle")) {
			String targetExtension = ParamUtil.getString(
				resourceRequest, "targetExtension");

			targetExtension = StringUtil.toUpperCase(targetExtension);

			PortletPreferences portletPreferences =
				resourceRequest.getPreferences();

			String[] allowedExtensions = StringUtil.split(
				portletPreferences.getValue(
					"userToolAssetAddonEntryKeys", null));

			if (ArrayUtil.contains(
					allowedExtensions,
					"enable" + StringUtil.toUpperCase(targetExtension))) {

				_exportArticleHelper.sendFile(
					targetExtension, resourceRequest, resourceResponse);
			}
		}
		else {
			resourceRequest.setAttribute(
				JournalWebKeys.JOURNAL_CONTENT, _journalContent);

			try {
				JournalContentDisplayContext.create(
					resourceRequest, resourceResponse, _CLASS_NAME_ID,
					_ddmTemplateModelResourcePermission, _itemSelector,
					_trashHelper);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}

			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	@Activate
	protected void activate() {
		_portletRegistry.registerAlias(
			"web-content", JournalContentPortletKeys.JOURNAL_CONTENT);
	}

	@Deactivate
	protected void deactivate() {
		_portletRegistry.unregisterAlias("web-content");
	}

	private static final long _CLASS_NAME_ID = PortalUtil.getClassNameId(
		DDMStructure.class);

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentPortlet.class);

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplate)"
	)
	private ModelResourcePermission<DDMTemplate>
		_ddmTemplateModelResourcePermission;

	@Reference
	private ExportArticleHelper _exportArticleHelper;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private Language _language;

	@Reference
	private PortletRegistry _portletRegistry;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.journal.content.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))"
	)
	private Release _release;

	@Reference
	private TrashEntryService _trashEntryService;

	@Reference
	private TrashHelper _trashHelper;

}