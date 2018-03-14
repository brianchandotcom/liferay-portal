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

package com.liferay.contacts.uad.display;

import com.liferay.contacts.model.Entry;

import com.liferay.petra.string.StringPool;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = EntryUADEntityDisplayHelper.class)
public class EntryUADEntityDisplayHelper {
	/**
	 * Implement getEntryEditURL() to enable editing Entries from the GDPR UI.
	 *
	 * <p>
	 * Editing Entries in the GDPR UI depends on generating valid edit URLs. Implement getEntryEditURL() such that it returns a valid edit URL for the specified Entry.
	 * </p>
	 *
	 */
	public String getEntryEditURL(Entry entry,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {
		return StringPool.BLANK;
	}
}