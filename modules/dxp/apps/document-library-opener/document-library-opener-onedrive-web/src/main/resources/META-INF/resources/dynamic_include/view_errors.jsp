<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<liferay-ui:error exception="<%= GraphServicePortalException.class %>" message="onedrive-exception-general-exception" />
<liferay-ui:error exception="<%= GraphServicePortalException.AccessDenied.class %>" message="onedrive-exception-access-denied" />
<liferay-ui:error exception="<%= GraphServicePortalException.ActivityLimitReached.class %>" message="onedrive-exception-activity-limit-reached" />
<liferay-ui:error exception="<%= GraphServicePortalException.InvalidRange.class %>" message="onedrive-exception-invalid-range" />
<liferay-ui:error exception="<%= GraphServicePortalException.InvalidRequest.class %>" message="onedrive-exception-invalid-request" />
<liferay-ui:error exception="<%= GraphServicePortalException.ItemNotFound.class %>" message="onedrive-exception-item-not-found" />
<liferay-ui:error exception="<%= GraphServicePortalException.MalwareDetected.class %>" message="onedrive-exception-malware-detected" />
<liferay-ui:error exception="<%= GraphServicePortalException.NameAlreadyExists.class %>" message="onedrive-exception-name-already-exists" />
<liferay-ui:error exception="<%= GraphServicePortalException.NotAllowed.class %>" message="onedrive-exception-not-allowed" />
<liferay-ui:error exception="<%= GraphServicePortalException.NotSupported.class %>" message="onedrive-exception-not-supported" />
<liferay-ui:error exception="<%= GraphServicePortalException.QuotaLimitReached.class %>" message="onedrive-exception-quota-limit-reached" />
<liferay-ui:error exception="<%= GraphServicePortalException.ResourceModified.class %>" message="onedrive-exception-resource-modified" />
<liferay-ui:error exception="<%= GraphServicePortalException.ResyncRequired.class %>" message="onedrive-exception-resync-required" />
<liferay-ui:error exception="<%= GraphServicePortalException.ServiceNotAvailable.class %>" message="onedrive-exception-service-not-available" />
<liferay-ui:error exception="<%= GraphServicePortalException.Unauthenticated.class %>" message="onedrive-exception-unauthenticated" />