<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
%>

		<div class="alert alert-success taglib-trash-undo">
			<aui:form action="<%= portletURL %>" name="undoForm">
				<liferay-util:buffer var="trashLink">
					<c:choose>
						<c:when test="<%= themeDisplay.isShowSiteAdministrationIcon() %>">
							<liferay-portlet:renderURL plid="<%= PortalUtil.getControlPanelPlid(company.getCompanyId()) %>" portletName="<%= PortletKeys.TRASH %>" varImpl="trashURL" windowState="<%= WindowState.NORMAL.toString() %>">
								<portlet:param name="struts_action" value="/trash/view" />
							</liferay-portlet:renderURL>

							<%
							String trashURLString = HttpUtil.setParameter(trashURL.toString(), "doAsGroupId", String.valueOf(themeDisplay.getScopeGroupId()));

							if (!layout.isTypeControlPanel() || Validator.isNull(themeDisplay.getControlPanelCategory())) {
								trashURLString = HttpUtil.setParameter(trashURLString, "controlPanelCategory", "current_site");
							}
							%>

							<aui:a href="<%= trashURLString %>" label="the-recycle-bin" />
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
								<liferay-ui:message arguments="<%= new Object[] {trashedEntriesCount} %>" key="x-items-were-removed" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message arguments="<%= new Object[] {trashedEntriesCount, trashLink.trim()} %>" key="x-items-were-moved-to-x" />
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

							type = ResourceActionsUtil.getModelResource(pageContext, className);
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
									<liferay-portlet:renderURL plid="<%= PortalUtil.getControlPanelPlid(company.getCompanyId()) %>" portletName="<%= PortletKeys.TRASH %>" varImpl="trashURL" windowState="<%= WindowState.NORMAL.toString() %>">
										<portlet:param name="struts_action" value="/trash/view_content" />
										<portlet:param name="className" value="<%= className %>" />
										<portlet:param name="classPK" value="<%= String.valueOf(primaryKeys[0]) %>" />
									</liferay-portlet:renderURL>

									<%
									String trashURLString = HttpUtil.setParameter(trashURL.toString(), "doAsGroupId", String.valueOf(themeDisplay.getScopeGroupId()));

									if (Validator.isNull(themeDisplay.getControlPanelCategory())) {
										trashURLString = HttpUtil.setParameter(trashURLString, "controlPanelCategory", "current_site");
									}
									%>

									<em class="delete-entry-title"><aui:a href="<%= trashURLString %>" label="<%= HtmlUtil.escape(title) %>" /></em>
								</c:when>
								<c:when test="<%= Validator.isNotNull(title) %>">
									<em class="delete-entry-title"><%= HtmlUtil.escape(title) %></em>
								</c:when>
							</c:choose>
						</liferay-util:buffer>

						<c:choose>
							<c:when test="<%= Validator.equals(cmd, Constants.REMOVE) %>">
								<liferay-ui:message arguments="<%= new Object[] {type, trashEntityLink} %>" key="the-x-x-was-removed" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message arguments="<%= new Object[] {type, trashEntityLink, trashLink.trim()} %>" key="the-x-x-was-moved-to-x" />
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>

				<a class="btn btn-primary btn-small trash-undo-link" href="javascript:;" id="<%= namespace %>undo"><liferay-ui:message key="undo" /></a>

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

		<aui:script use="aui-base">
			var undoLink = A.one('#<%= namespace %>undo');

			if (undoLink) {
				undoLink.on(
					'click',
					function(event) {
						submitForm(document.<%= namespace %>undoForm);
					}
				);
			}
		</aui:script>

<%
	}
}
%>