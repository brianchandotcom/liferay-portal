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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
Collection<ConvertProcess> convertProcesses = ConvertProcessUtil.getEnabledConvertProcesses();
%>

<liferay-ui:error exception="<%= FileSystemStoreRootDirException.class %>" message="the-root-directories-of-the-selected-file-system-stores-are-not-valid" />

<c:choose>
	<c:when test="<%= convertProcesses.isEmpty() %>">
		<div class="alert alert-info">
			<liferay-ui:message key="no-data-migration-processes-are-available" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		int i = 0;

		for (ConvertProcess convertProcess : convertProcesses) {
			String parameterDescription = convertProcess.getParameterDescription();
			String[] parameterNames = convertProcess.getParameterNames();
		%>

			<liferay-ui:panel-container extended="<%= true %>" id='<%= "convert" + i + "PanelContainer" %>' persistState="<%= true %>">
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='<%= "convert" + i + "Panel" %>' persistState="<%= true %>" title="<%= convertProcess.getDescription() %>">
					<c:choose>
						<c:when test="<%= parameterNames == null %>">
							<div class="alert alert-info">
								<liferay-ui:message key="<%= convertProcess.getConfigurationErrorMessage() %>" />
							</div>
						</c:when>
						<c:otherwise>
							<aui:fieldset label='<%= Validator.isNotNull(parameterDescription) ? parameterDescription : "" %>'>

								<%
								for (String parameterName : parameterNames) {
									if (parameterName.contains(StringPool.EQUAL) && parameterName.contains(StringPool.SEMICOLON)) {
										String[] parameterPair = StringUtil.split(parameterName, CharPool.EQUAL);
										String[] parameterSelectEntries = StringUtil.split(parameterPair[1], CharPool.SEMICOLON);
								%>

									<aui:select label="<%= parameterPair[0] %>" name="<%= convertProcess.getClass().getName() + StringPool.PERIOD + parameterPair[0] %>">

										<%
										for (String parameterSelectEntry : parameterSelectEntries) {
										%>

											<aui:option label="<%= parameterSelectEntry %>" />

										<%
										}
										%>

									</aui:select>

								<%
									}
									else {
										String[] parameterPair = StringUtil.split(parameterName, CharPool.EQUAL);

										String currentParameterName = null;
										String currentParameterType = null;

										if (parameterPair.length > 1) {
											currentParameterName = parameterPair[0];
											currentParameterType = parameterPair[1];
										}
										else {
											currentParameterName = parameterName;
										}
								%>

										<aui:input cssClass="lfr-input-text-container" label="<%= currentParameterName %>" name="<%= convertProcess.getClass().getName() + StringPool.PERIOD + currentParameterName %>" type='<%= currentParameterType != null ? currentParameterType : "" %>' />

								<%
									}
								}
								%>

							</aui:fieldset>

							<aui:button-row>
								<aui:button cssClass="save-server-button" data-cmd='<%= "convertProcess." + convertProcess.getClass().getName() %>' value="execute" />
							</aui:button-row>
						</c:otherwise>
					</c:choose>
				</liferay-ui:panel>
			</liferay-ui:panel-container>

			<br />

		<%
			i++;
		}
		%>

	</c:otherwise>
</c:choose>