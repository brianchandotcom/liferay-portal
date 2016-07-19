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

<aui:form action="<%= configurationActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<liferay-ui:tabs
				names='<%= "user-settings,display-settings,rss" %>'
				param="tabs2"
				refresh="<%= false %>"
				type="tabs nav-tabs-default"
			>
				<liferay-ui:section>
					<%@ include file="/configuration/user_settings.jspf" %>
				</liferay-ui:section>

				<liferay-ui:section>
					<%@ include file="/configuration/display_settings.jspf" %>
				</liferay-ui:section>

				<liferay-ui:section>
					<%@ include file="/configuration/rss.jspf" %>
				</liferay-ui:section>
			</liferay-ui:tabs>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>