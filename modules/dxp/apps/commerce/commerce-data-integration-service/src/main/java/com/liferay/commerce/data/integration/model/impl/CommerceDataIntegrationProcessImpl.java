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

package com.liferay.commerce.data.integration.model.impl;

import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDataIntegrationProcessImpl
	extends CommerceDataIntegrationProcessBaseImpl {

	public CommerceDataIntegrationProcessImpl() {
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = new UnicodeProperties(true);

			_typeSettingsUnicodeProperties.fastLoad(getTypeSettings());
		}

		return _typeSettingsUnicodeProperties;
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		super.setTypeSettings(typeSettings);

		_typeSettingsUnicodeProperties = null;
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsUnicodeProperties) {

		_typeSettingsUnicodeProperties = typeSettingsUnicodeProperties;

		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = new UnicodeProperties();
		}

		super.setTypeSettings(_typeSettingsUnicodeProperties.toString());
	}

	private UnicodeProperties _typeSettingsUnicodeProperties;

}