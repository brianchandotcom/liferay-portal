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

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedWriter;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.FileWriter;

import java.util.Iterator;

/**
 * @author Brian Wing Shun Chan
 */
public class ValueMapperUtil {

	public static void persist(
			ValueMapper valueMapper, String tmpDir, String fileName)
		throws Exception {

		FileUtil.mkdirs(tmpDir);

		try (UnsyncBufferedWriter unsyncBufferedWriter =
				new UnsyncBufferedWriter(
					new FileWriter(
						StringBundler.concat(tmpDir, "/", fileName, ".txt")))) {

			Iterator<Object> itr = valueMapper.iterator();

			while (itr.hasNext()) {
				Object oldValue = itr.next();

				Object newValue = valueMapper.getNewValue(oldValue);

				unsyncBufferedWriter.write(
					oldValue + StringPool.EQUAL + newValue);

				if (itr.hasNext()) {
					unsyncBufferedWriter.write(StringPool.NEW_LINE);
				}
			}
		}
	}

}