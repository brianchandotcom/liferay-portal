<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
page import="com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil" %><%@
page import="com.liferay.portal.kernel.model.CompanyConstants" %><%@
page import="com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerClassNameComparator" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
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
	SearchAdminDisplayContext searchAdminDisplayContext = (SearchAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
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
					module="js/execution_options/index"
					props="<%= indexActionsDisplayContext.getData() %>"
				/>
			</clay:col>

			<clay:col
				size="8"
			>
				<c:choose>
					<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-183661") %>'>
						<clay:sheet>
							<h2 class="sheet-title">
								<liferay-ui:message key="reindex-actions" />
							</h2>

							<ul class="list-group">
								<li class="list-group-header">
									<h3 class="list-group-header-title">
										<liferay-ui:message key="global" />
									</h3>
								</li>
								<li class="list-group-item list-group-item-flex">
									<div class="autofit-col autofit-col-expand">
										<p class="list-group-title">
											<liferay-ui:message key="all-search-indexes" />
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

												<aui:button cssClass="save-server-button" data-blocking='<%= ParamUtil.getBoolean(request, "blocking") %>' data-cmd="reindex" data-timeout="<%= (timeout == 0) ? StringPool.BLANK : timeout %>" disabled="<%= !reindexSingleBackgroundTasks.isEmpty() %>" value="execute" />
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
											<liferay-ui:message key="all-spell-check-dictionaries" />
										</p>
									</div>

									<div class="autofit-col index-action-wrapper" data-type="spellCheck">
										<aui:button cssClass="save-server-button" data-cmd="reindexDictionaries" data-concurrent-disabled="<%= true %>" disabled="<%= !reindexPortalBackgroundTasks.isEmpty() %>" value="execute" />
									</div>
								</li>

								<%
								Map<String, List<Indexer<?>>> indexerMap = searchAdminDisplayContext.getIndexerMap();

								for (Map.Entry<String, List<Indexer<?>>> mapEntry : indexerMap.entrySet()) {
									List<Indexer<?>> indexers = mapEntry.getValue();
								%>

									<li class="list-group-header">
										<h3 class="list-group-header-title">
											<liferay-ui:message key='<%= "model.resource." + mapEntry.getKey() %>' />
										</h3>
									</li>

									<%
									for (Indexer<?> indexer : indexers) {
										backgroundTaskDisplay = classNameToBackgroundTaskDisplayMap.get(indexer.getClassName());
									%>

										<li class="list-group-item list-group-item-flex">
											<div class="autofit-col autofit-col-expand">
												<p class="list-group-title" style="word-break: break-word;">
													<liferay-ui:message key='<%= "model.resource." + indexer.getClassName() %>' /> (<%= indexer.getClassName() %>)
												</p>
											</div>

											<div class="autofit-col index-action-wrapper" data-type="<%= indexer.getClassName() %>">
												<c:choose>
													<c:when test="<%= (backgroundTaskDisplay == null) || !backgroundTaskDisplay.hasPercentage() %>">
														<aui:button cssClass="save-server-button" data-classname="<%= indexer.getClassName() %>" data-cmd="reindex" data-concurrent-disabled="<%= true %>" disabled="<%= !indexer.isIndexerEnabled() || !reindexPortalBackgroundTasks.isEmpty() %>" value="execute" />
													</c:when>
													<c:otherwise>
														<%= backgroundTaskDisplay.renderDisplayTemplate() %>
													</c:otherwise>
												</c:choose>
											</div>
										</li>

								<%
									}
								}
								%>

								<li class="list-group-header">
									<h3 class="list-group-header-title">
										<liferay-ui:message key="search-tuning" />
									</h3>
								</li>

								<%
								List<String> indexReindexerClassNames = searchAdminDisplayContext.getIndexReindexerClassNames();

								for (String indexReindexerClassName : indexReindexerClassNames) {
								%>

									<li class="list-group-item list-group-item-flex">
										<div class="autofit-col autofit-col-expand">
											<p class="list-group-title" style="word-break: break-word;">
												<liferay-ui:message key='<%= "model.resource." + indexReindexerClassName %>' /> (<%= indexReindexerClassName %>)
											</p>
										</div>

										<div class="autofit-col index-action-wrapper" data-type="<%= indexReindexerClassName %>">
											<aui:button cssClass="save-server-button" data-classname="<%= indexReindexerClassName %>" data-cmd="reindexIndexReindexer" data-concurrent-disabled="<%= true %>" disabled="<%= !reindexPortalBackgroundTasks.isEmpty() %>" value="execute" />
										</div>
									</li>

								<%
								}
								%>

							</ul>
						</clay:sheet>
					</c:when>
					<c:otherwise>
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
										<aui:button cssClass="save-server-button" data-cmd="reindexDictionaries" data-concurrent-disabled="" value="execute" />
									</div>
								</li>

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
													<aui:button cssClass="save-server-button" data-classname="<%= indexer.getClassName() %>" data-cmd="reindex" data-concurrent-disabled="" disabled="<%= !indexer.isIndexerEnabled() %>" value="execute" />
												</c:when>
												<c:otherwise>
													<%= backgroundTaskDisplay.renderDisplayTemplate() %>
												</c:otherwise>
											</c:choose>
										</div>
									</li>

								<%
								}

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
											<aui:button cssClass="save-server-button" data-classname="<%= indexReindexerClassName %>" data-cmd="reindexIndexReindexer" data-concurrent-disabled="" value="execute" />
										</div>
									</li>

								<%
								}
								%>

							</ul>
						</clay:sheet>
					</c:otherwise>
				</c:choose>
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