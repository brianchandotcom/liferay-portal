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

<%@ include file="/html/taglib/aui/field_wrapper/init.jsp" %>

<div class="<%= controlGroupCss %> <%= AUIUtil.buildCss("field-wrapper", false, first, last, cssClass) %>" <%= AUIUtil.buildData(data) %>>
	<<%= showForLabel ? "label" : "span" %> <%= AUIUtil.buildLabel("wrapper", inlineField, showForLabel, name, disabled) %>>
		<liferay-ui:message key="<%= label %>" localizeKey="<%= localizeLabel %>" />

		<c:if test="<%= required %>">
			<aui:icon cssClass="reference-mark text-warning" image="asterisk" markupView="lexicon" />

			<span class="hide-accessible"><liferay-ui:message key="required" /></span>
		</c:if>

		<c:if test="<%= Validator.isNotNull(helpMessage) %>">
			<liferay-ui:icon-help message="<%= helpMessage %>" />
		</c:if>
	</<%= showForLabel ? "label" : "span" %>>