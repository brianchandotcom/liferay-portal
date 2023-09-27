<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = PortalUtil.getLayoutFullURL(layout, themeDisplay);
%>

<clay:container-fluid>
	<div class="sheet">
		<div class="panel-group panel-group-flush">
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
		</div>
	</div>

	<aui:button-row>
		<clay:button
			displayType="primary"
			label="add"
			onClick='<%= liferayPortletResponse.getNamespace() + "addAssetEntry();" %>'
		/>

		<clay:link
			displayType="secondary"
			href="<%= redirect %>"
			label="cancel"
			type="button"
		/>
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