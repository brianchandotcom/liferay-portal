/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.provider;

import com.liferay.layout.utility.page.kernel.provider.LayoutUtilityPageEntryLayoutProvider;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = LayoutUtilityPageEntryLayoutProvider.class)
public class LayoutUtilityPageEntryLayoutProviderImpl
	implements LayoutUtilityPageEntryLayoutProvider {

	@Override
	public Layout getDefaultLayoutUtilityPageEntryLayout(
			long groupId, String type)
		throws PortalException {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.
				fetchDefaultLayoutUtilityPageEntry(groupId, type);

		if (layoutUtilityPageEntry == null) {
			return null;
		}

		return _layoutLocalService.getLayout(layoutUtilityPageEntry.getPlid());
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

}