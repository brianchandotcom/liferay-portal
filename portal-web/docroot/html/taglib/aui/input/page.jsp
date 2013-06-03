<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<liferay-util:buffer var="labelContent">
	<liferay-ui:message key="<%= label %>" />

	<c:if test='<%= required && showRequiredLabel && !type.equals("radio")%>'>
		<span class="label-required">(<liferay-ui:message key="required" />)</span>
	</c:if>

	<c:if test="<%= Validator.isNotNull(helpMessage) %>">
		<liferay-ui:icon-help message="<%= helpMessage %>" />
	</c:if>

	<c:if test="<%= changesContext %>">
		<span class="hide-accessible"><liferay-ui:message key="changing-the-value-of-this-field-will-reload-the-page" />)</span>
	</c:if>
</liferay-util:buffer>

<c:if test='<%= !choiceField && !type.equals("hidden") %>'>
	<div class="<%= controlGroupCss %>">
</c:if>

<c:if test='<%= !type.equals("assetCategories") && !type.equals("hidden") && Validator.isNotNull(label) %>'>
	<label <%= labelTag %>>
		<c:if test='<%= !choiceField && !inlineLabel.equals("right") %>'>
				<%= labelContent %>
			</label>
		</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(prefix) || Validator.isNotNull(suffix) %>">
	<div class="<%= addOnCss %>">
		<c:if test="<%= Validator.isNotNull(prefix) %>">
			<span class="add-on"><liferay-ui:message key="<%= prefix %>" /></span>
		</c:if>
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
			cssClass="<%= fieldCss %>"
			defaultLanguageId="<%= defaultLanguageId %>"
			defaultValue="<%= value %>"
			disabled="<%= disabled %>"
			field="<%= field %>"
			fieldParam="<%= fieldParam %>"
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

		<input <%= checked ? "checked" : StringPool.BLANK %> class="<%= fieldCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= namespace + id %>Checkbox" name="<%= namespace + name %>Checkbox" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> onClick="Liferay.Util.updateCheckboxValue(this); <%= onClick %>" <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> type="checkbox" value="<%= HtmlUtil.escapeAttribute(defaultValueString) %>" <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
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

		<input <%= checked ? "checked" : StringPool.BLANK %> class="<%= fieldCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= namespace + id %>" name="<%= namespace + name %>" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> type="radio" value="<%= HtmlUtil.escapeAttribute(valueString) %>" <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
	</c:when>
	<c:when test='<%= type.equals("timeZone") %>'>

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

				<%
				String[] storedDimensions = resizable ? StringUtil.split(SessionClicks.get(request, _TEXTAREA_WIDTH_HEIGHT_PREFIX + namespace + id, StringPool.BLANK)) : StringPool.EMPTY_ARRAY;
				%>

				<textarea class="<%= fieldCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= namespace + id %>" <%= multiple ? "multiple" : StringPool.BLANK %> name="<%= namespace + name %>" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(placeholder) ? "placeholder=\"" + LanguageUtil.get(pageContext, placeholder) + "\"" : StringPool.BLANK %> <%= (storedDimensions.length > 1) ? "style=\"height: " + storedDimensions[0] + "; width: " + storedDimensions[1] + ";" + title + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>><%= HtmlUtil.escape(valueString) %></textarea>

				<c:if test="<%= autoSize %>">
					<aui:script use="aui-autosize">
						A.one('#<%= namespace + id %>').plug(A.Plugin.Autosize);
					</aui:script>
				</c:if>

				<c:if test="<%= resizable %>">
					<aui:script use="liferay-store,resize-base">
						var textareaNode = A.one('#<%= namespace + id %>');

						var resizeInstance = new A.Resize(
							{
								after: {
									'end': function(event) {
										Liferay.Store('<%= _TEXTAREA_WIDTH_HEIGHT_PREFIX %><%= namespace + id %>', textareaNode.getStyle('height') + ',' + textareaNode.getStyle('width'));
									}
								},
								autoHide: true,
								handles: 'r, br, b',
								node: textareaNode
							}
						);

						textareaNode.setData('resizeInstance', resizeInstance);
					</aui:script>
				</c:if>

			</c:when>
			<c:otherwise>
				<input class="<%= fieldCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= namespace + id %>" <%= multiple ? "multiple" : StringPool.BLANK %> name="<%= namespace + name %>" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(placeholder) ? "placeholder=\"" + LanguageUtil.get(pageContext, placeholder) + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> type="<%= Validator.isNull(type) ? "text" : type %>" <%= !type.equals("image") ? "value=\"" + HtmlUtil.escapeAttribute(valueString) + "\"" : StringPool.BLANK %> <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<c:if test="<%= Validator.isNotNull(prefix) || Validator.isNotNull(suffix) %>">
		<c:if test="<%= Validator.isNotNull(suffix) %>">
			<span class="add-on"><liferay-ui:message key="<%= suffix %>" /></span>
		</c:if>
	</div>
</c:if>

<c:if test='<%= !type.equals("assetCategories") && !type.equals("hidden") && Validator.isNotNull(label) %>'>
	<c:if test='<%= !choiceField && inlineLabel.equals("right") %>'>
		<label <%= labelTag %>>
			<%= labelContent %>
	</c:if>
	<c:if test="<%= choiceField %>">
		<%= labelContent %>
	</c:if>

	</label>
</c:if>

<c:if test='<%= !choiceField && !type.equals("hidden") %>'>
	</div>
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

private static final String _TEXTAREA_WIDTH_HEIGHT_PREFIX = "liferay_resize_";
%>