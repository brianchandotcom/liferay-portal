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
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

RedirectEntry redirectEntry = (RedirectEntry)request.getAttribute(RedirectEntry.class.getName());

if (redirectEntry == null) {
	renderResponse.setTitle(LanguageUtil.get(request, "new-redirect"));
}
else {
	renderResponse.setTitle(LanguageUtil.get(request, "edit-redirect"));
}

RedirectDisplayContext redirectDisplayContext = new RedirectDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
%>

<portlet:actionURL name="/redirect_entry/edit" var="editRedirectEntryURL" />

<liferay-frontend:edit-form
	action="<%= editRedirectEntryURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />

	<c:if test="<%= redirectEntry != null %>">
		<aui:input name="redirectEntryId" type="hidden" value="<%= redirectEntry.getRedirectEntryId() %>" />
	</c:if>

	<liferay-frontend:edit-form-body>
		<aui:field-wrapper cssClass="form-group" label="source-url" name="sourceURL" required="<%= true %>">
			<div class="form-text"><%= redirectDisplayContext.getGroupBaseURL() %></div>

			<aui:input autoFocus="<%= true %>" label="" name="sourceURL" prefix="/" required="<%= true %>" type="text" value="<%= (redirectEntry != null) ? redirectEntry.getSourceURL() : null %>" />
		</aui:field-wrapper>

		<aui:input name="destinationURL" required="<%= true %>" value="<%= (redirectEntry != null) ? redirectEntry.getDestinationURL() : null %>" />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>