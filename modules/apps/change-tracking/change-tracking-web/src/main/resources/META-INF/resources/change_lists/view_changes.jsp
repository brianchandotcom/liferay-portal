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
ViewChangesDisplayContext viewChangesDisplayContext = (ViewChangesDisplayContext)request.getAttribute(CTWebKeys.VIEW_CHANGES_DISPLAY_CONTEXT);

CTCollection ctCollection = viewChangesDisplayContext.getCtCollection();

Format format = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

renderResponse.setTitle(LanguageUtil.get(request, "changes"));

portletDisplay.setURLBack(viewChangesDisplayContext.getBackURL());
portletDisplay.setShowBackIcon(true);
%>

<nav class="change-lists-tbar component-tbar subnav-tbar-light tbar">
	<clay:container-fluid>
		<ul class="tbar-nav">
			<c:choose>
				<c:when test="<%= ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED %>">
					<li class="tbar-item tbar-item-expand text-left">
						<div class="change-list-name">
							<span><%= HtmlUtil.escape(ctCollection.getName()) %></span>

							<clay:label
								displayType="<%= WorkflowConstants.getStatusStyle(ctCollection.getStatus()) %>"
								label="<%= viewChangesDisplayContext.getStatusLabel(ctCollection.getStatus()) %>"
							/>
						</div>

						<%
						String description = ctCollection.getDescription();

						if (Validator.isNotNull(description)) {
							description = description.concat(" | ");
						}
						%>

						<div class="change-list-description"><%= HtmlUtil.escape(description.concat(LanguageUtil.format(resourceBundle, "published-by-x-on-x", new Object[] {ctCollection.getUserName(), format.format(ctCollection.getStatusDate())}, false))) %></div>
					</li>
					<li class="tbar-item">
						<liferay-portlet:renderURL var="revertURL">
							<portlet:param name="mvcRenderCommandName" value="/change_lists/undo_ct_collection" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
							<portlet:param name="revert" value="true" />
						</liferay-portlet:renderURL>

						<a class="btn btn-secondary btn-sm" href="<%= revertURL %>" type="button">
							<liferay-ui:message key="revert" />
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="tbar-item tbar-item-expand text-left">
						<div class="change-list-name">
							<span><%= HtmlUtil.escape(ctCollection.getName()) %></span>

							<clay:label
								displayType="<%= WorkflowConstants.getStatusStyle(ctCollection.getStatus()) %>"
								label="<%= viewChangesDisplayContext.getStatusLabel(ctCollection.getStatus()) %>"
							/>
						</div>

						<div class="change-list-description"><%= HtmlUtil.escape(ctCollection.getDescription()) %></div>
					</li>
					<li class="tbar-item">
						<liferay-portlet:renderURL var="conflictsURL">
							<portlet:param name="mvcRenderCommandName" value="/change_lists/view_conflicts" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
						</liferay-portlet:renderURL>

						<a class="btn btn-primary btn-sm <%= viewChangesDisplayContext.hasChanges() ? StringPool.BLANK : "disabled" %>" href="<%= conflictsURL %>" type="button">
							<liferay-ui:message key="prepare-to-publish" />
						</a>
					</li>
					<li class="tbar-item">
						<liferay-ui:icon-menu
							direction="left-side"
							icon="ellipsis-v"
							markupView="lexicon"
						>
							<c:if test="<%= ctCollection.getCtCollectionId() != changeListsDisplayContext.getCtCollectionId() %>">
								<liferay-portlet:actionURL name="/change_lists/checkout_ct_collection" var="checkoutURL">
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
								</liferay-portlet:actionURL>

								<liferay-ui:icon
									message="work-on-publication"
									url="<%= checkoutURL %>"
								/>
							</c:if>

							<liferay-portlet:renderURL var="editURL">
								<portlet:param name="mvcRenderCommandName" value="/change_lists/edit_ct_collection" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
							</liferay-portlet:renderURL>

							<liferay-ui:icon
								message="edit"
								url="<%= editURL %>"
							/>

							<li aria-hidden="true" class="dropdown-divider" role="presentation"></li>

							<liferay-portlet:actionURL name="/change_lists/delete_ct_collection" var="deleteURL">
								<portlet:param name="redirect" value="<%= viewChangesDisplayContext.getBackURL() %>" />
								<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
							</liferay-portlet:actionURL>

							<liferay-ui:icon-delete
								confirmation="are-you-sure-you-want-to-delete-this-publication"
								message="delete"
								url="<%= deleteURL %>"
							/>
						</liferay-ui:icon-menu>
					</li>
				</c:otherwise>
			</c:choose>
		</ul>
	</clay:container-fluid>
</nav>

<div class="change-lists-view-changes-wrapper">
	<c:choose>
		<c:when test="<%= viewChangesDisplayContext.hasChanges() %>">
			<react:component
				module="change_lists/js/ChangeTrackingChangesView"
				props="<%= viewChangesDisplayContext.getReactData() %>"
			/>
		</c:when>
		<c:otherwise>
			<clay:container-fluid>
				<liferay-ui:empty-result-message
					message="no-changes-were-found"
				/>
			</clay:container-fluid>
		</c:otherwise>
	</c:choose>
</div>