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
long previewAssetEntryId = ParamUtil.getLong(request, "previewAssetEntryId");
int previewAssetEntryType = ParamUtil.getInteger(request, "previewAssetEntryType");

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
			if (previewAssetEntryId == assetEntry.getEntryId()) {
				assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK(), previewAssetEntryType);
			}
			else {
				assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		if ((assetRenderer == null) || (!assetRenderer.isDisplayable() && (previewAssetEntryId <= 0))) {
			continue;
		}

		request.setAttribute("view.jsp-assetEntry", assetEntry);
		request.setAttribute("view.jsp-assetRenderer", assetRenderer);

		Map<String, Object> fragmentsEditorData = new HashMap<>();

		fragmentsEditorData.put("fragments-editor-item-id", PortalUtil.getClassNameId(assetRenderer.getClassName()) + "-" + assetRenderer.getClassPK());
		fragmentsEditorData.put("fragments-editor-item-type", "fragments-editor-mapped-item");
	%>

		<li class="list-group-item list-group-item-flex <%= (previewAssetEntryId == assetEntry.getEntryId()) ? "active" : StringPool.BLANK %>" <%= AUIUtil.buildData(fragmentsEditorData) %>>
			<c:if test="<%= assetPublisherDisplayContext.isShowAuthor() %>">
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
				Date displayDate = assetPublisherDisplayContext.isShowCreateDate() ? assetEntry.getCreateDate() : null;

				if (assetPublisherDisplayContext.isShowPublishDate() && (assetEntry.getPublishDate() != null)) {
					displayDate = assetEntry.getPublishDate();
				}
				else if (assetPublisherDisplayContext.isShowModifiedDate() && (assetEntry.getModifiedDate() != null)) {
					displayDate = assetEntry.getModifiedDate();
				}
				%>

				<c:if test="<%= displayDate != null %>">
					<p class="list-group-subtitle text-truncate">
						<%= dateFormatDateTime.format(displayDate) %>
					</p>
				</c:if>

				<c:if test="<%= assetPublisherDisplayContext.isShowCategories() || assetPublisherDisplayContext.isShowTags() %>">
					<div class="list-group-detail">
						<c:if test="<%= assetPublisherDisplayContext.isShowCategories() %>">
							<liferay-asset:asset-categories-summary
								className="<%= assetEntry.getClassName() %>"
								classPK="<%= assetEntry.getClassPK() %>"
								displayStyle="simple-category"
								portletURL="<%= renderResponse.createRenderURL() %>"
							/>
						</c:if>

						<c:if test="<%= assetPublisherDisplayContext.isShowTags() %>">
							<liferay-asset:asset-tags-summary
								className="<%= assetEntry.getClassName() %>"
								classPK="<%= assetEntry.getClassPK() %>"
								portletURL="<%= renderResponse.createRenderURL() %>"
							/>
						</c:if>
					</div>
				</c:if>
			</div>

			<div class="autofit-col">
				<liferay-util:include page="/asset_actions.jsp" servletContext="<%= application %>" />
			</div>
		</li>

	<%
	}
	%>

</ul>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_asset_publisher_web.view_asset_entries_title_list_jsp");
%>