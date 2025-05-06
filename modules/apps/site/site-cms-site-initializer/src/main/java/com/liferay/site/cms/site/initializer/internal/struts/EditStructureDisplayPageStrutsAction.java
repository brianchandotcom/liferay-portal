/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.struts;

import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.layout.manager.FormManager;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "path=/cms/edit_structure_display_page",
	service = StrutsAction.class
)
public class EditStructureDisplayPageStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletResponse.sendRedirect(
			ActionUtil.getDisplayPageEditURL(
				_formManager, _fragmentEntryLinkListenerRegistry,
				httpServletRequest,
				_objectDefinitionService.getObjectDefinition(
					ParamUtil.getLong(
						httpServletRequest, "objectDefinitionId"))));

		return null;
	}

	@Reference
	private FormManager _formManager;

	@Reference
	private FragmentEntryLinkListenerRegistry
		_fragmentEntryLinkListenerRegistry;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

}