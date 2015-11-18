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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Group group = (Group)request.getAttribute("edit_pages.jsp-group");
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

UnicodeProperties layoutTypeSettings = selLayout.getTypeSettingsProperties();
%>

<liferay-ui:error-marker key="errorSection" value="advanced" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="advanced" /></h3>

<liferay-ui:error exception="<%= ImageTypeException.class %>" message="please-enter-a-file-with-a-valid-file-type" />

<aui:fieldset cssClass="lfr-portrait-editor">
	<c:if test="<%= !group.isLayoutPrototype() %>">

		<%
		String queryString = GetterUtil.getString(layoutTypeSettings.getProperty("query-string"));
		%>

		<aui:input helpMessage="query-string-help" label="query-string" name="TypeSettingsProperties--query-string--" size="30" type="text" value="<%= queryString %>" />
	</c:if>

	<%
	String curTarget = GetterUtil.getString(layoutTypeSettings.getProperty("target"));
	%>

	<aui:input label="target" name="TypeSettingsProperties--target--" size="15" type="text" value="<%= HtmlUtil.escapeAttribute(curTarget) %>" />

	<aui:input name="iconImage" type="hidden" value="<%= selLayout.isIconImage() %>" />

	<aui:field-wrapper helpMessage="this-icon-will-be-shown-in-the-navigation-menu" label="icon" name="iconFileName">
		<aui:input inlineField="<%= true %>" label="" name="iconFileName" type="file" />

		<c:if test="<%= selLayout.getIconImage() %>">
			<liferay-ui:icon
				cssClass="modify-link"
				id="deleteIconLink"
				image="delete"
				label="<%= true %>"
				url="javascript:;"
			/>

			<div id="<portlet:namespace />layoutIconContainer">
				<liferay-theme:layout-icon layout="<%= selLayout %>" />
			</div>
		</c:if>
	</aui:field-wrapper>
</aui:fieldset>

<aui:script use="aui-base">
	var panel = A.one('#<portlet:namespace />advanced');

	var deleteLogoLink = panel.one('#<portlet:namespace />deleteIconLink');
	var iconFileNameInput = panel.one('#<portlet:namespace />iconFileName');
	var iconImageInput = panel.one('#<portlet:namespace />iconImage');
	var layoutIconContainer = panel.one('#<portlet:namespace />layoutIconContainer');

	var changeLogo = function(event) {
		var changeLogo = (event.type == 'change');

		iconImageInput.val(changeLogo);

		if (layoutIconContainer) {
			layoutIconContainer.hide();
		}
	};

	if (deleteLogoLink) {
		deleteLogoLink.on('click', changeLogo);
	}

	iconFileNameInput.on('change', changeLogo);
</aui:script>