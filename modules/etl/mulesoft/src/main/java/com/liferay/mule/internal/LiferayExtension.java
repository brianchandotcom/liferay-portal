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

package com.liferay.mule.internal;

import com.liferay.mule.internal.connection.BasicCachedConnectionProvider;
import com.liferay.mule.internal.connection.OAuth2CachedConnectionProvider;
import com.liferay.mule.internal.error.LiferayError;
import com.liferay.mule.internal.operation.LiferayOperations;

import org.mule.runtime.api.meta.Category;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;
import org.mule.runtime.extension.api.annotation.license.RequiresEnterpriseLicense;

/**
 * @author Matija Petanjek
 */
@ConnectionProviders(
	{BasicCachedConnectionProvider.class, OAuth2CachedConnectionProvider.class}
)
@ErrorTypes(LiferayError.class)
@Extension(category = Category.CERTIFIED, name = "Liferay", vendor = "Liferay")
@Operations(LiferayOperations.class)
@RequiresEnterpriseLicense(allowEvaluationLicense = true)
@Xml(prefix = "liferay")
public class LiferayExtension {
}