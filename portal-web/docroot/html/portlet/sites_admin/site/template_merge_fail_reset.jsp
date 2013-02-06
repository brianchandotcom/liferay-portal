<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
long groupId = (Long)request.getAttribute("details.jsp-groupId");
boolean privateLayoutSet = (Boolean)request.getAttribute("details.jsp-privateLayoutSet");
boolean forceMergeNow = (Boolean)request.getAttribute("details.jsp-forceMergeNow");

LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute("details.jsp-layoutSetPrototype");

int mergeFailCount = SitesUtil.getMergeFailCount(layoutSetPrototype);

String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_sites_admin_site_template_propagation_fail_reset_" + privateLayoutSet) + StringPool.UNDERLINE;
%>

<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">

	<aui:field-wrapper inlineField="true" label="">
		<span class="portlet-msg-alert">
			<aui:a href="javascript:;" id='<%= randomNamespace + "mergeFailPopupButton" %>' >
				<liferay-ui:message key="disabled-temporarily" />
			</aui:a>
		</span>
	</aui:field-wrapper>

	<div class="aui-helper-hidden" id='<%= randomNamespace + "mergeFailCountDialogContentWrapper" %>'>
		<div class="content">
			<p>
				<liferay-ui:message arguments="<%= new Object[]{mergeFailCount} %>" key="the-propagation-has-been-disabled-temporarily-after-x-errors" />
			</p>

			<aui:button onClick='<%= randomNamespace + "resetMergeFailCount()" %>' value='<%= forceMergeNow ? "reset-merge-fail-count-and-merge-template" : "reset-merge-fail-count" %>' />
		</div>
	</div>

	<aui:script use="aui-base,aui-dialog">
		var warningElem = A.one("#<portlet:namespace /><%= randomNamespace %>mergeFailPopupButton");

		if(warningElem) {
			warningElem.on('click', function(){

				var dialogContent = A.one('#<%= randomNamespace %>mergeFailCountDialogContentWrapper .content');

				var options = {
					title: '<liferay-ui:message key="propagation-of-changes" />',
					bodyContent: dialogContent.html(),
					centered: true,
					width: 400,
					height: 150,
					modal: true
				};

				var popup = new A.Dialog(options).render();

				dialogContent.show();
			});
		}
	</aui:script>

	<aui:script>
		function <%= randomNamespace %>resetMergeFailCount() {
			<portlet:actionURL var="resetAndMergeURL">
				<portlet:param name="struts_action" value="/sites_admin/edit_site" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="privateLayoutSet" value="<%= String.valueOf(privateLayoutSet) %>" />
				<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()) %>" />
				<portlet:param name="forceMergeNow" value="<%= String.valueOf(forceMergeNow) %>" />
			</portlet:actionURL>

			document.<portlet:namespace />fm.method = 'post';
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.RESET_MERGE_FAIL_COUNT %>';
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= currentURL %>';
			submitForm(document.<portlet:namespace />fm, '<%= resetAndMergeURL %>');
		}
	</aui:script>
</c:if>