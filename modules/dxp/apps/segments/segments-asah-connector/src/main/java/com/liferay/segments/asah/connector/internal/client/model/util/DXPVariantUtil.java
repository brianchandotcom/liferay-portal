/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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