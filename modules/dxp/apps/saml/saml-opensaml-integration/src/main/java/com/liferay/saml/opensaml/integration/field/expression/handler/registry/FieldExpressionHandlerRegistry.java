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