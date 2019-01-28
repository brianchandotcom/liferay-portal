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
SelectSiteInitializerDisplayContext selectSiteInitializerDisplayContext = new SelectSiteInitializerDisplayContext(request, renderRequest, renderResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(selectSiteInitializerDisplayContext.getBackURL());

renderResponse.setTitle(LanguageUtil.get(request, "select-site-template"));
%>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= selectSiteInitializerDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.site.admin.web.internal.util.SiteInitializerItem"
			keyProperty="key"
			modelVar="siteInitializerItem"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());

			Map<String, Object> addLayoutData = new HashMap<>();

			addLayoutData.put("creation-type", siteInitializerItem.getType());
			addLayoutData.put("layout-set-prototype-id", siteInitializerItem.getLayoutSetPrototypeId());
			addLayoutData.put("site-initializer-key", siteInitializerItem.getSiteInitializerKey());
			%>

			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test="<%= Objects.equals(siteInitializerItem.getType(), SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE) || Validator.isBlank(siteInitializerItem.getIcon()) %>">
						<liferay-frontend:icon-vertical-card
							cssClass="add-site-action-option"
							data="<%= addLayoutData %>"
							icon="site-template"
							title="<%= siteInitializerItem.getName() %>"
							url="javascript:;"
						/>
					</c:when>
					<c:otherwise>
						<liferay-frontend:vertical-card
							cssClass="add-site-action-option"
							data="<%= addLayoutData %>"
							imageUrl="<%= HtmlUtil.escape(siteInitializerItem.getIcon()) %>"
							title="<%= siteInitializerItem.getName() %>"
							url="javascript:;"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<portlet:actionURL name="addGroup" var="addSiteURL">
		<portlet:param name="mvcPath" value="/select_layout_set_prototype_entry.jsp" />
		<portlet:param name="parentGroupId" value="<%= String.valueOf(selectSiteInitializerDisplayContext.getParentGroupId()) %>" />
	</portlet:actionURL>

	<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
		var addSiteActionOptionQueryClickHandler = dom.delegate(
			document.body,
			'click',
			'.add-site-action-option',
			function(event) {
				var data = event.delegateTarget.dataset;

				var uri = '<%= addSiteURL %>';

				uri = Liferay.Util.addParams('<portlet:namespace />creationType=' + data.creationType, uri);
				uri = Liferay.Util.addParams('<portlet:namespace />siteInitializerKey=' + data.siteInitializerKey, uri);

				modalCommands.openSimpleInputModal(
					{
						checkboxFieldLabel: '<liferay-ui:message key="create-default-pages-as-private-available-only-to-members-if-unchecked-they-will-be-public-available-to-anyone" />',
						checkboxFieldName: (data.creationType == '<%= SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE %>') ? 'layoutSetVisibilityPrivate' : '',
						checkboxFieldValue: false,
						dialogTitle: '<liferay-ui:message key="add-site" />',
						formSubmitURL: uri,
						idFieldName: 'layoutSetPrototypeId',
						idFieldValue: data.layoutSetPrototypeId,
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