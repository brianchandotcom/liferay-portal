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

	public void deleteDependencies(List<GradleDependency> dependencies) {
		List<String> lines = getSourceLines();

		dependencies.sort(GradleDependency.COMPARATOR_LAST_LINE_NUMBER_DESC);

		for (GradleDependency dependency : dependencies) {
			int lineNumber = dependency.getLineNumber();
			int lastLineNumber = dependency.getLastLineNumber();

			for (int i = lastLineNumber; i >= lineNumber; i--) {
				lines.remove(i - 1);
			}
		}

		_saveSource(lines);
	}

	public void deleteDependency(String group, String name) {
		deleteDependency(null, group, name);
	}

	public void deleteDependency(
		String configuration, String group, String name) {

		List<GradleDependency> dependencies = getDependencies();

		ListIterator<GradleDependency> listIterator = dependencies.listIterator(
			dependencies.size());

		List<String> lines = getSourceLines();

		while (listIterator.hasPrevious()) {
			GradleDependency dependency = listIterator.previous();

			if ((configuration != null) &&
				!Objects.equals(configuration, dependency.getConfiguration())) {

				continue;
			}

			if (Objects.equals(group, dependency.getGroup()) &&
				Objects.equals(name, dependency.getName())) {

				for (int i = dependency.getLastLineNumber();
					 i >= dependency.getLineNumber(); i--) {

					lines.remove(i - 1);
				}
			}
		}

		_saveSource(lines);
	}

	public List<GradleDependency> getDependencies() {
		GradleBuildFileVisitor buildFileVisitor = _walkAST();

		return buildFileVisitor.getDependencies();
	}

	public List<GradleDependency> getDependencies(String configuration) {
		GradleBuildFileVisitor buildFileVisitor = _walkAST();

		List<GradleDependency> dependencies =
			buildFileVisitor.getDependencies();

		Stream<GradleDependency> stream = dependencies.stream();

		return stream.filter(
			dependency -> Objects.equals(
				configuration, dependency.getConfiguration())
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

	public void insertDependency(GradleDependency dependency) {
		GradleBuildFileVisitor buildFileVisitor = _walkAST();

		for (GradleDependency dep : buildFileVisitor.getDependencies()) {
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

	public void insertDependency(
		String configuration, String group, String name) {

		insertDependency(configuration, group, name, null);
	}

	public void insertDependency(
		String configuration, String group, String name, String version) {

		insertDependency(
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