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
ViewChangesDisplayContext viewChangesDisplayContext = (ViewChangesDisplayContext)request.getAttribute(CTWebKeys.VIEW_CHANGES_DISPLAY_CONTEXT);

CTCollection ctCollection = viewChangesDisplayContext.getCtCollection();

Format format = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

renderResponse.setTitle(LanguageUtil.get(request, "changes"));

portletDisplay.setURLBack(viewChangesDisplayContext.getBackURL());
portletDisplay.setShowBackIcon(true);
%>

<nav class="component-tbar publications-tbar subnav-tbar-light tbar">
	<clay:container-fluid>
		<ul class="tbar-nav">
			<c:choose>
				<c:when test="<%= ctCollection.getStatus() == WorkflowConstants.STATUS_DRAFT %>">
					<li class="tbar-item tbar-item-expand text-left">
						<div class="publication-name">
							<span><%= HtmlUtil.escape(ctCollection.getName()) %></span>

							<clay:label
								displayType="<%= publicationsDisplayContext.getStatusStyle(ctCollection.getStatus()) %>"
								label="<%= publicationsDisplayContext.getStatusLabel(ctCollection.getStatus()) %>"
							/>
						</div>

						<div class="publication-description"><%= HtmlUtil.escape(ctCollection.getDescription()) %></div>
					</li>

					<c:if test="<%= CTCollectionPermission.contains(permissionChecker, ctCollection, CTActionKeys.PUBLISH) %>">
						<c:if test="<%= PropsValues.SCHEDULER_ENABLED %>">
							<li class="tbar-item">
								<liferay-portlet:renderURL var="scheduleURL">
									<portlet:param name="mvcRenderCommandName" value="/change_tracking/view_conflicts" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
									<portlet:param name="schedule" value="<%= Boolean.TRUE.toString() %>" />
								</liferay-portlet:renderURL>

								<a class="btn btn-secondary btn-sm <%= viewChangesDisplayContext.hasChanges() ? StringPool.BLANK : "disabled" %>" href="<%= scheduleURL %>" type="button">
									<span class="inline-item inline-item-before">
										<clay:icon
											symbol="calendar"
										/>
									</span>

									<liferay-ui:message key="schedule" />
								</a>
							</li>
						</c:if>

						<li class="tbar-item">
							<liferay-portlet:renderURL var="publishURL">
								<portlet:param name="mvcRenderCommandName" value="/change_tracking/view_conflicts" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
							</liferay-portlet:renderURL>

							<a class="btn btn-secondary btn-sm <%= viewChangesDisplayContext.hasChanges() ? StringPool.BLANK : "disabled" %>" href="<%= publishURL %>" type="button">
								<span class="inline-item inline-item-before">
									<clay:icon
										symbol="change"
									/>
								</span>

								<liferay-ui:message key="publish" />
							</a>
						</li>
					</c:if>

					<li class="tbar-item">
						<div class="dropdown">
							<button class="btn btn-monospaced btn-sm btn-unstyled dropdown-toggle hidden" type="button">
								<svg class="lexicon-icon lexicon-icon-ellipsis-v publications-hidden" role="presentation">
									<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg#ellipsis-v" />
								</svg>
							</button>
						</div>

						<react:component
							module="publications/js/DropdownMenu"
							props="<%= viewChangesDisplayContext.getDropdownReactData(permissionChecker) %>"
						/>
					</li>
				</c:when>
				<c:when test="<%= ctCollection.getStatus() == WorkflowConstants.STATUS_EXPIRED %>">
					<li class="tbar-item tbar-item-expand text-left">
						<div class="publication-name">
							<span><%= HtmlUtil.escape(ctCollection.getName()) %></span>

							<clay:label
								displayType="<%= publicationsDisplayContext.getStatusStyle(ctCollection.getStatus()) %>"
								label="<%= publicationsDisplayContext.getStatusLabel(ctCollection.getStatus()) %>"
							/>
						</div>

						<div class="publication-description"><%= HtmlUtil.escape(ctCollection.getDescription()) %></div>
					</li>
					<li class="tbar-item">
						<a class="btn btn-secondary btn-sm disabled" type="button">
							<span class="inline-item inline-item-before">
								<clay:icon
									symbol="calendar"
								/>
							</span>

							<liferay-ui:message key="schedule" />
						</a>
					</li>
					<li class="tbar-item">
						<a class="btn btn-secondary btn-sm disabled" type="button">
							<span class="inline-item inline-item-before">
								<clay:icon
									symbol="change"
								/>
							</span>

							<liferay-ui:message key="publish" />
						</a>
					</li>
					<li class="tbar-item">
						<div class="dropdown">
							<button class="btn btn-monospaced btn-sm btn-unstyled dropdown-toggle hidden" type="button">
								<svg class="lexicon-icon lexicon-icon-ellipsis-v publications-hidden" role="presentation">
									<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg#ellipsis-v" />
								</svg>
							</button>
						</div>

						<react:component
							module="publications/js/DropdownMenu"
							props="<%= viewChangesDisplayContext.getDropdownReactData(permissionChecker) %>"
						/>
					</li>
				</c:when>
				<c:when test="<%= ctCollection.getStatus() == WorkflowConstants.STATUS_SCHEDULED %>">
					<li class="tbar-item tbar-item-expand text-left">
						<div class="publication-name">
							<span><%= HtmlUtil.escape(ctCollection.getName()) %></span>

							<clay:label
								displayType="<%= publicationsDisplayContext.getStatusStyle(ctCollection.getStatus()) %>"
								label="<%= publicationsDisplayContext.getStatusLabel(ctCollection.getStatus()) %>"
							/>
						</div>

						<div class="publication-description"><%= HtmlUtil.escape(viewChangesDisplayContext.getScheduledDescription()) %></div>
					</li>

					<c:if test="<%= CTCollectionPermission.contains(permissionChecker, ctCollection, CTActionKeys.PUBLISH) && PropsValues.SCHEDULER_ENABLED %>">
						<li class="tbar-item">
							<liferay-portlet:actionURL name="/change_tracking/unschedule_publication" var="unscheduleURL">
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
							</liferay-portlet:actionURL>

							<a class="btn btn-secondary btn-sm" href="<%= unscheduleURL %>" type="button">
								<span class="inline-item inline-item-before">
									<clay:icon
										symbol="times-circle"
									/>
								</span>

								<liferay-ui:message key="unschedule" />
							</a>
						</li>
						<li class="tbar-item">
							<liferay-portlet:renderURL var="rescheduleURL">
								<portlet:param name="mvcRenderCommandName" value="/change_tracking/reschedule_publication" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
							</liferay-portlet:renderURL>

							<a class="btn btn-primary btn-sm" href="<%= rescheduleURL %>" type="button">
								<span class="inline-item inline-item-before">
									<clay:icon
										symbol="calendar"
									/>
								</span>

								<liferay-ui:message key="reschedule" />
							</a>
						</li>
					</c:if>
				</c:when>
				<c:otherwise>
					<li class="tbar-item tbar-item-expand text-left">
						<div class="publication-name">
							<span><%= HtmlUtil.escape(ctCollection.getName()) %></span>

							<clay:label
								displayType="<%= publicationsDisplayContext.getStatusStyle(ctCollection.getStatus()) %>"
								label="<%= publicationsDisplayContext.getStatusLabel(ctCollection.getStatus()) %>"
							/>
						</div>

						<%
						String description = ctCollection.getDescription();

						if (Validator.isNotNull(description)) {
							description = description.concat(" | ");
						}
						%>

						<div class="publication-description"><%= HtmlUtil.escape(description.concat(LanguageUtil.format(resourceBundle, "published-by-x-on-x", new Object[] {ctCollection.getUserName(), format.format(ctCollection.getStatusDate())}, false))) %></div>
					</li>
					<li class="tbar-item">
						<liferay-portlet:renderURL var="revertURL">
							<portlet:param name="mvcRenderCommandName" value="/change_tracking/undo_ct_collection" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
							<portlet:param name="revert" value="true" />
						</liferay-portlet:renderURL>

						<a class="btn btn-secondary btn-sm" href="<%= revertURL %>" type="button">
							<liferay-ui:message key="revert" />
						</a>
					</li>
				</c:otherwise>
			</c:choose>
		</ul>
	</clay:container-fluid>
</nav>

<div class="publications-view-changes-wrapper">
	<c:choose>
		<c:when test="<%= viewChangesDisplayContext.hasChanges() %>">
			<react:component
				module="publications/js/ChangeTrackingChangesView"
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