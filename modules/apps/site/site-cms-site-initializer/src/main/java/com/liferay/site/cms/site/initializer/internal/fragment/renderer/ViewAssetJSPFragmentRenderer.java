/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.site.cms.site.initializer.internal.display.context.ViewAssetDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = FragmentRenderer.class)
public class ViewAssetJSPFragmentRenderer
	extends BaseJSPSectionFragmentRenderer<ViewAssetDisplayContext> {

	@Override
	public String getLabelKey() {
		return "view-asset";
	}

	@Override
	protected ViewAssetDisplayContext getDisplayContext(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return new ViewAssetDisplayContext(
			httpServletRequest, _objectDefinitionLocalService,
			_objectEntryService);
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

}