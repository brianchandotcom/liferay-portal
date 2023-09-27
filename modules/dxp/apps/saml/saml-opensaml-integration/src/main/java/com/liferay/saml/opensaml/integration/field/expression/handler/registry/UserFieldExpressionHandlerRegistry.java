/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.field.expression.handler.registry;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.User;
import com.liferay.saml.opensaml.integration.field.expression.handler.UserFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.processor.context.UserProcessorContext;

import java.util.List;
import java.util.Map;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface UserFieldExpressionHandlerRegistry
	extends FieldExpressionHandlerRegistry
		<User, UserProcessorContext, UserFieldExpressionHandler> {

	@Override
	public UserFieldExpressionHandler getFieldExpressionHandler(String prefix);

	@Override
	public List<String> getOrderedFieldExpressionHandlerPrefixes();

	@Override
	public List<Map.Entry<String, UserFieldExpressionHandler>>
		getOrderedFieldExpressionHandlers();

}