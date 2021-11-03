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

<%
SegmentsExperienceSelectorDisplayContext segmentsExperienceSelectorDisplayContext = new SegmentsExperienceSelectorDisplayContext(request);
%>

<li class="border-left border-secondary control-menu-nav-item ml-3 pl-3">
	<div class="dropdown">
		<button aria-expanded="false" aria-haspopup="true" class="btn btn-sm btn-unstyled dropdown-toggle" id="<portlet:namespace />dropdownToggle" type="button">
			<span class="align-items-center c-inner d-flex" tabindex="-1">
				<span class="mr-2 text-truncate">
					<%= segmentsExperienceSelectorDisplayContext.getSelectedSegmentsExperienceName() %>
				</span>

				<clay:icon
					cssClass="flex-shrink-0"
					symbol="caret-double"
				/>
			</span>
		</button>

		<ul aria-labelledby="<portlet:namespace />dropdownToggle" class="dropdown-menu" id="<portlet:namespace />dropdown">

			<%
			JSONArray segmentsExperiencesJSONArray = segmentsExperienceSelectorDisplayContext.getSegmentsExperiencesJSONArray();

			for (int i = 0; i < segmentsExperiencesJSONArray.length(); i++) {
				JSONObject segmentsExperiencesJSONObject = segmentsExperiencesJSONArray.getJSONObject(i);
			%>

				<li>
					<a class="border-0 dropdown-item list-group-item list-group-item-flex rounded-0" href="<%= segmentsExperiencesJSONObject.getString("url") %>">
						<div class="autofit-col autofit-col-expand">
							<p class="list-group-title text-truncate">
								<%= segmentsExperiencesJSONObject.getString("segmentsExperienceName") %>
							</p>

							<p class="list-group-text text-secondary text-truncate">
								<liferay-ui:message arguments='<%= segmentsExperiencesJSONObject.getString("segmentsEntryName") %>' key="segment-x" />
							</p>
						</div>

						<c:if test='<%= segmentsExperiencesJSONObject.getBoolean("active") %>'>
							<div class="autofit-col">
								<span class="label label-success m-0">
									<span class="label-item label-item-expand">
										<liferay-ui:message key="active" />
									</span>
								</span>
							</div>
						</c:if>
					</a>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</li>

<liferay-frontend:component
	module="js/ExperienceSelector"
/>