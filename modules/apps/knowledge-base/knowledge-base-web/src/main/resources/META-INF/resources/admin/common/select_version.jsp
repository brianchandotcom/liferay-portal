<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);
int status = (Integer)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_STATUS);

int selStatus = KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.UPDATE) ? WorkflowConstants.STATUS_ANY : status;

int sourceVersion = ParamUtil.getInteger(request, "sourceVersion");

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/admin/common/select_version.jsp"
).setRedirect(
	currentURL
).setParameter(
	"resourcePrimKey", kbArticle.getResourcePrimKey()
).setParameter(
	"sourceVersion", sourceVersion
).buildPortletURL();
%>

<clay:container-fluid>
	<aui:form action="<%= portletURL %>" method="post" name="selectVersionFm">
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
</clay:container-fluid>