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
import com.liferay.portal.tools.rest.builder.internal.freemarker.FreeMarker;
import com.liferay.portal.tools.rest.builder.internal.freemarker.FreeMarkerConstants;
import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.Components;
import com.liferay.portal.tools.rest.builder.internal.yaml.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.Schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
		String copyrightFileName = arguments.get("copyright.file");
		String inputFileName = arguments.get("input.file");

		try {
			new RESTBuilder(
				apiDirName, apiPackagePath, author, copyrightFileName,
				inputFileName);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public RESTBuilder(
			String apiDirName, String apiPackagePath, String author,
			String copyrightFileName, String inputFileName)
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

			File file = _getDTOFile(apiDirName, apiPackagePath, name);
			String content = _getDTOContent(
				apiPackagePath, author, copyrightFileName, name, schema);

			FileUtil.write(file, content);

			file = _getResourceFile(apiDirName, apiPackagePath, name);
			content = _getResourceContent(
				apiPackagePath, author, copyrightFileName, name, configuration);

			FileUtil.write(file, content);
		}
	}

	private ConfigYAML _getConfigYAML(String restConfigFileName) {
		File file = new File(restConfigFileName);

		try (InputStream inputStream = new FileInputStream(file)) {
			Constructor constructor = new Constructor(ConfigYAML.class);

			Representer representer = new Representer();

			PropertyUtils propertyUtils = representer.getPropertyUtils();

			propertyUtils.setSkipMissingProperties(true);

			Yaml yaml = new Yaml(constructor, representer);

			return yaml.loadAs(inputStream, ConfigYAML.class);
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

	private String _getDTOContent(
			String apiPackagePath, String author, String copyrightFileName,
			String name, Schema schema)
		throws Exception {

		Map<String, Object> context = new HashMap<>();

		context.put("apiPackagePath", apiPackagePath);
		context.put("author", author);
		context.put("name", name);
		context.put("schema", schema);

		String content = _freeMarker.processTemplate(
			FreeMarkerConstants.DTO_FTL, context);

		if ((copyrightFileName != null) && !copyrightFileName.isEmpty()) {
			File copyrightFile = new File(copyrightFileName);

			content = FileUtil.read(copyrightFile) + "\n\n" + content;
		}

		return content;
	}

	private File _getDTOFile(
		String apiDir, String apiPackagePath, String name) {

		StringBuilder sb = new StringBuilder();

		sb.append(apiDir);
		sb.append("/");
		sb.append(apiPackagePath.replace('.', '/'));
		sb.append("/");
		sb.append("/dto/");
		sb.append(name);
		sb.append(".java");

		return new File(sb.toString());
	}

	private OpenAPIYAML _getOpenAPIYAML(String restOpenAPIFileName) {
		File file = new File(restOpenAPIFileName);

		try (InputStream inputStream = new FileInputStream(file)) {
			Constructor constructor = new Constructor(OpenAPIYAML.class);

			Representer representer = new Representer();

			PropertyUtils propertyUtils = representer.getPropertyUtils();

			propertyUtils.setSkipMissingProperties(true);

			Yaml yaml = new Yaml(constructor, representer);

			return yaml.loadAs(inputStream, OpenAPIYAML.class);
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

	private String _getResourceContent(
			String apiPackagePath, String author, String copyrightFileName,
			String name, Configuration configuration)
		throws Exception {

		Map<String, Object> context = new HashMap<>();

		context.put("apiPackagePath", apiPackagePath);
		context.put("author", author);
		context.put("info", configuration.getInfo());
		context.put("name", name);

		String content = _freeMarker.processTemplate(
			FreeMarkerConstants.RESOURCE_FTL, context);

		if ((copyrightFileName != null) && !copyrightFileName.isEmpty()) {
			File copyrightFile = new File(copyrightFileName);

			content = FileUtil.read(copyrightFile) + "\n\n" + content;
		}

		return content;
	}

	private File _getResourceFile(
		String apiDir, String apiPackagePath, String name) {

		StringBuilder sb = new StringBuilder();

		sb.append(apiDir);
		sb.append("/");
		sb.append(apiPackagePath.replace('.', '/'));
		sb.append("/");
		sb.append("/resource/");
		sb.append(name);
		sb.append("Resource.java");

		return new File(sb.toString());
	}

	private static final FreeMarker _freeMarker = new FreeMarker();

}