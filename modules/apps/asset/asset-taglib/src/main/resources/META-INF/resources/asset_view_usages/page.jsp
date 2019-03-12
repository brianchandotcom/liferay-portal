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
		<ul class="asset-links-list list-group" id="<portlet:namespace/>assetEntryUsagesList">

			<%
			for (AssetEntryUsage assetEntryUsage : assetEntryUsages) {
			%>

				<li class="list-group-item list-group-item-flex">
					<div class="autofit-col autofit-col-expand">

						<%
						String name = StringPool.BLANK;
						String previewURL = StringPool.BLANK;

						long classNameId = assetEntryUsage.getClassNameId();

						if (classNameId == PortalUtil.getClassNameId(Layout.class)) {
							Layout curLayout = LayoutLocalServiceUtil.fetchLayout(assetEntryUsage.getClassPK());

							if (curLayout != null) {
								name = curLayout.getName(locale);

								previewURL = PortalUtil.getLayoutFriendlyURL(curLayout, themeDisplay);
							}
						}
						else {
							LayoutPageTemplateEntry layoutPageTemplateEntry = LayoutPageTemplateEntryLocalServiceUtil.fetchLayoutPageTemplateEntry(assetEntryUsage.getClassPK());

							if (layoutPageTemplateEntry != null) {
								name = layoutPageTemplateEntry.getName();

								Layout curLayout = LayoutLocalServiceUtil.fetchLayout(layoutPageTemplateEntry.getPlid());

								previewURL = PortalUtil.getLayoutFriendlyURL(curLayout, themeDisplay);
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

					<c:if test="<%= Validator.isNotNull(previewURL) %>">
						<div class="dropdown dropdown-action">

							<%
							Map<String, String> data = new HashMap<>();

							data.put("href", previewURL);
							data.put("title", assetEntry.getTitle(locale));
							%>

							<clay:button
								data="<%= data %>"
								elementClasses="preview-asset-entry-usage"
								icon="view"
								style="secondary"
							/>
						</div>
					</c:if>
				</li>

			<%
			}
			%>

		</ul>

		<aui:script require="metal-dom/src/all/dom as dom">
			var previewAssetEntryUsagesList = dom.delegate(
				document.querySelector('#<portlet:namespace/>assetEntryUsagesList'),
				'click',
				'.preview-asset-entry-usage',
				function(event) {
					var delegateTarget = event.delegateTarget;

					Liferay.fire(
						'previewArticle',
						{
							title: delegateTarget.getAttribute('data-title'),
							uri: delegateTarget.getAttribute('data-href')
						}
					);
				}
			);

			function removeListener() {
				previewAssetEntryUsagesList.removeListener();

				Liferay.detach('destroyPortlet', removeListener);
			}

			Liferay.on('destroyPortlet', removeListener);
		</aui:script>
	</c:when>
	<c:otherwise>
		<p class="text-secondary">
			<liferay-ui:message key="there-are-no-asset-entry-usages" />
		</p>
	</c:otherwise>
</c:choose>