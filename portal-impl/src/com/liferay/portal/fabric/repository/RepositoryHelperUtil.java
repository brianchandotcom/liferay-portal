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

package com.liferay.portal.fabric.repository;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.nio.file.Path;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class RepositoryHelperUtil {

	public static Path getRepositoryFilePath(
		Path repositoryPath, Path remoteFilePath) {

		Path fileNamePath = remoteFilePath.getFileName();

		String name = fileNamePath.toString();

		int index = name.lastIndexOf(CharPool.PERIOD);

		if (index == -1) {
			StringBundler sb = new StringBundler(5);

			sb.append(name);
			sb.append(StringPool.DASH);
			sb.append(System.currentTimeMillis());
			sb.append(StringPool.DASH);
			sb.append(idGenerator.getAndIncrement());

			return repositoryPath.resolve(sb.toString());
		}

		StringBundler sb = new StringBundler(6);

		sb.append(name.substring(0, index));
		sb.append(StringPool.DASH);
		sb.append(System.currentTimeMillis());
		sb.append(StringPool.DASH);
		sb.append(idGenerator.getAndIncrement());
		sb.append(name.substring(index));

		return repositoryPath.resolve(sb.toString());
	}

	protected static final AtomicLong idGenerator = new AtomicLong();

}