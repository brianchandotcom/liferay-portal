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
String backURL = ParamUtil.getString(request, "backURL");

CTCollection ctCollection = (CTCollection)request.getAttribute("ctCollection");

long ctCollectionId = 0;
String description = StringPool.BLANK;
String name = StringPool.BLANK;

if (ctCollection != null) {
	ctCollectionId = ctCollection.getCtCollectionId();
	description = ctCollection.getDescription();
	name = ctCollection.getName();
}

portletDisplay.setURLBack(backURL.toString());
portletDisplay.setShowBackIcon(true);
%>

<liferay-portlet:renderURL var="portletURL" />

<liferay-portlet:actionURL name="/change_lists/edit_ct_collection" var="actionURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="/change_lists/view" />
	<liferay-portlet:param name="redirect" value="<%= portletURL.toString() %>" />
	<liferay-portlet:param name="backURL" value="<%= backURL.toString() %>" />
</liferay-portlet:actionURL>

<liferay-ui:error exception="<%= CTCollectionDescriptionCTEngineException.class %>" message="the-change-list-description-is-too-long" />
<liferay-ui:error exception="<%= CTCollectionNameCTEngineException.class %>" message="the-change-list-name-is-too-long" />
<liferay-ui:error key="ctCollectionDuplicate" message="name-is-already-used-by-another-change-list" />
<liferay-ui:error key="ctCollectionName" message="the-change-list-name-is-too-short" />

<div class="custom-sheet sheet sheet-lg">
	<aui:form action='<%= actionURL.toString() + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="addChangeListFm">
		<aui:input name="ctCollectionId" type="hidden" value="<%= ctCollectionId %>" />

		<aui:input label="name" name="name" placeholder="change-list-name-placeholder" value="<%= name %>">
			<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(CTCollection.class.getName(), "name") %></aui:validator>
			<aui:validator name="required" />
		</aui:input>

		<aui:input label="description" name="description" type="textarea" placeholder="change-list-description-placeholder" value="<%= description %>">
			<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(CTCollection.class.getName(), "description") %></aui:validator>
		</aui:input>
		<aui:button-row>
			<aui:button id="saveButton" type="submit" value='<%= LanguageUtil.get(request, "save") %>' />

			<aui:button href="<%= backURL %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>
