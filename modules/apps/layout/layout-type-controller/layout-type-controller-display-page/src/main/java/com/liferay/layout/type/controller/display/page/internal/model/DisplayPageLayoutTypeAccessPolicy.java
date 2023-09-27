/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.display.page.internal.model;

import com.liferay.layout.type.controller.model.BaseLayoutTypeAccessPolicy;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeAccessPolicy;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "layout.type=" + LayoutConstants.TYPE_ASSET_DISPLAY,
	service = LayoutTypeAccessPolicy.class
)
public class DisplayPageLayoutTypeAccessPolicy
	extends BaseLayoutTypeAccessPolicy {
}