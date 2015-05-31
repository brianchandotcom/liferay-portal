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

package com.liferay.portal.tools.sass;

import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.util.PropsValues;
import com.liferay.sass.compiler.SassCompiler;
import com.liferay.sass.compiler.SassCompilerException;
import com.liferay.sass.compiler.jni.internal.JniSassCompiler;
import com.liferay.sass.compiler.ruby.internal.RubySassCompiler;

import java.io.File;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Minhchau Dang
 * @author Shuyang Zhou
 * @author David Truong
 */
public class SassExecutorUtil {

	public static SassFile execute(String docrootDirName, String fileName)
		throws Exception {

		SassFile sassFile = _sassFileCache.get(fileName);

		if (sassFile != null) {
			return sassFile;
		}

		sassFile = new SassFile(docrootDirName, fileName);

		SassFile previousSassFile = _sassFileCache.putIfAbsent(
			fileName, sassFile);

		if (previousSassFile != null) {
			sassFile = previousSassFile;
		}
		else {
			sassFile.build();
		}

		return sassFile;
	}

	public static void init(String docrootDirName, String portalCommonDirName)
		throws Exception {

		_docrootDirName = docrootDirName;
		_portalCommonDirName = portalCommonDirName;

		try {
			_sassCompiler = new JniSassCompiler();
		}
		catch (Throwable t) {
			_sassCompiler = new RubySassCompiler(
				PropsValues.SCRIPTING_JRUBY_COMPILE_MODE,
				PropsValues.SCRIPTING_JRUBY_COMPILE_THRESHOLD, _TMP_DIR);
		}
	}

	public static String parse(String fileName, String content)
		throws SassCompilerException {

		String filePath = _docrootDirName.concat(fileName);

		String cssThemePath = filePath;

		int pos = filePath.lastIndexOf("/css/");

		if (pos >= 0) {
			cssThemePath = filePath.substring(0, pos + 4);
		}

		return _sassCompiler.compileString(
			content,
			_portalCommonDirName + File.pathSeparator + cssThemePath, "");
	}

	public static void persist() throws Exception {
		for (SassFile sassFile : _sassFileCache.values()) {
			sassFile.writeCacheFiles();

			System.out.println(sassFile);
		}
	}

	private static final String _TMP_DIR = SystemProperties.get(
		SystemProperties.TMP_DIR);

	private static String _docrootDirName;
	private static String _portalCommonDirName;
	private static SassCompiler _sassCompiler;
	private static final ConcurrentMap<String, SassFile> _sassFileCache =
		new ConcurrentHashMap<>();

}