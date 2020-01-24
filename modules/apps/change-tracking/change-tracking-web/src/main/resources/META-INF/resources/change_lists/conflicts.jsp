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
CTCollection ctCollection = (CTCollection)request.getAttribute(CTWebKeys.CT_COLLECTION);
List<ObjectValuePair<ConflictInfo, CTEntry>> resolvedConflicts = (List<ObjectValuePair<ConflictInfo, CTEntry>>)request.getAttribute(CTWebKeys.RESOLVED_CONFLICTS);
List<ObjectValuePair<ConflictInfo, CTEntry>> unresolvedConflicts = (List<ObjectValuePair<ConflictInfo, CTEntry>>)request.getAttribute(CTWebKeys.UNRESOLVED_CONFLICTS);

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
			for (ObjectValuePair<ConflictInfo, CTEntry> pair : resolvedConflicts) {
				ConflictInfo conflictInfo = pair.getKey();
				CTEntry ctEntry = pair.getValue();

				Date modifiedDate = ctEntry.getModifiedDate();

				String age = LanguageUtil.format(locale, "x-ago", LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - modifiedDate.getTime(), true));

				String title = StringBundler.concat(ctDisplayRendererRegistry.getTypeName(locale, ctEntry), " ", changeListsDisplayContext.getChangeTypeName(ctEntry), " by ", ctEntry.getUserName(), " ", age);

				String viewURL = ctDisplayRendererRegistry.getViewURL(liferayPortletRequest, liferayPortletResponse, ctEntry);
			%>

				<tr>
					<td>
						<p class="text-muted"><%= title %></p>

						<div>
							<div class="alert alert-success autofit-row" role="alert">
								<div class="autofit-col autofit-col-expand">
									<div class="autofit-section">
										<span class="alert-indicator">
											<aui:icon image="check-circle-full" markupView="lexicon" />
										</span>

										<strong class="lead">
											<%= conflictInfo.getConflictDescription(conflictInfo.getResourceBundle(locale)) %>
										</strong>
										<%= conflictInfo.getResolutionDescription(conflictInfo.getResourceBundle(locale)) %>
									</div>
								</div>

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
			%>

			<tr class="table-divider"><td><%= LanguageUtil.get(request, "needs-manual-resolution") %></td></tr>

			<%
			for (ObjectValuePair<ConflictInfo, CTEntry> pair : unresolvedConflicts) {
				ConflictInfo conflictInfo = pair.getKey();
				CTEntry ctEntry = pair.getValue();

				Date modifiedDate = ctEntry.getModifiedDate();

				String age = LanguageUtil.format(locale, "x-ago", LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - modifiedDate.getTime(), true));

				String title = StringBundler.concat(ctDisplayRendererRegistry.getTypeName(locale, ctEntry), " ", changeListsDisplayContext.getChangeTypeName(ctEntry), " by ", ctEntry.getUserName(), " ", age);

				String editURL;

				try {
					editURL = ctDisplayRendererRegistry.getEditURL(request, ctEntry);
				}
				catch (Exception e) {
					editURL = null;
				}
			%>

				<tr>
					<td>
						<p class="text-muted"><%= title %></p>

						<div>
							<div class="alert alert-warning autofit-row" role="alert">
								<div class="autofit-col autofit-col-expand">
									<div class="autofit-section">
										<span class="alert-indicator">
											<aui:icon image="warning-full" markupView="lexicon" />
										</span>

										<strong class="lead">
											<%= conflictInfo.getConflictDescription(conflictInfo.getResourceBundle(locale)) %>
										</strong>
										<%= conflictInfo.getResolutionDescription(conflictInfo.getResourceBundle(locale)) %>
									</div>
								</div>

								<c:if test="<%= Validator.isNotNull(editURL) %>">
									<div class="autofit-col">
										<div class="autofit-section">
											<a class="btn btn-secondary btn-sm" href="<%= editURL %>" type="button">
												<%= LanguageUtil.get(request, "edit") %>
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
			%>

			<tr><td>
				<aui:button disabled="<%= !unresolvedConflicts.isEmpty() %>" href="<%= changeListsDisplayContext.getPublishURL(ctCollection.getCtCollectionId(), ctCollection.getName()) %>" primary="true" value="publish" />

				<aui:button href="<%= backURL %>" type="cancel" />
			</td></tr>
		</table>
	</div>
</div>