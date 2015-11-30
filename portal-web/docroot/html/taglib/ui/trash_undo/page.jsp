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

<%@ include file="/html/taglib/init.jsp" %>

<%
String portletURL = (String)request.getAttribute("liferay-ui:trash-undo:portletURL");
String redirect = GetterUtil.getString((String)request.getAttribute("liferay-ui:trash-undo:redirect"), currentURL);

if (SessionMessages.contains(portletRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA)) {
	Map<String, String[]> data = (HashMap<String, String[]>)SessionMessages.get(portletRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA);

	if (data != null) {
		int trashedEntriesCount = 0;

		String[] primaryKeys = new String[0];

		Set<String> keys = data.keySet();

		for (String key : keys) {
			if (!key.endsWith("Ids")) {
				continue;
			}

			primaryKeys = ArrayUtil.append(primaryKeys, data.get(key));

			trashedEntriesCount = primaryKeys.length;
		}

		Map<String, Object> trashData = new HashMap<String, Object>();

		trashData.put("navigation", Boolean.TRUE.toString());
%>

		<div class="alert alert-success taglib-trash-undo">
			<aui:form action="<%= portletURL %>" name="undoForm">
				<liferay-util:buffer var="trashLink">
					<c:choose>
						<c:when test="<%= themeDisplay.isShowSiteAdministrationIcon() %>">

							<%
							PortletURL trashURL = TrashUtil.getViewURL(request);
							%>

							<aui:a cssClass="alert-link" data="<%= trashData %>" href="<%= trashURL.toString() %>" label="the-recycle-bin" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="the-recycle-bin" />
						</c:otherwise>
					</c:choose>
				</liferay-util:buffer>

				<%
				String cmd = MapUtil.getString(data, Constants.CMD);
				%>

				<c:choose>
					<c:when test="<%= trashedEntriesCount > 1 %>">
						<c:choose>
							<c:when test="<%= Validator.equals(cmd, Constants.REMOVE) %>">
								<liferay-ui:message arguments="<%= new Object[] {trashedEntriesCount} %>" key="x-items-were-removed" translateArguments="<%= false %>" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message arguments="<%= new Object[] {trashedEntriesCount, trashLink.trim()} %>" key="x-items-were-moved-to-x" translateArguments="<%= false %>" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>

						<%
						String[] classNames = data.get("deleteEntryClassName");

						String className = null;

						String type = "selected-item";

						if (ArrayUtil.isNotEmpty(classNames)) {
							className = classNames[0];

							type = ResourceActionsUtil.getModelResource(request, className);
						}

						String[] titles = data.get("deleteEntryTitle");

						String title = StringPool.BLANK;

						if (ArrayUtil.isNotEmpty(titles)) {
							title = titles[0];
						}
						%>

						<liferay-util:buffer var="trashEntityLink">
							<c:choose>
								<c:when test="<%= !Validator.equals(cmd, Constants.REMOVE) && themeDisplay.isShowSiteAdministrationIcon() && Validator.isNotNull(className) && Validator.isNotNull(title) && Validator.isNotNull(primaryKeys[0]) %>">

									<%
									PortletURL trashURL = TrashUtil.getViewContentURL(request, GetterUtil.getLong(primaryKeys[0]));
									%>

									<em class="delete-entry-title"><aui:a cssClass="alert-link" data="<%= trashData %>" href="<%= trashURL.toString() %>" label="<%= HtmlUtil.escape(title) %>" /></em>
								</c:when>
								<c:when test="<%= Validator.isNotNull(title) %>">
									<em class="delete-entry-title"><%= HtmlUtil.escape(title) %></em>
								</c:when>
							</c:choose>
						</liferay-util:buffer>

						<c:choose>
							<c:when test="<%= Validator.equals(cmd, Constants.REMOVE) %>">
								<liferay-ui:message arguments="<%= new Object[] {LanguageUtil.get(request, type), trashEntityLink} %>" key="the-x-x-was-removed" translateArguments="<%= false %>" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message arguments="<%= new Object[] {LanguageUtil.get(request, type), trashEntityLink, trashLink.trim()} %>" key="the-x-x-was-moved-to-x" translateArguments="<%= false %>" />
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>

				<a class="btn btn-primary btn-sm trash-undo-link" href="javascript:;" id="<portlet:namespace />undo"><liferay-ui:message key="undo" /></a>

				<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

				<%
				for (String key : keys) {
					if (!key.endsWith("Ids")) {
						continue;
					}

					primaryKeys = data.get(key);
				%>

					<aui:input name="<%= key %>" type="hidden" value="<%= StringUtil.merge(primaryKeys) %>" />

				<%
				}
				%>

				<aui:button cssClass="trash-undo-button" type="submit" value="undo" />
			</aui:form>
		</div>

		<aui:script sandbox="<%= true %>">
			$('#<portlet:namespace />undo').on(
				'click',
				function(event) {
					submitForm(document.<portlet:namespace />undoForm);
				}
			);
		</aui:script>

<%
	}
}
%>