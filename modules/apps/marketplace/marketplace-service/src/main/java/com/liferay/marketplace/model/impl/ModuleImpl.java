/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model.impl;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Ryan Park
 * @author Joan Kim
 */
public class ModuleImpl extends ModuleBaseImpl {

	@Override
	public boolean isBundle() {
		if (Validator.isNull(getBundleSymbolicName()) &&
			Validator.isNull(getBundleVersion())) {

			return false;
		}

		return true;
	}

}