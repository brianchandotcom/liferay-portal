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
assetPublisherDisplayContext.setPageKeywords();

if (assetPublisherDisplayContext.isEnableTagBasedNavigation() && !assetPublisherDisplayContext.isSelectionStyleAssetList() && assetPublisherDisplayContext.isSelectionStyleManual() && (ArrayUtil.isNotEmpty(assetPublisherDisplayContext.getAllAssetCategoryIds()) || ArrayUtil.isNotEmpty(assetPublisherDisplayContext.getAllAssetTagNames()))) {
	assetPublisherDisplayContext.setSelectionStyle(AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC);
}
%>

<liferay-ui:success key='<%= AssetPublisherPortletKeys.ASSET_PUBLISHER + "requestProcessed" %>' message="your-request-completed-successfully" />

<c:if test="<%= assetPublisherDisplayContext.isEnableSubscriptions() %>">
	<div class="mb-4 subscribe-action">
		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() && assetPublisherDisplayContext.isEnableRSS() %>">
			<liferay-portlet:resourceURL id="getRSS" varImpl="rssURL" />

			<div class="btn-group-item">
				<clay:link
					borderless="<%= true %>"
					data-senna-off="<%= true %>"
					displayType="secondary"
					href="<%= rssURL.toString() %>"
					icon="rss-full"
					label="rss"
					outline="<%= true %>"
					small="<%= true %>"
					type="button"
				/>
			</div>

			<liferay-util:html-top>
				<link href="<%= HtmlUtil.escapeAttribute(rssURL.toString()) %>" rel="alternate" title="RSS" type="application/rss+xml" />
			</liferay-util:html-top>
		</c:if>

		<c:if test="<%= assetPublisherDisplayContext.isSubscriptionEnabled() %>">
			<c:choose>
				<c:when test="<%= assetPublisherWebHelper.isSubscribed(themeDisplay.getCompanyId(), user.getUserId(), themeDisplay.getPlid(), portletDisplay.getId()) %>">
					<portlet:actionURL name="unsubscribe" var="unsubscribeURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</portlet:actionURL>

					<clay:link
						displayType="secondary"
						href="<%= unsubscribeURL %>"
						label="unsubscribe"
						small="<%= true %>"
						type="button"
					/>
				</c:when>
				<c:otherwise>
					<portlet:actionURL name="subscribe" var="subscribeURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</portlet:actionURL>

					<clay:link
						displayType="secondary"
						href="<%= subscribeURL %>"
						label="subscribe"
						small="<%= true %>"
						type="button"
					/>
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
</c:if>

<c:if test="<%= assetPublisherDisplayContext.isShowMetadataDescriptions() %>">
	<liferay-asset:categorization-filter
		assetType="content"
		portletURL="<%= assetPublisherDisplayContext.getPortletURL() %>"
	/>
</c:if>

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(assetPublisherDisplayContext.getAssetEntryResults()) %>">
		<c:choose>
			<c:when test="<%= ArrayUtil.contains(assetPublisherDisplayContext.getDisplayStyles(), assetPublisherDisplayContext.getDisplayStyle()) || StringUtil.startsWith(assetPublisherDisplayContext.getDisplayStyle(), PortletDisplayTemplateManager.DISPLAY_STYLE_PREFIX) %>">
				<liferay-util:include page="/view_asset_entry_list.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message arguments="<%= assetPublisherDisplayContext.getDisplayStyle() %>" escape="<%= true %>" key="x-is-not-a-display-type" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<liferay-ddm:template-renderer
			className="<%= AssetEntry.class.getName() %>"
			displayStyle="<%= assetPublisherDisplayContext.getDisplayStyle() %>"
			displayStyleGroupId="<%= assetPublisherDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= new ArrayList<AssetEntry>() %>"
		>

			<%
			Map<Long, List<AssetPublisherAddItemHolder>> scopeAssetPublisherAddItemHolders = assetPublisherDisplayContext.getScopeAssetPublisherAddItemHolders(1);
			%>

			<c:if test="<%= portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS) || (MapUtil.isEmpty(scopeAssetPublisherAddItemHolders) && !((assetPublisherDisplayContext.getAssetCategoryId() > 0) || Validator.isNotNull(assetPublisherDisplayContext.getAssetTagName()))) %>">

				<%
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
				%>

			</c:if>

			<div class="alert alert-info text-center">
				<c:choose>
					<c:when test="<%= assetPublisherDisplayContext.isSelectionStyleAssetList() && (assetPublisherDisplayContext.fetchAssetListEntry() == null) && Validator.isNull(assetPublisherDisplayContext.getInfoListProviderKey()) && !portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS) %>">
						<div>
							<liferay-ui:message key="this-application-is-not-visible-to-users-yet" />
						</div>

						<div>
							<aui:a href="javascript:;" onClick="<%= portletDisplay.getURLConfigurationJS() %>"><liferay-ui:message key="select-a-collection-to-make-it-visible" /></aui:a>
						</div>
					</c:when>
					<c:when test="<%= !portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS) %>">
						<liferay-ui:message key="there-are-no-results" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="there-are-no-related-assets" />
					</c:otherwise>
				</c:choose>
			</div>
		</liferay-ddm:template-renderer>
	</c:otherwise>
</c:choose>

<%
SearchContainer<AssetEntry> searchContainer = assetPublisherDisplayContext.getSearchContainer();
%>

<c:if test="<%= !assetPublisherDisplayContext.isPaginationTypeNone() && (searchContainer.getTotal() > searchContainer.getDelta()) %>">
	<liferay-ui:search-paginator
		searchContainer="<%= searchContainer %>"
		type="<%= assetPublisherDisplayContext.getPaginationType() %>"
	/>
</c:if>

<aui:script sandbox="<%= true %>">
	var assetEntryId =
		'<%= HtmlUtil.escape(assetPublisherDisplayContext.getAssetEntryId()) %>';

	if (assetEntryId) {
		window.location.hash = assetEntryId;
	}
</aui:script>