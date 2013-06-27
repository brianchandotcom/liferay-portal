<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");
String toLanguageId = (String)request.getAttribute("edit_article.jsp-toLanguageId");

String layoutUuid = BeanParamUtil.getString(article, request, "layoutUuid");

Layout selLayout = null;

String layoutBreadcrumb = StringPool.BLANK;

if (Validator.isNotNull(layoutUuid)) {
	try {
		selLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getSiteGroupId(), false);

		layoutBreadcrumb = _getLayoutBreadcrumb(selLayout, locale);
	}
	catch (NoSuchLayoutException nsle) {
	}

	if (selLayout == null) {
		try {
			selLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getSiteGroupId(), true);

			layoutBreadcrumb = _getLayoutBreadcrumb(selLayout, locale);
		}
		catch (NoSuchLayoutException nsle) {
		}
	}
}

Group parentGroup = themeDisplay.getSiteGroup();
%>

<liferay-ui:error-marker key="errorSection" value="display-page" />

<h3><liferay-ui:message key="display-page" /><liferay-ui:icon-help message="default-display-page-help" /></h3>

<div id="<portlet:namespace />pagesContainer">
	<aui:input id="pagesContainerInput" name="layoutUuid" type="hidden" value="<%= layoutUuid %>" />

	<div class="display-page-item-container hide" id="<portlet:namespace />displayPageItemContainer">
		<span class="display-page-item">
			<span>
				<span id="<portlet:namespace />displayPageNameInput"><%= layoutBreadcrumb %></span>

				<span class="display-page-item-remove icon icon-remove" id="<portlet:namespace />displayPageItemRemove" tabindex="0"></span>
			</span>
		</span>
	</div>
</div>

