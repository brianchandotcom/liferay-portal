/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SearchContainerTest {

	@After
	public void tearDown() {
		_searchContainer = null;
	}

	@Test
	public void
		testCalculateCurWhenTotalSmallerThanCurLessOneMultiplyByDelta() {

		int cur = 341;
		int delta = 20;
		int total = 1001;

		createSearchContainer(cur, delta);

		_searchContainer.setTotal(total);

		Assert.assertEquals(51, _searchContainer.getCur());
	}

	@Test
	public void testCalculateCurWhenTotalBiggerThanCurLessOneMultiplyByDelta() {
		int cur = 35;
		int delta = 20;
		int total = 999;

		createSearchContainer(cur, delta);

		_searchContainer.setTotal(total);

		Assert.assertEquals(35, _searchContainer.getCur());
	}

	@Test
	public void testCalculateCurWhenTotalEqualsDelta() {
		int cur = 2;
		int delta = 5;
		int total = 5;

		createSearchContainer(cur, delta);

		_searchContainer.setTotal(total);

		Assert.assertEquals(1, _searchContainer.getCur());
	}

	@Test
	public void testCalculateCurWhenTotalEqualsZero() {
		int cur = 2;
		int delta = 5;
		int total = 0;

		createSearchContainer(cur, delta);

		_searchContainer.setTotal(total);

		Assert.assertEquals(1, _searchContainer.getCur());
	}

	@Test
	public void testCalculateCurWhenTotalLessThanDelta() {
		int cur = 2;
		int delta = 5;
		int total = 1;

		createSearchContainer(cur, delta);

		_searchContainer.setTotal(total);

		Assert.assertEquals(1, _searchContainer.getCur());
	}

	@Test
	public void testCalculateCurWhenTotalMoreThanDelta() {
		int cur = 5;
		int delta = 5;
		int total = 16;

		createSearchContainer(cur, delta);

		_searchContainer.setTotal(total);

		Assert.assertEquals(4, _searchContainer.getCur());
	}

	protected void createSearchContainer(int cur, int delta) {
		PortletRequest portletRequest= PowerMockito.mock(PortletRequest.class);

		PortletURL portletURL = PowerMockito.mock(PortletURL.class);

		_searchContainer = new SearchContainer(
			portletRequest, null, null, "cur2", cur, delta, portletURL, null,
			null);
	}

	private SearchContainer _searchContainer;

}