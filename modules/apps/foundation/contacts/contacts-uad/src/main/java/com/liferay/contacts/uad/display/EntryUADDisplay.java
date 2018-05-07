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

import com.liferay.contacts.constants.ContactsPortletKeys;
import com.liferay.contacts.model.Entry;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = UADDisplay.class)
public class EntryUADDisplay extends BaseEntryUADDisplay {

	@Override
	public String getEditURL(
			Entry entry, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			portal.getControlPanelPlid(liferayPortletRequest),
			ContactsPortletKeys.CONCTACTS_CENTER, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/contacts_center/edit_entry");
		portletURL.setParameter(
			"redirect", portal.getCurrentURL(liferayPortletRequest));
		portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

		return portletURL.toString();
	}

	@Reference
	protected Portal portal;

}