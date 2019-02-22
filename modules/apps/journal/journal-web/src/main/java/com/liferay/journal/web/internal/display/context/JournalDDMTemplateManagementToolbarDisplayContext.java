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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.security.permission.resource.DDMTemplatePermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalDDMTemplateManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public JournalDDMTemplateManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest request,
			JournalDDMTemplateDisplayContext journalDDMTemplateDisplayContext)
		throws Exception {

		super(
			liferayPortletRequest, liferayPortletResponse, request,
			journalDDMTemplateDisplayContext.getDDMTemplateSearch());

		_journalDDMTemplateDisplayContext = journalDDMTemplateDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteDDMTemplates");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getAvailableActions(DDMTemplate ddmTemplate)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (DDMTemplatePermission.contains(
				themeDisplay.getPermissionChecker(), ddmTemplate,
				ActionKeys.DELETE)) {

			return "deleteDDMTemplates";
		}

		return StringPool.BLANK;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "ddmTemplateManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							liferayPortletResponse.createRenderURL(), "mvcPath",
							"/edit_ddm_template.jsp", "redirect",
							themeDisplay.getURLCurrent(), "groupId",
							String.valueOf(themeDisplay.getScopeGroupId()),
							"classNameId",
							String.valueOf(
								PortalUtil.getClassNameId(DDMStructure.class)),
							"classPK",
							String.valueOf(
								_journalDDMTemplateDisplayContext.getClassPK()),
							"resourceClassNameId",
							String.valueOf(
								PortalUtil.getClassNameId(
									JournalArticle.class)),
							"type", DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);
						dropdownItem.setLabel(LanguageUtil.get(request, "add"));
					});
			}
		};
	}

	@Override
	public String getDefaultEventHandler() {
		return "journalDDMTemplatesManagementToolbarDefaultEventHandler";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "ddmTemplates";
	}

	@Override
	public Boolean isSelectable() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		return !user.isDefaultUser();
	}

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (stagingGroupHelper.isLocalLiveGroup(group) ||
			stagingGroupHelper.isRemoteLiveGroup(group)) {

			return false;
		}

		try {
			if (DDMTemplatePermission.containsAddTemplatePermission(
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroupId(),
					PortalUtil.getClassNameId(DDMStructure.class),
					PortalUtil.getClassNameId(JournalArticle.class))) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"modified-date", "id"};
	}

	private final JournalDDMTemplateDisplayContext
		_journalDDMTemplateDisplayContext;

}