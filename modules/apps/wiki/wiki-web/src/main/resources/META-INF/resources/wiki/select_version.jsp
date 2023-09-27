<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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