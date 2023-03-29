<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<div class="sheet sheet-lg">
	<h5><liferay-ui:message key="build-date" /></h5>

	<p>
		<%= System.getenv("LABEL_BUILD_DATE") %>
	</p>

	<h5><liferay-ui:message key="vcs-hash" /></h5>

	<p>
		<%= System.getenv("LABEL_VCS_REF") %>
	</p>
</div>