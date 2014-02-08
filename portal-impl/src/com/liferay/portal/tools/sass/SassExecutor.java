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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.scripting.ruby.RubyExecutor;

import org.jruby.RubyArray;
import org.jruby.RubyException;
import org.jruby.embed.ScriptingContainer;
import org.jruby.exceptions.RaiseException;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * @author Minhchau Dang
 */
public class SassExecutor {

	public SassExecutor(String docrootDirName, String portalCommonDirName)
		throws Exception {

		_docrootDirName = docrootDirName;
		_portalCommonDirName = portalCommonDirName;

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		RubyExecutor rubyExecutor = new RubyExecutor();

		rubyExecutor.setExecuteInSeparateThread(false);

		_scriptingContainer = rubyExecutor.getScriptingContainer();

		String rubyScript = StringUtil.read(
			classLoader,
			"com/liferay/portal/servlet/filters/dynamiccss" +
				"/dependencies/main.rb");

		_scriptObject = _scriptingContainer.runScriptlet(rubyScript);
	}

	public String parse(String fileName, String content) {
		String filePath = _docrootDirName.concat(fileName);

		Object[] arguments = new Object[] {
			content, _portalCommonDirName, filePath, _getCssThemePath(filePath),
			_tempDir, false
		};

		try {
			content = _scriptingContainer.callMethod(
				_scriptObject, "process", arguments, String.class);
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

	private String _getCssThemePath(String fileName) {
		int pos = fileName.lastIndexOf("/css/");

		return fileName.substring(0, pos + 4);
	}

	private static Log _log = LogFactoryUtil.getLog(SassExecutor.class);

	private static String _tempDir = SystemProperties.get(
		SystemProperties.TMP_DIR); private String _portalCommonDirName;

	private String _docrootDirName;
	private ScriptingContainer _scriptingContainer;
	private Object _scriptObject;

}