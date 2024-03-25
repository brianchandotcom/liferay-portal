<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPOptionDisplayContext cpOptionDisplayContext = (CPOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long cpOptionId = ParamUtil.getLong(request, "cpOptionId");
%>

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "create-new-option-value") %>'
>
	<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit();" %>'>
		<c:choose>
			<c:when test="<%= cpOptionDisplayContext.isCPOptionSelectDate() %>">

				<%
				Calendar calendar = Calendar.getInstance();
				%>

				<div class="row">
					<div class="col-4">
						<div class="form-group input-date-wrapper">
							<label for="date"><liferay-ui:message key="date" /></label>

							<liferay-ui:input-date
								dayParam="day"
								dayValue="<%= calendar.get(Calendar.DAY_OF_MONTH) %>"
								disabled="<%= false %>"
								monthParam="month"
								monthValue="<%= calendar.get(Calendar.MONTH) %>"
								name="date"
								required="<%= true %>"
								yearParam="year"
								yearValue="<%= calendar.get(Calendar.YEAR) %>"
							/>
						</div>
					</div>

					<div class="col-4">
						<div class="form-group input-date-wrapper">
							<label for="time"><liferay-ui:message key="time" /></label>

							<liferay-ui:input-time
								amPmParam="amPm"
								amPmValue="<%= calendar.get(Calendar.AM_PM) %>"
								disabled="<%= false %>"
								hourParam="hour"
								hourValue="<%= calendar.get(Calendar.HOUR) %>"
								minuteParam="minute"
								minuteValue="<%= calendar.get(Calendar.MINUTE) %>"
								name="time"
							/>
						</div>
					</div>

					<div class="col-4">
						<div class="form-group input-date-wrapper">
							<label for="timeZone"><liferay-ui:message key="time-zone" /></label>

							<liferay-ui:input-time-zone
								name="timeZone"
								value="<%= timeZone.getID() %>"
							/>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-6">
						<aui:input name="duration" required="<%= true %>" type="text">
							<aui:validator name="min">1</aui:validator>
							<aui:validator name="digits" />
						</aui:input>
					</div>

					<div class="col-6">
						<aui:select label="duration-type" name="durationType">
							<aui:option label="hours" value="<%= CPConstants.HOURS_DURATION_TYPE %>" />
							<aui:option label="days" value="<%= CPConstants.DAYS_DURATION_TYPE %>" />
						</aui:select>
					</div>
				</div>

				<aui:input id="optionValueSelectDateLabel" name="label" readonly="<%= true %>" type="text" />

				<aui:input label="position" name="priority" wrapperCssClass="mb-6" />
			</c:when>
			<c:otherwise>
				<aui:input name="name" required="<%= true %>" type="text" />

				<aui:input helpMessage="key-help" name="key" required="<%= true %>" />

				<aui:input label="position" name="priority" />
			</c:otherwise>
		</c:choose>
	</aui:form>

	<portlet:renderURL var="editOptionURL">
		<portlet:param name="mvcRenderCommandName" value="/cp_options/edit_cp_option" />
		<portlet:param name="cpOptionId" value="<%= String.valueOf(cpOptionId) %>" />
	</portlet:renderURL>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"bcp47LanguageId", LocaleUtil.toBCP47LanguageId(locale)
			).put(
				"cpOptionId", cpOptionId
			).put(
				"defaultLanguageId", LanguageUtil.getLanguageId(LocaleUtil.getDefault())
			).put(
				"editOptionURL", editOptionURL
			).put(
				"isCPOptionSelectDate", cpOptionDisplayContext.isCPOptionSelectDate()
			).put(
				"windowState", LiferayWindowState.MAXIMIZED.toString()
			).build()
		%>'
		module="{addCpOptionValue} from commerce-product-options-web"
	/>
</commerce-ui:modal-content>