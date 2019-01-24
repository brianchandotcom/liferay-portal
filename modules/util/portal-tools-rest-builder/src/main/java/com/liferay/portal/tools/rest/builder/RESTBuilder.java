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

package com.liferay.portal.tools.rest.builder;

import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.Components;
import com.liferay.portal.tools.rest.builder.internal.yaml.Configuration;
import com.liferay.portal.tools.rest.builder.internal.yaml.Schema;

import freemarker.cache.ClassTemplateLoader;

import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

/**
 * @author Peter Shin
 */
public class RESTBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String apiDirName = arguments.get("api.dir");
		String apiPackagePath = arguments.get("api.package.path");
		String author = arguments.get("author");
		String inputFileName = arguments.get("input.file");

		try {
			new RESTBuilder(apiDirName, apiPackagePath, author, inputFileName);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public RESTBuilder(
			String apiDirName, String apiPackagePath, String author,
			String inputFileName)
		throws Exception {

		Configuration configuration = _getConfiguration(inputFileName);

		if (configuration == null) {
			return;
		}

		Components components = configuration.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
			String name = entry.getKey();
			Schema schema = entry.getValue();

			File file = _getModelFile(apiDirName, apiPackagePath, name);
			String content = _getModelContent(
				apiPackagePath, author, name, schema);

			FileUtil.write(file, content);
		}
	}

	private Configuration _getConfiguration(String inputFileName) {
		File inputFile = new File(inputFileName);

		try (InputStream inputStream = new FileInputStream(inputFile)) {
			Constructor constructor = new Constructor(Configuration.class);

			Representer representer = new Representer();

			PropertyUtils propertyUtils = representer.getPropertyUtils();

			propertyUtils.setSkipMissingProperties(true);

			Yaml yaml = new Yaml(constructor, representer);

			return yaml.loadAs(inputStream, Configuration.class);
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());

			return null;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();

			return null;
		}
	}

	private String _getModelContent(
			String apiPackagePath, String author, String name, Schema schema)
		throws Exception {

		Map<String, Object> context = new HashMap<>();

		context.put("apiPackagePath", apiPackagePath);
		context.put("author", author);
		context.put("name", name);
		context.put("schema", schema);

		return _processTemplate(
			"com/liferay/portal/tools/rest/builder/dependencies/model.ftl",
			context);
	}

	private File _getModelFile(
		String apiDirName, String apiPackagePath, String name) {

		StringBuilder sb = new StringBuilder();

		sb.append(apiDirName);
		sb.append("/");
		sb.append(apiPackagePath.replace('.', '/'));
		sb.append("/");
		sb.append("/model/");
		sb.append(name);
		sb.append("Model.java");

		return new File(sb.toString());
	}

	private String _processTemplate(String name, Map<String, Object> context)
		throws Exception {

		freemarker.template.Configuration configuration = _configuration;

		if (configuration == null) {
			_configuration = new freemarker.template.Configuration(
				freemarker.template.Configuration.getVersion());

			_configuration.setNumberFormat("computer");

			DefaultObjectWrapperBuilder defaultObjectWrapperBuilder =
				new DefaultObjectWrapperBuilder(
					freemarker.template.Configuration.getVersion());

			_configuration.setObjectWrapper(
				defaultObjectWrapperBuilder.build());

			_configuration.setTemplateLoader(
				new ClassTemplateLoader(RESTBuilder.class, "/"));
			_configuration.setTemplateUpdateDelayMilliseconds(Long.MAX_VALUE);

			configuration = _configuration;
		}

		Template template = configuration.getTemplate(name);

		StringWriter stringWriter = new StringWriter();

		template.process(context, stringWriter);

		StringBuffer stringBuffer = stringWriter.getBuffer();

		String content = stringBuffer.toString();

		return content.replace("\r", "");
	}

	private static freemarker.template.Configuration _configuration;

}