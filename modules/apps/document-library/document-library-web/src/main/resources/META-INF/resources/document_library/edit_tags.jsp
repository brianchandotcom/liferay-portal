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

<liferay-portlet:actionURL copyCurrentRenderParameters="<%= true %>" name="/document_library/edit_tags" varImpl="editTagsURL" />

<div class="container-fluid-1280">
	<aui:form action="<%= editTagsURL %>" cssClass="lfr-dynamic-form" enctype="multipart/form-data" method="post" name="fm">
		<liferay-portlet:renderURLParams varImpl="editTagsURL" />
		<aui:input name="<%= Constants.CMD %>" type="hidden" />

		<div class="lfr-form-content">
			<liferay-asset:asset-tags-error />

			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>

					<%
					Selection<DLFileEntry> selection = (Selection)request.getAttribute("selection");
					%>

					<c:choose>
						<c:when test="<%= selection.getSize() == 1 %>">

							<%
							DLFileEntry dlFileEnty = selection.getFirst();
							%>

							<liferay-ui:message arguments="<%= dlFileEnty.getTitle() %>" key="you-are-editing-the-tags-for-x" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message arguments="<%= selection.getSize() %>" key="you-are-editing-the-common-tags-for-x-items" /> <liferay-ui:message key="select-add-or-replace-current-tags" />

							<div class="form-group" id="<portlet:namespace />tagOptions">
								<aui:input checked="<%= true %>" label="add" name="add" type="radio" value="<%= true %>" />

								<aui:input checked="<%= false %>" label="replace" name="add" type="radio" value="<%= false %>" />
							</div>
						</c:otherwise>
					</c:choose>

					<liferay-asset:asset-tags-selector
						tagNames='<%= GetterUtil.getString((String)request.getAttribute("commonTagNames")) %>'
					/>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>

		<aui:button-row>
			<aui:button type="submit" value="save" />
			<aui:button href='<%= ParamUtil.getString(request, "redirect") %>' type="cancel" />
		</aui:button-row>
	</aui:form>
</div>