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

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, service = {MBMessageUADDisplay.class, UADDisplay.class}
)
public class MBMessageUADDisplay extends BaseMBMessageUADDisplay {

	@Override
	public String[] getColumnFieldNames() {
		return new String[] {"subject", "body"};
	}

	@Override
	public String[] getDisplayFieldNames() {
		return new String[] {
			"subject", "body", "userId", "userName", "statusByUserId",
			"statusByUserName"
		};
	}

	@Override
	public String getEditURL(
			MBMessage mbMessage, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		if (mbMessage.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID) {

			return null;
		}

		if (mbMessage.isInTrash()) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			portal.getControlPanelPlid(liferayPortletRequest),
			MBPortletKeys.MESSAGE_BOARDS_ADMIN, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/edit_message");
		portletURL.setParameter(
			"redirect", portal.getCurrentURL(liferayPortletRequest));
		portletURL.setParameter(
			"messageId", String.valueOf(mbMessage.getMessageId()));

		return portletURL.toString();
	}

	@Override
	public Map<String, Object> getFieldValues(
		MBMessage mbMessage, String[] fieldNames, Locale locale) {

		Map<String, Object> fieldValues = super.getFieldValues(
			mbMessage, fieldNames, locale);

		List<String> fieldNamesList = Arrays.asList(fieldNames);

		if (fieldNamesList.contains("content")) {
			fieldValues.put("content", mbMessage.getBody());
		}

		return fieldValues;
	}

	@Override
	public String getName(MBMessage mbMessage, Locale locale) {
		return mbMessage.getSubject();
	}

	@Override
	public Class<?> getParentContainerClass() {
		return MBThread.class;
	}

	@Override
	public Serializable getParentContainerId(MBMessage mbMessage) {
		return mbMessage.getThreadId();
	}

	@Override
	public boolean isUserOwned(MBMessage mbMessage, long userId) {
		if (mbMessage.getUserId() == userId) {
			return true;
		}

		return false;
	}

	@Reference
	protected Portal portal;

}