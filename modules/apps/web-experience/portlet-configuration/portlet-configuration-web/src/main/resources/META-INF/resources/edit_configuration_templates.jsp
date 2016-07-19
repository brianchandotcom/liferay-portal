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
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_configuration_templates.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);

SearchContainer<ArchivedSettings> archivedSettingsSearch = new SearchContainer<ArchivedSettings>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-configuration-templates");

List<ArchivedSettings> archivedSettingsList = SettingsFactoryUtil.getPortletInstanceArchivedSettingsList(scopeGroupId, selPortlet.getRootPortletId());

boolean orderByAsc = false;

if (orderByType.equals("asc")) {
	orderByAsc = true;
}

OrderByComparator orderByComparator = null;

if (orderByCol.equals("modified-date")) {
	orderByComparator = new ArchivedSettingsNameComparator(orderByAsc);
}
else {
	orderByComparator = new ArchivedSettingsModifiedDateComparator(orderByAsc);
}

archivedSettingsList = ListUtil.sort(archivedSettingsList, orderByComparator);

int archivedSettingsCount = archivedSettingsList.size();

archivedSettingsSearch.setTotal(archivedSettingsCount);

archivedSettingsList = ListUtil.subList(archivedSettingsList, archivedSettingsSearch.getStart(), archivedSettingsSearch.getEnd());

archivedSettingsSearch.setResults(archivedSettingsList);
%>

<div class="portlet-configuration-edit-templates">
	<portlet:actionURL name="deleteArchivedSetups" var="deleteArchivedSetupsURL">
		<portlet:param name="mvcPath" value="/edit_configuration_templates.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
	</portlet:actionURL>

	<aui:form action="<%= deleteArchivedSetupsURL %>" name="fm">
		<div class="portlet-configuration-body-content">
			<aui:nav-bar markupView="lexicon">
				<aui:nav cssClass="navbar-nav">
					<aui:nav-item label="configuration-templates" selected="<%= true %>" />
				</aui:nav>
			</aui:nav-bar>

			<liferay-frontend:management-bar
				disabled="<%= archivedSettingsCount <= 0 %>"
				includeCheckBox="<%= true %>"
				searchContainerId="archivedSettings"
			>
				<liferay-frontend:management-bar-filters>
					<liferay-frontend:management-bar-navigation
						navigationKeys='<%= new String[] {"all"} %>'
						portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
					/>

					<liferay-frontend:management-bar-sort
						orderByCol="<%= orderByCol %>"
						orderByType="<%= orderByType %>"
						orderColumns='<%= new String[] {"name", "modified-date"} %>'
						portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
					/>
				</liferay-frontend:management-bar-filters>

				<liferay-frontend:management-bar-buttons>
					<liferay-frontend:management-bar-display-buttons
						displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
						portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
						selectedDisplayStyle="<%= displayStyle %>"
					/>
				</liferay-frontend:management-bar-buttons>

				<liferay-frontend:management-bar-action-buttons>
					<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteArchivedSettings" label="delete" />
				</liferay-frontend:management-bar-action-buttons>
			</liferay-frontend:management-bar>

			<div class="container-fluid-1280">
				<liferay-ui:error exception="<%= NoSuchPortletItemException.class %>" message="the-setup-could-not-be-found" />

				<div class="button-holder text-center">
					<portlet:renderURL var="addConfigurationTemplateURL">
						<portlet:param name="mvcPath" value="/add_configuration_template.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="portletResource" value="<%= portletResource %>" />
					</portlet:renderURL>

					<aui:button href="<%= addConfigurationTemplateURL %>" value="save-current-configuration-as-template" />
				</div>

				<liferay-ui:search-container
					id="archivedSettings"
					rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
					searchContainer="<%= archivedSettingsSearch %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.settings.ArchivedSettings"
						keyProperty="name"
						modelVar="archivedSettings"
					>
						<c:choose>
							<c:when test='<%= displayStyle.equals("descriptive") %>'>
								<liferay-ui:search-container-column-icon
									icon="archive"
								/>

								<liferay-ui:search-container-column-text
									colspan="<%= 2 %>"
								>
									<h6 class="text-default">
										<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - archivedSettings.getModifiedDate().getTime(), true), HtmlUtil.escape(archivedSettings.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
									</h6>

									<h5>
										<%= HtmlUtil.escape(archivedSettings.getName()) %>
									</h5>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-jsp
									path="/configuration_template_action.jsp"
								/>
							</c:when>
							<c:when test='<%= displayStyle.equals("icon") %>'>

								<%
								row.setCssClass("entry-card lfr-asset-item");
								%>

								<liferay-ui:search-container-column-text>
									<liferay-frontend:icon-vertical-card
										actionJsp="/configuration_template_action.jsp"
										actionJspServletContext="<%= application %>"
										icon="archive"
										resultRow="<%= row %>"
										title="<%= archivedSettings.getName() %>"
									>
										<liferay-frontend:vertical-card-header>
											<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - archivedSettings.getModifiedDate().getTime(), true), HtmlUtil.escape(archivedSettings.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
										</liferay-frontend:vertical-card-header>
									</liferay-frontend:icon-vertical-card>
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:when test='<%= displayStyle.equals("list") %>'>
								<liferay-ui:search-container-column-text
									name="name"
									truncate="<%= true %>"
								>
									<%= HtmlUtil.escape(archivedSettings.getName()) %>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text
									name="user-name"
									truncate="<%= true %>"
								>
									<%= HtmlUtil.escape(archivedSettings.getUserName()) %>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-date
									name="modified-date"
									property="modifiedDate"
								/>

								<liferay-ui:search-container-column-jsp
									path="/configuration_template_action.jsp"
								/>
							</c:when>
						</c:choose>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
				</liferay-ui:search-container>
			</div>
		</div>
	</aui:form>
</div>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteArchivedSettings').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>