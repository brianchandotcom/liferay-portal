<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceDataIntegrationProcess commerceDataIntegrationProcess = (CommerceDataIntegrationProcess)request.getAttribute(CommerceDataIntegrationWebKeys.COMMERCE_DATA_INTEGRATION_PROCESS);
TalendProcessTypeHelper talendProcessTypeHelper = (TalendProcessTypeHelper)request.getAttribute("talendProcessTypeHelper");
%>

<liferay-portlet:actionURL name="editTalendCommerceDataIntegrationProcess" portletName="<%= CommerceDataIntegrationPortletKeys.COMMERCE_DATA_INTEGRATION %>" var="editTalendCommerceDataIntegrationProcessActionURL" />

<div class="closed container-fluid container-fluid-max-xl" id="<portlet:namespace />editCommerceDataIntegrationProcessId">
	<div class="container main-content-body sheet">
		<aui:form action="<%= editTalendCommerceDataIntegrationProcessActionURL %>" cssClass="container-fluid container-fluid-max-xl" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commerceDataIntegrationProcessId" type="hidden" value="<%= String.valueOf(commerceDataIntegrationProcess.getCommerceDataIntegrationProcessId()) %>" />

			<aui:model-context bean="<%= commerceDataIntegrationProcess %>" model="<%= CommerceDataIntegrationProcess.class %>" />

			<%
			FileEntry fileEntry = talendProcessTypeHelper.getFileEntry(commerceDataIntegrationProcess.getCommerceDataIntegrationProcessId());
			%>

			<p class="<%= (fileEntry != null) ? "text-default" : "hide text-default" %>" id="<portlet:namespace />fileEntryName">
				<span id="<portlet:namespace />fileEntryRemove">
					<liferay-ui:icon
						icon="times"
						markupView="lexicon"
						message="remove"
					/>
				</span>
				<span>
					<%= (fileEntry != null) ? fileEntry.getFileName() : StringPool.BLANK %>
				</span>
			</p>

			<c:if test="<%= (commerceDataIntegrationProcess == null) || !commerceDataIntegrationProcess.isSystem() %>">
				<div class="<%= (fileEntry != null) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />fileEntry">
					<aui:input name="srcArchive" required="<%= true %>" type="file" />
				</div>
			</c:if>

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" value="save" />

				<aui:button cssClass="btn-lg" href="<%= currentURL %>" type="cancel" />
			</aui:button-row>
		</aui:form>
	</div>
</div>

<aui:script>
	window.document
		.querySelector('#<portlet:namespace />fileEntryRemove')
		.addEventListener('click', function (event) {
			event.preventDefault();

			window.document
				.querySelector('#<portlet:namespace />fileEntry')
				.classList.remove('hide');

			window.document
				.querySelector('#<portlet:namespace />fileEntryName')
				.classList.add('hide');
		});
</aui:script>