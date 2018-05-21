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