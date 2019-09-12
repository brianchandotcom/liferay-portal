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

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);
int status = (Integer)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_STATUS);

int selStatus = KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.UPDATE) ? WorkflowConstants.STATUS_ANY : status;

int sourceVersion = ParamUtil.getInteger(request, "sourceVersion");
String eventName = ParamUtil.getString(request, "eventName", renderResponse.getNamespace() + "selectVersionFm");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/admin/common/select_version.jsp");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("resourcePrimKey", String.valueOf(kbArticle.getResourcePrimKey()));
portletURL.setParameter("sourceVersion", String.valueOf(sourceVersion));
%>

<div class="container-fluid-1280">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="selectVersionFm">
		<liferay-ui:search-container
			id="articleVersionSearchContainer"
			iteratorURL="<%= portletURL %>"
			total="<%= KBArticleServiceUtil.getKBArticleVersionsCount(scopeGroupId, kbArticle.getResourcePrimKey(), selStatus) %>"
		>
			<liferay-ui:search-container-results
				results="<%= KBArticleServiceUtil.getKBArticleVersions(scopeGroupId, kbArticle.getResourcePrimKey(), selStatus, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.knowledge.base.model.KBArticle"
				modelVar="curKBArticle"
			>
				<liferay-ui:search-container-column-text
					name="version"
				>
					<c:choose>
						<c:when test="<%= sourceVersion != curKBArticle.getVersion() %>">

							<%
							int curSourceVersion = sourceVersion;
							int curTargetVersion = curKBArticle.getVersion();

							if (curTargetVersion < curSourceVersion) {
								int tempVersion = curTargetVersion;

								curTargetVersion = curSourceVersion;
								curSourceVersion = tempVersion;
							}

							Map<String, Object> data = new HashMap<String, Object>();

							data.put("sourceversion", curSourceVersion);
							data.put("targetversion", curTargetVersion);
							%>

							<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
								<%= String.valueOf(curKBArticle.getVersion()) %>
							</aui:a>
						</c:when>
						<c:otherwise>
							<%= curKBArticle.getVersion() %>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-date
					name="date"
					value="<%= curKBArticle.getModifiedDate() %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectVersionFm',
		'<%= HtmlUtil.escapeJS(eventName) %>'
	);
</aui:script>