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

package com.liferay.journal.web.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateVariableDefinition;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = JournalDDMTemplateUtil.class)
public class JournalDDMTemplateUtil {

	public String getDataContent(
		TemplateVariableDefinition templateVariableDefinition,
		String language) {

		String dataContent = StringPool.BLANK;

		String dataType = templateVariableDefinition.getDataType();

		if (templateVariableDefinition.isCollection()) {
			TemplateVariableDefinition itemTemplateVariableDefinition =
				templateVariableDefinition.getItemTemplateVariableDefinition();

			dataContent = _getListCode(
				templateVariableDefinition.getName(),
				itemTemplateVariableDefinition.getName(),
				itemTemplateVariableDefinition.getAccessor(), language);
		}
		else if (Validator.isNull(dataType)) {
			dataContent = _getVariableReferenceCode(
				templateVariableDefinition.getName(),
				templateVariableDefinition.getAccessor(), language);
		}
		else if (dataType.equals("service-locator")) {
			Class<?> templateVariableDefinitionClass =
				templateVariableDefinition.getClazz();

			String variableName =
				templateVariableDefinitionClass.getSimpleName();

			StringBundler sb = new StringBundler(3);

			sb.append(
				_getVariableAssignmentCode(
					variableName,
					"serviceLocator.findService(\"" +
						templateVariableDefinition.getName() + "\")",
					language));
			sb.append("[$CURSOR$]");
			sb.append(_getVariableReferenceCode(variableName, null, language));

			dataContent = sb.toString();
		}
		else {
			try {
				String[] generateCode = templateVariableDefinition.generateCode(
					language);

				dataContent = generateCode[0];
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return dataContent;
	}

	public String getPaletteItemTitle(
		HttpServletRequest httpServletRequest, ResourceBundle resourceBundle,
		TemplateVariableDefinition templateVariableDefinition) {

		StringBundler sb = new StringBundler(12);

		String help = templateVariableDefinition.getHelp();

		if (Validator.isNotNull(help)) {
			sb.append("<p>");
			sb.append(
				HtmlUtil.escape(
					LanguageUtil.get(
						httpServletRequest, resourceBundle, help)));
			sb.append("</p>");
		}

		if (templateVariableDefinition.isCollection()) {
			sb.append("<p><i>*");
			sb.append(
				LanguageUtil.get(
					httpServletRequest, "this-is-a-collection-of-fields"));
			sb.append("</i></p>");
		}
		else if (templateVariableDefinition.isRepeatable()) {
			sb.append("<p><i>*");
			sb.append(
				LanguageUtil.get(
					httpServletRequest, "this-is-a-repeatable-field"));
			sb.append("</i></p>");
		}

		if (!Objects.equals(
				templateVariableDefinition.getDataType(), "service-locator")) {

			sb.append(LanguageUtil.get(httpServletRequest, "variable"));
			sb.append(StringPool.COLON);
			sb.append(StringPool.NBSP);
			sb.append(HtmlUtil.escape(templateVariableDefinition.getName()));
		}

		sb.append(
			_getPaletteItemTitle(
				httpServletRequest, "class",
				templateVariableDefinition.getClazz()));

		if (templateVariableDefinition.isCollection()) {
			TemplateVariableDefinition itemTemplateVariableDefinition =
				templateVariableDefinition.getItemTemplateVariableDefinition();

			sb.append(
				_getPaletteItemTitle(
					httpServletRequest, "items-class",
					itemTemplateVariableDefinition.getClazz()));
		}

		return sb.toString();
	}

	private String _getAccessor(String accessor, String language) {
		if (StringUtil.equalsIgnoreCase(
				language, TemplateConstants.LANG_TYPE_VM)) {

			if (!accessor.contains(StringPool.OPEN_PARENTHESIS)) {
				return accessor;
			}

			StringTokenizer st = new StringTokenizer(accessor, "(,");

			StringBundler sb = new StringBundler(st.countTokens() * 2);

			sb.append(st.nextToken());
			sb.append(StringPool.OPEN_PARENTHESIS);

			while (st.hasMoreTokens()) {
				sb.append(StringPool.DOLLAR);
				sb.append(st.nextToken());
			}

			accessor = sb.toString();
		}

		return accessor;
	}

	private String _getListCode(
		String variableName, String itemName, String accessor,
		String language) {

		if (StringUtil.equalsIgnoreCase(
				language, TemplateConstants.LANG_TYPE_FTL)) {

			StringBundler sb = new StringBundler(9);

			sb.append("<#if ");
			sb.append(variableName);
			sb.append("?has_content>\n\t<#list ");
			sb.append(variableName);
			sb.append(" as ");
			sb.append(itemName);
			sb.append(">\n\t\t");
			sb.append(_getVariableReferenceCode(itemName, accessor, language));
			sb.append("[$CURSOR$]\n\t</#list>\n</#if>");

			return sb.toString();
		}
		else if (StringUtil.equalsIgnoreCase(
					language, TemplateConstants.LANG_TYPE_VM)) {

			StringBundler sb = new StringBundler(9);

			sb.append("#if (!$");
			sb.append(variableName);
			sb.append(".isEmpty())\n\t#foreach ($");
			sb.append(itemName);
			sb.append(" in $");
			sb.append(variableName);
			sb.append(")\n\t\t");
			sb.append(_getVariableReferenceCode(itemName, accessor, language));
			sb.append("[$CURSOR$]#end\n#end");

			return sb.toString();
		}

		return StringPool.BLANK;
	}

	private String _getPaletteItemTitle(
		HttpServletRequest httpServletRequest, String label, Class<?> clazz) {

		if (clazz == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(10);

		String className = clazz.getName();

		sb.append("<br />");
		sb.append(LanguageUtil.get(httpServletRequest, label));
		sb.append(StringPool.COLON);
		sb.append(StringPool.NBSP);

		String javadocURL = null;

		if (className.startsWith("com.liferay.portal.kernel")) {
			javadocURL =
				"http://docs.liferay.com/portal/7.0/javadocs/portal-kernel/";
		}

		if (Validator.isNotNull(javadocURL)) {
			sb.append("<a href=\"");
			sb.append(javadocURL);
			sb.append(
				StringUtil.replace(className, CharPool.PERIOD, CharPool.SLASH));
			sb.append(".html\" target=\"_blank\">");
		}

		sb.append(clazz.getSimpleName());

		if (Validator.isNull(javadocURL)) {
			sb.append("</a>");
		}

		return sb.toString();
	}

	private String _getVariableAssignmentCode(
		String variableName, String variableValue, String language) {

		if (StringUtil.equalsIgnoreCase(
				language, TemplateConstants.LANG_TYPE_FTL)) {

			return StringBundler.concat(
				"<#assign ", variableName, " = ", variableValue, ">");
		}
		else if (StringUtil.equalsIgnoreCase(
					language, TemplateConstants.LANG_TYPE_VM)) {

			if (!variableValue.startsWith(StringPool.DOUBLE_QUOTE) &&
				!variableValue.startsWith(StringPool.OPEN_BRACKET) &&
				!variableValue.startsWith(StringPool.OPEN_CURLY_BRACE) &&
				!variableValue.startsWith(StringPool.QUOTE) &&
				!Validator.isNumber(variableValue)) {

				variableValue = StringPool.DOLLAR + variableValue;
			}

			return StringBundler.concat(
				"#set ($", variableName, " = ", variableValue, ")");
		}

		return variableName;
	}

	private String _getVariableReferenceCode(
		String variableName, String accessor, String language) {

		String methodInvocation = StringPool.BLANK;

		if (Validator.isNotNull(accessor)) {
			methodInvocation =
				StringPool.PERIOD + _getAccessor(accessor, language);
		}

		if (StringUtil.equalsIgnoreCase(
				language, TemplateConstants.LANG_TYPE_FTL)) {

			return StringBundler.concat(
				"${", variableName, methodInvocation, "}");
		}
		else if (StringUtil.equalsIgnoreCase(
					language, TemplateConstants.LANG_TYPE_VM)) {

			return StringPool.DOLLAR + variableName + methodInvocation;
		}

		return variableName;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalDDMTemplateUtil.class);

}