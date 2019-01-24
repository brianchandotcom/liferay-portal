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

import freemarker.cache.ClassTemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

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

		List<Entity> entities = _getEntities(
			apiDirName, apiPackagePath, author, inputFileName);

		for (Entity entity : entities) {
			FileUtil.write(_getModelFile(entity), _getModelContent(entity));
		}
	}

	private List<Entity> _getEntities(
		String apiDirName, String apiPackagePath, String author,
		String inputFileName) {

		File inputFile = new File(inputFileName);

		try (InputStream inputStream = new FileInputStream(inputFile)) {
			Yaml yaml = new Yaml();

			Map<String, Object> yamlData = yaml.load(inputStream);

			if (yamlData == null) {
				return Collections.emptyList();
			}

			List<Entity> entities = new ArrayList<>();

			for (Object object : (List)yamlData.get("entities")) {
				Map<String, Object> map = (Map)object;

				Entity entity = new Entity();

				entity.setApiDir(apiDirName);
				entity.setApiPackagePath(apiPackagePath);
				entity.setAuthor(author);
				entity.setName((String)map.get("name"));

				entities.add(entity);
			}

			return entities;
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());

			return Collections.emptyList();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();

			return Collections.emptyList();
		}
	}

	private String _getModelContent(Entity entity) throws Exception {
		Map<String, Object> context = new HashMap<>();

		context.put("entity", entity);

		return _processTemplate(
			"com/liferay/portal/tools/rest/builder/dependencies/model.ftl",
			context);
	}

	private File _getModelFile(Entity entity) {
		StringBuilder sb = new StringBuilder();

		sb.append(entity.getApiDir());
		sb.append("/");

		String apiPackagePath = entity.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/");
		sb.append("/model/");
		sb.append(entity.getName());
		sb.append("Model.java");

		return new File(sb.toString());
	}

	private String _processTemplate(String name, Map<String, Object> context)
		throws Exception {

		Configuration configuration = _configuration;

		if (configuration == null) {
			_configuration = new Configuration(Configuration.getVersion());

			_configuration.setNumberFormat("computer");

			DefaultObjectWrapperBuilder defaultObjectWrapperBuilder =
				new DefaultObjectWrapperBuilder(Configuration.getVersion());

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

	private static Configuration _configuration;

}