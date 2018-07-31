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

package com.liferay.portal.model;

import com.liferay.portal.kernel.model.ModelHintsCallback;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProvider;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.xml.SAXReaderFactory;

import org.dom4j.io.SAXReader;

/**
 * @author Raymond Augé
 */
public class DefaultModelHintsImpl extends BaseModelHintsImpl {

	@Override
	public ModelHintsCallback getModelHintsCallback() {
		return _modelHintsCallback;
	}

	@Override
	public String[] getModelHintsConfigs() {
		return _MODEL_HINTS_CONFIGS;
	}

	@Override
	public SAXReader getSAXReader() {
		SecureXMLFactoryProvider secureXMLFactoryProvider =
			SecureXMLFactoryProviderUtil.getSecureXMLFactoryProvider();

		return SAXReaderFactory.getSAXReader(
			secureXMLFactoryProvider.newXMLReader(),
			PropsValues.XML_VALIDATION_ENABLED, false);
	}

	public class RuntimeModelHintsCallback implements ModelHintsCallback {

		@Override
		public void execute(ClassLoader classLoader, String name) {
			if (classLoader != BaseModelHintsImpl.class.getClassLoader()) {
				ClassNameLocalServiceUtil.getClassName(name);
			}
		}

	}

	private static final String[] _MODEL_HINTS_CONFIGS = StringUtil.split(
		PropsUtil.get(PropsKeys.MODEL_HINTS_CONFIGS));

	private final ModelHintsCallback _modelHintsCallback =
		new RuntimeModelHintsCallback();

}