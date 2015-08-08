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

package com.liferay.portal.kernel.upload;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.UploadServletRequestImpl;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.output.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Shinn Lok
 */
public class UploadServletRequestTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testWrapUploadServletRequestTwice() throws Exception {
		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		Part[] parts = new Part[] {new StringPart("key", "value")};

		PostMethod postMethod = new PostMethod();

		MultipartRequestEntity multipartRequestEntity =
			new MultipartRequestEntity(parts, postMethod.getParams());

		ByteArrayOutputStream requestContent = new ByteArrayOutputStream();

		multipartRequestEntity.writeRequest(requestContent);

		httpServletRequest.setContent(requestContent.toByteArray());

		httpServletRequest.setContentType(
			multipartRequestEntity.getContentType());
		httpServletRequest.setCharacterEncoding(StringPool.UTF8);

		UploadServletRequestImpl uploadServletRequest1 =
			new UploadServletRequestImpl(httpServletRequest);

		UploadServletRequestImpl uploadServletRequest2 =
			new UploadServletRequestImpl(uploadServletRequest1);

		String[] values = uploadServletRequest2.getParameterValues("key");

		Assert.assertEquals(1, values.length);
	}

}