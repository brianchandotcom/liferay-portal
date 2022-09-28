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