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

<%@ include file="/html/portlet/user_groups_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

UserGroup userGroup = (UserGroup)request.getAttribute(WebKeys.USER_GROUP);

long userGroupId = BeanParamUtil.getLong(userGroup, request, "userGroupId");

boolean hasUserGroupUpdatePermission = true;

if (userGroup != null) {
	hasUserGroupUpdatePermission = UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.UPDATE);
}
%>

<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveUserGroup();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="userGroupId" type="hidden" value="<%= userGroupId %>" />

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
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" disabled="<%= !hasUserGroupUpdatePermission %>" label='<%= (userGroup != null) ? "new-name" : "name" %>' name="name" />

		<aui:input disabled="<%= !hasUserGroupUpdatePermission %>" name="description" />

		<liferay-ui:custom-attributes-available className="<%= UserGroup.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= UserGroup.class.getName() %>"
				classPK="<%= userGroupId %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

	</aui:fieldset>

	<%
	Group userGroupGroup = null;

	if (userGroup != null) {
		userGroupGroup = userGroup.getGroup();
	}

	LayoutSet layoutSet = null;
	LayoutSetPrototype layoutSetPrototype = null;
	boolean layoutSetPrototypeLinkEnabled = true;

	if (userGroupGroup != null) {
		try {
			LayoutLocalServiceUtil.getLayouts(userGroupGroup.getGroupId(), LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(userGroupGroup.getGroupId());

			layoutSetPrototypeLinkEnabled = layoutSet.isLayoutSetPrototypeLinkEnabled();

			String layoutSetPrototypeUuid = layoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
			}
		}
		catch (Exception e) {
		}
	}

	List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
	%>

	<c:if test="<%= (userGroupGroup != null) || !layoutSetPrototypes.isEmpty() %>">
		<aui:fieldset helpMessage="user-group-site-help" label="user-group-site">

			<%
			boolean hasUnlinkLayoutSetPrototypePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);

			boolean hasUpdateSitePermission = false;

			if (userGroupGroup != null) {
				hasUpdateSitePermission = GroupPermissionUtil.contains(permissionChecker, userGroupGroup, ActionKeys.UPDATE);
			}
			else {
				for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
					if (GroupPermissionUtil.contains(permissionChecker, curLayoutSetPrototype.getGroup(), ActionKeys.UPDATE)) {
						hasUpdateSitePermission = true;
					}
				}
			}
			%>

			<c:choose>
				<c:when test="<%= ((userGroupGroup == null) || ((layoutSetPrototype == null) && !userGroupGroup.hasLayouts())) && !layoutSetPrototypes.isEmpty() %>">
					<aui:select disabled="<%= !hasUpdateSitePermission || !hasUserGroupUpdatePermission %>" label="pages" name="layoutSetPrototypeId">
						<aui:option label="none" selected="<%= true %>" value="" />

						<%
						for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
						%>

							<aui:option value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(curLayoutSetPrototype.getName(locale)) %></aui:option>

						<%
						}
						%>

					</aui:select>

					<c:choose>
						<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
							<div class="hide" id="<portlet:namespace />layoutSetPrototypeIdOptions">
								<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="layoutSetPrototypeLinkEnabled" type="checkbox" value="<%= layoutSetPrototypeLinkEnabled %>" />
							</div>
						</c:when>
						<c:otherwise>
							<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<aui:field-wrapper label="pages">
						<c:choose>
							<c:when test="<%= userGroupGroup != null %>">
								<c:choose>
									<c:when test="<%= userGroupGroup.hasLayouts() %>">
										<liferay-ui:icon
											iconCssClass="icon-search"
											label="<%= true %>"
											message="open-pages"
											method="get"
											target="_blank"
											url="<%= userGroupGroup.getDisplayURL(themeDisplay) %>"
										/>
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="this-user-group-does-not-have-any-pages" />
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="<%= (layoutSetPrototype != null) && hasUnlinkLayoutSetPrototypePermission %>">
										<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(layoutSetPrototype.getName(locale)), false) %>' name="layoutSetPrototypeLinkEnabled" type="checkbox" value="<%= layoutSetPrototypeLinkEnabled %>" />
									</c:when>
									<c:when test="<%= layoutSetPrototype != null %>">
										<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(layoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

										<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
									</c:when>
								</c:choose>
							</c:when>
						</c:choose>
					</aui:field-wrapper>
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</c:if>

	<aui:button-row>
		<aui:button disabled="<%= !hasUserGroupUpdatePermission %>" type="submit" />

		<aui:button disabled="<%= !hasUserGroupUpdatePermission %>" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />isVisible(currentValue, value) {
		return currentValue != '';
	}

	function <portlet:namespace />saveUserGroup() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (userGroup == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/edit_user_group" /></portlet:actionURL>');
	}

	Liferay.Util.toggleSelectBox('<portlet:namespace />layoutSetPrototypeId', <portlet:namespace />isVisible, '<portlet:namespace />layoutSetPrototypeIdOptions');
</aui:script>

<%
if (userGroup != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, userGroup.getName(), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-user-group"), currentURL);
}
%>