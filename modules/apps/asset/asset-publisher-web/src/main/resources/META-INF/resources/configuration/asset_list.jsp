<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AssetListEntry assetListEntry = assetPublisherDisplayContext.fetchAssetListEntry();
%>

<aui:input id="assetListEntryId" name="preferences--assetListEntryId--" type="hidden" value="<%= (assetListEntry != null) ? assetListEntry.getAssetListEntryId() : StringPool.BLANK %>" />
<aui:input id="infoListProviderKey" name="preferences--infoListProviderKey--" type="hidden" value="<%= assetPublisherDisplayContext.getInfoListProviderKey() %>" />

<div class="form-group input-text-wrapper text-default" id="<portlet:namespace />title">
	<c:choose>
		<c:when test="<%= assetListEntry != null %>">
			<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
		</c:when>
		<c:when test="<%= Validator.isNotNull(assetPublisherDisplayContext.getInfoListProviderKey()) %>">
			<%= assetPublisherDisplayContext.getInfoListProviderLabel() %>
		</c:when>
		<c:otherwise>
			<span class="text-muted"><liferay-ui:message key="none" /></span>
		</c:otherwise>
	</c:choose>
</div>

<div class="button-row">
	<clay:button
		cssClass="mr-2"
		displayType="secondary"
		id='<%= liferayPortletResponse.getNamespace() + "selectAssetListButton" %>'
		label="select"
	/>

	<clay:button
		displayType="secondary"
		id='<%= liferayPortletResponse.getNamespace() + "clearAssetListButton" %>'
		label="clear"
	/>
</div>

<aui:script sandbox="<%= true %>">
	var assetListEntryId = document.getElementById(
		'<portlet:namespace />assetListEntryId'
	);
	var infoListProviderKey = document.getElementById(
		'<portlet:namespace />infoListProviderKey'
	);
	var title = document.getElementById('<portlet:namespace />title');

	var selectAssetListButton = document.getElementById(
		'<portlet:namespace />selectAssetListButton'
	);

	if (selectAssetListButton) {
		selectAssetListButton.addEventListener('click', () => {
			Liferay.Util.openSelectionModal({
				onSelect: function (selectedItem) {
					if (selectedItem) {
						if (
							selectedItem.returnType ===
							'<%= InfoListItemSelectorReturnType.class.getName() %>'
						) {
							var itemValue = JSON.parse(selectedItem.value);

							assetListEntryId.value = itemValue.classPK;

							title.innerHTML = itemValue.title;

							infoListProviderKey.value = '';
						}
						else if (
							selectedItem.returnType ===
							'<%= InfoListProviderItemSelectorReturnType.class.getName() %>'
						) {
							var itemValue = JSON.parse(selectedItem.value);

							title.innerHTML = itemValue.title;

							infoListProviderKey.value = itemValue.key;

							assetListEntryId.value = '';
						}
					}
				},
				selectEventName:
					'<%= assetPublisherDisplayContext.getSelectAssetListEventName() %>',
				title: '<liferay-ui:message key="select-collection" />',
				url:
					'<%= assetPublisherDisplayContext.getAssetListSelectorURL() %>',
			});
		});
	}

	var clearAssetListButton = document.getElementById(
		'<portlet:namespace />clearAssetListButton'
	);

	if (clearAssetListButton) {
		clearAssetListButton.addEventListener('click', (event) => {
			title.innerHTML = '<liferay-ui:message key="none" />';

			assetListEntryId.value = '';
			infoListProviderKey.value = '';
		});
	}
</aui:script>