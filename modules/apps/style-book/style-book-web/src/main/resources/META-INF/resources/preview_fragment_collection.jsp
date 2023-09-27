<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PreviewFragmentCollectionDisplayContext previewFragmentCollectionDisplayContext = new PreviewFragmentCollectionDisplayContext(request);
%>

<div>
	<react:component
		module="js/fragment-collection-preview/FragmentCollectionPreview"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"fragmentCollectionKey", previewFragmentCollectionDisplayContext.getFragmentCollectionKey()
			).put(
				"fragments", previewFragmentCollectionDisplayContext.getFragmentsJSONArray()
			).put(
				"namespace", previewFragmentCollectionDisplayContext.getStyleBookPortletNamespace()
			).build()
		%>'
	/>
</div>