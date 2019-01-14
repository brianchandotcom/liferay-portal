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

<%@ include file="/select_asset_display_page/init.jsp" %>

<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= selectAssetDisplayPageDisplayContext.getLayoutUuid() %>" />

<aui:input id="assetDisplayPageIdInput" ignoreRequestValue="<%= true %>" name="assetDisplayPageId" type="hidden" value="<%= selectAssetDisplayPageDisplayContext.getAssetDisplayPageId() %>" />

<span><liferay-ui:message key="please-select-one-option" /></span>

<liferay-frontend:fieldset
	id='<%= renderResponse.getNamespace() + "eventsContainer" %>'
>

	<%
	String defaultAssetDisplayPageName = selectAssetDisplayPageDisplayContext.getDefaultAssetDisplayPageName();

	String taglibLabelTypeDefault = LanguageUtil.format(resourceBundle, "use-default-display-page-for-x-x", new Object[] {selectAssetDisplayPageDisplayContext.getAssetTypeName(), Validator.isNotNull(defaultAssetDisplayPageName) ? defaultAssetDisplayPageName : LanguageUtil.get(resourceBundle, "none")}, false);

	if (Validator.isNull(defaultAssetDisplayPageName)) {
		taglibLabelTypeDefault += " <span class=\"text-muted\">" + LanguageUtil.get(resourceBundle, "this-entity-will-not-be-referenceable-with-an-url") + "</span>";
	}
	%>

	<aui:input checked="<%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeDefault() %>" label="<%= taglibLabelTypeDefault %>" name="displayPageType" type="radio" value="<%= AssetDisplayPageConstants.TYPE_DEFAULT %>" />

	<aui:input checked="<%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeSpecific() %>" label="use-a-specific-display-page-for-the-entity" name="displayPageType" type="radio" value="<%= AssetDisplayPageConstants.TYPE_SPECIFIC %>" />

	<div class="<%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeSpecific() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />displayPageContainer">
		<p class="text-default">
			<span class="<%= Validator.isNull(selectAssetDisplayPageDisplayContext.getAssetDisplayPageName()) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />displayPageItemRemove" role="button">
				<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
			</span>
			<span id="<portlet:namespace />displayPageNameInput">
				<c:choose>
					<c:when test="<%= Validator.isNull(selectAssetDisplayPageDisplayContext.getAssetDisplayPageName()) %>">
						<span class="text-muted"><liferay-ui:message key="none" /></span>
					</c:when>
					<c:otherwise>
						<%= selectAssetDisplayPageDisplayContext.getAssetDisplayPageName() %>
					</c:otherwise>
				</c:choose>
			</span>
		</p>

		<aui:button name="chooseDisplayPage" value="choose" />

		<c:if test="<%= selectAssetDisplayPageDisplayContext.isURLViewInContext() %>">

			<%
			Layout defaultDisplayLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(selectAssetDisplayPageDisplayContext.getLayoutUuid(), themeDisplay.getScopeGroupId(), false);

			if (defaultDisplayLayout == null) {
				defaultDisplayLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(selectAssetDisplayPageDisplayContext.getLayoutUuid(), themeDisplay.getScopeGroupId(), true);
			}
			%>

			<c:if test="<%= selectAssetDisplayPageDisplayContext.isShowViewInContextLink() %>">
				<aui:a href="<%= selectAssetDisplayPageDisplayContext.getURLViewInContext() %>" target="blank">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(defaultDisplayLayout.getName(locale)) %>" key="view-content-in-x" translateArguments="<%= false %>" />
				</aui:a>
			</c:if>
		</c:if>
	</div>

	<%
	String taglibLabelTypeNone = LanguageUtil.get(resourceBundle, "none") + " <span class=\"text-muted\">" + LanguageUtil.get(resourceBundle, "this-entity-will-not-be-referenceable-with-an-url") + "</span>";
	%>

	<aui:input checked="<%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeNone() %>" label="<%= taglibLabelTypeNone %>" name="displayPageType" type="radio" value="<%= AssetDisplayPageConstants.TYPE_NONE %>" />
</liferay-frontend:fieldset>

<aui:script use="liferay-item-selector-dialog">
	var assetDisplayPageIdInput = $('#<portlet:namespace />assetDisplayPageIdInput');
	var displayPageContainer = $('#<portlet:namespace />displayPageContainer');
	var displayPageItemContainer = $('#<portlet:namespace />displayPageItemContainer');
	var displayPageItemRemove = $('#<portlet:namespace />displayPageItemRemove');
	var displayPageNameInput = $('#<portlet:namespace />displayPageNameInput');
	var pagesContainerInput = $('#<portlet:namespace />pagesContainerInput');

	$('#<portlet:namespace />chooseDisplayPage').on(
		'click',
		function(event) {
			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<%= selectAssetDisplayPageDisplayContext.getEventName() %>',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							assetDisplayPageIdInput.val('');

							pagesContainerInput.val('');

							if (selectedItem) {
								if (selectedItem.type === "asset-display-page") {
									assetDisplayPageIdInput.val(selectedItem.id);
								}
								else {
									pagesContainerInput.val(selectedItem.id);
								}

								displayPageNameInput.html(selectedItem.name);

								displayPageItemRemove.removeClass('hide');
							}
						}
					},
					'strings.add': '<liferay-ui:message key="done" />',
					title: '<liferay-ui:message key="select-page" />',
					url: '<%= selectAssetDisplayPageDisplayContext.getAssetDisplayPageItemSelectorURL() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	displayPageItemRemove.on(
		'click',
		function(event) {
			displayPageNameInput.html('<liferay-ui:message key="none" />');

			pagesContainerInput.val('');

			displayPageItemRemove.addClass('hide');
		}
	);

	$('#<portlet:namespace />eventsContainer').on(
		'change',
		function(event) {
			var target = event.target;

			if (target && target.value === '<%= AssetDisplayPageConstants.TYPE_SPECIFIC %>') {
				displayPageContainer.removeClass('hide');
			}
			else {
				displayPageContainer.addClass('hide');
			}
		}
	);
</aui:script>