<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderAttachmentsDisplayContext commerceOrderAttachmentsDisplayContext = (CommerceOrderAttachmentsDisplayContext)request.getAttribute(CommerceOrderAttachmentsDisplayContext.class.getName());
%>

<frontend-data-set:headless-display
	additionalProps="<%= commerceOrderAttachmentsDisplayContext.getAdditionalProps() %>"
	apiURL="<%= commerceOrderAttachmentsDisplayContext.getAPIURL() %>"
	creationMenu="<%= commerceOrderAttachmentsDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= commerceOrderAttachmentsDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= CommerceOrderFDSNames.ATTACHMENTS %>"
	propsTransformer="{AttachmentsFDSPropsTransformer} from commerce-order-web"
	style="stacked"
/>