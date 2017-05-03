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

<%
CPMediaType cpMediaType = (CPMediaType)request.getAttribute(CPWebKeys.COMMERCE_PRODUCT_MEDIA_TYPE);

long cpMediaTypeId = BeanParamUtil.getLong(cpMediaType, request, "cpMediaTypeId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((cpMediaType == null) ? LanguageUtil.get(request, "add-media-type") : cpMediaType.getTitle(locale));
%>

<portlet:actionURL name="editMediaType" var="editMediaTypeActionURL" />

<aui:form action="<%= editMediaTypeActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpMediaType == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="cpMediaTypeId" type="hidden" value="<%= cpMediaTypeId %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= cpMediaType %>"
			id="<%= CPMediaTypeFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_MEDIA_TYPE %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>