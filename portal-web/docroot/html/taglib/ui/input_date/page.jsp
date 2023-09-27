<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_input_date_page") + StringPool.UNDERLINE;

if (!GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:useNamespace"), true)) {
	namespace = StringPool.BLANK;
}

String autoComplete = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:autoComplete"));
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:cssClass"));
String dateTogglerCheckboxLabel = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:dateTogglerCheckboxLabel"), "disable");
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:disabled"));
String dayParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:dayParam"));
int dayValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:dayValue"));
int firstDayOfWeek = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:firstDayOfWeek"));
Date firstEnabledDate = GetterUtil.getDate(request.getAttribute("liferay-ui:input-date:firstEnabledDate"), DateFormatFactoryUtil.getDate(locale), null);
String formName = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:formName"));
Date lastEnabledDate = GetterUtil.getDate(request.getAttribute("liferay-ui:input-date:lastEnabledDate"), DateFormatFactoryUtil.getDate(locale), null);
String monthParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:monthParam"));
int monthValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:monthValue"));
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:name"));
boolean nullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:nullable"));
boolean required = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:required"));
boolean showDisableCheckbox = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:showDisableCheckbox"));
String yearParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:yearParam"));
int yearValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:yearValue"));

String dayParamId = namespace + HtmlUtil.getAUICompatibleId(dayParam);
String monthParamId = namespace + HtmlUtil.getAUICompatibleId(monthParam);
String nameId = namespace + HtmlUtil.getAUICompatibleId(name);
String yearParamId = namespace + HtmlUtil.getAUICompatibleId(yearParam);

Calendar calendar = null;

if (required && (yearValue == 0) && (monthValue == -1) && (dayValue == 0)) {
	calendar = CalendarFactoryUtil.getCalendar(timeZone);

	dayValue = calendar.get(Calendar.DAY_OF_MONTH);
	monthValue = calendar.get(Calendar.MONTH);
	yearValue = calendar.get(Calendar.YEAR);
}
else {
	calendar = CalendarFactoryUtil.getCalendar(yearValue, monthValue, dayValue);
}

String mask = _MASK_YMD;
String simpleDateFormatPattern = _SIMPLE_DATE_FORMAT_PATTERN_HTML5;

if (!BrowserSnifferUtil.isMobile(request)) {
	DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);

	SimpleDateFormat shortDateFormatSimpleDateFormat = (SimpleDateFormat)shortDateFormat;

	simpleDateFormatPattern = shortDateFormatSimpleDateFormat.toPattern();

	simpleDateFormatPattern = simpleDateFormatPattern.replaceAll("yyyy", "y");
	simpleDateFormatPattern = simpleDateFormatPattern.replaceAll("yy", "y");
	simpleDateFormatPattern = simpleDateFormatPattern.replaceAll("MM", "M");
	simpleDateFormatPattern = simpleDateFormatPattern.replaceAll("dd", "d");

	simpleDateFormatPattern = simpleDateFormatPattern.replaceAll("y", "yyyy");
	simpleDateFormatPattern = simpleDateFormatPattern.replaceAll("M", "MM");
	simpleDateFormatPattern = simpleDateFormatPattern.replaceAll("d", "dd");

	mask = simpleDateFormatPattern;

	// Replace single quotes to prevent the string from breaking when passing
	// into the A.DatePicker mask. This is used for the zh_HK locale which
	// returns the format yyyy'年'MM'月'dd'日'. See LPS-191923.

	mask = mask.replaceAll("'", "");

	mask = mask.replaceAll("yyyy", "%Y");
	mask = mask.replaceAll("MM", "%m");
	mask = mask.replaceAll("dd", "%d");
}

String dayAbbreviation = LanguageUtil.get(resourceBundle, "day-abbreviation");
String monthAbbreviation = LanguageUtil.get(resourceBundle, "month-abbreviation");
String yearAbbreviation = LanguageUtil.get(resourceBundle, "year-abbreviation");

String[] dateAbbreviations = {"M", "d", "y"};
String[] localizedDateAbbreviations = {monthAbbreviation, dayAbbreviation, yearAbbreviation};

String placeholderValue = StringUtil.replace(simpleDateFormatPattern, dateAbbreviations, localizedDateAbbreviations);

boolean nullDate = false;

if (nullable && !required && (dayValue == 0) && (monthValue == -1) && (yearValue == 0)) {
	nullDate = true;
}

String dateString = null;

Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(simpleDateFormatPattern, locale);

