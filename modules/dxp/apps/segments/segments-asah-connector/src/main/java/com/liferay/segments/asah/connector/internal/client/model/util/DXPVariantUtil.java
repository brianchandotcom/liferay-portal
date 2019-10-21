/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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