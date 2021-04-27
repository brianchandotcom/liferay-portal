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
SimpleSiteItemSelectorViewDisplayContext simpleSiteItemSelectorViewDisplayContext = (SimpleSiteItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= false %>"
	searchContainerId="sites"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= simpleSiteItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= simpleSiteItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= simpleSiteItemSelectorViewDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />siteSelectorWrapper">
	<liferay-ui:search-container
		id="sites"
		searchContainer="<%= simpleSiteItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Group"
			keyProperty="groupId"
			modelVar="group"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="name"
				value="<%= HtmlUtil.escape(group.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="channel"
				value="<%= HtmlUtil.escape(simpleSiteItemSelectorViewDisplayContext.getChannelUsingSite(group.getGroupId())) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
			>
				<c:choose>
					<c:when test="<%= simpleSiteItemSelectorViewDisplayContext.isSiteAvailable(group.getGroupId()) %>">
						<aui:button cssClass="selector-button" data='<%= HashMapBuilder.<String, Object>put("id", group.getGroupId()).put("name", group.getName(locale)).build() %>' value="choose" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="that-site-is-already-associated-with-another-channel" />
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>