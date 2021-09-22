<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:renderURL var="searchSourcesURL">
	<portlet:param name="mvcPath" value="/admin/view.jsp" />
	<portlet:param name="tabs1" value="sources" />
</portlet:renderURL>

<%
String backURL = ParamUtil.getString(request, "backURL", searchSourcesURL);

Source source = (Source)request.getAttribute(ReportsEngineWebKeys.SOURCE);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((source != null) ? LanguageUtil.format(request, "edit-x", source.getName(locale), false) : LanguageUtil.get(request, "new-data-source"));
%>

<portlet:actionURL name="/reports_admin/edit_data_source" var="actionURL">
	<portlet:param name="mvcPath" value="/admin/data_source/edit_data_source.jsp" />
	<portlet:param name="redirect" value="<%= searchSourcesURL %>" />
</portlet:actionURL>

<aui:form action="<%= actionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<liferay-ui:error exception="<%= SourceDriverClassNameException.class %>" message="please-enter-a-valid-data-source-driver" />
	<liferay-ui:error exception="<%= SourceJDBCConnectionException.class %>" message="could-not-connect-to-the-database.-please-verify-that-the-settings-are-correct" />
	<liferay-ui:error exception="<%= SourceTypeException.class %>" message="please-enter-a-valid-data-source-type" />

	<aui:model-context bean="<%= source %>" model="<%= Source.class %>" />

	<aui:input name="sourceId" type="hidden" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<div class="form-group">
				<aui:input name="name" required="<%= true %>" />

				<aui:input label="jdbc-driver-class-name" name="driverClassName" required="<%= true %>" />

				<aui:input label="jdbc-url" name="driverUrl" required="<%= true %>" />

				<aui:input autocomplete="off" label="jdbc-user-name" name="driverUserName" required="<%= true %>" />

				<aui:input label="jdbc-password" name="driverPassword" type="password" />
			</div>
		</aui:fieldset>

		<c:if test="<%= source == null %>">
			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= Source.class.getName() %>"
				/>
			</aui:fieldset>
		</c:if>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" name="testDatabaseConnectionButton" onClick='<%= liferayPortletResponse.getNamespace() + "testDatabaseConnection();" %>' value="test-database-connection" />

		<aui:button cssClass="btn-lg" href="<%= searchSourcesURL %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	window['<portlet:namespace />testDatabaseConnection'] = function () {
		var baseUrl =
			'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/admin/data_source/test_database_connection.jsp" /></portlet:renderURL>';

		var data = {};

		<c:if test="<%= source != null %>">
			data.<portlet:namespace />sourceId =
				document.<portlet:namespace />fm['<portlet:namespace />sourceId'].value;
		</c:if>

		data.<portlet:namespace />driverClassName =
			document.<portlet:namespace />fm[
				'<portlet:namespace />driverClassName'
			].value;
		data.<portlet:namespace />driverUrl =
			document.<portlet:namespace />fm[
				'<portlet:namespace />driverUrl'
			].value;
		data.<portlet:namespace />driverUserName =
			document.<portlet:namespace />fm[
				'<portlet:namespace />driverUserName'
			].value;
		data.<portlet:namespace />driverPassword =
			document.<portlet:namespace />fm[
				'<portlet:namespace />driverPassword'
			].value;

		if (baseUrl != null) {
			var searchParams = new URLSearchParams(data);

			var url = new URL(baseUrl);

			searchParams.forEach((value, key) => {
				url.searchParams.append(key, value);
			});

			var id = '<portlet:namespace />databaseConnectionModal';

			Liferay.Util.fetch(url)
				.then((response) => {
					return response.text();
				})
				.then((text) => {
					Liferay.Util.openModal({
						bodyHTML: text,
						buttons: [
							{
								label: '<liferay-ui:message key="close" />',
								onClick: function () {
									Liferay.Util.getOpener().Liferay.fire(
										'closeModal',
										{
											id: id,
										}
									);
								},
							},
						],
						id: id,
						title: '<liferay-ui:message key="source" />',
					});
				})
				.catch((error) => {
					Liferay.Util.openToast({
						message: Liferay.Language.get(
							'an-unexpected-system-error-occurred'
						),
						type: 'danger',
					});
				});
		}
	};
</aui:script>