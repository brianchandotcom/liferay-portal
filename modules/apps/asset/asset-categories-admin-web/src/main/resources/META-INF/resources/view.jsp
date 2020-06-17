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

<clay:container-fluid
	cssClass="container-view"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<c:choose>
							<c:when test="<%= ListUtil.isNotEmpty(assetCategoriesDisplayContext.getVocabularies()) %>">
								<clay:content-row
									cssClass="mb-4"
									verticalAlign="center"
								>
									<clay:content-col
										expand="<%= true %>"
									>
										<strong class="text-uppercase">
											<liferay-ui:message key="vocabularies" />
										</strong>
									</clay:content-col>

									<clay:content-col>
										<ul class="navbar-nav">
											<li>
												<c:if test="<%= assetCategoriesDisplayContext.hasAddVocabularyPermission() %>">

													<%
													PortletURL editVocabularyURL = assetCategoriesDisplayContext.getEditVocabularyURL();
													%>

													<liferay-ui:icon
														icon="plus"
														iconCssClass="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
														markupView="lexicon"
														url="<%= editVocabularyURL.toString() %>"
													/>
												</c:if>
											</li>
											<li>
												<clay:dropdown-actions
													componentId="actionsComponent"
													dropdownItems="<%= assetCategoriesDisplayContext.getVocabulariesDropdownItems() %>"
												/>
											</li>
										</ul>
									</clay:content-col>
								</clay:content-row>

								<ul class="mb-2 nav nav-stacked">

									<%
									for (AssetVocabulary vocabulary : assetCategoriesDisplayContext.getVocabularies()) {
									%>

										<li class="nav-item">

											<%
											PortletURL vocabularyURL = renderResponse.createRenderURL();

											vocabularyURL.setParameter("mvcPath", "/view.jsp");
											vocabularyURL.setParameter("vocabularyId", String.valueOf(vocabulary.getVocabularyId()));
											%>

											<a class="nav-link text-truncate <%= (assetCategoriesDisplayContext.getVocabularyId() == vocabulary.getVocabularyId()) ? "active" : StringPool.BLANK %>" href="<%= vocabularyURL.toString() %>">
												<%= HtmlUtil.escape(vocabulary.getTitle(locale)) %>
											</a>
										</li>

									<%
									}
									%>

								</ul>
							</c:when>
							<c:otherwise>
								<p class="text-uppercase">
									<strong><liferay-ui:message key="vocabularies" /></strong>
								</p>

								<liferay-frontend:empty-result-message
									actionDropdownItems="<%= assetCategoriesDisplayContext.getVocabularyActionDropdownItems() %>"
									animationType="<%= EmptyResultMessageKeys.AnimationType.NONE %>"
									componentId='<%= renderResponse.getNamespace() + "emptyResultMessageComponent" %>'
									description='<%= LanguageUtil.get(request, "vocabularies-are-needed-to-create-categories") %>'
									elementType='<%= LanguageUtil.get(request, "vocabularies") %>'
								/>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</nav>
		</clay:col>

		<clay:col
			lg="9"
		>

			<%
			AssetVocabulary vocabulary = assetCategoriesDisplayContext.getVocabulary();
			%>

			<c:if test="<%= vocabulary != null %>">
				<clay:sheet>
					<h2 class="sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<%= HtmlUtil.escape(vocabulary.getTitle(locale)) %>
							</clay:content-col>

							<clay:content-col
								cssClass="inline-item-after"
							>
								<liferay-util:include page="/vocabulary_action.jsp" servletContext="<%= application %>" />
							</clay:content-col>
						</clay:content-row>
					</h2>

					<clay:sheet-section>
						<liferay-util:include page="/view_categories.jsp" servletContext="<%= application %>" />
					</clay:sheet-section>
				</clay:sheet>
			</c:if>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<aui:form cssClass="hide" name="vocabulariesFm">
</aui:form>

<aui:script require="metal-dom/src/dom as dom, frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	var deleteVocabularies = function () {
		var vocabulariesFm = document.<portlet:namespace />vocabulariesFm;

		if (vocabulariesFm) {
			var itemSelectorDialog = new ItemSelectorDialog.default({
				buttonAddLabel: '<liferay-ui:message key="delete" />',
				eventName: '<portlet:namespace />selectVocabularies',
				title: '<liferay-ui:message key="delete-vocabulary" />',
				url:
					'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/view_vocabularies.jsp" /></portlet:renderURL>',
			});

			itemSelectorDialog.on('selectedItemChange', function (event) {
				var selectedItems = event.selectedItem;

				if (selectedItems) {
					if (
						confirm(
							'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-entries" />'
						)
					) {
						Array.prototype.forEach.call(selectedItems, function (
							item,
							index
						) {
							dom.append(vocabulariesFm, item);
						});

						<liferay-portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="deleteVocabulary" var="deleteVocabulariesURL">
							<portlet:param name="redirect" value="<%= assetCategoriesDisplayContext.getDefaultRedirect() %>" />
						</liferay-portlet:actionURL>

						submitForm(vocabulariesFm, '<%= deleteVocabulariesURL %>');
					}
				}
			});

			itemSelectorDialog.open();
		}
	};

	var ACTIONS = {
		deleteVocabularies: deleteVocabularies,
	};

	Liferay.componentReady('actionsComponent').then(function (actionsComponent) {
		actionsComponent.on(['click', 'itemClicked'], function (event, facade) {
			var itemData;

			if (event.data && event.data.item) {
				itemData = event.data.item.data;
			}
			else if (!event.data && facade && facade.target) {
				itemData = facade.target.data;
			}

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>