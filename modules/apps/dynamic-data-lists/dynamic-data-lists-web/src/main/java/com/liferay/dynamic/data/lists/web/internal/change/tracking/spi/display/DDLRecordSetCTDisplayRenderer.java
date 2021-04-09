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

package com.liferay.dynamic.data.lists.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.web.internal.security.permission.resource.DDLRecordSetPermission;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DDLRecordSetCTDisplayRenderer
	extends BaseCTDisplayRenderer<DDLRecordSet> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, DDLRecordSet ddlRecordSet)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!DDLRecordSetPermission.contains(
				themeDisplay.getPermissionChecker(), ddlRecordSet,
				ActionKeys.UPDATE)) {

			return null;
		}

		Group group = _groupLocalService.getGroup(ddlRecordSet.getGroupId());

		if (group.isCompany()) {
			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, DDLPortletKeys.DYNAMIC_DATA_LISTS, 0,
				0, PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_record_set.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"groupId", ddlRecordSet.getGroupId()
		).setParameter(
			"recordSetId", ddlRecordSet.getRecordSetId()
		).setParameter(
			"version", ddlRecordSet.getVersion()
		).buildString();
	}

	@Override
	public Class<DDLRecordSet> getModelClass() {
		return DDLRecordSet.class;
	}

	@Override
	public String getTitle(Locale locale, DDLRecordSet ddlRecordSet) {
		return ddlRecordSet.getName(locale);
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DDLRecordSet> displayBuilder) {
		DDLRecordSet ddlRecordSet = displayBuilder.getModel();

		Locale locale = displayBuilder.getLocale();

		displayBuilder.display(
			"name", ddlRecordSet.getName(locale)
		).display(
			"description", ddlRecordSet.getDescription(locale)
		).display(
			"data-definition",
			() -> {
				DDMStructure ddmStructure =
					_ddmStructureLocalService.fetchDDMStructure(
						ddlRecordSet.getDDMStructureId());

				if (ddmStructure != null) {
					return ddmStructure.getName(locale);
				}

				return StringPool.BLANK;
			}
		).display(
			"workflow",
			() -> {
				WorkflowDefinitionLink workflowDefinitionLink =
					_workflowDefinitionLinkLocalService.
						fetchWorkflowDefinitionLink(
							ddlRecordSet.getCompanyId(),
							ddlRecordSet.getGroupId(),
							DDLRecordSet.class.getName(),
							ddlRecordSet.getRecordSetId(), 0, true);

				if (workflowDefinitionLink != null) {
					return workflowDefinitionLink.getWorkflowDefinitionName();
				}

				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					locale, DDLRecordSetCTDisplayRenderer.class);

				return _language.get(resourceBundle, "no-workflow");
			}
		);
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}