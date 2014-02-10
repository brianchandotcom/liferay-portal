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

package com.liferay.portal.tools.sass;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Minhchau Dang
 */
public class SassFileCache {

	public SassFileCache(
		ExecutorService executorService, SassExecutor sassExecutor,
		String docrootDirName, List<String> fileNames) {

		_executorService = executorService;
		_sassExecutor = sassExecutor;

		for (String fileName : fileNames) {
			submit(docrootDirName, fileName);
		}
	}

	public void processAll() throws Exception {
		while (!_futures.isEmpty()) {
			Future<SassFile> future = _futures.poll();

			System.out.println(future.get());
		}

		for (SassFile file : _sassFileCache.values()) {
			file.writeCacheFiles();
		}
	}

	public SassFile submit(String docrootDirName, String fileName) {
		SassFile sassFile = _sassFileCache.get(fileName);

		if (sassFile != null) {
			return sassFile;
		}

		sassFile = new SassFile(this, _sassExecutor, docrootDirName, fileName);

		_sassFileCache.put(fileName, sassFile);
		_futures.add(_executorService.submit(sassFile));

		return sassFile;
	}

	private static Log _log = LogFactoryUtil.getLog(SassFileCache.class);

	private ExecutorService _executorService;
	private ConcurrentLinkedQueue<Future<SassFile>> _futures =
		new ConcurrentLinkedQueue<Future<SassFile>>();
	private SassExecutor _sassExecutor;
	private Map<String, SassFile> _sassFileCache =
		new ConcurrentHashMap<String, SassFile>();

}