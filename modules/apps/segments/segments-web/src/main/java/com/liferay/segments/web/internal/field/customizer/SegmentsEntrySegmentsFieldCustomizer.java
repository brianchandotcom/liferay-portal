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

package com.liferay.segments.web.internal.field.customizer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.field.Field;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"segments.field.customizer.entity.name=Segment",
		"segments.field.customizer.key=" + SegmentsEntrySegmentsFieldCustomizer.KEY,
		"segments.field.customizer.priority:Integer=50"
	},
	service = SegmentsFieldCustomizer.class
)
public class SegmentsEntrySegmentsFieldCustomizer
	extends BaseSegmentsFieldCustomizer {

	public static final String KEY = "segment";

	@Override
	public ClassedModel getClassedModel(String fieldValue) {
		return _getSegmentsEntry(fieldValue);
	}

	@Override
	public String getClassName() {
		return SegmentsEntry.class.getName();
	}

	@Override
	public List<String> getFieldNames() {
		return _fieldNames;
	}

	@Override
	public String getFieldValueName(String fieldValue, Locale locale) {
		SegmentsEntry segmentsEntry = _getSegmentsEntry(fieldValue);

		if (segmentsEntry == null) {
			return null;
		}

		return segmentsEntry.getName(locale);
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public Field.SelectEntity getSelectEntity(PortletRequest portletRequest) {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				portletRequest, SegmentsEntry.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter("eventName", "selectEntity");

			long segmentsEntryId = ParamUtil.getLong(
				portletRequest, "segmentsEntryId");

			if (segmentsEntryId > 0) {
				portletURL.setParameter(
					"excludedSegmentsEntryIds",
					String.valueOf(segmentsEntryId));
			}

			portletURL.setParameter(
				"excludedSources",
				StringUtil.toLowerCase(SegmentsEntryConstants.SOURCE_REFERRED));
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return new Field.SelectEntity(
				"selectEntity",
				getSelectEntityTitle(
					_portal.getLocale(portletRequest),
					SegmentsEntry.class.getName()),
				portletURL.toString(), false);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get select entity", exception);
			}

			return null;
		}
	}

	private SegmentsEntry _getSegmentsEntry(String fieldValue) {
		long segmentsEntryId = GetterUtil.getLong(fieldValue);

		if (segmentsEntryId == 0) {
			return null;
		}

		return _segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntrySegmentsFieldCustomizer.class);

	private static final List<String> _fieldNames = ListUtil.fromArray(
		"segmentsEntryIds");

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}