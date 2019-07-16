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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.web.internal.constants.TrashPortletKeys;
import com.liferay.trash.web.internal.display.context.TrashDisplayContext;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

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
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "restore");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		try {
			TrashDisplayContext trashDisplayContext = new TrashDisplayContext(
				_portal.getHttpServletRequest(portletRequest),
				_portal.getLiferayPortletRequest(portletRequest),
				_portal.getLiferayPortletResponse(portletResponse));

			TrashHandler trashHandler = trashDisplayContext.getTrashHandler();

			long classPK = trashDisplayContext.getClassPK();

			PortletURL moveURL = _portal.getControlPanelPortletURL(
				portletRequest, TrashPortletKeys.TRASH,
				PortletRequest.RENDER_PHASE);

			moveURL.setParameter("mvcPath", "/view_container_model.jsp");
			moveURL.setParameter(
				"redirect", trashDisplayContext.getViewContentRedirectURL());
			moveURL.setParameter(
				"classNameId",
				String.valueOf(trashDisplayContext.getClassNameId()));
			moveURL.setParameter("classPK", String.valueOf(classPK));

			moveURL.setParameter(
				"containerModelClassNameId",
				String.valueOf(
					_portal.getClassNameId(
						trashHandler.getContainerModelClassName(classPK))));

			moveURL.setWindowState(LiferayWindowState.POP_UP);

			StringBundler sb = new StringBundler(4);

			sb.append(portletResponse.getNamespace());
			sb.append("restoreDialog('");
			sb.append(moveURL);
			sb.append("')");

			return sb.toString();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
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

		if (trashHandler == null) {
			return false;
		}

		if (trashHandler.isContainerModel()) {
			return false;
		}

		TrashEntry trashEntry = trashDisplayContext.getTrashEntry();

		if (trashEntry != null) {
			try {
				if (!trashHandler.isMovable(trashEntry.getClassPK())) {
					return false;
				}

				if (!trashHandler.isRestorable(trashEntry.getClassPK())) {
					return false;
				}

				if (!trashHandler.isInTrashContainer(trashEntry.getClassPK())) {
					return false;
				}
			}
			catch (Exception e) {
				return false;
			}
		}

		return true;
	}

	@Reference
	private Portal _portal;

}