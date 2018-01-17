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

package com.liferay.user.associated.data.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;

/**
 * @author William Newbury
 */
public class UADEntityTypeComposite {

	public UADEntityTypeComposite(
		long userId, String key, UADEntityDisplay uadEntityDisplay,
		List<UADEntity> uadEntities) {

		_userId = userId;
		_key = key;
		_uadEntityDisplay = uadEntityDisplay;
		_uadEntities = uadEntities;
	}

	public int getCount() {
		return _uadEntities.size();
	}

	public String getEditURL(
			UADEntity uadEntity, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return _uadEntityDisplay.getEditURL(
			uadEntity, liferayPortletRequest, liferayPortletResponse);
	}

	public String getEntityNonAnonymizableFields(UADEntity uadEntity)
		throws PortalException {

		String nonAnonymizableFields =
			_uadEntityDisplay.getEntityNonAnonymizableFieldValues(uadEntity);

		if (nonAnonymizableFields == null) {
			nonAnonymizableFields = "";
		}

		return nonAnonymizableFields;
	}

	public String getEntityTypeDescription() {
		return _uadEntityDisplay.getEntityTypeDescription();
	}

	public String getEntityTypeName() {
		return _uadEntityDisplay.getEntityTypeName();
	}

	public String getEntityTypeNonAnonymizableFields() {
		String nonAnonymizableFields =
			_uadEntityDisplay.getEntityTypeNonAnonymizableFieldNames();

		if (nonAnonymizableFields == null) {
			nonAnonymizableFields = "";
		}

		return nonAnonymizableFields;
	}

	public String getKey() {
		return _key;
	}

	public List<UADEntity> getUADEntities() {
		return _uadEntities;
	}

	public long getUserId() {
		return _userId;
	}

	private final String _key;
	private final List<UADEntity> _uadEntities;
	private final UADEntityDisplay _uadEntityDisplay;
	private final long _userId;

}