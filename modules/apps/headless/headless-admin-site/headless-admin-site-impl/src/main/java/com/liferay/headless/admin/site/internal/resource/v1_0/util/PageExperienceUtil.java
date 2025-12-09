/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.Objects;

/**
 * @author Mikel Lorza
 */
public class PageExperienceUtil {

	public static PageExperience getDefaultPageExperience(
		PageExperience[] pageExperiences) {

		if (ArrayUtil.isEmpty(pageExperiences)) {
			return null;
		}

		for (PageExperience pageExperience : pageExperiences) {
			if (Objects.equals(
					pageExperience.getKey(),
					SegmentsExperienceConstants.KEY_DEFAULT)) {

				return pageExperience;
			}
		}

		return null;
	}

}