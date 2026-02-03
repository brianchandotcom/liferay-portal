/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cmp.site.initializer.internal.display.context.ViewTasksSectionDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(service = FragmentRenderer.class)
public class ViewTasksJSPSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer<ViewTasksSectionDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabelKey() {
		return "tasks";
	}

	@Override
	protected ViewTasksSectionDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new ViewTasksSectionDisplayContext(
			httpServletRequest, _listTypeEntryLocalService,
			_objectFieldLocalService, _objectStateFlowLocalService,
			_objectStateLocalService,
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_CMP_PROJECT", themeDisplay.getCompanyId()),
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_CMP_TASK", themeDisplay.getCompanyId()));
	}

	@Override
	protected String getJSPPath() {
		return "/view_tasks.jsp";
	}

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Reference
	private ObjectStateLocalService _objectStateLocalService;

}