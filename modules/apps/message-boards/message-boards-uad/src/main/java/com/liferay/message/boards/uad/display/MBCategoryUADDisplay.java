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
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;

import java.io.Serializable;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, service = {UADDisplay.class, MBCategoryUADDisplay.class}
)
public class MBCategoryUADDisplay extends BaseMBCategoryUADDisplay {

	@Override
	public String getEditURL(
			MBCategory mbCategory, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			portal.getControlPanelPlid(liferayPortletRequest),
			MBPortletKeys.MESSAGE_BOARDS, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/edit_category");
		portletURL.setParameter(
			"redirect", portal.getCurrentURL(liferayPortletRequest));
		portletURL.setParameter(
			"mbCategoryId", String.valueOf(mbCategory.getCategoryId()));

		return portletURL.toString();
	}

	@Override
	public Serializable getParentContainerId(MBCategory mbCategory) {
		return mbCategory.getParentCategoryId();
	}

	@Override
	public MBCategory getTopLevelContainer(
		Class parentContainerType, Serializable parentContainerId,
		Object childObject) {

		if (!parentContainerType.equals(MBCategory.class)) {
			return null;
		}

		try {
			MBCategory childCategory;

			if (childObject instanceof MBMessage) {
				MBMessage mbMessage = (MBMessage)childObject;

				childCategory = mbMessage.getCategory();
			}
			else if (childObject instanceof MBThread) {
				MBThread mbThread = (MBThread)childObject;

				childCategory = mbThread.getCategory();
			}
			else {
				childCategory = (MBCategory)childObject;
			}

			List<Long> ancestorCategoryIds =
				childCategory.getAncestorCategoryIds();

			long parentCategoryId = (long)parentContainerId;

			if ((childCategory.getCategoryId() == parentCategoryId) ||
				((parentCategoryId !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
				 !ancestorCategoryIds.contains(parentCategoryId))) {

				return null;
			}

			if (childCategory.getParentCategoryId() == parentCategoryId) {
				return childCategory;
			}

			if (parentCategoryId ==
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				return get(
					ancestorCategoryIds.get(ancestorCategoryIds.size() - 1));
			}

			if (ancestorCategoryIds.contains(parentCategoryId)) {
				return get(
					ancestorCategoryIds.get(
						ancestorCategoryIds.indexOf(parentCategoryId) - 1));
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return null;
	}

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		MBCategoryUADDisplay.class);

}