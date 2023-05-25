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

package com.liferay.layout.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.search.test.util.BaseSearchRequestBuilderHighlightTestCase;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class LayoutSearchRequestBuilderHighlightTest
	extends BaseSearchRequestBuilderHighlightTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layoutFixture = new LayoutFixture(group);
	}

	@Override
	protected void addAssets(String... titles) throws Exception {
		for (String title : titles) {
			_layoutFixture.createLayout(title);
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return Layout.class;
	}

	protected String[] getFieldNames() {
		return new String[] {"title_en_US"};
	}

	private LayoutFixture _layoutFixture;

}