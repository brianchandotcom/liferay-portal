package com.liferay.dynamic.data.mapping.type.text.internal;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
public class TextDDMFormFieldContextHelper {

	public TextDDMFormFieldContextHelper(
		JSONFactory jsonFactory, DDMFormFieldOptions ddmFormFieldOptions,
		String value, LocalizedValue predefinedValue, Locale locale) {

			_jsonFactory = jsonFactory;
			_ddmFormFieldOptions = ddmFormFieldOptions;
			_locale = locale;
		}

		public List<Object> getOptions() {
			List<Object> options = new ArrayList<>();

			for (String optionValue : _ddmFormFieldOptions.getOptionsValues()) {
				Map<String, String> optionMap = new HashMap<>();

				LocalizedValue optionLabel =
					_ddmFormFieldOptions.getOptionLabels(optionValue);

				optionMap.put("label", optionLabel.getString(_locale));
				optionMap.put("value", optionValue);

				options.add(optionMap);
			}

			return options;
		}

		protected String[] toStringArray(String value) {
			if (Validator.isNull(value)) {
				return GetterUtil.DEFAULT_STRING_VALUES;
			}

			try {
				JSONArray jsonArray = _jsonFactory.createJSONArray(value);

				return ArrayUtil.toStringArray(jsonArray);
			}
			catch (JSONException jsone) {
				return StringUtil.split(value);
			}
		}

		private final DDMFormFieldOptions _ddmFormFieldOptions;
		private final JSONFactory _jsonFactory;
		private final Locale _locale;

}