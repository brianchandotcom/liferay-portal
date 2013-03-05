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

package net.sourceforge.cobertura.instrument;

import java.io.File;
import java.io.IOException;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.coveragedata.CoverageDataFileHandler;
import net.sourceforge.cobertura.coveragedata.ProjectData;

/**
 * @author Shuyang Zhou
 */
public class InstrumentationAgent {

	public static synchronized void assertCoverage(
		boolean includeInnerClasses, Class<?>... classes) {

		if (!_dynamicInstrumented) {
			return;
		}

		File dataFile = CoverageDataFileHandler.getDefaultDataFile();

		ProjectData projectData = ProjectDataUtil.captureProjectData(
			dataFile, _lockFile);

		for (Class<?> clazz : classes) {
			ClassData classData = projectData.getClassData(clazz.getName());

			_assertClassDataCoverage(clazz, classData);

			Class<?>[] declaredClasses = clazz.getDeclaredClasses();

			for (Class<?> declaredClass : declaredClasses) {
				classData = projectData.getClassData(declaredClass.getName());

				_assertClassDataCoverage(declaredClass, classData);
			}
		}

		System.clearProperty("junit.code.coverage");
	}

	public static synchronized void dynamicInstrument(
			String[] includes, String[] excludes)
		throws UnmodifiableClassException {

		if ((_instrumentation == null) || _dynamicInstrumented) {
			return;
		}

		if (includes == null) {
			includes = _includes;
		}

		if (excludes == null) {
			excludes = _excludes;
		}

		CoberturaClassFileTransformer coberturaClassFileTransformer =
			new CoberturaClassFileTransformer(includes, excludes, _lockFile);

		_instrumentation.addTransformer(coberturaClassFileTransformer, true);

		Class<?>[] allLoadedClasses =_instrumentation.getAllLoadedClasses();

		List<Class<?>> modifiableClasses = new ArrayList<Class<?>>();

		for (Class<?> loadedClass : allLoadedClasses) {
			if (_instrumentation.isModifiableClass(loadedClass)) {
				modifiableClasses.add(loadedClass);
			}
		}

		_instrumentation.retransformClasses(
			modifiableClasses.toArray(new Class<?>[modifiableClasses.size()]));

		_dynamicInstrumented = true;

		System.setProperty("junit.code.coverage", "true");
	}

	public static void initialize() {

		// Shutdown hook for each ClassLoader to persistent touch data

		ProjectDataUtil.addShutdownHook(new Runnable() {

			public void run() {

				// Merge persistent touch data

				File dataFile = CoverageDataFileHandler.getDefaultDataFile();

				ProjectData projectData = ProjectDataUtil.collectProjectData();

				ProjectDataUtil.mergeSave(dataFile, _lockFile, projectData);
			}

		});
	}

	public static synchronized void premain(
		String agentArgs, Instrumentation instrumentation) {

		String[] args = agentArgs.split(";");

		String[] includes = args[0].split(",");
		String[] excludes = args[1].split(",");

		boolean junitCodeCoverage = Boolean.getBoolean("junit.code.coverage");

		if (junitCodeCoverage) {
			CoberturaClassFileTransformer coberturaClassFileTransformer =
				new CoberturaClassFileTransformer(
					includes, excludes, _lockFile);

			instrumentation.addTransformer(coberturaClassFileTransformer);
		}
		else if (instrumentation.isRetransformClassesSupported()) {
			_instrumentation = instrumentation;
			_includes = includes;
			_excludes = excludes;

			// Force clear data file to make sure the coervage assert is based
			// on current test.

			File dataFile = CoverageDataFileHandler.getDefaultDataFile();

			dataFile.delete();
		}
		else {
			System.out.println(
				"Warning! Current JVM does not support class retransform. " +
					"Dynamic instrument is disabled.");
		}
	}

	private static void _assertClassDataCoverage(
		Class<?> clazz, ClassData classData) {

		if (classData == null) {
			throw new RuntimeException(
				"Class " + clazz.getName() + " has no coverage data.");
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

			throw new RuntimeException(
				classData.getName() + " is not fully covered");
		}

		System.out.printf(
			"[Cobertura] %s is fully covered.%n", classData.getName());
	}

	private static final File _lockFile;

	static {
		File dataFile = CoverageDataFileHandler.getDefaultDataFile();

		File parentFolder = dataFile.getParentFile();

		if (!parentFolder.exists()) {
			parentFolder.mkdirs();
		}

		// OS wide lock file, created by the first started process where we know
		// for sure there is no race condition. All data file accessing needs to
		// acquire exclusive lock on this lock file to prevent lost update.

		_lockFile = new File(parentFolder, "lock");

		try {
			_lockFile.createNewFile();
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

	private static boolean _dynamicInstrumented;
	private static String[] _excludes;
	private static String[] _includes;
	private static Instrumentation _instrumentation;

}