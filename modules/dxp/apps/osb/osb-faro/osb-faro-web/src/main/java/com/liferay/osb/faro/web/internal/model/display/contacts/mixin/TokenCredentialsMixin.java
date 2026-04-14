/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Matthew Kong
 */
public abstract class TokenCredentialsMixin {

	@JsonIgnore
	public abstract String getPrivateKey();

	@JsonIgnore
	public abstract String getPublicKey();

}