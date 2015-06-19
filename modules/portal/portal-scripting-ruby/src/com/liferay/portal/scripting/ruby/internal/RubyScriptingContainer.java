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

package com.liferay.portal.scripting.ruby.internal;

import com.liferay.portal.kernel.scripting.ScriptingContainer;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.StringPool;

import org.jruby.RubyArray;
import org.jruby.RubyException;
import org.jruby.exceptions.RaiseException;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * @author Michael C. Han
 */
public class RubyScriptingContainer
	implements ScriptingContainer<org.jruby.embed.ScriptingContainer> {

	public RubyScriptingContainer(
		org.jruby.embed.ScriptingContainer scriptingContainer) {

		_scriptingContainer = scriptingContainer;
	}

	@Override
	public <T> T callMethod(
			Object scriptObject, String methodName, Object[] arguments,
			Class<T> returnClass)
		throws ScriptingException {

		try {
			return _scriptingContainer.callMethod(
				scriptObject, methodName, arguments, returnClass);
		}
		catch (RaiseException re) {
			RubyException rubyException = re.getException();

			IRubyObject iRubyObject = rubyException.getBacktrace();

			RubyArray rubyArray = (RubyArray)iRubyObject.toJava(
				RubyArray.class);

			StringBuilder sb = new StringBuilder(2 * rubyArray.size() + 2);

			sb.append(
				String.valueOf(rubyException.message.toJava(String.class)));
			sb.append(StringPool.NEW_LINE);

			for (int i = 0; i < rubyArray.size(); i++) {
				Object object = rubyArray.get(i);

				sb.append(String.valueOf(object));
				sb.append(StringPool.NEW_LINE);
			}

			throw new ScriptingException(sb.toString(), re);
		}
	}

	@Override
	public void destroy() {
		_scriptingContainer.terminate();
	}

	@Override
	public org.jruby.embed.ScriptingContainer getWrappedScriptingContainer() {
		return _scriptingContainer;
	}

	@Override
	public Object runScriptlet(String scriptlet) {
		return _scriptingContainer.runScriptlet(scriptlet);
	}

	@Override
	public void setCurrentDirName(String currentDirName) {
		_scriptingContainer.setCurrentDirectory(currentDirName);
	}

	private final org.jruby.embed.ScriptingContainer _scriptingContainer;

}