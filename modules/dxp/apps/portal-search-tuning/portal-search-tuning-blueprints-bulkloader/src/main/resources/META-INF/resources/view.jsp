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

<portlet:actionURL name="<%= MVCActionCommandNames.IMPORT %>" var="importActionURL" />

<div class="container-md">
	<h1><liferay-ui:message key="bulk-loader-title" /></h1>

	<aui:form action="<%= importActionURL %>" name="importform">
		<aui:fieldset>
			<aui:select label="select-data-type" name="type">
				<aui:option label="all-places-data" value="<%= ImportTypeKeys.ALL_PLACES_DATA %>" />
				<aui:option label="restaurants" value="<%= ImportTypeKeys.RESTAURANTS %>" />
				<aui:option label="tourist-attractions" value="<%= ImportTypeKeys.TOURIST_ATTRACTIONS %>" />
				<aui:option label="wikipedia-articles" value="<%= ImportTypeKeys.WIKIPEDIA_ARTICLES %>" />
			</aui:select>

			<div class="hide" id="<portlet:namespace />wikifields">
				<aui:input label="wiki-articles" name="wikiArticles" />
				<aui:input label="count-of-articles" name="count" value="100" />

				<clay:alert
					message="long-contents-will-be-truncated"
					style="info"
					title="info"
				/>
			</div
		>
		</aui:fieldset>

		<aui:fieldset label="target-information">
			<aui:input label="list-of-target-article-creators" name="userIds" value="<%= themeDisplay.getUserId() %>">
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="list-of-target-groups" name="groupIds" required="<%= true %>" value="<%= themeDisplay.getScopeGroupId() %>">
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="target-language-id" name="languageId" required="<%= true %>" value="<%= themeDisplay.getLanguageId() %>">
				<aui:validator name="required" />
			</aui:input>
		</aui:fieldset>

		<aui:button-row>
			<aui:button cssClass="btn btn-primary" type="submit" value="import" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	AUI().ready((A) => {
		var importTypeElement = A.one('#<portlet:namespace />type');

		importTypeElement.on('change', function () {
			let value = this.val();
			let wikifields = A.one('#<portlet:namespace />wikifields');

			if (value == '<%=ImportTypeKeys.WIKIPEDIA_ARTICLES %>') {
				wikifields.removeClass('hide');
			}
			else {
				wikifields.addClass('hide');
			}
		});
	});
</aui:script>