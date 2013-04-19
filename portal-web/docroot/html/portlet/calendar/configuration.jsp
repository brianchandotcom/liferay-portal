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

<%@ include file="/html/portlet/calendar/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", CalUtil.getEmailFromName(preferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", CalUtil.getEmailFromAddress(preferences, company.getCompanyId()));

Boolean emailEventReminderEnabled = ParamUtil.getBoolean(request, "preferences--emailEventReminderEnabled--", CalUtil.getEmailEventReminderEnabled(preferences));

String emailSubject = ParamUtil.getString(request, "preferences--emailEventReminderSubject--", CalUtil.getEmailEventReminderSubject(preferences));
String emailBody = ParamUtil.getString(request, "preferences--emailEventReminderBody--", CalUtil.getEmailEventReminderBody(preferences));

String editorParam = "emailEventReminderBody";
String editorContent = emailBody;
%>

<liferay-portlet:renderURL portletConfiguration="true" var="portletURL">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:tabs
		names="email-from,event-reminder-email,display-settings"
		param="tabs2"
		url="<%= portletURL %>"
	/>

	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
	<liferay-ui:error key="emailEventReminderBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailEventReminderSubject" message="please-enter-a-valid-subject" />

	<c:choose>
		<c:when test='<%= tabs2.equals("email-from") %>'>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" type="text" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" type="text" value="<%= emailFromAddress %>" />
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("event-reminder-email") %>'>
			<aui:fieldset>
				<aui:input label="enabled" name="preferences--emailEventReminderEnabled--" type="checkbox" value="<%= emailEventReminderEnabled %>" />

				<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailEventReminderSubject--" type="text" value="<%= emailSubject %>" />

				<aui:field-wrapper label="body">
					<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

					<aui:input name='<%= "preferences--" + editorParam + "--" %>' type="hidden" />
				</aui:field-wrapper>
			</aui:fieldset>

			<div class="definition-of-terms">
				<h4><liferay-ui:message key="definition-of-terms" /></h4>

				<dl>
					<dt>
						[$EVENT_LOCATION$]
					</dt>
					<dd>
						<liferay-ui:message key="the-event-location" />
					</dd>
					<dt>
						[$EVENT_START_DATE$]
					</dt>
					<dd>
						<liferay-ui:message key="the-event-start-date" />
					</dd>
					<dt>
						[$EVENT_TITLE$]
					</dt>
					<dd>
						<liferay-ui:message key="the-event-title" />
					</dd>
					<dt>
						[$FROM_ADDRESS$]
					</dt>
					<dd>
						<%= HtmlUtil.escape(emailFromAddress) %>
					</dd>
					<dt>
						[$FROM_NAME$]
					</dt>
					<dd>
						<%= HtmlUtil.escape(emailFromName) %>
					</dd>
					<dt>
						[$PORTAL_URL$]
					</dt>
					<dd>
						<%= company.getVirtualHostname() %>
					</dd>
					<dt>
						[$PORTLET_NAME$]
					</dt>
					<dd>
						<%= PortalUtil.getPortletTitle(renderResponse) %>
					</dd>
					<dt>
						[$TO_ADDRESS$]
					</dt>
					<dd>
						<liferay-ui:message key="the-address-of-the-email-recipient" />
					</dd>
					<dt>
						[$TO_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-name-of-the-email-recipient" />
					</dd>
				</dl>
			</div>
		</c:when>
		<c:when test='<%= tabs2.equals("display-settings") %>'>
			<aui:fieldset label="default-tab">
				<aui:select label="default-tab" name="preferences--tabs1Default--">

					<%
					for (String tabs1Name : tabs1NamesArray) {
					%>

						<aui:option label="<%= tabs1Name %>" selected="<%= tabs1Default.equals(tabs1Name) %>" />

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>

			<aui:fieldset label="summary-tab">
				<aui:select label="summary-tab" name="preferences--summaryTabOrientation--">
					<aui:option label="horizontal" selected='<%= summaryTabOrientation.equals("horizontal") %>' />
					<aui:option label="vertical" selected='<%= summaryTabOrientation.equals("vertical") %>' />
				</aui:select>

				<aui:input label="show-mini-month" name="preferences--summaryTabShowMiniMonth--" type="checkbox" value="<%= summaryTabShowMiniMonth %>" />

				<aui:input label="show-todays-events" name="preferences--summaryTabShowTodaysEvents--" type="checkbox" value="<%= summaryTabShowTodaysEvents %>" />
			</aui:fieldset>

			<aui:fieldset label="events">
				<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= enableRelatedAssets %>" />

				<c:if test="<%= PropsValues.CALENDAR_EVENT_RATINGS_ENABLED %>">
					<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= enableRatings %>" />
				</c:if>

				<c:if test="<%= PropsValues.CALENDAR_EVENT_COMMENTS_ENABLED %>">
					<aui:input name="preferences--enableComments--" type="checkbox" value="<%= enableComments %>" />
				</c:if>
			</aui:fieldset>
		</c:when>
	</c:choose>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(editorContent) %>";
	}

	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.equals("event-reminder-email") %>'>
			document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = window.<portlet:namespace />editor.getHTML();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.calendar.configuration.jsp";
%>