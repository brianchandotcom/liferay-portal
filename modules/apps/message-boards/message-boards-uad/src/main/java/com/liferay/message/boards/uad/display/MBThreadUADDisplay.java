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
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = UADDisplay.class)
public class MBThreadUADDisplay extends BaseMBThreadUADDisplay {

	@Override
	public Serializable getParentContainerId(MBThread mbThread) {
		return mbThread.getCategoryId();
	}

	@Override
	public MBThread getTopLevelContainer(
		Class parentContainerType, Serializable parentContainerId,
		Object childObject) {

		if (!parentContainerType.equals(MBCategory.class) ||
			(childObject instanceof MBCategory)) {

			return null;
		}

		try {
			MBThread childThread;

			if (childObject instanceof MBMessage) {
				MBMessage mbMessage = (MBMessage)childObject;

				childThread = mbMessage.getThread();
			}
			else {
				childThread = (MBThread)childObject;
			}

			if (childThread.getCategoryId() == (long)parentContainerId) {
				return childThread;
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
		MBThreadUADDisplay.class);

}