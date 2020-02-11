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

import com.liferay.mule.internal.config.LiferayConfig;
import com.liferay.mule.internal.error.LiferayError;

import org.mule.runtime.api.meta.Category;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;
import org.mule.runtime.extension.api.annotation.license.RequiresEnterpriseLicense;

/**
 * @author Matija Petanjek
 */
@Configurations(LiferayConfig.class)
@ErrorTypes(LiferayError.class)
@Extension(category = Category.CERTIFIED, name = "Liferay", vendor = "Liferay")
@RequiresEnterpriseLicense(allowEvaluationLicense = true)
@Xml(prefix = "liferay")
public class LiferayConnector {
}