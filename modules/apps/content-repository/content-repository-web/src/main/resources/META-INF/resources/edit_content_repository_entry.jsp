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
ContentRepositoryEntry contentRepositoryEntry = (ContentRepositoryEntry)request.getAttribute("contentRepositoryEntry");

Group group = GroupServiceUtil.getGroup(contentRepositoryEntry.getGroupId());

portletDisplay.setShowBackIcon(true);

String backURL = ParamUtil.getString(request, "redirect");

portletDisplay.setURLBack(backURL);

renderResponse.setTitle(HtmlUtil.escape(group.getDescriptiveName(locale)));
%>

<liferay-ui:success key='<%= ContentRepositoryPortletKeys.CONTENT_REPOSITORY_ADMIN + "requestProcessed" %>' message="repository-was-added" />

<liferay-frontend:edit-form
	action="<%= ContentRepositoryEntryURLUtil.getEditContentRepositoryEntryActionURL(liferayPortletResponse) %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= backURL %>" />
	<aui:input name="contentRepositoryEntryId" type="hidden" value="<%= contentRepositoryEntry.getContentRepositoryEntryId() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsible="false"
				label='<%= LanguageUtil.get(request, "details") %>'
			>
				<aui:model-context bean="<%= group %>" model="<%= Group.class %>" />

				<aui:input name="repositoryId" type="resource" value="<%= String.valueOf(group.getGroupId()) %>" />

				<aui:input name="name" placeholder="name" required="<%= true %>" value="<%= String.valueOf(group.getName(locale)) %>" />

				<aui:input name="description" placeholder="description" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>