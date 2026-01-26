/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cmp.site.initializer.internal.display.context.ViewTaskInfoSummarySectionDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Leite
 */
@Component(service = FragmentRenderer.class)
public class ViewTaskInfoSummaryJSPSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	protected Object getDisplayContext(HttpServletRequest httpServletRequest)
		throws PortalException {

		Object object = httpServletRequest.getAttribute(
			InfoDisplayWebKeys.INFO_ITEM);

		if (!(object instanceof ObjectEntry)) {
			return null;
		}

		return new ViewTaskInfoSummarySectionDisplayContext(
			_listTypeEntryLocalService, (ObjectEntry)object,
			_objectFieldLocalService, _objectStateFlowLocalService,
			_objectStateLocalService,
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY));
	}

	@Override
	protected String getJSPPath() {
		return "/view_task_info_summary.jsp";
	}

	@Override
	protected String getLabelKey() {
		return "task-info-summary";
	}

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Reference
	private ObjectStateLocalService _objectStateLocalService;

}