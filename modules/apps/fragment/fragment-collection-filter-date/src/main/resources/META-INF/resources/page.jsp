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

<label class="control-label <%= fragmentCollectionFilterDateDisplayContext.isShowLabel() ? "" : "sr-only" %>" for="<portlet:namespace />dateInput">
	<%= fragmentCollectionFilterDateDisplayContext.getLabel() %>
</label>

<div className="date-picker">
	<div class="input-group">
		<div class="input-group-item">
			<input name="datePicker" type="hidden" value="" />

			<input class="form-control input-group-inset input-group-inset-after" id="<portlet:namespace />dateInput" placeholder="YYYY-MM-DD" type="text" value="" />

			<div class="input-group-inset-item input-group-inset-item-after">
				<clay:button
					displayType="unstyled"
					icon="date"
				/>
			</div>
		</div>
	</div>

	<react:component
		module="js/FragmentCollectionFilterDate"
		props="<%= fragmentCollectionFilterDateDisplayContext.getProps() %>"
	/>
</div>