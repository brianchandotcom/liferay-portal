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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsPortletKeys;
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
		"segments.field.customizer.key=" + UserSegmentsFieldCustomizer.KEY,
		"segments.field.customizer.priority:Integer=50"
	},
	service = SegmentsFieldCustomizer.class
)
public class UserSegmentsFieldCustomizer extends BaseSegmentsFieldCustomizer {

	public static final String KEY = "user";

	@Override
	public ClassedModel getClassedModel(String fieldValue) {
		return _getUser(fieldValue);
	}

	@Override
	public String getClassName() {
		return User.class.getName();
	}

	@Override
	public List<String> getFieldNames() {
		return _fieldNames;
	}

	@Override
	public String getFieldValueName(String fieldValue, Locale locale) {
		User user = _getUser(fieldValue);

		if (user == null) {
			return fieldValue;
		}

		return user.getFullName();
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public Field.SelectEntity getSelectEntity(PortletRequest portletRequest) {
		try {
			PortletURL portletURL = _portal.getControlPanelPortletURL(
				portletRequest, SegmentsPortletKeys.SEGMENTS,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mvcRenderCommandName", "selectUsers");
			portletURL.setParameter("eventName", "selectEntity");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return new Field.SelectEntity(
				"selectEntity",
				getSelectEntityTitle(
					_portal.getLocale(portletRequest), User.class.getName()),
				portletURL.toString(), true);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get select entity", e);
			}

			return null;
		}
	}

	private User _getUser(String fieldValue) {
		long userId = GetterUtil.getLong(fieldValue);

		if (userId == 0) {
			return null;
		}

		return _userLocalService.fetchUser(userId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserSegmentsFieldCustomizer.class);

	private static final List<String> _fieldNames = ListUtil.fromArray(
		"userId");

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}