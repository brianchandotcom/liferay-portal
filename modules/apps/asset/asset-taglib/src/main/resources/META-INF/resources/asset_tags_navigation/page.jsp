<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset_tags_navigation/init.jsp" %>

<%
AssetTagsNavigationDisplayContext assetTagsNavigationDisplayContext = new AssetTagsNavigationDisplayContext(request, renderResponse);

Map<String, Object> data = assetTagsNavigationDisplayContext.getData();

JSONArray assetTagsJSONArray = (JSONArray)data.get("assetTags");
%>

<c:choose>
	<c:when test="<%= !JSONUtil.isEmpty(assetTagsJSONArray) %>">
		<ul class="tag-items <%= data.get("displayStyle") %>">

			<%
			for (int i = 0; i < assetTagsJSONArray.length(); i++) {
				JSONObject assetTagJSONObject = assetTagsJSONArray.getJSONObject(i);
			%>

				<li class="tag-popularity-<%= assetTagJSONObject.getString("popularity") %>">
					<span>
						<a class="<%= assetTagsNavigationDisplayContext.getTagSelectedCssClass(assetTagJSONObject) %>" href="<%= assetTagJSONObject.getString("tagURL") %>">
							<%= assetTagJSONObject.getString("tagName") %>
							<c:if test="<%= assetTagsNavigationDisplayContext.isShowAssetCount() %>">
								<span class="tag-asset-count">
									(<%= assetTagJSONObject.get("count") %>)
								</span>
							</c:if>
						</a>
					</span>
				</li>

			<%
			}
			%>

		</ul>
	</c:when>
	<c:otherwise>

		<%
		if (assetTagsNavigationDisplayContext.isHidePortletWhenEmpty()) {
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
		%>

		<clay:alert
			displayType="info"
			message="there-are-no-tags"
		/>
	</c:otherwise>
</c:choose>