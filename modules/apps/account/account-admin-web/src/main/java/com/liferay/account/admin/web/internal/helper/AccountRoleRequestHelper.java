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

package com.liferay.account.admin.web.internal.helper;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.application.list.display.context.logic.PersonalMenuEntryHelper;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;
import com.liferay.roles.admin.constants.RolesAdminWebKeys;
import com.liferay.roles.admin.panel.category.role.type.mapper.PanelCategoryRoleTypeMapper;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
@Component(service = AccountRoleRequestHelper.class)
public class AccountRoleRequestHelper {

	public void setRequestAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_APP_REGISTRY, _panelAppRegistry);
		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_HELPER,
			new PanelCategoryHelper(_panelAppRegistry, _panelCategoryRegistry));
		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY,
			_panelCategoryRegistry);
		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PERSONAL_MENU_ENTRY_HELPER,
			new PersonalMenuEntryHelper(_personalMenuEntries));
		httpServletRequest.setAttribute(
			RolesAdminWebKeys.CURRENT_ROLE_TYPE, _accountRoleTypeContributor);
		httpServletRequest.setAttribute(
			RolesAdminWebKeys.PANEL_CATEGORY_KEYS,
			ArrayUtil.toStringArray(_panelCategoryKeys));
		httpServletRequest.setAttribute(
			RolesAdminWebKeys.SHOW_NAV_TABS, Boolean.FALSE);
	}

	public void setRequestAttributes(PortletRequest portletRequest) {
		setRequestAttributes(_portal.getHttpServletRequest(portletRequest));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "_removePanelCategoryRoleTypeMapper"
	)
	private void _addPanelCategoryRoleTypeMapper(
		PanelCategoryRoleTypeMapper panelCategoryRoleTypeMapper) {

		if (ArrayUtil.contains(
				panelCategoryRoleTypeMapper.getRoleTypes(),
				RoleConstants.TYPE_ACCOUNT)) {

			_panelCategoryKeys.add(
				panelCategoryRoleTypeMapper.getPanelCategoryKey());
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "_removePersonalMenuEntry"
	)
	private void _addPersonalMenuEntry(PersonalMenuEntry personalMenuEntry) {
		_personalMenuEntries.add(personalMenuEntry);
	}

	private void _removePanelCategoryRoleTypeMapper(
		PanelCategoryRoleTypeMapper panelCategoryRoleTypeMapper) {

		_panelCategoryKeys.remove(
			panelCategoryRoleTypeMapper.getPanelCategoryKey());
	}

	private void _removePersonalMenuEntry(PersonalMenuEntry personalMenuEntry) {
		_personalMenuEntries.remove(personalMenuEntry);
	}

	@Reference(target = "(component.name=*.AccountRoleTypeContributor)")
	private RoleTypeContributor _accountRoleTypeContributor;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	private final List<String> _panelCategoryKeys =
		new CopyOnWriteArrayList<>();

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	private final List<PersonalMenuEntry> _personalMenuEntries =
		new CopyOnWriteArrayList<>();

	@Reference
	private Portal _portal;

}