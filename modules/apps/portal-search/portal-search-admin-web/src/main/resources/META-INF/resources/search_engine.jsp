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

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.search.admin.web.internal.constants.SearchAdminWebKeys" %><%@
page import="com.liferay.portal.search.admin.web.internal.display.context.SearchEngineDisplayContext" %><%@
page import="com.liferay.portal.search.engine.ConnectionInformation" %><%@
page import="com.liferay.portal.search.engine.NodeInformation" %>

<%@ page import="java.util.List" %>

<%
SearchEngineDisplayContext
	searchEngineDisplayContext = (SearchEngineDisplayContext)request.getAttribute(SearchAdminWebKeys.SEARCH_ENGINE_DISPLAY_CONTEXT);
%>

<div class="container-fluid container-fluid-max-xl container-form-lg search-engine-page-container">
	<c:choose>
		<c:when test="<%= searchEngineDisplayContext.isMissingSearchEngine() %>">
			<div class="alert alert-warning">
				<liferay-ui:message key="no-search-engine-detected-help" />
			</div>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="<%= searchEngineDisplayContext.getConnectionInformationList() == null %>">
					<div class="alert alert-info">
						<liferay-ui:message key="search-engine-vendor" />: <strong><%= searchEngineDisplayContext.getVendorString() %></strong>,
						<liferay-ui:message key="client-version" />: <strong><%= searchEngineDisplayContext.getClientVersionString() %></strong>,
						<liferay-ui:message key="nodes" />: <strong><%= searchEngineDisplayContext.getNodesString() %></strong>
					</div>
				</c:when>
				<c:otherwise>
					<div class="connection-info-item connection-info-item-header sheet sheet-lg">
						<div class="connection-info-data-container">
							<div class="data-item">
								<div class="key"><liferay-ui:message key="search-engine-vendor" /></div>
								<div class="value"><%= searchEngineDisplayContext.getVendorString() %></div>
							</div>

							<div class="data-item">
								<div class="key"><liferay-ui:message key="client-version" /></div>
								<div class="value"><%= searchEngineDisplayContext.getClientVersionString() %></div>
							</div>
						</div>
					</div>

					<%
					List<ConnectionInformation> connectionInformationList = searchEngineDisplayContext.getConnectionInformationList();
					%>

					<h3 class="search-engine-page-title">
						<liferay-ui:message key="active-connections" />

						<span class="badge badge-secondary">
							<span class="badge-item badge-item-expand">
								<%= (connectionInformationList == null) ? 0 : connectionInformationList.size() %>
							</span>
						</span>
					</h3>

					<c:choose>
						<c:when test="<%= (connectionInformationList != null) && (connectionInformationList.size() > 0) %>">

							<%
							for (ConnectionInformation connectionInformation : connectionInformationList) {
							%>

								<div class="connection-info-item sheet sheet-lg">
									<div class="connection-info-item-header">
										<div class="connection-info-item-header-block">
											<h4 class="connection-id">
												<%= connectionInformation.getConnectionId() %>

												<%
												for (String label : connectionInformation.getLabels()) {
												%>

													<span class="label label-secondary">
														<span class="label-item label-item-expand">
															<liferay-ui:message key="<%= label %>" />
														</span>
													</span>

												<%
												}
												%>

											</h4>

											<c:if test="<%= Validator.isNotNull(connectionInformation.getClusterName()) %>">
												<span class="connection-cluster-name text-secondary"><%= connectionInformation.getClusterName() %></span>
											</c:if>
										</div>

										<c:if test="<%= Validator.isNotNull(connectionInformation.getHealth()) %>">
											<div class="connection-info-item-header-block">
												<div class="connection-health-indicator <%= StringUtil.lowerCase(connectionInformation.getHealth()) %>">
													<div class="indicator-item">
														<clay:icon
															symbol="simple-circle"
														/>
													</div>

													<div class="connection-health-indicator-text indicator-item">
														<liferay-ui:message arguments="<%= new String[] {connectionInformation.getHealth()} %>" key="health-x" />
													</div>
												</div>
											</div>
										</c:if>
									</div>

									<%
									List<NodeInformation> nodeInformationList = connectionInformation.getNodeInformationList();
									%>

									<liferay-frontend:fieldset
										collapsible="<%= nodeInformationList.size() > 0 %>"
										cssClass="connection-info-node-list"
										label='<%= LanguageUtil.format(request, "nodes-x", nodeInformationList.size(), false) %>'
									>
										<liferay-ui:search-container
											deltaConfigurable="<%= false %>"
											headerNames="name,version"
											total="<%= nodeInformationList.size() %>"
										>
											<liferay-ui:search-container-results
												results="<%= nodeInformationList %>"
											/>

											<liferay-ui:search-container-row
												className="com.liferay.portal.search.engine.NodeInformation"
												escapedModel="<%= true %>"
												keyProperty="name"
												modelVar="nodeInformation"
											>
												<liferay-ui:search-container-column-text
													property="name"
												/>

												<liferay-ui:search-container-column-text
													property="version"
												/>
											</liferay-ui:search-container-row>

											<liferay-ui:search-iterator
												markupView="lexicon"
												paginate="<%= false %>"
											/>
										</liferay-ui:search-container>
									</liferay-frontend:fieldset>

									<%
									String errorMessage = connectionInformation.getError();
									%>

									<c:if test="<%= Validator.isNotNull(errorMessage) %>">
										<clay:alert
											message="<%= errorMessage %>"
											style="danger"
											title='<%= LanguageUtil.get(request, "error") %>'
										/>
									</c:if>
								</div>

							<%
							}
							%>

						</c:when>
						<c:otherwise>
							<clay:alert
								message='<%= LanguageUtil.get(request, "no-active-connections") %>'
								style="info"
								title='<%= LanguageUtil.get(request, "info") %>'
							/>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</div>