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

package com.liferay.journal.content.web.portlet.ddm;

import com.liferay.journal.content.web.util.JournalContentPortletKeys;
import com.liferay.portlet.dynamicdatamapping.util.DDMDisplay;
import com.liferay.portlet.journal.ddm.JournalDDMDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=com_liferay_journal_content_web_portlet_JournalContentPortlet"
	},
	service = DDMDisplay.class
)
public class JournalContentDDMDisplay extends JournalDDMDisplay {

	@Override
	public String getPortletId() {
		return JournalContentPortletKeys.JOURNAL_CONTENT;
	}

}