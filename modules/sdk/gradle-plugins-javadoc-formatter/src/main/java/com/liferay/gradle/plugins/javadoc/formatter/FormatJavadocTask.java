/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.javadoc.formatter;

import com.liferay.gradle.util.GUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.javadoc.formatter.JavadocFormatterArgs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.JavaExec;
import org.gradle.util.CollectionUtils;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class FormatJavadocTask extends JavaExec {

	public FormatJavadocTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set("com.liferay.javadoc.formatter.JavadocFormatter");
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	@Input
	public String getAuthor() {
		return GradleUtil.toString(_author);
	}

	@Input
	public List<String> getLimits() {
		return GradleUtil.toStringList(_limits);
	}

	@Input
	public String getOutputFilePrefix() {
		return GradleUtil.toString(_outputFilePrefix);
	}

	@Input
	public boolean isGenerateXml() {
		return _generateXml;
	}

	@Input
	public boolean isInitializeMissingJavadocs() {
		return _initializeMissingJavadocs;
	}

	@Input
	public boolean isUpdateJavadocs() {
		return _updateJavadocs;
	}

	public FormatJavadocTask limits(Iterable<Object> limits) {
		GUtil.addToCollection(_limits, limits);

		return this;
	}

	public FormatJavadocTask limits(Object... limits) {
		return limits(Arrays.asList(limits));
	}

	public void setAuthor(Object author) {
		_author = author;
	}

	public void setGenerateXml(boolean generateXml) {
		_generateXml = generateXml;
	}

	public void setInitializeMissingJavadocs(
		boolean initializeMissingJavadocs) {

		_initializeMissingJavadocs = initializeMissingJavadocs;
	}

	public void setLimits(Iterable<Object> limits) {
		_limits.clear();

		limits(limits);
	}

	public void setLimits(Object... limits) {
		setLimits(Arrays.asList(limits));
	}

	public void setOutputFilePrefix(Object outputFilePrefix) {
		_outputFilePrefix = outputFilePrefix;
	}

	public void setUpdateJavadocs(boolean updateJavadocs) {
		_updateJavadocs = updateJavadocs;
	}

	@Internal
	protected List<String> getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("javadoc.author=" + getAuthor());
		args.add("javadoc.generate.xml=" + isGenerateXml());
		args.add("javadoc.init=" + isInitializeMissingJavadocs());
		args.add("javadoc.input.dir=./");
		args.add("javadoc.limit=" + CollectionUtils.join(",", getLimits()));
		args.add("javadoc.output.file.prefix=" + getOutputFilePrefix());
		args.add("javadoc.update=" + isUpdateJavadocs());

		return args;
	}

	private Object _author = JavadocFormatterArgs.AUTHOR;
	private boolean _generateXml;
	private boolean _initializeMissingJavadocs;
	private final Set<Object> _limits = new LinkedHashSet<>();
	private Object _outputFilePrefix = JavadocFormatterArgs.OUTPUT_FILE_PREFIX;
	private boolean _updateJavadocs;

}