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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.layout.content.LayoutContentProvider;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.service.LayoutLocalizationLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.sites.kernel.util.Sites;

import java.util.Collections;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/publish_layout"
	},
	service = {AopService.class, MVCActionCommand.class}
)
public class PublishLayoutMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout draftLayout = _layoutLocalService.getLayout(
			themeDisplay.getPlid());

		if (!draftLayout.isDraftLayout()) {
			sendRedirect(actionRequest, actionResponse);

			return;
		}

		Layout layout = _layoutLocalService.getLayout(draftLayout.getClassPK());

		LayoutPermissionUtil.checkLayoutUpdatePermission(
			themeDisplay.getPermissionChecker(), draftLayout);

		LayoutPermissionUtil.checkLayoutUpdatePermission(
			themeDisplay.getPermissionChecker(), layout);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_publishLayout(
			actionRequest, actionResponse, draftLayout, layout, serviceContext,
			themeDisplay.getUserId());

		String portletId = _portal.getPortletId(actionRequest);

		if (SessionMessages.contains(
				actionRequest,
				portletId.concat(
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE))) {

			SessionMessages.clear(actionRequest);
		}

		MultiSessionMessages.add(actionRequest, "layoutPublished");

		sendRedirect(actionRequest, actionResponse);
	}

	private void _publishLayout(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Layout draftLayout, Layout layout, ServiceContext serviceContext,
			long userId)
		throws Exception {

		LayoutStructureUtil.deleteMarkedForDeletionItems(
			draftLayout.getGroupId(), draftLayout.getPlid());

		if (_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				layout.getCompanyId(), layout.getGroupId(),
				Layout.class.getName())) {

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				layout.getCompanyId(), layout.getGroupId(), userId,
				Layout.class.getName(), layout.getPlid(), layout,
				serviceContext, Collections.emptyMap());
		}
		else {
			UnicodeProperties originalTypeSettingsUnicodeProperties =
				layout.getTypeSettingsProperties();

			_layoutCopyHelper.copyLayout(draftLayout, layout);

			layout = _layoutLocalService.getLayout(layout.getPlid());

			_updateLayoutContent(
				actionRequest, actionResponse, layout, serviceContext);

			draftLayout = _layoutLocalService.getLayout(draftLayout.getPlid());

			UnicodeProperties typeSettingsUnicodeProperties =
				draftLayout.getTypeSettingsProperties();

			String layoutPrototypeUuid = layout.getLayoutPrototypeUuid();

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				typeSettingsUnicodeProperties.setProperty(
					"layoutPrototypeUuid", layoutPrototypeUuid);
			}

			typeSettingsUnicodeProperties.put(
				"published", Boolean.TRUE.toString());

			draftLayout.setStatus(WorkflowConstants.STATUS_APPROVED);

			_layoutLocalService.updateLayout(draftLayout);

			LayoutSet layoutSet = layout.getLayoutSet();

			if (layoutSet.isLayoutSetPrototypeLinkActive()) {
				UnicodeProperties updatedTypeSettingsUnicodeProperties =
					layout.getTypeSettingsProperties();

				if (originalTypeSettingsUnicodeProperties.containsKey(
						Sites.LAST_MERGE_LAYOUT_MODIFIED_TIME)) {

					updatedTypeSettingsUnicodeProperties.put(
						Sites.LAST_MERGE_LAYOUT_MODIFIED_TIME,
						originalTypeSettingsUnicodeProperties.getProperty(
							Sites.LAST_MERGE_LAYOUT_MODIFIED_TIME));
				}

				if (originalTypeSettingsUnicodeProperties.containsKey(
						Sites.LAST_MERGE_TIME)) {

					updatedTypeSettingsUnicodeProperties.put(
						Sites.LAST_MERGE_TIME,
						originalTypeSettingsUnicodeProperties.getProperty(
							Sites.LAST_MERGE_TIME));
				}
			}

			layout.setType(draftLayout.getType());
			layout.setLayoutPrototypeUuid(null);
			layout.setStatus(WorkflowConstants.STATUS_APPROVED);

			_layoutLocalService.updateLayout(layout);

			_updateLayoutRevision(layout, serviceContext);
		}
	}

	private void _updateLayoutContent(
		ActionRequest actionRequest, ActionResponse actionResponse,
		Layout layout, ServiceContext serviceContext) {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		for (Locale locale :
				_language.getAvailableLocales(layout.getGroupId())) {

			_layoutLocalizationLocalService.updateLayoutLocalization(
				_layoutContentProvider.getLayoutContent(
					httpServletRequest, httpServletResponse, layout, locale),
				LocaleUtil.toLanguageId(locale), layout.getPlid(),
				serviceContext);
		}
	}

	private void _updateLayoutRevision(
			Layout layout, ServiceContext serviceContext)
		throws Exception {

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return;
		}

		_layoutRevisionLocalService.updateLayoutRevision(
			serviceContext.getUserId(), layoutRevision.getLayoutRevisionId(),
			layoutRevision.getLayoutBranchId(), layoutRevision.getName(),
			layoutRevision.getTitle(), layoutRevision.getDescription(),
			layoutRevision.getKeywords(), layoutRevision.getRobots(),
			layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
			layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
			layoutRevision.getColorSchemeId(), layoutRevision.getCss(),
			serviceContext);
	}

	@Reference
	private Language _language;

	@Reference
	private LayoutContentProvider _layoutContentProvider;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalizationLocalService _layoutLocalizationLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private Sites _sites;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}