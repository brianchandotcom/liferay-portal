<%--
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
--%>

<%@ include file="/html/taglib/aui/input/init.jsp" %>

<c:if test='<%= !type.equals("hidden") && !type.equals("assetCategories") %>'>
	<span class="<%= fieldCss %>">
		<span class="aui-field-content">
			<c:if test='<%= Validator.isNotNull(label) && !inlineLabel.equals("right") %>'>
				<label <%= labelTag %>>
					<liferay-ui:message key="<%= label %>" />

					<c:if test="<%= required && showRequiredLabel %>">
						<span class="aui-label-required">(<liferay-ui:message key="required" />)</span>
					</c:if>

					<c:if test="<%= Validator.isNotNull(helpMessage) %>">
						<liferay-ui:icon-help message="<%= helpMessage %>" />
					</c:if>

					<c:if test="<%= changesContext %>">
						<span class="aui-helper-hidden-accessible"><liferay-ui:message key="changing-the-value-of-this-field-will-reload-the-page" />)</span>
					</c:if>
				</label>
			</c:if>

			<c:if test="<%= Validator.isNotNull(prefix) %>">
				<span class="aui-prefix"><liferay-ui:message key="<%= prefix %>" /></span>
			</c:if>

			<span class="aui-field-element <%= Validator.isNotNull(label) && inlineLabel.equals("right") ? "aui-field-label-right" : (inlineLabel.equals("left") ? "aui-field-label-left" : StringPool.BLANK) %>">
</c:if>

