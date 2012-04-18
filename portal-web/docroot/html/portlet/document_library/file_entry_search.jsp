<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));
%>

<liferay-portlet:resourceURL var="searchURL">
	<portlet:param name="struts_action" value="/document_library/search" />
	<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
	<portlet:param name="searchRepositoryId" value="<%= String.valueOf(folderId) %>" />
	<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
	<portlet:param name="searchFolderId" value="<%= String.valueOf(folderId) %>" />
</liferay-portlet:resourceURL>

<div class="lfr-search-combobox search-button-container" id="<portlet:namespace />fileEntrySearchContainer">
	<aui:form action="<%= searchURL.toString() %>" method="get" name="fm1" onSubmit="event.preventDefault();">

		<aui:layout>
			<aui:column columnWidth="70">
				<aui:input cssClass="keywords lfr-search-combobox-item" id="keywords" label="" name="keywords" type="text" />
			</aui:column>

			<aui:column columnWidth="30">
				<aui:button cssClass="lfr-search-combobox-item" name="search" type="submit" value="search" />
			</aui:column>
		</aui:layout>
	</aui:form>
</div>