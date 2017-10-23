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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.saml.opensaml.integration.resolver.NameIdResolver;

import java.util.function.Function;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.NameID;

/**
 * @author Tomas Polesovsky
 */
public class NameIdResolverSAMLCommand<T, R extends NameIdResolver>
	extends SAMLCommandImpl<SAMLObject, SAMLObject, T, R> {

	public NameIdResolverSAMLCommand(
		Function<SAMLMessageContext<SAMLObject, SAMLObject, NameID>, T>
			samlMessageContextFunction) {

		super(samlMessageContextFunction);
	}

}