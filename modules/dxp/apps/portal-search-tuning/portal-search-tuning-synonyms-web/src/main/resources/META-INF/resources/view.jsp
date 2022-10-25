<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.search.tuning.synonyms.web.internal.constants.SynonymsPortletKeys" %><%@
page import="com.liferay.portal.search.tuning.synonyms.web.internal.display.context.SynonymsDisplayContext" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
SynonymsDisplayContext synonymsDisplayContext = (SynonymsDisplayContext)request.getAttribute(SynonymsPortletKeys.SYNONYMS_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	actionDropdownItems="<%= synonymsDisplayContext.getActionDropdownMultipleItems() %>"
	creationMenu="<%= synonymsDisplayContext.getCreationMenu() %>"
	disabled="<%= synonymsDisplayContext.isDisabledManagementBar() %>"
	itemsTotal="<%= synonymsDisplayContext.getItemsTotal() %>"
	propsTransformer="js/SynonymsManagementToolbarPropsTransformer"
	searchContainerId="synonymSetsEntries"
	selectable="<%= true %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= false %>"
/>

<portlet:actionURL name="/synonyms/delete_synonym_sets" var="deleteSynonymSetActionURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteSynonymSetActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="deletedSynonymSetsString" type="hidden" value="" />

	<liferay-ui:search-container
		id="synonymSetsEntries"
		searchContainer="<%= synonymsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.search.tuning.synonyms.web.internal.display.context.SynonymSetDisplayContext"
			keyProperty="synonymSetId"
			modelVar="synonymSetDisplayContext"
		>
			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
				cssClass="table-cell-expand table-title"
			>
				<aui:a href="<%= synonymSetDisplayContext.getEditRenderURL() %>">
					<%= HtmlUtil.escape(synonymSetDisplayContext.getDisplayedSynonymSet()) %>
				</aui:a>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text>
				<clay:dropdown-actions
					aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
					dropdownItems="<%= synonymSetDisplayContext.getDropdownItems() %>"
					propsTransformer="js/SynonymSetsDropdownDefaultPropsTransformer"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>