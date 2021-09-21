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
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListenerTracker;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.util.Collections;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
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

		try {
			LayoutPermissionUtil.check(
				themeDisplay.getPermissionChecker(), draftLayout,
				ActionKeys.UPDATE);

			LayoutPermissionUtil.check(
				themeDisplay.getPermissionChecker(), layout, ActionKeys.UPDATE);
		}
		catch (PrincipalException principalException) {
			if (!LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.UPDATE_LAYOUT_CONTENT)) {

				throw principalException;
			}
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_publishLayout(
			draftLayout, layout, serviceContext, themeDisplay.getUserId());

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
			Layout draftLayout, Layout layout, ServiceContext serviceContext,
			long userId)
		throws Exception {

		LayoutStructureUtil.deleteMarkedForDeletionItems(
			draftLayout.getCompanyId(), _contentPageEditorListenerTracker,
			draftLayout.getGroupId(), draftLayout.getPlid(), _portletRegistry);

		if (_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				layout.getCompanyId(), layout.getGroupId(),
				Layout.class.getName())) {

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				layout.getCompanyId(), layout.getGroupId(), userId,
				Layout.class.getName(), layout.getPlid(), layout,
				serviceContext, Collections.emptyMap());
		}
		else {
			layout = _layoutCopyHelper.copyLayout(draftLayout, layout);

			layout.setType(draftLayout.getType());
			layout.setStatus(WorkflowConstants.STATUS_APPROVED);

			String layoutPrototypeUuid = layout.getLayoutPrototypeUuid();

			layout.setLayoutPrototypeUuid(null);

			_layoutLocalService.updateLayout(layout);

			draftLayout = _layoutLocalService.getLayout(draftLayout.getPlid());

			UnicodeProperties typeSettingsUnicodeProperties =
				draftLayout.getTypeSettingsProperties();

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				typeSettingsUnicodeProperties.setProperty(
					"layoutPrototypeUuid", layoutPrototypeUuid);
			}

			draftLayout.setStatus(WorkflowConstants.STATUS_APPROVED);

			_layoutLocalService.updateLayout(draftLayout);

			_updateLayoutRevision(layout, serviceContext);
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
	private ContentPageEditorListenerTracker _contentPageEditorListenerTracker;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletRegistry _portletRegistry;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}