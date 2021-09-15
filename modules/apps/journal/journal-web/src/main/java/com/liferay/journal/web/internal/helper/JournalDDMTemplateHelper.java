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

package com.liferay.journal.web.internal.helper;

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

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = JournalDDMTemplateHelper.class)
public class JournalDDMTemplateHelper {

	public String getDataContent(
		TemplateVariableDefinition templateVariableDefinition) {

		String dataContent = StringPool.BLANK;

		String dataType = templateVariableDefinition.getDataType();

		if (templateVariableDefinition.isCollection()) {
			TemplateVariableDefinition itemTemplateVariableDefinition =
				templateVariableDefinition.getItemTemplateVariableDefinition();

			dataContent = _getListCode(
				templateVariableDefinition.getName(),
				itemTemplateVariableDefinition.getName(),
				itemTemplateVariableDefinition.getAccessor());
		}
		else if (Validator.isNull(dataType)) {
			dataContent = _getVariableReferenceCode(
				templateVariableDefinition.getName(),
				templateVariableDefinition.getAccessor());
		}
		else if (dataType.equals("service-locator")) {
			Class<?> templateVariableDefinitionClass =
				templateVariableDefinition.getClazz();

			String variableName =
				templateVariableDefinitionClass.getSimpleName();

			dataContent = StringBundler.concat(
				_getVariableAssignmentCode(
					variableName,
					"serviceLocator.findService(\"" +
						templateVariableDefinition.getName() + "\")"),
				"[$CURSOR$]", _getVariableReferenceCode(variableName, null));
		}
		else {
			try {
				String[] generateCode = templateVariableDefinition.generateCode(
					TemplateConstants.LANG_TYPE_FTL);

				dataContent = generateCode[0];
			}
			catch (Exception exception) {
				_log.error(exception, exception);
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

	private String _getListCode(
		String variableName, String itemName, String accessor) {

		return StringBundler.concat(
			"<#if ", variableName, "?has_content>\n\t<#list ", variableName,
			" as ", itemName, ">\n\t\t",
			_getVariableReferenceCode(itemName, accessor),
			"[$CURSOR$]\n\t</#list>\n</#if>");
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
		String variableName, String variableValue) {

		return StringBundler.concat(
			"<#assign ", variableName, " = ", variableValue, ">");
	}

	private String _getVariableReferenceCode(
		String variableName, String accessor) {

		String methodInvocation = StringPool.BLANK;

		if (Validator.isNotNull(accessor)) {
			methodInvocation = StringPool.PERIOD + accessor;
		}

		return StringBundler.concat("${", variableName, methodInvocation, "}");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalDDMTemplateHelper.class);

}