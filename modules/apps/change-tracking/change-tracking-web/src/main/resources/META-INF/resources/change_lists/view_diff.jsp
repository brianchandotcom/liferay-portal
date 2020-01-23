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
CTEntryDiffDisplay ctEntryDiffDisplay = (CTEntryDiffDisplay)request.getAttribute(CTWebKeys.CT_ENTRY_DIFF_DISPLAY);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<div class="change-lists-diff-table-wrapper">
	<table class="table table-autofit">
		<tr class="table-divider">
			<c:choose>
				<c:when test="<%= ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_ADDITION) %>">
					<td class="change-lists-diff-td"><%= HtmlUtil.escape(ctEntryDiffDisplay.getCTCollectionTitle()) %></td>
				</c:when>
				<c:when test="<%= ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_DELETION) %>">
					<td class="change-lists-diff-td"><%= HtmlUtil.escape(ctEntryDiffDisplay.getProductionTitle()) %></td>
				</c:when>
				<c:otherwise>
					<td class="change-lists-diff-td"><%= HtmlUtil.escape(ctEntryDiffDisplay.getProductionTitle()) %></td>
					<td class="change-lists-diff-td"><%= HtmlUtil.escape(ctEntryDiffDisplay.getCTCollectionTitle()) %></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
				<c:when test="<%= ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_ADDITION) %>">
					<td class="change-lists-diff-td">

						<%
						ctEntryDiffDisplay.renderCTCollectionCTEntry();
						%>

					</td>
				</c:when>
				<c:when test="<%= ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_DELETION) %>">
					<td class="change-lists-diff-td">

						<%
						ctEntryDiffDisplay.renderProductionCTEntry();
						%>

					</td>
				</c:when>
				<c:otherwise>
					<td class="change-lists-diff-td">

						<%
						ctEntryDiffDisplay.renderProductionCTEntry();
						%>

					</td>
					<td class="change-lists-diff-td">

						<%
						ctEntryDiffDisplay.renderCTCollectionCTEntry();
						%>

					</td>
				</c:otherwise>
			</c:choose>
		</tr>
	</table>
</div>