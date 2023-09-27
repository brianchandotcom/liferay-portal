/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.BaseSearchRequestBuilderHighlightTestCase;

import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class DLFileEntrySearchRequestBuilderHighlightTest
	extends BaseSearchRequestBuilderHighlightTestCase {

	@Override
	protected void addModels(String... titles) throws Exception {
		for (String title : titles) {
			DLAppLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), group.getGroupId(), 0,
				StringUtil.randomString(),
				ContentTypes.APPLICATION_OCTET_STREAM, title, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, new byte[0], null, null,
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return DLFileEntry.class;
	}

	protected String[] getFieldNames() {
		return new String[] {"title_en_US"};
	}

}