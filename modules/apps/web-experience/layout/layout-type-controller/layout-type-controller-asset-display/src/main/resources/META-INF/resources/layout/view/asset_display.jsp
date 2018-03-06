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

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(fragmentEntryLinks) %>">

		<%
		StringBundler sb = new StringBundler(fragmentEntryLinks.size());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			sb.append(FragmentEntryRenderUtil.renderFragmentEntryLink(fragmentEntryLink));
		}

		TemplateResource templateResource = new StringTemplateResource("template_id", sb.toString());

		Template template = TemplateManagerUtil.getTemplate(TemplateConstants.LANG_TYPE_FTL, templateResource, false);

		TemplateManager templateManager = TemplateManagerUtil.getTemplateManager(TemplateConstants.LANG_TYPE_FTL);

		templateManager.addTaglibSupport(template, request, response);
		templateManager.addTaglibTheme(template, "taglibLiferay", request, response);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.put(TemplateConstants.WRITER, unsyncStringWriter);

		template.processTemplate(unsyncStringWriter);
		%>

		<%= unsyncStringWriter.toString() %>
	</c:when>
	<c:otherwise>
		<div class="sheet">
			<div class="sheet-header">
				<h2 class="sheet-title">
					<%= assetEntry.getTitle(locale) %>
				</h2>

				<div class="sheet-text">
					<%= assetEntry.getDescription(locale) %>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>