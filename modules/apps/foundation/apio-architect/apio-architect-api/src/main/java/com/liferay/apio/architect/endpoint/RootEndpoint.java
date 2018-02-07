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

package com.liferay.apio.architect.endpoint;

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.documentation.Documentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Declares the endpoint from which all of your APIs originate. There should
 * only be one {@code RootEndpoint} in the application.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@ProviderType
public interface RootEndpoint {

	/**
	 * Returns the endpoint for binary operations.
	 *
	 * @return the endpoint for binary operations.
	 * @review
	 */
	@Path("/b/")
	public BinaryEndpoint binaryEndpoint();

	/**
	 * Returns the application profile.
	 *
	 * @return the application profile.
	 * @review
	 */
	@GET
	@Path("/doc")
	public Documentation documentation();

	/**
	 * Returns the endpoint for form operations.
	 *
	 * @return the endpoint for form operations.
	 * @review
	 */
	@Path("/f/")
	public FormEndpoint formEndpoint();

	/**
	 * Returns the string representation of the application's home.
	 *
	 * @return the string representation of the application's home
	 */
	@GET
	@Path("/")
	public String home();

	/**
	 * Returns the endpoint for page operations.
	 *
	 * @return the endpoint for page operations.
	 * @review
	 */
	@Path("/p/")
	public PageEndpoint pageEndpoint();

}