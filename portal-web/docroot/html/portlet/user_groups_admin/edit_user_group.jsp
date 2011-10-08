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

<%@ include file="/html/portlet/user_groups_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

UserGroup userGroup = (UserGroup)request.getAttribute(WebKeys.USER_GROUP);

long userGroupId = BeanParamUtil.getLong(userGroup, request, "userGroupId");
%>

<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveUserGroup();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="userGroupId" type="hidden" value="<%= userGroupId %>" />

	<liferay-util:include page="/html/portlet/user_groups_admin/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value='<%= (userGroup == null) ? "add" : "view" %>' />
	</liferay-util:include>

	<liferay-ui:header
		backURL="<%= backURL %>"
		localizeTitle="<%= (userGroup == null) %>"
		title='<%= (userGroup == null) ? "new-user-group" : userGroup.getName() %>'
	/>

	<liferay-ui:error exception="<%= DuplicateUserGroupException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= RequiredUserGroupException.class %>" message="this-is-a-required-user-group" />
	<liferay-ui:error exception="<%= UserGroupNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= userGroup %>" model="<%= UserGroup.class %>" />

	<aui:fieldset>
		<c:if test="<%= userGroup != null %>">
			<aui:field-wrapper label="old-name">
				<%= HtmlUtil.escape(userGroup.getName()) %>
			</aui:field-wrapper>
		</c:if>

		<aui:input label='<%= (userGroup != null) ? "new-name" : "name" %>' name="name" />

		<aui:input name="description" />

		<%
		List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
		%>

		<%
		LayoutSetPrototype publicLayoutSetPrototype = null;
		LayoutSetPrototype privateLayoutSetPrototype = null;

		long userGroupUserCount = 0;

		if (userGroup != null) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

			params.put("usersUserGroups", userGroup.getPrimaryKey());

			userGroupUserCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, 0, params);

			if (userGroup.getPublicLayoutSetPrototypeId() > 0) {
				publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(userGroup.getPublicLayoutSetPrototypeId());
			}

			if (userGroup.getPrivateLayoutSetPrototypeId() > 0) {
				privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(userGroup.getPrivateLayoutSetPrototypeId());
			}
		}
		%>

		<c:if test="<%= (userGroup != null) || !layoutSetPrototypes.isEmpty() %>">
			<liferay-ui:panel defaultState="closed" extended="<%= false %>" helpMessage="personal-site-template-help" id="userGroupSiteTemplatePanel" persistState="<%= true %>" title="personal-site-template">
				<aui:field-wrapper label="public-pages">
					<c:choose>
						<c:when test="<%= (publicLayoutSetPrototype == null) || ((userGroup == null) || (userGroupUserCount == 0)) && !layoutSetPrototypes.isEmpty() %>">
							<aui:select inlineField="<%= true %>" label="" name="publicLayoutSetPrototypeId">
								<aui:option label="none" selected="<%= true %>" value="" />

								<%
								for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
								%>

									<aui:option value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= layoutSetPrototype.getName(user.getLanguageId()) %></aui:option>

								<%
								}
								%>

							</aui:select>

							<c:if test="<%= (publicLayoutSetPrototype != null) && LayoutSetPrototypePermissionUtil.contains(permissionChecker, publicLayoutSetPrototype.getPrimaryKey(), ActionKeys.UPDATE) %>">
								<liferay-portlet:renderURL portletName="<%= PortletKeys.LAYOUT_SET_PROTOTYPE %>" var="editLayoutSetPrototypeURL">
									<portlet:param name="struts_action" value="/layout_set_prototypes/edit_layout_set_prototype" />
									<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(userGroup.getPublicLayoutSetPrototypeId()) %>" />
								</liferay-portlet:renderURL>

								<liferay-ui:icon
									image="edit"
									label="<%= true %>"
									method="get"
									url="<%= editLayoutSetPrototypeURL %>"
								/>
							</c:if>
						</c:when>
						<c:otherwise>
							<aui:input name="publicLayoutSetPrototypeId" type="hidden" value="<%= String.valueOf(userGroup.getPublicLayoutSetPrototypeId()) %>" />

							<c:choose>
								<c:when test="<%= (userGroup != null) && (userGroupUserCount > 0) && (publicLayoutSetPrototype != null) && LayoutSetPrototypePermissionUtil.contains(permissionChecker, publicLayoutSetPrototype.getPrimaryKey(), ActionKeys.UPDATE) %>">
									<liferay-portlet:renderURL portletName="<%= PortletKeys.LAYOUT_SET_PROTOTYPE %>" var="editLayoutSetPrototypeURL">
										<portlet:param name="struts_action" value="/layout_set_prototypes/edit_layout_set_prototype" />
										<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(userGroup.getPublicLayoutSetPrototypeId()) %>" />
									</liferay-portlet:renderURL>

									<%
									String title = LanguageUtil.format(pageContext, "edit-x-site-template", publicLayoutSetPrototype.getName(locale));
									%>

									<aui:a href="<%= editLayoutSetPrototypeURL %>" label="<%= publicLayoutSetPrototype.getName(locale) %>" title="<%= title %>" />
								</c:when>
								<c:when test="<%= (userGroup != null) && (userGroupUserCount > 0) %>">
									<%= publicLayoutSetPrototype.getName() %>
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="this-user-group-does-not-have-any-public-pages" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</aui:field-wrapper>

				<aui:field-wrapper label="private-pages">
					<c:choose>
						<c:when test="<%= (privateLayoutSetPrototype == null) || ((userGroup == null) || (userGroupUserCount == 0)) && !layoutSetPrototypes.isEmpty() %>">
							<aui:select inlineField="<%= true %>" label="" name="privateLayoutSetPrototypeId">
								<aui:option label="none" selected="<%= true %>" value="" />

								<%
								for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
								%>

									<aui:option value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= layoutSetPrototype.getName(user.getLanguageId()) %></aui:option>

								<%
								}
								%>

							</aui:select>

							<c:if test="<%= (privateLayoutSetPrototype != null) && LayoutSetPrototypePermissionUtil.contains(permissionChecker, privateLayoutSetPrototype.getPrimaryKey(), ActionKeys.UPDATE) %>">
								<liferay-portlet:renderURL portletName="<%= PortletKeys.LAYOUT_SET_PROTOTYPE %>" var="editLayoutSetPrototypeURL">
									<portlet:param name="struts_action" value="/layout_set_prototypes/edit_layout_set_prototype" />
									<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(userGroup.getPrivateLayoutSetPrototypeId()) %>" />
								</liferay-portlet:renderURL>

								<liferay-ui:icon
									image="edit"
									label="<%= true %>"
									method="get"
									url="<%= editLayoutSetPrototypeURL %>"
								/>
							</c:if>
						</c:when>
						<c:otherwise>
							<aui:input name="privateLayoutSetPrototypeId" type="hidden" value="<%= String.valueOf(userGroup.getPrivateLayoutSetPrototypeId()) %>" />

							<c:choose>
								<c:when test="<%= (userGroup != null) && (userGroupUserCount > 0) && (privateLayoutSetPrototype != null) && LayoutSetPrototypePermissionUtil.contains(permissionChecker, privateLayoutSetPrototype.getPrimaryKey(), ActionKeys.UPDATE) %>">
									<liferay-portlet:renderURL portletName="<%= PortletKeys.LAYOUT_SET_PROTOTYPE %>" var="editLayoutSetPrototypeURL">
										<portlet:param name="struts_action" value="/layout_set_prototypes/edit_layout_set_prototype" />
										<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(userGroup.getPrivateLayoutSetPrototypeId()) %>" />
									</liferay-portlet:renderURL>

									<%
									String title = LanguageUtil.format(pageContext, "edit-x-site-template", privateLayoutSetPrototype.getName(locale));
									%>

									<aui:a href="<%= editLayoutSetPrototypeURL %>" label="<%= privateLayoutSetPrototype.getName(locale) %>" title="<%= title %>" />
								</c:when>
								<c:when test="<%= (userGroup != null) && (userGroupUserCount > 0) %>">
									<%= privateLayoutSetPrototype.getName() %>
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="this-user-group-does-not-have-any-private-pages" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</aui:field-wrapper>
			</liferay-ui:panel>
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveUserGroup() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (userGroup == null) ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/user_groups_admin/edit_user_group" /></portlet:actionURL>");
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</aui:script>

<%
if (userGroup != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, userGroup.getName(), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-user-group"), currentURL);
}
%>