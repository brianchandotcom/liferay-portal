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

<%@ include file="/html/taglib/ui/search_toggle/init.jsp" %>

		<c:if test="<%= Validator.isNotNull(buttonLabel) %>">
			<aui:button type="submit" value="<%= buttonLabel %>" />
		</c:if>

		<div class="toggle-link">
			<aui:a href="javascript:;" tabindex="-1">&laquo; <liferay-ui:message key="basic" /></aui:a>
		</div>
	</div>
</div>

<aui:script>
	var <%= id %>curClickValue = "<%= clickValue %>";
</aui:script>

<aui:script use="liferay-store">
	var basicForm = A.one("#<%= id %>basic");
	var advancedForm = A.one("#<%= id %>advanced");

	var basicControls = basicForm.all('input:not(:submit), select');
	var advancedControls = advancedForm.all('input:not(:submit), select');

	if (<%= id %>curClickValue == "basic") {
		advancedControls.attr('disabled', 'disabled');
	}
	else {
		basicControls.attr('disabled', 'disabled');
	}

	A.all('.toggle-link a').on(
		'click',
		function() {
			basicForm.toggle();
			advancedForm.toggle();

			var advancedSearchObj = A.one("#<%= namespace %><%= id %><%= displayTerms.ADVANCED_SEARCH %>");

			if (<%= id %>curClickValue == "basic") {
				<%= id %>curClickValue = "advanced";

				advancedSearchObj.val(true);

				basicControls.attr('disabled', 'disabled');
				advancedControls.attr('disabled', '');
			}
			else {
				<%= id %>curClickValue = "basic";

				advancedSearchObj.val(false);

				basicControls.attr('disabled', '');
				advancedControls.attr('disabled', 'disabled');
			}

			Liferay.Store('<%= id %>', <%= id %>curClickValue);
		}
	);
</aui:script>