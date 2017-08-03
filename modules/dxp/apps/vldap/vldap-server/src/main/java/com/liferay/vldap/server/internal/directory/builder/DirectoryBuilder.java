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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.Directory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class DirectoryBuilder {

	public void addDirectoryBuilder(DirectoryBuilder directoryBuilder) {
		_directoryBuilders.add(directoryBuilder);
	}

	public List<Directory> buildDirectories(
			SearchBase searchBase, List<FilterConstraint> filterConstraints)
		throws Exception {

		return new ArrayList<>();
	}

	public List<Directory> buildDirectories(
		SearchBase searchBase, List<FilterConstraint> filterConstraints,
		boolean subtree) {

		if (_built) {
			return Collections.emptyList();
		}

		List<Directory> directories = null;

		try {
			directories = buildDirectories(searchBase, filterConstraints);
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptyList();
		}

		if (subtree) {
			for (DirectoryBuilder directoryBuilder : _directoryBuilders) {
				directories.addAll(
					directoryBuilder.buildDirectories(
						searchBase, filterConstraints, subtree));
			}
		}

		_built = true;

		return directories;
	}

	public List<DirectoryBuilder> getDirectoryBuilders() {
		return _directoryBuilders;
	}

	public boolean isValidAttribute(String attributeId, String value) {
		return attributeValidator.isValidAttribute(attributeId, value);
	}

	public boolean isValidFilterConstraint(FilterConstraint filterConstraint) {
		Map<String, String> map = filterConstraint.getMap();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (!isValidAttribute(entry.getKey(), entry.getValue())) {
				return false;
			}
		}

		return true;
	}

	protected final AttributeValidator attributeValidator =
		new AttributeValidator();

	private static final Log _log = LogFactoryUtil.getLog(
		DirectoryBuilder.class);

	private boolean _built;
	private final List<DirectoryBuilder> _directoryBuilders = new ArrayList<>();

}