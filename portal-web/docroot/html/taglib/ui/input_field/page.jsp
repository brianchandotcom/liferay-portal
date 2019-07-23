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

<%@ include file="/html/taglib/init.jsp" %>

<%
boolean autoComplete = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-field:autoComplete"));
boolean autoFocus = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-field:autoFocus"));
boolean autoSize = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-field:autoSize"));
Object bean = request.getAttribute("liferay-ui:input-field:bean");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-field:cssClass"));
String dateTogglerCheckboxLabel = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-field:dateTogglerCheckboxLabel"));
String defaultLanguageId = (String)request.getAttribute("liferay-ui:input-field:defaultLanguageId");
Object defaultValue = request.getAttribute("liferay-ui:input-field:defaultValue");
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-field:disabled"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ui:input-field:dynamicAttributes");
String field = (String)request.getAttribute("liferay-ui:input-field:field");
String fieldParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-field:fieldParam"));
Format format = (Format)request.getAttribute("liferay-ui:input-field:format");
String formName = (String)request.getAttribute("liferay-ui:input-field:formName");
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-field:id"));
boolean ignoreRequestValue = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-field:ignoreRequestValue"));
String languageId = (String)request.getAttribute("liferay-ui:input-field:languageId");
String model = (String)request.getAttribute("liferay-ui:input-field:model");
String placeholder = (String)request.getAttribute("liferay-ui:input-field:placeholder");

String type = ModelHintsUtil.getType(model, field);

Map<String, String> hints = ModelHintsUtil.getHints(model, field);

if (hints != null) {
	type = GetterUtil.getString(hints.get("type"), type);
}
%>