<c:choose>
	<c:when test='<%= (model != null) && type.equals("assetCategories") %>'>
		<liferay-ui:asset-categories-selector
			className="<%= model.getName() %>"
			classPK="<%= _getClassPK(bean, classPK) %>"
			contentCallback='<%= portletResponse.getNamespace() + "getSuggestionsContent" %>'
		/>
	</c:when>
	<c:when test='<%= (model != null) && type.equals("assetTags") %>'>
		<liferay-ui:asset-tags-selector
			className="<%= model.getName() %>"
			classPK="<%= _getClassPK(bean, classPK) %>"
			contentCallback='<%= portletResponse.getNamespace() + "getSuggestionsContent" %>'
			id="<%= namespace + id %>"
		/>
	</c:when>
	<c:when test="<%= (model != null) && Validator.isNull(type) %>">
		<liferay-ui:input-field
			bean="<%= bean %>"
			cssClass="<%= inputCss %>"
			defaultLanguageId="<%= defaultLanguageId %>"
			defaultValue="<%= value %>"
			disabled="<%= disabled %>"
			field="<%= field %>"
			fieldParam='<%= fieldParam %>'
			formName="<%= formName %>"
			format='<%= (Format)dynamicAttributes.get("format") %>'
			id="<%= id %>"
			ignoreRequestValue="<%= ignoreRequestValue %>"
			languageId="<%= languageId %>"
			model="<%= model %>"
			placeholder="<%= placeholder %>"
		/>
	</c:when>
	<c:when test='<%= type.equals("checkbox") %>'>

		<%
		String valueString = String.valueOf(checked);

		if (value != null) {
			valueString = value.toString();
		}

		if (!ignoreRequestValue) {
			valueString = ParamUtil.getString(request, name, valueString);
		}

		if (valueString.equalsIgnoreCase("false") || valueString.equalsIgnoreCase("true")) {
			checked = GetterUtil.getBoolean(valueString);
		}

		String defaultValueString = Boolean.TRUE.toString();

		if (Validator.isNotNull(valueString) && !valueString.equalsIgnoreCase("false") && !valueString.equalsIgnoreCase("true")) {
			defaultValueString = valueString;
		}
		%>

		<input id="<%= namespace + id %>" name="<%= namespace + name %>" type="hidden" value="<%= HtmlUtil.escapeAttribute(valueString) %>" />

		<input <%= checked ? "checked" : StringPool.BLANK %> class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= namespace + id %>Checkbox" name="<%= namespace + name %>Checkbox" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> onClick="Liferay.Util.updateCheckboxValue(this); <%= onClick %>" <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> type="checkbox" value="<%= HtmlUtil.escapeAttribute(defaultValueString) %>" <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
	</c:when>
	<c:when test='<%= type.equals("radio") %>'>

		<%
		String valueString = String.valueOf(checked);

		if (value != null) {
			valueString = value.toString();
		}

		if (!ignoreRequestValue) {
			String requestValue = ParamUtil.getString(request, name);

			if (Validator.isNotNull(requestValue)) {
				checked = valueString.equals(requestValue);
			}
		}
		%>

		<input <%= checked ? "checked" : StringPool.BLANK %> class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= namespace + id %>" name="<%= namespace + name %>" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> type="radio" value="<%= valueString %>" <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
	</c:when>
	<c:when test='<%= type.equals("timeZone") %>'>
		<span class="<%= fieldCss %>">
			<span class="aui-field-content">

				<%
				int displayStyle = TimeZone.LONG;

				if (dynamicAttributes.get("displayStyle") != null) {
					displayStyle = GetterUtil.getInteger((String)dynamicAttributes.get("displayStyle"));
				}
				%>

				<liferay-ui:input-time-zone
					daylight='<%= GetterUtil.getBoolean((String)dynamicAttributes.get("daylight")) %>'
					disabled="<%= disabled %>"
					displayStyle="<%= displayStyle %>"
					name="<%= name %>"
					nullable='<%= GetterUtil.getBoolean((String)dynamicAttributes.get("nullable")) %>'
					value="<%= value.toString() %>"
				/>
			</span>
		</span>
	</c:when>
	<c:otherwise>

		<%
		String valueString = StringPool.BLANK;

		if (value != null) {
			valueString = value.toString();
		}

		if (type.equals("hidden") && (value == null)) {
			valueString = BeanPropertiesUtil.getStringSilent(bean, name);
		}
		else if (!ignoreRequestValue && (Validator.isNull(type) || type.equals("text") || type.equals("textarea"))) {
			valueString = BeanParamUtil.getStringSilent(bean, request, name, valueString);

			if (Validator.isNotNull(fieldParam)) {
				valueString = ParamUtil.getString(request, fieldParam, valueString);
			}
		}
		%>

		<c:choose>
			<c:when test='<%= type.equals("textarea") %>'>
				<textarea class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= namespace + id %>" <%= multiple ? "multiple" : StringPool.BLANK %> name="<%= namespace + name %>" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(placeholder) ? "placeholder=\"" + LanguageUtil.get(pageContext, placeholder) + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>><%= HtmlUtil.escape(valueString) %></textarea>
			</c:when>
			<c:otherwise>
				<input class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= namespace + id %>" <%= multiple ? "multiple" : StringPool.BLANK %> name="<%= namespace + name %>" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(placeholder) ? "placeholder=\"" + LanguageUtil.get(pageContext, placeholder) + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> type="<%= Validator.isNull(type) ? "text" : type %>" <%= !type.equals("image") ? "value=\"" + HtmlUtil.escapeAttribute(valueString) + "\"" : StringPool.BLANK %> <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<c:if test='<%= !type.equals("hidden") && !type.equals("assetCategories") %>'>
			</span>

			<c:if test='<%= Validator.isNotNull(suffix) && !inlineLabel.equals("right") %>'>
				<span class="aui-suffix"><liferay-ui:message key="<%= suffix %>" /></span>
			</c:if>

			<c:if test='<%= Validator.isNotNull(label) && inlineLabel.equals("right") %>'>
				<label <%= labelTag %>>
					<liferay-ui:message key="<%= label %>" />

					<c:if test="<%= required && showRequiredLabel %>">
						<span class="aui-label-required">(<liferay-ui:message key="required" />)</span>
					</c:if>

					<c:if test="<%= Validator.isNotNull(helpMessage) %>">
						<liferay-ui:icon-help message="<%= helpMessage %>" />
					</c:if>

					<c:if test="<%= changesContext %>">
						<span class="aui-helper-hidden-accessible"><liferay-ui:message key="changing-the-value-of-this-field-will-reload-the-page" />)</span>
					</c:if>

					<c:if test="<%= Validator.isNotNull(suffix) %>">
						<span class="aui-suffix"><liferay-ui:message key="<%= suffix %>" /></span>
					</c:if>
				</label>
			</c:if>
		</span>
	</span>
</c:if>

<%!
private long _getClassPK(Object bean, long classPK) {
	if ((bean != null) && (classPK <= 0)) {
		if (bean instanceof ClassedModel) {
			ClassedModel classedModel = (ClassedModel)bean;

			Serializable primaryKeyObj = classedModel.getPrimaryKeyObj();

			if (primaryKeyObj instanceof Long) {
				classPK = (Long)primaryKeyObj;
			}
			else {
				classPK = GetterUtil.getLong(primaryKeyObj.toString());
			}
		}
	}

	return classPK;
}
%>