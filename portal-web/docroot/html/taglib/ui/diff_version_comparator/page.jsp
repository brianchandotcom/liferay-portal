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

<%@ include file="/html/taglib/ui/diff_version_comparator/init.jsp" %>

<%
Set<Locale> availableLocales = (Set<Locale>)request.getAttribute("liferay-ui:diff-version-comparator:availableLocales");
String diffHtmlResults = (String)request.getAttribute("liferay-ui:diff-version-comparator:diffHtmlResults");
DiffVersionsInfo diffVersionsInfo = (DiffVersionsInfo)request.getAttribute("liferay-ui:diff-version-comparator:diffVersionsInfo");
boolean hideControls = (Boolean)request.getAttribute("liferay-ui:diff-version-comparator:hideControls");
String languageId = (String)request.getAttribute("liferay-ui:diff-version-comparator:languageId");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:diff-version-comparator:portletURL");
PortletURL resourceURL = (PortletURL)request.getAttribute("liferay-ui:diff-version-comparator:resourceURL");
double sourceVersion = (Double)request.getAttribute("liferay-ui:diff-version-comparator:sourceVersion");
double targetVersion = (Double)request.getAttribute("liferay-ui:diff-version-comparator:targetVersion");

double nextVersion = diffVersionsInfo.getNextVersion();
double previousVersion = diffVersionsInfo.getPreviousVersion();

PortletURL iteratorURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

if (Validator.isNotNull(languageId)) {
	iteratorURL.setParameter("languageId", languageId);

	resourceURL.setParameter("languageId", languageId);
}
%>

