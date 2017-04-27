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

<%@ include file="/admin/init.jsp" %>

<%
String displayStyle = ddlFormAdminDisplayContext.getDisplayStyle();

PortletURL portletURL = ddlFormAdminDisplayContext.getPortletURL();

portletURL.setParameter("displayStyle", displayStyle);
%>

<liferay-util:include page="/admin/search_bar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/admin/toolbar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteRecordSetIds" type="hidden" />

		<liferay-ui:search-container
			id="ddlRecordSet"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= ddlFormAdminDisplayContext.getRecordSetSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecordSet"
				cssClass="entry-display-style"
				keyProperty="recordSetId"
				modelVar="recordSet"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
					<portlet:param name="displayStyle" value="<%= displayStyle %>" />
				</portlet:renderURL>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-icon
							cssClass="asset-icon"
							icon="forms"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="<%= 2 %>"
							href="<%= rowURL %>"
							path="/admin/view_record_set_descriptive.jsp"
						/>

						<liferay-ui:search-container-column-jsp
							path="/admin/record_set_action.jsp"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							href="<%= rowURL %>"
							name="name"
							value="<%= HtmlUtil.escape(recordSet.getName(locale)) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="description"
							value="<%= HtmlUtil.escape(recordSet.getDescription(locale)) %>"
						/>

						<liferay-ui:search-container-column-date
							name="modified-date"
							value="<%= recordSet.getModifiedDate() %>"
						/>

						<liferay-ui:search-container-column-jsp
							path="/admin/record_set_action.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= ddlFormAdminDisplayContext.isShowAddRecordSetButton() %>">
	<portlet:renderURL var="addRecordSetURL">
		<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "new-form") %>' url="<%= addRecordSetURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<%@ include file="/admin/export_record_set.jspf" %>

<div class="publish-popover-content view-popover">
	<div class="form-group">
		<label><liferay-ui:message key="copy-url" /></label>
		<div class="input-group">
			<input class="form-control" id="<portlet:namespace />clipboardView" readOnly type="text" value="<%= ddlFormAdminDisplayContext.getPublishedFormURL() %>" />

			<div class="help-block"><liferay-ui:message key="copied-to-clipboard" /></div>

			<span class="input-group-btn">
				<button class="btn btn-default" data-clipboard data-target="#<portlet:namespace />clipboardView" type="button">
					<span class="publish-button-text">
						<liferay-ui:message key="copy" />
					</span>
					<span class="publish-button-success-icon">
						<svg class="lexicon-icon">
							<use xlink:href="<%= ddlFormAdminDisplayContext.getLexiconIconsPath() %>check" />
						</svg>
					</span>
				</button>
			</span>
		</div>
	</div>
</div>

<aui:script use="aui-popover,event-outside">
	var A = AUI();

	var popover = new A.Popover(
		{
			bodyContent: A.one('.publish-popover-content.view-popover'),
			constrain: false,
			cssClass: 'form-builder-publish-popover',
			position: 'left',
			visible: false,
			width: 500,
			zIndex: 999
		}
	).render();

	popover.set(
		'hideOn',[
			{
				eventName: 'key',
				keyCode: 'esc',
				node: A.getDoc()
			},
			{
				eventName: 'clickoutside',
				node: A.one('.form-builder-publish-popover')
			}
		]
	);

	popover.after("visibleChange", function(event) {
		if (event.prevVal) {
			var popoverContent = A.one('.publish-popover-content.view-popover');

			var formGroup = popoverContent.one('.form-group');

			formGroup.removeClass('has-error');
			formGroup.removeClass('has-success');

			var copyButton = popoverContent.one('.btn');

			copyButton.removeClass('btn-danger');
			copyButton.removeClass('btn-success');

			popoverContent.one('.publish-button-text').html(Liferay.Language.get('copy'));
		}
	});

	Liferay.on(
		'<portlet:namespace />copyFormURL',
		function(event) {

			if (popover.get('visible')) {
				popover.hide();
			}

			var url = event.url;

			var clipboardInput = A.one('#<portlet:namespace />clipboardView');

			clipboardInput.set('value', url);

			popover.set("align", {
				node: Liferay.Menu._INSTANCE._activeTrigger,
				points: [A.WidgetPositionAlign.TR, A.WidgetPositionAlign.TR]
			});

			popover.show();
		}
	);

	Liferay.on('destroyPortlet', function() {
		popover.destroy();
	});
</aui:script>

<aui:script require="metal-clipboard/src/Clipboard">
	var A = AUI();

	var viewClipboard = new metalClipboardSrcClipboard.default();

	viewClipboard.on('success', function() {
		var popoverContent = A.one('.publish-popover-content.view-popover');

		popoverContent.one('.form-group').addClass('has-success');
		popoverContent.one('.form-group').removeClass('has-error');

		popoverContent.one('.btn').addClass('btn-success');
		popoverContent.one('.btn').removeClass('btn-danger');

		popoverContent.one('.help-block').html('<liferay-ui:message key="copied-to-clipboard" />');
		popoverContent.one('.publish-button-text').html('<liferay-ui:message key="Copy" />');
	});

	viewClipboard.on('error', function() {
		var popoverContent = A.one('.publish-popover-content.view-popover');

		popoverContent.one('.form-group').addClass('has-error');
		popoverContent.one('.form-group').removeClass('has-success');

		popoverContent.one('.btn').addClass('btn-danger');
		popoverContent.one('.btn').removeClass('btn-success');

		popoverContent.one('.help-block').html('<liferay-ui:message key="sorry-something-wrong-happened" />');
		popoverContent.one('.publish-button-text').html('<liferay-ui:message key="retry" />');
	});

	Liferay.on('destroyPortlet', function() {
		viewClipboard.dispose();
	});
</aui:script>