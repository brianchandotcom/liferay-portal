/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

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