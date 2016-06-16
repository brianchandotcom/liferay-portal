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
WikiPageInfoPanelDisplayContext wikiPageInfoPanelDisplayContext = wikiDisplayContextProvider.getWikiPageInfoPanelDisplayContext(request, response);

WikiPage wikiPage = wikiPageInfoPanelDisplayContext.getFirstPage();

List<WikiPage> incomingLinkPages = WikiPageLocalServiceUtil.getIncomingLinks(wikiPage.getNodeId(), wikiPage.getTitle());
List<WikiPage> outgoingLinkPages = WikiPageLocalServiceUtil.getOutgoingLinks(wikiPage.getNodeId(), wikiPage.getTitle());

boolean hasIncomingLinkPages = ListUtil.isNotEmpty(incomingLinkPages);
boolean hasOutgoingLinkPages = ListUtil.isNotEmpty(outgoingLinkPages);
%>

<div>
	<c:choose>
		<c:when test="<%= hasIncomingLinkPages || hasOutgoingLinkPages %>">
			<liferay-ui:panel-container extended="<%= true %>" id="wikiPageLinks" markupView="lexicon" persistState="<%= true %>">
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= hasIncomingLinkPages %>" markupView="lexicon" title="incoming-links">
					<c:choose>
						<c:when test="<%= hasIncomingLinkPages %>">
							<dl>

								<%
								for (WikiPage incomingLinkPage : incomingLinkPages) {
									WikiNode wikiNode = incomingLinkPage.getNode();

									PortletURL portletURL = liferayPortletResponse.createRenderURL();

									portletURL.setParameter("mvcRenderCommandName", "/wiki/view");
									portletURL.setParameter("redirect", currentURL);
									portletURL.setParameter("nodeName", wikiNode.getName());
									portletURL.setParameter("title", incomingLinkPage.getTitle());
								%>

									<dt class="h5">
										<h4>
											<a class="text-default" href="<%= portletURL.toString() %>"><%= incomingLinkPage.getTitle() %></a>
										</h4>
									</dt>

									<dd>
										<small>
											<aui:workflow-status markupView="lexicon" showLabel="<%= false %>" status="<%= incomingLinkPage.getStatus() %>" />
										</small>
									</dd>

								<%
								}
								%>

							</dl>
						</c:when>
						<c:otherwise>
							<div class="alert alert-info">
								<liferay-ui:message key="there-are-no-pages-that-link-to-this-page" />
							</div>
						</c:otherwise>
					</c:choose>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= true %>" extended="<%= hasOutgoingLinkPages %>" markupView="lexicon" title="outgoing-links">
					<c:choose>
						<c:when test="<%= hasOutgoingLinkPages %>">
							<dl>

								<%
								for (WikiPage outgoingLinkPage : outgoingLinkPages) {
								%>

									<c:choose>
										<c:when test="<%= outgoingLinkPage.isNew() %>">
											<dt class="h5">
												<h4 class="truncate-text">
													<a class="text-default" href="<%= outgoingLinkPage.getTitle() %>"><%= outgoingLinkPage.getTitle() %></a>
												</h4>
											</dt>
										</c:when>
										<c:otherwise>

											<%
											WikiNode wikiNode = outgoingLinkPage.getNode();

											PortletURL portletURL = liferayPortletResponse.createRenderURL();

											portletURL.setParameter("mvcRenderCommandName", "/wiki/view");
											portletURL.setParameter("redirect", currentURL);
											portletURL.setParameter("nodeName", wikiNode.getName());
											portletURL.setParameter("title", outgoingLinkPage.getTitle());
											%>

											<dt class="h5">
												<h4 class="truncate-text">
													<a class="text-default" href="<%= portletURL.toString() %>"><%= outgoingLinkPage.getTitle() %></a>
												</h4>
											</dt>

											<dd>
												<small>
													<aui:workflow-status markupView="lexicon" showLabel="<%= false %>" status="<%= outgoingLinkPage.getStatus() %>" />
												</small>
											</dd>
										</c:otherwise>
									</c:choose>

								<%
								}
								%>

							</dl>
						</c:when>
						<c:otherwise>
							<div class="alert alert-info">
								<liferay-ui:message key="this-page-has-no-links" />
							</div>
						</c:otherwise>
					</c:choose>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<liferay-ui:message key="this-page-has-no-links" />
			</div>
		</c:otherwise>
	</c:choose>
</div>