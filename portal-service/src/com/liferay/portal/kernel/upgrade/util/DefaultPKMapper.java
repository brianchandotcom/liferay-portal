/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultPKMapper extends ValueMapperWrapper {

	public DefaultPKMapper(ValueMapper valueMapper) {
		super(valueMapper);
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		String oldValueString = GetterUtil.getString(String.valueOf(oldValue));

		if (oldValueString.equals("-1") || oldValueString.equals("0") ||
			oldValueString.equals("")) {

			return new Long(0);
		}

		try {
			ValueMapper valueMapper = getValueMapper();

			if (oldValue instanceof String) {
				oldValue = StringUtil.toLowerCase(oldValueString);
			}

			return valueMapper.getNewValue(oldValue);
		}
		catch (StagnantRowException sre) {
			return new Long(0);
		}
	}

}