/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.LinkToPagePageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.LinkToURLPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.PageSetPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSpecification;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class PageSpecificationUtil {

	public static PageSpecification getPageSpecification(
		PageSpecification[] pageSpecifications) {

		if (ArrayUtil.isEmpty(pageSpecifications)) {
			return null;
		}

		if (pageSpecifications.length != 1) {
			throw new UnsupportedOperationException();
		}

		PageSpecification pageSpecification = pageSpecifications[0];

		if ((!(pageSpecification instanceof LinkToPagePageSpecification) &&
			 !(pageSpecification instanceof LinkToURLPageSpecification) &&
			 !(pageSpecification instanceof PageSetPageSpecification) &&
			 !(pageSpecification instanceof WidgetPageSpecification)) ||
			!Objects.equals(
				pageSpecification.getStatus(),
				PageSpecification.Status.APPROVED)) {

			throw new UnsupportedOperationException();
		}

		return pageSpecification;
	}

	public static int getPublishedStatus(
		PageSpecification[] pageSpecifications) {

		if (pageSpecifications == null) {
			return WorkflowConstants.STATUS_DRAFT;
		}

		if (pageSpecifications.length != 2) {
			throw new UnsupportedOperationException();
		}

		ContentPageSpecification publishedContentPageSpecification =
			(ContentPageSpecification)pageSpecifications[0];

		if (Validator.isNull(
				publishedContentPageSpecification.
					getDraftContentPageSpecificationExternalReferenceCode())) {

			publishedContentPageSpecification =
				(ContentPageSpecification)pageSpecifications[1];
		}

		if (Objects.equals(
				publishedContentPageSpecification.getStatus(),
				PageSpecification.Status.APPROVED)) {

			return WorkflowConstants.STATUS_APPROVED;
		}

		return WorkflowConstants.STATUS_DRAFT;
	}

	public static WidgetPageSpecification getWidgetPageSpecification(
		PageSpecification[] pageSpecifications) {

		return (WidgetPageSpecification)getPageSpecification(
			pageSpecifications);
	}

}