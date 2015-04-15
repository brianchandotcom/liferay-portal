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

String backURL = ParamUtil.getString(request, "backURL", redirect);

long layoutPrototypeId = ParamUtil.getLong(request, "layoutPrototypeId");

LayoutPrototype layoutPrototype = LayoutPrototypeServiceUtil.fetchLayoutPrototype(layoutPrototypeId);

if (layoutPrototype == null) {
	layoutPrototype = new LayoutPrototypeImpl();

	layoutPrototype.setNew(true);
	layoutPrototype.setActive(true);
}
%>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="toolbarItem" value='<%= layoutPrototype.isNew() ? "add" : StringPool.BLANK %>' />
</liferay-util:include>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= layoutPrototype.isNew() %>"
	title='<%= layoutPrototype.isNew() ? "new-page-template" : layoutPrototype.getName(locale) %>'
/>

<%
request.setAttribute("edit_layout_prototype.jsp-layoutPrototype", layoutPrototype);
request.setAttribute("edit_layout_prototype.jsp-redirect", redirect);
%>

<liferay-util:include page="/merge_alert.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="editLayoutPrototype" var="editLayoutPrototypeURL" />

<aui:form action="<%= editLayoutPrototypeURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="layoutPrototypeId" type="hidden" value="<%= layoutPrototypeId %>" />

	<aui:model-context bean="<%= layoutPrototype %>" model="<%= LayoutPrototype.class %>" />

	<aui:fieldset>
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

		<aui:input name="description" />

		<aui:input name="active" />

		<c:if test="<%= !layoutPrototype.isNew() %>">
			<aui:field-wrapper label="configuration">

				<%
				Group layoutPrototypeGroup = layoutPrototype.getGroup();
				%>

				<liferay-ui:icon
					iconCssClass="icon-search"
					label="<%= true %>"
					message="open-page-template"
					method="get"
					target="_blank"
					url="<%= layoutPrototypeGroup.getDisplayURL(themeDisplay) %>"
				/>
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<%
if (!layoutPrototype.isNew()) {
	PortalUtil.addPortletBreadcrumbEntry(request, layoutPrototype.getName(locale), currentURL);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-page"), currentURL);
}
%>