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

renderResponse.setTitle(StringBundler.concat(LanguageUtil.get(request, "publish"), ": ", ctCollection.getName()));
%>

<liferay-portlet:renderURL var="backURL">
	<portlet:param name="mvcRenderCommandName" value="/change_lists/view_changes" />
	<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
</liferay-portlet:renderURL>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<clay:container
	className="container-form-lg"
>
	<div class="sheet-lg table-responsive">
		<table class="change-lists-conflicts-table table table-autofit table-list">
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
							<div class="autofit-row">
								<div class="autofit-col user-portrait-wrapper">
									<liferay-ui:user-portrait
										userId="<%= ctEntry.getUserId() %>"
									/>
								</div>

								<div class="autofit-col-expand">
									<p class="entry-title text-muted"><%= ctDisplayRendererRegistry.getEntryTitle(ctEntry, request) %></p>

									<div>
										<div class="alert alert-success autofit-row" role="alert">
											<div class="autofit-col autofit-col-expand">
												<div class="autofit-section">
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
												</div>
											</div>

											<liferay-portlet:actionURL name="/change_lists/delete_ct_auto_resolution_info" var="dismissURL">
												<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
												<liferay-portlet:param name="ctAutoResolutionInfoId" value="<%= String.valueOf(conflictInfo.getCTAutoResolutionInfoId()) %>" />
											</liferay-portlet:actionURL>

											<div class="autofit-col">
												<div class="autofit-section">
													<a class="btn btn-secondary btn-sm" href="<%= dismissURL %>" type="button">
														<liferay-ui:message key="dismiss" />
													</a>
												</div>
											</div>

											<%
											boolean viewDiff = false;

											if (conflictInfo.getSourcePrimaryKey() == conflictInfo.getTargetPrimaryKey()) {
												viewDiff = true;
											}

											String viewURL = ctDisplayRendererRegistry.getViewURL(liferayPortletRequest, liferayPortletResponse, ctEntry, viewDiff);
											%>

											<c:if test="<%= Validator.isNotNull(viewURL) %>">
												<div class="autofit-col">
													<div class="autofit-section">
														<a class="btn btn-secondary btn-sm" href="<%= viewURL %>" type="button">
															<liferay-ui:message key="view" />
														</a>
													</div>
												</div>
											</c:if>
										</div>
									</div>
								</div>
							</div>
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
							<div class="autofit-row">
								<div class="autofit-col user-portrait-wrapper">
									<liferay-ui:user-portrait
										userId="<%= ctEntry.getUserId() %>"
									/>
								</div>

								<div class="autofit-col-expand">
									<p class="entry-title text-muted"><%= ctDisplayRendererRegistry.getEntryTitle(ctEntry, request) %></p>

									<div>
										<div class="alert alert-warning autofit-row" role="alert">
											<div class="autofit-col autofit-col-expand">
												<div class="autofit-section">
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
												</div>
											</div>

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
														<div class="autofit-col">
															<div class="autofit-section">
																<a class="btn btn-secondary btn-sm" href="<%= viewURL %>" type="button">
																	<liferay-ui:message key="view" />
																</a>
															</div>
														</div>
													</c:if>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
							</div>
						</td>
					</tr>

			<%
				}
			}
			%>

			<liferay-portlet:actionURL name="/change_lists/publish_ct_collection" var="publishURL">
				<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
				<portlet:param name="name" value="<%= ctCollection.getName() %>" />
			</liferay-portlet:actionURL>

			<tr><td>
				<aui:button disabled="<%= unresolved %>" href="<%= publishURL %>" primary="true" value="publish" />

				<aui:button href="<%= backURL %>" type="cancel" />
			</td></tr>
		</table>
	</div>
</clay:container>