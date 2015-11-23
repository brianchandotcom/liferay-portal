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
long assetCategoryId = ParamUtil.getLong(request, "categoryId");

if (assetCategoryId > 0) {
	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategory(assetCategoryId);

	assetCategory = assetCategory.toEscapedModel();

	PortalUtil.setPageKeywords(assetCategory.getTitle(locale), request);
}

String assetTagName = ParamUtil.getString(request, "tag");

if (Validator.isNotNull(assetTagName)) {
	PortalUtil.setPageKeywords(assetTagName, request);
}

if (assetPublisherDisplayContext.isEnableTagBasedNavigation() && assetPublisherDisplayContext.isSelectionStyleManual() && ((assetPublisherDisplayContext.getAllAssetCategoryIds().length > 0) || (assetPublisherDisplayContext.getAllAssetTagNames().length > 0))) {
	assetPublisherDisplayContext.setSelectionStyle("dynamic");
}

Group scopeGroup = themeDisplay.getScopeGroup();
%>

<c:if test="<%= assetPublisherDisplayContext.isShowAddContentButton() && (scopeGroup != null) && !scopeGroup.isLayoutPrototype() && (!scopeGroup.hasStagingGroup() || scopeGroup.isStagingGroup()) && !portletName.equals(AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) && !portletName.equals(AssetPublisherPortletKeys.MOST_VIEWED_ASSETS) && !portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS) %>">

	<%
	long[] groupIds = assetPublisherDisplayContext.getGroupIds();
	%>

	<c:if test="<%= groupIds.length > 0 %>">

		<%
		PortletURL redirectURL = renderResponse.createRenderURL();

		redirectURL.setParameter("hideDefaultSuccessMessage", Boolean.TRUE.toString());
		redirectURL.setParameter("mvcPath", "/add_asset_redirect.jsp");
		redirectURL.setParameter("redirect", currentURL);
		redirectURL.setWindowState(LiferayWindowState.POP_UP);
		%>

		<aui:nav-bar cssClass='<%= "add-asset-selector lfr-meta-actions" + ((groupIds.length == 1) ? " single-item-button" : StringPool.BLANK) %>'>
			<liferay-ui:asset-add-button
				addDisplayPageParameter="<%= AssetUtil.isDefaultAssetPublisher(layout, portletDisplay.getId(), assetPublisherDisplayContext.getPortletResource()) %>"
				allAssetCategoryIds="<%= assetPublisherDisplayContext.getAllAssetCategoryIds() %>"
				allAssetTagNames="<%= assetPublisherDisplayContext.getAllAssetTagNames() %>"
				classNameIds="<%= assetPublisherDisplayContext.getClassNameIds() %>"
				classTypeIds="<%= assetPublisherDisplayContext.getClassTypeIds() %>"
				groupIds="<%= groupIds %>"
				redirect="<%= redirectURL.toString() %>"
			/>
		</aui:nav-bar>
	</c:if>
</c:if>

<div class="subscribe-action">
	<c:if test="<%= !portletName.equals(AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) && !portletName.equals(AssetPublisherPortletKeys.MOST_VIEWED_ASSETS) && !portletName.equals(AssetPublisherPortletKeys.RECENT_CONTENT) && !portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS) && PortletPermissionUtil.contains(permissionChecker, 0, layout, portletDisplay.getId(), ActionKeys.SUBSCRIBE, false, false) && AssetPublisherUtil.getEmailAssetEntryAddedEnabled(portletPreferences) %>">
		<c:choose>
			<c:when test="<%= AssetPublisherUtil.isSubscribed(themeDisplay.getCompanyId(), user.getUserId(), themeDisplay.getPlid(), portletDisplay.getId()) %>">
				<portlet:actionURL name="unsubscribe" var="unsubscribeURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-remove-sign"
					label="<%= true %>"
					message="unsubscribe"
					url="<%= unsubscribeURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL name="subscribe" var="subscribeURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-ok-sign"
					label="<%= true %>"
					message="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<%
	boolean enableRSS = !PortalUtil.isRSSFeedsEnabled() ? false : assetPublisherDisplayContext.isEnableRSS();
	%>

	<c:if test="<%= enableRSS %>">
		<liferay-portlet:resourceURL id="getRSS" varImpl="rssURL" />

		<liferay-ui:rss resourceURL="<%= rssURL %>" />
	</c:if>
</div>

<%
PortletURL portletURL = renderResponse.createRenderURL();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, assetPublisherDisplayContext.getDelta(), portletURL, null, null);

if (!assetPublisherDisplayContext.isPaginationTypeNone()) {
	searchContainer.setDelta(assetPublisherDisplayContext.getDelta());
	searchContainer.setDeltaConfigurable(false);
}
%>

<c:if test="<%= assetPublisherDisplayContext.isShowMetadataDescriptions() %>">
	<liferay-ui:categorization-filter
		assetType="content"
		portletURL="<%= portletURL %>"
	/>
</c:if>

<%
request.setAttribute("view.jsp-viewInContext", assetPublisherDisplayContext.isAssetLinkBehaviorViewInPortlet());
%>

<c:choose>
	<c:when test="<%= assetPublisherDisplayContext.isSelectionStyleDynamic() %>">
		<%@ include file="/view_dynamic_list.jspf" %>
	</c:when>
	<c:when test="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>">
		<%@ include file="/view_manual.jspf" %>
	</c:when>
</c:choose>

<c:if test="<%= !assetPublisherDisplayContext.isPaginationTypeNone() && (searchContainer.getTotal() > searchContainer.getResults().size()) %>">
	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="<%= assetPublisherDisplayContext.getPaginationType() %>" />
</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_asset_publisher_web.view_jsp");
%>