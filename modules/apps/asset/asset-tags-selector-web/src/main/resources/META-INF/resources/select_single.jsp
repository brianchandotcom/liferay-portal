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
assetTagsSelectorDisplayContext = new AssetTagsSelectorDisplayContext(request, renderRequest, renderResponse, false);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new AssetTagsSelectorManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, assetTagsSelectorDisplayContext) %>"
/>

<aui:form action="<%= assetTagsSelectorDisplayContext.getPortletURL() %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="selectAssetTagFm">
	<liferay-ui:search-container
		searchContainer="<%= assetTagsSelectorDisplayContext.getTagsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetTag"
			escapedModel="<%= true %>"
			keyProperty="name"
			modelVar="tag"
			rowIdProperty="friendlyURL"
			rowVar="row"
		>
			<liferay-ui:search-container-column-text
				name="name"
				truncate="<%= true %>"
			>
				<aui:a
					cssClass="selector-button"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"entityid", tag.getTagId()
						).put(
							"entityname", tag.getName()
						).build()
					%>'
					href="javascript:;"
				>
					<%= HtmlUtil.escape(tag.getName()) %>
				</aui:a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>