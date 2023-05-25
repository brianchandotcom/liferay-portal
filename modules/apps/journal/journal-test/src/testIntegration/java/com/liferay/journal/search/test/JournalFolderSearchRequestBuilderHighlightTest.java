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

package com.liferay.journal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.test.util.JournalFolderFixture;
import com.liferay.portal.search.test.util.BaseSearchRequestBuilderHighlightTestCase;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class JournalFolderSearchRequestBuilderHighlightTest
	extends BaseSearchRequestBuilderHighlightTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_journalFolderFixture = new JournalFolderFixture(
			JournalFolderLocalServiceUtil.getService());
	}

	@Override
	protected void addAssets(String... keywords) throws Exception {
		for (String keyword : keywords) {
			_journalFolderFixture.addFolder(
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, keyword,
				keyword, serviceContext);
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return JournalFolder.class;
	}

	protected String[] getFieldNames() {
		return new String[] {"description_en_US", "title_en_US"};
	}

	private JournalFolderFixture _journalFolderFixture;

}