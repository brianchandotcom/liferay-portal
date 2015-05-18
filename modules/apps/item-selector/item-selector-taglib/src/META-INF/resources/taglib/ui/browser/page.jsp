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

<%@ include file="/taglib/ui/browser/init.jsp" %>

<%
String displayStyle = GetterUtil.getString(request.getAttribute("liferay-ui:item-selector-browser:displayStyle"), "descriptive");
String idPrefix = GetterUtil.getString(request.getAttribute("liferay-ui:item-selector-browser:idPrefix"));
String itemSelectedEventName = GetterUtil.getString(request.getAttribute("liferay-ui:item-selector-browser:itemSelectedEventName"));
ReturnType returnType = (ReturnType)request.getAttribute("liferay-ui:item-selector-browser:returnType");
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:item-selector-browser:searchContainer");
String tabName = GetterUtil.getString(request.getAttribute("liferay-ui:item-selector-browser:tabName"));
String uploadMessage = GetterUtil.getString(request.getAttribute("liferay-ui:item-selector-browser:uploadMessage"));
%>

<div class="lfr-item-viewer" id="<%= idPrefix %>ItemSelectorContainer">
	<c:if test="<%= ReturnType.BASE_64.equals(returnType) %>">
		<div class="drop-zone">
			<label class="btn btn-primary" for="<%= idPrefix %>InputFile"><liferay-ui:message key="select-file" /></label>

			<input class="hide" id="<%= idPrefix %>InputFile" type="file" />

			<p>
				<%= uploadMessage %>
			</p>
		</div>
	</c:if>

	<c:choose>
		<c:when test='<%= displayStyle.equals("list") %>'>
			<div class="list-content">
				<liferay-ui:search-container
					searchContainer="<%= searchContainer %>"
					total="<%= searchContainer.getTotal() %>"
					var="listSearchContainer"
				>
					<liferay-ui:search-container-results
						results="<%= searchContainer.getResults() %>"
					/>

					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.repository.model.FileEntry"
						keyProperty="fileEntryId"
						modelVar="fileEntry"
					>

						<%
						FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

						String title = DLUtil.getTitleWithExtension(fileEntry);
						%>

						<liferay-ui:search-container-column-text name="title">

							<%
							ObjectValuePair<String, String> returnTypeAndValue = returnType.getReturnTypeAndValue(fileEntry, themeDisplay);
							%>

							<a class="item-preview" data-returnType="<%= returnTypeAndValue.getKey() %>" data-url="<%= HtmlUtil.escapeAttribute(DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= returnTypeAndValue.getValue() %>" href="<%= HtmlUtil.escapeHREF(DLUtil.getImagePreviewURL(fileEntry, themeDisplay)) %>" title="<%= HtmlUtil.escapeAttribute(title) %>">

								<%
								String iconCssClass = DLUtil.getFileIconCssClass(fileEntry.getExtension());
								%>

								<c:if test="<%= Validator.isNotNull(iconCssClass) %>">
									<i class="<%= iconCssClass %>"></i>
								</c:if>

								<span class="taglib-text">
									<%= HtmlUtil.escape(title) %>
								</span>
							</a>

							<%@ include file="/taglib/ui/browser/metadata_view.jspf" %>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text name="size" value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>" />

						<liferay-ui:search-container-column-status name="status" status="<%= latestFileVersion.getStatus() %>" />

						<liferay-ui:search-container-column-text name="modified-date">
							<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - fileEntry.getModifiedDate().getTime(), true), HtmlUtil.escape(fileEntry.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
						</liferay-ui:search-container-column-text>

					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator />
				</liferay-ui:search-container>
			</div>
		</c:when>
		<c:otherwise>
			<ul class="tabular-list-group">

			<%
			for (Object result : searchContainer.getResults()) {
				FileEntry fileEntry = (FileEntry)result;

				FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

				String title = DLUtil.getTitleWithExtension(fileEntry);
			%>

				<li class="list-group-item list-group-item-default">
					<div class="list-group-item-field">
						<img src="<%= DLUtil.getThumbnailSrc(fileEntry, themeDisplay) %>" />
					</div>

					<div class="list-group-item-content">
						<div class="text-default">
							<liferay-ui:message key="modified" />
							<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - fileEntry.getModifiedDate().getTime(), true), HtmlUtil.escape(fileEntry.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
						</div>

						<div class="text-primary">

							<%
							ObjectValuePair<String, String> returnTypeAndValue = returnType.getReturnTypeAndValue(fileEntry, themeDisplay);
							%>

							<a class="item-preview" data-returnType="<%= returnTypeAndValue.getKey() %>" data-url="<%= HtmlUtil.escapeAttribute(DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= returnTypeAndValue.getValue() %>" href="<%= HtmlUtil.escapeHREF(DLUtil.getImagePreviewURL(fileEntry, themeDisplay)) %>" title="<%= HtmlUtil.escapeAttribute(title) %>">
								<%= HtmlUtil.escape(title) %>
							</a>

							<%@ include file="/taglib/ui/browser/metadata_view.jspf" %>
						</div>

						<div class="status text-default">
							<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(latestFileVersion.getStatus()) %>" />
						</div>
					</div>
				</li>

			<%
			}
			%>

			</ul>

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</c:otherwise>
	</c:choose>

	<liferay-ui:drop-here-info message="drop-files-here" />
</div>

<aui:script use="liferay-item-selector-browser">
	var itemBrowser = new Liferay.ItemSelectorBrowser(
		{
			closeCaption: '<%= UnicodeLanguageUtil.get(request, tabName) %>',
			rootNode: '#<%= idPrefix %>ItemSelectorContainer'
		}
	);

	itemBrowser.on(
		'selectedItem',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire('<%= itemSelectedEventName %>', event);
		}
	);

</aui:script>