if (nullable && nullDate) {
	dateString = StringPool.BLANK;
}
else {
	dateString = format.format(calendar.getTime());
}
%>

<span class="lfr-input-date" id="<%= randomNamespace %>displayDate">
	<c:choose>
		<c:when test="<%= BrowserSnifferUtil.isMobile(request) %>">
			<input <%= Validator.isNotNull(autoComplete) ? "autocomplete=\"" + autoComplete + "\"" : StringPool.BLANK %> class="form-control <%= cssClass %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= nameId %>" name="<%= namespace + HtmlUtil.escapeAttribute(name) %>" type="date" value="<%= dateString %>" />
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="<%= Validator.isNotNull(autoComplete) %>">
					<aui:input autocomplete="<%= autoComplete %>" cssClass="<%= cssClass %>" disabled="<%= disabled %>" id="<%= HtmlUtil.getAUICompatibleId(name) %>" label="" name="<%= name %>" placeholder="<%= StringUtil.toLowerCase(placeholderValue) %>" required="<%= required %>" title="" type="text" useNamespace="<%= !StringPool.BLANK.equals(namespace) %>" value="<%= dateString %>" wrappedField="<%= true %>">
						<aui:validator errorMessage="please-enter-a-valid-date" name="custom">
							function(val) {
								const dateValidation = AUI().use('aui-datatype-date-parse').Parsers.date('<%= mask %>', val);

								if (!dateValidation) {
									let parentNode = A.one('#<%= nameId %>')._node.parentElement;

									let feedbackElement = parentNode.querySelector('.form-feedback-item');

									if (feedbackElement) {
										parentNode.removeChild(feedbackElement);
									}
								}

								return dateValidation;
							}
						</aui:validator>
					</aui:input>
				</c:when>
				<c:otherwise>
					<aui:input cssClass="<%= cssClass %>" disabled="<%= disabled %>" id="<%= HtmlUtil.getAUICompatibleId(name) %>" label="" name="<%= name %>" placeholder="<%= StringUtil.toLowerCase(placeholderValue) %>" required="<%= required %>" title="" type="text" useNamespace="<%= !StringPool.BLANK.equals(namespace) %>" value="<%= dateString %>" wrappedField="<%= true %>">
						<aui:validator errorMessage="please-enter-a-valid-date" name="custom">
							function(val) {
								const dateValidation = AUI().use('aui-datatype-date-parse').Parsers.date('<%= mask %>', val);

								if (!dateValidation) {
									let parentNode = A.one('#<%= nameId %>')._node.parentElement;

									let feedbackElement = parentNode.querySelector('.form-feedback-item');

									if (feedbackElement) {
										parentNode.removeChild(feedbackElement);
									}
								}
								return dateValidation;
							}
						</aui:validator>
					</aui:input>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= dayParamId %>" name="<%= namespace + HtmlUtil.escapeAttribute(dayParam) %>" type="hidden" value="<%= dayValue %>" />
	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= monthParamId %>" name="<%= namespace + HtmlUtil.escapeAttribute(monthParam) %>" type="hidden" value="<%= monthValue %>" />
	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= yearParamId %>" name="<%= namespace + HtmlUtil.escapeAttribute(yearParam) %>" type="hidden" value="<%= yearValue %>" />
</span>

<c:if test="<%= nullable && !required && showDisableCheckbox %>">

	<%
	String dateTogglerCheckboxName = TextFormatter.format(dateTogglerCheckboxLabel, TextFormatter.M);
	%>

	<aui:input label="<%= dateTogglerCheckboxLabel %>" name="<%= randomNamespace + dateTogglerCheckboxName %>" type="checkbox" value="<%= disabled %>" />

	<script>
		(function() {
			var form = document.<%= namespace + formName %>;

			var checkbox = document.getElementById('<%= namespace + randomNamespace + dateTogglerCheckboxName %>');

			if (checkbox) {
				checkbox.addEventListener(
					'click',
					function() {
						var checked = checkbox.checked;

						if (!form) {
							form = checkbox.form;
						}

						var dayField = Liferay.Util.getFormElement(form, '<%= HtmlUtil.escapeJS(dayParam) %>');

						if (dayField) {
							dayField.disabled = checked;

							if (checked) {
								dayField.value = '';
							}
						}

						var inputDateField = Liferay.Util.getFormElement(form, '<%= HtmlUtil.getAUICompatibleId(name) %>');

						if (inputDateField) {
							inputDateField.disabled = checked;

							if (checked) {
								inputDateField.value = '';
							}
						}

						var monthField = Liferay.Util.getFormElement(form, '<%= HtmlUtil.escapeJS(monthParam) %>');

						if (monthField) {
							monthField.disabled = checked;

							if (checked) {
								monthField.value = '';
							}
						}

						var yearField = Liferay.Util.getFormElement(form, '<%= HtmlUtil.escapeJS(yearParam) %>');

						if (yearField) {
							yearField.disabled = checked;

							if (checked) {
								yearField.value = '';
							}
						}
					}
				);
			}
		})();
	</script>
