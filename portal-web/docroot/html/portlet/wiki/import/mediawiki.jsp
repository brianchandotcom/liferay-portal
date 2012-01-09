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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<aui:input name="filesCount" type="hidden" value="3" />

<liferay-ui:error exception="<%= PortalException.class %>" message="please-provide-all-mandatory-files-and-make-sure-the-file-types-are-valid" />

<aui:fieldset>
	<aui:input helpMessage="import-wiki-pages-help" label="pages-file" name="file0" type="file" />

	<aui:input helpMessage="import-wiki-users-help" label='<%= LanguageUtil.get(pageContext, "users-file") + "(" + LanguageUtil.get(pageContext, "optional") + ")" %>' name="file1" type="file" />

	<aui:input helpMessage="import-wiki-images-help" label='<%= LanguageUtil.get(pageContext, "images-file") + "(" + LanguageUtil.get(pageContext, "optional") + ")" %>' name="file2" type="file" />

	<aui:input label='<%= WikiPageConstants.FRONT_PAGE + "(" + LanguageUtil.get(pageContext, "optional") + ")" %>' name="<%= WikiImporterKeys.OPTIONS_FRONT_PAGE %>" type="text" size="40" value="Main Page" />

	<aui:input checked="<%= true %>" inlineLabel="left" label="import-only-the-latest-version-and-not-the-full-history" name="<%= WikiImporterKeys.OPTIONS_IMPORT_LATEST_VERSION %>" type="checkbox" />

	<aui:input checked="<%= true %>" helpMessage="import-wiki-strict-mode-help" inlineLabel="left" label="strict-mode" name="<%= WikiImporterKeys.OPTIONS_STRICT_IMPORT_MODE %>" type="checkbox" />
</aui:fieldset>