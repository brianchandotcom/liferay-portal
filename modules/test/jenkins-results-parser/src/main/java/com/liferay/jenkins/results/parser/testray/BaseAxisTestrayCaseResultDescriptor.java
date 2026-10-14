/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseAxisTestrayCaseResultDescriptor
	extends BaseTestrayCaseResultDescriptor {

	public String getAxisName() {
		return _axisTestClassGroup.getAxisName();
	}

	@Override
	public AxisTestClassGroup getAxisTestClassGroup() {
		return _axisTestClassGroup;
	}

	@Override
	public String getName() {
		return getAxisName();
	}

	protected BaseAxisTestrayCaseResultDescriptor(
		AxisTestClassGroup axisTestClassGroup,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor) {

		super(axisTestClassGroup.getJob(), parentTestrayCaseResultDescriptor);

		_axisTestClassGroup = axisTestClassGroup;
	}

	private final AxisTestClassGroup _axisTestClassGroup;

}