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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.util.PropsUtil;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
@DoPrivileged
public class ModelHintsImpl implements ModelHints {

	public void afterPropertiesSet() {
		_hintCollections = new HashMap<String, Map<String, String>>();
		_defaultHints = new HashMap<String, Map<String, String>>();
		_modelFields = new HashMap<String, Object>();
		_models = new TreeSet<String>();

		try {
			ClassLoader classLoader = getClass().getClassLoader();

			String[] configs = StringUtil.split(
				PropsUtil.get(PropsKeys.MODEL_HINTS_CONFIGS));

			for (String config : configs) {
				if (config.startsWith("classpath*:")) {
					String name = config.substring("classpath*:".length());

					Enumeration<URL> enu = classLoader.getResources(name);

					if (_log.isDebugEnabled() && !enu.hasMoreElements()) {
						_log.debug("No resources found for " + name);
					}

					while (enu.hasMoreElements()) {
						URL url = enu.nextElement();

						if (_log.isDebugEnabled()) {
							_log.debug("Loading " + name + " from " + url);
						}

						InputStream inputStream = url.openStream();

						read(classLoader, url.toString(), inputStream);
					}
				}
				else {
					read(classLoader, config);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public String buildCustomValidatorName(String validatorName) {
		return validatorName.concat(StringPool.UNDERLINE).concat(
			StringUtil.randomId());
	}

	@Override
	public Map<String, String> getDefaultHints(String model) {
		return _defaultHints.get(model);
	}

	@Override
	public com.liferay.portal.kernel.xml.Element getFieldsEl(
		String model, String field) {

		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return null;
		}

		Element fieldsEl = (Element)fields.get(field + _ELEMENTS_SUFFIX);

		if (fieldsEl == null) {
			return null;
		}
		else {
			return fieldsEl;
		}
	}

	@Override
	public Map<String, String> getHints(String model, String field) {
		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return null;
		}
		else {
			return (Map<String, String>)fields.get(field + _HINTS_SUFFIX);
		}
	}

	@Override
	public int getMaxLength(String model, String field) {
		Map<String, String> hints = getHints(model, field);

		if (hints == null) {
			return Integer.MAX_VALUE;
		}

		int maxLength = GetterUtil.getInteger(
			ModelHintsConstants.TEXT_MAX_LENGTH);

		maxLength = GetterUtil.getInteger(hints.get("max-length"), maxLength);

		return maxLength;
	}

	@Override
	public List<String> getModels() {
		return ListUtil.fromCollection(_models);
	}

	@Override
	public Tuple getSanitizeTuple(String model, String field) {
		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return null;
		}
		else {
			return (Tuple)fields.get(field + _SANITIZE_SUFFIX);
		}
	}

	@Override
	public List<Tuple> getSanitizeTuples(String model) {
		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return Collections.emptyList();
		}

		List<Tuple> sanitizeTuples = new ArrayList<Tuple>();

		for (Map.Entry<String, Object> entry : fields.entrySet()) {
			String key = entry.getKey();

			if (key.endsWith(_SANITIZE_SUFFIX)) {
				Tuple sanitizeTuple = (Tuple)entry.getValue();

				sanitizeTuples.add(sanitizeTuple);
			}
		}

		return sanitizeTuples;
	}

