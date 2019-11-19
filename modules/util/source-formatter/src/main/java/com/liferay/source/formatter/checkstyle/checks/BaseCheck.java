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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.SourceFormatterCheckUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hugo Huijser
 */
public abstract class BaseCheck extends AbstractCheck {

	@Override
	public int[] getAcceptableTokens() {
		return getDefaultTokens();
	}

	@Override
	public int[] getRequiredTokens() {
		return getDefaultTokens();
	}

	public void setAttributes(String attributes) throws JSONException {
		_attributesJSONObject = new JSONObjectImpl(attributes);
	}

	public void setExcludes(String excludes) throws JSONException {
		_excludesJSONObject = new JSONObjectImpl(excludes);
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		if (!isAttributeValue(SourceFormatterCheckUtil.ENABLED_KEY, true)) {
			return;
		}

		if (!isAttributeValue(CheckstyleUtil.SHOW_DEBUG_INFORMATION_KEY)) {
			doVisitToken(detailAST);

			return;
		}

		long startTime = System.currentTimeMillis();

		doVisitToken(detailAST);

		long endTime = System.currentTimeMillis();

		Class<?> clazz = getClass();

		DebugUtil.increaseProcessingTime(
			clazz.getSimpleName(), endTime - startTime);
	}

	protected abstract void doVisitToken(DetailAST detailAST);

	protected String getAbsolutePath() {
		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		return SourceUtil.getAbsolutePath(fileName);
	}

	protected String getAttributeValue(String attributeKey) {
		return getAttributeValue(attributeKey, StringPool.BLANK);
	}

	protected String getAttributeValue(
		String attributeKey, String defaultValue) {

		return SourceFormatterCheckUtil.getJSONObjectValue(
			_attributesJSONObject, _attributeValueMap, attributeKey,
			defaultValue, getAbsolutePath(), getBaseDirName());
	}

	protected List<String> getAttributeValues(String attributeKey) {
		return SourceFormatterCheckUtil.getJSONObjectValues(
			_attributesJSONObject, attributeKey, _attributeValuesMap,
			getAbsolutePath(), getBaseDirName());
	}

	protected String getBaseDirName() {
		return SourceFormatterCheckUtil.getJSONObjectValue(
			_attributesJSONObject, _attributeValueMap,
			CheckstyleUtil.BASE_DIR_NAME_KEY, StringPool.BLANK, null, null,
			true);
	}

	protected boolean isAttributeValue(String attributeKey) {
		return GetterUtil.getBoolean(getAttributeValue(attributeKey));
	}

	protected boolean isAttributeValue(
		String attributeKey, boolean defaultValue) {

		String attributeValue = getAttributeValue(attributeKey);

		if (Validator.isNull(attributeValue)) {
			return defaultValue;
		}

		return GetterUtil.getBoolean(attributeValue);
	}

	protected boolean isExcludedPath(String key) {
		return SourceFormatterCheckUtil.isExcludedPath(
			_excludesJSONObject, _excludesValuesMap, key, getAbsolutePath(), -1,
			null, getBaseDirName());
	}

	protected static final int[] ARITHMETIC_OPERATOR_TOKEN_TYPES = {
		TokenTypes.DIV, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.PLUS,
		TokenTypes.STAR
	};

	protected static final int[] RELATIONAL_OPERATOR_TOKEN_TYPES = {
		TokenTypes.EQUAL, TokenTypes.GE, TokenTypes.GT, TokenTypes.LE,
		TokenTypes.LT, TokenTypes.NOT_EQUAL
	};

	private JSONObject _attributesJSONObject = new JSONObjectImpl();
	private final Map<String, String> _attributeValueMap =
		new ConcurrentHashMap<>();
	private final Map<String, List<String>> _attributeValuesMap =
		new ConcurrentHashMap<>();
	private JSONObject _excludesJSONObject = new JSONObjectImpl();
	private final Map<String, List<String>> _excludesValuesMap =
		new ConcurrentHashMap<>();

}