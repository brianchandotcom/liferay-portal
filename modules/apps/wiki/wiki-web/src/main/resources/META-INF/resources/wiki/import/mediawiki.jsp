<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<liferay-ui:error exception="<%= PortalException.class %>" message="please-provide-all-mandatory-files-and-make-sure-the-file-types-are-valid" />

<aui:fieldset markupView="lexicon">
	<aui:input helpMessage="import-wiki-pages-help" label="pages-file" name="file0" type="file" />

	<aui:input helpMessage="import-wiki-users-help" label='<%= LanguageUtil.get(request, "users-file") + "(" + LanguageUtil.get(request, "optional") + ")" %>' name="file1" type="file" />

	<aui:input helpMessage="import-wiki-images-help" label='<%= LanguageUtil.get(request, "images-file") + "(" + LanguageUtil.get(request, "optional") + ")" %>' name="file2" type="file" />

	<aui:input label='<%= wikiGroupServiceConfiguration.frontPageName() + "(" + LanguageUtil.get(request, "optional") + ")" %>' name="<%= WikiWebKeys.OPTIONS_FRONT_PAGE %>" size="40" type="text" value="Main Page" />

	<aui:input checked="<%= true %>" label="import-only-the-latest-version-and-not-the-full-history" name="<%= WikiWebKeys.OPTIONS_IMPORT_LATEST_VERSION %>" type="checkbox" />

	<aui:input checked="<%= true %>" helpMessage="import-wiki-strict-mode-help" label="strict-mode" name="<%= WikiWebKeys.OPTIONS_STRICT_IMPORT_MODE %>" type="checkbox" />
</aui:fieldset>