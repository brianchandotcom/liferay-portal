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

package com.liferay.portal.tools.rest.builder.internal.yaml.util;

import com.liferay.portal.tools.rest.builder.internal.yaml.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.OpenAPIYAML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

/**
 * @author Peter Shin
 */
public class YAMLUtil {

	public static <T> String dump(T t) {
		StringWriter stringWriter = new StringWriter();

		Yaml yaml = new Yaml();

		yaml.dump(t, stringWriter);

		return stringWriter.toString();
	}

	public static <T> T load(
		Class<T> clazz, String fileName, TypeDescription... typeDescriptions) {

		File file = new File(fileName);

		try (InputStream inputStream = new FileInputStream(file)) {
			Constructor constructor = new Constructor(clazz);

			for (TypeDescription typeDescription : typeDescriptions) {
				constructor.addTypeDescription(typeDescription);
			}

			Representer representer = new Representer();

			PropertyUtils propertyUtils = representer.getPropertyUtils();

			propertyUtils.setSkipMissingProperties(true);

			Yaml yaml = new Yaml(constructor, representer);

			return yaml.loadAs(inputStream, clazz);
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

	public static ConfigYAML loadConfigYAML(String fileName) {
		return load(ConfigYAML.class, fileName);
	}

	public static OpenAPIYAML loadOpenAPIYAML(String fileName) {
		return load(OpenAPIYAML.class, fileName);
	}

}