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

<portlet:actionURL name="/layout_page_template_admin/edit_layout_prototype" var="editLayoutPrototypeURL" />

<liferay-frontend:edit-form
	action="<%= editLayoutPrototypeURL %>"
	method="post"
	name="fm"
>
	<liferay-ui:error exception="<%= LayoutPageTemplateEntryNameException.MustNotBeDuplicate.class %>" message="a-page-template-entry-with-that-name-already-exists" />
	<liferay-ui:error exception="<%= LayoutPageTemplateEntryNameException.MustNotBeNull.class %>" message="name-must-not-be-empty" />

	<liferay-ui:error exception="<%= LayoutPageTemplateEntryNameException.MustNotContainInvalidCharacters.class %>">

		<%
		LayoutPageTemplateEntryNameException.MustNotContainInvalidCharacters exception = (LayoutPageTemplateEntryNameException.MustNotContainInvalidCharacters)errorException;
		%>

		<liferay-ui:message arguments="<%= exception.character %>" key="name-cannot-contain-the-following-invalid-character-x" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= LayoutPageTemplateEntryNameException.MustNotExceedMaximumSize.class %>">
		<liferay-ui:message arguments='<%= ModelHintsUtil.getMaxLength(LayoutPageTemplateEntry.class.getName(), "name") %>' key="please-enter-a-name-with-fewer-than-x-characters" />
	</liferay-ui:error>

	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="layoutPrototypeId" type="hidden" value="<%= layoutPrototypeId %>" />

	<aui:model-context bean="<%= layoutPrototype %>" model="<%= LayoutPrototype.class %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" placeholder="name" />

				<aui:input name="description" placeholder="description" />

				<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="active" type="toggle-switch" value="<%= layoutPrototype.isActive() %>" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= redirect %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>