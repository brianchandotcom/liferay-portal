<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:input name="preferences--metadataFields--" type="hidden" />

<%

// Left list

List<KeyValuePair> leftList = new ArrayList<>();

String[] metadataFields = assetPublisherDisplayContext.getMetadataFields();

for (String metadataField : metadataFields) {
	leftList.add(new KeyValuePair(metadataField, LanguageUtil.get(request, metadataField)));
}

// Right list

List<KeyValuePair> rightList = new ArrayList<>();

String[] allMetadataFields = {"author", "categories", "create-date", "expiration-date", "modified-date", "priority", "publish-date", "tags", "view-count"};

for (String metadataField : allMetadataFields) {
	if (!ArrayUtil.contains(metadataFields, metadataField)) {
		rightList.add(new KeyValuePair(metadataField, LanguageUtil.get(request, metadataField)));
	}
}

rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
%>

<liferay-ui:input-move-boxes
	leftBoxName="currentMetadataFields"
	leftList="<%= leftList %>"
	leftTitle="current"
	rightBoxName="availableMetadataFields"
	rightList="<%= rightList %>"
	rightTitle="available"
/>