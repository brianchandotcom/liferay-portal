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

<%@ include file="/document_library/init.jsp" %>

<%
DLSelectRestrictedFileEntryTypesDisplayContext selectRestrictedFileEntryTypesDisplayContext = new DLSelectRestrictedFileEntryTypesDisplayContext(renderRequest, renderResponse, request);

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectFileEntryType");
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setLabel(LanguageUtil.get(request, "document-types"));
					});
			}
		}
	%>'
/>

<aui:form action="<%= selectRestrictedFileEntryTypesDisplayContext.getFormActionURL() %>" cssClass="container-fluid-1280" method="post" name="selectFileEntryTypeFm">
	<liferay-ui:search-container
		searchContainer="<%= selectRestrictedFileEntryTypesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.document.library.kernel.model.DLFileEntryType"
			keyProperty="fileEntryTypeId"
			modelVar="fileEntryType"
		>
			<liferay-ui:search-container-column-icon
				icon="edit-layout"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<h5><%= HtmlUtil.escape(fileEntryType.getName(locale)) %></h5>

				<h6 class="text-default">
					<span><%= fileEntryType.getDescription(locale) %></span>
				</h6>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("entityid", fileEntryType.getFileEntryTypeId());
				data.put("entityname", fileEntryType.getName(locale));
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectFileEntryTypeFm',
		'<%= HtmlUtil.escapeJS(eventName) %>'
	);
</aui:script>