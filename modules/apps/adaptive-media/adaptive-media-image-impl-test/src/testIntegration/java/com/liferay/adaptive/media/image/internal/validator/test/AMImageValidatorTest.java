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

package com.liferay.adaptive.media.image.internal.validator.test;

import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.size.AMImageSizeProvider;
import com.liferay.adaptive.media.image.validator.AMImageValidator;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.FileVersionWrapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class AMImageValidatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testIsProcessingSupported() {
		for (String mimeType :
				_amImageMimeTypeProvider.getSupportedMimeTypes()) {

			if (mimeType.equals(ContentTypes.IMAGE_SVG_XML)) {
				Assert.assertFalse(
					_amImageValidator.isProcessingSupported(
						_getFileVersion(mimeType)));
			}
			else {
				Assert.assertTrue(
					_amImageValidator.isProcessingSupported(
						_getFileVersion(mimeType)));
			}
		}
	}

	@Test
	public void testIsValid() {
		for (String mimeType :
				_amImageMimeTypeProvider.getSupportedMimeTypes()) {

			Assert.assertTrue(
				_amImageValidator.isValid(_getFileVersion(mimeType)));
		}
	}

	private FileVersion _getFileVersion(String mimeType) {
		return new FileVersionWrapper(null) {

			public long getCompanyId() {
				return 0;
			}

			@Override
			public String getMimeType() {
				return mimeType;
			}

			@Override
			public long getSize() {
				return 1;
			}

		};
	}

	@Inject
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Inject
	private AMImageSizeProvider _amImageSizeProvider;

	@Inject
	private AMImageValidator _amImageValidator;

}