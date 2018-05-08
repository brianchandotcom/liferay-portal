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
EditArticleDisplayPageDisplayContext editArticleDisplayPageDisplayContext = new EditArticleDisplayPageDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

JournalArticle article = editArticleDisplayPageDisplayContext.getArticle();

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

Group group = GroupLocalServiceUtil.fetchGroup(groupId);
%>

<c:choose>
	<c:when test="<%= group.isLayout() %>">
		<p class="text-muted">
			<liferay-ui:message key="the-display-page-cannot-be-set-when-the-scope-of-the-web-content-is-a-page" />
		</p>
	</c:when>
	<c:otherwise>
		<liferay-ui:error-marker
			key="<%= WebKeys.ERROR_SECTION %>"
			value="display-page"
		/>

		<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= editArticleDisplayPageDisplayContext.getLayoutUuid() %>" />

		<aui:input id="assetDisplayPageIdInput" ignoreRequestValue="<%= true %>" name="assetDisplayPageId" type="hidden" value="<%= editArticleDisplayPageDisplayContext.getAssetDisplayPageId() %>" />

		<p class="text-muted">
			<liferay-ui:message key="default-display-page-help" />
		</p>

		<p class="text-default">
			<span class="<%= Validator.isNull(editArticleDisplayPageDisplayContext.getDisplayPageName()) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />displayPageItemRemove" role="button">
				<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
			</span>
			<span id="<portlet:namespace />displayPageNameInput">
				<c:choose>
					<c:when test="<%= Validator.isNull(editArticleDisplayPageDisplayContext.getDisplayPageName()) %>">
						<span class="text-muted"><liferay-ui:message key="none" /></span>
					</c:when>
					<c:otherwise>
						<%= editArticleDisplayPageDisplayContext.getDisplayPageName() %>
					</c:otherwise>
				</c:choose>
			</span>
		</p>

		<aui:button name="chooseDisplayPage" value="choose" />

		<c:if test="<%= editArticleDisplayPageDisplayContext.isURLViewInContext() %>">

			<%
			Layout defaultDisplayLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(editArticleDisplayPageDisplayContext.getLayoutUuid(), themeDisplay.getScopeGroupId(), false);

			if (defaultDisplayLayout == null) {
				defaultDisplayLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(editArticleDisplayPageDisplayContext.getLayoutUuid(), themeDisplay.getScopeGroupId(), true);
			}
			%>

			<aui:a href="<%= editArticleDisplayPageDisplayContext.getURLViewInContext() %>" target="blank">
				<liferay-ui:message arguments="<%= HtmlUtil.escape(defaultDisplayLayout.getName(locale)) %>" key="view-content-in-x" translateArguments="<%= false %>" />
			</aui:a>
		</c:if>

		<aui:script use="liferay-item-selector-dialog">
			var assetDisplayPageIdInput = $('#<portlet:namespace />assetDisplayPageIdInput');
			var displayPageItemContainer = $('#<portlet:namespace />displayPageItemContainer');
			var displayPageItemRemove = $('#<portlet:namespace />displayPageItemRemove');
			var displayPageNameInput = $('#<portlet:namespace />displayPageNameInput');
			var pagesContainerInput = $('#<portlet:namespace />pagesContainerInput');

			$('#<portlet:namespace />chooseDisplayPage').on(
				'click',
				function(event) {
					var itemSelectorDialog = new A.LiferayItemSelectorDialog(
						{
							eventName: '<%= liferayPortletResponse.getNamespace() + "selectDisplayPage" %>',
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
							url: '<%= editArticleDisplayPageDisplayContext.getDisplayPageItemSelectorURL() %>'
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
		</aui:script>
	</c:otherwise>
</c:choose>