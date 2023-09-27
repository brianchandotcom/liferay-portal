/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	protected void addModels(String... titles) throws Exception {
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