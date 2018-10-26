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

package com.liferay.project.templates.internal;

import com.liferay.project.templates.CodeTemplatesArgs;
import com.liferay.project.templates.ProjectTemplates;
import com.liferay.project.templates.internal.util.Validator;

import java.io.File;
import java.io.StringWriter;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeManager;
import org.apache.maven.archetype.common.ArchetypeArtifactManager;
import org.apache.maven.archetype.exception.UnknownArchetype;
import org.apache.maven.archetype.metadata.ArchetypeDescriptor;
import org.apache.maven.archetype.metadata.FileSet;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.util.StringUtils;

import org.codehaus.plexus.velocity.VelocityComponent;

/**
 * @author Gregory Amerson
 * @author Simon Jiang
 */
public class CodeGenerator {

	public void generateCode(
			CodeTemplatesArgs codeTemplatesArgs, File destinationDir)
		throws Exception {

		List<File> archetypesDirs = codeTemplatesArgs.getArchetypesDirs();
		String packageName = codeTemplatesArgs.getPackageName();

		ArchetypeGenerationRequest archetypeGenerationRequest =
			new ArchetypeGenerationRequest();

		archetypeGenerationRequest.setArchetypeArtifactId(
			ProjectTemplates.TEMPLATE_BUNDLE_PREFIX + "code");
		archetypeGenerationRequest.setArchetypeGroupId("com.liferay");
		archetypeGenerationRequest.setArchetypeVersion("0");
		archetypeGenerationRequest.setArtifactId("code");
		archetypeGenerationRequest.setGroupId("com.liferay");
		archetypeGenerationRequest.setInteractiveMode(false);
		archetypeGenerationRequest.setOutputDirectory(destinationDir.getPath());
		archetypeGenerationRequest.setPackage(packageName);

		Properties properties = new Properties();

		_setProperty(properties, "author", codeTemplatesArgs.getAuthor());
		_setProperty(
			properties, "className",
			StringUtils.capitalizeFirstLetter(
				codeTemplatesArgs.getClassName()));

		Map<String, String> addtionalParameters =
			codeTemplatesArgs.getAddtionalParameters();

		Set<String> addtionalParametersKeyset = addtionalParameters.keySet();

		Stream<String> parametersStream = addtionalParametersKeyset.stream();

		parametersStream.forEach(
			key -> _setProperty(properties, key, addtionalParameters.get(key))
		);

		_setProperty(properties, "package", packageName);
		_setProperty(
			properties, "packagePath", packageName.replaceAll("\\.", "_"));
		_setProperty(properties, "template", codeTemplatesArgs.getTemplate());

		archetypeGenerationRequest.setProperties(properties);

		Archetyper archetyper = new Archetyper(archetypesDirs) {

			@Override
			protected ArchetypeArtifactManager newArchetypeArtifactManager() {
				try {
					VelocityComponent velocityComponent =
						createVelocityComponent();

					return new CodeGeneratorArchetypeArtifactManager(
						archetypesDirs, velocityComponent, codeTemplatesArgs);
				}
				catch (Exception e) {
					return null;
				}
			}

		};

		ArchetypeManager archetypeManager = archetyper.createArchetypeManager();

		archetypeManager.generateProjectFromArchetype(
			archetypeGenerationRequest);
	}

	private void _setProperty(
		Properties properties, String name, String value) {

		if (Validator.isNotNull(value)) {
			properties.setProperty(name, value);
		}
	}

	private static class CodeGeneratorArchetypeArtifactManager
		extends ArchetyperArchetypeArtifactManager {

		@Override
		public ArchetypeDescriptor getFileSetArchetypeDescriptor(
				File archetypeFile)
			throws UnknownArchetype {

			ArchetypeDescriptor archetypeDescriptor =
				super.getFileSetArchetypeDescriptor(archetypeFile);

			for (FileSet fileSet : archetypeDescriptor.getFileSets()) {
				List<String> excludes = fileSet.getExcludes();

				Stream<String> stream = excludes.stream();

				List<String> newExcludes = stream.map(
					this::_filterElement
				).collect(
					Collectors.toList()
				);

				fileSet.setExcludes(newExcludes);

				List<String> includes = fileSet.getIncludes();

				stream = includes.stream();

				List<String> newIncludes = stream.map(
					this::_filterElement
				).collect(
					Collectors.toList()
				);

				fileSet.setIncludes(newIncludes);
			}

			return archetypeDescriptor;
		}

		private CodeGeneratorArchetypeArtifactManager(
			List<File> archetypesFiles, VelocityComponent velocityComponent,
			CodeTemplatesArgs codeTemplatesArgs) {

			super(archetypesFiles);

			_velocityComponent = velocityComponent;
			_codeTemplatesArgs = codeTemplatesArgs;
		}

		private String _filterElement(String element) {
			try {
				VelocityEngine velocityEngine = _velocityComponent.getEngine();

				if (velocityEngine != null) {
					VelocityContext velocityContext = new VelocityContext();

					velocityContext.put("StringUtils", new StringUtils());
					velocityContext.put(
						"template", _codeTemplatesArgs.getTemplate());

					StringWriter stringWriter = new StringWriter();

					boolean success = velocityEngine.evaluate(
						velocityContext, stringWriter, "CodeGenerator",
						element);

					if (success) {
						return stringWriter.toString();
					}
				}
			}
			catch (Exception ioe) {
			}

			return element;
		}

		private final CodeTemplatesArgs _codeTemplatesArgs;
		private final VelocityComponent _velocityComponent;

	}

}