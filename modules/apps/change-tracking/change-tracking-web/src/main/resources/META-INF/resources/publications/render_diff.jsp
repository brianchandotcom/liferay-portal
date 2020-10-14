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
CTEntryDiffDisplay ctEntryDiffDisplay = (CTEntryDiffDisplay)request.getAttribute(CTWebKeys.CT_ENTRY_DIFF_DISPLAY);

String alertDescription = ParamUtil.getString(request, "alertDescription");
String alertResolution = ParamUtil.getString(request, "alertResolution");
String alertType = ParamUtil.getString(request, "alertType");
%>

<div class="publications-diff-table-wrapper">
	<table class="table table-autofit">
		<c:if test="<%= Validator.isNotNull(alertType) %>">
			<tr class="publications-diff-no-border-top">
				<td class="publications-diff-td publications-header-td" colspan="<%= ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_MODIFICATION) ? 2 : 1 %>">
					<div class="autofit-row">

						<%
						long userId = ctEntryDiffDisplay.getUserId();
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
								<%= HtmlUtil.escape(ctEntryDiffDisplay.getEntryTitle()) %>
							</div>

							<%
							String entryDescription = ctEntryDiffDisplay.getEntryDescription();
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
			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_ADDITION) %>">
				<td class="publications-diff-td"><%= HtmlUtil.escape(ctEntryDiffDisplay.getLeftTitle()) %></td>
			</c:if>

			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_DELETION) %>">
				<td class="publications-diff-td"><%= HtmlUtil.escape(ctEntryDiffDisplay.getRightTitle()) %></td>
			</c:if>
		</tr>
		<tr>
			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_ADDITION) %>">
				<td class="publications-diff-td">

					<%
					ctEntryDiffDisplay.renderLeftCTRow();
					%>

				</td>
			</c:if>

			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_DELETION) %>">
				<td class="publications-diff-td">

					<%
					ctEntryDiffDisplay.renderRightCTRow();
					%>

				</td>
			</c:if>
		</tr>
	</table>
</div>