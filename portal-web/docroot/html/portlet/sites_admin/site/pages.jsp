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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group group = (Group)request.getAttribute("site.group");
LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute("site.layoutSetPrototype");

List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

LayoutSet privateLayoutSet = null;
LayoutSetPrototype privateLayoutSetPrototype = null;

LayoutSet publicLayoutSet = null;
LayoutSetPrototype publicLayoutSetPrototype = null;

if (group != null) {
	if (group.getPrivateLayoutsPageCount() > 0) {
		try {
			privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(group.getGroupId(), true);

			String layoutSetPrototypeUuid = privateLayoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(layoutSetPrototypeUuid);
			}
		}
		catch (Exception e) {
		}
	}
	if (group.getPublicLayoutsPageCount() > 0) {
		try {
			publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(group.getGroupId(), false);

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

<aui:fieldset>
	<aui:input name="siteTemplateRelationship" type="hidden" value="inherited" />

	<c:choose>
		<c:when test="<%= (group != null) || (!layoutSetPrototypes.isEmpty() && (layoutSetPrototype == null)) %>">
			<aui:fieldset label="public-pages">
				<c:choose>
					<c:when test="<%= ((group == null) || (group.getPublicLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
						<aui:select helpMessage="site-templates-with-an-incompatible-application-adapter-are-disabled" label="public-pages" name="publicLayoutSetPrototypeId">
							<aui:option label="none" selected="<%= true %>" value="" />

							<%
							for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
								UnicodeProperties settingsProperties = curLayoutSetPrototype.getSettingsProperties();

								String servletContextName = settingsProperties.getProperty("customJspServletContextName", StringPool.BLANK);
							%>

								<aui:option data-servletContextName="<%= servletContextName %>" value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(curLayoutSetPrototype.getName(user.getLanguageId())) %></aui:option>

							<%
							}
							%>

						</aui:select>
					</c:when>
					<c:otherwise>
						<c:if test="<%= (publicLayoutSetPrototype != null) %>">
							<liferay-ui:message arguments="<%= new Object[] {publicLayoutSetPrototype.getName(locale)} %>" key="these-pages-are-linked-to-template-x" />
						</c:if>

						<c:choose>
							<c:when test="<%= (group != null) && (group.getPublicLayoutsPageCount() > 0) %>">
								<liferay-portlet:actionURL var="publicPagesURL" portletName="<%= PortletKeys.MY_SITES %>">
									<portlet:param name="struts_action" value="/my_sites/view" />
									<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
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
								<liferay-ui:message key="this-site-does-not-have-any-public-pages" />
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</aui:fieldset>
			<aui:fieldset label="private-pages">
				<c:choose>
					<c:when test="<%= ((group == null) || (group.getPrivateLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
						<aui:select helpMessage="site-templates-with-an-incompatible-application-adapter-are-disabled" label="private-pages" name="privateLayoutSetPrototypeId">
							<aui:option label="none" selected="<%= true %>" value="" />

							<%
							for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
								UnicodeProperties settingsProperties = curLayoutSetPrototype.getSettingsProperties();

								String servletContextName = settingsProperties.getProperty("customJspServletContextName", StringPool.BLANK);
							%>

								<aui:option data-servletContextName="<%= servletContextName %>" value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(curLayoutSetPrototype.getName(user.getLanguageId())) %></aui:option>

							<%
							}
							%>

						</aui:select>
					</c:when>
					<c:otherwise>
						<c:if test="<%= (privateLayoutSetPrototype != null) %>">
							<liferay-ui:message arguments="<%= new Object[] {privateLayoutSetPrototype.getName(locale)} %>" key="these-pages-are-linked-to-template-x" />
						</c:if>

						<c:choose>
							<c:when test="<%= (group != null) && (group.getPrivateLayoutsPageCount() > 0) %>">
								<liferay-portlet:actionURL var="privatePagesURL" portletName="<%= PortletKeys.MY_SITES %>">
										<portlet:param name="struts_action" value="/my_sites/view" />
										<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
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
								<liferay-ui:message key="this-site-does-not-have-any-private-pages" />
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</aui:fieldset>
			<aui:fieldset label="configuration">

				<%
				Set<String> servletContextNames = CustomJspRegistryUtil.getServletContextNames();

				String customJspServletContextName = StringPool.BLANK;

				if (group != null) {
					UnicodeProperties typeSettingsProperties = group.getTypeSettingsProperties();

					customJspServletContextName = GetterUtil.getString(typeSettingsProperties.get("customJspServletContextName"));
				}
				%>

				<aui:select helpMessage='<%= LanguageUtil.format(pageContext, "application-adapter-help", "http://www.liferay.com/community/wiki/-/wiki/Main/Application+Adapters") %>' label="application-adapter" name="customJspServletContextName">
					<aui:option label="none" value="" />

					<%
					for (String servletContextName : servletContextNames) {
					%>

						<aui:option selected="<%= customJspServletContextName.equals(servletContextName) %>" value="<%= servletContextName %>"><%= CustomJspRegistryUtil.getDisplayName(servletContextName) %></aui:option>

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>

		</c:when>
		<c:when test="<%= layoutSetPrototype != null %>">
			<aui:fieldset label="pages">
				<br />

				<aui:input name="layoutSetPrototypeId" type="hidden" value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>" />

				<aui:select label="visibility" name="layoutSetVisibility">
					<aui:option label="public" value="0" />
					<aui:option label="private" value="1" />
				</aui:select>
			</aui:fieldset>
		</c:when>
	</c:choose>
</aui:fieldset>