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
String languageId = (String)request.getAttribute("liferay-ui:diff-version-comparator:languageId");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:diff-version-comparator:portletURL");
PortletURL resourceURL = (PortletURL)request.getAttribute("liferay-ui:diff-version-comparator:resourceURL");
double sourceVersion = (Double)request.getAttribute("liferay-ui:diff-version-comparator:sourceVersion");
double targetVersion = (Double)request.getAttribute("liferay-ui:diff-version-comparator:targetVersion");

double nextVersion = diffVersionsInfo.getNextVersion();
double previousVersion = diffVersionsInfo.getPreviousVersion();

if (Validator.isNotNull(languageId)) {
	resourceURL.setParameter("languageId", languageId);
}
%>

<div class="diff-version-comparator">
	<span class="diff-version-title">
		<liferay-ui:message key="you-are-comparing-these-versions" />
	</span>

	<aui:form action="<%= portletURL %>" method="post" name="diffVersionFm">
		<aui:input name="sourceVersion" type="hidden" value="<%= sourceVersion %>" />
		<aui:input name="targetVersion" type="hidden" value="<%= targetVersion %>" />

		<aui:row cssClass="diff-version-head">
			<aui:col cssClass="diff-source-selector" width="<%= 30 %>">
				<div class="diff-selector">
					<liferay-ui:icon-menu cssClass="diff-selector-version pull-right" direction="down" extended="<%= false %>" icon="../aui/caret-bottom-right" message='<%= LanguageUtil.format(request, "version-x", sourceVersion) %>' showArrow="<%= true %>" showWhenSingleIcon="<%= true %>" useIconCaret="<%= true %>">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						for (DiffVersion diffVersion : diffVersionsInfo.getDiffVersions()) {
							data.put("version", diffVersion.getVersion());
						%>

							<liferay-ui:icon
								data="<%= data %>"
								label="<%= true %>"
								linkCssClass="source-version"
								message='<%= LanguageUtil.format(request, "version-x", diffVersion.getVersion()) %>'
								url="javascript:;"
							/>

						<%
						}
						%>

					</liferay-ui:icon-menu>
				</div>

				<c:if test="<%= previousVersion == 0 %>">
					<div class="diff-selector-version-info pull-right">
						(<liferay-ui:message key="first-version" />)
					</div>
				</c:if>
			</aui:col>

			<aui:col cssClass="diff-target-selector" width="<%= 70 %>">
				<div class="diff-selector">
					<liferay-ui:icon-menu cssClass="diff-selector-version" direction="down" extended="<%= false %>" icon="../aui/caret-bottom-right" message='<%= LanguageUtil.format(request, "version-x", targetVersion) %>' showArrow="<%= true %>" showWhenSingleIcon="<%= true %>" useIconCaret="<%= true %>">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						for (DiffVersion diffVersion : diffVersionsInfo.getDiffVersions()) {
							data.put("version", diffVersion.getVersion());
						%>

							<liferay-ui:icon
								data="<%= data %>"
								label="<%= true %>"
								linkCssClass="target-version"
								message='<%= LanguageUtil.format(request, "version-x", diffVersion.getVersion()) %>'
								url="javascript:;"
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
			<aui:col cssClass="search-container-column" width="<%= 30 %>">

				<%
				List<DiffVersion> diffVersions = diffVersionsInfo.getDiffVersions();

				int diffVersionsCount = 0;

				for (int i = 0; i < diffVersions.size(); i++) {
					DiffVersion diffVersion = diffVersions.get(i);

					if ((diffVersion.getVersion() <= sourceVersion) || (diffVersion.getVersion() > targetVersion)) {
						continue;
					}

					diffVersionsCount++;
				}
				%>

				<c:if test="<%= diffVersionsCount >= 5 %>">
					<div class="search-panels">
						<div class="search-panels-bar">
							<aui:input cssClass="col-md-12 search-panels-input search-query" label="" name="searchPanel" type="text" />

							<i class="search-panel-icon"></i>
						</div>
					</div>
				</c:if>

				<c:if test="<%= (availableLocales != null) && (availableLocales.size() > 1) %>">
					<div class="language-selector">
						<aui:select label="" name="languageId" title="language">

							<%
							for (Locale availableLocale : availableLocales) {
							%>

								<aui:option label="<%= availableLocale.getDisplayName(locale) %>" selected="<%= languageId.equals(LocaleUtil.toLanguageId(availableLocale)) %>" value="<%= LocaleUtil.toLanguageId(availableLocale) %>" />

							<%
							}
							%>

						</aui:select>
					</div>
				</c:if>

				<div id="<portlet:namespace />versionItems">

					<%
					double previousSourceVersion = sourceVersion;

					for (int i = 0; i < diffVersions.size(); i++) {
						DiffVersion diffVersion = diffVersions.get(i);

						if ((diffVersion.getVersion() <= sourceVersion) || (diffVersion.getVersion() > targetVersion)) {
							continue;
						}

						User userDisplay = UserLocalServiceUtil.getUser(diffVersion.getUserId());

						String displayDate = LanguageUtil.format(request, "x-ago", LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - diffVersion.getModifiedDate().getTime(), true), false);
					%>

						<div class="<%= (diffVersion.getVersion() >= targetVersion) ? "last" : StringPool.BLANK %> version-item" data-display-date="<%= displayDate %>" data-source-version="<%= previousSourceVersion %>" data-user-name="<%= HtmlUtil.escape(userDisplay.getFullName()) %>" data-version="<%= diffVersion.getVersion() %>" data-version-name="<%= LanguageUtil.format(request, "version-x", diffVersion.getVersion()) %>">
							<span class="version-title">
								<liferay-ui:message arguments="<%= diffVersion.getVersion() %>" key="version-x" />
							</span>

							<div class="pull-left version-avatar">
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

					<div class="alert alert-info hide message-info">
						<liferay-ui:message key="there-are-no-results" />
					</div>
				</div>
			</aui:col>
			<aui:col cssClass="diff-container-column" width="<%= 70 %>">
				<div class="diff-version-filter hide" id="<portlet:namespace />versionFilter">
				</div>

				<div class="diff-container">

					<%
					request.setAttribute(WebKeys.DIFF_HTML_RESULTS, diffHtmlResults);
					%>

					<liferay-util:include page="/html/taglib/ui/diff_version_comparator/diff_html.jsp" />
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

					<span class="diff-html-changed legend-item">
						<liferay-ui:message key="format-changes" />
					</span>
				</div>
			</aui:col>
		</aui:row>
	</aui:form>
</div>

<aui:script use="liferay-diff-version-comparator">
	new Liferay.DiffVersionComparator(
		{
			diffContainerHtmlResults: '#<portlet:namespace />diffContainerHtmlResults',
			diffForm: '#<portlet:namespace />diffVersionFm',
			initialSourceVersion: '<%= sourceVersion %>',
			initialTargetVersion: '<%= targetVersion %>',
			namespace: '<portlet:namespace />',
			resourceURL: '<%= resourceURL.toString() %>',
			searchBox: '#<portlet:namespace />searchPanel',
			versionFilter: '#<portlet:namespace />versionFilter',
			versionItems: '#<portlet:namespace />versionItems'
		}
	);
</aui:script>