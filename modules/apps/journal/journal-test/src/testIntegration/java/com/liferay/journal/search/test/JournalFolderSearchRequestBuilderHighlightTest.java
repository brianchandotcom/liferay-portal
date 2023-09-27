/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	protected void addModels(String... keywords) throws Exception {
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