<div class="diff-version-comparator">
	<span class="diff-version-title">
		<liferay-ui:message key="you-are-comparing-these-versions" />:
	</span>

	<aui:row cssClass="diff-version-head">
		<aui:col cssClass="diff-source-selector" width="30">
			<div class="diff-selector">
				<liferay-ui:icon-menu cssClass="diff-selector-version" direction="down" extended="<%= false %>" icon="../aui/caret-bottom-right" id="sourceVersionSelector" message='<%= LanguageUtil.format(pageContext, "version-x", sourceVersion) %>' showArrow="<%= true %>" showWhenSingleIcon="<%= true %>" useIconCaret="<%= true %>">

					<%
					for (DiffVersion diffVersion : diffVersionsInfo.getDiffVersions()) {
					%>

						<liferay-ui:icon
							label="<%= true %>"
							message='<%= LanguageUtil.format(pageContext, "version-x", diffVersion.getVersion()) %>'
						/>

					<%
					}
					%>

				</liferay-ui:icon-menu>
			</div>

			<c:if test="<%= previousVersion == 0 %>">
				<div class="diff-selector-version-info">
					(<liferay-ui:message key="first-version" />)
				</div>
			</c:if>
		</aui:col>

		<aui:col cssClass="diff-target-selector" width="70">
			<div class="diff-selector">
				<liferay-ui:icon-menu cssClass="diff-selector-version" direction="down" extended="<%= false %>" icon="../aui/caret-bottom-right" id="targetVersionSelector" message='<%= LanguageUtil.format(pageContext, "version-x", targetVersion) %>' showArrow="<%= true %>" showWhenSingleIcon="<%= true %>" useIconCaret="<%= true %>">

					<%
					for (DiffVersion diffVersion : diffVersionsInfo.getDiffVersions()) {
					%>

						<liferay-ui:icon
							label="<%= true %>"
							message='<%= LanguageUtil.format(pageContext, "version-x", diffVersion.getVersion()) %>'
						/>

					<%
					}
					%>

				</liferay-ui:icon-menu>
			</div>

			<c:if test="<%= nextVersion == 0 %>">
				<div class="diff-selector-version-info">
					(<liferay-ui:message key="last-version" />)
				</div>
			</c:if>
		</aui:col>
	</aui:row>

	<aui:row>
		<aui:col cssClass="search-container-column" width="30">
			<div class="search-panels">
				<div class="search-panels-bar">
					<i class="search-panel-icon"></i>

					<aui:input cssClass="search-panels-input search-query span12" label="" name="searchPanel" type="text" />
				</div>
			</div>

			<c:if test="<%= (availableLocales != null) && !availableLocales.isEmpty() %>">

				<%
				portletURL.setParameter("sourceVersion", String.valueOf(sourceVersion));
				portletURL.setParameter("targetVersion", String.valueOf(targetVersion));
				%>

				<div class="language-selector">
					<aui:form action="<%= portletURL %>" method="post" name="fm">
						<aui:select label="" name="languageId" title="language">

							<%
							for (Locale availableLocale : availableLocales) {
							%>

								<aui:option label="<%= availableLocale.getDisplayName(locale) %>" selected="<%= languageId.equals(LocaleUtil.toLanguageId(availableLocale)) %>" value="<%= LocaleUtil.toLanguageId(availableLocale) %>" />

							<%
							}
							%>

						</aui:select>
					</aui:form>
				</div>

				<aui:script use="aui-base">
					A.one('#<portlet:namespace />languageId').on(
						'change',
						function(event) {
							submitForm(document.<portlet:namespace />fm);
						}
					);
				</aui:script>
			</c:if>

			<div id="<portlet:namespace />versionItems">

				<%
				double previousSourceVersion = sourceVersion;

				List<DiffVersion> diffVersions = diffVersionsInfo.getDiffVersions();

				for (int i = 0; i < diffVersions.size(); i++) {
					DiffVersion diffVersion = diffVersions.get(i);

					User userDisplay = UserLocalServiceUtil.getUser(diffVersion.getUserId());

					String displayDate = LanguageUtil.format(pageContext, "x-ago", LanguageUtil.getTimeDescription(pageContext, System.currentTimeMillis() - diffVersion.getModifiedDate().getTime(), true), false);
				%>

					<div class='version-item <%= (i == (diffVersions.size() - 1)) ? "last" : StringPool.BLANK %>' data-display-date="<%= displayDate %>" data-source-version="<%= previousSourceVersion %>" data-user-name="<%= HtmlUtil.escape(userDisplay.getFullName()) %>" data-version="<%= diffVersion.getVersion() %>">
						<span class="version-title">
							<liferay-ui:message arguments="<%= diffVersion.getVersion() %>" key="version-x" />
						</span>

						<div class="version-avatar">
							<img alt="<%= HtmlUtil.escapeAttribute(userDisplay.getFullName()) %>" class="avatar img-circle" src="<%= HtmlUtil.escape(userDisplay.getPortraitURL(themeDisplay)) %>" />
						</div>

						<div class="version-info">
							<span class="user-info"><%= HtmlUtil.escapeAttribute(userDisplay.getFullName()) %></span>
							<span class="date-info"><%= displayDate %></span>
						</div>
					</div>

				<%
					previousSourceVersion = diffVersion.getVersion();
				}
				%>

			</div>
		</aui:col>
		<aui:col cssClass="diff-container-column" width="70">
			<div class="diff-version-filter hide" id="<portlet:namespace />versionFilter">
			</div>

			<div class="diff-container">
				<div id="<portlet:namespace />diffContainerHtmlResults">
					<liferay-ui:diff-html diffHtmlResults="<%= diffHtmlResults %>" />
				</div>

				<div class="legend-info">
					<liferay-ui:icon
						cssClass="legend-item"
						iconCssClass="delete icon-stop"
						label="<%= true %>"
						message="deleted"
					/>

					<liferay-ui:icon
						cssClass="legend-item"
						iconCssClass="add icon-stop"
						label="<%= true %>"
						message="added"
					/>
				</div>
			</div>
		</aui:col>
	</aui:row>
</div>

<aui:script use="liferay-diff-version-comparator">
	new Liferay.DiffVersionComparator(
		{
			diffContainerHtmlResultsSelector: 'diffContainerHtmlResults',
			initialSourceVersion: '<%= sourceVersion %>',
			initialTargetVersion: '<%= targetVersion %>',
			namespace: '<portlet:namespace />',
			resourceURL: '<%= resourceURL.toString() %>',
			searchBoxSelector: 'searchPanel',
			versionFilterSelector: 'versionFilter',
			versionItemsSelector: 'versionItems'
		}
	);
</aui:script>