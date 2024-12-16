/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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

	public static void testPostSiteSiteByExternalReferenceCodePageSpecification(
			Layout layout, PageSpecification[] pageSpecifications,
			ServiceContext serviceContext,
			UnsafeFunction
				<ContentPageSpecification, ContentPageSpecification, Exception>
					unsafeFunction)
		throws Exception {

		assertPageSpecifications(layout, pageSpecifications);

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertFalse(_isPublished(draftLayout));

		assertPageSpecifications(layout, pageSpecifications);

		ContentPageSpecification contentPageSpecification =
			(ContentPageSpecification)pageSpecifications[0];

		Assert.assertEquals(
			contentPageSpecification.getStatus(),
			PageSpecification.Status.DRAFT);

		contentPageSpecification.setExternalReferenceCode(
			layout.getExternalReferenceCode());

		_assertProblemException(
			() -> unsafeFunction.apply(contentPageSpecification));

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_APPROVED);

		contentPageSpecification.setExternalReferenceCode(
			draftLayout.getExternalReferenceCode());

		_assertProblemException(
			() -> unsafeFunction.apply(contentPageSpecification));

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);

		draftLayout = layout.fetchDraftLayout();

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_APPROVED);

		contentPageSpecification.setExternalReferenceCode(
			draftLayout.getExternalReferenceCode());

		contentPageSpecification.setStatus(PageSpecification.Status.APPROVED);

		_assertProblemException(
			() -> unsafeFunction.apply(contentPageSpecification));

		contentPageSpecification.setExternalReferenceCode(
			layout.getExternalReferenceCode());

		_assertProblemException(
			() -> unsafeFunction.apply(contentPageSpecification));

		contentPageSpecification.setExternalReferenceCode(
			draftLayout.getExternalReferenceCode());

		contentPageSpecification.setStatus(PageSpecification.Status.DRAFT);

		assertContentPageSpecification(
			draftLayout, unsafeFunction.apply(contentPageSpecification));

		draftLayout = LayoutLocalServiceUtil.getLayout(draftLayout.getPlid());

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_DRAFT);

		_assertProblemException(
			() -> unsafeFunction.apply(contentPageSpecification));

		draftLayout = LayoutLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		contentPageSpecification.setExternalReferenceCode((String)null);
		contentPageSpecification.setStatus((PageSpecification.Status)null);

		assertContentPageSpecification(
			draftLayout, unsafeFunction.apply(contentPageSpecification));

		draftLayout = LayoutLocalServiceUtil.getLayout(draftLayout.getPlid());

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_DRAFT);

		_assertProblemException(
			() -> unsafeFunction.apply(contentPageSpecification));
	}

	private static void _assertContentPageSpecifications(
		Layout layout, PageSpecification[] pageSpecifications) {

		Layout draftLayout = layout.fetchDraftLayout();

		if (!_isPublished(draftLayout)) {
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

	private static void _assertProblemException(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();
			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
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

	private static boolean _isPublished(Layout draftLayout) {
		if (GetterUtil.getBoolean(
				draftLayout.getTypeSettingsProperty("published"))) {

			return true;
		}

		return false;
	}

}