/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.PageSpecification;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Arrays;
import java.util.Objects;

import org.junit.Assert;

/**
 * @author Lourdes Fernández Besada
 */
public class PageSpecificationsTestUtil {

	public static void assertContentPageSpecification(
		Layout layout, PageSpecification pageSpecification) {

		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageSpecification.getExternalReferenceCode());

		if (layout.isDraftLayout()) {
			Assert.assertEquals(
				PageSpecification.Status.DRAFT, pageSpecification.getStatus());
		}
		else {
			Assert.assertEquals(
				PageSpecification.Status.APPROVED,
				pageSpecification.getStatus());
		}

		Assert.assertEquals(
			PageSpecification.Type.CONTENT_PAGE_SPECIFICATION,
			pageSpecification.getType());
	}

	public static void assertPageSpecifications(
		Layout layout, PageSpecification[] pageSpecifications) {

		Assert.assertTrue(ArrayUtil.isNotEmpty(pageSpecifications));

		if (!layout.isTypeAssetDisplay() && !layout.isTypeContent()) {
			_assertWidgetPageSpecifications(layout, pageSpecifications);

			return;
		}

		_assertContentPageSpecifications(layout, pageSpecifications);
	}

	private static void _assertContentPageSpecifications(
		Layout layout, PageSpecification[] pageSpecifications) {

		Layout draftLayout = layout.fetchDraftLayout();

		if (!layout.isPublished()) {
			Assert.assertEquals(
				Arrays.toString(pageSpecifications), 1,
				pageSpecifications.length);

			assertContentPageSpecification(draftLayout, pageSpecifications[0]);

			return;
		}

		if (Objects.equals(
				draftLayout.getStatus(), WorkflowConstants.STATUS_APPROVED)) {

			Assert.assertEquals(
				Arrays.toString(pageSpecifications), 1,
				pageSpecifications.length);

			assertContentPageSpecification(layout, pageSpecifications[0]);

			return;
		}

		Assert.assertEquals(
			Arrays.toString(pageSpecifications), 2, pageSpecifications.length);

		PageSpecification pageSpecification1 = pageSpecifications[0];

		Assert.assertEquals(
			PageSpecification.Type.CONTENT_PAGE_SPECIFICATION,
			pageSpecification1.getType());

		PageSpecification pageSpecification2 = pageSpecifications[1];

		Assert.assertEquals(
			PageSpecification.Type.CONTENT_PAGE_SPECIFICATION,
			pageSpecification2.getType());

		if (Objects.equals(
				layout.getExternalReferenceCode(),
				pageSpecification1.getExternalReferenceCode())) {

			Assert.assertEquals(
				PageSpecification.Status.APPROVED,
				pageSpecification1.getStatus());
			Assert.assertEquals(
				draftLayout.getExternalReferenceCode(),
				pageSpecification2.getExternalReferenceCode());
			Assert.assertEquals(
				PageSpecification.Status.DRAFT, pageSpecification2.getStatus());

			return;
		}

		Assert.assertEquals(
			draftLayout.getExternalReferenceCode(),
			pageSpecification1.getExternalReferenceCode());
		Assert.assertEquals(
			PageSpecification.Status.DRAFT, pageSpecification1.getStatus());
		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageSpecification2.getExternalReferenceCode());
		Assert.assertEquals(
			PageSpecification.Status.APPROVED, pageSpecification2.getStatus());
	}

	private static void _assertWidgetPageSpecifications(
		Layout layout, PageSpecification[] pageSpecifications) {

		Assert.assertEquals(
			Arrays.toString(pageSpecifications), 1, pageSpecifications.length);

		PageSpecification pageSpecification = pageSpecifications[0];

		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageSpecification.getExternalReferenceCode());
		Assert.assertEquals(
			PageSpecification.Status.APPROVED, pageSpecification.getStatus());
		Assert.assertEquals(
			PageSpecification.Type.WIDGET_PAGE_SPECIFICATION,
			pageSpecification.getType());
	}

}