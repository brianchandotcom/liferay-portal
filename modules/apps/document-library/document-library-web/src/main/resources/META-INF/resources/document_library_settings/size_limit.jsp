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

<clay:sheet>
	<clay:sheet-header>
		<h2>
			<liferay-ui:message key="dl-size-limit-configuration-name" />
		</h2>
	</clay:sheet-header>

	<clay:sheet-section>
		<aui:input label="file-max-size" name="fileMaxSize" value="<%= 0 %>" />

		<p class="text-muted">
			<liferay-ui:message key="file-max-size-help" />
		</p>
	</clay:sheet-section>

	<clay:sheet-section>
		<span aria-hidden="true" class="loading-animation"></span>

		<react:component
			module="document_library/js/file-size-limit/FileSizeMimetypes"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"portletNamespace", liferayPortletResponse.getNamespace()
				).build()
			%>'
		/>
	</clay:sheet-section>
</clay:sheet>