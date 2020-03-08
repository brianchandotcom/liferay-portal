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

package com.liferay.users.admin.test.util.search;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author André de Oliveira
 */
public class GroupSearchFixture {

	public Group addGroup(GroupBlueprint groupBlueprint) {
		Group group = _addGroup();

		Locale locale = groupBlueprint.getDefaultLocale();

		if (locale != null) {
			_updateDisplaySettings(group, locale);
		}

		return group;
	}

	public List<Group> getGroups() {
		return Collections.unmodifiableList(_groups);
	}

	private Group _addGroup() {
		Group group = _addTestGroup();

		_groups.add(group);

		return group;
	}

	private Group _addTestGroup() {
		try {
			return GroupTestUtil.addGroup();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private Group _updateDisplaySettings(Group group, Locale locale) {
		try {
			return GroupTestUtil.updateDisplaySettings(
				group.getGroupId(), null, locale);
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private final List<Group> _groups = new ArrayList<>();

}