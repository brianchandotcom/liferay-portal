/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.filter.name;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class SynonymSetFilterNameHolderImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_synonymSetFilterNameHolderImpl = new SynonymSetFilterNameHolderImpl();
	}

	@Test
	public void testActivate() {
		_synonymSetFilterNameHolderImpl.activate(Collections.emptyMap());

		Assert.assertArrayEquals(
			new String[] {
				"liferay_filter_synonym_en", "liferay_filter_synonym_es"
			},
			_synonymSetFilterNameHolderImpl.getFilterNames());
	}

	private SynonymSetFilterNameHolderImpl _synonymSetFilterNameHolderImpl;

}