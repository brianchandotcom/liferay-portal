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
JournalArticle article = journalDisplayContext.getArticle();

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

Group group = GroupLocalServiceUtil.fetchGroup(groupId);

long classNameId = ParamUtil.getLong(request, "classNameId");

boolean changeStructure = GetterUtil.getBoolean(request.getAttribute("edit_article.jsp-changeStructure"));
%>

<c:choose>
	<c:when test="<%= group.isLayout() %>">
		<p class="text-muted">
			<liferay-ui:message key="the-display-page-cannot-be-set-when-the-scope-of-the-web-content-is-a-page" />
		</p>
	</c:when>
	<c:otherwise>

		<%
		String layoutUuid = BeanParamUtil.getString(article, request, "layoutUuid");

		if (changeStructure && (article != null)) {
			layoutUuid = article.getLayoutUuid();
		}

		String layoutBreadcrumb = StringPool.BLANK;

		if (Validator.isNotNull(layoutUuid)) {
			Layout selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getSiteGroupId(), false);

			if (selLayout == null) {
				selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getSiteGroupId(), true);
			}

			if (selLayout != null) {
				layoutBreadcrumb = journalDisplayContext.getLayoutBreadcrumb(selLayout);
			}
		}
		%>

		<liferay-ui:error-marker
			key="<%= WebKeys.ERROR_SECTION %>"
			value="display-page"
		/>

		<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= layoutUuid %>" />

		<p class="text-muted">
			<liferay-ui:message key="default-display-page-help" />
		</p>

		<p class="text-default">
			<span class="<%= Validator.isNull(layoutBreadcrumb) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />displayPageItemRemove" role="button">
				<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
			</span>
			<span id="<portlet:namespace />displayPageNameInput">
				<c:choose>
					<c:when test="<%= Validator.isNull(layoutBreadcrumb) %>">
						<span class="text-muted"><liferay-ui:message key="none" /></span>
					</c:when>
					<c:otherwise>
						<%= layoutBreadcrumb %>
					</c:otherwise>
				</c:choose>
			</span>
		</p>

		<aui:button name="chooseDisplayPage" value="choose" />

		<c:if test="<%= (article != null) && Validator.isNotNull(layoutUuid) %>">

			<%
			Layout defaultDisplayLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, scopeGroupId, false);

			if (defaultDisplayLayout == null) {
				defaultDisplayLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, scopeGroupId, true);
			}

			AssetRendererFactory<JournalArticle> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(JournalArticle.class);

			AssetRenderer<JournalArticle> assetRenderer = assetRendererFactory.getAssetRenderer(article.getResourcePrimKey());

			String urlViewInContext = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, currentURL);
			%>

			<c:if test="<%= !article.isNew() && (classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) && Validator.isNotNull(urlViewInContext) %>">
				<aui:a href="<%= urlViewInContext %>" target="blank">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(defaultDisplayLayout.getName(locale)) %>" key="view-content-in-x" translateArguments="<%= false %>" />
				</aui:a>
			</c:if>
		</c:if>

		<%
		String eventName = liferayPortletResponse.getNamespace() + "selectDisplayPage";

		ItemSelector itemSelector = (ItemSelector)request.getAttribute(JournalWebKeys.ITEM_SELECTOR);

		LayoutItemSelectorCriterion layoutItemSelectorCriterion = new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setCheckDisplayPage(true);

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes = new ArrayList<ItemSelectorReturnType>();

		desiredItemSelectorReturnTypes.add(new UUIDItemSelectorReturnType());

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(desiredItemSelectorReturnTypes);

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(liferayPortletRequest), eventName, layoutItemSelectorCriterion);

		itemSelectorURL.setParameter("layoutUuid", layoutUuid);
		%>

		<aui:script use="liferay-item-selector-dialog">
			var displayPageItemContainer = $('#<portlet:namespace />displayPageItemContainer');
			var displayPageItemRemove = $('#<portlet:namespace />displayPageItemRemove');
			var displayPageNameInput = $('#<portlet:namespace />displayPageNameInput');
			var pagesContainerInput = $('#<portlet:namespace />pagesContainerInput');

			$('#<portlet:namespace />chooseDisplayPage').on(
				'click',
				function(event) {
					var itemSelectorDialog = new A.LiferayItemSelectorDialog(
						{
							eventName: '<%= eventName %>',
							on: {
								selectedItemChange: function(event) {
									var selectedItem = event.newVal;

									if (selectedItem) {
										pagesContainerInput.val(selectedItem.id);

										displayPageNameInput.html(selectedItem.name);

										displayPageItemRemove.removeClass('hide');
									}
								}
							},
							'strings.add': '<liferay-ui:message key="done" />',
							title: '<liferay-ui:message key="select-page" />',
							url: '<%= itemSelectorURL.toString() %>'
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