<div class="display-page-toolbar" id="<portlet:namespace />displayPageToolbar"></div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />loadDisplayPage',
		function(event) {
			var A = AUI();

			var Lang = A.Lang;

			var Util = Liferay.Util;

			var TPL_TAB_CONTENT = '<div id="<portlet:namespace />{tabId}">' +
				'<div id="<portlet:namespace />{tabContentId}"></div>' +
			'</div>';

			var TPL_TAB_VIEW = '<div id="<portlet:namespace />{pagesTabViewId}"></div>' +
				'<div class="alert alert-block selected-page-message" id="<portlet:namespace />selectedPageMessage">' +
					'<%= UnicodeLanguageUtil.get(pageContext, "there-is-no-selected-page") %>' +
				'</div>';

			var dialog;

			var displayPageItemContainer = A.one('#<portlet:namespace />displayPageItemContainer');
			var displayPageNameInput = A.one('#<portlet:namespace />displayPageNameInput');
			var pagesContainerInput = A.one('#<portlet:namespace />pagesContainerInput');

			var pagesTabViewId = A.guid();
			var privatePagesTabContentId = A.guid();
			var privatePagesTabId = A.guid();
			var publicPagesTabContentId = A.guid();
			var publicPagesTabId = A.guid();

			var okButton;
			var privatePagesTabNode;
			var publicPagesTabNode;
			var selectedNodeMessage;
			var tabView;
			var treeViewPrivate;
			var treeViewPublic;

			var treePrivatePagesContainerId = '<portlet:namespace />treeContainerPrivatePagesOutput';
			var treePublicPagesContainerId = '<portlet:namespace />treeContainerPublicPagesOutput';

			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="treeUrlPublicPages">
				<portlet:param name="struts_action" value="/journal/select_display_page" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= ActionKeys.VIEW_TREE %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>" />

				<c:if test="<%= selLayout != null && !selLayout.isPrivateLayout() %>">
					<portlet:param name="selPlid" value="<%= String.valueOf(selLayout.getPlid()) %>" />
				</c:if>

				<portlet:param name="treeId" value="treeContainerPublicPages" />
				<portlet:param name="checkContentDisplayPage" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="draggableTree" value="<%= Boolean.FALSE.toString() %>" />
				<portlet:param name="expandFirstNode" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="saveState" value="<%= Boolean.FALSE.toString() %>" />
			</liferay-portlet:resourceURL>

			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="treeUrlPrivatePages">
				<portlet:param name="struts_action" value="/journal/select_display_page" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= ActionKeys.VIEW_TREE %>" />
				<portlet:param name="tabs1" value="private-pages" />
				<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>" />

				<c:if test="<%= selLayout != null && selLayout.isPrivateLayout() %>">
					<portlet:param name="selPlid" value="<%= String.valueOf(selLayout.getPlid()) %>" />
				</c:if>

				<portlet:param name="treeId" value="treeContainerPrivatePages" />
				<portlet:param name="checkContentDisplayPage" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="expandFirstNode" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="saveState" value="<%= Boolean.FALSE.toString() %>" />
			</liferay-portlet:resourceURL>

			var bindTreeUI = function(treeInstance) {
				treeInstance.after(
					'lastSelectedChange',
					function(event) {
						setSelectedPage(event.newVal);
					}
				);
			};

			var displayPageMessage = function(html, type) {
				selectedNodeMessage.html(html);

				var cssClass = 'selected-page-message';

				if (type) {
					cssClass += ' alert alert-' + type;
				}

				selectedNodeMessage.attr('className', cssClass);
			};

			var getChosenPagePath = function(node) {
				var buffer = [];

				if (A.instanceOf(node, A.TreeNode)) {
					var labelText = Util.escapeHTML(node.get('labelEl').text());

					buffer.push(labelText);

					node.eachParent(
						function(treeNode) {
							var labelEl = treeNode.get('labelEl');

							if (labelEl) {
								labelText = Util.escapeHTML(labelEl.text());

								buffer.unshift(labelText);
							}
						}
					);
				}

				return buffer.join(' > ');
			}

			var getDialog = function() {
				if (!dialog) {
					var bodyContent = Lang.sub(
						TPL_TAB_VIEW,
						{
							pagesTabViewId: pagesTabViewId
						}
					);

					dialog = Liferay.Util.Window.getWindow(
						{
							dialog: {
								align: {
									node: A.one('#portlet_<%= portletDisplay.getId() %>'),
									points: ['tc', 'tc']
								},
								bodyContent: bodyContent,
								cssClass: 'display-page-dialog',
								toolbars: {
									footer: [
										{
											disabled: true,
											on: {
												click: setDisplayPage
											},
											label: '<%= UnicodeLanguageUtil.get(pageContext, "ok") %>'
										},
										{
											on: {
												click: function() {
													dialog.hide();
												}
											},
											label: '<%= UnicodeLanguageUtil.get(pageContext, "cancel") %>'
										}
									]
								}
							},
							title: '<%= UnicodeLanguageUtil.get(pageContext, "choose-a-display-page") %>'
						}
					);

					selectedNodeMessage = A.one('#<portlet:namespace />selectedPageMessage');

					var dialogButtons = dialog.buttons;

					okButton = dialog.getToolbar('footer').item(0);

					var tabs = [];

					<c:if test="<%= parentGroup.getPublicLayoutsPageCount() > 0 %>">
						tabs.push(
							{
								label: '<%= UnicodeLanguageUtil.get(pageContext, "public-pages") %>',
								content: Lang.sub(
									TPL_TAB_CONTENT,
									{
										tabContentId: publicPagesTabContentId,
										tabId: publicPagesTabId
									}
								)
							}
						);
					</c:if>

					<c:if test="<%= parentGroup.getPrivateLayoutsPageCount() > 0 %>">
						tabs.push(
							{
								label: '<%= UnicodeLanguageUtil.get(pageContext, "private-pages") %>sssssss',
								content: Lang.sub(
									TPL_TAB_CONTENT,
									{
										tabContentId: privatePagesTabContentId,
										tabId: privatePagesTabId
									}
								)
							}
						);
					</c:if>

					tabView = new A.TabView(
						{
							children: tabs,
							contentBox: '#<portlet:namespace />' + pagesTabViewId
						}
					);

					tabView.render();

					tabView.after(
						'activeTabChange',
						function() {
							displayPageMessage('');

							loadPages();
						}
					);

					<c:if test="<%= parentGroup.getPublicLayoutsPageCount() > 0 %>">
						publicPagesTabNode = A.one('#<portlet:namespace />' + publicPagesTabContentId);

						publicPagesTabNode.plug(A.Plugin.ParseContent);
					</c:if>

					<c:if test="<%= parentGroup.getPrivateLayoutsPageCount() > 0 %>">
						privatePagesTabNode = A.one('#<portlet:namespace />' + privatePagesTabContentId);

						privatePagesTabNode.plug(A.Plugin.ParseContent);
					</c:if>

					dialog.on('visibleChange', onDialogVisibleChange);
				}

				return dialog;
			};

			var isPublicPagesTabSelected = function() {
				var result = <%= parentGroup.getPublicLayoutsPageCount() > 0 %>;

				if (tabView.size() >= 2) {
					var index = tabView.getTabIndex(tabView.get('activeTab'));

					result = (index == 0);
				}

				return result;
			};

			var loadPages = function() {
				var url;

				var publicPages = isPublicPagesTabSelected();

				if (publicPages && !treeViewPublic) {
					url = '<%= treeUrlPublicPages %>';
				}
				else if (!treeViewPrivate) {
					url = '<%= treeUrlPrivatePages %>';
				}

				if (url) {
					A.io.request(
						url,
						{
							on: {
								success: function(event, id, obj) {
									var response = this.get('responseData');

									onPagesLoad(response, publicPages);
								}
							}
						}
					);
				}
				else {
					var treeInstance = treeViewPrivate;

					if (publicPages) {
						treeInstance = treeViewPublic;
					}

					setSelectedPage(treeInstance.get('lastSelected'));
				}
			}

			var onDialogVisibleChange = function(event) {
				if (!event.newVal) {
					var treeContainer;

					if (treeViewPublic) {
						treeViewPublic.destroy();

						treeViewPublic = null;
					}

					if (treeViewPrivate) {
						treeViewPrivate.destroy();

						treeViewPrivate = null;
					}

					if (treeContainer) {
						treeContainer.purge(true);
					}

					displayPageMessage('<%= UnicodeLanguageUtil.get(pageContext, "there-is-no-selected-page") %>', 'alert');
				}
				else {
					loadPages();
				}
			};

			var onPagesLoad = function(response, publicPages) {
				var treeContainerId;
				var treeWrapper;

				if (publicPages) {
					treeContainerId = treePublicPagesContainerId;
					treeWrapper = publicPagesTabNode;
				}
				else {
					treeContainerId = treePrivatePagesContainerId;
					treeWrapper = privatePagesTabNode;
				}

				if (treeWrapper) {
					treeWrapper.setContent(response);

					var treeContainer = A.one('#' + treeContainerId);

					var processTreeTask = A.debounce(
						function() {
							treeViewInstance = treeContainer.getData('treeInstance');

							if (treeViewInstance) {
								if (publicPages) {
									treeViewPublic = treeViewInstance;
								}
								else {
									treeViewPrivate = treeViewInstance;
								}

								bindTreeUI(treeViewInstance);

								treeContainer.swallowEvent('click', true);

								setSelectedPage(treeViewInstance.get('lastSelected'));
							}
							else {
								processTreeTask();
							}
						},
						100
					);

					processTreeTask();
				}
			};

			var onSelectDisplayPage = function(event) {
				<c:if test="<%= (parentGroup.getPrivateLayoutsPageCount() > 0) || (parentGroup.getPublicLayoutsPageCount() > 0) %>">
					event.domEvent.preventDefault();

					getDialog().show();
				</c:if>
			};

			var setDisplayPage = function() {
				var publicPages = isPublicPagesTabSelected();

				var tree;

				if (publicPages && treeViewPublic) {
					tree = treeViewPublic;
				}
				else if (treeViewPrivate) {
					tree = treeViewPrivate;
				}

				if (tree) {
					var lastSelected = tree.get('lastSelected');

					if (lastSelected) {
						var labelEl = lastSelected.get('labelEl');

						var link = labelEl.one('a');

						if (link && !link.hasClass('layout-page-invalid')) {
							var label = getChosenPagePath(lastSelected);

							var uuid = link.attr('data-uuid');

							pagesContainerInput.val(uuid);

							displayPageNameInput.html(label);

							displayPageItemContainer.show();

							if (A.UA.webkit) {
								var parentNode = removeDisplayPageItem.get('parentNode');

								removeDisplayPageItem.remove();

								parentNode.appendChild(removeDisplayPageItem);
							}

							getDialog().hide();
						}
					}
				}
			};

			var setSelectedPage = function(lastSelectedNode) {
				var disabled = true;

				var messageText = '<%= UnicodeLanguageUtil.get(pageContext, "there-is-no-selected-page") %>';
				var messageType = 'alert';

				if (lastSelectedNode) {
					var labelEl = lastSelectedNode.get('labelEl');

					var link = labelEl.one('a');

					var text = getChosenPagePath(lastSelectedNode);

					if (link && !link.hasClass('layout-page-invalid')) {
						disabled = false;

						messageText = text;
						messageType = 'info';
					}
					else if (text) {
						messageText = Lang.sub('<%= UnicodeLanguageUtil.get(pageContext, "x-is-not-a-content-display-page") %>', ['"' + text + '"']);
					}
				}

				displayPageMessage(messageText, messageType);

				okButton.set('disabled', disabled);
			};

			var toolbar = new A.Toolbar(
				{
					children: [
						{
							<c:if test="<%= (parentGroup.getPrivateLayoutsPageCount() <= 0) && (parentGroup.getPublicLayoutsPageCount() <= 0) %>">
								disabled: true,
							</c:if>

							icon: 'icon-search',
							label: '<%= UnicodeLanguageUtil.get(pageContext, "select") %>',
							on: {
								click: onSelectDisplayPage
							}
						}
					]
				}
			).render('#<portlet:namespace />displayPageToolbar');

			if (displayPageNameInput.text()) {
				displayPageItemContainer.show();
			}

			var removeDisplayPageItem = A.one('#<portlet:namespace />displayPageItemRemove');

			removeDisplayPageItem.on(
				'click',
				function(event) {
					pagesContainerInput.val('');

					displayPageItemContainer.hide();
				}
			);
		},
		['aui-io-plugin-deprecated', 'aui-io-request', 'aui-tabview', 'aui-tree', 'liferay-util-window']
	);

	<c:choose>
		<c:when test="<%= Validator.isNull(toLanguageId) %>">
			Liferay.once('formNavigator:reveal<portlet:namespace />displayPage', <portlet:namespace />loadDisplayPage);
		</c:when>
		<c:otherwise>
			<portlet:namespace />loadDisplayPage();
		</c:otherwise>
	</c:choose>
