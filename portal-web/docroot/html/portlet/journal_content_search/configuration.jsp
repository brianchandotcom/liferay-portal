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

<%@ include file="/html/portlet/journal_content_search/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<div class="alert alert-info">
		<liferay-ui:message key="define-the-behavior-of-this-search" />
	</div>

	<aui:fieldset>
		<aui:select label="web-content-type" name="preferences--type--">
			<aui:option value="" />

			<%
			for (int i = 0; i < JournalArticleConstants.TYPES.length; i++) {
			%>

				<aui:option label="<%= JournalArticleConstants.TYPES[i] %>" selected="<%= type.equals(JournalArticleConstants.TYPES[i]) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input label="only-show-results-for-web-content-listed-in-a-web-content-display-portlet" name="preferences--showListed--" type="checkbox" value="<%= showListed %>" />

		<div class="<%= !showListed ? StringPool.BLANK : " hide" %>" id="<portlet:namespace />webContentDisplay">
			<aui:input cssClass="lfr-input-text-container" name="preferences--targetPortletId--" value="<%= targetPortletId %>" />
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />showListedCheckbox','<portlet:namespace />webContentDisplay', true);
</aui:script>