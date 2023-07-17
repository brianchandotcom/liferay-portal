<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long fragmentCollectionId = ParamUtil.getLong(request, "fragmentCollectionId");

ImportDisplayContext importDisplayContext = new ImportDisplayContext(request, renderRequest, renderResponse);
%>

<portlet:actionURL name="/fragment/import" var="importURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
	<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentCollectionId) %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= importURL %>"
	enctype="multipart/form-data"
	name="fm"
>
	<liferay-frontend:edit-form-body>

		<%
		List<String> draftFragmentsImporterResultEntries = importDisplayContext.getFragmentsImporterResultEntries(FragmentsImporterResultEntry.Status.IMPORTED_DRAFT);
		%>

		<c:if test="<%= ListUtil.isNotEmpty(draftFragmentsImporterResultEntries) %>">
			<clay:alert
				dismissible="<%= true %>"
				message='<%= LanguageUtil.format(request, "the-following-fragments-have-validation-issues.-they-have-been-left-in-draft-status-x", "<strong>" + StringUtil.merge(draftFragmentsImporterResultEntries, StringPool.COMMA_AND_SPACE) + "</strong>", false) %>'
			/>
		</c:if>

		<%
		List<String> invalidFragmentsImporterResultEntries = importDisplayContext.getFragmentsImporterResultEntries(FragmentsImporterResultEntry.Status.INVALID);
		%>

		<c:if test="<%= ListUtil.isNotEmpty(invalidFragmentsImporterResultEntries) %>">
			<clay:alert
				dismissible="<%= true %>"
				displayType="warning"
				message='<%= LanguageUtil.format(request, "the-following-fragments-could-not-be-imported-x", "<strong>" + StringUtil.merge(invalidFragmentsImporterResultEntries, StringPool.COMMA_AND_SPACE) + "</strong>", false) %>'
			/>
		</c:if>

		<liferay-ui:error exception="<%= DuplicateFragmentCollectionKeyException.class %>">

			<%
			DuplicateFragmentCollectionKeyException dfcke = (DuplicateFragmentCollectionKeyException)errorException;
			%>

			<liferay-ui:message arguments='<%= "<em>" + dfcke.getMessage() + "</em>" %>' key="a-fragment-set-with-the-key-x-already-exists" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= DuplicateFragmentEntryKeyException.class %>">

			<%
			DuplicateFragmentEntryKeyException dfeke = (DuplicateFragmentEntryKeyException)errorException;
			%>

			<liferay-ui:message arguments='<%= "<em>" + dfeke.getMessage() + "</em>" %>' key="a-fragment-entry-with-the-key-x-already-exists" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= InvalidFileException.class %>" message="the-selected-file-is-not-a-valid-zip-file" />

		<liferay-frontend:fieldset>
			<aui:input label="select-file" name="file" required="<%= true %>" type="file">
				<aui:validator name="acceptFiles">
					'zip'
				</aui:validator>
			</aui:input>

			<aui:input checked="<%= true %>" label="overwrite-existing-entries" name="overwrite" type="checkbox" />
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>