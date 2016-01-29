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

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

long layoutPrototypeId = ParamUtil.getLong(request, "layoutPrototypeId");

LayoutPrototype layoutPrototype = LayoutPrototypeServiceUtil.fetchLayoutPrototype(layoutPrototypeId);

if (layoutPrototype == null) {
	layoutPrototype = new LayoutPrototypeImpl();

	layoutPrototype.setNew(true);
	layoutPrototype.setActive(true);
}

request.setAttribute("edit_layout_prototype.jsp-layoutPrototype", layoutPrototype);
request.setAttribute("edit_layout_prototype.jsp-redirect", redirect);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(layoutPrototype.isNew() ? LanguageUtil.get(request, "new-page-template") : layoutPrototype.getName(locale));
%>

<liferay-util:include page="/merge_alert.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="editLayoutPrototype" var="editLayoutPrototypeURL" />

<aui:form action="<%= editLayoutPrototypeURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="layoutPrototypeId" type="hidden" value="<%= layoutPrototypeId %>" />

	<aui:model-context bean="<%= layoutPrototype %>" model="<%= LayoutPrototype.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" placeholder="name" />

			<aui:input name="description" placeholder="description" />

			<aui:input name="active" type="toggle-switch" value="<%= layoutPrototype.isActive() %>" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
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