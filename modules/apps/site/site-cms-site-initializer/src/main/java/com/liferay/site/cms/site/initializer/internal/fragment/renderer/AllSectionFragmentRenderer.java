/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.cms.site.initializer.internal.display.context.AllSectionDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FragmentRenderer.class)
public class AllSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer<AllSectionDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabelKey() {
		return "all-section";
	}

	@Override
	protected AllSectionDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new AllSectionDisplayContext(
			_depotEntryLocalService, _groupLocalService, httpServletRequest,
			language, _objectDefinitionService,
			_objectDefinitionSettingLocalService, _portal);
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

	@Reference
	private Portal _portal;

}