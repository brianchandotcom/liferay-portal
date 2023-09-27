<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<div>
	<react:component
		module="document_library/js/checkin/Checkin.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"checkedOut", GetterUtil.getBoolean(request.getAttribute("edit_file_entry.jsp-checkedOut"))
			).put(
				"dlVersionNumberIncreaseValues",
				HashMapBuilder.<String, Object>put(
					"MAJOR", DLVersionNumberIncrease.MAJOR
				).put(
					"MINOR", DLVersionNumberIncrease.MINOR
				).put(
					"NONE", DLVersionNumberIncrease.NONE
				).build()
			).build()
		%>'
	/>
</div>