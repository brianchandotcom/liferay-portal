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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.field.Field;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;

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
		"segments.field.customizer.entity.name=User",
		"segments.field.customizer.key=" + GroupSegmentsFieldCustomizer.KEY,
		"segments.field.customizer.priority:Integer=50"
	},
	service = SegmentsFieldCustomizer.class
)
public class GroupSegmentsFieldCustomizer extends BaseSegmentsFieldCustomizer {

	public static final String KEY = "group";

	@Override
	public ClassedModel getClassedModel(String fieldValue) {
		return _getGroup(fieldValue);
	}

	@Override
	public String getClassName() {
		return Group.class.getName();
	}

	@Override
	public List<String> getFieldNames() {
		return _fieldNames;
	}

	@Override
	public String getFieldValueName(String fieldValue, Locale locale) {
		Group group = _getGroup(fieldValue);

		if (group == null) {
			return fieldValue;
		}

		try {
			return group.getDescriptiveName(locale);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get name for group " + fieldValue, pe);
			}

			return fieldValue;
		}
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public Field.SelectEntity getSelectEntity(PortletRequest portletRequest) {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				portletRequest, Group.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter("eventName", "selectEntity");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return new Field.SelectEntity(
				"selectEntity",
				getSelectEntityTitle(
					_portal.getLocale(portletRequest), Group.class.getName()),
				portletURL.toString(), false);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get select entity", e);
			}

			return null;
		}
	}

	private Group _getGroup(String fieldValue) {
		long groupId = GetterUtil.getLong(fieldValue);

		if (groupId == 0) {
			return null;
		}

		return _groupLocalService.fetchGroup(groupId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupSegmentsFieldCustomizer.class);

	private static final List<String> _fieldNames = ListUtil.fromArray(
		"groupIds");

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}