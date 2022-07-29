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

		List<String> lines = getSourceLines();

		gradleDependencies.sort(
			GradleDependency.COMPARATOR_LAST_LINE_NUMBER_DESC);

		for (GradleDependency gradleDependency : gradleDependencies) {
			int lineNumber = gradleDependency.getLineNumber();
			int lastLineNumber = gradleDependency.getLastLineNumber();

			for (int i = lastLineNumber; i >= lineNumber; i--) {
				lines.remove(i - 1);
			}
		}

		_saveSource(lines);
	}

	public void deleteGradleDependency(String group, String name) {
		deleteGradleDependency(null, group, name);
	}

	public void deleteGradleDependency(
		String configuration, String group, String name) {

		List<GradleDependency> gradleDependencies = getGradleDependencies();

		ListIterator<GradleDependency> listIterator =
			gradleDependencies.listIterator(gradleDependencies.size());

		List<String> lines = getSourceLines();

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

					lines.remove(i - 1);
				}
			}
		}

		_saveSource(lines);
	}

	public List<GradleDependency> getGradleDependencies() {
		GradleBuildFileVisitor buildFileVisitor = _walkAST();

		return buildFileVisitor.getGradleDependencies();
	}

	public List<GradleDependency> getGradleDependencies(String configuration) {
		GradleBuildFileVisitor buildFileVisitor = _walkAST();

		List<GradleDependency> gradleDependencies =
			buildFileVisitor.getGradleDependencies();

		Stream<GradleDependency> stream = gradleDependencies.stream();

		return stream.filter(
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

	public void insertGradleDependency(GradleDependency dependency) {
		GradleBuildFileVisitor buildFileVisitor = _walkAST();

		for (GradleDependency dep : buildFileVisitor.getGradleDependencies()) {
			if (dep.equals(dependency)) {
				return;
			}
		}

		List<String> lines = getSourceLines();

		int dependencyLastLineNumber =
			buildFileVisitor.getDependenciesLastLineNumber();

		if (dependencyLastLineNumber == -1) {
			lines.add("");
			lines.add("dependencies {");
			lines.add("\t" + dependency.toString());
			lines.add("}");
		}
		else {
			lines.add(
				dependencyLastLineNumber - 1, "\t" + dependency.toString());
		}

		_saveSource(lines);
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
		Stream<String> stream = lines.stream();

		_source = stream.collect(Collectors.joining(System.lineSeparator()));
	}

	private GradleBuildFileVisitor _walkAST() {
		AstBuilder astBuilder = new AstBuilder();
		GradleBuildFileVisitor buildFileVisitor = new GradleBuildFileVisitor();

		for (ASTNode astNode :
				astBuilder.buildFromString(CompilePhase.CONVERSION, _source)) {

			if (astNode instanceof ClassNode) {
				continue;
			}

			astNode.visit(buildFileVisitor);
		}

		return buildFileVisitor;
	}

	private String _source;

}