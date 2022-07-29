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

package com.liferay.source.formatter.upgrade;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.control.CompilePhase;

/**
 * @author Kevin Lee
 */
public class GradleBuildFile {

	public GradleBuildFile(String source) {
		_source = source;
	}

	public void deleteGradleDependencies(
		List<GradleDependency> gradleDependencies) {

		List<String> sourceLines = getSourceLines();

		gradleDependencies.sort(
			GradleDependency.COMPARATOR_LAST_LINE_NUMBER_DESC);

		for (GradleDependency gradleDependency : gradleDependencies) {
			int lineNumber = gradleDependency.getLineNumber();
			int lastLineNumber = gradleDependency.getLastLineNumber();

			for (int i = lastLineNumber; i >= lineNumber; i--) {
				sourceLines.remove(i - 1);
			}
		}

		_saveSource(sourceLines);
	}

	public void deleteGradleDependency(String group, String name) {
		deleteGradleDependency(null, group, name);
	}

	public void deleteGradleDependency(
		String configuration, String group, String name) {

		List<GradleDependency> gradleDependencies = getGradleDependencies();

		ListIterator<GradleDependency> listIterator =
			gradleDependencies.listIterator(gradleDependencies.size());

		List<String> sourceLines = getSourceLines();

		while (listIterator.hasPrevious()) {
			GradleDependency gradleDependency = listIterator.previous();

			if ((configuration != null) &&
				!Objects.equals(
					configuration, gradleDependency.getConfiguration())) {

				continue;
			}

			if (Objects.equals(group, gradleDependency.getGroup()) &&
				Objects.equals(name, gradleDependency.getName())) {

				for (int i = gradleDependency.getLastLineNumber();
					 i >= gradleDependency.getLineNumber(); i--) {

					sourceLines.remove(i - 1);
				}
			}
		}

		_saveSource(sourceLines);
	}

	public List<GradleDependency> getGradleDependencies() {
		GradleBuildFileVisitor gradleBuildFileVisitor = _walkAST();

		return gradleBuildFileVisitor.getGradleDependencies();
	}

	public List<GradleDependency> getGradleDependencies(String configuration) {
		GradleBuildFileVisitor gradleBuildFileVisitor = _walkAST();

		List<GradleDependency> gradleDependencies =
			gradleBuildFileVisitor.getGradleDependencies();

		Stream<GradleDependency> gradleDependencyStream =
			gradleDependencies.stream();

		return gradleDependencyStream.filter(
			gradleDependency -> Objects.equals(
				configuration, gradleDependency.getConfiguration())
		).collect(
			Collectors.toList()
		);
	}

	public String getSource() {
		return _source;
	}

	public List<String> getSourceLines() {
		return Stream.of(
			_source.split(System.lineSeparator())
		).collect(
			Collectors.toList()
		);
	}

	public void insertGradleDependency(GradleDependency gradleDependency) {
		GradleBuildFileVisitor gradleBuildFileVisitor = _walkAST();

		for (GradleDependency currentGradleDependency :
				gradleBuildFileVisitor.getGradleDependencies()) {

			if (currentGradleDependency.equals(gradleDependency)) {
				return;
			}
		}

		List<String> sourceLines = getSourceLines();

		int dependenciesLastLineNumber =
			gradleBuildFileVisitor.getDependenciesLastLineNumber();

		if (dependenciesLastLineNumber == -1) {
			sourceLines.add("");
			sourceLines.add("dependencies {");
			sourceLines.add("\t" + gradleDependency.toString());
			sourceLines.add("}");
		}
		else {
			sourceLines.add(
				dependenciesLastLineNumber - 1,
				"\t" + gradleDependency.toString());
		}

		_saveSource(sourceLines);
	}

	public void insertGradleDependency(
		String configuration, String group, String name) {

		insertGradleDependency(configuration, group, name, null);
	}

	public void insertGradleDependency(
		String configuration, String group, String name, String version) {

		insertGradleDependency(
			new GradleDependency(configuration, group, name, version));
	}

	private void _saveSource(List<String> lines) {
		Stream<String> sourceStream = lines.stream();

		_source = sourceStream.collect(
			Collectors.joining(System.lineSeparator()));
	}

	private GradleBuildFileVisitor _walkAST() {
		AstBuilder astBuilder = new AstBuilder();

		GradleBuildFileVisitor gradleBuildFileVisitor =
			new GradleBuildFileVisitor();

		for (ASTNode astNode :
				astBuilder.buildFromString(CompilePhase.CONVERSION, _source)) {

			if (astNode instanceof ClassNode) {
				continue;
			}

			astNode.visit(gradleBuildFileVisitor);
		}

		return gradleBuildFileVisitor;
	}

	private String _source;

}