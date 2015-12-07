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

boolean changeStructure = GetterUtil.getBoolean(request.getAttribute("edit_article.jsp-changeStructure"));
%>

<c:choose>
	<c:when test="<%= group.isLayout() %>">
		<div class="alert alert-info">
			<liferay-ui:message key="the-display-page-cannot-be-set-when-the-scope-of-the-web-content-is-a-page" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		String layoutUuid = BeanParamUtil.getString(article, request, "layoutUuid");

		if (changeStructure && (article != null)) {
			layoutUuid = article.getLayoutUuid();
		}

		Layout selLayout = null;

		String layoutBreadcrumb = StringPool.BLANK;

		if (Validator.isNotNull(layoutUuid)) {
			selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getSiteGroupId(), false);

			if (selLayout != null) {
				layoutBreadcrumb = _getLayoutBreadcrumb(request, selLayout, locale);
			}
			else {
				selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getSiteGroupId(), true);

				if (selLayout != null) {
					layoutBreadcrumb = _getLayoutBreadcrumb(request, selLayout, locale);
				}
			}
		}

		Group parentGroup = themeDisplay.getSiteGroup();
		%>

		<liferay-ui:error-marker key="errorSection" value="display-page" />

		<div class="alert alert-info">
			<liferay-ui:message key="default-display-page-help" />
		</div>

		<div id="<portlet:namespace />pagesContainer">
			<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= layoutUuid %>" />

			<div class="display-page-item-container <%= Validator.isNull(layoutBreadcrumb) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />displayPageItemContainer">
				<span class="display-page-item">
					<span>
						<span id="<portlet:namespace />displayPageNameInput"><%= layoutBreadcrumb %></span>

						<span class="display-page-item-remove icon icon-remove" id="<portlet:namespace />displayPageItemRemove" tabindex="0"></span>
					</span>
				</span>
			</div>
		</div>

		<div>
			<aui:button name="chooseDisplayPage" value="choose" />
		</div>

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

			<c:if test="<%= Validator.isNotNull(urlViewInContext) %>">
				<a href="<%= urlViewInContext %>" target="blank">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(defaultDisplayLayout.getName(locale)) %>" key="view-content-in-x" translateArguments="<%= false %>" />
				</a>
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
		%>

		<aui:script sandbox="<%= true %>">
			var displayPageItemContainer = $('#<portlet:namespace />displayPageItemContainer');
			var displayPageNameInput = $('#<portlet:namespace />displayPageNameInput');
			var pagesContainerInput = $('#<portlet:namespace />pagesContainerInput');

			$('#<portlet:namespace />chooseDisplayPage').on(
				'click',
				function(event) {
					Liferay.Util.selectEntity(
						{
							dialog: {
								constrain: true,
								destroyOnHide: true,
								modal: true
							},
							eventName: '<%= eventName %>',
							id: '<portlet:namespace />selectDisplayPage',
							title: '<liferay-ui:message key="select-page" />',
							uri: '<%= itemSelectorURL.toString() %>'
						},
						function(event) {
							pagesContainerInput.val(event.value);

							displayPageNameInput.html(event.layoutpath);

							displayPageItemContainer.removeClass('hide');
						}
					);
				}
			);

			$('#<portlet:namespace />displayPageItemRemove').on(
				'click',
				function(event) {
					pagesContainerInput.val('');

					displayPageItemContainer.addClass('hide');
				}
			);
		</aui:script>
	</c:otherwise>
</c:choose>

<%!
private String _getLayoutBreadcrumb(HttpServletRequest request, Layout layout, Locale locale) throws Exception {
	List<Layout> ancestors = layout.getAncestors();

	StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

	if (layout.isPrivateLayout()) {
		sb.append(LanguageUtil.get(request, "private-pages"));
	}
	else {
		sb.append(LanguageUtil.get(request, "public-pages"));
	}

	sb.append(StringPool.SPACE);
	sb.append(StringPool.GREATER_THAN);
	sb.append(StringPool.SPACE);

	Collections.reverse(ancestors);

	for (Layout ancestor : ancestors) {
		sb.append(HtmlUtil.escape(ancestor.getName(locale)));
		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);
	}

	sb.append(HtmlUtil.escape(layout.getName(locale)));

	return sb.toString();
}
%>