<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

if (SessionMessages.contains(portletRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA)) {
	Map<String, long[]> data = (HashMap<String, long[]>)SessionMessages.get(portletRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA);

	if (data != null) {
		int trashedEntriesCount = 0;

		Set<String> keys = data.keySet();

		for (String key : keys) {
			long[] primaryKeys = data.get(key);

			trashedEntriesCount += primaryKeys.length;
		}
%>

		<div class="portlet-msg-success taglib-trash-undo">
			<liferay-ui:message arguments='<%= trashedEntriesCount %>' key='<%= trashedEntriesCount > 1 ? "x-items-were-moved-to-the-recycle-bin" : "the-selected-item-was-moved-to-the-recycle-bin" %>' />

			<a class="trash-undo-link" href="javascript:;" id="<%= namespace %>undo"><liferay-ui:message key="undo" /></a>

			<aui:form action="<%= portletURL %>" cssClass="trash-undo-form" name="undoForm">
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

				<%
				for (String key : keys) {
					long[] primaryKeys = data.get(key);
				%>

					<aui:input name="<%= key %>" type="hidden" value="<%= StringUtil.merge(primaryKeys) %>" />

				<%
				}
				%>

				<aui:button type="submit" value="undo" />
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