/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.field.expression.handler;

import aQute.bnd.annotation.ProviderType;

import com.liferay.saml.opensaml.integration.processor.context.SamlSpIdpConnectionProcessorContext;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface SamlSpIdpConnectionFieldExpressionHandler
	extends FieldExpressionHandler
		<SamlSpIdpConnection, SamlSpIdpConnectionProcessorContext> {

	@Override
	public void bindProcessorContext(
		SamlSpIdpConnectionProcessorContext
			samlSpIdpConnectionProcessorContext);

}