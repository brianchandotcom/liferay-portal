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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * @author Peter Shin
 */
public class RESTBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String inputFileName = arguments.get("input.file");

		try {
			new RESTBuilder(inputFileName);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public RESTBuilder(String inputFileName) {
		List<Entity> entities = _getEntities(inputFileName);

		for (Entity entity : entities) {
			System.out.println(entity);
		}
	}

	private List<Entity> _getEntities(String inputFileName) {
		File inputFile = new File(inputFileName);

		try (InputStream inputStream = new FileInputStream(inputFile)) {
			Yaml yaml = new Yaml();

			Map<String, Object> yamlData = yaml.load(inputStream);

			if (yamlData == null) {
				return Collections.emptyList();
			}

			String apiDir = (String)yamlData.get("api-dir");
			String apiPackagePath = (String)yamlData.get("api-package-path");
			String author = (String)yamlData.get("author");

			List<Entity> entities = new ArrayList<>();

			for (Object object : (List)yamlData.get("entities")) {
				Map<String, Object> map = (Map)object;

				Entity entity = new Entity();

				entity.setApiDir(apiDir);
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

}