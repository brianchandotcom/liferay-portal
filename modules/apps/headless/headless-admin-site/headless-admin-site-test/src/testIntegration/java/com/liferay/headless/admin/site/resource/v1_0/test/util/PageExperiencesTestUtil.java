/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.PageExperience;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import org.junit.Assert;

/**
 * @author Lourdes Fernández Besada
 */
public class PageExperiencesTestUtil {

	public static void assertPageExperiences(
		PageExperience[] expectedPageExperiences, Layout layout,
		PageExperience[] pageExperiences) {

		Assert.assertEquals(
			pageExperiences.toString(), 1, pageExperiences.length);

		PageExperience pageExperience = pageExperiences[0];

		if (expectedPageExperiences == null) {
			Assert.assertNotNull(pageExperience.getExternalReferenceCode());
		}
		else {
			PageExperience expectedPageExperience = expectedPageExperiences[0];

			Assert.assertEquals(
				expectedPageExperience.getExternalReferenceCode(),
				pageExperience.getExternalReferenceCode());
		}

		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageExperience.getPageSpecificationExternalReferenceCode());

		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperience(
				layout.getPlid());

		Assert.assertEquals(
			segmentsExperience.getExternalReferenceCode(),
			pageExperience.getExternalReferenceCode());
	}

}