/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.template.variable;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.blueprint.template.variable.SXPBlueprintTemplateVariableParser;
import com.liferay.search.experiences.internal.blueprint.parameter.visitor.ToSubstitutionStringVisitor;
import com.liferay.search.experiences.internal.problem.ProblemUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPBlueprintTemplateVariableParser.class)
public class SXPTemplateVariableParserImpl
	implements SXPBlueprintTemplateVariableParser {

	@Override
	public Optional<Object> parse(
		String className, Object object, SXPParameterData sxpParameterData) {

		if (Objects.isNull(object)) {
			return Optional.empty();
		}

		Set<SXPParameter> sxpParameters = sxpParameterData.getSXPParameters();

		if (sxpParameters.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug("No SXPParameters available");
			}

			return Optional.of(object);
		}

		try {
			if (object instanceof JSONObject) {
				return Optional.ofNullable(
					_parseJSONObject(
						className, (JSONObject)object, sxpParameterData));
			}
			else if (object instanceof JSONArray) {
				return Optional.ofNullable(
					_parseJSONArray(
						className, (JSONArray)object, sxpParameterData));
			}
		}
		catch (JSONException jsonException) {
			
			_handleException(className, jsonException, object);

			return Optional.empty();
		}

		return Optional.of(object);
	}

	@Override
	public Optional<JSONArray> parseJSONArray(
		String className, JSONArray jsonArray,
		SXPParameterData sxpParameterData) {

		if (jsonArray == null) {
			return Optional.empty();
		}

		Set<SXPParameter> sxpParameters = sxpParameterData.getSXPParameters();

		if (sxpParameters.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug("No SXPParameters available");
			}

			return Optional.of(jsonArray);
		}

		try {
			return Optional.ofNullable(
				_parseJSONArray(className, jsonArray, sxpParameterData));
		}
		catch (JSONException jsonException) {
			_handleException(className, jsonException, jsonArray);
		}

		return Optional.empty();
	}

	@Override
	public Optional<JSONObject> parseJSONObject(
		String className, JSONObject jsonObject,
		SXPParameterData sxpParameterData) {

		if (jsonObject == null) {
			return Optional.empty();
		}

		Set<SXPParameter> sxpParameters = sxpParameterData.getSXPParameters();

		if (sxpParameters.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug("No sxpParameters available");
			}

			return Optional.of(jsonObject);
		}

		try {
			return Optional.ofNullable(
				_parseJSONObject(className, jsonObject, sxpParameterData));
		}
		catch (JSONException jsonException) {
			_handleException(className, jsonException, jsonObject);
		}

		return Optional.empty();
	}

	private Map<String, String> _getParameterOptions(String optionsString)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		if (Validator.isBlank(optionsString)) {
			return map;
		}

		String[] arr = optionsString.split(",");

		for (String str : arr) {
			String[] optionArr = str.split("=");

			if (optionArr.length == 1) {
				map.put(optionArr[0], null);
			}
			else {
				map.put(optionArr[0], optionArr[1]);
			}
		}

		return map;
	}

	private String _getParametrizedVariableStem(String str) {
		StringBundler sb = new StringBundler(2);

		sb.append(str.substring(0, str.length() - 1));
		sb.append("|");

		return sb.toString();
	}

	private void _handleException(
		String className, Exception exception, Object rootObject) {
		
		_log.error(exception.getMessage(), exception);

		ProblemUtil.addError(
			className, "error-in-parsing-template-variables", rootObject, null,
			null, exception);
	}

	private boolean _hasTemplateVariables(String str) {
		if (str.indexOf("${") > 0) {
			return true;
		}

		return false;
	}

	private JSONArray _parseJSONArray(
			String className, JSONArray jsonArray,
			SXPParameterData sxpParameterData)
		throws JSONException {

		String json = jsonArray.toString();

		if (!_hasTemplateVariables(json)) {
			return jsonArray;
		}

		String parsed = _parseString(className, json, sxpParameterData);

		if (!Validator.isBlank(parsed)) {
			return _jsonFactory.createJSONArray(parsed);
		}

		return null;
	}

	private JSONObject _parseJSONObject(
			String className, JSONObject jsonObject,
			SXPParameterData sxpParameterData)
		throws JSONException {

		String jsonString = jsonObject.toString();

		if (!_hasTemplateVariables(jsonString)) {
			return jsonObject;
		}

		String parsedString = _parseString(
			className, jsonString, sxpParameterData);

		if (!Validator.isBlank(parsedString)) {
			return _jsonFactory.createJSONObject(parsedString);
		}

		return null;
	}

	private String _parseString(
		String className, String str, SXPParameterData sxpParameterData) {

		try {
			ToSubstitutionStringVisitor toStringVisitor =
				new ToSubstitutionStringVisitor();

			for (SXPParameter sxpParameter : sxpParameterData.getSXPParameters()) {
				String templateVariable = sxpParameter.getTemplateVariable();

				if (Validator.isNull(templateVariable)) {
					continue;
				}

				String stem = _getParametrizedVariableStem(templateVariable);

				if (str.contains(stem)) {
					str = _processParametrizedTemplateVariables(
						str, sxpParameter, toStringVisitor, stem);
				}

				if (str.contains(templateVariable)) {
					str = _processTemplateVariables(
						str, sxpParameter, toStringVisitor);
				}

				if (!_hasTemplateVariables(str)) {
					break;
				}
			}

			if (!_validateResults(className, str)) {
				return null;
			}

			return str;
		}
		catch (Exception exception) {
			_handleException(className, exception, str);
		}

		return null;
	}

	private String _processParametrizedTemplateVariable(
			String str, SXPParameter sxpParameter,
			ToSubstitutionStringVisitor toStringVisitor,
			String templateVariable, int from)
		throws Exception {

		int end = str.indexOf("}", from);

		String optionsString = str.substring(
			from + templateVariable.length(), end);

		StringBuilder sb = new StringBuilder();

		sb.append(templateVariable);
		sb.append(optionsString);
		sb.append("}");

		String substitution = sxpParameter.accept(
			toStringVisitor, _getParameterOptions(optionsString));

		if (substitution.startsWith("[")) {
			return _replaceArrayValue(str, sb.toString(), substitution);
		}

		return StringUtil.replace(str, sb.toString(), substitution);
	}

	private String _processParametrizedTemplateVariables(
			String str, SXPParameter sxpParameter,
			ToSubstitutionStringVisitor toStringVisitor,
			String templateVariable)
		throws Exception {

		// Only date type can currently be parametrized
		
		if (!DateSXPParameter.class.isAssignableFrom(sxpParameter.getClass())) {
			return str;
		}

		int from = str.indexOf(templateVariable);

		while (from >= 0) {
			str = _processParametrizedTemplateVariable(
				str, sxpParameter, toStringVisitor, templateVariable, from);

			from = str.indexOf(templateVariable, from);
		}

		return str;
	}

	private String _processTemplateVariables(
			String str, SXPParameter sxpParameter,
			ToSubstitutionStringVisitor toStringVisitor)
		throws Exception {

		String templateVariable = sxpParameter.getTemplateVariable();

		String substitution = sxpParameter.accept(toStringVisitor, null);

		if (substitution.startsWith("[")) {
			return _replaceArrayValue(str, templateVariable, substitution);
		}

		return StringUtil.replace(str, templateVariable, substitution);
	}

	private String _replaceArrayValue(
		String str, String templateVariable, String substitution) {

		StringBundler sb = new StringBundler(3);

		sb.append("\"");
		sb.append(templateVariable);
		sb.append("\"");

		return StringUtil.replace(str, sb.toString(), substitution);
	}

	private boolean _validateResults(String className, String str) {
		if (str.contains("${")) {

			if (_log.isWarnEnabled()) {
				_log.warn("Unable to parse all template variables in " + str);
			}

			ProblemUtil.addWarning(
				className, "unable-to-parse-all-template-variables",
				"Unable to parse all template variables", str, null, null);

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPTemplateVariableParserImpl.class);

	@Reference
	private JSONFactory _jsonFactory;

}