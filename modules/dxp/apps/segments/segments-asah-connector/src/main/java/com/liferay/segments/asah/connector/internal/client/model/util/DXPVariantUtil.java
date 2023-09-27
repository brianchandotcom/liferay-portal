/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariant;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariants;
import com.liferay.segments.model.SegmentsExperimentRel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Sarai Díaz
 */
public class DXPVariantUtil {

	public static DXPVariant toDXPVariant(
			Locale locale, SegmentsExperimentRel segmentsExperimentRel)
		throws PortalException {

		DXPVariant dxpVariant = new DXPVariant();

		dxpVariant.setChanges(0);
		dxpVariant.setControl(segmentsExperimentRel.isControl());
		dxpVariant.setDXPVariantId(
			segmentsExperimentRel.getSegmentsExperienceKey());
		dxpVariant.setDXPVariantName(segmentsExperimentRel.getName(locale));
		dxpVariant.setTrafficSplit(segmentsExperimentRel.getSplit() * 100);

		return dxpVariant;
	}

	public static List<DXPVariant> toDXPVariantList(
			Locale locale, List<SegmentsExperimentRel> segmentsExperimentRels)
		throws PortalException {

		List<DXPVariant> dxpVariants = new ArrayList<>();

		for (SegmentsExperimentRel segmentsExperimentRel :
				segmentsExperimentRels) {

			dxpVariants.add(toDXPVariant(locale, segmentsExperimentRel));
		}

		return dxpVariants;
	}

	public static DXPVariants toDXPVariants(
			Locale locale, List<SegmentsExperimentRel> segmentsExperimentRels)
		throws PortalException {

		return new DXPVariants(
			toDXPVariantList(locale, segmentsExperimentRels));
	}

	private DXPVariantUtil() {
	}

}