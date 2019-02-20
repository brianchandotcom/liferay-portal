<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceName = ResourceActionsUtil.getModelResource(request, modelResource);

long columnId = ParamUtil.getLong(request, "columnId");

ExpandoColumn column = null;

if (columnId > 0) {
	column = ExpandoColumnServiceUtil.fetchExpandoColumn(columnId);
}

int type = 0;

if (column != null) {
	type = column.getType();
}

ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(company.getCompanyId(), modelResource);

UnicodeProperties properties = new UnicodeProperties(true);
Serializable defaultValue = null;

if (column != null) {
	properties = expandoBridge.getAttributeProperties(column.getName());
	defaultValue = expandoBridge.getAttributeDefault(column.getName());
}

boolean propertyHidden = GetterUtil.getBoolean(properties.get(ExpandoColumnConstants.PROPERTY_HIDDEN));
boolean propertyVisibleWithUpdatePermission = GetterUtil.getBoolean(properties.get(ExpandoColumnConstants.PROPERTY_VISIBLE_WITH_UPDATE_PERMISSION));
int propertyIndexType = GetterUtil.getInteger(properties.get(ExpandoColumnConstants.INDEX_TYPE));
boolean propertySecret = GetterUtil.getBoolean(properties.get(ExpandoColumnConstants.PROPERTY_SECRET));
int propertyHeight = GetterUtil.getInteger(properties.get(ExpandoColumnConstants.PROPERTY_HEIGHT));
int propertyWidth = GetterUtil.getInteger(properties.get(ExpandoColumnConstants.PROPERTY_WIDTH));
String propertyDisplayType = GetterUtil.getString(properties.get(ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_attributes.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("modelResource", modelResource);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(modelResourceName + ": " + ((column == null) ? LanguageUtil.get(request, "new-custom-field") : column.getName()));
%>

<portlet:actionURL name='<%= (column == null) ? "addExpando" : "updateExpando" %>' var="editExpandoURL">
	<portlet:param name="mvcPath" value="/edit_expando.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editExpandoURL %>"
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="columnId" type="hidden" value="<%= columnId %>" />
	<aui:input name="modelResource" type="hidden" value="<%= modelResource %>" />

	<liferay-ui:error exception="<%= ColumnNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= ColumnTypeException.class %>" message="please-select-a-valid-type" />
	<liferay-ui:error exception="<%= DuplicateColumnNameException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= ValueDataException.class %>" message="please-enter-a-valid-value" />

	<liferay-frontend:edit-form-body>
		<h2 class="sheet-title">
			<%
			String typeLabel = LanguageUtil.get(request, ExpandoColumnConstants.getTypeLabel(type));
			%>

			<%= LanguageUtil.format(request, column != null ? "edit-x-field" : "new-custom-field", new Object[] {typeLabel}, false) %>
		</h2>

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<c:choose>
					<c:when test="<%= column != null %>">
						<aui:field-wrapper>
							<aui:input name="name" type="hidden" value="<%= column.getName() %>" />

							<aui:input label="field-name" name="key" type="resource" value="<%= column.getName() %>" />

							<div class="form-text">
								<liferay-ui:message key="custom-field-key-help" />
							</div>
						</aui:field-wrapper>
					</c:when>
					<c:otherwise>
						<aui:field-wrapper>
							<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" label="field-name" name="name" />

							<div class="form-text">
								<liferay-ui:message key="custom-field-key-help" />
							</div>
						</aui:field-wrapper>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="<%= column != null %>">
						<aui:input name="type" type="hidden" value="<%= type %>" />
					</c:when>
					<c:otherwise>
						<aui:select helpMessage="custom-field-type-help" label="field-type" name="type">
							<optgroup label="<liferay-ui:message key="presets" />">
								<aui:option label="selection-of-integer-values" value="PresetSelectionIntegerArray()" />
								<aui:option label="selection-of-decimal-values" value="PresetSelectionDoubleArray()" />
								<aui:option label="selection-of-text-values" value="PresetSelectionStringArray()" />
								<aui:option label="text-box" value="PresetTextBox()" />
								<aui:option label="text-box-indexed" value="PresetTextBoxIndexed()" />
								<aui:option label="text-field-secret" value="PresetTextFieldSecret()" />
								<aui:option label="text-field-indexed" selected="<%= true %>" value="PresetTextFieldIndexed()" />
							</optgroup>

							<optgroup label="<liferay-ui:message key="primitives" />">

								<%
								for (int curType : ExpandoColumnConstants.TYPES) {
									if ((curType == ExpandoColumnConstants.BOOLEAN_ARRAY) || (curType == ExpandoColumnConstants.DATE_ARRAY) || (curType == ExpandoColumnConstants.STRING_ARRAY_LOCALIZED)) {
										continue;
									}
								%>

									<aui:option label="<%= ExpandoColumnConstants.getTypeLabel(curType) %>" value="<%= curType %>" />

								<%
								}
								%>

							</optgroup>
						</aui:select>
					</c:otherwise>
				</c:choose>

				<c:if test="<%= column != null %>">
					<c:choose>
						<c:when test="<%= type == ExpandoColumnConstants.BOOLEAN %>">

							<%
							boolean curValue = ((Boolean)defaultValue).booleanValue();
							%>

							<aui:select name="defaultValue">
								<aui:option label="<%= true %>" selected="<%= curValue %>" value="<%= true %>" />
								<aui:option label="<%= false %>" selected="<%= !curValue %>" value="<%= false %>" />
							</aui:select>
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.BOOLEAN_ARRAY %>">
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.DATE %>">

							<%
							Calendar defaultValueDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

							if (defaultValue != null) {
								defaultValueDate.setTime((Date)defaultValue);
							}
							%>

							<aui:field-wrapper label="default-value">
								<liferay-ui:input-date
									dayParam="defaultValueDay"
									dayValue="<%= defaultValueDate.get(Calendar.DATE) %>"
									disabled="<%= false %>"
									firstDayOfWeek="<%= defaultValueDate.getFirstDayOfWeek() - 1 %>"
									monthParam="defaultValueMonth"
									monthValue="<%= defaultValueDate.get(Calendar.MONTH) %>"
									yearParam="defaultValueYear"
									yearValue="<%= defaultValueDate.get(Calendar.YEAR) %>"
								/>

								<liferay-ui:input-time
									amPmParam="defaultValueAmPm"
									amPmValue="<%= defaultValueDate.get(Calendar.AM_PM) %>"
									disabled="<%= false %>"
									hourParam="defaultValueHour"
									hourValue="<%= defaultValueDate.get(Calendar.HOUR) %>"
									minuteParam="defaultValueMinute"
									minuteValue="<%= defaultValueDate.get(Calendar.MINUTE) %>"
								/>
							</aui:field-wrapper>
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.DATE_ARRAY %>">
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.DOUBLE_ARRAY %>">
							<aui:input cssClass="lfr-textarea-container" helpMessage="enter-one-value-per-line" label="values" name="defaultValue" required="<%= true %>" type="textarea" value="<%= StringUtil.merge((double[])defaultValue, StringPool.NEW_LINE) %>" />
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.FLOAT_ARRAY %>">
							<aui:input cssClass="lfr-textarea-container" helpMessage="enter-one-value-per-line" label="values" name="defaultValue" required="<%= true %>" type="textarea" value="<%= StringUtil.merge((float[])defaultValue, StringPool.NEW_LINE) %>" />
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.INTEGER_ARRAY %>">
							<aui:input cssClass="lfr-textarea-container" helpMessage="enter-one-value-per-line" label="values" name="defaultValue" required="<%= true %>" type="textarea" value="<%= StringUtil.merge((int[])defaultValue, StringPool.NEW_LINE) %>" />
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.LONG_ARRAY %>">
							<aui:input cssClass="lfr-textarea-container" helpMessage="enter-one-value-per-line" label="values" name="defaultValue" required="<%= true %>" type="textarea" value="<%= StringUtil.merge((long[])defaultValue, StringPool.NEW_LINE) %>" />
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.NUMBER_ARRAY %>">
							<aui:input cssClass="lfr-textarea-container" helpMessage="enter-one-value-per-line" label="values" name="defaultValue" required="<%= true %>" type="textarea" value="<%= StringUtil.merge((Number[])defaultValue, StringPool.NEW_LINE) %>" />
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.SHORT_ARRAY %>">
							<aui:input cssClass="lfr-textarea-container" helpMessage="enter-one-value-per-line" label="values" name="defaultValue" required="<%= true %>" type="textarea" value="<%= StringUtil.merge((short[])defaultValue, StringPool.NEW_LINE) %>" />
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.STRING_ARRAY %>">
							<aui:input cssClass="lfr-textarea-container" helpMessage="enter-one-value-per-line" label="values" name="defaultValue" required="<%= true %>" type="textarea" value="<%= StringUtil.merge((String[])defaultValue, StringPool.NEW_LINE) %>" />
						</c:when>
						<c:when test="<%= type == ExpandoColumnConstants.STRING_LOCALIZED %>">

							<%
							String xml = StringPool.BLANK;

							if (defaultValue != null) {
								xml = LocalizationUtil.updateLocalization((Map<Locale, String>)defaultValue, StringPool.BLANK, "Data", LocaleUtil.toLanguageId(locale));
							}
							%>

							<aui:field-wrapper label="starting-value">
								<liferay-ui:input-localized
									cssClass="lfr-input-text-container"
									name="defaultValue"
									xml="<%= xml %>"
								/>
							</aui:field-wrapper>
						</c:when>
						<c:otherwise>
							<aui:input cssClass="lfr-input-text-container" label="starting-value" name="defaultValue" type="text" value="<%= String.valueOf(defaultValue) %>" />
						</c:otherwise>
					</c:choose>
				</c:if>
			</liferay-frontend:fieldset>

			<c:if test="<%= column != null %>">
				<liferay-frontend:fieldset
					collapsed="<%= true %>"
					collapsible="<%= true %>"
					label="advanced-properties"
				>
					<aui:field-wrapper>
						<aui:input label="visible-with-update-permission" name="Property--visible-with-update-permission--" type="toggle-switch" value="<%= propertyVisibleWithUpdatePermission %>" />

						<div class="form-text">
							<liferay-ui:message key="setting-a-custom-field-to-visible-with-update-permission-means-that-a-user-with-update-permission-can-view-this-field.-this-setting-overrides-the-value-of-hidden-in-this-case" />
						</div>
					</aui:field-wrapper>

					<aui:field-wrapper>
						<aui:input label="hidden" name="Property--hidden--" type="toggle-switch" value="<%= propertyHidden %>" />

						<div class="form-text">
							<liferay-ui:message key="setting-a-custom-field-to-hidden-means-that-the-field's-value-is-never-shown-in-any-user-interface-besides-this-one.-this-lets-the-field-be-used-for-more-obscure-and-advanced-purposes-such-as-acting-as-a-placeholder-for-custom-permissions" />
						</div>
					</aui:field-wrapper>

					<c:if test="<%= type == ExpandoColumnConstants.STRING %>">
						<aui:field-wrapper>
							<aui:input label="secret" name="Property--secret--" type="toggle-switch" value="<%= propertySecret %>" />

							<div class="form-text">
								<liferay-ui:message key="setting-a-custom-field-to-secret-means-that-typing-is-hidden-on-the-screen.-use-this-for-passwords" />
							</div>
						</aui:field-wrapper>
					</c:if>

					<aui:field-wrapper>
						<aui:input label="searchable" name="searchable" type="toggle-switch" value="<%= propertyIndexType != ExpandoColumnConstants.INDEX_TYPE_NONE %>" />

						<div class="form-text">
							<liferay-ui:message key="setting-a-custom-field-to-searchable-means-that-the-value-of-the-field-is-indexed-when-the-entity-such-as-user-is-modified.-only-java.lang.string-fields-can-be-made-searchable.-note-that-when-an-field-is-newly-made-searchable,-the-indexes-must-be-updated-before-the-data-is-available-to-search" />
						</div>

						<div class="<%= propertyIndexType != ExpandoColumnConstants.INDEX_TYPE_NONE ? "" : "hide" %>" id="<portlet:namespace />propertyIndexType">
							<div class="radio">
								<aui:input checked="<%= propertyIndexType == ExpandoColumnConstants.INDEX_TYPE_KEYWORD %>" label="as-keyword" name="Property--index-type--" type="radio" value="<%= ExpandoColumnConstants.INDEX_TYPE_KEYWORD %>" />
							</div>

							<div class="radio">
								<aui:input checked="<%= propertyIndexType == ExpandoColumnConstants.INDEX_TYPE_TEXT %>" label="as-text" name="Property--index-type--" type="radio" value="<%= ExpandoColumnConstants.INDEX_TYPE_TEXT %>" />
							</div>
						</div>
					</aui:field-wrapper>

					<c:if test="<%= type == ExpandoColumnConstants.STRING %>">
						<aui:field-wrapper>
							<aui:input cssClass="lfr-input-text short-input-text" label="height" name="Property--height--" type="text" value="<%= propertyHeight %>" />

							<div class="form-text">
								<liferay-ui:message key="custom-field-height-help" />
							</div>
						</aui:field-wrapper>

						<aui:field-wrapper>
							<aui:input cssClass="lfr-input-text short-input-text" label="width" name="Property--width--" type="text" value="<%= propertyWidth %>" />

							<div class="form-text">
								<liferay-ui:message key="custom-field-width-help" />
							</div>
						</aui:field-wrapper>
					</c:if>

					<c:if test="<%= (type == ExpandoColumnConstants.DOUBLE_ARRAY) || (type == ExpandoColumnConstants.FLOAT_ARRAY) || (type == ExpandoColumnConstants.INTEGER_ARRAY) || (type == ExpandoColumnConstants.LONG_ARRAY) || (type == ExpandoColumnConstants.NUMBER_ARRAY) || (type == ExpandoColumnConstants.SHORT_ARRAY) || (type == ExpandoColumnConstants.STRING_ARRAY) %>">
						<aui:select helpMessage="custom-field-display-type-help" label="display-type" name="Property--display-type--" value="<%= propertyDisplayType %>">
							<aui:option label="checkbox" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_CHECKBOX %>" />
							<aui:option label="radio" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_RADIO %>" />
							<aui:option label="selection-list" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST %>" />
							<aui:option label="text-box" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX %>" />
						</aui:select>
					</c:if>
				</liferay-frontend:fieldset>
			</c:if>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<%
PortalUtil.addPortletBreadcrumbEntry(request, modelResourceName, portletURL.toString());

if (column != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, column.getName(), null);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, ((column == null) ? "add-attribute" : "edit")), currentURL);
%>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />searchable', '<portlet:namespace />propertyIndexType');
</aui:script>