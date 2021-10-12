<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error key="<%= SXPBlueprintWebKeys.ERROR %>">
	<liferay-ui:message arguments="<%= SessionErrors.get(liferayPortletRequest, SXPBlueprintWebKeys.ERROR) %>" key="error-x" />
</liferay-ui:error>

<%
final String tab = ParamUtil.getString(request, SXPBlueprintWebKeys.TAB, SXPBlueprintTabNames.SXP_BLUEPRINTS);

PortletURL renderURL = renderResponse.createRenderURL();
%>

<clay:navigation-bar
	inverted="<%= false %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tab.equals(SXPBlueprintTabNames.SXP_BLUEPRINTS));
						navigationItem.setHref(renderURL, SXPBlueprintWebKeys.TAB, SXPBlueprintTabNames.SXP_BLUEPRINTS, "mvcRenderCommandName", SXPBlueprintMVCCommandNames.VIEW_SXP_BLUEPRINTS);
						navigationItem.setLabel(LanguageUtil.get(request, "blueprints"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(tab.equals(SXPBlueprintTabNames.SXP_ELEMENTS));
						navigationItem.setHref(renderURL, SXPBlueprintWebKeys.TAB, SXPBlueprintTabNames.SXP_ELEMENTS, "mvcRenderCommandName", SXPBlueprintMVCCommandNames.VIEW_SXP_ELEMENTS, SXPBlueprintWebKeys.HIDDEN, Boolean.FALSE);
						navigationItem.setLabel(LanguageUtil.get(request, "elements"));
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test="<%= tab.equals(SXPBlueprintTabNames.SXP_ELEMENTS) %>">
		<liferay-util:include page="/view_sxp_elements.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/view_sxp_blueprints.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>