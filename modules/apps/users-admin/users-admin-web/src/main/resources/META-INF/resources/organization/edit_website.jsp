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
long entryId = ParamUtil.getLong(request, "entryId", 0L);

Website website = null;

if (entryId > 0L) {
	website = WebsiteServiceUtil.getWebsite(entryId);
}
%>

<aui:form cssClass="modal-body" name="fm">
	<aui:model-context bean="<%= website %>" model="<%= Website.class %>" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.WEBSITE %>" />

	<aui:input checked="<%= (website != null)? website.isPrimary() : false %>" id="websitePrimary" label="make-primary" name="websitePrimary" type="checkbox" />

	<aui:select inlineField="<%= true %>" label="type" listType="<%= Organization.class.getName() + ListTypeConstants.WEBSITE %>" name="websiteTypeId" />

	<aui:input fieldParam="websiteUrl" id="websiteUrl" name="url" required="<%= true %>" />
</aui:form>