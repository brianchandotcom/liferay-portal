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

package com.liferay.analytics.settings.web.internal.search;

import com.liferay.analytics.settings.web.internal.model.Field;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.RenderResponse;

/**
 * @author Rachael Koestartyo
 */
public class FieldChecker extends EmptyOnClickRowChecker {

	public FieldChecker(
		String mvcRenderCommandName, RenderResponse renderResponse,
		String[] recommendedFieldNames, String[] requiredFieldNames,
		String[] selectedFieldNames) {

		super(renderResponse);

		if (StringUtil.equalsIgnoreCase(
				mvcRenderCommandName,
				"/analytics_settings/edit_synced_contacts_fields")) {

			setRowIds("syncedContactFieldNames");
		}
		else if (StringUtil.equalsIgnoreCase(
					mvcRenderCommandName,
					"/analytics_settings/edit_synced_users_fields")) {

			setRowIds("syncedUserFieldNames");
		}

		_recommendedFieldNames = recommendedFieldNames;
		_requiredFieldNames = requiredFieldNames;
		_selectedFieldNames = selectedFieldNames;
	}

	@Override
	public boolean isChecked(Object object) {
		Field field = (Field)object;

		if (ArrayUtil.contains(_recommendedFieldNames, field.getName()) ||
			ArrayUtil.contains(_requiredFieldNames, field.getName()) ||
			ArrayUtil.contains(_selectedFieldNames, field.getName())) {

			return true;
		}

		return super.isChecked(object);
	}

	@Override
	public boolean isDisabled(Object object) {
		Field field = (Field)object;

		if (ArrayUtil.contains(_requiredFieldNames, field.getName())) {
			return true;
		}

		return super.isDisabled(object);
	}

	private final String[] _recommendedFieldNames;
	private final String[] _requiredFieldNames;
	private final String[] _selectedFieldNames;

}