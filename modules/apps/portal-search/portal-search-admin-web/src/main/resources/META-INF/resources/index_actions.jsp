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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTask" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil" %><%@
page import="com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants" %><%@
page import="com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay" %><%@
page import="com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplayFactoryUtil" %><%@
page import="com.liferay.portal.kernel.model.CompanyConstants" %><%@
page import="com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerClassNameComparator" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.admin.web.internal.constants.SearchAdminWebKeys" %><%@
page import="com.liferay.portal.search.admin.web.internal.display.context.IndexActionsDisplayContext" %><%@
page import="com.liferay.portal.search.admin.web.internal.display.context.SearchAdminDisplayContext" %><%@
page import="com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys" %>

<%@ page import="java.io.Serializable" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Collections" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<portlet:defineObjects />

<portlet:renderURL var="redirectURL">
	<portlet:param name="mvcRenderCommandName" value="/portal_search_admin/view" />
	<portlet:param name="tabs1" value="index-actions" />
</portlet:renderURL>

<aui:form
	action='<%=
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCRenderCommandName(
			"/portal_search_admin/view"
		).buildString()
	%>'
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />

	<%
	Map<String, BackgroundTaskDisplay> classNameToBackgroundTaskDisplayMap = new HashMap<>();

	List<BackgroundTask> reindexPortalBackgroundTasks = BackgroundTaskManagerUtil.getBackgroundTasks(CompanyConstants.SYSTEM, "com.liferay.portal.search.internal.background.task.ReindexPortalBackgroundTaskExecutor", BackgroundTaskConstants.STATUS_IN_PROGRESS);

	List<BackgroundTask> reindexSingleBackgroundTasks = BackgroundTaskManagerUtil.getBackgroundTasks(CompanyConstants.SYSTEM, "com.liferay.portal.search.internal.background.task.ReindexSingleIndexerBackgroundTaskExecutor", BackgroundTaskConstants.STATUS_IN_PROGRESS);

	if (!reindexSingleBackgroundTasks.isEmpty()) {
		for (BackgroundTask backgroundTask : reindexSingleBackgroundTasks) {
			Map<String, Serializable> taskContextMap = backgroundTask.getTaskContextMap();

			String className = (String)taskContextMap.get("className");

			classNameToBackgroundTaskDisplayMap.put(className, BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask));
		}
	}

	IndexActionsDisplayContext indexActionsDisplayContext = (IndexActionsDisplayContext)request.getAttribute(SearchAdminWebKeys.INDEX_ACTIONS_DISPLAY_CONTEXT);
	%>

	<clay:container-fluid
		cssClass="container-fluid-max-xl container-form-lg search-admin-index-actions-container"
		id='<%= liferayPortletResponse.getNamespace() + "adminSearchAdminIndexActionsPanel" %>'
	>
		<clay:row>
			<clay:col
				size="4"
			>
				<react:component
					module="js/ExecutionScope.es"
					props="<%= indexActionsDisplayContext.getData() %>"
				/>
			</clay:col>

			<clay:col
				size="8"
			>
				<clay:sheet>
					<h2 class="sheet-title">
						<liferay-ui:message key="actions" />
					</h2>

					<ul class="list-group">
						<li class="list-group-item list-group-item-flex">
							<div class="autofit-col autofit-col-expand">
								<p class="list-group-title">
									<liferay-ui:message key="reindex-search-indexes" />
								</p>
							</div>

							<%
							BackgroundTask backgroundTask = null;
							BackgroundTaskDisplay backgroundTaskDisplay = null;

							if (!reindexPortalBackgroundTasks.isEmpty()) {
								backgroundTask = reindexPortalBackgroundTasks.get(0);

								backgroundTaskDisplay = BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask);
							}
							%>

							<div class="autofit-col index-action-wrapper" data-type="portal">
								<c:choose>
									<c:when test="<%= (backgroundTaskDisplay == null) || !backgroundTaskDisplay.hasPercentage() %>">

										<%
										long timeout = ParamUtil.getLong(request, "timeout");
										%>

										<aui:button cssClass="save-server-button" data-blocking='<%= ParamUtil.getBoolean(request, "blocking") %>' data-cmd="reindex" data-timeout="<%= (timeout == 0) ? StringPool.BLANK : timeout %>" value="execute" />
									</c:when>
									<c:otherwise>
										<%= backgroundTaskDisplay.renderDisplayTemplate() %>
									</c:otherwise>
								</c:choose>
							</div>
						</li>
						<li class="list-group-item list-group-item-flex">
							<div class="autofit-col autofit-col-expand">
								<p class="list-group-title">
									<liferay-ui:message key="reindex-spell-check-dictionaries" />
								</p>
							</div>

							<div class="autofit-col">
								<aui:button cssClass="save-server-button" data-cmd="reindexDictionaries" value="execute" />
							</div>
						</li>

						<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688")) %>'>
							<li class="list-group-item list-group-item-flex">
								<div class="autofit-col autofit-col-expand">
									<p class="list-group-title">
										<liferay-ui:message arguments="<%= indexActionsDisplayContext.getTextEmbeddingServiceStatus() %>" key="reindex-text-embeddings-x" />
									</p>
								</div>

								<div class="autofit-col">
									<aui:button cssClass="save-server-button" data-cmd="reindexTextEmbeddings" value="execute" />
								</div>
							</li>
						</c:if>

						<%
						List<Indexer<?>> indexers = new ArrayList<>(IndexerRegistryUtil.getIndexers());

						Collections.sort(indexers, new IndexerClassNameComparator(true));

						for (Indexer<?> indexer : indexers) {
							backgroundTaskDisplay = classNameToBackgroundTaskDisplayMap.get(indexer.getClassName());
						%>

							<li class="list-group-item list-group-item-flex">
								<div class="autofit-col autofit-col-expand">
									<p class="list-group-title">
										<liferay-ui:message arguments="<%= indexer.getClassName() %>" key="reindex-x" />
									</p>
								</div>

								<div class="autofit-col index-action-wrapper" data-type="<%= indexer.getClassName() %>">
									<c:choose>
										<c:when test="<%= (backgroundTaskDisplay == null) || !backgroundTaskDisplay.hasPercentage() %>">
											<aui:button cssClass="save-server-button" data-classname="<%= indexer.getClassName() %>" data-cmd="reindex" disabled="<%= !indexer.isIndexerEnabled() %>" value="execute" />
										</c:when>
										<c:otherwise>
											<%= backgroundTaskDisplay.renderDisplayTemplate() %>
										</c:otherwise>
									</c:choose>
								</div>
							</li>

						<%
						}

						SearchAdminDisplayContext searchAdminDisplayContext = (SearchAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

						List<String> indexReindexerClassNames = searchAdminDisplayContext.getIndexReindexerClassNames();

						for (String indexReindexerClassName : indexReindexerClassNames) {
						%>

							<li class="list-group-item list-group-item-flex">
								<div class="autofit-col autofit-col-expand">
									<p class="list-group-title">
										<liferay-ui:message arguments="<%= indexReindexerClassName %>" key="reindex-x" />
									</p>
								</div>

								<div class="autofit-col index-action-wrapper" data-type="<%= indexReindexerClassName %>">
									<aui:button cssClass="save-server-button" data-classname="<%= indexReindexerClassName %>" data-cmd="reindexIndexReindexer" value="execute" />
								</div>
							</li>

						<%
						}
						%>

					</ul>
				</clay:sheet>
			</clay:col>
		</clay:row>
	</clay:container-fluid>
</aui:form>

<aui:script use="liferay-admin">
	new Liferay.Portlet.Admin({
		controlMenuCategoryKey:
			'<%= ProductNavigationControlMenuCategoryKeys.TOOLS %>',
		form: document.<portlet:namespace />fm,
		indexActionWrapperSelector: '.index-action-wrapper',
		indexActionsPanel:
			'<%= '#' + liferayPortletResponse.getNamespace() + "adminSearchAdminIndexActionsPanel" %>',
		namespace: '<portlet:namespace />',
		redirectUrl: '<%= redirectURL %>',
		submitButton: '.save-server-button',
		url:
			'<portlet:actionURL name="/portal_search_admin/edit"><portlet:param name="redirect" value="<%= redirectURL %>" /></portlet:actionURL>',
	});
</aui:script>