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

<%@ include file="/availability_label/init.jsp" %>

<%
String availabilityLabel;
String availabilityLabelType;

if (GetterUtil.getInteger(stockQuantity) > 0) {
	if (GetterUtil.getBoolean(lowStock)) {
		availabilityLabel = "low-stock";
		availabilityLabelType = "warning";
	}
	else {
		availabilityLabel = "available";
		availabilityLabelType = "success";
	}
}
else {
	availabilityLabel = "out-of-stock";
	availabilityLabelType = "danger";
}
%>

<span class="label label-<%= availabilityLabelType %> m-0" data-text-cp-instance-availability>
	<span class="label-item label-item-expand">
		<%= LanguageUtil.get(request, availabilityLabel) %>
	</span>
</span>

<%
if (GetterUtil.getBoolean(willUpdate)) {
%>

	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as Events">
		function changeAvailability(event) {
			const cpInstance = event.cpInstance;
			const availabilityLabel = document.querySelector(
				'[data-text-cp-instance-availability]'
			);

			if (availabilityLabel) {
				const isAvailable = cpInstance.stockQuantity > 0;
				const availabilityLabelText = availabilityLabel.querySelector(
					'.label-item'
				);

				availabilityLabelText.innerHTML = Liferay.Language.get(
					isAvailable ? 'available' : 'out-of-stock'
				);

				const labelType = isAvailable ? 'success' : 'danger';

				availabilityLabel.className = 'label label-' + labelType + ' m-0';
			}
		}

		Liferay.on(Events.CP_INSTANCE_CHANGED, changeAvailability);
	</aui:script>

<%
}
%>