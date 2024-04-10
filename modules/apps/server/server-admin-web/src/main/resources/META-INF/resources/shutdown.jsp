<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error key="shutdownMinutes" message="please-enter-the-number-of-minutes" />

<c:choose>
	<c:when test="<%= ShutdownUtil.isInProcess() %>">
		<aui:button cssClass="save-server-button" data-cmd="shutdown" value="cancel-shutdown" />
	</c:when>
	<c:otherwise>
		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:input label="number-of-minutes" name="minutes" required="<%= true %>" size="3" type="text">
					<aui:validator name="digits" />
					<aui:validator name="min">1</aui:validator>
				</aui:input>

				<aui:input cssClass="lfr-textarea-container" label="custom-message" name="message" type="textarea" />

				<aui:button cssClass="save-server-button" data-cmd="shutdown" primary="<%= true %>" value="shutdown" />
			</div>
		</div>
	</c:otherwise>
</c:choose>