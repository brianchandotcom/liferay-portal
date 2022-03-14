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
DLViewFileEntryDisplayContext dlViewFileEntryDisplayContext = (DLViewFileEntryDisplayContext)request.getAttribute(DLViewFileEntryDisplayContext.class.getName());

FileEntry fileEntry = dlViewFileEntryDisplayContext.getFileEntry();
FileVersion fileVersion = dlViewFileEntryDisplayContext.getFileVersion();
%>

<div class="upper-tbar-container-fixed">
	<div class="tbar upper-tbar">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<div class="tbar-section text-left">
						<h2 class="text-truncate-inline upper-tbar-title" title="<%= HtmlUtil.escapeAttribute(dlViewFileEntryDisplayContext.getDocumentTitle()) %>">
							<span class="text-truncate"><%= HtmlUtil.escape(dlViewFileEntryDisplayContext.getDocumentTitle()) %></span>
						</h2>

						<c:if test="<%= fileEntry.hasLock() || fileEntry.isCheckedOut() %>">
							<span class="inline-item inline-item-after state-icon">
								<aui:icon image="lock" markupView="lexicon" message="locked" />
							</span>
						</c:if>

						<c:if test="<%= dlViewFileEntryDisplayContext.isShared() %>">
							<span class="inline-item inline-item-after lfr-portal-tooltip state-icon" title="<%= LanguageUtil.get(request, "shared") %>">
								<aui:icon image="users" markupView="lexicon" message="shared" />
							</span>
						</c:if>
					</div>
				</li>
				<li class="tbar-item">
					<clay:button
						borderless="<%= true %>"
						data-qa-id="infoButton"
						displayType="secondary"
						icon="info-circle-open"
						id='<%= liferayPortletResponse.getNamespace() + "OpenContextualSidebar" %>'
						small="<%= true %>"
						title='<%= LanguageUtil.get(resourceBundle, "info") %>'
					/>
				</li>

				<c:if test="<%= dlViewFileEntryDisplayContext.isSharingLinkVisible() %>">
					<li class="d-none d-sm-flex tbar-item">
						<liferay-sharing:button
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileEntry.getFileEntryId() %>"
						/>
					</li>
				</c:if>

				<c:if test="<%= dlViewFileEntryDisplayContext.isDownloadLinkVisible() %>">
					<li class="d-none d-sm-flex tbar-item">
						<clay:link
							data-analytics-file-entry-id="<%= fileEntry.getFileEntryId() %>"
							data-analytics-file-entry-title="<%= fileEntry.getTitle() %>"
							displayType="primary"
							href="<%= DLURLHelperUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK, false, true) %>"
							icon="download"
							label="download"
							small="<%= true %>"
							title='<%= LanguageUtil.format(resourceBundle, "file-size-x", LanguageUtil.formatStorageSize(fileVersion.getSize(), locale), false) %>'
							type="button"
						/>
					</li>
				</c:if>

				<li class="tbar-item">
					<liferay-ui:menu
						menu="<%= dlViewFileEntryDisplayContext.getMenu() %>"
					/>
				</li>
			</ul>
		</clay:container-fluid>
	</div>
</div>