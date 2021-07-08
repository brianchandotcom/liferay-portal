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

package com.liferay.batch.engine.internal.task.progress;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayOutputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Matija Petanjek
 */
public abstract class BaseBatchEngineTaskProgressTestCase {

	protected byte[] compressContent(byte[] content, String contentType)
		throws Exception {

		try (ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream()) {

			try (ZipOutputStream zipOutputStream = new ZipOutputStream(
					byteArrayOutputStream)) {

				ZipEntry zipEntry = new ZipEntry(
					"import." + StringUtil.toLowerCase(contentType));

				zipOutputStream.putNextEntry(zipEntry);

				zipOutputStream.write(content, 0, content.length);
			}

			return byteArrayOutputStream.toByteArray();
		}
	}

	protected static final String PRODUCT_JSON = StringBundler.concat(
		"{\"active\":true,\"catalogId\":111,\"name\":{\"en_US\":",
		"\"Test Product\"},\"productType\":\"simple\",\"tags\":[],",
		"\"workflowStatusInfo\":{\"code\":0,\"label\":\"approved\",",
		"\"label_i18n\":\"Approved\"}}");

	protected static final int PRODUCTS_COUNT = 10;

}