<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library_display/init.jsp" %>

<c:choose>
	<c:when test="<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) || portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY) %>">

		<%
		String topLink = ParamUtil.getString(request, "topLink", "home");

		long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

		long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));
		%>

		<c:if test="<%= showTabs || showFoldersSearch %>">
			<aui:nav-bar>
				<c:if test="<%= showTabs %>">
					<aui:nav>

						<%
						PortletURL portletURL = renderResponse.createRenderURL();

						String label = "home";
						boolean selected = topLink.equals(label);

						portletURL.setParameter("topLink", label);
						portletURL.setParameter("categoryId", StringPool.BLANK);
						portletURL.setParameter("tag", StringPool.BLANK);
						%>

						<aui:nav-item className='<%= selected ? "active" : StringPool.BLANK %>' href="<%= portletURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

						<%
						label = "recent";
						selected = topLink.equals(label);

						portletURL.setParameter("topLink", label);
						%>

						<aui:nav-item className='<%= selected ? "active" : StringPool.BLANK %>' href="<%= portletURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

						<c:if test="<%= themeDisplay.isSignedIn() %>">

							<%
							label = "mine";
							selected = topLink.equals(label);

							portletURL.setParameter("topLink", label);
							%>

							<aui:nav-item className='<%= selected ? "active" : StringPool.BLANK %>' href="<%= portletURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />
						</c:if>
					</aui:nav>
				</c:if>

				<c:if test="<%= showFoldersSearch %>">
					<liferay-portlet:renderURL varImpl="searchURL">
						<portlet:param name="struts_action" value="/document_library_display/search" />
					</liferay-portlet:renderURL>

					<aui:nav-bar-search cssClass="pull-right">
						<div class="form-search">
							<aui:form action="<%= searchURL %>" method="get" name="searchFm">
								<liferay-portlet:renderURLParams varImpl="searchURL" />
								<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
								<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
								<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
								<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
								<aui:input name="searchFolderIds" type="hidden" value="<%= folderId %>" />

								<liferay-ui:input-search id="keywords1" />
							</aui:form>
						</div>
					</aui:nav-bar-search>

					<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
						<aui:script>
							Liferay.Util.focusFormField(document.getElementById('<portlet:namespace />keywords1'));
						</aui:script>
					</c:if>
				</c:if>
			</aui:nav-bar>
		</c:if>
	</c:when>
	<c:when test="<%= (showTabs || showFoldersSearch) && portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) %>">
		<liferay-ui:header
			title="home"
		/>
	</c:when>
</c:choose>