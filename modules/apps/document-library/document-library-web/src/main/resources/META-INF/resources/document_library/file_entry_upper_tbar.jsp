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

<%
DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = (DLViewFileVersionDisplayContext)request.getAttribute("file_entry_upper_tbar.jsp-dlViewFileVersionDisplayContext");
FileEntry fileEntry = (FileEntry)request.getAttribute("file_entry_upper_tbar.jsp-fileEntry");
FileVersion fileVersion = (FileVersion)request.getAttribute("file_entry_upper_tbar.jsp-fileVersion");
boolean versionSpecific = GetterUtil.getBoolean(request.getAttribute("file_entry_upper_tbar.jsp-versionSpecific"));
%>

<liferay-util:buffer
	var="documentTitle"
>
	<%= fileVersion.getTitle() %>

	<c:if test="<%= versionSpecific %>">
		(<liferay-ui:message key="version" /> <%= fileVersion.getVersion() %>)
	</c:if>
</liferay-util:buffer>

<div class="tbar upper-tbar">
	<div class="container-fluid container-fluid-max-xl">
		<ul class="tbar-nav">
			<li class="tbar-item tbar-item-expand">
				<div class="tbar-section text-left">
					<h2 class="text-truncate-inline upper-tbar-title" title="<%= HtmlUtil.escapeAttribute(documentTitle) %>">
						<span class="text-truncate"><%= HtmlUtil.escape(documentTitle) %></span>
					</h2>

					<c:if test="<%= fileEntry.hasLock() || fileEntry.isCheckedOut() %>">
						<span>
							<aui:icon cssClass="icon-monospaced" image="lock" markupView="lexicon" message="locked" />
						</span>
					</c:if>
				</div>
			</li>
			<li class="tbar-item">
				<liferay-frontend:info-bar-sidenav-toggler-button
					cssClass="btn-sm"
					label="info"
				/>
			</li>
			<li class="tbar-item">
				<liferay-sharing:button
					className="<%= DLFileEntryConstants.getClassName() %>"
					classPK="<%= fileEntry.getFileEntryId() %>"
				/>
			</li>

			<c:if test="<%= dlViewFileVersionDisplayContext.isDownloadLinkVisible() %>">
				<li class="tbar-item">
					<clay:link
						buttonStyle="primary"
						elementClasses="btn-sm"
						href="<%= DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>"
						icon="download"
						label='<%= LanguageUtil.get(resourceBundle, "download") %>'
						title='<%= LanguageUtil.get(resourceBundle, "download") + " (" + TextFormatter.formatStorageSize(fileVersion.getSize(), locale) + ")" %>'
					/>
				</li>
			</c:if>

			<li class="tbar-item">
				<liferay-ui:menu
					menu="<%= dlViewFileVersionDisplayContext.getMenu() %>"
				/>
			</li>
		</ul>
	</div>
</div>