</c:if>

<aui:script use='<%= "aui-datepicker" + (BrowserSnifferUtil.isMobile(request) ? "-native" : StringPool.BLANK) %>'>
	Liferay.component(
		'<%= nameId %>DatePicker',
		function() {
			var keysPressed = {};

			var onKeyDown = function (domEvent) {
				if (domEvent.keyCode === 16) {
					keysPressed[domEvent.keyCode] = true;
				}
			};

			var onKeyUp = function (domEvent) {
				if (domEvent.keyCode === 16) {
					delete keysPressed[domEvent.keyCode];
				}
			};

			var closePopoverOnKeyboardNavigation = function (instance) {
				instance.hide();

				keysPressed = {};

				var trigger = A.one('#<%= nameId %>');

				if (trigger) {
					Liferay.Util.focusFormField(trigger);
				}
			}

			var datePicker = new A.DatePicker<%= BrowserSnifferUtil.isMobile(request) ? "Native" : StringPool.BLANK %>(
				{
					calendar: {
						<%
						String calendarOptions = String.format("headerRenderer: '%s'", LanguageUtil.get(resourceBundle, "b-y"));

						if (lastEnabledDate != null) {
							calendarOptions += StringPool.COMMA + String.format("maximumDate: new Date(%s)", lastEnabledDate.getTime());
						}

						if (firstEnabledDate != null) {
							calendarOptions += StringPool.COMMA + String.format("minimumDate: new Date(%s)", firstEnabledDate.getTime());
						}

						if (firstDayOfWeek != -1) {
							calendarOptions += StringPool.COMMA + String.format("'strings.first_weekday': %d", firstDayOfWeek);
						}
						%>

						<%= calendarOptions %>
					},
					container: '#<%= randomNamespace %>displayDate',
					mask: '<%= mask %>',
					on: {
						destroy: function () {
							document.removeEventListener('keydown', onKeyDown);
							document.removeEventListener('keyup', onKeyUp);
						},
						disabledChange: function(event) {
							var instance = this;

							var container = instance.get('container');

							var newVal = event.newVal;

							container.one('#<%= dayParamId %>').attr('disabled', newVal);
							container.one('#<%= monthParamId %>').attr('disabled', newVal);
							container.one('#<%= nameId %>').attr('disabled', newVal);
							container.one('#<%= yearParamId %>').attr('disabled', newVal);
						},
						enterKey: function() {
							var instance = this;

							var inputVal = instance.get('activeInput').val();

							var date = instance.getParsedDatesFromInputValue(inputVal);

							if (date) {
								datePicker.updateValue(date[0]);
							}
							else if (<%= nullable %> && !date) {
								datePicker.updateValue('');
							}

							var countInterval = 0;

							var intervalId = setInterval(function () {
								var trigger = A.one('.datepicker-popover:not(.popover-hidden) .yui3-calendarnav-prevmonth');

								if (trigger) {
									Liferay.Util.focusFormField(trigger);
									clearInterval(intervalId);
								} else if (countInterval > 10) {
									clearInterval(intervalId);
								}

								countInterval++;
							}, 100);
						},
						init: function () {
							document.addEventListener('keydown', onKeyDown);
							document.addEventListener('keyup', onKeyUp);
						},
						selectionChange: function(event) {
							var newSelection = event.newSelection[0];

							var nullable = <%= nullable %>;

							var date = A.DataType.Date.parse(newSelection);
							var invalidNumber = isNaN(newSelection);

							if ((invalidNumber && !nullable) || (invalidNumber && !date && nullable && newSelection) || (newSelection.getFullYear() > 9999)) {
								event.newSelection[0] = new Date();

								const instance = this;

								const container = instance.get('container');

								let year = container.one('#<%= yearParamId %>');

								const fullYear = newSelection.getFullYear();

								year.val(fullYear);

								const parentNode = A.one('#<%= nameId %>')._node.parentElement;

								const feedbackElement = parentNode.querySelector('.form-feedback-item');

								if (!feedbackElement) {
									let input = A.one('#<%= nameId %>');

									let changeAlert = document.createElement('div');
									let feedback = document.createElement('span');
									let sr = document.createElement('span');

									changeAlert.className = 'form-feedback-item';
									changeAlert.role = 'alert';
									feedback.className = 'form-feedback-indicator';
									feedback.innerHTML = 'The Date has been corrected.';
									sr.className = 'sr-only'
									sr.className = 'New Date: ' + event.newSelection[0];

									changeAlert.append(feedback);
									changeAlert.append(sr);
									input._node.insertAdjacentElement('afterEnd', changeAlert);
								}
							}

							var updatedVal = '';

							if (event.newSelection[0]) {
								updatedVal = event.newSelection[0];
							}

							datePicker.updateValue(updatedVal);
						}
					},
					popover: {
						on: {
							keydown: function(event) {
								var instance = this;

								var domEvent = event.domEvent;

								keysPressed[domEvent.keyCode] = true;

								var isTabPressed = domEvent.keyCode === 9 || keysPressed[9];

								var isShiftPressed = domEvent.keyCode === 16 || keysPressed[16];

								var isForwardNavigation = isTabPressed && !isShiftPressed;

								var isEscapePressed = domEvent.keyCode === 27 || keysPressed[27];

								var hasClassName = domEvent.target.hasClass('yui3-calendar-grid') ||
								domEvent.target.hasClass('yui3-calendar-day');

								if ((isForwardNavigation && hasClassName) || isEscapePressed) {
									closePopoverOnKeyboardNavigation(instance);
								}
							},
							keyup: function(event) {
								var instance = this;

								var domEvent = event.domEvent;

								var isTabPressed = domEvent.keyCode === 9 || keysPressed[9];

								var isShiftPressed = domEvent.keyCode === 16 || keysPressed[16];

								var isBackwardNavigation = isTabPressed && isShiftPressed;

								var hasClassName = domEvent.target.hasClass('yui3-calendar-focused');

								if (isBackwardNavigation && hasClassName) {
									closePopoverOnKeyboardNavigation(instance);
								}

								delete keysPressed[domEvent.keyCode];
							}
						},
						zIndex: Liferay.zIndex.POPOVER
					},
					trigger: '#<%= nameId %>'
				}
			);

			datePicker.getDate = function() {
				var instance = this;

				var container = instance.get('container');

				return new Date(container.one('#<%= yearParamId %>').val(), container.one('#<%= monthParamId %>').val(), container.one('#<%= dayParamId %>').val());
			};

			datePicker.updateValue = function(date) {
				const instance = this;

				const container = instance.get('container');

				let dateVal = '';
				let monthVal = '';
				let yearVal = '';

				if (date && !isNaN(date)) {
					dateVal = date.getDate();
					monthVal = date.getMonth();
					yearVal = date.getFullYear();
				}

				let day = container.one('#<%= dayParamId %>');
				let month = container.one('#<%= monthParamId %>');
				let year = container.one('#<%= yearParamId %>');

				if (!(yearVal < 9999 && year._node.value > 9999)) {
					if (day._node.value != dateVal || month._node.value != monthVal || year._node.value !=yearVal) {
						const parentNode = A.one('#<%= nameId %>')._node.parentElement;

						const feedbackElement = parentNode.querySelector('.form-feedback-item');

						if (feedbackElement) {
							parentNode.removeChild(feedbackElement);
						}
					}
				}

				day.val(dateVal);
				month.val(monthVal);
				year.val(yearVal);
			};

			datePicker.after(
				'selectionChange',
				function() {
					var input = A.one('#<%= nameId %>');

					if (input) {
						var form = input.get('form');

						var formId = form.get('id');

						var formInstance = Liferay.Form.get(formId);

						if (formInstance && formInstance.formValidator) {
							formInstance.formValidator.validateField('<%= namespace + HtmlUtil.escape(name) %>');
						}
					}
				}
			);

			Liferay.once(
				'screenLoad',
				function() {
					datePicker.destroy();
				}
			);

			return datePicker;
		}
	);

	Liferay.component('<%= nameId %>DatePicker');
</aui:script>

<%!
private static final String _SIMPLE_DATE_FORMAT_PATTERN_HTML5 = "yyyy-MM-dd";

private static final String _MASK_YMD = "%Y/%m/%d";
%>