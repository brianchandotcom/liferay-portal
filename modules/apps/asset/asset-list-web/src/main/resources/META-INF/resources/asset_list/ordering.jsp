<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-frontend:fieldset
	disabled="<%= editAssetListDisplayContext.isLiveGroup() %>"
>
	<div>
		<react:component
			module="{CollectionOrdering} from asset-list-web"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"initialOrderByColumn1", editAssetListDisplayContext.getOrderByColumn1()
				).put(
					"initialOrderByColumn2", editAssetListDisplayContext.getOrderByColumn2()
				).put(
					"initialOrderByType1", editAssetListDisplayContext.getOrderByType1()
				).put(
					"initialOrderByType2", editAssetListDisplayContext.getOrderByType2()
				).put(
					"namespace", liferayPortletResponse.getNamespace()
				).put(
					"properties", editAssetListDisplayContext.getTypePropertiesJSONArray()
				).build()
			%>'
		/>
	</div>
</liferay-frontend:fieldset>