/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.field.expression.handler.registry;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.saml.opensaml.integration.field.expression.handler.FieldExpressionHandler;
import com.liferay.saml.opensaml.integration.processor.context.ProcessorContext;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface FieldExpressionHandlerRegistry
	<M extends BaseModel<M>, PC extends ProcessorContext<M>,
	 V extends FieldExpressionHandler<M, PC>> {

	public V getFieldExpressionHandler(String prefix);

	public Set<String> getFieldExpressionHandlerPrefixes();

	public List<String> getOrderedFieldExpressionHandlerPrefixes();

	public List<Map.Entry<String, V>> getOrderedFieldExpressionHandlers();

}