/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class PropertiesUtilTest {

	@Test
	public void testLoad1() throws Exception {
		Properties properties = PropertiesUtil.load(_PROPERTIES_STRING);

		for (String[] property : _PROPERTIES_ARRAY) {
			Assert.assertEquals(property[1], properties.get(property[0]));
		}
	}

	@Test
	public void testLoad2() throws Exception {
		Properties properties = PropertiesUtil.load(
			new UnsyncStringReader(_PROPERTIES_STRING));

		for (String[] property : _PROPERTIES_ARRAY) {
			Assert.assertEquals(property[1], properties.get(property[0]));
		}
	}

	private static final String[][] _PROPERTIES_ARRAY = {
		{"testKey", "testValue"}, {"测试键", "测试值"}
	};

	private static final String _PROPERTIES_STRING;

	static {
		StringBundler sb = new StringBundler(_PROPERTIES_ARRAY.length * 4);

		for (String[] property : _PROPERTIES_ARRAY) {
			sb.append(property[0]);
			sb.append(StringPool.EQUAL);
			sb.append(property[1]);
			sb.append(StringPool.NEW_LINE);
		}

		_PROPERTIES_STRING = sb.toString();
	}

}