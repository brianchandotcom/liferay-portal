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
String documentTitle = GetterUtil.getString(request.getAttribute("file_entry_upper_tbar.jsp-documentTitle"));
FileEntry fileEntry = (FileEntry)request.getAttribute("file_entry_upper_tbar.jsp-fileEntry");
FileVersion fileVersion = (FileVersion)request.getAttribute("file_entry_upper_tbar.jsp-fileVersion");
%>

<div class="upper-tbar-container-fixed">
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
					<clay:button
						elementClasses="btn-outline-borderless btn-outline-secondary"
						icon="info-circle-open"
						id='<%= liferayPortletResponse.getNamespace() + "OpenContextualSidebar" %>'
						monospaced="true"
						size="sm"
						style="<%= false %>"
						title='<%= LanguageUtil.get(resourceBundle, "info") %>'
					/>
				</li>

				<c:if test="<%= dlViewFileVersionDisplayContext.isSharingLinkVisible() %>">
					<li class="d-none d-sm-flex tbar-item">
						<liferay-sharing:button
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileEntry.getFileEntryId() %>"
						/>
					</li>
				</c:if>

				<c:if test="<%= dlViewFileVersionDisplayContext.isDownloadLinkVisible() %>">
					<li class="d-none d-sm-flex tbar-item">

						<%
						Map<String, String> data = new HashMap<>();

						data.put("analytics-file-entry-id", String.valueOf(fileEntry.getFileEntryId()));
						%>

						<clay:link
							buttonStyle="primary"
							data="<%= data %>"
							elementClasses="btn-sm"
							href="<%= DLURLHelperUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK, false, true) %>"
							icon="download"
							label='<%= LanguageUtil.get(resourceBundle, "download") %>'
							title='<%= LanguageUtil.format(resourceBundle, "file-size-x", TextFormatter.formatStorageSize(fileVersion.getSize(), locale), false) %>'
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
</div>