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

package com.liferay.message.boards.uad.display;

import com.liferay.message.boards.model.MBMessage;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import org.osgi.service.component.annotations.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = MBMessageUADEntityDisplayHelper.class)
public class MBMessageUADEntityDisplayHelper {
	/**
	 * Returns an ordered string array of the fields' names to be displayed.
	 * Each field name corresponds to a table column based on the order they are
	 * specified.
	 *
	 * @return the array of field names to display
	 */
	public String[] getDisplayFieldNames() {
		return new String[] { "subject", "body" };
	}

	/**
	 * Implement getMBMessageEditURL() to enable editing MBMessages from the GDPR UI.
	 *
	 * <p>
	 * Editing MBMessages in the GDPR UI depends on generating valid edit URLs. Implement getMBMessageEditURL() such that it returns a valid edit URL for the specified MBMessage.
	 * </p>
	 *
	 */
	public String getMBMessageEditURL(MBMessage mbMessage,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {
		return "";
	}

	public Map<String, Object> getUADEntityNonanonymizableFieldValues(
		MBMessage mbMessage) {
		Map<String, Object> uadEntityNonanonymizableFieldValues = new HashMap<String, Object>();

		uadEntityNonanonymizableFieldValues.put("subject",
			mbMessage.getSubject());
		uadEntityNonanonymizableFieldValues.put("body", mbMessage.getBody());

		return uadEntityNonanonymizableFieldValues;
	}
}