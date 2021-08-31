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
List<Group> groups = new ArrayList<>();

SiteCommerceChannelTypeDisplayContext siteCommerceChannelTypeDisplayContext = (SiteCommerceChannelTypeDisplayContext)request.getAttribute("site.jsp-portletDisplayContext");

Group channelSiteGroup = siteCommerceChannelTypeDisplayContext.getChannelSite();

if (channelSiteGroup != null) {
	groups = Arrays.asList(channelSiteGroup);
}

CommerceChannel commerceChannel = siteCommerceChannelTypeDisplayContext.getCommerceChannel();
long commerceChannelId = siteCommerceChannelTypeDisplayContext.getCommerceChannelId();

String searchContainerId = "CommerceChannelSitesSearchContainer";

boolean viewOnly = false;

if (commerceChannel != null) {
	viewOnly = !siteCommerceChannelTypeDisplayContext.hasPermission(commerceChannel.getCommerceChannelId(), ActionKeys.UPDATE);
}
%>

<liferay-util:buffer
	var="removeCommerceChannelSiteIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<portlet:actionURL name="/commerce_channels/edit_commerce_channel" var="editCommerceChannelActionURL" />

<aui:form action="<%= editCommerceChannelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="selectSite" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelId %>" />
	<aui:input name="siteGroupId" type="hidden" value="<%= (commerceChannel == null) ? 0 : commerceChannel.getSiteGroupId() %>" />

	<commerce-ui:panel
		bodyClasses="flex-fill"
		title='<%= LanguageUtil.get(request, "details") %>'
	>
		<commerce-ui:info-box
			title='<%= LanguageUtil.get(request, "type") %>'
		>
			<div class="item mb-4 pl-4">
				<liferay-ui:message key="site" />
			</div>
		</commerce-ui:info-box>

		<liferay-ui:search-container
			curParam="commerceChannelSiteCur"
			headerNames="null,null"
			id="<%= searchContainerId %>"
			iteratorURL="<%= currentURLObj %>"
			total="<%= groups.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= groups %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Group"
				keyProperty="groupId"
				modelVar="group"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					value="<%= HtmlUtil.escape(group.getName(locale)) %>"
				/>

				<c:if test="<%= !viewOnly %>">
					<liferay-ui:search-container-column-text>
						<a class="float-right modify-link" data-rowId="<%= group.getGroupId() %>" href="javascript:;"><%= removeCommerceChannelSiteIcon %></a>
					</liferay-ui:search-container-column-text>
				</c:if>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>

		<c:if test="<%= !viewOnly %>">
			<aui:button cssClass="mb-4" name="selectSite" value='<%= LanguageUtil.format(locale, "select-x", "site") %>' />
		</c:if>
	</commerce-ui:panel>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"itemSelectorUrl", siteCommerceChannelTypeDisplayContext.getItemSelectorUrl()
		).put(
			"portletNamespace", liferayPortletResponse.getNamespace()
		).put(
			"removeCommerceChannelSiteIcon", removeCommerceChannelSiteIcon
		).put(
			"searchContainerId", searchContainerId
		).build()
	%>'
	module="js/CommerceChannelSite"
/>