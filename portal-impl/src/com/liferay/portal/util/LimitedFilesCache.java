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

package com.liferay.portal.util;

import com.liferay.portal.kernel.concurrent.ConcurrentLFUCache;

import java.io.File;

/**
 * @author Igor Spasic
 */
public class LimitedFilesCache<T> {

	public LimitedFilesCache(int maxSize) {
		_fileRemovingLFUCache = new FileRemovingLFUCache<T>(maxSize);
	}

	public File get(T key) {
		return _fileRemovingLFUCache.get(key);
	}

	public void put(T key) {
		_fileRemovingLFUCache.put(key, null);
	}

	public void put(T key, File file) {
		_fileRemovingLFUCache.put(key, file);
	}

	private FileRemovingLFUCache<T> _fileRemovingLFUCache;

	private class FileRemovingLFUCache<K> extends ConcurrentLFUCache<K, File> {

		public FileRemovingLFUCache(int maxSize) {
			super(maxSize);
		}

		@Override
		protected void onRemove(K key, File cachedFile) {
			if (cachedFile != null) {
				cachedFile.delete();
			}
			else {
				File file = new File(key.toString());

				file.delete();
			}
		}

	}

}