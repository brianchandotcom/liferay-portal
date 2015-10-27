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

<%@ include file="/configuration_header/init.jsp" %>

<aui:fieldset cssClass="options-group" label="<%= label %>">
	<c:choose>
		<c:when test='<%= SessionMessages.contains(liferayPortletRequest, portletDisplay.getId() + "name") %>'>
			<aui:model-context bean="<%= null %>" model="<%= ExportImportConfiguration.class %>" />

			<%
			String name = (String)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "name");
			String description = (String)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "description");
			%>

			<aui:input label="name" name="name" showRequiredLabel="<%= false %>" value="<%= HtmlUtil.escape(name) %>">
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="description" name="description" value="<%= HtmlUtil.escape(description) %>" />
		</c:when>
		<c:otherwise>
			<aui:model-context bean="<%= exportImportConfiguration %>" model="<%= ExportImportConfiguration.class %>" />

			<aui:input label="name" name="name" showRequiredLabel="<%= false %>">
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="description" name="description" />
		</c:otherwise>
	</c:choose>
</aui:fieldset>