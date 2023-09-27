<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info/item/renderer/init.jsp" %>

<%
ObjectEntry objectEntry = (ObjectEntry)request.getAttribute(ObjectWebKeys.OBJECT_ENTRY);

String friendlyURL = assetDisplayPageFriendlyURLProvider.getFriendlyURL(new InfoItemReference(objectEntry.getModelClassName(), new ClassPKInfoItemIdentifier(objectEntry.getObjectEntryId())), themeDisplay);

Map<String, Serializable> objectEntryValues = (Map<String, Serializable>)request.getAttribute(ObjectWebKeys.OBJECT_ENTRY_VALUES);

for (Map.Entry<String, Serializable> entry : objectEntryValues.entrySet()) {
%>

	<td class="table-cell-expand-smallest">
		<c:choose>
			<c:when test="<%= entry.getValue() instanceof Link %>">

				<%
				Link fileEntryLink = (Link)entry.getValue();
				%>

				<a class="text-truncate-inline" href="<%= fileEntryLink.getHref() %>">
					<span class="text-truncate"><%= fileEntryLink.getLabel() %></span>
				</a>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(friendlyURL) %>">
						<a class="text-truncate-inline" href="<%= friendlyURL %>">
							<span class="text-truncate"><%= entry.getValue() %></span>
						</a>
					</c:when>
					<c:otherwise>
						<span class="text-truncate-inline">
							<span class="text-truncate"><%= entry.getValue() %></span>
						</span>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</td>

<%
}
%>