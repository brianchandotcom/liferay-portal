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

<div className="frontend-js-components-sample-web">
	<h1>Translation Manager</h1>

	<h2>JSP Tag</h2>

	<i>JSP Samples</i>

	<h2>React Component</h2>

	<div>
		<react:component
			module="js/App"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"displayNames", LocaleUtil.toDisplayNames(LanguageUtil.getAvailableLocales(), locale)
				).put(
					"languageIds", LocaleUtil.toLanguageIds(LanguageUtil.getAvailableLocales())
				).build()
			%>'
		/>
	</div>
</div>