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

package com.liferay.journal.content.web.internal.portlet.configuration.icon;

import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.content.web.internal.display.context.JournalContentDisplayContext;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
		"path=-"
	},
	service = PortletConfigurationIcon.class
)
public class EditTemplatePortletConfigurationIcon
	extends BaseJournalArticlePortletConfigurationIcon {

	@Override
	public String getJspPath() {
		return "/configuration/icon/edit_template.jsp";
	}

	@Override
	public double getWeight() {
		return 99.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		JournalContentDisplayContext journalContentDisplayContext =
			getJournalContentDisplayContext(portletRequest, null);

		if (journalContentDisplayContext.isShowEditTemplateIcon()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.content.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}