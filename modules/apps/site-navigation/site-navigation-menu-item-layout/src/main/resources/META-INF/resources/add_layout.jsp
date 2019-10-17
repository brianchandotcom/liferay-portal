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
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout", false);

PortletURL portletURL = currentURLObj;
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(!privateLayout);
						navigationItem.setHref(portletURL, "privateLayout", false);
						navigationItem.setLabel(LanguageUtil.get(request, "public-pages"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(privateLayout);
						navigationItem.setHref(portletURL, "privateLayout", true);
						navigationItem.setLabel(LanguageUtil.get(request, "private-pages"));
					});
			}
		}
	%>'
/>

<aui:input id="groupId" name="TypeSettingsProperties--groupId--" type="hidden" value="<%= scopeGroupId %>" />

<aui:input id="layoutUuid" name="TypeSettingsProperties--layoutUuid--" type="hidden" value="">
	<aui:validator name="required" />
</aui:input>

<aui:input id="privateLayout" name="TypeSettingsProperties--privateLayout--" type="hidden" value="<%= privateLayout %>" />

<liferay-layout:select-layout
	componentId='<%= liferayPortletResponse.getNamespace() + "selectLayout" %>'
	itemSelectorSaveEvent='<%= liferayPortletResponse.getNamespace() + "selectLayout" %>'
	multiSelection="<%= true %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pathThemeImages="<%= themeDisplay.getPathThemeImages() %>"
	privateLayout="<%= privateLayout %>"
	showHiddenLayouts="<%= true %>"
/>

<aui:script>
	var layoutUuid = document.getElementById('<portlet:namespace />layoutUuid');

	if (layoutUuid) {
		Liferay.componentReady('<portlet:namespace />selectLayout').then(function(
			selectLayout
		) {
			selectLayout.on('<portlet:namespace />selectLayout', function(event) {
				var selectedItems = event.data;

				if (selectedItems) {
					var layoutUuids = selectedItems.reduce(function(
						previousValue,
						currentValue
					) {
						return previousValue.concat([currentValue.id]);
					},
					[]);

					layoutUuid.value = layoutUuids.join();
				}
			});
		});
	}
</aui:script>