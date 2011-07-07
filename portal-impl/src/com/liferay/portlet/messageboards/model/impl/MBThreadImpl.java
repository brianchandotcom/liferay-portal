/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.model.Lock;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBThread;

/**
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 */
public class MBThreadImpl extends MBThreadBaseImpl {

	public MBThreadImpl() {
	}

	public String getAttachmentsDir() {
		return "messageboards/" + getThreadId();
	}

	public Lock getLock() {
		try {
			return LockLocalServiceUtil.getLock(
				MBThread.class.getName(), getThreadId());
		}
		catch (Exception e) {
		}

		return null;
	}

	public boolean hasLock(long userId) {
		try {
			return LockLocalServiceUtil.hasLock(
				userId, MBThread.class.getName(), getThreadId());
		}
		catch (Exception e) {
		}

		return false;
	}

	public boolean isLocked() {
		try {
			return LockLocalServiceUtil.isLocked(
				MBThread.class.getName(), getThreadId());
		}
		catch (Exception e) {
		}

		return false;
	}

}