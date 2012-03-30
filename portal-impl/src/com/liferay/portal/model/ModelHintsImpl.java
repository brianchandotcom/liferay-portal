/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.util.PwdGenerator;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
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

			for (int i = 0; i < configs.length; i++) {
				read(classLoader, configs[i]);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public String buildCustomValidatorName(String validatorName) {
		return validatorName.concat(StringPool.UNDERLINE).concat(
			PwdGenerator.getPassword(PwdGenerator.KEY3, 4));
	}

	public Map<String, String> getDefaultHints(String model) {
		return _defaultHints.get(model);
	}

	public com.liferay.portal.kernel.xml.Element getFieldsEl(
		String model, String field) {

		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return null;
		}
		else {
			Element fieldsEl = (Element)fields.get(field + _ELEMENTS_SUFFIX);

			if (fieldsEl == null) {
				return null;
			}
			else {
				return fieldsEl;
			}
		}
	}

	public Map<String, String> getHints(String model, String field) {
		Map<String, Object> fields =
			(Map<String, Object>)_modelFields.get(model);

		if (fields == null) {
			return null;
		}
		else {
			return (Map<String, String>)fields.get(field + _HINTS_SUFFIX);
		}
	}

	public List<String> getModels() {
		return ListUtil.fromCollection(_models);
	}

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

	public List<Tuple> getSanitizeTuples(String model) {
		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return Collections.emptyList();
		}
		else {
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
	}

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

	public boolean isCustomValidator(String validatorName) {
		if (validatorName.equals("custom")) {
			return true;
		}

		return false;
	}

	public boolean isLocalized(String model, String field) {
		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return false;
		}
		else {
			Boolean localized = (Boolean)fields.get(
				field + _LOCALIZATION_SUFFIX);

			if (localized != null) {
				return localized;
			}
			else {
				return false;
			}
		}
	}

	public void read(ClassLoader classLoader, String source) throws Exception {
		InputStream is = classLoader.getResourceAsStream(source);

		if (is == null) {
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

		Document doc = _saxReader.read(is);

		Element root = doc.getRootElement();

		List<Element> rootElements = root.elements("hint-collection");

		for (Element hintCollection : rootElements) {
			String name = hintCollection.attributeValue("name");

			Map<String, String> hints = _hintCollections.get(name);

			if (hints == null) {
				hints = new HashMap<String, String>();

				_hintCollections.put(name, hints);
			}

			List<Element> hintElements = hintCollection.elements("hint");

			for (Element hint : hintElements) {
				String hintName = hint.attributeValue("name");
				String hintValue = hint.getText();

				hints.put(hintName, hintValue);
			}
		}

		rootElements = root.elements("model");

		for (Element model : rootElements) {
			String name = model.attributeValue("name");

			if (classLoader != ModelHintsImpl.class.getClassLoader()) {
				ClassNameLocalServiceUtil.getClassName(name);
			}

			Map<String, String> defaultHints = new HashMap<String, String>();

			_defaultHints.put(name, defaultHints);

			Element defaultHintsEl = model.element("default-hints");

			if (defaultHintsEl != null) {
				List<Element> hintElements = defaultHintsEl.elements("hint");

				for (Element hint : hintElements) {
					String hintName = hint.attributeValue("name");
					String hintValue = hint.getText();

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

			List<Element> modelElements = model.elements("field");

			for (Element field : modelElements) {
				String fieldName = field.attributeValue("name");
				String fieldType = field.attributeValue("type");
				boolean fieldLocalized = GetterUtil.getBoolean(
					field.attributeValue("localized"));

				Map<String, String> fieldHints = new HashMap<String, String>();

				fieldHints.putAll(defaultHints);

				List<Element> fieldElements = field.elements("hint-collection");

				for (Element hintCollection : fieldElements) {
					Map<String, String> hints = _hintCollections.get(
						hintCollection.attributeValue("name"));

					fieldHints.putAll(hints);
				}

				fieldElements = field.elements("hint");

				for (Element hint : fieldElements) {
					String hintName = hint.attributeValue("name");
					String hintValue = hint.getText();

					fieldHints.put(hintName, hintValue);
				}

				Tuple fieldSanitize = null;

				Element sanitize = field.element("sanitize");

				if (sanitize != null) {
					String contentType = sanitize.attributeValue(
						"content-type");
					String modes = sanitize.attributeValue("modes");

					fieldSanitize = new Tuple(fieldName, contentType, modes);
				}

				Map<String, Tuple> fieldValidators =
					new TreeMap<String, Tuple>();

				fieldElements = field.elements("validator");

				for (Element validator : fieldElements) {
					String validatorName = validator.attributeValue("name");

					if (Validator.isNull(validatorName)) {
						continue;
					}

					String validatorErrorMessage = GetterUtil.getString(
						validator.attributeValue("error-message"));
					String validatorValue = GetterUtil.getString(
						validator.getText());
					boolean customValidator = isCustomValidator(validatorName);

					if (customValidator) {
						validatorName = buildCustomValidatorName(validatorName);
					}

					Tuple fieldValidator = new Tuple(
						fieldName, validatorName, validatorErrorMessage,
						validatorValue, customValidator);

					fieldValidators.put(validatorName, fieldValidator);
				}

				fields.put(fieldName + _ELEMENTS_SUFFIX, field);
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

	public String trimString(String model, String field, String value) {
		if (value == null) {
			return value;
		}

		Map<String, String> hints = getHints(model, field);

		if (hints == null) {
			return value;
		}

		int maxLength = GetterUtil.getInteger(
			ModelHintsConstants.TEXT_MAX_LENGTH);

		maxLength = GetterUtil.getInteger(hints.get("max-length"), maxLength);

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

	private static Log _log = LogFactoryUtil.getLog(ModelHintsImpl.class);

	private Map<String, Map<String, String>> _defaultHints;
	private Map<String, Map<String, String>> _hintCollections;
	private Map<String, Object> _modelFields;
	private Set<String> _models;
	private SAXReader _saxReader;

}