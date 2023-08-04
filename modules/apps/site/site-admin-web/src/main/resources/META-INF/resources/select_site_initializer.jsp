<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SelectSiteInitializerDisplayContext selectSiteInitializerDisplayContext = new SelectSiteInitializerDisplayContext(request, renderRequest, renderResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(selectSiteInitializerDisplayContext.getBackURL());

renderResponse.setTitle(LanguageUtil.get(request, "select-template"));
%>

<clay:navigation-bar
	navigationItems="<%= selectSiteInitializerDisplayContext.getNavigationItems() %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= selectSiteInitializerDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.site.admin.web.internal.util.SiteInitializerItem"
			keyProperty="key"
			modelVar="siteInitializerItem"
		>
			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					propsTransformer="js/SelectSiteInitializerVerticalCardPropsTransformer"
					verticalCard="<%= new SelectSiteInitializerVerticalCard(siteInitializerItem, renderRequest, renderResponse) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<portlet:actionURL name="/site_admin/add_group" var="addSiteURL">
		<portlet:param name="mvcPath" value="/select_layout_set_prototype_entry.jsp" />
		<portlet:param name="parentGroupId" value="<%= String.valueOf(selectSiteInitializerDisplayContext.getParentGroupId()) %>" />
	</portlet:actionURL>
</aui:form>