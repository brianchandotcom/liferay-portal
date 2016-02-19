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

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="alert alert-info">
		<liferay-ui:message key="define-the-behavior-of-this-search" />
	</div>

	<aui:fieldset>
		<aui:input label="only-show-results-for-web-content-listed-in-a-web-content-display-portlet" name="preferences--showListed--" type="checkbox" value="<%= journalContentSearchPortletInstanceConfiguration.showListed() %>" />

		<div class="<%= !journalContentSearchPortletInstanceConfiguration.showListed() ? StringPool.BLANK : " hide" %>" id="<portlet:namespace />webContentDisplay">
			<aui:input cssClass="lfr-input-text-container" name="preferences--targetPortletId--" value="<%= journalContentSearchPortletInstanceConfiguration.targetPortletId() %>" />
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />showListed', '<portlet:namespace />webContentDisplay', true);
</aui:script>