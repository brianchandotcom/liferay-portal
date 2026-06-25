/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.util;

import com.liferay.segments.model.SegmentsExperience;

import java.util.List;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class SegmentsExperienceUtil {

	public static boolean isActive(
		SegmentsExperience segmentsExperience,
		List<SegmentsExperience> segmentsExperiences) {

		for (SegmentsExperience curSegmentsExperience : segmentsExperiences) {
			if (!_isMatchingSegmentsEntry(
					curSegmentsExperience, segmentsExperience)) {

				continue;
			}

			if (curSegmentsExperience.getSegmentsExperienceId() ==
					segmentsExperience.getSegmentsExperienceId()) {

				return true;
			}

			return false;
		}

		return false;
	}

	private static boolean _isMatchingSegmentsEntry(
		SegmentsExperience curSegmentsExperience,
		SegmentsExperience segmentsExperience) {

		if (curSegmentsExperience.hasDefaultSegmentsEntry() ||
			(Objects.equals(
				curSegmentsExperience.getSegmentsEntryERC(),
				segmentsExperience.getSegmentsEntryERC()) &&
			 Objects.equals(
				 curSegmentsExperience.getSegmentsEntryScopeERC(),
				 segmentsExperience.getSegmentsEntryScopeERC()))) {

			return true;
		}

		return false;
	}

}