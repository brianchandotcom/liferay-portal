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