	@Override
	public String getType(String model, String field) {
		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return null;
		}
		else {
			return (String)fields.get(field + _TYPE_SUFFIX);
		}
	}

	@Override
	public List<Tuple> getValidators(String model, String field) {
		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if ((fields == null) ||
			(fields.get(field + _VALIDATORS_SUFFIX) == null)) {

			return null;
		}
		else {
			return (List<Tuple>)fields.get(field + _VALIDATORS_SUFFIX);
		}
	}

	@Override
	public String getValue(
		String model, String field, String name, String defaultValue) {

		Map<String, String> hints = getHints(model, field);

		if (hints == null) {
			return defaultValue;
		}

		return GetterUtil.getString(hints.get(name), defaultValue);
	}

	@Override
	public boolean hasField(String model, String field) {
		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return false;
		}

		return fields.containsKey(field + _ELEMENTS_SUFFIX);
	}

	@Override
	public boolean isCustomValidator(String validatorName) {
		if (validatorName.equals("custom")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLocalized(String model, String field) {
		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return false;
		}

		Boolean localized = (Boolean)fields.get(field + _LOCALIZATION_SUFFIX);

		if (localized != null) {
			return localized;
		}
		else {
			return false;
		}
	}

	@Override
	public void read(ClassLoader classLoader, InputStream inputStream)
		throws Exception {

		read(classLoader, null, inputStream);
	}

	@Override
	public void read(ClassLoader classLoader, String source) throws Exception {
		read(classLoader, source, classLoader.getResourceAsStream(source));
	}

	public void read(
			ClassLoader classLoader, String source, InputStream inputStream)
		throws Exception {

		if (inputStream == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Cannot load " + source);
			}

			return;
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Loading " + source);
			}
		}

		Document document = _saxReader.read(inputStream);

		Element rootElement = document.getRootElement();

		List<Element> rootElements = rootElement.elements("hint-collection");

		for (Element hintCollectionElement : rootElements) {
			String name = hintCollectionElement.attributeValue("name");

			Map<String, String> hints = _hintCollections.get(name);

			if (hints == null) {
				hints = new HashMap<String, String>();

				_hintCollections.put(name, hints);
			}

			List<Element> hintElements = hintCollectionElement.elements("hint");

			for (Element hintElement : hintElements) {
				String hintName = hintElement.attributeValue("name");
				String hintValue = hintElement.getText();

				hints.put(hintName, hintValue);
			}
		}

		rootElements = rootElement.elements("model");

		for (Element modelElement : rootElements) {
			String name = modelElement.attributeValue("name");

			if (classLoader != ModelHintsImpl.class.getClassLoader()) {
				ClassNameLocalServiceUtil.getClassName(name);
			}

			Map<String, String> defaultHints = new HashMap<String, String>();

			_defaultHints.put(name, defaultHints);

			Element defaultHintsElement = modelElement.element("default-hints");

			if (defaultHintsElement != null) {
				List<Element> hintElements = defaultHintsElement.elements(
					"hint");

				for (Element hintElement : hintElements) {
					String hintName = hintElement.attributeValue("name");
					String hintValue = hintElement.getText();

					defaultHints.put(hintName, hintValue);
				}
			}

			Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
				name);

			if (fields == null) {
				fields = new LinkedHashMap<String, Object>();

				_modelFields.put(name, fields);
			}

			_models.add(name);

			List<Element> modelElements = modelElement.elements("field");

			for (Element fieldElement : modelElements) {
				String fieldName = fieldElement.attributeValue("name");
				String fieldType = fieldElement.attributeValue("type");
				boolean fieldLocalized = GetterUtil.getBoolean(
					fieldElement.attributeValue("localized"));

				Map<String, String> fieldHints = new HashMap<String, String>();

				fieldHints.putAll(defaultHints);

				List<Element> fieldElements = fieldElement.elements(
					"hint-collection");

				for (Element hintCollectionElement : fieldElements) {
					Map<String, String> hints = _hintCollections.get(
						hintCollectionElement.attributeValue("name"));

					fieldHints.putAll(hints);
				}

				fieldElements = fieldElement.elements("hint");

				for (Element hintElement : fieldElements) {
					String hintName = hintElement.attributeValue("name");
					String hintValue = hintElement.getText();

					fieldHints.put(hintName, hintValue);
				}

				Tuple fieldSanitize = null;

				Element sanitizeElement = fieldElement.element("sanitize");

				if (sanitizeElement != null) {
					String contentType = sanitizeElement.attributeValue(
						"content-type");
					String modes = sanitizeElement.attributeValue("modes");

					fieldSanitize = new Tuple(fieldName, contentType, modes);
				}

				Map<String, Tuple> fieldValidators =
					new TreeMap<String, Tuple>();

				fieldElements = fieldElement.elements("validator");

				for (Element validatorElement : fieldElements) {
					String validatorName = validatorElement.attributeValue(
						"name");

					if (Validator.isNull(validatorName)) {
						continue;
					}

					String validatorErrorMessage = GetterUtil.getString(
						validatorElement.attributeValue("error-message"));
					String validatorValue = GetterUtil.getString(
						validatorElement.getText());
					boolean customValidator = isCustomValidator(validatorName);

					if (customValidator) {
						validatorName = buildCustomValidatorName(validatorName);
					}

					Tuple fieldValidator = new Tuple(
						fieldName, validatorName, validatorErrorMessage,
						validatorValue, customValidator);

					fieldValidators.put(validatorName, fieldValidator);
				}

				fields.put(fieldName + _ELEMENTS_SUFFIX, fieldElement);
				fields.put(fieldName + _TYPE_SUFFIX, fieldType);
				fields.put(fieldName + _LOCALIZATION_SUFFIX, fieldLocalized);
				fields.put(fieldName + _HINTS_SUFFIX, fieldHints);

				if (fieldSanitize != null) {
					fields.put(fieldName + _SANITIZE_SUFFIX, fieldSanitize);
				}

				if (!fieldValidators.isEmpty()) {
					fields.put(
						fieldName + _VALIDATORS_SUFFIX,
						ListUtil.fromMapValues(fieldValidators));
				}
			}
		}
	}

	public void setSAXReader(SAXReader saxReader) {
		_saxReader = saxReader;
	}

	@Override
	public String trimString(String model, String field, String value) {
		if (value == null) {
			return value;
		}

		int maxLength = getMaxLength(model, field);

		if (value.length() > maxLength) {
			return value.substring(0, maxLength);
		}
		else {
			return value;
		}
	}

	private static final String _ELEMENTS_SUFFIX = "_ELEMENTS";

	private static final String _HINTS_SUFFIX = "_HINTS";

	private static final String _LOCALIZATION_SUFFIX = "_LOCALIZATION";

	private static final String _SANITIZE_SUFFIX = "_SANITIZE_SUFFIX";

	private static final String _TYPE_SUFFIX = "_TYPE";

	private static final String _VALIDATORS_SUFFIX = "_VALIDATORS";

	private static final Log _log = LogFactoryUtil.getLog(ModelHintsImpl.class);

	private Map<String, Map<String, String>> _defaultHints;
	private Map<String, Map<String, String>> _hintCollections;
	private Map<String, Object> _modelFields;
	private Set<String> _models;
	private SAXReader _saxReader;

}