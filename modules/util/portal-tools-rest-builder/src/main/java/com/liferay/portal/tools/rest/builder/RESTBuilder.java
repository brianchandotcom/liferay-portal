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

		String copyrightFileName = arguments.get("copyright.file");
		String restConfigFileName = arguments.get("rest.config.file");
		String restOpenAPIFileName = arguments.get("rest.openapi.file");

		try {
			new RESTBuilder(
				copyrightFileName, restConfigFileName, restOpenAPIFileName);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public RESTBuilder(
			String copyrightFileName, String restConfigFileName,
			String restOpenAPIFileName)
		throws Exception {

		ConfigYAML configYAML = _getConfigYAML(restConfigFileName);

		OpenAPIYAML openAPIYAML = _getOpenAPIYAML(restOpenAPIFileName);

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
			String schemaName = entry.getKey();
			Schema schema = entry.getValue();

			File file = _getDTOFile(configYAML, schemaName);
			String content = _getDTOContent(
				configYAML, copyrightFileName, schema, schemaName);

			FileUtil.write(file, content);

			file = _getResourceFile(configYAML, schemaName);
			content = _getResourceContent(
				configYAML, copyrightFileName, openAPIYAML, schemaName);

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
			ConfigYAML configYAML, String copyrightFileName, Schema schema,
			String schemaName)
		throws Exception {

		Map<String, Object> context = new HashMap<>();

		context.put("configYAML", configYAML);
		context.put("name", schemaName);
		context.put("schema", schema);

		String content = _freeMarker.processTemplate(
			FreeMarkerConstants.DTO_FTL, context);

		if ((copyrightFileName != null) && !copyrightFileName.isEmpty()) {
			File copyrightFile = new File(copyrightFileName);

			content = FileUtil.read(copyrightFile) + "\n\n" + content;
		}

		return content;
	}

	private File _getDTOFile(ConfigYAML configYAML, String schemaName) {
		StringBuilder sb = new StringBuilder();

		sb.append(configYAML.getApiDir());
		sb.append("/");

		String apiPackagePath = configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/");
		sb.append("/dto/");
		sb.append(schemaName);
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
			ConfigYAML configYAML, String copyrightFileName,
			OpenAPIYAML openAPIYAML, String schemaName)
		throws Exception {

		Map<String, Object> context = new HashMap<>();

		context.put("configYAML", configYAML);
		context.put("info", openAPIYAML.getInfo());
		context.put("name", schemaName);

		String content = _freeMarker.processTemplate(
			FreeMarkerConstants.RESOURCE_FTL, context);

		if ((copyrightFileName != null) && !copyrightFileName.isEmpty()) {
			File copyrightFile = new File(copyrightFileName);

			content = FileUtil.read(copyrightFile) + "\n\n" + content;
		}

		return content;
	}

	private File _getResourceFile(ConfigYAML configYAML, String schemaName) {
		StringBuilder sb = new StringBuilder();

		sb.append(configYAML.getApiDir());
		sb.append("/");

		String apiPackagePath = configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/");
		sb.append("/resource/");
		sb.append(schemaName);
		sb.append("Resource.java");

		return new File(sb.toString());
	}

	private static final FreeMarker _freeMarker = new FreeMarker();

}