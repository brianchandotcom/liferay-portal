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
String className = ParamUtil.getString(request, "className");

long websiteId = ParamUtil.getLong(request, "primaryKey", 0L);

Website website = null;

if (websiteId > 0L) {
	website = WebsiteServiceUtil.getWebsite(websiteId);
}
%>

<aui:form cssClass="modal-body" name="fm">
	<clay:alert
		message='<%= LanguageUtil.format(request, "url-must-start-with-x-or-x", new String[] {"http://", "https://"}, false) %>'
		style="info"
		title='<%= LanguageUtil.get(request, "info") + ":" %>'
	/>

	<aui:model-context bean="<%= website %>" model="<%= Website.class %>" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= websiteId %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.WEBSITE %>" />

	<aui:input checked="<%= (website != null)? website.isPrimary() : false %>" id="websitePrimary" label="make-primary" name="websitePrimary" type="checkbox" />

	<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.WEBSITE %>" message="please-select-a-type" />

	<aui:select inlineField="<%= true %>" label="type" listType="<%= className + ListTypeConstants.WEBSITE %>" name="websiteTypeId" />

	<liferay-ui:error exception="<%= WebsiteURLException.class %>" message="please-enter-a-valid-url" />

	<aui:input fieldParam="websiteUrl" id="websiteUrl" name="url" required="<%= true %>" />
</aui:form>