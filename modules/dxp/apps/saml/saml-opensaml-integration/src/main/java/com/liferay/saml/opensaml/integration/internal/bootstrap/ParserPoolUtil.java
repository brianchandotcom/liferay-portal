/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.bootstrap;

import com.liferay.saml.opensaml.integration.internal.util.ConfigurationServiceBootstrapUtil;

import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.opensaml.core.xml.config.XMLObjectProviderRegistry;

/**
 * @author Shuyang Zhou
 */
public class ParserPoolUtil {

	public static ParserPool getParserPool() {
		XMLObjectProviderRegistry xmlObjectProviderRegistry =
			ConfigurationServiceBootstrapUtil.get(
				XMLObjectProviderRegistry.class);

		return xmlObjectProviderRegistry.getParserPool();
	}

}