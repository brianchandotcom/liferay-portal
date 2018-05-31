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
List<FragmentCollection> fragmentCollections = FragmentCollectionServiceUtil.getFragmentCollections(themeDisplay.getScopeGroupId());
%>

<div class="container-fluid container-fluid-max-xl container-view">
	<div class="row">
		<div class="col-lg-3">
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<portlet:renderURL var="editFragmentCollectionURL">
							<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_collection" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:renderURL>

						<c:choose>
							<c:when test="<%= ListUtil.isNotEmpty(fragmentCollections) %>">
								<div class="align-items-center autofit-row">
									<div class="autofit-col autofit-col-expand">
										<strong class="text-uppercase">
											<liferay-ui:message key="collections" />
										</strong>
									</div>

									<div class="autofit-col autofit-col-end">
										<ul class="navbar-nav">
											<li>
												<c:if test="<%= fragmentDisplayContext.isShowAddButton(FragmentActionKeys.ADD_FRAGMENT_COLLECTION) %>">
													<liferay-ui:icon
														icon="plus"
														iconCssClass="btn btn-outline-borderless btn-outline-secondary btn-sm"
														markupView="lexicon"
														url="<%= editFragmentCollectionURL %>"
													/>
												</c:if>
											</li>
											<li>
												<clay:dropdown-actions
													dropdownItems="<%= fragmentDisplayContext.getCollectionsDropdownItems() %>"
												/>
											</li>
										</ul>
									</div>
								</div>

								<ul class="nav nav-stacked">

									<%
									for (FragmentCollection fragmentCollection : fragmentCollections) {
									%>

										<li class="nav-item">

											<%
											PortletURL fragmentCollectionURL = renderResponse.createRenderURL();

											fragmentCollectionURL.setParameter("mvcRenderCommandName", "/fragment/view");
											fragmentCollectionURL.setParameter("fragmentCollectionId", String.valueOf(fragmentCollection.getFragmentCollectionId()));
											%>

											<a class="nav-link truncate-text <%= (fragmentCollection.getFragmentCollectionId() == fragmentDisplayContext.getFragmentCollectionId()) ? "active" : StringPool.BLANK %>" href="<%= fragmentCollectionURL.toString() %>">
												<%= HtmlUtil.escape(fragmentCollection.getName()) %>
											</a>
										</li>

									<%
									}
									%>

								</ul>
							</c:when>
							<c:otherwise>
								<p class="text-uppercase">
									<strong><liferay-ui:message key="collections" /></strong>
								</p>

								<liferay-frontend:empty-result-message
									actionDropdownItems="<%= fragmentDisplayContext.isShowAddButton(FragmentActionKeys.ADD_FRAGMENT_COLLECTION) ? fragmentDisplayContext.getActionDropdownItems() : null %>"
									animationType="<%= EmptyResultMessageKeys.AnimationType.NONE %>"
									description='<%= LanguageUtil.get(request, "collections-are-needed-to-create-fragments") %>'
									elementType='<%= LanguageUtil.get(request, "collections") %>'
								/>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</nav>
		</div>

		<div class="col-lg-9">

			<%
			FragmentCollection fragmentCollection = fragmentDisplayContext.getFragmentCollection();
			%>

			<c:if test="<%= fragmentCollection != null %>">
				<div class="sheet">
					<h3 class="sheet-title">
						<div class="align-items-center autofit-row">
							<div class="autofit-col">
								<%= HtmlUtil.escape(fragmentCollection.getName()) %>
							</div>

							<div class="autofit-col autofit-col-end inline-item-after">
								<liferay-util:include page="/fragment_collection_action.jsp" servletContext="<%= application %>" />
							</div>
						</div>
					</h3>

					<div class="sheet-section">
						<liferay-util:include page="/view_fragment_entries.jsp" servletContext="<%= application %>" />
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>

<aui:form cssClass="hide" name="fragmentCollectionsFm">
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	window.<portlet:namespace />deleteCollections = function() {
		var fragmentCollectionsFm = $(document.<portlet:namespace />fragmentCollectionsFm);

		var itemSelectorDialog = new A.LiferayItemSelectorDialog(
			{
				eventName: '<portlet:namespace />selectCollections',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							fragmentCollectionsFm.append(selectedItem);

							submitForm(fragmentCollectionsFm, '<liferay-portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="/fragment/delete_fragment_collection"></liferay-portlet:actionURL>');
						}
					}
				},
				'strings.add': '<liferay-ui:message key="delete" />',
				title: '<liferay-ui:message key="delete-collection" />',
				url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/fragment/view_fragment_collections" /></portlet:renderURL>'
			}
		);

		itemSelectorDialog.open();
	}

	window.<portlet:namespace />exportCollections = function() {
		var fragmentCollectionsFm = $(document.<portlet:namespace />fragmentCollectionsFm);

		var itemSelectorDialog = new A.LiferayItemSelectorDialog(
			{
				eventName: '<portlet:namespace />selectCollections',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							fragmentCollectionsFm.append(selectedItem);

							submitForm(fragmentCollectionsFm, '<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/fragment/export_fragment_collections" />');
						}
					}
				},
				'strings.add': '<liferay-ui:message key="export" />',
				title: '<liferay-ui:message key="export-collection" />',
				url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/fragment/view_fragment_collections" /></portlet:renderURL>'
			}
		);

		itemSelectorDialog.open();
	}

	window.<portlet:namespace />openImportView = function() {
		Liferay.Util.openWindow(
			{
				dialog: {
					after: {
						destroy: function(event) {
							window.location.reload();
						}
					},
					destroyOnHide: true
				},
				id: '<portlet:namespace />openImportView',
				title: '<liferay-ui:message key="import-collections" />',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/fragment/view_import_fragment_collections" /></portlet:renderURL>'
			}
		);
	}
</aui:script>

<aui:script require="metal-dom/src/all/dom as dom">
	window.<portlet:namespace />exportSelectedFragmentCollections = function() {
		submitForm(document.querySelector('#<portlet:namespace />fm'), '<portlet:resourceURL id="/fragment/export_fragment_collections" />');
	}

	<portlet:renderURL var="importFragmentEntriesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/view_import_fragment_entries.jsp" />
	</portlet:renderURL>

	var importFragmentEntriesActionClickHandler = dom.delegate(
		document.body,
		'click',
		'.<portlet:namespace />import-fragment-entries-action-option > a',
		function(event) {
			debugger;
			var data = event.delegateTarget.dataset;

			event.preventDefault();

			uri = '<%= importFragmentEntriesURL %>';

			uri = Liferay.Util.addParams('<portlet:namespace />fragmentCollectionId=' + data.fragmentCollectionId, uri);

			Liferay.Util.openWindow(
				{
					dialog: {
						after: {
							destroy: function(event) {
								window.location.reload();
							}
						},
						destroyOnHide: true
					},
					id: '<portlet:namespace />openInstallFromURLView',
					title: '<liferay-ui:message key="import-fragments" />',
					uri: uri
				}
			);
		}
	);

	function handleDestroyPortlet() {
		importFragmentEntriesActionClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>