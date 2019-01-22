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

import java.util.Collections;
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
		Map<String, Object> restMap = _load(inputFileName);

		for (Map.Entry<String, Object> entry : restMap.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	private Map<String, Object> _load(String inputFileName) {
		File inputFile = new File(inputFileName);

		try (InputStream inputStream = new FileInputStream(inputFile)) {
			Yaml yaml = new Yaml();

			Map<String, Object> yamlData = yaml.load(inputStream);

			if (yamlData != null) {
				return yamlData;
			}

			return Collections.emptyMap();
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());

			return Collections.emptyMap();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();

			return Collections.emptyMap();
		}
	}

}