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

package com.liferay.dynamic.data.mapping.form.field.type.internal.validation;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.validation.DDMValidation;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.VALIDATION,
	service = {
		DDMFormFieldTemplateContextContributor.class,
		ValidationDDMFormFieldTemplateContextContributor.class
	}
)
public class ValidationDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"validations",
			_getValidations(ddmFormFieldRenderingContext.getLocale())
		).put(
			"value", getValue(ddmFormFieldRenderingContext)
		).build();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, DDMValidation.class, "ddm.validation.data.type");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected Map<String, Object> getValue(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		String valueString = ddmFormFieldRenderingContext.getValue();

		if (Validator.isNotNull(valueString)) {
			try {
				JSONObject valueJSONObject = jsonFactory.createJSONObject(
					valueString);

				return HashMapBuilder.<String, Object>put(
					"errorMessage",
					valueJSONObject.getJSONObject("errorMessage")
				).put(
					"expression", valueJSONObject.getJSONObject("expression")
				).put(
					"parameter", valueJSONObject.getJSONObject("parameter")
				).build();
			}
			catch (JSONException jsonException) {
				if (_log.isWarnEnabled()) {
					_log.warn(jsonException, jsonException);
				}
			}
		}

		return HashMapBuilder.<String, Object>put(
			"errorMessage", jsonFactory.createJSONObject()
		).put(
			"expression", jsonFactory.createJSONObject()
		).put(
			"parameter", jsonFactory.createJSONObject()
		).build();
	}

	@Reference
	protected JSONFactory jsonFactory;

	private HashMap<String, Object> _getValidations(Locale locale) {
		HashMap<String, Object> map = new HashMap<>();

		Set<String> keySet = _serviceTrackerMap.keySet();

		for (String key : keySet) {
			List<DDMValidation> ddmValidations = _serviceTrackerMap.getService(
				key);

			Stream<DDMValidation> stream = ddmValidations.stream();

			map.put(
				key,
				stream.map(
					ddmValidation -> HashMapBuilder.put(
						"label", ddmValidation.getLabel(locale)
					).put(
						"name", ddmValidation.getName()
					).put(
						"parameterMessage",
						ddmValidation.getParameterMessage(locale)
					).put(
						"regex", ddmValidation.getRegex()
					).put(
						"template", ddmValidation.getTemplate()
					).build()
				).toArray());
		}

		return map;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ValidationDDMFormFieldTemplateContextContributor.class);

	private ServiceTrackerMap<String, List<DDMValidation>> _serviceTrackerMap;

}