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

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 */
public class IdReplacer {

	public static String replaceLongIds(
			String s, String begin, ValueMapper valueMapper)
		throws Exception {

		if ((s == null) || (begin == null) ||
			(valueMapper == null) || (valueMapper.size() == 0)) {

			return s;
		}

		char[] chars = s.toCharArray();

		StringBundler sb = new StringBundler();

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);
			int y = _getEndPos(chars, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos));

				break;
			}
			else {
				sb.append(s.substring(pos, x + begin.length()));

				String oldString = s.substring(x + begin.length(), y);

				if (Validator.isNotNull(oldString)) {
					Long oldValue = new Long(GetterUtil.getLong(oldString));

					Long newValue = null;

					try {
						newValue = (Long)valueMapper.getNewValue(oldValue);
					}
					catch (StagnantRowException sre) {
						if (_log.isWarnEnabled()) {
							_log.warn(sre);
						}
					}

					if (newValue == null) {
						newValue = oldValue;
					}

					sb.append(newValue);
				}

				pos = y;
			}
		}

		return sb.toString();
	}

	public String replaceLongIds(
			String s, String begin, String end, ValueMapper valueMapper)
		throws Exception {

		if ((s == null) || (begin == null) || (end == null) ||
			(valueMapper == null) || (valueMapper.size() == 0)) {

			return s;
		}

		StringBundler sb = new StringBundler();

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);
			int y = s.indexOf(end, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos));

				break;
			}
			else {
				sb.append(s.substring(pos, x + begin.length()));

				Long oldValue = new Long(
					GetterUtil.getLong(s.substring(x + begin.length(), y)));

				Long newValue = null;

				try {
					newValue = (Long)valueMapper.getNewValue(oldValue);
				}
				catch (StagnantRowException sre) {
					if (_log.isWarnEnabled()) {
						_log.warn(sre);
					}
				}

				if (newValue == null) {
					newValue = oldValue;
				}

				sb.append(newValue);

				pos = y;
			}
		}

		return sb.toString();
	}

	private static int _getEndPos(char[] chars, int pos) {
		while (true) {
			if (pos >= chars.length) {
				break;
			}

			if (!Character.isDigit(chars[pos])) {
				break;
			}

			pos++;
		}

		return pos;
	}

	private static Log _log = LogFactoryUtil.getLog(IdReplacer.class);

}