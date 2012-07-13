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

package com.liferay.portal.util;

import com.liferay.portal.kernel.concurrent.ConcurrentLRUCache;

import java.io.File;

/**
 * @author Igor Spasic
 */
public class LimitedFilesCache<T> {

	public LimitedFilesCache(int maxSize) {
		_cache = new FileRemovingLFUCache<T>(maxSize);
	}

	public File get(T key) {
		return _cache.get(key);
	}

	public void put(T key) {
		_cache.put(key, null);
	}

	public void put(T key, File file) {
		_cache.put(key, file);
	}

	public static class FileRemovingLFUCache<K> extends
		ConcurrentLRUCache<K, File> {

		public FileRemovingLFUCache(int maxSize) {
			super(maxSize);
		}

		@Override
		protected void onRemove(K key, File cachedFile) {
			if (cachedFile != null) {
				cachedFile.delete();
			}
			else {
				new File(key.toString()).delete();
			}
		}
	}

	private FileRemovingLFUCache<T> _cache;

}