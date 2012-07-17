/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.model.GroupConstants;

/**
 * <a href="VirtualLayoutThreadLocal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Dale Shan
 */
public class VirtualLayoutThreadLocal {

	public static long getTargetGroupId() {
		return _targetGroupId.get();
	}

	public static void setTargetGroupId(long targetGroupId) {
		_targetGroupId.set(targetGroupId);
	}

	private static ThreadLocal<Long> _targetGroupId =
		new AutoResetThreadLocal<Long>(
			VirtualLayoutThreadLocal.class + "._targetGroupId",
			GroupConstants.DEFAULT_LIVE_GROUP_ID);

}