<c:if test="<%= type != null %>">
	<c:choose>
		<c:when test='<%= type.equals("boolean") %>'>

			<%
			boolean defaultBoolean = GetterUtil.DEFAULT_BOOLEAN;

			if (defaultValue != null) {
				defaultBoolean = ((Boolean)defaultValue).booleanValue();
			}
			else {
				if (hints != null) {
					defaultBoolean = GetterUtil.getBoolean(hints.get("default-value"));
				}
			}

			boolean value = BeanPropertiesUtil.getBooleanSilent(bean, field, defaultBoolean);

			if (!ignoreRequestValue && Validator.isNotNull(ParamUtil.getString(request, "checkboxNames"))) {
				value = ParamUtil.getBoolean(request, fieldParam, value);
			}
			%>

			<liferay-ui:input-checkbox
				cssClass="<%= cssClass %>"
				defaultValue="<%= value %>"
				disabled="<%= disabled %>"
				formName="<%= formName %>"
				id="<%= namespace + id %>"
				param="<%= fieldParam %>"
			/>
		</c:when>
		<c:when test='<%= type.equals("Date") %>'>

			<%
			boolean checkDefaultDelta = false;

			Calendar cal = null;

			if (defaultValue != null) {
				cal = (Calendar)defaultValue;
			}
			else {
				cal = CalendarFactoryUtil.getCalendar(timeZone, locale);

				Date date = (Date)BeanPropertiesUtil.getObject(bean, field);

				if (date == null) {
					checkDefaultDelta = true;

					date = new Date();
				}

				cal.setTime(date);
			}

			boolean updateFromDefaultDelta = false;

			int month = -1;

			if (!ignoreRequestValue) {
				month = ParamUtil.getInteger(request, fieldParam + "Month", month);
			}

			if ((month == -1) && (cal != null)) {
				month = cal.get(Calendar.MONTH);

				if (checkDefaultDelta && (hints != null)) {
					int defaultMonthDelta = GetterUtil.getInteger(hints.get("default-month-delta"));

					cal.add(Calendar.MONTH, defaultMonthDelta);

					updateFromDefaultDelta = true;
				}
			}

			int day = -1;

			if (!ignoreRequestValue) {
				day = ParamUtil.getInteger(request, fieldParam + "Day", day);
			}

			if ((day == -1) && (cal != null)) {
				day = cal.get(Calendar.DATE);

				if (checkDefaultDelta && (hints != null)) {
					int defaultDayDelta = GetterUtil.getInteger(hints.get("default-day-delta"));

					cal.add(Calendar.DATE, defaultDayDelta);

					updateFromDefaultDelta = true;
				}
			}

			int year = -1;

			if (!ignoreRequestValue) {
				year = ParamUtil.getInteger(request, fieldParam + "Year", year);
			}

			if ((year == -1) && (cal != null)) {
				year = cal.get(Calendar.YEAR);

				if (checkDefaultDelta && (hints != null)) {
					int defaultYearDelta = GetterUtil.getInteger(hints.get("default-year-delta"));

					cal.add(Calendar.YEAR, defaultYearDelta);

					updateFromDefaultDelta = true;
				}
			}

			if (updateFromDefaultDelta) {
				month = cal.get(Calendar.MONTH);
				day = cal.get(Calendar.DATE);
				year = cal.get(Calendar.YEAR);
			}

			int firstDayOfWeek = Calendar.SUNDAY - 1;

			if (cal != null) {
				firstDayOfWeek = cal.getFirstDayOfWeek() - 1;
			}

			int hour = -1;

			if (!ignoreRequestValue) {
				hour = ParamUtil.getInteger(request, fieldParam + "Hour", hour);
			}

			if ((hour == -1) && (cal != null)) {
				hour = cal.get(Calendar.HOUR_OF_DAY);

				if (DateUtil.isFormatAmPm(locale)) {
					hour = cal.get(Calendar.HOUR);
				}
			}

			int minute = -1;

			if (!ignoreRequestValue) {
				minute = ParamUtil.getInteger(request, fieldParam + "Minute", minute);
			}

			if ((minute == -1) && (cal != null)) {
				minute = cal.get(Calendar.MINUTE);
			}

			int amPm = -1;

			if (!ignoreRequestValue) {
				amPm = ParamUtil.getInteger(request, fieldParam + "AmPm", amPm);
			}

			if ((amPm == -1) && (cal != null)) {
				amPm = Calendar.AM;

				if (DateUtil.isFormatAmPm(locale)) {
					amPm = cal.get(Calendar.AM_PM);
				}
			}

			boolean showTime = true;

			if (hints != null) {
				showTime = GetterUtil.getBoolean(hints.get("show-time"), showTime);
			}

			String timeFormat = GetterUtil.getString((String)dynamicAttributes.get("timeFormat"));
			%>

			<div class="form-group-autofit">
				<div class="form-group-item">
					<liferay-ui:input-date
						autoFocus="<%= autoFocus %>"
						cssClass="<%= cssClass %>"
						dayParam='<%= fieldParam + "Day" %>'
						dayValue="<%= day %>"
						disabled="<%= disabled %>"
						firstDayOfWeek="<%= firstDayOfWeek %>"
						formName="<%= formName %>"
						monthParam='<%= fieldParam + "Month" %>'
						monthValue="<%= month %>"
						name="<%= fieldParam %>"
						yearParam='<%= fieldParam + "Year" %>'
						yearValue="<%= year %>"
					/>
				</div>

				<c:if test="<%= showTime %>">
					<div class="form-group-item">
						<liferay-ui:input-time
							amPmParam='<%= fieldParam + "AmPm" %>'
							amPmValue="<%= amPm %>"
							cssClass="<%= cssClass %>"
							disabled="<%= disabled %>"
							hourParam='<%= fieldParam + "Hour" %>'
							hourValue="<%= hour %>"
							minuteParam='<%= fieldParam + "Minute" %>'
							minuteValue="<%= minute %>"
							name='<%= fieldParam + "Time" %>'
							timeFormat="<%= timeFormat %>"
						/>
					</div>
				</c:if>
			</div>

			<c:if test="<%= Validator.isNotNull(dateTogglerCheckboxLabel) %>">

				<%
				String dateTogglerCheckboxName = TextFormatter.format(dateTogglerCheckboxLabel, TextFormatter.M);
				%>

				<div class="clearfix">
					<aui:input id="<%= formName + fieldParam %>" label="<%= dateTogglerCheckboxLabel %>" name="<%= dateTogglerCheckboxName %>" type="checkbox" value="<%= disabled %>" />
				</div>

				<aui:script use="event-base">
					var checkbox = A.one('#<portlet:namespace /><%= formName + fieldParam %>');

					if (checkbox) {
						checkbox.once(
							'click',
							function() {
								Liferay.component('<portlet:namespace /><%= fieldParam %>DatePicker');
							}
						);

						var form = document.<portlet:namespace /><%= formName %>;

						checkbox.on(
							'click',
							function(event) {
								var checked = checkbox.get('checked');

								var elements = [
									Liferay.Util.getFormElement(form, '<%= fieldParam %>'),
									Liferay.Util.getFormElement(form, '<%= fieldParam %>Day'),
									Liferay.Util.getFormElement(form, '<%= fieldParam %>Month'),
									Liferay.Util.getFormElement(form, '<%= fieldParam %>Year'),
									Liferay.Util.getFormElement(form, '<%= fieldParam %>Time'),
									Liferay.Util.getFormElement(form, '<%= fieldParam %>Hour'),
									Liferay.Util.getFormElement(form, '<%= fieldParam %>Minute'),
									Liferay.Util.getFormElement(form, '<%= fieldParam %>AmPm')
								].filter(Boolean);

								elements.forEach(
									function(element) {
										if (checked) {
											element.setAttribute('disabled', '');
										}
										else {
											element.removeAttribute('disabled');
										}

										A.one(element).toggleClass('disabled', checked);
									}
								);

								var label = A.one('label[for="<portlet:namespace /><%= fieldParam %>"]');

								if (label) {
									label.toggleClass('disabled', checked);
								}
							}
						);
					}
				</aui:script>
			</c:if>
		</c:when>
		<c:when test='<%= type.equals("double") || type.equals("int") || type.equals("long") || type.equals("String") %>'>

			<%
			String defaultString = GetterUtil.DEFAULT_STRING;

			if (defaultValue != null) {
				defaultString = (String)defaultValue;
			}

			String value = null;

			if (type.equals("double")) {
				double doubleValue = BeanPropertiesUtil.getDoubleSilent(bean, field, GetterUtil.getDouble(defaultString));

				if (!ignoreRequestValue) {
					doubleValue = ParamUtil.getDouble(request, fieldParam, doubleValue, locale);
				}

				if (format != null) {
					value = format.format(doubleValue);
				}
				else {
					value = String.valueOf(doubleValue);
				}
			}
			else if (type.equals("int")) {
				int intValue = BeanPropertiesUtil.getIntegerSilent(bean, field, GetterUtil.getInteger(defaultString));

				if (!ignoreRequestValue) {
					intValue = ParamUtil.getInteger(request, fieldParam, intValue);
				}

				if (format != null) {
					value = format.format(intValue);
				}
				else {
					value = String.valueOf(intValue);
				}
			}
			else if (type.equals("long")) {
				long longValue = BeanPropertiesUtil.getLongSilent(bean, field, GetterUtil.getLong(defaultString));

				if (!ignoreRequestValue) {
					longValue = ParamUtil.getLong(request, fieldParam, longValue);
				}

				if (format != null) {
					value = format.format(longValue);
				}
				else {
					value = String.valueOf(longValue);
				}
			}
			else {
				value = BeanPropertiesUtil.getStringSilent(bean, field, defaultString);

				if (!ignoreRequestValue) {
					value = ParamUtil.getString(request, fieldParam, value);
				}
			}

			boolean autoEscape = true;

			if (hints != null) {
				autoEscape = GetterUtil.getBoolean(hints.get("auto-escape"), true);
			}

			boolean checkTab = false;
			String displayHeight = ModelHintsConstants.TEXT_DISPLAY_HEIGHT;
			boolean editor = false;
			String maxLength = ModelHintsConstants.TEXT_MAX_LENGTH;
			boolean secret = false;
			boolean upperCase = false;

			if (hints != null) {
				autoSize = GetterUtil.getBoolean(hints.get("autoSize"), autoSize);
				checkTab = GetterUtil.getBoolean(hints.get("check-tab"), checkTab);
				displayHeight = GetterUtil.getString(hints.get("display-height"), displayHeight);
				editor = GetterUtil.getBoolean(hints.get("editor"), editor);
				maxLength = GetterUtil.getString(hints.get("max-length"), maxLength);
				secret = GetterUtil.getBoolean(hints.get("secret"), secret);
				upperCase = GetterUtil.getBoolean(hints.get("upper-case"), upperCase);
			}

			if (autoSize) {
				displayHeight = "auto";
			}

			cssClass += " form-control";

			if (editor) {
				cssClass += " lfr-input-editor";
			}

			boolean localized = ModelHintsUtil.isLocalized(model, field);

			Set<Locale> availableLocales = null;

			String xml = StringPool.BLANK;

			if (localized) {
				if (ModelHintsUtil.hasField(model, "groupId")) {
					availableLocales = LanguageUtil.getAvailableLocales(BeanPropertiesUtil.getLongSilent(bean, "groupId", themeDisplay.getSiteGroupId()));
				}
				else {
					availableLocales = LanguageUtil.getAvailableLocales();
				}

				if (Validator.isNotNull(bean)) {
					xml = BeanPropertiesUtil.getString(bean, field);
				}
			}
			%>

			<c:choose>
				<c:when test="<%= editor %>">
					<c:choose>
						<c:when test="<%= localized %>">
							<liferay-ui:input-localized
								autoFocus="<%= autoFocus %>"
								availableLocales="<%= availableLocales %>"
								cssClass="<%= cssClass %>"
								defaultLanguageId="<%= defaultLanguageId %>"
								disabled="<%= disabled %>"
								formName="<%= formName %>"
								id="<%= id %>"
								ignoreRequestValue="<%= ignoreRequestValue %>"
								languageId="<%= languageId %>"
								maxLength="<%= maxLength %>"
								name="<%= fieldParam %>"
								placeholder="<%= placeholder %>"
								style='<%= upperCase ? "text-transform: uppercase;" : "" %>'
								type="editor"
								xml="<%= xml %>"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:input-editor
								contents="<%= value %>"
								contentsLanguageId="<%= languageId %>"
								cssClass="<%= cssClass %>"
								editorName="ckeditor"
								name="<%= fieldParam %>"
								placeholder="<%= placeholder %>"
								toolbarSet="simple"
							/>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="<%= displayHeight.equals(ModelHintsConstants.TEXT_DISPLAY_HEIGHT) %>">

					<%
					if (Validator.isNotNull(value)) {
						int maxLengthInt = GetterUtil.getInteger(maxLength);

						if (value.length() > maxLengthInt) {
							value = value.substring(0, maxLengthInt);
						}
					}
					%>

					<c:choose>
						<c:when test="<%= localized %>">
							<liferay-ui:input-localized
								autoFocus="<%= autoFocus %>"
								availableLocales="<%= availableLocales %>"
								cssClass='<%= cssClass + " lfr-input-text" %>'
								defaultLanguageId="<%= defaultLanguageId %>"
								disabled="<%= disabled %>"
								formName="<%= formName %>"
								id="<%= id %>"
								ignoreRequestValue="<%= ignoreRequestValue %>"
								languageId="<%= languageId %>"
								maxLength="<%= maxLength %>"
								name="<%= fieldParam %>"
								placeholder="<%= placeholder %>"
								style='<%= upperCase ? "text-transform: uppercase;" : "" %>'
								xml="<%= xml %>"
							/>
						</c:when>
						<c:otherwise>
							<input <%= !autoComplete ? "autocomplete=\"off\"" : StringPool.BLANK %> class="<%= cssClass + " lfr-input-text" %>" <%= disabled ? "disabled=\"disabled\"" : StringPool.BLANK %> id="<%= namespace %><%= id %>" name="<%= namespace %><%= fieldParam %>" <%= Validator.isNotNull(placeholder) ? "placeholder=\"" + LanguageUtil.get(resourceBundle, placeholder) + "\"" : StringPool.BLANK %> style="<%= upperCase ? "text-transform: uppercase;" : StringPool.BLANK %>" type="<%= secret ? "password" : "text" %>" value="<%= autoEscape ? HtmlUtil.escape(value) : value %>" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="<%= localized %>">
							<liferay-ui:input-localized
								autoFocus="<%= autoFocus %>"
								autoSize="<%= autoSize %>"
								availableLocales="<%= availableLocales %>"
								cssClass='<%= cssClass + " lfr-input-text" %>'
								defaultLanguageId="<%= defaultLanguageId %>"
								disabled="<%= disabled %>"
								formName="<%= formName %>"
								id="<%= id %>"
								ignoreRequestValue="<%= ignoreRequestValue %>"
								languageId="<%= languageId %>"
								maxLength="<%= maxLength %>"
								name="<%= fieldParam %>"
								onKeyDown='<%= (checkTab ? "Liferay.Util.checkTab(this); " : StringPool.BLANK) + "Liferay.Util.disableEsc();" %>'
								placeholder="<%= placeholder %>"
								style='<%= !autoSize ? "height: " + displayHeight + (Validator.isDigit(displayHeight) ? "px" : StringPool.BLANK) + ";" : StringPool.BLANK %>'
								type="textarea"
								wrap="soft"
								xml="<%= xml %>"
							/>
						</c:when>
						<c:otherwise>
							<textarea class="<%= cssClass + " lfr-textarea" %>" <%= disabled ? "disabled=\"disabled\"" : StringPool.BLANK %> id="<%= namespace %><%= id %>" name="<%= namespace %><%= fieldParam %>" onKeyDown="<%= checkTab ? "Liferay.Util.checkTab(this); " : StringPool.BLANK %> Liferay.Util.disableEsc();" <%= Validator.isNotNull(placeholder) ? "placeholder=\"" + LanguageUtil.get(resourceBundle, placeholder) + "\"" : StringPool.BLANK %> style="<%= !autoSize ? "height: " + displayHeight + (Validator.isDigit(displayHeight) ? "px" : StringPool.BLANK) + ";" : StringPool.BLANK %>" wrap="soft"><%= autoEscape ? HtmlUtil.escape(value) : value %></textarea>
						</c:otherwise>
					</c:choose>

					<c:if test="<%= autoSize && !localized %>">
						<aui:script use="aui-autosize">
							A.one('#<%= namespace %><%= id %>').plug(
								A.Plugin.Autosize,
								{
									<c:if test="<%= Validator.isDigit(displayHeight) %>">
										minHeight: <%= displayHeight %>
									</c:if>
								}
							);
						</aui:script>
					</c:if>
				</c:otherwise>
			</c:choose>

			<c:if test="<%= !localized %>">
				<c:if test="<%= autoFocus %>">
					<aui:script>
						Liferay.Util.focusFormField('#<%= namespace %><%= id %>');
					</aui:script>
				</c:if>

				<aui:script use="aui-char-counter">
					new A.CharCounter(
						{
							input: '#<%= namespace %><%= id %>',
							maxLength: <%= maxLength %>
						}
					);
				</aui:script>
			</c:if>
		</c:when>
	</c:choose>
</c:if>