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
AssetEntryResult assetEntryResult = (AssetEntryResult)request.getAttribute("view.jsp-assetEntryResult");
%>

<ul class="list-group show-quick-actions-on-hover">
	<c:if test="<%= Validator.isNotNull(assetEntryResult.getTitle()) %>">
		<li class="list-group-header">
			<h3 class="list-group-header-title"><%= assetEntryResult.getTitle() %></h3>
		</li>
	</c:if>

	<%
	for (AssetEntry assetEntry : assetEntryResult.getAssetEntries()) {
		AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassNameId(assetEntry.getClassNameId());

		if (assetRendererFactory == null) {
			continue;
		}

		AssetRenderer<?> assetRenderer = null;

		try {
			assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		if ((assetRenderer == null) || !assetRenderer.isDisplayable()) {
			continue;
		}
	%>

		<li class="list-group-item list-group-item-flex">
			<c:if test='<%= ArrayUtil.contains(assetPublisherDisplayContext.getMetadataFields(), "author") %>'>
				<div class="autofit-col">
					<span class="inline-item">
						<liferay-ui:user-portrait
							userId="<%= assetEntry.getUserId() %>"
						/>
					</span>
				</div>
			</c:if>

			<div class="autofit-col autofit-col-expand">
				<h4 class="list-group-title text-truncate">
					<span class="asset-anchor lfr-asset-anchor" id="<%= assetEntry.getEntryId() %>"></span>

					<aui:a href="<%= assetPublisherHelper.getAssetViewURL(liferayPortletRequest, liferayPortletResponse, assetRenderer, assetEntry, assetPublisherDisplayContext.isAssetLinkBehaviorViewInPortlet()) %>">
						<%= HtmlUtil.escape(assetEntry.getTitle(locale)) %>
					</aui:a>
				</h4>

				<%
				Date displayDate = ArrayUtil.contains(assetPublisherDisplayContext.getMetadataFields(), "create-date") ? assetEntry.getCreateDate() : null;

				if (ArrayUtil.contains(assetPublisherDisplayContext.getMetadataFields(), "publish-date") && (assetEntry.getPublishDate() != null)) {
					displayDate = assetEntry.getPublishDate();
				}
				else if (ArrayUtil.contains(assetPublisherDisplayContext.getMetadataFields(), "modified-date") && (assetEntry.getModifiedDate() != null)) {
					displayDate = assetEntry.getModifiedDate();
				}
				%>

				<c:if test="<%= displayDate != null %>">
					<p class="list-group-subtitle text-truncate">
						<%= dateFormatDateTime.format(displayDate) %>
					</p>
				</c:if>

				<c:if test='<%= ArrayUtil.contains(assetPublisherDisplayContext.getMetadataFields(), "categories") || ArrayUtil.contains(assetPublisherDisplayContext.getMetadataFields(), "tags") %>'>
					<div class="list-group-detail">
						<c:if test='<%= ArrayUtil.contains(assetPublisherDisplayContext.getMetadataFields(), "categories") %>'>
							<liferay-asset:asset-categories-summary
								className="<%= assetEntry.getClassName() %>"
								classPK="<%= assetEntry.getClassPK() %>"
								displayStyle="simple-category"
								portletURL="<%= renderResponse.createRenderURL() %>"
							/>
						</c:if>

						<c:if test='<%= ArrayUtil.contains(assetPublisherDisplayContext.getMetadataFields(), "tags") %>'>
							<liferay-asset:asset-tags-summary
								className="<%= assetEntry.getClassName() %>"
								classPK="<%= assetEntry.getClassPK() %>"
								portletURL="<%= renderResponse.createRenderURL() %>"
							/>
						</c:if>
					</div>
				</c:if>
			</div>

			<%
			AssetEntryActionDropdownItemsProvider assetEntryActionDropdownItemsProvider = new AssetEntryActionDropdownItemsProvider(assetRenderer, assetPublisherDisplayContext.getAssetEntryActions(assetEntry.getClassName()), StringPool.BLANK, liferayPortletRequest, liferayPortletResponse);
			%>

			<div class="autofit-col">
				<clay:dropdown-actions
					defaultEventHandler="<%= com.liferay.asset.publisher.web.internal.constants.AssetPublisherWebKeys.ASSET_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
					dropdownItems="<%= assetEntryActionDropdownItemsProvider.getActionDropdownItems() %>"
					elementClasses="visible-interaction"
				/>
			</div>
		</li>

	<%
	}
	%>

</ul>

<liferay-frontend:component
	componentId="<%= com.liferay.asset.publisher.web.internal.constants.AssetPublisherWebKeys.ASSET_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/AssetPublisherDropdownDefaultEventHandler.es"
/>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_asset_publisher_web.view_asset_entry_title_list_jsp");
%>