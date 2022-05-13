/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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