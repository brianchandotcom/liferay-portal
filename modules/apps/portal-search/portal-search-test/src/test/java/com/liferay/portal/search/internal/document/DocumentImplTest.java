/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.document;

import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class DocumentImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		documentFixture.setUp();

		documentImpl = new DocumentImpl();
	}

	@After
	public void tearDown() throws Exception {
		documentFixture.tearDown();
	}

	@Test
	public void testAddDate() throws Exception {
		_clearDateFormat();

		documentImpl.addDate(RandomTestUtil.randomString(), new Date());
	}

	@Test
	public void testAddDateSortable() throws Exception {
		_clearDateFormat();

		documentImpl.addDateSortable(RandomTestUtil.randomString(), new Date());
	}

	protected DocumentFixture documentFixture = new DocumentFixture();
	protected DocumentImpl documentImpl;

	private void _clearDateFormat() {
		ReflectionTestUtil.setFieldValue(
			DocumentImpl.class, "_dateFormat", null);
	}

}