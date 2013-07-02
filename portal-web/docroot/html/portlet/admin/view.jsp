<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/admin/init.jsp" %>

<c:choose>
	<c:when test="<%= permissionChecker.isOmniadmin() %>">

		<%
		String tabs1 = ParamUtil.getString(request, "tabs1", "server");

		boolean showTabs1 = false;

		if (portletName.equals(PortletKeys.ADMIN_INSTANCE)) {
			tabs1 = "instances";
		}
		else if (portletName.equals(PortletKeys.ADMIN_PLUGINS)) {
			tabs1 = "plugins";
		}
		else if (portletName.equals(PortletKeys.ADMIN_SERVER)) {
			tabs1 = "server";
		}
		else if (portletName.equals(PortletKeys.ADMIN)) {
			showTabs1 = true;
		}

		String tabs2 = ParamUtil.getString(request, "tabs2");
		String tabs3 = ParamUtil.getString(request, "tabs3");

		if (tabs1.equals("plugins")) {
			if (!tabs2.equals("portlet-plugins") && !tabs2.equals("theme-plugins") && !tabs2.equals("layout-template-plugins") && !tabs2.equals("hook-plugins") && !tabs2.equals("web-plugins")) {
				tabs2 = "portlet-plugins";
			}
		}

		int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
		int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/admin/view");
		portletURL.setParameter("tabs1", tabs1);
		portletURL.setParameter("tabs2", tabs2);
		portletURL.setParameter("tabs3", tabs3);
		%>

		<portlet:renderURL var="redirectURL">
			<portlet:param name="struts_action" value="/admin/view" />
			<portlet:param name="tabs1" value="<%= tabs1 %>" />
			<portlet:param name="tabs2" value="<%= tabs2 %>" />
			<portlet:param name="tabs3" value="<%= tabs3 %>" />
			<portlet:param name="cur" value="<%= String.valueOf(cur) %>" />
		</portlet:renderURL>

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
			<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
			<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
			<aui:input name="portletId" type="hidden" />

			<c:if test="<%= showTabs1 %>">
				<liferay-ui:tabs
					names="server,instances,plugins"
					url="<%= portletURL.toString() %>"
				/>
			</c:if>

			<c:choose>
				<c:when test='<%= tabs1.equals("server") %>'>
					<%@ include file="/html/portlet/admin/server.jspf" %>

					<aui:script use="liferay-admin">
						new Liferay.Portlet.Admin(
							{
								form: document.<portlet:namespace />fm,
								namespace: '<portlet:namespace />',
								url: '<portlet:actionURL><portlet:param name="struts_action" value="/admin/edit_server" /></portlet:actionURL>'
							}
						);
					</aui:script>
				</c:when>
				<c:when test='<%= tabs1.equals("instances") %>'>
					<%@ include file="/html/portlet/admin/instances.jspf" %>
				</c:when>
				<c:when test='<%= tabs1.equals("plugins") %>'>

					<%
					PortletURL marketplaceURL = null;

					if ((PrefsPropsUtil.getBoolean(PropsKeys.AUTO_DEPLOY_ENABLED, PropsValues.AUTO_DEPLOY_ENABLED) || PortalUtil.isOmniadmin(user.getUserId())) && PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_STORE)) {
						marketplaceURL = ((RenderResponseImpl)renderResponse).createRenderURL(PortletKeys.MARKETPLACE_STORE);
					}

					boolean showEditPluginHREF = false;
					boolean showReindexButton = true;
					%>

					<%@ include file="/html/portlet/plugins_admin/plugins.jspf" %>
				</c:when>
			</c:choose>
		</aui:form>

		<aui:script>
			function <portlet:namespace />saveServer(cmd) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
				document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL><portlet:param name="struts_action" value="/admin/view" /><portlet:param name="tabs1" value="<%= tabs1 %>" /><portlet:param name="tabs2" value="<%= tabs2 %>" /><portlet:param name="tabs3" value="<%= tabs3 %>" /><portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= String.valueOf(cur) %>" /><portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= String.valueOf(delta) %>" /></portlet:renderURL>";

				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/admin/edit_server" /></portlet:actionURL>");
			}
		</aui:script>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portal/portlet_access_denied.jsp" />
	</c:otherwise>
</c:choose>