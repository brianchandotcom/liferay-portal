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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.security.permission.resource.DDMStructurePermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class DLViewFileEntryMetadataSetsManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public DLViewFileEntryMetadataSetsManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			DLViewFileEntryMetadataSetsDisplayContext
				dlViewFileEntryMetadataSetsDisplayContext)
		throws Exception {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			dlViewFileEntryMetadataSetsDisplayContext.getStructureSearch());

		_dlRequestHelper = new DLRequestHelper(httpServletRequest);
		_dlViewFileEntryMetadataSetsDisplayContext =
			dlViewFileEntryMetadataSetsDisplayContext;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("keywords", StringPool.BLANK);

		return portletURL.toString();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)liferayPortletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				dropdownItem.setHref(
					liferayPortletResponse.createRenderURL(),
					"mvcRenderCommandName",
					"/document_library/ddm/edit_ddm_structure", "redirect",
					themeDisplay.getURLCurrent(), "groupId",
					String.valueOf(_dlRequestHelper.getScopeGroupId()));

				dropdownItem.setLabel(
					LanguageUtil.get(_dlRequestHelper.getRequest(), "new"));
			}
		).build();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("navigation", "file_entry_metadata_sets");
		portletURL.setParameter(
			"groupId", String.valueOf(_dlRequestHelper.getScopeGroupId()));

		return portletURL.toString();
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isShowCreationMenu() {
		try {
			return _isShowAddButton();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get creation menu", portalException);
			}

			return false;
		}
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"modified-date", "id"};
	}

	private boolean _isShowAddButton() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if ((!group.hasLocalOrRemoteStagingGroup() || group.isStagingGroup()) &&
			DDMStructurePermission.containsAddDDMStructurePermission(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(),
				_dlViewFileEntryMetadataSetsDisplayContext.
					getStructureClassNameId())) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLViewFileEntryMetadataSetsManagementToolbarDisplayContext.class);

	private final DLRequestHelper _dlRequestHelper;
	private final DLViewFileEntryMetadataSetsDisplayContext
		_dlViewFileEntryMetadataSetsDisplayContext;

}