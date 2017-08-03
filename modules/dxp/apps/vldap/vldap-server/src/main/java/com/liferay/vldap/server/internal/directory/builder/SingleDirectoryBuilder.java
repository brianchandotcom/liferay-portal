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

package com.liferay.vldap.server.internal.directory.builder;

import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.Directory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Betts
 */
public abstract class SingleDirectoryBuilder extends DirectoryBuilder {

	@Override
	public List<Directory> buildDirectories(
		SearchBase searchBase, List<FilterConstraint> filterConstraints) {

		List<Directory> directories = new ArrayList<>();

		Directory directory = getDirectory();

		directories.add(directory);

		return directories;
	}

	@Override
	public boolean isValidAttribute(String attributeId, String value) {
		return true;
	}

	protected abstract Directory getDirectory();

}