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

package com.liferay.cobertura.agent;

import com.liferay.cobertura.coveragedata.ProjectData;
import com.liferay.cobertura.coveragedata.ProjectDataUtil;
import com.liferay.cobertura.instrument.CoberturaClassFileTransformer;

import java.io.File;
import java.io.IOException;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.coveragedata.CoverageData;
import net.sourceforge.cobertura.coveragedata.LineData;

/**
 * @author Shuyang Zhou
 */
public class InstrumentationAgent {

	public static synchronized void assertCoverage(
		boolean includeInnerClasses, Class<?>... classes) {

		if (!_dynamicallyInstrumented) {
			return;
		}

		_instrumentation.removeTransformer(_coberturaClassFileTransformer);

		_coberturaClassFileTransformer = null;

		try {
			ProjectData projectData = ProjectDataUtil.captureProjectData(false);

			List<AssertionError> assertionErrors = new ArrayList<>();

			for (Class<?> clazz : classes) {
				ClassData classData = projectData.getClassData(clazz.getName());

				_assertClassDataCoverage(assertionErrors, clazz, classData);

				if (includeInnerClasses) {
					Class<?>[] declaredClasses = clazz.getDeclaredClasses();

					declaredClass:
					for (Class<?> declaredClass : declaredClasses) {
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
			System.clearProperty("junit.code.coverage");

			_dynamicallyInstrumented = false;

			if (_originalClassDefinitions != null) {
				try {
					List<ClassDefinition> classDefinitions = new ArrayList<>(
						_originalClassDefinitions.size());

					for (int i = 0; i < _originalClassDefinitions.size(); i++) {
						OriginalClassDefinition originalClassDefinition =
							_originalClassDefinitions.get(i);

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

		if (_coberturaClassFileTransformer == null) {
			_coberturaClassFileTransformer = new CoberturaClassFileTransformer(
				includes, excludes);
		}

		_instrumentation.addTransformer(_coberturaClassFileTransformer, true);

		Class<?>[] allLoadedClasses =_instrumentation.getAllLoadedClasses();

		List<Class<?>> modifiableClasses = new ArrayList<>();

		for (Class<?> loadedClass : allLoadedClasses) {
			if (_instrumentation.isModifiableClass(loadedClass)) {
				String className = loadedClass.getName();

				className = className.replace('.', '/');

				if (_coberturaClassFileTransformer.matches(className)) {
					modifiableClasses.add(loadedClass);
				}
			}
		}

		_instrumentation.retransformClasses(
			modifiableClasses.toArray(new Class<?>[modifiableClasses.size()]));

		_dynamicallyInstrumented = true;
		_originalClassDefinitions = null;

		System.setProperty("junit.code.coverage", "true");
	}

	public static File getLockFile() {
		return _lockFile;
	}

	public static synchronized void premain(
		String agentArguments, Instrumentation instrumentation) {

		String[] arguments = agentArguments.split(";");

		String[] includes = arguments[0].split(",");
		String[] excludes = arguments[1].split(",");

		if (Boolean.getBoolean("junit.code.coverage")) {
			final CoberturaClassFileTransformer coberturaClassFileTransformer =
				new CoberturaClassFileTransformer(includes, excludes);

			instrumentation.addTransformer(coberturaClassFileTransformer);

			Runtime runtime = Runtime.getRuntime();

			runtime.addShutdownHook(
				new Thread() {

					@Override
					public void run() {
						ProjectDataUtil.captureProjectData(true);
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

			File dataFile = new File(
				System.getProperty("net.sourceforge.cobertura.datafile"));

			dataFile.delete();
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

		if (clazz.isInterface() || clazz.isSynthetic()) {
			return;
		}

		if (classData == null) {
			assertionErrors.add(
				new AssertionError(
					"Class " + clazz.getName() + " has no coverage data"));

			return;
		}

		if ((classData.getBranchCoverageRate() != 1.0) ||
			(classData.getLineCoverageRate() != 1.0)) {

			System.out.printf(
				"%n[Cobertura] %s is not fully covered.%n[Cobertura]Branch " +
					"coverage rate : %.2f, line coverage rate : %.2f.%n" +
						"[Cobertura]Please rerun test with -Djunit.code." +
							"coverage=true to see coverage report.%n",
				classData.getName(), classData.getBranchCoverageRate(),
				classData.getLineCoverageRate());

			Set<CoverageData> coverageDatas = classData.getLines();

			for (CoverageData coverageData : coverageDatas) {
				if (!(coverageData instanceof LineData)) {
					continue;
				}

				LineData lineData = (LineData)coverageData;

				if (lineData.isCovered()) {
					continue;
				}

				System.out.printf(
					"[Cobertura] %s line %d is not covered %n",
					classData.getName(), lineData.getLineNumber());
			}

			assertionErrors.add(
				new AssertionError(
					classData.getName() + " is not fully covered"));

			return;
		}

		System.out.printf(
			"[Cobertura] %s is fully covered.%n", classData.getName());
	}

	private static CoberturaClassFileTransformer _coberturaClassFileTransformer;
	private static boolean _dynamicallyInstrumented;
	private static String[] _excludes;
	private static String[] _includes;
	private static Instrumentation _instrumentation;
	private static final File _lockFile;
	private static List<OriginalClassDefinition> _originalClassDefinitions;

	static {
		File dataFile = new File(
			System.getProperty("net.sourceforge.cobertura.datafile"));

		File parentFolder = dataFile.getParentFile();

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

		public OriginalClassDefinition(
			ClassLoader classLoader, String className, byte[] bytes) {

			_classLoader = classLoader;
			_className = className.replace('/', '.');
			_bytes = bytes;
		}

		public ClassDefinition toClassDefinition()
			throws ClassNotFoundException {

			try {
				Class<?> clazz = Class.forName(_className, true, _classLoader);

				return new ClassDefinition(clazz, _bytes);
			}
			catch (Throwable t) {
				return null;
			}
		}

		private final byte[] _bytes;
		private final ClassLoader _classLoader;
		private final String _className;

	}

}