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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long parentGroupSearchContainerPrimaryKeys = ParamUtil.getLong(request, "parentGroupSearchContainerPrimaryKeys");

SearchContainer<SiteInitializerItemDisplayContext> SiteInitializerItemSearchContainer = new SearchContainer<>(liferayPortletRequest, currentURLObj, null, "there-are-no-site-templates");

List<SiteInitializerItemDisplayContext> siteInitializerItems = siteAdminDisplayContext.getSiteInitializerItems();

int indexFrom = SiteInitializerItemSearchContainer.getStart();
int indexTo = SiteInitializerItemSearchContainer.getEnd();

if (indexTo > siteInitializerItems.size()) {
	indexTo = siteInitializerItems.size();
}

SiteInitializerItemSearchContainer.setResults(siteInitializerItems.subList(indexFrom, indexTo));
SiteInitializerItemSearchContainer.setTotal(siteInitializerItems.size());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "select-site-template"));
%>

<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) %>">
	<aui:form cssClass="container-fluid-1280" name="fm">
		<liferay-ui:search-container
			searchContainer="<%= SiteInitializerItemSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.site.admin.web.internal.display.context.SiteInitializerItemDisplayContext"
				keyProperty="key"
				modelVar="siteInitializerItem"
			>

				<%
				row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
				%>

				<liferay-ui:search-container-column-text>

					<%
					Map<String, Object> addLayoutData = new HashMap<>();

					String siteInitializerType = siteInitializerItem.getType();

					addLayoutData.put("creation-type", siteInitializerType);

					addLayoutData.put("group-initializer-key", siteInitializerItem.getGroupInitializerKey());
					addLayoutData.put("layout-set-prototype-id", siteInitializerItem.getLayoutSetPrototypeId());

					if (siteInitializerType.equals(SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE) || Validator.isBlank(siteInitializerItem.getIcon())) {
					%>

						<liferay-frontend:icon-vertical-card
							actionJspServletContext="<%= application %>"
							cssClass='<%= renderResponse.getNamespace() + "add-site-action-option" %>'
							data="<%= addLayoutData %>"
							icon="site-template"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= siteInitializerItem.getName() %>"
							url="javascript:;"
						>

						</liferay-frontend:icon-vertical-card>

					<%
					}
					else {
					%>

						<liferay-frontend:vertical-card
							actionJspServletContext="<%= application %>"
							cssClass='<%= renderResponse.getNamespace() + "add-site-action-option" %>'
							data="<%= addLayoutData %>"
							imageUrl="<%= HtmlUtil.escape(siteInitializerItem.getIcon()) %>"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= siteInitializerItem.getName() %>"
							url="javascript:;"
						>

						</liferay-frontend:vertical-card>

					<%
					}
					%>

				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="icon"
				markupView="lexicon"
				searchContainer="<%= SiteInitializerItemSearchContainer %>"
			/>
		</liferay-ui:search-container>

		<portlet:actionURL name="addGroup" var="addSiteURL">
			<portlet:param name="mvcPath" value="/select_layout_set_prototype_entry.jsp" />
			<portlet:param name="groupId" value="<%= String.valueOf(siteAdminDisplayContext.getGroupId()) %>" />
			<portlet:param name="parentGroupSearchContainerPrimaryKeys" value="<%= String.valueOf(parentGroupSearchContainerPrimaryKeys) %>" />
		</portlet:actionURL>

		<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
			AUI().use('liferay-portlet-url');

			var addSiteActionOptionQueryClickHandler = dom.delegate(
				document.body,
				'click',
				'.<portlet:namespace />add-site-action-option',
				function(event) {
					var actionElement = event.delegateTarget;

					var creationType = actionElement.dataset.creationType;

					var portletURL = new Liferay.PortletURL.createURL('<%= addSiteURL %>');

					portletURL.setParameter('creationType', creationType);
					portletURL.setParameter('groupInitializerKey', actionElement.dataset.groupInitializerKey);

					portletURL.setPortletId('<%= SiteAdminPortletKeys.SITE_ADMIN %>');

					modalCommands.openSimpleInputModal(
						{
							checkboxFieldLabel: '<liferay-ui:message key="create-default-pages-as-private-available-only-to-members-if-unchecked-they-will-be-public-available-to-anyone" />',
							checkboxFieldName: creationType == '<%= SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE %>' ? 'layoutSetVisibilityPrivate' : '',
							checkboxFieldValue: false,
							dialogTitle: '<liferay-ui:message key="add-site" />',
							formSubmitURL: portletURL.toString(),
							idFieldName: 'layoutSetPrototypeId',
							idFieldValue: actionElement.dataset.layoutSetPrototypeId,
							mainFieldName: 'name',
							mainFieldLabel: '<liferay-ui:message key="name" />',
							namespace: '<portlet:namespace />',
							spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
						}
					);
				}
			);

			function handleDestroyPortlet () {
				addSiteActionOptionQueryClickHandler.removeListener();

				Liferay.detach('destroyPortlet', handleDestroyPortlet);
			}

			Liferay.on('destroyPortlet', handleDestroyPortlet);
		</aui:script>
	</aui:form>
</c:if>