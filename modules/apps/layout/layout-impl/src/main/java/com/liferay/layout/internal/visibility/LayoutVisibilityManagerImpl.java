/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.visibility;

import com.liferay.layout.admin.kernel.visibility.LayoutVisibilityManager;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutVisibilityManager.class)
public class LayoutVisibilityManagerImpl implements LayoutVisibilityManager {

	@Override
	public boolean isPrivateLayoutsEnabled(long companyId) {
		if (FeatureFlagManagerUtil.isEnabled(companyId, "LPD-38869")) {
			return true;
		}

		return false;
	}

}