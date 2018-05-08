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

package com.liferay.server.manager.internal.executor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class ExecutorPathResolver {

	public static final String ROOT_EXECUTOR_PATH = StringPool.SLASH;

	public ExecutorPathResolver(Set<String> availableExecutorPaths) {
		_availableExecutorPaths = availableExecutorPaths;
	}

	public String getExecutorPath(String path) {
		String executorPath = path;

		if (Validator.isNull(executorPath)) {
			executorPath = StringPool.SLASH;
		}

		while (!executorPath.isEmpty() &&
			   !_availableExecutorPaths.contains(executorPath)) {

			int index = executorPath.lastIndexOf(StringPool.SLASH);

			executorPath = executorPath.substring(0, index);
		}

		if (!executorPath.isEmpty()) {
			return executorPath;
		}

		return ROOT_EXECUTOR_PATH;
	}

	public List<String> getNextExecutorsPaths(String path) {
		List<String> nextExecutorsPaths = new ArrayList<>();

		String executorPath = getExecutorPath(path);

		for (String availablePath : _availableExecutorPaths) {
			if (isNextExecutorPath(availablePath, executorPath)) {
				nextExecutorsPaths.add(availablePath);
			}
		}

		Collections.sort(nextExecutorsPaths);

		return nextExecutorsPaths;
	}

	protected boolean isNextExecutorPath(
		String path, String matchingExecutorPath) {

		String currentExecutorPath = matchingExecutorPath;

		if (!currentExecutorPath.equals("/")) {
			currentExecutorPath = currentExecutorPath + "/";
		}

		String nextExecutorPath = path.replaceFirst(
			currentExecutorPath, StringPool.BLANK);

		if (!nextExecutorPath.isEmpty() &&
			!nextExecutorPath.contains(StringPool.SLASH)) {

			return true;
		}

		return false;
	}

	private final Set<String> _availableExecutorPaths;

}