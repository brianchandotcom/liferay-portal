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

<portlet:actionURL name="unsubscribe" var="unsubscribeURL" />

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="subscriptions" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<%
int subscriptionsCount = SubscriptionLocalServiceUtil.getUserSubscriptionsCount(user.getUserId());

PortletURL displayStyleURL = renderResponse.createRenderURL();

displayStyleURL.setParameter("mvcRenderCommandName", "/mysubscriptions/view");
%>

<liferay-frontend:management-bar
	disabled="<%= subscriptionsCount <= 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="subscriptions"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			navigationParam="entriesNavigation"
			portletURL="<%= displayStyleURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<aui:form action="<%= unsubscribeURL %>" method="get" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "unsubscribe();" %>'>
		<liferay-portlet:renderURLParams varImpl="portletURL" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="subscriptionIds" type="hidden" />

		<liferay-ui:error exception="<%= NoSuchSubscriptionException.class %>" message="the-subscription-could-not-be-found" />

		<liferay-ui:error-principal />

		<aui:fieldset>
			<liferay-portlet:renderURL varImpl="iteratorURL" />

			<liferay-ui:search-container
				deltaConfigurable="<%= true %>"
				emptyResultsMessage="no-subscriptions-were-found"
				id="subscriptions"
				iteratorURL="<%= iteratorURL %>"
				rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
				total="<%= subscriptionsCount %>"
			>
				<liferay-ui:search-container-results
					results="<%= SubscriptionLocalServiceUtil.getUserSubscriptions(user.getUserId(), searchContainer.getStart(), searchContainer.getEnd(), new SubscriptionClassNameIdComparator(true)) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.subscription.model.Subscription"
					escapedModel="<%= true %>"
					keyProperty="subscriptionId"
					modelVar="subscription"
				>

					<%
					AssetRenderer assetRenderer = MySubscriptionsUtil.getAssetRenderer(subscription.getClassName(), subscription.getClassPK());

					String rowURL = null;

					if (assetRenderer != null) {
						rowURL = assetRenderer.getURLViewInContext((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse, currentURL);
					}
					else {
						rowURL = MySubscriptionsUtil.getAssetURLViewInContext(themeDisplay, subscription.getClassName(), subscription.getClassPK());
					}
					%>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="title"
						value="<%= MySubscriptionsUtil.getTitleText(locale, subscription.getClassName(), subscription.getClassPK(), ((assetRenderer != null) ? assetRenderer.getTitle(locale) : null)) %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="asset-type"
						value="<%= ResourceActionsUtil.getModelResource(locale, subscription.getClassName()) %>"
					/>

					<liferay-ui:search-container-column-date
						href="<%= rowURL %>"
						name="create-date"
						value="<%= subscription.getCreateDate() %>"
					/>

					<liferay-ui:search-container-column-jsp
						align="right"
						cssClass="entry-action"
						path="/subscription_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator markupView="lexicon" resultRowSplitter="<%= new MySubscriptionsResultRowSplitter(locale) %>" />

				<c:if test="<%= !results.isEmpty() %>">
					<aui:button-row cssName="unsubscribe-button-row">
						<aui:button type="submit" value="unsubscribe" />
					</aui:button-row>
				</c:if>
			</liferay-ui:search-container>
		</aui:fieldset>
	</aui:form>
</div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />displayPopup',
		function(url, title) {
			var dialog = Liferay.Util.Window.getWindow(
				{
					dialog: {
						align: {
							node: null,
							points: ['tc', 'tc']
						},
						constrain2view: true,
						cssClass: 'portlet-my-subscription',
						modal: true,
						resizable: true,
						width: 950
					},
					title: title,
					uri: url
				}
			)
		},
		['liferay-util-window']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />unsubscribe',
		function() {
			document.<portlet:namespace />fm.method = 'post';
			document.<portlet:namespace />fm.<portlet:namespace />subscriptionIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>