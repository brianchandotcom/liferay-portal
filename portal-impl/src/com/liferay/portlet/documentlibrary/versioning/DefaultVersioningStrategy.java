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

package com.liferay.portlet.documentlibrary.versioning;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.versioning.VersioningStrategy;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

/**
 * @author Adolfo Pérez
 */
@OSGiBeanProperties(property = "service.ranking:Integer=" + Integer.MIN_VALUE)
public class DefaultVersioningStrategy implements VersioningStrategy {

	@Override
	public DLVersionNumberIncrease computeDLVersionNumberIncrease(
		DLFileVersion previousDLFileVersion, DLFileVersion nextDLFileVersion) {

		return DLVersionNumberIncrease.NONE;
	}

	@Override
	public boolean isOverridable() {
		return true;
	}

}