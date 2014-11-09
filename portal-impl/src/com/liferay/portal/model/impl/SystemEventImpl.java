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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class SystemEventImpl extends SystemEventBaseImpl {

	@Override
	public String getReferrerClassName() {
		long referrerClassNameId = getReferrerClassNameId();

		if (referrerClassNameId > 0) {
			return PortalUtil.getClassName(referrerClassNameId);
		}

		return StringPool.BLANK;
	}

	@Override
	public void setReferrerClassName(String referrerClassName) {
		long referrerClassNameId = 0;

		if (Validator.isNotNull(referrerClassName)) {
			referrerClassNameId = PortalUtil.getClassNameId(referrerClassName);
		}

		setReferrerClassNameId(referrerClassNameId);
	}

}