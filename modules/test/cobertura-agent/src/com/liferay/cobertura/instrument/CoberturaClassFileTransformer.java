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

package com.liferay.cobertura.instrument;

import com.liferay.cobertura.agent.InstrumentationAgent;
import com.liferay.cobertura.coveragedata.ProjectData;
import com.liferay.cobertura.coveragedata.ProjectDataUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import java.lang.instrument.ClassFileTransformer;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import java.security.ProtectionDomain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * @author Shuyang Zhou
 */
public class CoberturaClassFileTransformer implements ClassFileTransformer {

	public CoberturaClassFileTransformer(String[] includes, String[] excludes) {
		_includePatterns = new Pattern[includes.length];

		for (int i = 0; i < includes.length; i++) {
			Pattern pattern = Pattern.compile(includes[i]);

			_includePatterns[i] = pattern;
		}

		_excludePatterns = new Pattern[excludes.length];

		for (int i = 0; i < excludes.length; i++) {
			Pattern pattern = Pattern.compile(excludes[i]);

			_excludePatterns[i] = pattern;
		}
	}

	public boolean matches(String className) {
		if (className == null) {
			return false;
		}

		if (_excludePatterns.length != 0) {
			for (Pattern excludePattern : _excludePatterns) {
				Matcher matcher = excludePattern.matcher(className);

				if (matcher.matches()) {
					return false;
				}
			}
		}

		if (_includePatterns.length != 0) {
			for (Pattern includePattern : _includePatterns) {
				Matcher matcher = includePattern.matcher(className);

				if (matcher.matches()) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public byte[] transform(
		ClassLoader classLoader, String className, Class<?> refinedClass,
		ProtectionDomain protectionDomain, byte[] classfileBuffer) {

		try {
			if (matches(className)) {
				InstrumentationAgent.recordInstrumentation(
					classLoader, className, classfileBuffer);

				ProjectData projectData =
					ProjectDataUtil.getOrCreateProjectData(classLoader);

				ClassWriter classWriter = new ContextAwareClassWriter(
					ClassWriter.COMPUTE_FRAMES);

				String name = className.replace('/', '.');

				ClassVisitor classVisitor = new CoberturaClassVisitor(
					projectData.getOrCreateClassData(name), classWriter);

				ClassReader classReader = new ClassReader(classfileBuffer);

				synchronized (projectData) {
					classReader.accept(classVisitor, 0);
				}

				byte[] data = classWriter.toByteArray();

				dumpIntrumentedClass(classLoader, className, data);

				return data;
			}
		}
		catch (Throwable t) {
			t.printStackTrace();

			throw new RuntimeException(t);
		}

		return null;
	}

	protected void dumpIntrumentedClass(
			ClassLoader classLoader, String className, byte[] data)
		throws IOException {

		if (!Boolean.getBoolean("junit.code.coverage.dump")) {
			return;
		}

		File logFile = new File(_dumpDir, "instrument.log");

		File dumpDir = _dumpDir;

		int index = className.lastIndexOf('/');

		if (index != -1) {
			dumpDir = new File(
				dumpDir + "/" + classLoader.toString(),
				className.substring(0, index));

			className = className.substring(index + 1);
		}

		dumpDir.mkdirs();

		File classFile = new File(dumpDir, className + ".class");

		try (OutputStream outputStream = new FileOutputStream(classFile)) {
			outputStream.write(data);
		}

		try (FileWriter fileWriter = new FileWriter(logFile, true)) {
			fileWriter.write(
				"Instrumented " + className + " from " + classLoader +
					" and dumped to " + classFile.getAbsolutePath() + "\n");
		}
	}

	private static final File _dumpDir;

	static {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		String name = runtimeMXBean.getName();

		int index = name.indexOf('@');

		String processId = null;

		if (index == -1) {
			processId = Long.toString(System.currentTimeMillis());
		}
		else {
			processId = name.substring(0, index);
		}

		_dumpDir = new File(
			System.getProperty("java.io.tmpdir"),
			"cobertura-dump/" + processId);
	}

	private final Pattern[] _excludePatterns;
	private final Pattern[] _includePatterns;

}