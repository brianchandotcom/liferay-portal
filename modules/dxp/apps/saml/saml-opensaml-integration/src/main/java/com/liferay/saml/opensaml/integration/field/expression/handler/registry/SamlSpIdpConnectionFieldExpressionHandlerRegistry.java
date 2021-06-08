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