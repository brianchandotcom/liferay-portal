<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");

List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
%>

<h3><liferay-ui:message key="personal-site" /></h3>

<aui:fieldset>
	<c:choose>
		<c:when test="<%= ((selUser == null) || (selUser.getPublicLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
			<aui:select label="public-pages" name="publicLayoutSetPrototypeId">
				<aui:option label='<%= selUser == null ? "default" : "none" %>' selected="<%= true %>" value="" />

				<%
				for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
				%>

					<aui:option label="<%= layoutSetPrototype.getName(user.getLanguageId()) %>" value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>" />

				<%
				}
				%>

			</aui:select>
		</c:when>
		<c:otherwise>
			<aui:field-wrapper label="public-pages">
				<c:choose>
					<c:when test="<%= (selUser != null) && (selUser.getPublicLayoutsPageCount() > 0) %>">
						<liferay-portlet:actionURL var="publicPagesURL" portletName="<%= PortletKeys.MY_SITES %>">
							<portlet:param name="struts_action" value="/my_sites/view" />
							<portlet:param name="groupId" value="<%= String.valueOf(selUser.getGroup().getGroupId()) %>" />
							<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
						</liferay-portlet:actionURL>

						<liferay-ui:icon
							image="view"
							label="<%= true %>"
							message="open-public-pages"
							method="get"
							target="_blank"
							url="<%= publicPagesURL.toString() %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="this-user-does-not-have-any-public-pages" />
					</c:otherwise>
				</c:choose>
			</aui:field-wrapper>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="<%= ((selUser == null) || (selUser.getPrivateLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
			<aui:select label="private-pages" name="privateLayoutSetPrototypeId">
				<aui:option label='<%= selUser == null ? "default" : "none" %>' selected="<%= true %>" value="" />

				<%
				for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
				%>

					<aui:option label="<%= layoutSetPrototype.getName(user.getLanguageId()) %>" value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>" />

				<%
				}
				%>

			</aui:select>
		</c:when>
		<c:otherwise>
			<aui:field-wrapper label="private-pages">
				<c:choose>
					<c:when test="<%= (selUser != null) && (selUser.getPrivateLayoutsPageCount() > 0) %>">
						<liferay-portlet:actionURL var="privatePagesURL" portletName="<%= PortletKeys.MY_SITES %>">
							<portlet:param name="struts_action" value="/my_sites/view" />
							<portlet:param name="groupId" value="<%= String.valueOf(selUser.getGroup().getGroupId()) %>" />
							<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
						</liferay-portlet:actionURL>

						<liferay-ui:icon
							image="view"
							label="<%= true %>"
							message="open-private-pages"
							method="get"
							target="_blank"
							url="<%= privatePagesURL.toString() %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="this-user-does-not-have-any-private-pages" />
					</c:otherwise>
				</c:choose>
			</aui:field-wrapper>
		</c:otherwise>
	</c:choose>
</aui:fieldset>

<%
if ((selUser == null) && layoutSetPrototypes.isEmpty()) {
	request.setAttribute(WebKeys.FORM_NAVIGATOR_SECTION_SHOW + "pages", Boolean.FALSE);
}
%>