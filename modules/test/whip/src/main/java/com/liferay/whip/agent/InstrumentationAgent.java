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

package com.liferay.whip.agent;

import com.liferay.whip.coveragedata.ClassData;
import com.liferay.whip.coveragedata.LineData;
import com.liferay.whip.coveragedata.ProjectData;
import com.liferay.whip.coveragedata.ProjectDataUtil;
import com.liferay.whip.instrument.WhipClassFileTransformer;

import java.io.File;
import java.io.IOException;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class InstrumentationAgent {

	public static synchronized void assertCoverage(
		boolean includeInnerClasses, Class<?>... classes) {

		if (!_dynamicallyInstrumented) {
			return;
		}

		_instrumentation.removeTransformer(_whipClassFileTransformer);

		_whipClassFileTransformer = null;

		try {
			ProjectData projectData = ProjectDataUtil.captureProjectData(
				false, false);

			List<AssertionError> assertionErrors = new ArrayList<>();

			for (Class<?> clazz : classes) {
				if (clazz.isSynthetic()) {
					continue;
				}

				ClassData classData = projectData.getClassData(clazz.getName());

				_assertClassDataCoverage(assertionErrors, clazz, classData);

				if (includeInnerClasses) {
					Class<?>[] declaredClasses = clazz.getDeclaredClasses();

					declaredClass:
					for (Class<?> declaredClass : declaredClasses) {
						if (declaredClass.isSynthetic()) {
							continue;
						}

						for (Class<?> clazz2 : classes) {
							if (clazz2.equals(declaredClass)) {
								continue declaredClass;
							}
						}

						classData = projectData.getClassData(
							declaredClass.getName());

						_assertClassDataCoverage(
							assertionErrors, declaredClass, classData);
					}
				}
			}

			if (!assertionErrors.isEmpty()) {
				AssertionError assertionError = assertionErrors.get(0);

				for (int i = 1; i < assertionErrors.size(); i++) {
					assertionError.addSuppressed(assertionErrors.get(i));
				}

				throw assertionError;
			}
		}
		finally {
			_dynamicallyInstrumented = false;

			if (_originalClassDefinitions != null) {
				try {
					List<ClassDefinition> classDefinitions = new ArrayList<>(
						_originalClassDefinitions.size());

					for (OriginalClassDefinition originalClassDefinition :
							_originalClassDefinitions) {

						ClassDefinition classDefinition =
							originalClassDefinition.toClassDefinition();

						if (classDefinition != null) {
							classDefinitions.add(classDefinition);
						}
					}

					_originalClassDefinitions = null;

					_instrumentation.redefineClasses(
						classDefinitions.toArray(
							new ClassDefinition[classDefinitions.size()]));
				}
				catch (Exception e) {
					throw new RuntimeException(
						"Unable to uninstrument classes", e);
				}
			}
		}
	}

	public static synchronized void dynamicallyInstrument(
			String[] includes, String[] excludes)
		throws UnmodifiableClassException {

		if ((_instrumentation == null) || _dynamicallyInstrumented) {
			return;
		}

		if (includes == null) {
			includes = _includes;
		}

		if (excludes == null) {
			excludes = _excludes;
		}

		String agentLine = System.getProperty("whip.agent");

		int index = agentLine.indexOf('=');

		if (index != -1) {
			StringBuilder sb = new StringBuilder();

			sb.append(agentLine, 0, index + 1);

			for (String include : includes) {
				sb.append(include);
				sb.append(',');
			}

			if (includes.length > 0) {
				sb.setLength(sb.length() - 1);
			}

			sb.append(';');

			for (String exclude : excludes) {
				sb.append(exclude);
				sb.append(',');
			}

			if (excludes.length > 0) {
				sb.setLength(sb.length() - 1);
			}

			System.setProperty("whip.agent", sb.toString());
		}

		if (_whipClassFileTransformer == null) {
			_whipClassFileTransformer = new WhipClassFileTransformer(
				includes, excludes);
		}

		_instrumentation.addTransformer(_whipClassFileTransformer, true);

		Class<?>[] allLoadedClasses = _instrumentation.getAllLoadedClasses();

		List<Class<?>> modifiableClasses = new ArrayList<>();

		for (Class<?> loadedClass : allLoadedClasses) {
			if (_instrumentation.isModifiableClass(loadedClass)) {
				String className = loadedClass.getName();

				className = className.replace('.', '/');

				if (_whipClassFileTransformer.matches(className)) {
					modifiableClasses.add(loadedClass);
				}
			}
		}

		_instrumentation.retransformClasses(
			modifiableClasses.toArray(new Class<?>[modifiableClasses.size()]));

		_dynamicallyInstrumented = true;
		_originalClassDefinitions = null;
	}

	public static File getDataFile() {
		return _dataFile;
	}

	public static File getLockFile() {
		return _lockFile;
	}

	public static synchronized void premain(
		String agentArguments, Instrumentation instrumentation) {

		String[] arguments = agentArguments.split(";");

		String[] includes = arguments[0].split(",");
		String[] excludes = arguments[1].split(",");

		if (Boolean.getBoolean("whip.static.instrument")) {
			final WhipClassFileTransformer whipClassFileTransformer =
				new WhipClassFileTransformer(includes, excludes);

			instrumentation.addTransformer(whipClassFileTransformer);

			Runtime runtime = Runtime.getRuntime();

			runtime.addShutdownHook(
				new Thread() {

					@Override
					public void run() {
						ProjectDataUtil.captureProjectData(
							true,
							Boolean.getBoolean(
								"whip.static.instrument.use.data.file"));
					}

				});
		}
		else if (instrumentation.isRedefineClassesSupported() &&
				 instrumentation.isRetransformClassesSupported()) {

			_instrumentation = instrumentation;
			_includes = includes;
			_excludes = excludes;

			// Forcibly clear the data file to make sure that the coverage
			// assert is based on the current test

			_dataFile.delete();
		}
		else {
			StringBuilder sb = new StringBuilder();

			sb.append("Current JVM is not capable for dynamic ");
			sb.append("instrumententation. Instrumentation ");

			if (instrumentation.isRetransformClassesSupported()) {
				sb.append("supports ");
			}
			else {
				sb.append("does not support ");
			}

			sb.append("restranforming classes. Instrumentation ");

			if (instrumentation.isRedefineClassesSupported()) {
				sb.append("supports ");
			}
			else {
				sb.append("does not support ");
			}

			sb.append("redefining classes. Dynamic instrumententation is ");
			sb.append("disabled.");

			System.out.println(sb.toString());
		}
	}

	public static synchronized void recordInstrumentation(
		ClassLoader classLoader, String name, byte[] bytes) {

		if (!_dynamicallyInstrumented) {
			return;
		}

		if (_originalClassDefinitions == null) {
			_originalClassDefinitions = new ArrayList<>();
		}

		OriginalClassDefinition originalClassDefinition =
			new OriginalClassDefinition(classLoader, name, bytes);

		_originalClassDefinitions.add(originalClassDefinition);
	}

	private static void _assertClassDataCoverage(
		List<AssertionError> assertionErrors, Class<?> clazz,
		ClassData classData) {

		if ((classData.getBranchCoverageRate() != 1.0) ||
			(classData.getLineCoverageRate() != 1.0)) {

			StringBuilder sb = new StringBuilder();

			sb.append("%n[Whip] %s is not fully covered.%n[Whip]Branch ");
			sb.append("coverage rate : %.2f, line coverage rate : ");
			sb.append("%.2f.%n[Whip]Please rerun test with ");
			sb.append("-Djunit.code.coverage=true to see coverage report.%n");

			System.out.printf(
				sb.toString(), classData.getName(),
				classData.getBranchCoverageRate(),
				classData.getLineCoverageRate());

			for (LineData lineData : classData.getLines()) {
				if (lineData.isCovered()) {
					continue;
				}

				System.out.printf(
					"[Whip] %s line %d is not covered %n", classData.getName(),
					lineData.getLineNumber());
			}

			assertionErrors.add(
				new AssertionError(
					classData.getName() + " is not fully covered"));

			return;
		}

		System.out.printf("[Whip] %s is fully covered.%n", classData.getName());
	}

	private static final File _dataFile;
	private static boolean _dynamicallyInstrumented;
	private static String[] _excludes;
	private static String[] _includes;
	private static Instrumentation _instrumentation;
	private static final File _lockFile;
	private static List<OriginalClassDefinition> _originalClassDefinitions;
	private static WhipClassFileTransformer _whipClassFileTransformer;

	static {
		_dataFile = new File(System.getProperty("whip.datafile"));

		File parentFolder = _dataFile.getParentFile();

		if (!parentFolder.exists()) {
			parentFolder.mkdirs();
		}

		// OS wide lock file is created by the first started process where we
		// know for sure that there is no race condition. Acquiring an exclusive
		// lock on this lock file prevents losing updates on the data file.

		_lockFile = new File(parentFolder, "lock");

		try {
			_lockFile.createNewFile();
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

	private static class OriginalClassDefinition {

		public ClassDefinition toClassDefinition() {
			try {
				Class<?> clazz = Class.forName(_className, true, _classLoader);

				return new ClassDefinition(clazz, _bytes);
			}
			catch (Throwable t) {
				return null;
			}
		}

		private OriginalClassDefinition(
			ClassLoader classLoader, String className, byte[] bytes) {

			_classLoader = classLoader;
			_className = className.replace('/', '.');
			_bytes = bytes;
		}

		private final byte[] _bytes;
		private final ClassLoader _classLoader;
		private final String _className;

	}

}