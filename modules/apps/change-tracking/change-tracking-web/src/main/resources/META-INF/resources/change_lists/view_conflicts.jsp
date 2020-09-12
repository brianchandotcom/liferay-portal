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

<%@ include file="/change_lists/init.jsp" %>

<%
Map<Long, List<ConflictInfo>> conflictInfoMap = (Map<Long, List<ConflictInfo>>)request.getAttribute(CTWebKeys.CONFLICT_INFO_MAP);
CTCollection ctCollection = (CTCollection)request.getAttribute(CTWebKeys.CT_COLLECTION);

boolean schedule = ParamUtil.getBoolean(request, "schedule");

renderResponse.setTitle(StringBundler.concat(LanguageUtil.get(request, schedule ? "schedule-to-publish-later" : "publish"), ": ", ctCollection.getName()));
%>

<liferay-portlet:renderURL var="backURL">
	<portlet:param name="mvcRenderCommandName" value="/change_lists/view_changes" />
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
		<table class="change-lists-conflicts-table table table-autofit table-list">
			<tr>
				<td class="text-muted" id="changeListsHeader">
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
					<h2><liferay-ui:message key="conflicting-changes" /></h2>

					<%
					String conflictingChangesDescriptionKey = "the-following-conflicts-need-to-be-resolved-before-publishing";

					if (conflictInfoMap.isEmpty()) {
						conflictingChangesDescriptionKey = "no-conflicts-were-found-ready-to-publish";
					}
					%>

					<h5 class="text-muted"><liferay-ui:message key="<%= conflictingChangesDescriptionKey %>" /></h5>
				</td>
			</tr>

			<%
			boolean autoResolved = false;

			for (Map.Entry<Long, List<ConflictInfo>> entry : conflictInfoMap.entrySet()) {
				for (ConflictInfo conflictInfo : entry.getValue()) {
					if (!conflictInfo.isResolved()) {
						continue;
					}

					CTEntry ctEntry = CTEntryLocalServiceUtil.fetchCTEntry(ctCollection.getCtCollectionId(), entry.getKey(), conflictInfo.getSourcePrimaryKey());

					if (ctEntry == null) {
						continue;
					}

					if (!autoResolved) {
						autoResolved = true;
			%>

						<tr class="table-divider"><td><liferay-ui:message key="automatically-resolved" /></td></tr>

					<%
					}
					%>

					<tr>
						<td>
							<clay:content-row>
								<clay:content-col
									cssClass="user-portrait-wrapper"
								>
									<liferay-ui:user-portrait
										userId="<%= ctEntry.getUserId() %>"
									/>
								</clay:content-col>

								<clay:content-col
									expand="<%= true %>"
								>
									<p class="entry-title text-muted"><%= HtmlUtil.escape(ctDisplayRendererRegistry.getEntryDescription(request, ctEntry)) %></p>

									<div>
										<div class="alert alert-success" role="alert">
											<clay:content-row>
												<clay:content-col
													expand="<%= true %>"
												>
													<clay:content-section>
														<span class="alert-indicator">
															<aui:icon image="check-circle-full" markupView="lexicon" />
														</span>

														<%
														ResourceBundle conflictInfoResourceBundle = conflictInfo.getResourceBundle(locale);
														%>

														<strong class="lead">
															<%= conflictInfo.getConflictDescription(conflictInfoResourceBundle) + ":" %>
														</strong>
														<%= conflictInfo.getResolutionDescription(conflictInfoResourceBundle) %>
													</clay:content-section>
												</clay:content-col>

												<%
												boolean viewDiff = false;

												if (conflictInfo.getSourcePrimaryKey() == conflictInfo.getTargetPrimaryKey()) {
													viewDiff = true;
												}

												String viewURL = ctDisplayRendererRegistry.getViewURL(liferayPortletRequest, liferayPortletResponse, ctEntry, viewDiff);
												%>

												<c:if test="<%= Validator.isNotNull(viewURL) %>">
													<clay:content-col>
														<clay:content-section>
															<a class="btn btn-secondary btn-sm" href="<%= viewURL %>" type="button">
																<liferay-ui:message key="view" />
															</a>
														</clay:content-section>
													</clay:content-col>
												</c:if>

												<liferay-portlet:actionURL name="/change_lists/delete_ct_auto_resolution_info" var="dismissURL">
													<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
													<liferay-portlet:param name="ctAutoResolutionInfoId" value="<%= String.valueOf(conflictInfo.getCTAutoResolutionInfoId()) %>" />
												</liferay-portlet:actionURL>

												<clay:content-col>
													<clay:content-section>
														<a class="btn btn-secondary btn-sm" href="<%= dismissURL %>" type="button">
															<liferay-ui:message key="ok" />
														</a>
													</clay:content-section>
												</clay:content-col>
											</clay:content-row>
										</div>
									</div>
								</clay:content-col>
							</clay:content-row>
						</td>
					</tr>

			<%
				}
			}

			boolean unresolved = false;

			for (Map.Entry<Long, List<ConflictInfo>> entry : conflictInfoMap.entrySet()) {
				for (ConflictInfo conflictInfo : entry.getValue()) {
					if (conflictInfo.isResolved()) {
						continue;
					}

					CTEntry ctEntry = CTEntryLocalServiceUtil.fetchCTEntry(ctCollection.getCtCollectionId(), entry.getKey(), conflictInfo.getSourcePrimaryKey());

					if (ctEntry == null) {
						continue;
					}

					if (!unresolved) {
						unresolved = true;
			%>

						<tr class="table-divider"><td><liferay-ui:message key="needs-manual-resolution" /></td></tr>

					<%
					}
					%>

					<tr>
						<td>
							<clay:content-row>
								<clay:content-col
									cssClass="user-portrait-wrapper"
								>
									<liferay-ui:user-portrait
										userId="<%= ctEntry.getUserId() %>"
									/>
								</clay:content-col>

								<clay:content-col
									expand="<%= true %>"
								>
									<p class="entry-title text-muted"><%= HtmlUtil.escape(ctDisplayRendererRegistry.getEntryDescription(request, ctEntry)) %></p>

									<div>
										<div class="alert alert-warning" role="alert">
											<clay:content-row>
												<clay:content-col
													expand="<%= true %>"
												>
													<clay:content-section>
														<span class="alert-indicator">
															<aui:icon image="warning-full" markupView="lexicon" />
														</span>

														<%
														ResourceBundle conflictInfoResourceBundle = conflictInfo.getResourceBundle(locale);
														%>

														<strong class="lead">
															<%= conflictInfo.getConflictDescription(conflictInfoResourceBundle) + ":" %>
														</strong>
														<%= conflictInfo.getResolutionDescription(conflictInfoResourceBundle) %>
													</clay:content-section>
												</clay:content-col>

												<%
												String editURL = ctDisplayRendererRegistry.getEditURL(request, ctEntry);
												%>

												<c:choose>
													<c:when test="<%= Validator.isNotNull(editURL) %>">
														<div class="autofit-col">
															<div class="autofit-section">
																<a class="btn btn-secondary btn-sm" href="<%= editURL %>" type="button">
																	<liferay-ui:message key="edit" />
																</a>
															</div>
														</div>
													</c:when>
													<c:otherwise>

														<%
														boolean viewDiff = false;

														if (conflictInfo.getSourcePrimaryKey() == conflictInfo.getTargetPrimaryKey()) {
															viewDiff = true;
														}

														String viewURL = ctDisplayRendererRegistry.getViewURL(liferayPortletRequest, liferayPortletResponse, ctEntry, viewDiff);
														%>

														<c:if test="<%= Validator.isNotNull(viewURL) %>">
															<clay:content-col>
																<clay:content-section>
																	<a class="btn btn-secondary btn-sm" href="<%= viewURL %>" type="button">
																		<liferay-ui:message key="view" />
																	</a>
																</clay:content-section>
															</clay:content-col>
														</c:if>
													</c:otherwise>
												</c:choose>

												<clay:content-col>
													<clay:content-section>
														<liferay-portlet:renderURL var="discardURL">
															<portlet:param name="mvcRenderCommandName" value="/change_lists/view_discard" />
															<portlet:param name="redirect" value="<%= currentURL %>" />
															<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctEntry.getCtCollectionId()) %>" />
															<portlet:param name="modelClassNameId" value="<%= String.valueOf(ctEntry.getModelClassNameId()) %>" />
															<portlet:param name="modelClassPK" value="<%= String.valueOf(ctEntry.getModelClassPK()) %>" />
														</liferay-portlet:renderURL>

														<a class="btn btn-secondary btn-sm" href="<%= discardURL %>" type="button">
															<liferay-ui:message key="discard" />
														</a>
													</clay:content-section>
												</clay:content-col>
											</clay:content-row>
										</div>
									</div>
								</clay:content-col>
							</clay:content-row>
						</td>
					</tr>

			<%
				}
			}
			%>

			<tr><td id="changeListsFooter">
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
										<portlet:param name="mvcRenderCommandName" value="/change_lists/schedule_publication" />
										<portlet:param name="redirect" value="<%= backURL %>" />
										<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
									</liferay-portlet:renderURL>

									<aui:button disabled="<%= unresolved %>" href="<%= scheduleURL %>" primary="true" value="next" />
								</c:when>
								<c:otherwise>
									<liferay-portlet:actionURL name="/change_lists/publish_ct_collection" var="publishURL">
										<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
										<portlet:param name="name" value="<%= ctCollection.getName() %>" />
									</liferay-portlet:actionURL>

									<aui:button disabled="<%= unresolved %>" href="<%= publishURL %>" primary="true" value="publish" />
								</c:otherwise>
							</c:choose>
						</span>
					</div>
				</div>
			</td></tr>
		</table>
	</div>
</clay:container-fluid>