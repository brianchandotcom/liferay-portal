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

package com.liferay.document.library.info.extractor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.info.extractor.InfoTextExtractor;
import com.liferay.info.extractor.InfoTextExtractorTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DLFileEntryInfoTextExtractorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExtract() throws Exception {
		String content = RandomTestUtil.randomString();

		byte[] bytes = content.getBytes();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), null, null, 0, null, null,
			new ByteArrayInputStream(bytes), bytes.length, serviceContext);

		InfoTextExtractor infoTextExtractor =
			_infoTextExtractorTracker.getInfoTextExtractor(
				DLFileEntryConstants.getClassName());

		Assert.assertEquals(
			content + StringPool.NEW_LINE,
			infoTextExtractor.extract(dlFileEntry, LocaleUtil.getDefault()));
	}

	@Inject
	private InfoTextExtractorTracker _infoTextExtractorTracker;

}