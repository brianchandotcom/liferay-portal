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

<liferay-portlet:renderURL var="backURL">
	<portlet:param name="mvcRenderCommandName" value="/change_lists/view" />
</liferay-portlet:renderURL>

<%
Map<Long, List<ConflictInfo>> conflictInfoMap = (Map<Long, List<ConflictInfo>>)request.getAttribute(CTWebKeys.CONFLICT_INFO_MAP);
CTCollection ctCollection = (CTCollection)request.getAttribute(CTWebKeys.CT_COLLECTION);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
renderResponse.setTitle(StringBundler.concat(LanguageUtil.get(request, "publish"), ": ", ctCollection.getName()));
%>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<div class="sheet-lg table-responsive">
		<table class="table table-autofit table-list">
			<tr ><td><h2><%= LanguageUtil.get(request, "conflicting-changes") %></h2></td></tr>
			<tr class="table-divider"><td><%= LanguageUtil.get(request, "automatically-resolved") %></td></tr>

			<%
			for (Map.Entry<Long, List<ConflictInfo>> entry : conflictInfoMap.entrySet()) {
				for (ConflictInfo conflictInfo : entry.getValue()) {
					if (!conflictInfo.isResolved()) {
						continue;
					}

					CTEntry ctEntry = CTEntryLocalServiceUtil.fetchCTEntry(ctCollection.getCtCollectionId(), entry.getKey(), conflictInfo.getSourcePrimaryKey());

					if (ctEntry == null) {
						continue;
					}
			%>

					<tr>
						<td>
							<p class="text-muted"><%= ctDisplayRendererRegistry.getEntryTitle(ctEntry, request) %></p>

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

									<%
									String viewURL = ctDisplayRendererRegistry.getViewURL(liferayPortletRequest, liferayPortletResponse, ctEntry);
									%>

									<c:if test="<%= Validator.isNotNull(viewURL) %>">
										<div class="autofit-col">
											<div class="autofit-section">
												<a class="btn btn-secondary btn-sm" href="<%= viewURL %>" type="button">
													<%= LanguageUtil.get(request, "view") %>
												</a>
											</div>
										</div>
									</c:if>
								</div>
							</div>
						</td>
					</tr>

			<%
				}
			}
			%>

			<tr class="table-divider"><td><%= LanguageUtil.get(request, "needs-manual-resolution") %></td></tr>

			<%
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

					unresolved = true;
			%>

					<tr>
						<td>
							<p class="text-muted"><%= ctDisplayRendererRegistry.getEntryTitle(ctEntry, request) %></p>

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
														<%= LanguageUtil.get(request, "edit") %>
													</a>
												</div>
											</div>
										</c:when>
										<c:otherwise>

											<%
											String viewURL = ctDisplayRendererRegistry.getViewURL(liferayPortletRequest, liferayPortletResponse, ctEntry);
											%>

											<c:if test="<%= Validator.isNotNull(viewURL) %>">
												<div class="autofit-col">
													<div class="autofit-section">
														<a class="btn btn-secondary btn-sm" href="<%= viewURL %>" type="button">
															<%= LanguageUtil.get(request, "view") %>
														</a>
													</div>
												</div>
											</c:if>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</td>
					</tr>

			<%
				}
			}
			%>

			<tr><td>
				<aui:button disabled="<%= unresolved %>" href="<%= changeListsDisplayContext.getPublishURL(ctCollection.getCtCollectionId(), ctCollection.getName()) %>" primary="true" value="publish" />

				<aui:button href="<%= backURL %>" type="cancel" />
			</td></tr>
		</table>
	</div>
</div>