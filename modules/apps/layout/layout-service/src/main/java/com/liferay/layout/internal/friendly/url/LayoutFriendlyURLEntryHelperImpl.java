/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.friendly.url;

import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = LayoutFriendlyURLEntryHelper.class)
public class LayoutFriendlyURLEntryHelperImpl
	implements LayoutFriendlyURLEntryHelper {

	@Override
	public String getClassName(boolean privateLayout) {
		return _resourceActions.getCompositeModelName(
			Layout.class.getName(), String.valueOf(privateLayout));
	}

	@Override
	public long getClassNameId(boolean privateLayout) {
		return _portal.getClassNameId(getClassName(privateLayout));
	}

	@Reference
	private Portal _portal;

	@Reference
	private ResourceActions _resourceActions;

}