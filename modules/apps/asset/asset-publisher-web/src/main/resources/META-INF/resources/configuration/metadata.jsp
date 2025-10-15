<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:input name="preferences--metadataFields--" type="hidden" />

<%
List<KeyValuePair> currentMetadataFields = new ArrayList<>();
List<KeyValuePair> availableMetadataFields = new ArrayList<>();

String[] metadataFields = assetPublisherDisplayContext.getMetadataFields();

for (String metadataField : metadataFields) {
	currentMetadataFields.add(new KeyValuePair(metadataField, LanguageUtil.get(request, metadataField)));
}

String[] allMetadataFields = {"author", "categories", "create-date", "expiration-date", "modified-date", "priority", "publish-date", "tags", "view-count"};

for (String metadataField : allMetadataFields) {
	if (!ArrayUtil.contains(metadataFields, metadataField)) {
		availableMetadataFields.add(new KeyValuePair(metadataField, LanguageUtil.get(request, metadataField)));
	}
}

availableMetadataFields = ListUtil.sort(availableMetadataFields, new KeyValuePairComparator(false, true));
%>

<liferay-ui:input-move-boxes
	rightBoxName="currentMetadataFields"
	rightList="<%= currentMetadataFields %>"
	rightTitle="in-use"
	leftBoxName="availableMetadataFields"
	leftList="<%= availableMetadataFields %>"
	leftTitle="available"
/>