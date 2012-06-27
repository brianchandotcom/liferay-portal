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

package com.liferay.portal.scripting.ruby;

import com.liferay.portal.kernel.scripting.BaseScriptingExecutor;
import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jruby.RubyInstanceConfig.CompileMode;
import org.jruby.RubyInstanceConfig;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.internal.LocalContextProvider;
import org.jruby.exceptions.RaiseException;

/**
 * @author Alberto Montero
 * @author Raymond Augé
 */
public class RubyExecutor extends BaseScriptingExecutor {

	public static final String LANGUAGE = "ruby";

	public RubyExecutor() {
		_scriptingContainer = new ScriptingContainer(
			LocalContextScope.THREADSAFE);

		LocalContextProvider localContextProvider =
			_scriptingContainer.getProvider();

		RubyInstanceConfig rubyInstanceConfig =
			localContextProvider.getRubyInstanceConfig();

		rubyInstanceConfig.setLoader(
			PACLClassLoaderUtil.getPortalClassLoader());

		if (PropsValues.SCRIPTING_JRUBY_COMPILE_MODE.equals(
				_COMPILE_MODE_FORCE)) {

			rubyInstanceConfig.setCompileMode(CompileMode.FORCE);
		}
		else if (PropsValues.SCRIPTING_JRUBY_COMPILE_MODE.equals(
					_COMPILE_MODE_JIT)) {

			rubyInstanceConfig.setCompileMode(CompileMode.JIT);
		}

		rubyInstanceConfig.setJitThreshold(
			PropsValues.SCRIPTING_JRUBY_COMPILE_THRESHOLD);

		_basePath = PortalUtil.getPortalLibDir();

		_loadPaths = new ArrayList<String>(
			PropsValues.SCRIPTING_JRUBY_LOAD_PATHS.length);

		for (String gemLibPath : PropsValues.SCRIPTING_JRUBY_LOAD_PATHS) {
			_loadPaths.add(gemLibPath);
		}

		rubyInstanceConfig.setLoadPaths(_loadPaths);

		_scriptingContainer.setCurrentDirectory(_basePath);
	}

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, File scriptFile,
			ClassLoader... classLoaders)
		throws ScriptingException {

		return eval(
			allowedClasses, inputObjects, outputNames, scriptFile, null,
			classLoaders);
	}

	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script, ClassLoader... classLoaders)
		throws ScriptingException {

		return eval(
			allowedClasses, inputObjects, outputNames, null, script,
			classLoaders);
	}

	public String getLanguage() {
		return LANGUAGE;
	}

	protected Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, File scriptFile, String script,
			ClassLoader... classLoaders)
		throws ScriptingException {

		if (allowedClasses != null) {
			throw new ExecutionException(
				"Constrained execution not supported for Ruby");
		}

		try {
			LocalContextProvider localContextProvider =
				_scriptingContainer.getProvider();

			RubyInstanceConfig rubyInstanceConfig =
				localContextProvider.getRubyInstanceConfig();

			if ((classLoaders != null) && (classLoaders.length > 0)) {
				ClassLoader aggregateClassLoader =
					AggregateClassLoader.getAggregateClassLoader(
						PACLClassLoaderUtil.getPortalClassLoader(),
						classLoaders);

				rubyInstanceConfig.setLoader(aggregateClassLoader);
			}

			rubyInstanceConfig.setCurrentDirectory(_basePath);
			rubyInstanceConfig.setLoadPaths(_loadPaths);

			for (Map.Entry<String, Object> entry : inputObjects.entrySet()) {
				String inputName = entry.getKey();
				Object inputObject = entry.getValue();

				if (!inputName.startsWith(StringPool.DOLLAR)) {
					inputName = StringPool.DOLLAR + inputName;
				}

				_scriptingContainer.put(inputName, inputObject);
			}

			if (scriptFile != null) {
				_scriptingContainer.runScriptlet(
					new FileInputStream(scriptFile), scriptFile.toString());
			}
			else {
				_scriptingContainer.runScriptlet(script);
			}

			if (outputNames == null) {
				return null;
			}

			Map<String, Object> outputObjects = new HashMap<String, Object>();

			for (String outputName : outputNames) {
				outputObjects.put(
					outputName, _scriptingContainer.get(outputName));
			}

			return outputObjects;
		}
		catch (RaiseException re) {
			throw new ScriptingException(
				re.getException().message.asJavaString() + "\n\n", re);
		}
		catch (FileNotFoundException fnfe) {
			throw new ScriptingException(fnfe);
		}
	}

	private static final String _COMPILE_MODE_FORCE = "force";

	private static final String _COMPILE_MODE_JIT = "jit";

	private String _basePath;
	private List<String> _loadPaths;
	private ScriptingContainer _scriptingContainer;

}