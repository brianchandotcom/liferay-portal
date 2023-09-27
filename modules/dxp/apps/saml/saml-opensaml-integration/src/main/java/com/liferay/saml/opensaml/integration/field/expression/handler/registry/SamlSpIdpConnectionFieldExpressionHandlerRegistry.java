/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.field.expression.handler.registry;

import aQute.bnd.annotation.ProviderType;

import com.liferay.saml.opensaml.integration.field.expression.handler.SamlSpIdpConnectionFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.processor.context.SamlSpIdpConnectionProcessorContext;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface SamlSpIdpConnectionFieldExpressionHandlerRegistry
	extends FieldExpressionHandlerRegistry
		<SamlSpIdpConnection, SamlSpIdpConnectionProcessorContext,
		 SamlSpIdpConnectionFieldExpressionHandler> {

	@Override
	public SamlSpIdpConnectionFieldExpressionHandler getFieldExpressionHandler(
		String prefix);

	@Override
	public Set<String> getFieldExpressionHandlerPrefixes();

	@Override
	public List<String> getOrderedFieldExpressionHandlerPrefixes();

	@Override
	public List<Map.Entry<String, SamlSpIdpConnectionFieldExpressionHandler>>
		getOrderedFieldExpressionHandlers();

}