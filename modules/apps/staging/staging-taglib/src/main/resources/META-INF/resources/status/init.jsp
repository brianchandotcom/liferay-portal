<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String cssClass = GetterUtil.getString(request.getAttribute("liferay-staging:status:cssClass"));
StagedModel stagedModel = (StagedModel)request.getAttribute("liferay-staging:status:stagedModel");

Date lastPublishDate = null;
Date modifiedDate = null;

if ((stagedModel != null) && (stagedModel instanceof StagedGroupedModel)) {
	StagedGroupedModel stagedGroupedModel = (StagedGroupedModel)stagedModel;

	lastPublishDate = stagedGroupedModel.getLastPublishDate();
	modifiedDate = stagedGroupedModel.getModifiedDate();
}

boolean published = false;

Group themeDisplayScopeGroup = themeDisplay.getScopeGroup();

boolean stagedPortlet = group.isInStagingPortlet(portletDisplay.getId());

if (stagedPortlet) {
	if (lastPublishDate == null) {
		lastPublishDate = modifiedDate;
	}

	if ((lastPublishDate != null) && lastPublishDate.after(modifiedDate)) {
		published = true;
	}

	if (Validator.isNull(cssClass)) {
		cssClass = "label-success";

		if (!published) {
			cssClass = "label-warning";
		}
	}
}
%>