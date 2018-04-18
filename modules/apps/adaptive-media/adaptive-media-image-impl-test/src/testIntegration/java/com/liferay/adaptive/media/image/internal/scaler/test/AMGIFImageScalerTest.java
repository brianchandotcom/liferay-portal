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

package com.liferay.adaptive.media.image.internal.scaler.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.scaler.AMImageScaledImage;
import com.liferay.adaptive.media.image.scaler.AMImageScaler;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class AMGIFImageScalerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Assume.assumeTrue(_amImageScaler.isEnabled());

		_group = GroupTestUtil.addGroup();

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry -> true);

		for (AMImageConfigurationEntry amImageConfigurationEntry :
				amImageConfigurationEntries) {

			_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry.getUUID());
		}

		_amImageConfigurationEntry = _addTestVariant();
	}

	@Test
	public void testAMGIFImageScaler() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		FileEntry fileEntry = _addFileEntry();

		AMImageScaledImage amImageScaledImage = _amImageScaler.scaleImage(
			fileEntry.getFileVersion(), _amImageConfigurationEntry);

		Assert.assertEquals(25, amImageScaledImage.getHeight());
		Assert.assertEquals(100, amImageScaledImage.getWidth());

		Assert.assertArrayEquals(
			FileUtil.getBytes(AMGIFImageScalerTest.class, "scaled.gif"),
			FileUtil.getBytes(amImageScaledImage.getInputStream()));
	}

	private FileEntry _addFileEntry() throws Exception {
		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".gif", ContentTypes.IMAGE_GIF,
			FileUtil.getBytes(AMGIFImageScalerTest.class, "image.gif"),
			new ServiceContext());
	}

	private AMImageConfigurationEntry _addTestVariant() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		return _amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "small", StringPool.BLANK, "0",
			properties);
	}

	private AMImageConfigurationEntry _amImageConfigurationEntry;

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Inject(filter = "mime.type=image/gif", type = AMImageScaler.class)
	private AMImageScaler _amImageScaler;

	@DeleteAfterTestRun
	private Group _group;

}