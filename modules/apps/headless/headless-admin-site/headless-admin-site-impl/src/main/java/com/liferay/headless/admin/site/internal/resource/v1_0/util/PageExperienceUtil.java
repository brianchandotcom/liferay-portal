/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Mikel Lorza
 */
public class PageExperienceUtil {

	public static void validatePageExperiences(
		SegmentsExperience defaultSegmentsExperience,
		PageExperience[] pageExperiences) {

		if ((defaultSegmentsExperience == null) ||
			ArrayUtil.isEmpty(pageExperiences)) {

			throw new UnsupportedOperationException();
		}

		PageExperience defaultPageExperience = null;

		Set<String> pageExperienceKeys = new HashSet<>(pageExperiences.length);

		for (PageExperience pageExperience : pageExperiences) {
			if (!pageExperienceKeys.add(pageExperience.getKey())) {
				throw new UnsupportedOperationException();
			}

			if (Objects.equals(
					pageExperience.getKey(),
					SegmentsExperienceConstants.KEY_DEFAULT)) {

				defaultPageExperience = pageExperience;
			}
		}

		if ((defaultPageExperience == null) ||
			!StringUtil.equals(
				defaultSegmentsExperience.getExternalReferenceCode(),
				defaultPageExperience.getExternalReferenceCode()) ||
			((defaultPageExperience.getPriority() != null) &&
			 (defaultPageExperience.getPriority() != 0)) ||
			(defaultPageExperience.getSegmentItemExternalReference() != null)) {

			throw new UnsupportedOperationException();
		}
	}

}