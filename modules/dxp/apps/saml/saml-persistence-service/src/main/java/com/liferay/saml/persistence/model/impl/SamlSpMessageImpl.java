/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.model.impl;

import java.util.Date;

/**
 * @author Mika Koivisto
 */
public class SamlSpMessageImpl extends SamlSpMessageBaseImpl {

	@Override
	public boolean isExpired() {
		Date date = new Date();

		Date expirationDate = getExpirationDate();

		return expirationDate.before(date);
	}

}