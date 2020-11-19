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
KBArticle kbArticle = (KBArticle)request.getAttribute("info_panel.jsp-kbArticle");

int selStatus = KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.UPDATE) ? WorkflowConstants.STATUS_ANY : WorkflowConstants.STATUS_APPROVED;

String orderByCol = ParamUtil.getString(request, "orderByCol", "version");
String orderByType = ParamUtil.getString(request, "orderByType", "desc");

OrderByComparator<KBArticle> orderByComparator = KBUtil.getKBArticleOrderByComparator(orderByCol, orderByType);

List<KBArticle> kbArticles = KBArticleServiceUtil.getKBArticleVersions(scopeGroupId, kbArticle.getResourcePrimKey(), selStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
%>

<clay:row>
	<clay:col>
		<ul class="list-group sidebar-list-group">

			<%
			for (KBArticle curKBArticle : kbArticles) {
			%>

				<li class="list-group-item list-group-item-flex">
					<div class="autofit-col autofit-col-expand">
						<div class="h5">
							<liferay-ui:message arguments="<%= curKBArticle.getVersion() %>" key="version-x" />
						</div>

						<div class="h6 sidebar-caption">
							<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(curKBArticle.getUserName()), dateFormatDateTime.format(curKBArticle.getModifiedDate())} %>" key="by-x-on-x" />
						</div>
					</div>

					<div class="autofit-col">
						<liferay-ui:icon-menu
							direction="left-side"
							icon="<%= StringPool.BLANK %>"
							markupView="lexicon"
							message="actions"
							showWhenSingleIcon="<%= true %>"
						>
							<c:if test="<%= (kbArticle.getStatus() == WorkflowConstants.STATUS_APPROVED) && KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.UPDATE) %>">
								<liferay-portlet:actionURL name="updateKBArticle" varImpl="revertURL">
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.REVERT %>" />
									<portlet:param name="version" value="<%= String.valueOf(curKBArticle.getVersion()) %>" />
									<portlet:param name="workflowAction" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />
								</liferay-portlet:actionURL>

								<%
								revertURL.setParameter("section", AdminUtil.unescapeSections(curKBArticle.getSections()));
								%>

								<liferay-ui:icon
									icon="undo"
									label="<%= true %>"
									markupView="lexicon"
									message="revert"
									url="<%= revertURL.toString() %>"
								/>
							</c:if>

							<portlet:renderURL var="selectVersionURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="mvcPath" value="/admin/common/select_version.jsp" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
								<portlet:param name="sourceVersion" value="<%= String.valueOf(curKBArticle.getVersion()) %>" />
							</portlet:renderURL>

							<%
							String taglibOnClick = liferayPortletResponse.getNamespace() + "openCompareVersionsPopup('" + selectVersionURL.toString() + "');";
							%>

							<liferay-ui:icon
								cssClass="compare-to-link"
								label="<%= true %>"
								message="compare-to"
								onClick="<%= taglibOnClick %>"
								url="javascript:;"
							/>
						</liferay-ui:icon-menu>
					</div>
				</li>

			<%
			}
			%>

		</ul>
	</clay:col>
</clay:row>

<portlet:renderURL var="compareVersionURL">
	<portlet:param name="mvcPath" value="/admin/common/compare_versions.jsp" />
	<portlet:param name="<%= Constants.CMD %>" value="compareVersions" />
	<portlet:param name="backURL" value="<%= currentURL %>" />
	<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
</portlet:renderURL>

<script>
	function <portlet:namespace />openCompareVersionsPopup(selectVersionUrl) {
		Liferay.Util.openSelectionModal({
			onSelect: function (event) {
				var uri = '<%= HtmlUtil.escapeJS(compareVersionURL) %>';

				uri = Liferay.Util.addParams(
					'<portlet:namespace />sourceVersion=' + event.sourceversion,
					uri
				);
				uri = Liferay.Util.addParams(
					'<portlet:namespace />targetVersion=' + event.targetversion,
					uri
				);

				Liferay.Util.navigate(uri);
			},
			selectEventName: '<portlet:namespace />selectVersionFm',
			title: '<liferay-ui:message key="compare-versions" />',
			url: selectVersionUrl,
		});
	}
</script>