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

<%@ include file="/wiki/init.jsp" %>

<%
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

double sourceVersion = ParamUtil.getDouble(request, "sourceVersion");

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/wiki/select_version"
).setRedirect(
	currentURL
).setParameter(
	"nodeId", wikiPage.getNodeId()
).setParameter(
	"sourceVersion", sourceVersion
).setParameter(
	"title", HtmlUtil.unescape(wikiPage.getTitle())
).buildPortletURL();
%>

<clay:container-fluid>
	<aui:form action="<%= portletURL %>" method="post" name="selectVersionFm">
		<liferay-ui:search-container
			id="wikiPageVersionSearchContainer"
			iteratorURL="<%= portletURL %>"
			total="<%= WikiPageLocalServiceUtil.getPagesCount(wikiPage.getNodeId(), wikiPage.getTitle()) %>"
		>
			<liferay-ui:search-container-results
				results="<%= WikiPageLocalServiceUtil.getPages(wikiPage.getNodeId(), wikiPage.getTitle(), searchContainer.getStart(), searchContainer.getEnd(), new PageVersionComparator()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.wiki.model.WikiPage"
				modelVar="curWikiPage"
			>
				<liferay-ui:search-container-column-text
					name="version"
				>
					<c:choose>
						<c:when test="<%= sourceVersion != curWikiPage.getVersion() %>">

							<%
							double curSourceVersion = sourceVersion;
							double curTargetVersion = curWikiPage.getVersion();

							if (curTargetVersion < curSourceVersion) {
								double tempVersion = curTargetVersion;

								curTargetVersion = curSourceVersion;
								curSourceVersion = tempVersion;
							}
							%>

							<aui:a
								cssClass="selector-button"
								data='<%=
									HashMapBuilder.<String, Object>put(
										"sourceversion", curSourceVersion
									).put(
										"targetversion", curTargetVersion
									).build()
								%>'
								href="javascript:void(0);"
							>
								<%= String.valueOf(curWikiPage.getVersion()) %>
							</aui:a>
						</c:when>
						<c:otherwise>
							<%= curWikiPage.getVersion() %>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-date
					name="date"
					value="<%= curWikiPage.getModifiedDate() %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>