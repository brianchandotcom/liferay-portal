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

String[] allMetadataFields = {"author", "categories", "create-date", "expiration-date", "modified-date", "priority", "publish-date", "tags", "view-count"};
String[] metadataFields = assetPublisherDisplayContext.getMetadataFields();

for (String metadataField : allMetadataFields) {
	if (!ArrayUtil.contains(metadataFields, metadataField)) {
		leftList.add(new KeyValuePair(metadataField, LanguageUtil.get(request, metadataField)));
	}
}

leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

// Right list

List<KeyValuePair> rightList = new ArrayList<>();

for (String metadataField : metadataFields) {
	rightList.add(new KeyValuePair(metadataField, LanguageUtil.get(request, metadataField)));
}
%>

<liferay-ui:input-move-boxes
	leftBoxName="availableMetadataFields"
	leftList="<%= leftList %>"
	leftTitle="available"
	rightBoxName="currentMetadataFields"
	rightList="<%= rightList %>"
	rightTitle="in-use"
/>