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

<aui:input name="preferences--metadataFields--" type="hidden" />

<%

// Left list

List leftList = new ArrayList();

String[] metadataFields = assetPublisherDisplayContext.getMetadataFields();

for (int i = 0; i < metadataFields.length; i++) {
	String folderColumn = metadataFields[i];

	leftList.add(new KeyValuePair(folderColumn, LanguageUtil.get(request, folderColumn)));
}

// Right list

List rightList = new ArrayList();

Arrays.sort(metadataFields);

String[] allMetadataFields = new String[] {"create-date", "modified-date", "publish-date", "expiration-date", "priority", "author", "view-count", "categories", "tags"};

for (String folderColumn : allMetadataFields) {
	if (Arrays.binarySearch(metadataFields, folderColumn) < 0) {
		rightList.add(new KeyValuePair(folderColumn, LanguageUtil.get(request, folderColumn)));
	}
}

rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
%>

<liferay-ui:input-move-boxes
	leftBoxName="currentMetadataFields"
	leftList="<%= leftList %>"
	leftReorder="<%= Boolean.TRUE.toString() %>"
	leftTitle="current"
	rightBoxName="availableMetadataFields"
	rightList="<%= rightList %>"
	rightTitle="available"
/>