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
List results = (List)request.getAttribute("view.jsp-results");

int assetEntryIndex = ((Integer)request.getAttribute("view.jsp-assetEntryIndex")).intValue();

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("view.jsp-assetRenderer");

Group stageableGroup = themeDisplay.getScopeGroup();

if (stageableGroup.isLayout()) {
	stageableGroup = layout.getGroup();
}

String title = (String)request.getAttribute("view.jsp-title");

if (Validator.isNull(title)) {
	title = assetRenderer.getTitle(locale);
}

PortletURL editPortletURL = assetRenderer.getURLEdit(liferayPortletRequest, liferayPortletResponse);

boolean viewInContext = ((Boolean)request.getAttribute("view.jsp-viewInContext")).booleanValue();

String viewURL = AssetPublisherHelper.getAssetViewURL(liferayPortletRequest, liferayPortletResponse, assetEntry, viewInContext);

request.setAttribute("view.jsp-showIconLabel", false);
%>

<c:if test="<%= assetEntryIndex == 0 %>">
	<table class="table table-list">
		<thead>
			<tr>
				<th class="clamp-horizontal content-column table-cell-content title-column">
					<div class="clamp-container">
						<span class="truncate-text"><liferay-ui:message key="title" /></span>
					</div>
				</th>

				<%
				for (String metadataField : assetPublisherDisplayContext.getMetadataFields()) {
				%>

					<th class="metadata-column table-cell-field text-column">
						<liferay-ui:message key="<%= metadataField %>" />
					</th>

				<%
				}
				%>

				<c:if test="<%= !stageableGroup.hasStagingGroup() %>">
					<th class="entry-action-column table-cell-field"></th>
				</c:if>
			</tr>
		</thead>

		<tbody>
</c:if>

			<tr>
				<td class="clamp-horizontal content-column table-cell-content title-column" colspan="1">
					<span class="asset-anchor lfr-asset-anchor" id="<%= assetEntry.getEntryId() %>"></span>

					<div class="clamp-container">
						<span class="truncate-text">
							<c:choose>
								<c:when test="<%= Validator.isNotNull(viewURL) %>">
									<a href="<%= viewURL %>"><%= HtmlUtil.escape(title) %></a>
								</c:when>
								<c:otherwise>
									<%= HtmlUtil.escape(title) %>
								</c:otherwise>
							</c:choose>
						</span>
					</div>
				</td>

				<%
				for (String metadataField : assetPublisherDisplayContext.getMetadataFields()) {
				%>

					<td class="metadata-column scope-column table-cell-field" colspan="1">
						<c:choose>
							<c:when test='<%= Objects.equals(metadataField, "categories") %>'>
								<liferay-ui:asset-categories-summary
									className="<%= assetEntry.getClassName() %>"
									classPK="<%= assetEntry.getClassPK() %>"
									portletURL="<%= renderResponse.createRenderURL() %>"
								/>
							</c:when>
							<c:when test='<%= Objects.equals(metadataField, "tags") %>'>
								<liferay-ui:asset-tags-summary
									className="<%= assetEntry.getClassName() %>"
									classPK="<%= assetEntry.getClassPK() %>"
									portletURL="<%= renderResponse.createRenderURL() %>"
								/>
							</c:when>
							<c:otherwise>

								<%
								String value = null;

								if (Objects.equals(metadataField, "create-date")) {
									value = dateFormatDate.format(assetEntry.getCreateDate());
								}
								else if (Objects.equals(metadataField, "modified-date")) {
									value = dateFormatDate.format(assetEntry.getModifiedDate());
								}
								else if (Objects.equals(metadataField, "publish-date")) {
									if (assetEntry.getPublishDate() == null) {
										value = StringPool.BLANK;
									}
									else {
										value = dateFormatDate.format(assetEntry.getPublishDate());
									}
								}
								else if (Objects.equals(metadataField, "expiration-date")) {
									if (assetEntry.getExpirationDate() == null) {
										value = StringPool.BLANK;
									}
									else {
										value = dateFormatDate.format(assetEntry.getExpirationDate());
									}
								}
								else if (Objects.equals(metadataField, "priority")) {
									value = String.valueOf(assetEntry.getPriority());
								}
								else if (Objects.equals(metadataField, "author")) {
									String userName = PortalUtil.getUserName(assetRenderer.getUserId(), assetRenderer.getUserName());

									value = HtmlUtil.escape(userName);
								}
								else if (Objects.equals(metadataField, "view-count")) {
									value = String.valueOf(assetEntry.getViewCount());
								}
								%>

								<liferay-ui:message key="<%= value %>" />
							</c:otherwise>
						</c:choose>
					</td>

				<%
				}
				%>

				<c:if test="<%= !stageableGroup.hasStagingGroup() %>">
					<td class="entry-action-column table-cell-field" colspan="1">
						<c:if test="<%= assetRenderer.hasEditPermission(permissionChecker) && (editPortletURL != null) %>">
							<liferay-util:include page="/asset_actions.jsp" servletContext="<%= application %>" />
						</c:if>
					</td>
				</c:if>
			</tr>

<c:if test="<%= (assetEntryIndex + 1) == results.size() %>">
		</tbody>
	</table>
</c:if>