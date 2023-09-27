/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.model.impl;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderedProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Properties;

/**
 * @author Mika Koivisto
 */
public class SamlSpIdpConnectionImpl extends SamlSpIdpConnectionBaseImpl {

	@Override
	public Properties getNormalizedUserAttributeMappings() throws IOException {
		Properties userAttributeMappingsProperties = new OrderedProperties();

		String userAttributeMappings = getUserAttributeMappings();

		if (Validator.isNotNull(userAttributeMappings)) {
			userAttributeMappingsProperties.load(
				new UnsyncStringReader(userAttributeMappings));

			userAttributeMappingsProperties.replaceAll(
				(key, value) -> _removeDefaultPrefix(
					GetterUtil.getString(value)));
		}

		return userAttributeMappingsProperties;
	}

	private String _removeDefaultPrefix(String userFieldExpression) {
		if (Validator.isNotNull(userFieldExpression) &&
			(userFieldExpression.charAt(0) == CharPool.COLON)) {

			return userFieldExpression.substring(1);
		}

		return userFieldExpression;
	}

}