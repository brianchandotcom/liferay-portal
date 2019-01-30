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

package com.liferay.segments.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizerRegistry;

import java.io.PrintWriter;

import java.util.Optional;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gavin Wan
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS,
		"mvc.command.name=getSegmentsFieldValueName"
	},
	service = MVCResourceCommand.class
)
public class GetSegmentsFieldValueNameMVCResourceCommand
	implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			PrintWriter printWriter = resourceResponse.getWriter();

			printWriter.write(getText(resourceRequest, resourceResponse));

			return false;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected String getText(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		String entityName = ParamUtil.getString(resourceRequest, "entityName");
		String fieldName = ParamUtil.getString(resourceRequest, "fieldName");
		String fieldValue = ParamUtil.getString(resourceRequest, "fieldValue");

		Optional<SegmentsFieldCustomizer> segmentFieldCustomizerOptional =
			_segmentsFieldCustomizerRegistry.getSegmentFieldCustomizerOptional(
				entityName, fieldName);

		if (!segmentFieldCustomizerOptional.isPresent()) {
			return fieldValue;
		}

		SegmentsFieldCustomizer segmentsFieldCustomizer =
			segmentFieldCustomizerOptional.get();

		return segmentsFieldCustomizer.getFieldValueName(
			fieldValue, _portal.getLocale(resourceRequest));
	}

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsFieldCustomizerRegistry _segmentsFieldCustomizerRegistry;

}