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

if (assetPublisherDisplayContext.isEnableTagBasedNavigation() && !assetPublisherDisplayContext.isSelectionStyleAssetList() && assetPublisherDisplayContext.isSelectionStyleManual() && ((assetPublisherDisplayContext.getAllAssetCategoryIds().length > 0) || (assetPublisherDisplayContext.getAllAssetTagNames().length > 0))) {
	assetPublisherDisplayContext.setSelectionStyle("dynamic");
}
%>

<liferay-ui:success key='<%= AssetPublisherPortletKeys.ASSET_PUBLISHER + "requestProcessed" %>' message="your-request-completed-successfully" />

<c:if test="<%= assetPublisherDisplayContext.isEnableSubscriptions() %>">
	<div class="subscribe-action">
		<c:if test="<%= assetPublisherDisplayContext.isSubscriptionEnabled() %>">
			<c:choose>
				<c:when test="<%= assetPublisherWebUtil.isSubscribed(themeDisplay.getCompanyId(), user.getUserId(), themeDisplay.getPlid(), portletDisplay.getId()) %>">
					<portlet:actionURL name="unsubscribe" var="unsubscribeURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm mb-4"
						markupView="lexicon"
						message="unsubscribe"
						url="<%= unsubscribeURL %>"
					/>
				</c:when>
				<c:otherwise>
					<portlet:actionURL name="subscribe" var="subscribeURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm mb-4"
						markupView="lexicon"
						message="subscribe"
						url="<%= subscribeURL %>"
					/>
				</c:otherwise>
			</c:choose>
		</c:if>

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() && assetPublisherDisplayContext.isEnableRSS() %>">
			<liferay-portlet:resourceURL id="getRSS" varImpl="rssURL" />

			<liferay-rss:rss
				resourceURL="<%= rssURL %>"
			/>
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

			<c:if test="<%= MapUtil.isEmpty(scopeAssetPublisherAddItemHolders) && !((assetPublisherDisplayContext.getAssetCategoryId() > 0) || Validator.isNotNull(assetPublisherDisplayContext.getAssetTagName())) %>">

				<%
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
				%>

			</c:if>

			<div class="alert alert-info text-center">
				<c:choose>
					<c:when test="<%= assetPublisherDisplayContext.isSelectionStyleAssetList() && (assetPublisherDisplayContext.fetchAssetListEntry() == null) && !portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS) %>">
						<div>
							<liferay-ui:message key="this-application-is-not-visible-to-users-yet" />
						</div>

						<div>
							<aui:a href="javascript:;" onClick="<%= portletDisplay.getURLConfigurationJS() %>"><liferay-ui:message key="select-a-content-set-to-make-it-visible" /></aui:a>
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
SearchContainer searchContainer = assetPublisherDisplayContext.getSearchContainer();
%>

<c:if test="<%= !assetPublisherDisplayContext.isPaginationTypeNone() && (searchContainer.getTotal() > searchContainer.getDelta()) %>">
	<liferay-ui:search-paginator
		searchContainer="<%= searchContainer %>"
		type="<%= assetPublisherDisplayContext.getPaginationType() %>"
	/>
</c:if>

<aui:script sandbox="<%= true %>">
	const assetEntryId = '<%= assetPublisherDisplayContext.getAssetEntryId() %>';

	if (assetEntryId) {
		window.location.hash = assetEntryId;
	}
</aui:script>