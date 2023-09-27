/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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