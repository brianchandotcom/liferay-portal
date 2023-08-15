/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.helper;

import org.opensaml.saml.common.messaging.context.SAMLBindingContext;

/**
 * @author Michael Bowerman
 */
public interface RelayStateHelper {

	public String getRelayState(SAMLBindingContext samlBindingContext);

	public void setRelayState(
		SAMLBindingContext samlBindingContext, String relayState);

}