</aui:script>

<c:if test="<%= (article != null) && Validator.isNotNull(layoutUuid) %>">

	<%
	Layout defaultDisplayLayout = null;

	try {
		defaultDisplayLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layoutUuid, scopeGroupId, false);
	}
	catch (NoSuchLayoutException nsle) {
		defaultDisplayLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layoutUuid, scopeGroupId, true);
	}

	defaultDisplayLayout = defaultDisplayLayout.toEscapedModel();

	AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());

	AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(article.getResourcePrimKey());

	String urlViewInContext = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, currentURL);
	%>

	<c:if test="<%= Validator.isNotNull(urlViewInContext) %>">
		<a href="<%= urlViewInContext %>" target="blank"><%= LanguageUtil.format(pageContext, "view-content-in-x", defaultDisplayLayout.getName(locale)) %></a>
	</c:if>
</c:if>

<%!
private String _getLayoutBreadcrumb(Layout layout, Locale locale) throws Exception {
	StringBundler sb = new StringBundler();

	layout = layout.toEscapedModel();

	if (layout.isPrivateLayout()) {
		sb.append(LanguageUtil.get(locale, "private-pages"));
	}
	else {
		sb.append(LanguageUtil.get(locale, "public-pages"));
	}

	sb.append(StringPool.SPACE);
	sb.append(StringPool.GREATER_THAN);
	sb.append(StringPool.SPACE);

	List<Layout> ancestors = layout.getAncestors();

	Collections.reverse(ancestors);

	for (Layout ancestor : ancestors) {
		ancestor = ancestor.toEscapedModel();

		sb.append(ancestor.getName(locale));
		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);
	}

	sb.append(layout.getName(locale));

	return sb.toString();
}
%>