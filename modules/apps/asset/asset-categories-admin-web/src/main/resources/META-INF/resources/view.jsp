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

<liferay-ui:error exception="<%= DuplicateCategoryException.class %>" message="there-is-another-category-with-the-same-name-and-the-same-parent" />

<liferay-ui:success key="categoryAdded" message='<%= GetterUtil.getString(MultiSessionMessages.get(renderRequest, "categoryAdded")) %>' />
<liferay-ui:success key="categoryUpdated" message='<%= GetterUtil.getString(MultiSessionMessages.get(renderRequest, "categoryUpdated")) %>' />

<clay:container-fluid
	cssClass="container-view"
>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, true, false, false, true, true) %>"
	/>

	<clay:row>
		<clay:col
			lg="3"
		>
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<c:choose>
							<c:when test="<%= MapUtil.isNotEmpty(assetCategoriesDisplayContext.getInheritedVocabularies()) || ListUtil.isNotEmpty(assetCategoriesDisplayContext.getVocabularies()) %>">
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

													<clay:link
														borderless="<%= true %>"
														cssClass="component-action"
														href="<%= editVocabularyURL.toString() %>"
														icon="plus"
														type="button"
													/>
												</c:if>
											</li>
											<li>
												<liferay-portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="/asset_categories_admin/delete_asset_vocabulary" var="deleteVocabulariesURL">
													<portlet:param name="redirect" value="<%= assetCategoriesDisplayContext.getDefaultRedirect() %>" />
												</liferay-portlet:actionURL>

												<portlet:renderURL var="viewVocabulariesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
													<portlet:param name="mvcPath" value="/view_asset_vocabularies.jsp" />
												</portlet:renderURL>

												<clay:dropdown-actions
													additionalProps='<%=
														HashMapBuilder.<String, Object>put(
															"deleteVocabulariesURL", deleteVocabulariesURL.toString()
														).put(
															"viewVocabulariesURL", viewVocabulariesURL.toString()
														).build()
													%>'
													aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
													dropdownItems="<%= assetCategoriesDisplayContext.getVocabulariesDropdownItems() %>"
													propsTransformer="js/ActionsComponentPropsTransformer"
												/>
											</li>
										</ul>
									</clay:content-col>
								</clay:content-row>

								<c:if test="<%= MapUtil.isNotEmpty(assetCategoriesDisplayContext.getInheritedVocabularies()) %>">

									<%
									Map<String, List<AssetVocabulary>> inheritedVocabularies = assetCategoriesDisplayContext.getInheritedVocabularies();

									for (Map.Entry<String, List<AssetVocabulary>> entry : inheritedVocabularies.entrySet()) {
									%>

										<ul class="mb-2 nav nav-stacked">
											<span class="text-truncate"><%= entry.getKey() %></span>

											<%
											for (AssetVocabulary vocabulary : entry.getValue()) {
											%>

												<li class="nav-item">
													<a
														class="d-flex nav-link <%= (assetCategoriesDisplayContext.getVocabularyId() == vocabulary.getVocabularyId()) ? "active" : StringPool.BLANK %>"
														href="<%=
															PortletURLBuilder.createRenderURL(
																renderResponse
															).setMVCPath(
																"/view.jsp"
															).setParameter(
																"vocabularyId", vocabulary.getVocabularyId()
															).buildString()
														%>"
													>
														<span class="text-truncate"><%= HtmlUtil.escape(vocabulary.getTitle(locale)) %></span>

														<liferay-ui:icon
															icon="lock"
															iconCssClass="ml-1 text-muted"
															markupView="lexicon"
															message="this-vocabulary-can-only-be-edited-from-the-global-site"
														/>

														<c:if test="<%= vocabulary.getVisibilityType() == AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL %>">
															<liferay-ui:icon
																icon="low-vision"
																iconCssClass="ml-1 text-muted"
																markupView="lexicon"
																message="for-internal-use-only"
															/>
														</c:if>
													</a>
												</li>

											<%
											}
											%>

										</ul>

									<%
									}
									%>

								</c:if>

								<c:if test="<%= ListUtil.isNotEmpty(assetCategoriesDisplayContext.getVocabularies()) %>">
									<ul class="mb-2 nav nav-stacked">
										<span class="text-truncate"><%= HtmlUtil.escape(assetCategoriesDisplayContext.getGroupName()) %></span>

										<%
										for (AssetVocabulary vocabulary : assetCategoriesDisplayContext.getVocabularies()) {
										%>

											<li class="nav-item">
												<a
													class="d-flex nav-link <%= (assetCategoriesDisplayContext.getVocabularyId() == vocabulary.getVocabularyId()) ? "active" : StringPool.BLANK %>"
													href="<%=
														PortletURLBuilder.createRenderURL(
															renderResponse
														).setMVCPath(
															"/view.jsp"
														).setParameter(
															"vocabularyId", vocabulary.getVocabularyId()
														).buildString()
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(vocabulary.getTitle(locale)) %></span>

													<c:if test="<%= vocabulary.getVisibilityType() == AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL %>">
														<liferay-ui:icon
															icon="low-vision"
															iconCssClass="ml-1 text-muted"
															markupView="lexicon"
															message="for-internal-use-only"
														/>
													</c:if>
												</a>
											</li>

										<%
										}
										%>

									</ul>
								</c:if>
							</c:when>
							<c:otherwise>
								<p class="text-uppercase">
									<strong><liferay-ui:message key="vocabularies" /></strong>
								</p>

								<liferay-frontend:empty-result-message
									actionDropdownItems="<%= assetCategoriesDisplayContext.getVocabularyActionDropdownItems() %>"
									animationType="<%= EmptyResultMessageKeys.AnimationType.NONE %>"
									componentId='<%= liferayPortletResponse.getNamespace() + "emptyResultMessageComponent" %>'
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
				<clay:sheet
					size="full"
				>
					<h2 class="sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<%= HtmlUtil.escape(vocabulary.getTitle(locale)) %>
							</clay:content-col>

							<clay:content-col
								cssClass="inline-item-after justify-content-end"
							>

								<%
								AssetVocabularyActionDropdownItemsProvider assetVocabularyActionDropdownItemsProvider = new AssetVocabularyActionDropdownItemsProvider(request, renderResponse);
								%>

								<clay:dropdown-actions
									aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
									cssClass="component-action"
									dropdownItems="<%= assetVocabularyActionDropdownItemsProvider.getActionDropdownItems(vocabulary) %>"
									propsTransformer="js/VocabularyActionDropdownPropsTransformer"
								/>
							</clay:content-col>
						</clay:content-row>
					</h2>

					<div class="mb-5">
						<div class="mb-2">
							<span class="mr-1"><liferay-ui:message key="asset-types" />:</span>
							<span class="text-secondary"><%= assetCategoriesDisplayContext.getAssetType(vocabulary) %></span>
						</div>

						<%
						String description = vocabulary.getDescription(locale);
						%>

						<c:if test="<%= Validator.isNotNull(description) %>">
							<div class="mb-2">
								<span class="mr-1"><liferay-ui:message key="description" />:</span>
								<span class="text-break text-secondary"><%= description %></span>
							</div>
						</c:if>
					</div>

					<p class="mb-5 text-secondary">
						<span class="mr-2">
							<liferay-ui:message arguments="<%= assetCategoriesDisplayContext.getMaximumNumberOfCategoriesPerVocabulary() %>" key="the-maximum-number-of-categories-per-vocabulary-is-x" />
						</span>

						<liferay-learn:message
							key="general"
							resource="asset-taglib"
						/>
					</p>

					<c:if test="<%= assetCategoriesDisplayContext.isAssetCategoriesLimitExceeded() %>">
						<div class="alert alert-warning">
							<span class="alert-indicator">
								<aui:icon image="warning" markupView="lexicon" />
							</span>

							<liferay-ui:message arguments="<%= assetCategoriesDisplayContext.getMaximumNumberOfCategoriesPerVocabulary() %>" key="you-have-reached-the-limit-of-x-categories-for-this-vocabulary" />
						</div>
					</c:if>

					<clay:sheet-section>
						<liferay-util:include page="/view_asset_categories.jsp" servletContext="<%= application %>" />
					</clay:sheet-section>
				</clay:sheet>
			</c:if>
		</clay:col>
	</clay:row>
</clay:container-fluid>