/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.rest.internal.jaxrs.context.provider;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.internal.deployer.ObjectDefinitionDeployerImpl;
import com.liferay.object.rest.internal.jaxrs.context.provider.util.ObjectsContextProviderUtil;
import com.liferay.object.rest.petra.sql.dsl.expression.FilterPredicateFactory;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Luis Miguel Barcos
 */
@Provider
public class PredicateContextProvider implements ContextProvider<Predicate> {

	public PredicateContextProvider(
		FilterPredicateFactory filterPredicateFactory,
		ObjectDefinitionDeployerImpl objectDefinitionDeployerImpl,
		Portal portal) {

		_filterPredicateFactory = filterPredicateFactory;
		_objectDefinitionDeployerImpl = objectDefinitionDeployerImpl;
		_portal = portal;
	}

	@Override
	public Predicate createContext(Message message) {
		String filter = ParamUtil.getString(
			ObjectsContextProviderUtil.getHttpServletRequest(message),
			"filter");

		Predicate predicate = null;

		if (Validator.isNotNull(filter)) {
			ObjectDefinition objectDefinition =
				ObjectsContextProviderUtil.getObjectDefinition(
					message, _objectDefinitionDeployerImpl, _portal);

			predicate = _filterPredicateFactory.create(
				filter, objectDefinition.getObjectDefinitionId());
		}

		return predicate;
	}

	private final FilterPredicateFactory _filterPredicateFactory;
	private final ObjectDefinitionDeployerImpl _objectDefinitionDeployerImpl;
	private final Portal _portal;

}