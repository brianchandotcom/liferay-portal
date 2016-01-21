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
String redirect = ParamUtil.getString(request, "redirect");
%>

<aui:fieldset markupView="lexicon">

	<%
	PortletURL redirectURL = renderResponse.createRenderURL();

	redirectURL.setParameter("hideDefaultSuccessMessage", Boolean.TRUE.toString());
	redirectURL.setParameter("mvcPath", "/add_asset_redirect.jsp");
	redirectURL.setParameter("redirect", redirect);
	redirectURL.setWindowState(LiferayWindowState.POP_UP);

	List<Long> addPortletURLsGroupIds = new ArrayList();
	%>

	<aui:select label="scope" name="selectScope">

		<%
		long[] groupIds = assetPublisherDisplayContext.getGroupIds();

		for (long groupId : groupIds) {
			Map<String, PortletURL> addPortletURLs = AssetUtil.getAddPortletURLs(liferayPortletRequest, liferayPortletResponse, groupId, assetPublisherDisplayContext.getClassNameIds(), assetPublisherDisplayContext.getClassTypeIds(), assetPublisherDisplayContext.getAllAssetCategoryIds(), assetPublisherDisplayContext.getAllAssetTagNames(), redirectURL.toString());

			if ((addPortletURLs != null) && !addPortletURLs.isEmpty()) {
				addPortletURLsGroupIds.add(groupId);
		%>

				<aui:option label="<%= HtmlUtil.escape((GroupLocalServiceUtil.getGroup(groupId)).getDescriptiveName(locale)) %>" selected="<%= groupId == scopeGroupId %>" value="<%= groupId %>" />

		<%
			}
		}
		%>

	</aui:select>

	<%
	for (Long groupId : addPortletURLsGroupIds) {
	%>

		<div class='<%= (groupId == scopeGroupId) ? StringPool.BLANK : "hide" %>' id="<%= liferayPortletResponse.getNamespace() + groupId %>">
			<liferay-ui:asset-add-button
				addDisplayPageParameter="<%= AssetUtil.isDefaultAssetPublisher(layout, portletDisplay.getId(), assetPublisherDisplayContext.getPortletResource()) %>"
				allAssetCategoryIds="<%= assetPublisherDisplayContext.getAllAssetCategoryIds() %>"
				allAssetTagNames="<%= assetPublisherDisplayContext.getAllAssetTagNames() %>"
				classNameIds="<%= assetPublisherDisplayContext.getClassNameIds() %>"
				classTypeIds="<%= assetPublisherDisplayContext.getClassTypeIds() %>"
				groupIds="<%= new long[] {groupId} %>"
				redirect="<%= redirectURL.toString() %>"
				useDialog="<%= false %>"
			/>
		</div>

		<aui:script>
			Liferay.Util.toggleSelectBox('<portlet:namespace />selectScope', '<%= groupId %>', '<portlet:namespace /><%= groupId %>');
		</aui:script>

	<%
	}
	%>

</aui:fieldset>