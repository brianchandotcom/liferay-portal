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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.client.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.client.serdes.v1_0.BlogPostingImageSerDes;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.io.ByteArrayInputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class BlogPostingImageResourceTest
	extends BaseBlogPostingImageResourceTestCase {

	@Override
	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = super.getEntityFields(type);

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> !StringUtil.equals(
				"fileExtension", entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	@Override
	protected MultipartBody toMultipartBody(BlogPostingImage blogPostingImage) {
		testContentType = "multipart/form-data;boundary=PART";

		Map<String, BinaryFile> binaryFileMap = new HashMap<>();

		String randomString = RandomTestUtil.randomString();

		binaryFileMap.put(
			"file",
			new BinaryFile(
				testContentType, RandomTestUtil.randomString(),
				new ByteArrayInputStream(randomString.getBytes()), 0));

		return MultipartBody.of(
			binaryFileMap, __ -> null,
			BlogPostingImageSerDes.toMap(blogPostingImage));
	}

}