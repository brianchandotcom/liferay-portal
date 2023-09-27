<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);
%>

<liferay-asset:asset-categories-error />

<liferay-asset:asset-tags-error />

<clay:sheet-section>
	<h3 class="sheet-subtitle">
		<liferay-ui:message key="more-information" />
	</h3>

	<%
	Group controlPanelGroup = themeDisplay.getControlPanelGroup();
	%>

	<div class="form-group">
		<liferay-asset:asset-categories-selector
			className="<%= AccountEntry.class.getName() %>"
			classPK="<%= accountEntryDisplay.getAccountEntryId() %>"
			groupIds="<%= new long[] {controlPanelGroup.getGroupId()} %>"
			visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
		/>
	</div>

	<div class="form-group">
		<liferay-asset:asset-tags-selector
			className="<%= AccountEntry.class.getName() %>"
			classPK="<%= accountEntryDisplay.getAccountEntryId() %>"
			groupIds="<%= new long[] {controlPanelGroup.getGroupId(), themeDisplay.getSiteGroupId()} %>"
		/>
	</div>
</clay:sheet-section>