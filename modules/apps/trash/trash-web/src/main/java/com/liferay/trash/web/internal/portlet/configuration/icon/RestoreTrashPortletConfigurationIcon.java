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

package com.liferay.trash.web.internal.portlet.configuration.icon;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.trash.constants.TrashPortletKeys;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.web.internal.display.context.TrashDisplayContext;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TrashPortletKeys.TRASH, "path=/view_content.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class RestoreTrashPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	@Override
	public String getIconCssClass() {
		return "restore";
	}

	@Override
	public String getJspPath() {
		return "/configuration/icon/restore_trash.jsp";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(getLocale(portletRequest), "restore");
	}

	@Override
	public double getWeight() {
		return 100.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		TrashDisplayContext trashDisplayContext = new TrashDisplayContext(
			_portal.getHttpServletRequest(portletRequest), null, null);

		TrashHandler trashHandler = trashDisplayContext.getTrashHandler();

		if ((trashHandler == null) || trashHandler.isContainerModel()) {
			return false;
		}

		TrashEntry trashEntry = trashDisplayContext.getTrashEntry();

		if (trashEntry != null) {
			try {
				if (!trashHandler.isMovable(trashEntry.getClassPK()) ||
					!trashHandler.isRestorable(trashEntry.getClassPK()) ||
					!trashHandler.isInTrashContainer(trashEntry.getClassPK())) {

					return false;
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				return false;
			}
		}

		return true;
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RestoreTrashPortletConfigurationIcon.class);

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.trash.web)")
	private ServletContext _servletContext;

}