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

<%@ include file="/diff_version_comparator/init.jsp" %>

<%
Set<Locale> availableLocales = (Set<Locale>)request.getAttribute("liferay-frontend:diff-version-comparator:availableLocales");
String diffHtmlResults = (String)request.getAttribute("liferay-frontend:diff-version-comparator:diffHtmlResults");
DiffVersionsInfo diffVersionsInfo = (DiffVersionsInfo)request.getAttribute("liferay-frontend:diff-version-comparator:diffVersionsInfo");
String languageId = (String)request.getAttribute("liferay-frontend:diff-version-comparator:languageId");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-frontend:diff-version-comparator:portletURL");
PortletURL resourceURL = (PortletURL)request.getAttribute("liferay-frontend:diff-version-comparator:resourceURL");
double sourceVersion = (Double)request.getAttribute("liferay-frontend:diff-version-comparator:sourceVersion");
double targetVersion = (Double)request.getAttribute("liferay-frontend:diff-version-comparator:targetVersion");

double nextVersion = diffVersionsInfo.getNextVersion();
double previousVersion = diffVersionsInfo.getPreviousVersion();

if (Validator.isNotNull(languageId)) {
	portletURL.setParameter("languageId", languageId);

	resourceURL.setParameter("languageId", languageId);
}
%>

<aui:form action="<%= portletURL %>" cssClass="container-fluid-1280 diff-version-comparator" method="post" name="diffVersionFm">
	<aui:input name="sourceVersion" type="hidden" value="<%= sourceVersion %>" />
	<aui:input name="targetVersion" type="hidden" value="<%= targetVersion %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<span class="h4 text-default">
				<liferay-ui:message key="you-are-comparing-these-versions" />
			</span>

			<aui:row>
				<aui:col width="<%= 30 %>">
					<div class="pull-right">
						<liferay-ui:icon-menu direction="down" extended="<%= false %>" message='<%= LanguageUtil.format(request, "version-x", sourceVersion) %>' showArrow="<%= true %>" showWhenSingleIcon="<%= true %>" useIconCaret="<%= true %>">

							<%
							PortletURL sourceURL = PortletURLUtil.clone(portletURL, renderResponse);

							sourceURL.setParameter("targetVersion", String.valueOf(targetVersion));

							for (DiffVersion diffVersion : diffVersionsInfo.getDiffVersions()) {
								sourceURL.setParameter("sourceVersion", String.valueOf(diffVersion.getVersion()));
							%>

								<c:if test="<%= sourceVersion != diffVersion.getVersion() %>">
									<liferay-ui:icon
										label="<%= true %>"
										message='<%= LanguageUtil.format(request, "version-x", diffVersion.getVersion()) %>'
										url="<%= sourceURL.toString() %>"
									/>
								</c:if>

							<%
							}
							%>

						</liferay-ui:icon-menu>

						<c:if test="<%= previousVersion == 0 %>">
							<h6 class="text-default">
								(<liferay-ui:message key="first-version" />)
							</h6>
						</c:if>
					</div>
				</aui:col>

				<aui:col cssClass="diff-target-selector" width="<%= 70 %>">
					<liferay-ui:icon-menu direction="down" extended="<%= false %>" message='<%= LanguageUtil.format(request, "version-x", targetVersion) %>' showArrow="<%= true %>" showWhenSingleIcon="<%= true %>" useIconCaret="<%= true %>">

						<%
						PortletURL targetURL = PortletURLUtil.clone(portletURL, renderResponse);

						targetURL.setParameter("sourceVersion", String.valueOf(sourceVersion));

						for (DiffVersion diffVersion : diffVersionsInfo.getDiffVersions()) {
							targetURL.setParameter("targetVersion", String.valueOf(diffVersion.getVersion()));
						%>

							<c:if test="<%= targetVersion != diffVersion.getVersion() %>">
								<liferay-ui:icon
									label="<%= true %>"
									message='<%= LanguageUtil.format(request, "version-x", diffVersion.getVersion()) %>'
									url="<%= targetURL.toString() %>"
								/>
							</c:if>

						<%
						}
						%>

					</liferay-ui:icon-menu>

					<c:if test="<%= nextVersion == 0 %>">
						<h6 class="text-default">
							(<liferay-ui:message key="last-version" />)
						</h6>
					</c:if>
				</aui:col>
			</aui:row>

			<div class="divider row"></div>

			<aui:row>
				<aui:col width="<%= 30 %>">

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
						<div class="input-group input-group-lg input-group-search">
							<input class="form-control" id="<portlet:namespace />searchPanel" name="<portlet:namespace />searchPanel" type="text" />

							<span class="input-group-addon">
								<aui:icon image="search" markupView="lexicon" />
							</span>
						</div>
					</c:if>

					<c:if test="<%= (availableLocales != null) && (availableLocales.size() > 1) %>">
						<aui:select label="" name="languageId" title="language">

							<%
							for (Locale availableLocale : availableLocales) {
							%>

								<aui:option label="<%= availableLocale.getDisplayName(locale) %>" selected="<%= languageId.equals(LocaleUtil.toLanguageId(availableLocale)) %>" value="<%= LocaleUtil.toLanguageId(availableLocale) %>" />

							<%
							}
							%>

						</aui:select>
					</c:if>

					<div id="<portlet:namespace />versionItems">
						<ul class="tabular-list-group">

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

								<li class="list-group-item version-item" data-display-date="<%= displayDate %>" data-source-version="<%= previousSourceVersion %>" data-user-name="<%= HtmlUtil.escape(userDisplay.getFullName()) %>" data-version="<%= diffVersion.getVersion() %>" data-version-name="<%= LanguageUtil.format(request, "version-x", diffVersion.getVersion()) %>" href="javascript:;">
									<div class="list-group-item-field">
										<liferay-ui:user-portrait
											cssClass="user-icon-lg"
											userId="<%= userDisplay.getUserId() %>"
										/>
									</div>

									<div class="list-group-item-content">
										<h6 class="text-default">
											<%= displayDate %>
										</h6>

										<h5 class="version-title">
											<liferay-ui:message arguments="<%= diffVersion.getVersion() %>" key="version-x" />
										</h5>

										<h6 class="text-default">
											<%= HtmlUtil.escape(userDisplay.getFullName()) %>
										</h6>
									</div>
								</li>

							<%
								previousSourceVersion = diffVersion.getVersion();
							}
							%>

						</ul>

						<div class="alert alert-info hide message-info">
							<liferay-ui:message key="there-are-no-results" />
						</div>
					</div>
				</aui:col>

				<aui:col width="<%= 70 %>">
					<div class="card diff-container-column">
						<div class="card-row-padded diff-version-filter hide" id="<portlet:namespace />versionFilter">
						</div>

						<div class="card-row-padded diff-container">
							<liferay-ui:diff-html
								diffHtmlResults="<%= diffHtmlResults %>"
							/>
						</div>

						<div class="card-row-padded taglib-diff-html">
							<span class="diff-html-added legend-item">
								<liferay-ui:message key="added" />
							</span>
							<span class="diff-html-removed legend-item">
								<liferay-ui:message key="deleted" />
							</span>
							<span class="diff-html-changed">
								<liferay-ui:message key="format-changes" />
							</span>
						</div>
					</div>
				</aui:col>
			</aui:row>
		</aui:fieldset>
	</aui:fieldset-group>
</aui:form>

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