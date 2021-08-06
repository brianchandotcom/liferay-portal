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
String redirect = PortalUtil.getLayoutFullURL(layout, themeDisplay);
%>

<clay:container-fluid>
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>

			<%
			long[] groupIds = assetPublisherDisplayContext.getGroupIds();

			Map<Long, List<AssetPublisherAddItemHolder>> scopeAssetPublisherAddItemHolders = assetPublisherDisplayContext.getScopeAssetPublisherAddItemHolders(groupIds.length);
			%>

			<aui:select label="scope" name="selectScope">

				<%
				for (Long groupId : scopeAssetPublisherAddItemHolders.keySet()) {
				%>

					<aui:option label="<%= HtmlUtil.escape((GroupLocalServiceUtil.getGroup(groupId)).getDescriptiveName(locale)) %>" selected="<%= groupId == scopeGroupId %>" value="<%= groupId %>" />

				<%
				}
				%>

			</aui:select>

			<%
			for (Map.Entry<Long, List<AssetPublisherAddItemHolder>> entry : scopeAssetPublisherAddItemHolders.entrySet()) {
				Long groupId = entry.getKey();
				List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders = entry.getValue();
			%>

				<div class="asset-entry-type <%= (groupId == scopeGroupId) ? StringPool.BLANK : "hide" %>" id="<%= liferayPortletResponse.getNamespace() + groupId %>">
					<aui:select cssClass="asset-entry-type-select" label="asset-type" name="selectAssetEntryType">

						<%
						for (AssetPublisherAddItemHolder assetPublisherAddItemHolder : assetPublisherAddItemHolders) {
							String message = assetPublisherAddItemHolder.getModelResource();

							long curGroupId = groupId;

							Group group = GroupLocalServiceUtil.fetchGroup(groupId);

							if (!group.isStagedPortlet(assetPublisherAddItemHolder.getPortletId()) && !group.isStagedRemotely()) {
								curGroupId = group.getLiveGroupId();
							}

							PortletURL portletURL = PortletURLBuilder.create(
								assetPublisherAddItemHolder.getPortletURL()
							).setRedirect(
								redirect
							).buildPortletURL();
						%>

							<aui:option
								data='<%=
									HashMapBuilder.<String, Object>put(
										"title", LanguageUtil.format((HttpServletRequest)pageContext.getRequest(), "new-x", HtmlUtil.escape(message), false)
									).put(
										"url", assetHelper.getAddURLPopUp(curGroupId, plid, portletURL, false, null)
									).build()
								%>'
								label="<%= HtmlUtil.escape(message) %>"
							/>

						<%
						}
						%>

					</aui:select>
				</div>

				<aui:script>
					Liferay.Util.toggleSelectBox(
						'<portlet:namespace />selectScope',
						'<%= groupId %>',
						'<portlet:namespace /><%= groupId %>'
					);
				</aui:script>

			<%
			}
			%>

		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "addAssetEntry();" %>' primary="<%= true %>" value="add" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />addAssetEntry() {
		var visibleItem = document.querySelector('.asset-entry-type:not(.hide)');

		var assetEntryTypeSelector = visibleItem.querySelector(
			'.asset-entry-type-select'
		);

		var selectedOption =
			assetEntryTypeSelector.options[assetEntryTypeSelector.selectedIndex];

		Liferay.Util.navigate(selectedOption.dataset.url);
	}
</aui:script>