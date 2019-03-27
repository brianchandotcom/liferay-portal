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
AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("view.jsp-assetRenderer");
String fullContentRedirect = (String)request.getAttribute("view.jsp-fullContentRedirect");

AssetEntryActionDropdownItemsProvider assetEntryActionDropdownItemsProvider = new AssetEntryActionDropdownItemsProvider(assetRenderer, assetPublisherDisplayContext.getAssetEntryActions(assetEntry.getClassName()), fullContentRedirect, liferayPortletRequest, liferayPortletResponse);

List<DropdownItem> dropdownItems = assetEntryActionDropdownItemsProvider.getActionDropdownItems();
%>

<c:if test="<%= ListUtil.isNotEmpty(dropdownItems) %>">
	<c:choose>
		<c:when test="<%= dropdownItems.size() > 1 %>">
			<liferay-ui:icon-menu
				cssClass="visible-interaction"
				direction="left-side"
				icon="<%= StringPool.BLANK %>"
				markupView="lexicon"
				message="<%= StringPool.BLANK %>"
				showWhenSingleIcon="<%= true %>"
				triggerCssClass="text-primary"
			>

				<%
				for (DropdownItem dropdownItem : dropdownItems) {
					Map data = (HashMap)dropdownItem.get("data");

					boolean useDialog = GetterUtil.getBoolean(data.get("useDialog"));
				%>

					<liferay-ui:icon
						data="<%= data %>"
						message='<%= String.valueOf(dropdownItem.get("label")) %>'
						method="get"
						url='<%= String.valueOf(dropdownItem.get("href")) %>'
						useDialog="<%= useDialog %>"
					/>

				<%
				}
				%>

			</liferay-ui:icon-menu>
		</c:when>
		<c:otherwise>

			<%
			DropdownItem dropdownItem = dropdownItems.get(0);

			Map data = (HashMap)dropdownItem.get("data");

			boolean useDialog = GetterUtil.getBoolean(data.get("useDialog"));
			%>

			<liferay-ui:icon
				cssClass="visible-interaction"
				data='<%= (HashMap)dropdownItem.get("data") %>'
				icon='<%= String.valueOf(dropdownItem.get("icon")) %>'
				linkCssClass="text-primary"
				markupView="lexicon"
				method="get"
				url='<%= String.valueOf(dropdownItem.get("href")) %>'
				useDialog="<%= useDialog %>"
			/>
		</c:otherwise>
	</c:choose>
</c:if>