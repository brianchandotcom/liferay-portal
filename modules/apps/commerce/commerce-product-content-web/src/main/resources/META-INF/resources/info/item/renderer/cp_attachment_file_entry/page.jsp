<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info/item/renderer/cp_attachment_file_entry/init.jsp" %>

<dt>
	<div class="autofit-col my-auto">
		<aui:icon cssClass="icon-monospaced" image="document-default" markupView="lexicon" />
	</div>

	<div class="autofit-col autofit-col-expand">
		<h5><%= HtmlUtil.escape(cpMedia.getTitle()) %></h5>

		<p class="m-0"><%= LanguageUtil.formatStorageSize(cpMedia.getSize(), locale) %></p>
	</div>
</dt>
<dd>
	<div class="autofit-col my-auto">
		<clay:link
			borderless="<%= false %>"
			cssClass="btn-secondary"
			href="<%= cpMedia.getDownloadURL() %>"
			label='<%= LanguageUtil.get(request, "download") %>'
			target="_blank"
			type="button"
		/>
	</div>
</dd>