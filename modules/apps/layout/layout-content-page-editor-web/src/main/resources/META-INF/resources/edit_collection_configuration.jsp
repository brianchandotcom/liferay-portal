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
EditCollectionConfigurationDisplayContext editCollectionConfigurationDisplayContext = (EditCollectionConfigurationDisplayContext)request.getAttribute(EditCollectionConfigurationDisplayContext.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(editCollectionConfigurationDisplayContext.getRedirect());

renderResponse.setTitle(LanguageUtil.get(request, "filter-collection"));
%>

<liferay-frontend:edit-form
	action="<%= editCollectionConfigurationDisplayContext.getActionURL() %>"
	method="post"
	name="fm"
>
	<aui:input name="classNameId" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getClassNameId() %>" />
	<aui:input name="classPK" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getClassPK() %>" />
	<aui:input name="collectionKey" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getCollectionKey() %>" />
	<aui:input name="itemId" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getItemId() %>" />
	<aui:input name="plid" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getPlid() %>" />
	<aui:input name="redirect" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getRedirect() %>" />
	<aui:input name="segmentsExperienceId" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getSegmentsExperienceId() %>" />
	<aui:input name="type" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getType() %>" />

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= editCollectionConfigurationDisplayContext.getRedirect() %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>