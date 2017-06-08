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

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingContainer;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.scripting.BaseScriptingExecutor;
import com.liferay.portal.scripting.ruby.configuration.RubyScriptingConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;

import org.jruby.Ruby;
import org.jruby.RubyException;
import org.jruby.RubyInstanceConfig;
import org.jruby.RubyInstanceConfig.CompileMode;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.internal.LocalContextProvider;
import org.jruby.exceptions.RaiseException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Montero
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.scripting.ruby.configuration.RubyScriptingConfiguration",
	immediate = true,
	property = {"scripting.language=" + RubyExecutor.LANGUAGE},
	service = ScriptingExecutor.class
)
public class RubyExecutor extends BaseScriptingExecutor {

	public static final String LANGUAGE = "ruby";

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

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script, ClassLoader... classLoaders)
		throws ScriptingException {

		return eval(
			allowedClasses, inputObjects, outputNames, null, script,
			classLoaders);
	}

	@Override
	public String getLanguage() {
		return LANGUAGE;
	}

	@Override
	public ScriptingContainer<?> getScriptingContainer() {
		return _scriptingContainer;
	}

	@Override
	public ScriptingExecutor newInstance(boolean executeInSeparateThread) {
		RubyExecutor rubyExecutor = new RubyExecutor();

		rubyExecutor.setExecuteInSeparateThread(executeInSeparateThread);

		return rubyExecutor;
	}

	public void setExecuteInSeparateThread(boolean executeInSeparateThread) {
		_executeInSeparateThread = executeInSeparateThread;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_rubyScriptingConfiguration = Configurable.createConfigurable(
			RubyScriptingConfiguration.class, properties);

		initialize();
	}

	@Deactivate
	protected void deactivate() {
		_scriptingContainer.destroy();
	}

	protected Map<String, Object> doEval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, File scriptFile, String script,
			ClassLoader... classLoaders)
		throws ScriptingException {

		if (allowedClasses != null) {
			throw new ExecutionException(
				"Constrained execution not supported for Ruby");
		}

		org.jruby.embed.ScriptingContainer scriptingContainer =
			_scriptingContainer.getWrappedScriptingContainer();

		try {
			LocalContextProvider localContextProvider =
				scriptingContainer.getProvider();

			RubyInstanceConfig rubyInstanceConfig =
				localContextProvider.getRubyInstanceConfig();

			rubyInstanceConfig.setCurrentDirectory(_basePath);

			if (ArrayUtil.isNotEmpty(classLoaders)) {
				rubyInstanceConfig.setLoader(
					getAggregateClassLoader(classLoaders));
			}

			rubyInstanceConfig.setLoadPaths(_loadPaths);

			for (Map.Entry<String, Object> entry : inputObjects.entrySet()) {
				String inputName = entry.getKey();
				Object inputObject = entry.getValue();

				if (!inputName.startsWith(StringPool.DOLLAR)) {
					inputName = StringPool.DOLLAR + inputName;
				}

				scriptingContainer.put(inputName, inputObject);
			}

			if (scriptFile != null) {
				scriptingContainer.runScriptlet(
					new FileInputStream(scriptFile), scriptFile.toString());
			}
			else {
				_scriptingContainer.runScriptlet(script);
			}

			if (outputNames == null) {
				return null;
			}

			Map<String, Object> outputObjects = new HashMap<>();

			for (String outputName : outputNames) {
				outputObjects.put(
					outputName, scriptingContainer.get(outputName));
			}

			return outputObjects;
		}
		catch (RaiseException re) {
			RubyException rubyException = re.getException();

			throw new ScriptingException(
				rubyException.message.asJavaString() + "\n\n", re);
		}
		catch (FileNotFoundException fnfe) {
			throw new ScriptingException(fnfe);
		}
		finally {
			try {
				_globalRuntimeField.set(null, null);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, File scriptFile, String script,
			ClassLoader... classLoaders)
		throws ScriptingException {

		if (!_executeInSeparateThread) {
			return doEval(
				allowedClasses, inputObjects, outputNames, scriptFile, script,
				classLoaders);
		}

		EvalCallable evalCallable = new EvalCallable(
			allowedClasses, inputObjects, outputNames, scriptFile, script,
			classLoaders);

		FutureTask<Map<String, Object>> futureTask = new FutureTask<>(
			evalCallable);

		Thread oneTimeExecutorThread = _threadFactory.newThread(futureTask);

		oneTimeExecutorThread.start();

		try {
			oneTimeExecutorThread.join();

			return futureTask.get();
		}
		catch (Exception e) {
			futureTask.cancel(true);
			oneTimeExecutorThread.interrupt();

			throw new ScriptingException(e);
		}
	}

	protected void initialize() {
		initScriptingExecutorClassLoader();

		org.jruby.embed.ScriptingContainer scriptingContainer =
			new org.jruby.embed.ScriptingContainer(
				LocalContextScope.THREADSAFE);

		_scriptingContainer = new RubyScriptingContainer(scriptingContainer);

		LocalContextProvider localContextProvider =
			scriptingContainer.getProvider();

		RubyInstanceConfig rubyInstanceConfig =
			localContextProvider.getRubyInstanceConfig();

		String compileMode = _rubyScriptingConfiguration.compileMode();

		if (compileMode.equals(_COMPILE_MODE_FORCE)) {
			rubyInstanceConfig.setCompileMode(CompileMode.FORCE);
		}
		else if (compileMode.equals(_COMPILE_MODE_JIT)) {
			rubyInstanceConfig.setCompileMode(CompileMode.JIT);
		}

		rubyInstanceConfig.setJitThreshold(
			_rubyScriptingConfiguration.compileThreshold());
		rubyInstanceConfig.setLoader(getScriptingExecutorClassLoader());

		String[] loadPaths = StringUtil.split(
			_rubyScriptingConfiguration.loadPaths());

		_loadPaths = new ArrayList<>(Arrays.asList(loadPaths));

		rubyInstanceConfig.setLoadPaths(_loadPaths);

		scriptingContainer.setCurrentDirectory(_basePath);
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_basePath = props.get(PropsKeys.LIFERAY_LIB_PORTAL_DIR);
	}

	private static final String _COMPILE_MODE_FORCE = "force";

	private static final String _COMPILE_MODE_JIT = "jit";

	private static final Log _log = LogFactoryUtil.getLog(RubyExecutor.class);

	private static final Field _globalRuntimeField;
	private static final ThreadFactory _threadFactory = new NamedThreadFactory(
		RubyExecutor.class.getName(), Thread.NORM_PRIORITY,
		RubyExecutor.class.getClassLoader());

	static {
		try {
			_globalRuntimeField = ReflectionUtil.getDeclaredField(
				Ruby.class, "globalRuntime");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private String _basePath;
	private boolean _executeInSeparateThread = true;
	private List<String> _loadPaths;
	private volatile RubyScriptingConfiguration _rubyScriptingConfiguration;
	private ScriptingContainer<org.jruby.embed.ScriptingContainer>
		_scriptingContainer;

	private class EvalCallable implements Callable<Map<String, Object>> {

		public EvalCallable(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, File scriptFile, String script,
			ClassLoader[] classLoaders) {

			_allowedClasses = allowedClasses;
			_inputObjects = inputObjects;
			_outputNames = outputNames;
			_scriptFile = scriptFile;
			_script = script;
			_classLoaders = classLoaders;
		}

		@Override
		public Map<String, Object> call() throws Exception {
			return doEval(
				_allowedClasses, _inputObjects, _outputNames, _scriptFile,
				_script, _classLoaders);
		}

		private final Set<String> _allowedClasses;
		private final ClassLoader[] _classLoaders;
		private final Map<String, Object> _inputObjects;
		private final Set<String> _outputNames;
		private final String _script;
		private final File _scriptFile;

	}

}