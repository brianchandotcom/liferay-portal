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

import com.liferay.portal.kernel.scripting.ScriptingContainer;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.scripting.ruby.RubyExecutor;
import com.liferay.sass.compiler.jni.JniSassCompiler;

import java.io.File;
import java.io.IOException;

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

	public static SassFile execute(String docrootDirName, String fileName) {
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
			_executorService.submit(sassFile);
		}

		return sassFile;
	}

	public static void init(String docrootDirName, String portalCommonDirName)
		throws IOException {

		_docrootDirName = docrootDirName;
		_portalCommonDirName = portalCommonDirName;

		int threads = 1;

		try {
			_jniSassCompiler = new JniSassCompiler();
		}
		catch (Throwable t) {
			threads = 2;

			RubyExecutor rubyExecutor = new RubyExecutor();

			rubyExecutor.setExecuteInSeparateThread(false);

			_scriptingContainer = rubyExecutor.getScriptingContainer();

			_scriptingContainer.setCurrentDirectory(
				System.getProperty("user.dir"));

			String rubyScript = StringUtil.read(
				SassExecutorUtil.class.getClassLoader(),
				"com/liferay/portal/servlet/filters/dynamiccss" +
					"/dependencies/main.rb");

			_scriptObject = _scriptingContainer.runScriptlet(rubyScript);
		}

		_executorService = Executors.newFixedThreadPool(
			threads,
			new NamedThreadFactory(
				"SassExecutor", Thread.NORM_PRIORITY,
				SassExecutorUtil.class.getClassLoader()));

		_mainThread = Thread.currentThread();
	}

	public static String parse(String fileName, String content) {
		String filePath = _docrootDirName.concat(fileName);

		String cssThemePath = filePath;

		int pos = filePath.lastIndexOf("/css/");

		if (pos >= 0) {
			cssThemePath = filePath.substring(0, pos + 4);
		}

		if (_jniSassCompiler != null) {
			try {
				return _jniSassCompiler.compileString(
					content,
					_portalCommonDirName + File.pathSeparator + cssThemePath,
					"");
			}
			catch (Exception e) {
				e.printStackTrace();

				_exception = new Exception("Unable to parse " + fileName, e);

				_mainThread.interrupt();
			}
		}
		else {
			try {
				return (String)_scriptingContainer.callMethod(
					_scriptObject, "process",
					new Object[] {
						content, _portalCommonDirName, filePath, cssThemePath,
						_TMP_DIR, false
					},
					String.class);
			}
			catch (Exception e) {
				System.err.println(e.getMessage());

				e.printStackTrace();

				_exception = new Exception("Unable to parse " + fileName, e);

				_mainThread.interrupt();
			}
		}

		return content;
	}

	public static void persist() throws Exception {
		_executorService.shutdown();

		try {
			if (!_executorService.awaitTermination(
					_WAIT_MINTUES, TimeUnit.MINUTES)) {

				System.err.println(
					"Abort processing Sass files after waiting " +
						_WAIT_MINTUES + " minutes");
			}
		}
		catch (InterruptedException ie) {
			if (_exception == null) {
				throw ie;
			}

			throw _exception;
		}

		for (SassFile sassFile : _sassFileCache.values()) {
			sassFile.writeCacheFiles();

			System.out.println(sassFile);
		}
	}

	private static final String _TMP_DIR = SystemProperties.get(
		SystemProperties.TMP_DIR);

	private static final int _WAIT_MINTUES = 30;

	private static String _docrootDirName;
	private static Exception _exception;
	private static ExecutorService _executorService;
	private static JniSassCompiler _jniSassCompiler;
	private static Thread _mainThread;
	private static String _portalCommonDirName;
	private static final ConcurrentMap<String, SassFile> _sassFileCache =
		new ConcurrentHashMap<>();
	private static ScriptingContainer _scriptingContainer;
	private static Object _scriptObject;

}