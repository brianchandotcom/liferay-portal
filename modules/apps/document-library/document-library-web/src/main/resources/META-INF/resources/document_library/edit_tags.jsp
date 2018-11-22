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

<%@ include file="/document_library/init.jsp" %>

<liferay-portlet:actionURL name="/document_library/edit_tags" varImpl="editTagsURL" />

<aui:form action="<%= editTagsURL %>" cssClass="lfr-dynamic-form" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />

	<div class="lfr-form-content">
		<liferay-asset:asset-tags-error />

		<aui:fieldset-group markupView="lexicon">
			<liferay-asset:asset-tags-selector
				tagNames='<%= GetterUtil.getString((String)request.getAttribute("commonTagNames")) %>'
			/>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>