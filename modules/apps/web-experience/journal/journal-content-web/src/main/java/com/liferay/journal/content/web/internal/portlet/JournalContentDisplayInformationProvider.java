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

package com.liferay.journal.content.web.internal.portlet;

import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.DisplayInformationProvider;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
	service = DisplayInformationProvider.class
)
public class JournalContentDisplayInformationProvider
	implements DisplayInformationProvider {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public String getClassPK(PortletPreferences portletPreferences) {
		return portletPreferences.getValue("articleId", StringPool.BLANK);
	}

}