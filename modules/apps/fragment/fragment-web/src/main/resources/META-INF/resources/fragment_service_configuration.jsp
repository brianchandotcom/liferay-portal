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
FragmentServiceConfigurationDisplayContext fragmentServiceConfigurationDisplayContext = (FragmentServiceConfigurationDisplayContext)request.getAttribute(FragmentServiceConfigurationDisplayContext.class.getName());
%>

<aui:form action="<%= fragmentServiceConfigurationDisplayContext.getEditFragmentServiceConfigurationURL() %>" method="post" name="fm">
	<clay:sheet>
		<liferay-ui:error exception="<%= ConfigurationModelListenerException.class %>" message="there-was-an-unknown-error" />

		<clay:sheet-header>
			<h2>
				<liferay-ui:message key="fragment-configuration-name" />
			</h2>
		</clay:sheet-header>

		<clay:sheet-section>
			<div class="mb-3">
				<clay:checkbox
					checked="<%= fragmentServiceConfigurationDisplayContext.isPropagateContributedFragmentChangesEnabled() %>"
					id='<%= liferayPortletResponse.getNamespace() + "propagateContributedFragmentChanges" %>'
					label='<%= LanguageUtil.get(request, "propagate-contributed-fragment-changes-automatically") %>'
					name='<%= liferayPortletResponse.getNamespace() + "propagateContributedFragmentChanges" %>'
				/>

				<div aria-hidden="true" class="form-feedback-group">
					<div class="form-text text-weight-normal">
						<liferay-ui:message key="propagate-contributed-fragment-changes-automatically-description" />
					</div>
				</div>
			</div>

			<div>
				<clay:checkbox
					checked="<%= fragmentServiceConfigurationDisplayContext.isPropagateChangesEnabled() %>"
					id='<%= liferayPortletResponse.getNamespace() + "propagateChanges" %>'
					label='<%= LanguageUtil.get(request, "propagate-fragment-changes-automatically") %>'
					name='<%= liferayPortletResponse.getNamespace() + "propagateChanges" %>'
				/>

				<div aria-hidden="true" class="form-feedback-group">
					<div class="form-text text-weight-normal">
						<liferay-ui:message key="propagate-fragment-changes-automatically-description" />
					</div>
				</div>
			</div>
		</clay:sheet-section>

		<clay:sheet-footer>
			<aui:button primary="<%= true %>" type="submit" value="save" />

			<aui:button type="cancel" />
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>