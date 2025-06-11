/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.cms.site.initializer.internal.display.context.ViewSpaceContentsSectionDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = FragmentRenderer.class)
public class ViewSpaceContentsJSPSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer
		<ViewSpaceContentsSectionDisplayContext> {

	@Override
	public String getLabelKey() {
		return "space-contents";
	}

	@Override
	protected ViewSpaceContentsSectionDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new ViewSpaceContentsSectionDisplayContext(
			_depotEntryLocalService, getGroupId(httpServletRequest),
			_groupLocalService, httpServletRequest, _language,
			_objectDefinitionService, _objectDefinitionSettingLocalService,
			_portal);
	}

	protected long getGroupId(HttpServletRequest httpServletRequest) {
		Object object = httpServletRequest.getAttribute(
			InfoDisplayWebKeys.INFO_ITEM);

		DepotEntry depotEntry =
			object instanceof DepotEntry ? (DepotEntry)object : null;

		if (depotEntry != null) {
			return depotEntry.getGroupId();
		}

		return 0;
	}

	@Override
	protected String getJSPPath() {
		return "/view_space_contents.jsp";
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

	@Reference
	private Portal _portal;

}