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

import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.FreeMarkerConstants;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.FreeMarkerUtil;
import com.liferay.portal.tools.rest.builder.internal.util.CamelCaseUtil;
import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.Application;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Components;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;
import com.liferay.portal.tools.rest.builder.internal.yaml.util.YAMLUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

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

		ConfigYAML configYAML = YAMLUtil.loadConfigYAML(restConfigFileName);

		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(restOpenAPIFileName);

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
			String schemaName = entry.getKey();
			Schema schema = entry.getValue();

			_createDTOFile(configYAML, copyrightFileName, schema, schemaName);

			_createResourceFile(
				configYAML, copyrightFileName, openAPIYAML, schemaName);

			_createResourceImplFile(
				configYAML, copyrightFileName, openAPIYAML, schemaName);
		}

		_createApplicationFile(configYAML, copyrightFileName);

		_createRESTCollectionFile(configYAML, copyrightFileName, openAPIYAML);

		System.out.println(YAMLUtil.dump(openAPIYAML));
	}

	private File _createApplicationFile(
			ConfigYAML configYAML, String copyrightFileName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/application/");

		Application application = configYAML.getApplication();

		sb.append(application.getClassName());

		sb.append(".java");

		File file = new File(sb.toString());

		Map<String, Object> context = new HashMap<>();

		context.put("configYAML", configYAML);
		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("validator", Validator_IW.getInstance());

		String content = FreeMarkerUtil.processTemplate(
			copyrightFileName, FreeMarkerConstants.APPLICATION_FTL, context);

		FileUtil.write(content, file);

		return file;
	}

	private File _createDTOFile(
			ConfigYAML configYAML, String copyrightFileName, Schema schema,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(configYAML.getApiDir());
		sb.append("/");

		String apiPackagePath = configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/dto/");
		sb.append(schemaName);
		sb.append(".java");

		File file = new File(sb.toString());

		Map<String, Object> context = new HashMap<>();

		context.put("configYAML", configYAML);
		context.put("schema", schema);
		context.put("schemaName", schemaName);
		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("validator", Validator_IW.getInstance());

		String content = FreeMarkerUtil.processTemplate(
			copyrightFileName, FreeMarkerConstants.DTO_FTL, context);

		FileUtil.write(content, file);

		return file;
	}

	private File _createResourceFile(
			ConfigYAML configYAML, String copyrightFileName,
			OpenAPIYAML openAPIYAML, String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(configYAML.getApiDir());
		sb.append("/");

		String apiPackagePath = configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/resource/");
		sb.append(schemaName);
		sb.append("Resource.java");

		File file = new File(sb.toString());

		Map<String, Object> context = new HashMap<>();

		context.put("configYAML", configYAML);
		context.put("openAPIYAML", openAPIYAML);
		context.put("schemaName", schemaName);
		context.put("schemaPath", CamelCaseUtil.fromCamelCase(schemaName));
		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("validator", Validator_IW.getInstance());

		String content = FreeMarkerUtil.processTemplate(
			copyrightFileName, FreeMarkerConstants.RESOURCE_FTL, context);

		FileUtil.write(content, file);

		return file;
	}

	private File _createResourceImplFile(
			ConfigYAML configYAML, String copyrightFileName,
			OpenAPIYAML openAPIYAML, String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/resource/");
		sb.append(schemaName);
		sb.append("ResourceImpl.java");

		File file = new File(sb.toString());

		Map<String, Object> context = new HashMap<>();

		context.put("configYAML", configYAML);
		context.put("openAPIYAML", openAPIYAML);
		context.put("schemaName", schemaName);
		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("validator", Validator_IW.getInstance());

		String content = FreeMarkerUtil.processTemplate(
			copyrightFileName, FreeMarkerConstants.RESOURCE_IMPL_FTL, context);

		FileUtil.write(content, file);

		return file;
	}

	private File _createRESTCollectionFile(
			ConfigYAML configYAML, String copyrightFileName,
			OpenAPIYAML openAPIYAML)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(configYAML.getApiDir());
		sb.append("/");

		String apiPackagePath = configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/dto/RESTCollection.java");

		File file = new File(sb.toString());

		Map<String, Object> context = new HashMap<>();

		context.put("configYAML", configYAML);
		context.put("openAPIYAML", openAPIYAML);
		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("validator", Validator_IW.getInstance());

		String content = FreeMarkerUtil.processTemplate(
			copyrightFileName, FreeMarkerConstants.REST_COLLECTION_FTL,
			context);

		FileUtil.write(content, file);

		return file;
	}

}