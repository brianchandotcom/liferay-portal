/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.service.impl.util;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Wade Cao
 */
public class KeyUtil {

	public static String getKey(
		CounterLocalService counterLocalService, String key) {

		if (Validator.isNull(key)) {
			return String.valueOf(counterLocalService.increment());
		}

		return StringUtil.toUpperCase(key.trim());
	}

}