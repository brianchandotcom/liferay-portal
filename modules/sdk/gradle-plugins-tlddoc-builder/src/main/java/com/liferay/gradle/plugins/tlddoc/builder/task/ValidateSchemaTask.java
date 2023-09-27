/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.tlddoc.builder.task;

import com.liferay.gradle.plugins.tlddoc.builder.internal.util.TLDUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.AntBuilder;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class ValidateSchemaTask extends SourceTask {

	@Input
	@Optional
	public String getXMLParserClassName() {
		return GradleUtil.toString(_xmlParserClassName);
	}

	@InputFiles
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public FileCollection getXMLParserClasspath() {
		return _xmlParserClasspath;
	}

	@Input
	public boolean isDTDDisabled() {
		return _dtdDisabled;
	}

	@Input
	public boolean isFullChecking() {
		return _fullChecking;
	}

	@Input
	public boolean isLenient() {
		return _lenient;
	}

	public void setDTDDisabled(boolean dtdDisabled) {
		_dtdDisabled = dtdDisabled;
	}

	public void setFullChecking(boolean fullChecking) {
		_fullChecking = fullChecking;
	}

	public void setLenient(boolean lenient) {
		_lenient = lenient;
	}

	public void setXMLParserClassName(Object xmlParserClassName) {
		_xmlParserClassName = xmlParserClassName;
	}

	public void setXMLParserClasspath(FileCollection xmlParserClasspath) {
		_xmlParserClasspath = xmlParserClasspath;
	}

	@TaskAction
	public void validateSchema() {
		Project project = getProject();

		final AntBuilder antBuilder = project.getAnt();

		Map<String, Object> args = new HashMap<>();

		String xmlParserClassName = getXMLParserClassName();

		if (Validator.isNotNull(xmlParserClassName)) {
			args.put("classname", xmlParserClassName);
		}

		FileCollection xmlParserClasspath = getXMLParserClasspath();

		if ((xmlParserClasspath != null) && !xmlParserClasspath.isEmpty()) {
			args.put("classpath", xmlParserClasspath.getAsPath());
		}

		args.put("disableDTD", isDTDDisabled());
		args.put("fullchecking", isFullChecking());
		args.put("lenient", isLenient());

		Closure<Void> closure = new Closure<Void>(antBuilder) {

			@SuppressWarnings("unused")
			public void doCall() {
				FileTree fileTree = getSource();
				Logger logger = getLogger();

				fileTree.addToAntBuilder(
					antBuilder, "fileset", FileCollection.AntType.FileSet);

				for (File file : fileTree.getFiles()) {
					try {
						TLDUtil.scanDTDAndXSD(
							file,
							(publicId, dtdFile) -> {
								Map<String, Object> args = new HashMap<>();

								args.put("location", dtdFile);
								args.put("publicId", publicId);

								antBuilder.invokeMethod("dtd", args);

								if (logger.isInfoEnabled()) {
									logger.info("DTD {}:{}", publicId, dtdFile);
								}
							},
							(namespace, schemaFile) -> {
								Map<String, Object> args = new HashMap<>();

								args.put("file", schemaFile);
								args.put("namespace", namespace);

								antBuilder.invokeMethod("schema", args);

								if (logger.isInfoEnabled()) {
									logger.info(
										"Schema {}:{}", namespace, schemaFile);
								}
							});
					}
					catch (Exception exception) {
						if (logger.isErrorEnabled()) {
							String fileName = file.getName();

							logger.error("Unable to process {}", fileName);
						}
					}
				}
			}

		};

		antBuilder.invokeMethod("schemavalidate", new Object[] {args, closure});
	}

	private boolean _dtdDisabled;
	private boolean _fullChecking = true;
	private boolean _lenient;
	private Object _xmlParserClassName;
	private FileCollection _xmlParserClasspath;

}