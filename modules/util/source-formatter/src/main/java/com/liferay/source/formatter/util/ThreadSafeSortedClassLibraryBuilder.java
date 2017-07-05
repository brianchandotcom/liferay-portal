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

package com.liferay.source.formatter.util;

import com.thoughtworks.qdox.library.ClassLibraryBuilder;
import com.thoughtworks.qdox.library.SortedClassLibraryBuilder;
import com.thoughtworks.qdox.model.JavaModule;
import com.thoughtworks.qdox.model.JavaSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.net.URL;

/**
 * @author Shuyang Zhou
 * @author Hugo Huijser
 */
public class ThreadSafeSortedClassLibraryBuilder
	extends SortedClassLibraryBuilder {

	public ThreadSafeSortedClassLibraryBuilder() {
		appendDefaultClassLoaders();
	}

	@Override
	public JavaSource addSource(File file) throws IOException {
		synchronized (_contextLock) {
			return super.addSource(file);
		}
	}

	@Override
	public JavaSource addSource(InputStream inputStream) throws IOException {
		synchronized (_contextLock) {
			return super.addSource(inputStream);
		}
	}

	@Override
	public JavaSource addSource(Reader reader) {
		synchronized (_contextLock) {
			return super.addSource(reader);
		}
	}

	@Override
	public JavaSource addSource(URL url) throws IOException {
		synchronized (_contextLock) {
			return super.addSource(url);
		}
	}

	@Override
	public JavaModule addSourceFolder(File sourceFolder) {
		synchronized (_sourceFoldersLock) {
			return super.addSourceFolder(sourceFolder);
		}
	}

	@Override
	public ClassLibraryBuilder appendSource(File file) throws IOException {
		synchronized (_contextLock) {
			return super.appendSource(file);
		}
	}

	@Override
	public ClassLibraryBuilder appendSource(Reader reader) {
		synchronized (_contextLock) {
			return super.appendSource(reader);
		}
	}

	@Override
	public ClassLibraryBuilder appendSource(URL url) throws IOException {
		synchronized (_contextLock) {
			return super.appendSource(url);
		}
	}

	@Override
	public ClassLibraryBuilder appendSourceFolder(File sourceFolder) {
		synchronized (_sourceFoldersLock) {
			return super.appendSourceFolder(sourceFolder);
		}
	}

	private final Object _contextLock = new Object();
	private final Object _sourceFoldersLock = new Object();

}