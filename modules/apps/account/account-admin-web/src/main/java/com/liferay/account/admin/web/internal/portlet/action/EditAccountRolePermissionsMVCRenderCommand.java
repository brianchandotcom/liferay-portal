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

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.admin.web.internal.util.AccountRoleRequestHelperUtil;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"mvc.command.name=/account_admin/edit_account_role_permissions"
	},
	service = MVCRenderCommand.class
)
public class EditAccountRolePermissionsMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		AccountRoleRequestHelperUtil.setRequestAttributes(
			renderRequest, _accountRoleTypeContributor, _panelAppRegistry,
			_panelCategoryRegistry, _personalMenuEntries);

		return "/account_entries_admin/edit_account_role.jsp";
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

	private void _removePersonalMenuEntry(PersonalMenuEntry personalMenuEntry) {
		_personalMenuEntries.remove(personalMenuEntry);
	}

	@Reference(target = "(component.name=*.AccountRoleTypeContributor)")
	private RoleTypeContributor _accountRoleTypeContributor;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	private final List<PersonalMenuEntry> _personalMenuEntries =
		new CopyOnWriteArrayList<>();

}