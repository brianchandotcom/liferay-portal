/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class DLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetAllMediaGalleryMimeTypesContainsSVG() {
		Set<String> allMediaGalleryMimeTypes =
			DLUtil.getAllMediaGalleryMimeTypes();

		Assert.assertTrue(
			allMediaGalleryMimeTypes.toString(),
			allMediaGalleryMimeTypes.contains(ContentTypes.IMAGE_SVG_XML));
	}

	@Test
	public void testIsValidVersion() {
		Assert.assertTrue(DLUtil.isValidVersion("1.1"));
		Assert.assertTrue(DLUtil.isValidVersion("1.1~" + UUID.randomUUID()));
		Assert.assertTrue(
			DLUtil.isValidVersion(
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION +
					StringPool.TILDE + UUID.randomUUID()));
		Assert.assertTrue(
			DLUtil.isValidVersion(
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION));
	}

}