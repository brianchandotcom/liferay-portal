/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.pmd.rules.junit;

import net.sourceforge.pmd.testframework.SimpleAggregatorTst;

import org.junit.Before;

/**
 * @author Cristina González
 */
public class AssertFailJUnitRuleTest extends SimpleAggregatorTst {

	@Before
	@Override
	public void setUp() {
		addRule(_RULESET, "AssertFailJUnitRule");
	}

	private static final String _RULESET = "java-liferay-junit";

}