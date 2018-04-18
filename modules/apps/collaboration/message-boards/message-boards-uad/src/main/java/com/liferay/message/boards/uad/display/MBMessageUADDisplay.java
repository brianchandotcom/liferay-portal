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
import com.liferay.message.boards.uad.constants.MBUADConstants;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import com.liferay.user.associated.data.display.UADDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + MBUADConstants.CLASS_NAME_MB_MESSAGE}, service = UADDisplay.class)
public class MBMessageUADDisplay implements UADDisplay<MBMessage> {
	public String getApplicationName() {
		return MBUADConstants.APPLICATION_NAME;
	}

	public String[] getDisplayFieldNames() {
		return _mbMessageUADDisplayHelper.getDisplayFieldNames();
	}

	@Override
	public String getEditURL(MBMessage mbMessage,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse)
		throws Exception {
		return _mbMessageUADDisplayHelper.getMBMessageEditURL(mbMessage,
			liferayPortletRequest, liferayPortletResponse);
	}

	public String getKey() {
		return MBUADConstants.CLASS_NAME_MB_MESSAGE;
	}

	@Override
	public Map<String, Object> getNonanonymizableFieldValues(
		MBMessage mbMessage) {
		return _mbMessageUADDisplayHelper.getUADEntityNonanonymizableFieldValues(mbMessage);
	}

	@Override
	public String getTypeDescription() {
		return "";
	}

	@Override
	public String getTypeName() {
		return "MBMessage";
	}

	@Reference
	private MBMessageUADDisplayHelper _mbMessageUADDisplayHelper;
}