/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.processor.factory;

import com.liferay.saml.opensaml.integration.field.expression.handler.registry.SamlSpIdpConnectionFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.processor.SamlSpIdpConnectionProcessor;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;

/**
 * @author Stian Sigvartsen
 */
public interface SamlSpIdpConnectionProcessorFactory {

	public SamlSpIdpConnectionProcessor create(
		SamlSpIdpConnection samlSpIdpConnection,
		SamlSpIdpConnectionFieldExpressionHandlerRegistry
			samlSpIdpConnectionFieldExpressionHandlerRegistry);

}