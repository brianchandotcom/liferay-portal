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

<%@ include file="/asset_view_usages/init.jsp" %>

<%
String className = (String)request.getAttribute("liferay-asset:asset-view-usages:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-asset:asset-view-usages:classPK"));

AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(className, classPK);

List<AssetEntryUsage> assetEntryUsages = AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(assetEntry.getEntryId());
%>

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(assetEntryUsages) %>">
		<ul class="asset-links-list list-group">

			<%
			for (AssetEntryUsage assetEntryUsage : assetEntryUsages) {
			%>

				<li class="list-group-item list-group-item-flex">
					<div class="autofit-col autofit-col-expand">

						<%
						String name = StringPool.BLANK;

						long classNameId = assetEntryUsage.getClassNameId();

						if (classNameId == PortalUtil.getClassNameId(Layout.class)) {
							Layout curLayout = LayoutLocalServiceUtil.fetchLayout(assetEntryUsage.getClassPK());

							if (curLayout != null) {
								name = curLayout.getName(locale);
							}
						}
						else {
							LayoutPageTemplateEntry layoutPageTemplateEntry = LayoutPageTemplateEntryLocalServiceUtil.fetchLayoutPageTemplateEntry(assetEntryUsage.getClassPK());

							if (layoutPageTemplateEntry != null) {
								name = layoutPageTemplateEntry.getName();
							}
						}
						%>

						<h4 class="list-group-title text-truncate">
							<%= HtmlUtil.escape(name) %>
						</h4>

						<h6 class="list-group-subtitle text-truncate">
							<c:choose>
								<c:when test="<%= assetEntryUsage.getClassNameId() == PortalUtil.getClassNameId(AssetDisplayPageEntry.class) %>">
									<liferay-ui:message key="display-page" />
								</c:when>
								<c:when test="<%= assetEntryUsage.getClassNameId() == PortalUtil.getClassNameId(Layout.class) %>">
									<liferay-ui:message key="page" />
								</c:when>
								<c:when test="<%= assetEntryUsage.getClassNameId() == PortalUtil.getClassNameId(FragmentEntryLink.class) %>">
									<liferay-ui:message key="fragment" />
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="page-template" />
								</c:otherwise>
							</c:choose>
						</h6>
					</div>
				</li>

			<%
			}
			%>

		</ul>
	</c:when>
	<c:otherwise>
		<p class="text-secondary">
			<liferay-ui:message key="there-are-no-asset-entry-usages" />
		</p>
	</c:otherwise>
</c:choose>