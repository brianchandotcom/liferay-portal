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

package com.liferay.object.rest.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Feliphe Marinho
 */
@Meta.OCD(
	factory = true,
	id = "com.liferay.object.rest.internal.configuration.FunctionObjectEntryManagerConfiguration"
)
public interface FunctionObjectEntryManagerConfiguration {

	@Meta.AD
	public String deleteObjectEntryResourcePath();

	@Meta.AD
	public String getObjectEntriesResourcePath();

	@Meta.AD
	public String getObjectEntryResourcePath();

	@Meta.AD
	public String oAuth2ApplicationExternalReferenceCode();

	@Meta.AD
	public String postObjectEntryResourcePath();

	@Meta.AD
	public String putObjectEntryResourcePath();

}