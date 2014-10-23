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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

Group group = GroupLocalServiceUtil.fetchGroup(groupId);
%>

<c:choose>
	<c:when test="<%= group.isLayout() %>">
		<div class="alert alert-info">
			<liferay-ui:message key="the-display-page-cannot-be-set-when-the-scope-of-the-web-content-is-a-page" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");

		String layoutUuid = BeanParamUtil.getString(article, request, "layoutUuid");

		Layout selLayout = null;

		String layoutBreadcrumb = StringPool.BLANK;

		if (Validator.isNotNull(layoutUuid)) {
			selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getSiteGroupId(), false);

			if (selLayout != null) {
				layoutBreadcrumb = _getLayoutBreadcrumb(selLayout, locale);
			}
			else {
				selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getSiteGroupId(), true);

				if (selLayout != null) {
					layoutBreadcrumb = _getLayoutBreadcrumb(selLayout, locale);
				}
			}
		}

		Group parentGroup = themeDisplay.getSiteGroup();
		%>

		<liferay-ui:error-marker key="errorSection" value="display-page" />

		<h3><liferay-ui:message key="display-page" /><liferay-ui:icon-help message="default-display-page-help" /></h3>

		<div id="<portlet:namespace />pagesContainer">
			<aui:input id="pagesContainerInput" name="layoutUuid" type="hidden" value="<%= layoutUuid %>" />

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

			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(article.getResourcePrimKey());

			String urlViewInContext = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, currentURL);
			%>

			<c:if test="<%= Validator.isNotNull(urlViewInContext) %>">
				<a href="<%= urlViewInContext %>" target="blank"><%= LanguageUtil.format(request, "view-content-in-x", HtmlUtil.escape(defaultDisplayLayout.getName(locale)), false) %></a>
			</c:if>
		</c:if>

		<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_SELECTOR %>" varImpl="documentSelectorURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/document_selector/view" />
			<portlet:param name="tabs1Names" value="pages" />
			<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
			<portlet:param name="checkContentDisplayPage" value="true" />
			<portlet:param name="eventName" value='<%= renderResponse.getNamespace() + "selectDisplayPage" %>' />
		</liferay-portlet:renderURL>

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
							eventName: '<portlet:namespace />selectDisplayPage',
							id: '<portlet:namespace />selectDisplayPage',
							title: '<%= LanguageUtil.get(locale, "select-page") %>',
							uri: '<%= documentSelectorURL.toString() %>'
						},
						function(event) {
							pagesContainerInput.val(event.uuid);

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
private String _getLayoutBreadcrumb(Layout layout, Locale locale) throws Exception {
	List<Layout> ancestors = layout.getAncestors();

	StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

	if (layout.isPrivateLayout()) {
		sb.append(LanguageUtil.get(locale, "private-pages"));
	}
	else {
		sb.append(LanguageUtil.get(locale, "public-pages"));
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