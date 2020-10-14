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
ViewEntryDisplayContext viewEntryDisplayContext = (ViewEntryDisplayContext)request.getAttribute(CTWebKeys.VIEW_ENTRY_DISPLAY_CONTEXT);

String alertDescription = ParamUtil.getString(request, "alertDescription");
String alertResolution = ParamUtil.getString(request, "alertResolution");
String alertType = ParamUtil.getString(request, "alertType");
%>

<div class="publications-diff-table-wrapper">
	<table class="table table-autofit">
		<c:if test="<%= Validator.isNotNull(alertType) %>">
			<tr class="publications-diff-no-border-top">
				<td class="publications-diff-td publications-header-td">
					<div class="autofit-row">

						<%
						long userId = viewEntryDisplayContext.getUserId();
						%>

						<c:if test="<%= userId > 0 %>">
							<div class="autofit-col">
								<liferay-ui:user-portrait
									userId="<%= userId %>"
								/>
							</div>
						</c:if>

						<div class="autofit-col">
							<div class="publication-name">
								<%= HtmlUtil.escape(viewEntryDisplayContext.getEntryTitle(locale)) %>
							</div>

							<%
							String entryDescription = viewEntryDisplayContext.getEntryDescription(request);
							%>

							<c:if test="<%= Validator.isNotNull(entryDescription) %>">
								<div class="publication-description"><%= HtmlUtil.escape(entryDescription) %></div>
							</c:if>
						</div>
					</div>

					<div class="alert alert-<%= alertType %>" role="alert">
						<span class="alert-indicator">
							<aui:icon image='<%= StringBundler.concat(alertType.equals("success") ? "check-circle" : "warning", "-full") %>' markupView="lexicon" />
						</span>

						<strong class="lead">
							<%= alertDescription %>:
						</strong>

						<%= alertResolution %>
					</div>
				</td>
			</tr>
		</c:if>

		<tr class="publications-diff-no-border-top table-divider">
			<td class="publications-diff-td"><%= HtmlUtil.escape(viewEntryDisplayContext.getDividerTitle(resourceBundle)) %></td>
		</tr>
		<tr>
			<td class="publications-diff-td">

				<%
				viewEntryDisplayContext.renderEntry(request, response);
				%>

			</td>
		</tr>
	</table>
</div>