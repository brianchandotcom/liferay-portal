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

package com.liferay.media.object.apio.internal.architect.resource.test;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.media.object.apio.internal.architect.resource.test.model.MediaObjectImpl;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.io.ByteArrayInputStream;

import java.lang.reflect.Method;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class AdaptiveMediaMediaObjectNestedCollectionResourceTest
	extends BaseMediaObjectNestedCollectionResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Sync
	@Test
	public void testGetAdaptiveMedias() throws Exception {
		String fileName = "image.png";

		byte[] bytes = FileUtil.getBytes(
			getClass(),
			"/com/liferay/media/object/apio/internal/architect/resource/test" +
				"/dependencies/" + fileName);

		BinaryFile binaryFile = new BinaryFile(
			new ByteArrayInputStream(bytes), (long)bytes.length, "image/jpeg",
			fileName);

		FileEntry fileEntry = addFileEntry(
			_group.getGroupId(),
			new MediaObjectImpl(
				binaryFile, "My media object testGetAdaptiveMedias", null, null,
				null));

		List<AdaptiveMedia<AMImageProcessor>> adaptiveMedias =
			_getAdaptiveMedias(fileEntry);

		Assert.assertFalse(adaptiveMedias.toString(), adaptiveMedias.isEmpty());
	}

	private List<AdaptiveMedia<AMImageProcessor>> _getAdaptiveMedias(
			FileEntry fileEntry)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			getNestedCollectionResource();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getAdaptiveMedias", FileEntry.class);

		method.setAccessible(true);

		return (List<AdaptiveMedia<AMImageProcessor>>)method.invoke(
			getNestedCollectionResource(), fileEntry);
	}

	@DeleteAfterTestRun
	private Group _group;

}