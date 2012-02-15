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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

LayoutSet privateLayoutSet = null;
LayoutSetPrototype privateLayoutSetPrototype = null;

LayoutSet publicLayoutSet = null;
LayoutSetPrototype publicLayoutSetPrototype = null;

boolean site = false;

if (organization != null) {
	Group organizationGroup = organization.getGroup();

	site = organizationGroup.isSite();

	if (site) {
		try {
			LayoutLocalServiceUtil.getLayouts(organizationGroup.getGroupId(), false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(organizationGroup.getGroupId(), true);

			String layoutSetPrototypeUuid = privateLayoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(layoutSetPrototypeUuid);
			}
		}
		catch (Exception e) {
		}

		try {
			LayoutLocalServiceUtil.getLayouts(organizationGroup.getGroupId(), true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(organizationGroup.getGroupId(), false);

			String layoutSetPrototypeUuid = publicLayoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(layoutSetPrototypeUuid);
			}
		}
		catch (Exception e) {
		}
	}
}
%>

<h3><liferay-ui:message key="organization-site" /></h3>

<aui:fieldset>
	<c:choose>
		<c:when test="<%= (organization == null) || ((publicLayoutSetPrototype == null) && (privateLayoutSetPrototype == null)) %>">
			<aui:input label="create-site" name="site" type="checkbox" value="<%= site %>" />
		</c:when>
		<c:otherwise>
			<aui:input label="create-site" name="site" type="hidden" value="<%= site %>" />
		</c:otherwise>
	</c:choose>

	<div id="<portlet:namespace />siteTemplates">
		<c:choose>
			<c:when test="<%= ((organization == null) || (publicLayoutSetPrototype == null)) && !layoutSetPrototypes.isEmpty() %>">
				<aui:select label="public-pages" name="publicLayoutSetPrototypeId">
					<aui:option label='<%= organization == null ? "default" : "none" %>' selected="<%= true %>" value="" />

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
						<c:when test="<%= (organization != null) && (organization.getPublicLayoutsPageCount() > 0) %>">

							<liferay-portlet:actionURL var="publicPagesURL" portletName="<%= PortletKeys.SITE_REDIRECTOR %>">
								<portlet:param name="struts_action" value="/my_sites/view" />
								<portlet:param name="groupId" value="<%= String.valueOf(organization.getGroup().getGroupId()) %>" />
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
							<liferay-ui:message key="this-organization-does-not-have-any-public-pages" />
						</c:otherwise>
					</c:choose>
				</aui:field-wrapper>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="<%= ((organization == null) || (privateLayoutSetPrototype == null)) && !layoutSetPrototypes.isEmpty() %>">
				<aui:select label="private-pages" name="privateLayoutSetPrototypeId">
					<aui:option label='<%= organization == null ? "default" : "none" %>' selected="<%= true %>" value="" />

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
						<c:when test="<%= (organization != null) && (organization.getPrivateLayoutsPageCount() > 0) %>">
							<liferay-portlet:actionURL var="privatePagesURL" portletName="<%= PortletKeys.SITE_REDIRECTOR %>">
								<portlet:param name="struts_action" value="/my_sites/view" />
								<portlet:param name="groupId" value="<%= String.valueOf(organization.getGroup().getGroupId()) %>" />
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
							<liferay-ui:message key="this-organization-does-not-have-any-private-pages" />
						</c:otherwise>
					</c:choose>
				</aui:field-wrapper>
			</c:otherwise>
		</c:choose>
	</div>
</aui:fieldset>

<%
if ((organization == null) && layoutSetPrototypes.isEmpty()) {
	request.setAttribute(WebKeys.FORM_NAVIGATOR_SECTION_SHOW + "pages", Boolean.FALSE);
}
%>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />siteCheckbox','<portlet:namespace />siteTemplates');
</aui:script>