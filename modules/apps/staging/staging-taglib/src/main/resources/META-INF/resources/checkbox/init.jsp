<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
boolean checked = GetterUtil.getBoolean(request.getAttribute("liferay-staging:checkbox:checked"));
List<String> childLabelKeys = (List<String>)request.getAttribute("liferay-staging:checkbox:childLabels");
long deletions = GetterUtil.getLong(request.getAttribute("liferay-staging:checkbox:deletions"));
String descriptionKey = GetterUtil.getString(request.getAttribute("liferay-staging:checkbox:description"));
boolean disabled = GetterUtil.getBoolean(request.getAttribute("liferay-staging:checkbox:disabled"));
String id = GetterUtil.getString(request.getAttribute("liferay-staging:checkbox:id"));
boolean ignoreRequestValue = GetterUtil.getBoolean(request.getAttribute("liferay-staging:checkbox:ignoreRequestValue"));
long items = GetterUtil.getLong(request.getAttribute("liferay-staging:checkbox:items"));
String labelKey = GetterUtil.getString(request.getAttribute("liferay-staging:checkbox:label"));
String name = GetterUtil.getString(request.getAttribute("liferay-staging:checkbox:name"));
String popoverTextKey = GetterUtil.getString(request.getAttribute("liferay-staging:checkbox:popover"));
String suggestionKey = GetterUtil.getString(request.getAttribute("liferay-staging:checkbox:suggestion"));
String tagLabelKey = GetterUtil.getString(request.getAttribute("liferay-staging:checkbox:tagLabel"));
String warningKey = GetterUtil.getString(request.getAttribute("liferay-staging:checkbox:warning"));

if (Validator.isNull(id)) {
	id = name;
}

if (!ignoreRequestValue && Validator.isNotNull(ParamUtil.getString(request, "checkboxNames"))) {
	checked = ParamUtil.getBoolean(request, name);
}

String checkedString = checked ? "checked" : "";

String childLabels = StringPool.BLANK;

if (ListUtil.isNotEmpty(childLabelKeys)) {
	StringBundler sb = new StringBundler();

	for (String childLabelKey : childLabelKeys) {
		sb.append(LanguageUtil.get(request, childLabelKey));
		sb.append(", ");
	}

	if (sb.index() > 1) {
		sb.setIndex(sb.index() - 1);
	}

	childLabels = sb.toString();
}

String description = LanguageUtil.get(request, descriptionKey);
String disabledString = disabled ? "disabled" : "";
String domId = liferayPortletResponse.getNamespace() + id;
String domName = liferayPortletResponse.getNamespace() + name;
String label = LanguageUtil.get(request, labelKey);
String popoverName = name + "_popover";
String popoverText = Validator.isNull(popoverTextKey) ? " " : LanguageUtil.get(request, popoverTextKey);
String suggestion = LanguageUtil.get(request, suggestionKey);
String tagLabel = LanguageUtil.get(request, tagLabelKey);
String warning = LanguageUtil.get(request, warningKey);

String separator = Validator.isNotNull(description + suggestion + warning) ? ":" : "";
%>