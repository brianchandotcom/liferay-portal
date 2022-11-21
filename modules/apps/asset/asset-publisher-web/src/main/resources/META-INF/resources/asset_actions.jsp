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
			<clay:dropdown-actions
				aria-label='<%= LanguageUtil.format(locale, "actions-for-x", assetEntry.getTitle(locale)) %>'
				borderless="<%= true %>"
				cssClass="text-primary visible-interaction"
				displayType="unstyled"
				dropdownItems="<%= dropdownItems %>"
				monospaced="<%= true %>"
				propsTransformer="js/AssetEntryActionDropdownPropsTransformer"
				small="<%= true %>"
				title='<%= LanguageUtil.format(locale, "actions-for-x", assetEntry.getTitle(locale)) %>'
			/>
		</c:when>
		<c:otherwise>

			<%
			DropdownItem dropdownItem = dropdownItems.get(0);

			Map<String, Object> data = (HashMap<String, Object>)dropdownItem.get("data");
			%>

			<c:choose>
				<c:when test='<%= (data != null) && GetterUtil.getBoolean(data.get("useDialog")) %>'>
					<clay:button
						aria-label='<%= String.valueOf(dropdownItem.get("label")) %>'
						borderless="<%= true %>"
						cssClass="text-primary visible-interaction"
						data-url='<%= String.valueOf(data.get("assetEntryActionURL")) %>'
						displayType="unstyled"
						icon='<%= String.valueOf(dropdownItem.get("icon")) %>'
						monospaced="<%= true %>"
						propsTransformer="js/AssetEntryActionButtonPropsTransformer"
						small="<%= true %>"
						title='<%= String.valueOf(dropdownItem.get("label")) %>'
					/>
				</c:when>
				<c:otherwise>
					<clay:link
						aria-label='<%= String.valueOf(dropdownItem.get("label")) %>'
						borderless="<%= true %>"
						cssClass="lfr-portal-tooltip text-primary visible-interaction"
						displayType="unstyled"
						href='<%= String.valueOf(dropdownItem.get("href")) %>'
						icon='<%= String.valueOf(dropdownItem.get("icon")) %>'
						monospaced="<%= true %>"
						small="<%= true %>"
						title='<%= String.valueOf(dropdownItem.get("label")) %>'
					/>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</c:if>