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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.scripting.ruby.RubyExecutor;

import java.io.IOException;

import org.jruby.RubyArray;
import org.jruby.RubyException;
import org.jruby.embed.ScriptingContainer;
import org.jruby.exceptions.RaiseException;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * @author Minhchau Dang
 * @author Shuyang Zhou
 */
public class SassExecutorUtil {

	public static void init(String docrootDirName, String portalCommonDirName)
		throws IOException {

		_docrootDirName = docrootDirName;
		_portalCommonDirName = portalCommonDirName;

		RubyExecutor rubyExecutor = new RubyExecutor();

		rubyExecutor.setExecuteInSeparateThread(false);

		_scriptingContainer = rubyExecutor.getScriptingContainer();

		String rubyScript = StringUtil.read(
			SassExecutorUtil.class.getClassLoader(),
			"com/liferay/portal/servlet/filters/dynamiccss" +
				"/dependencies/main.rb");

		_scriptObject = _scriptingContainer.runScriptlet(rubyScript);
	}

	public static String parse(String fileName, String content) {
		String filePath = _docrootDirName.concat(fileName);

		String cssThemePath = filePath;

		int pos = filePath.lastIndexOf("/css/");

		if (pos >= 0) {
			cssThemePath = filePath.substring(0, pos + 4);
		}

		try {
			return _scriptingContainer.callMethod(
				_scriptObject, "process",
				new Object[] {
					content, _portalCommonDirName, filePath, cssThemePath,
					_TEP_DIR, false
				}, String.class);
		}
		catch (Exception e) {
			if (e instanceof RaiseException) {
				RaiseException raiseException = (RaiseException)e;

				RubyException rubyException = raiseException.getException();

				System.err.println(
					String.valueOf(rubyException.message.toJava(String.class)));

				IRubyObject iRubyObject = rubyException.getBacktrace();

				RubyArray rubyArray = (RubyArray)iRubyObject.toJava(
					RubyArray.class);

				for (int i = 0; i < rubyArray.size(); i++) {
					Object object = rubyArray.get(i);

					System.err.println(String.valueOf(object));
				}
			}
			else {
				e.printStackTrace();
			}
		}

		return content;
	}

	private static final String _TEP_DIR = SystemProperties.get(
		SystemProperties.TMP_DIR);

	private static String _docrootDirName;
	private static String _portalCommonDirName;
	private static ScriptingContainer _scriptingContainer;
	private static Object _scriptObject;

}