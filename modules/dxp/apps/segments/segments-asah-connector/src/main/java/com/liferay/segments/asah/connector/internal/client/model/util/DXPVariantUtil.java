/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model.util;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariant;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Locale;

/**
 * @author Sarai Díaz
 */
public class DXPVariantUtil {

	public static DXPVariant toDXPVariant(
			Locale locale,
			SegmentsExperienceLocalService segmentsExperienceLocalService,
			SegmentsExperimentRel segmentsExperimentRel)
		throws PortalException {

		DXPVariant dxpVariant = new DXPVariant();

		dxpVariant.setChanges(0);
		dxpVariant.setControl(segmentsExperimentRel.isControl());
		dxpVariant.setDXPVariantId(
			_getSegmentsExperienceKey(
				segmentsExperienceLocalService, segmentsExperimentRel));
		dxpVariant.setDXPVariantName(segmentsExperimentRel.getName(locale));
		dxpVariant.setTrafficSplit(segmentsExperimentRel.getSplit() * 100);

		return dxpVariant;
	}

	public static List<DXPVariant> toDXPVariantList(
			Locale locale,
			SegmentsExperienceLocalService segmentsExperienceLocalService,
			List<SegmentsExperimentRel> segmentsExperimentRels)
		throws PortalException {

		return TransformUtil.transform(
			segmentsExperimentRels,
			segmentsExperimentRel -> toDXPVariant(
				locale, segmentsExperienceLocalService, segmentsExperimentRel));
	}

	public static DXPVariants toDXPVariants(
			Locale locale,
			SegmentsExperienceLocalService segmentsExperienceLocalService,
			List<SegmentsExperimentRel> segmentsExperimentRels)
		throws PortalException {

		return new DXPVariants(
			toDXPVariantList(
				locale, segmentsExperienceLocalService,
				segmentsExperimentRels));
	}

	private static String _getSegmentsExperienceKey(
			SegmentsExperienceLocalService segmentsExperienceLocalService,
			SegmentsExperimentRel segmentsExperimentRel)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperimentRel.getSegmentsExperienceId());

		UnicodeProperties typeSettingsUnicodeProperties =
			segmentsExperience.getTypeSettingsUnicodeProperties();

		String segmentsExperimentSegmentsExperienceKey =
			typeSettingsUnicodeProperties.get(
				"segmentsExperimentSegmentsExperienceKey");

		if (Validator.isNotNull(segmentsExperimentSegmentsExperienceKey)) {
			return segmentsExperimentSegmentsExperienceKey;
		}

		return segmentsExperience.getSegmentsExperienceKey();
	}

	private DXPVariantUtil() {
	}

}