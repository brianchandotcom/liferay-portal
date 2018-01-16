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

<%@ include file="/init.jsp" %>

<liferay-ui:error key="shutdownMinutes" message="please-enter-the-number-of-minutes" />

<aui:button-row>
	<c:choose>
		<c:when test="<%= ShutdownUtil.isInProcess() %>">
			<aui:button cssClass="save-server-button" data-cmd="shutdown" value="cancel-shutdown" />
		</c:when>
		<c:otherwise>
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:input label="number-of-minutes" name="minutes" size="3" type="text">
						<aui:validator name="digits" />
						<aui:validator name="min">1</aui:validator>
						<aui:validator name="required" />
					</aui:input>

					<aui:input cssClass="lfr-textarea-container" label="custom-message" name="message" type="textarea" />
				</aui:fieldset>
			</aui:fieldset-group>

			<aui:button cssClass="save-server-button" data-cmd="shutdown" value="shutdown" />
		</c:otherwise>
	</c:choose>
</aui:button-row>