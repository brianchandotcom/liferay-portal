<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
Map<String, Object> dlVersionNumberIncreaseValues = HashMapBuilder.<String, Object>put(
	"MAJOR", DLVersionNumberIncrease.MAJOR
).put(
	"NONE", DLVersionNumberIncrease.NONE
).build();

if (DLRepositoryUtil.isMinorVersionEnabled(GetterUtil.getLong(request.getAttribute("edit_file_entry.jsp-repositoryId")))) {
	dlVersionNumberIncreaseValues.put("MINOR", DLVersionNumberIncrease.MINOR);
}
%>

<div>
	<react:component
		module="{Checkin} from document-library-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"checkedOut", GetterUtil.getBoolean(request.getAttribute("edit_file_entry.jsp-checkedOut"))
			).put(
				"dlVersionNumberIncreaseValues", dlVersionNumberIncreaseValues
			).build()
		%>'
	/>
</div>