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

<%@ include file="/publications/init.jsp" %>

<%
ViewConflictsDisplayContext viewConflictsDisplayContext = (ViewConflictsDisplayContext)request.getAttribute(CTWebKeys.VIEW_CONFLICTS_DISPLAY_CONTEXT);

CTCollection ctCollection = viewConflictsDisplayContext.getCtCollection();

boolean resolved = !viewConflictsDisplayContext.hasUnresolvedConflicts();

boolean schedule = ParamUtil.getBoolean(request, "schedule");

renderResponse.setTitle(StringBundler.concat(LanguageUtil.get(request, schedule ? "schedule-to-publish-later" : "publish"), ": ", ctCollection.getName()));
%>

<liferay-portlet:renderURL var="backURL">
	<portlet:param name="mvcRenderCommandName" value="/publications/view_changes" />
	<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
</liferay-portlet:renderURL>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<div class="sheet-lg table-responsive">
		<table class="publications-conflicts-table table table-autofit table-list">
			<tr>
				<td class="text-muted" id="publicationsHeader">
					<div class="autofit-row">
						<div class="autofit-col autofit-col-expand">
							<span>
								<h5>
									<liferay-ui:message key="resolve-any-conflicts" />
								</h5>
							</span>
						</div>

						<div class="autofit-col">
							<span>
								<h5><liferay-ui:message arguments="<%= new Object[] {1, schedule ? 2 : 1} %>" key="step-x-of-x" translateArguments="<%= false %>" /></h5>
							</span>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<clay:sheet-header>
						<h2 class="sheet-title"><liferay-ui:message key="conflicting-changes" /></h2>

						<c:choose>
							<c:when test="<%= resolved %>">
								<clay:alert
									displayType="success"
									message="no-unresolved-conflicts-ready-to-publish"
								/>
							</c:when>
							<c:otherwise>
								<clay:alert
									displayType="warning"
									message="this-publication-contains-conflicting-changes-that-must-be-manually-resolved-before-publishing"
								/>
							</c:otherwise>
						</c:choose>
					</clay:sheet-header>

					<c:if test="<%= viewConflictsDisplayContext.hasUnresolvedConflicts() %>">
						<clay:sheet-section>
							<liferay-frontend:fieldset
								collapsible="<%= true %>"
								label="<%= viewConflictsDisplayContext.getUnresolvedConflictsTitle() %>"
								localizeLabel="<%= false %>"
							>
								<div>
									<react:component
										module="publications/js/ChangeTrackingConflictsView"
										props="<%= viewConflictsDisplayContext.getUnresolvedConflictsReactData() %>"
									/>
								</div>
							</liferay-frontend:fieldset>
						</clay:sheet-section>
					</c:if>

					<c:if test="<%= viewConflictsDisplayContext.hasResolvedConflicts() %>">
						<clay:sheet-section>
							<liferay-frontend:fieldset
								collapsed="<%= true %>"
								collapsible="<%= true %>"
								label="<%= viewConflictsDisplayContext.getResolvedConflictsTitle() %>"
								localizeLabel="<%= false %>"
							>
								<div>
									<react:component
										module="publications/js/ChangeTrackingConflictsView"
										props="<%= viewConflictsDisplayContext.getResolvedConflictsReactData() %>"
									/>
								</div>
							</liferay-frontend:fieldset>
						</clay:sheet-section>
					</c:if>
				</td>
			</tr>
			<tr><td id="publicationsFooter">
				<div class="autofit-row">
					<div class="autofit-col autofit-col-expand">
						<span>
							<aui:button href="<%= backURL %>" type="cancel" />
						</span>
					</div>

					<div class="autofit-col">
						<span>
							<c:choose>
								<c:when test="<%= schedule %>">
									<liferay-portlet:renderURL var="scheduleURL">
										<portlet:param name="mvcRenderCommandName" value="/publications/schedule_publication" />
										<portlet:param name="redirect" value="<%= backURL %>" />
										<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
									</liferay-portlet:renderURL>

									<aui:button disabled="<%= !resolved %>" href="<%= scheduleURL %>" primary="true" value="next" />
								</c:when>
								<c:otherwise>
									<liferay-portlet:actionURL name="/publications/publish_ct_collection" var="publishURL">
										<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
										<portlet:param name="name" value="<%= ctCollection.getName() %>" />
									</liferay-portlet:actionURL>

									<aui:button disabled="<%= !resolved %>" href="<%= publishURL %>" primary="true" value="publish" />
								</c:otherwise>
							</c:choose>
						</span>
					</div>
				</div>
			</td></tr>
		</table>
	</div>
</clay:container-fluid>