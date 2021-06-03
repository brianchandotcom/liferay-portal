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

<%@ include file="/html/portal/init.jsp" %>

<%
JSONObject samlSloContextJSONObject = (JSONObject)request.getAttribute("SAML_SLO_CONTEXT");

JSONArray samlSloRequestInfosJSONArray = samlSloContextJSONObject.getJSONArray("samlSloRequestInfos");
%>

<style type="text/css">
	.portlet-msg-no-support-label {
		background-image: url(<%= themeDisplay.getPathThemeImages() %>/arrows/02_x.png);
	}

	.portlet-msg-timed-out-label {
		background-image: url(<%= themeDisplay.getPathThemeImages() %>/common/time.png);
	}

	.saml-sp {
		margin-bottom: 0.5em;
	}

	.saml-sp-label {
		background-position: 0 50%;
		background-repeat: no-repeat;
		font-weight: bold;
		padding: 3px 3px 3px 20px;
	}

	.saml-sp-retry {
		display: inline-block;
		margin-left: 10px;
	}
</style>

<h3>
	<liferay-ui:message key="signing-out-from-services" />
</h3>

<h4>
	<liferay-ui:icon image="activate" /> <liferay-ui:message key="please-do-not-leave-this-page-in-order-to-avoid-inconsistencies" />
</h4>

<div id="samlSloResults"></div>

<div class="hide" id="samlSloCompleteSignOut">
	<div class="portlet-msg-info">
		<liferay-ui:message arguments="<%= 5 %>" key="all-service-providers-are-processed.-continuing-sign-out-automatically-in-x-seconds" />
	</div>

	<a href="?cmd=finish" id="samlCompleteSignOutLink"><liferay-ui:message key="complete-sign-out" /></a>
</div>

<noscript>
	<div class="portlet-msg-info">
		<liferay-ui:message key="your-browser-does-not-support-javascript.-you-will-need-to-sign-out-manually-from-each-service-provider" />
	</div>

	<%
	for (int i = 0; i < samlSloRequestInfosJSONArray.length(); i++) {
		JSONObject samlSloRequestInfoJSONObject = samlSloRequestInfosJSONArray.getJSONObject(i);
	%>

		<div class="saml-sp">
			<a class="saml-sp-label" href="?cmd=logout&entityId=<%= samlSloRequestInfoJSONObject.getString("entityId") %>" target="_blank">
				<liferay-ui:message arguments='<%= samlSloRequestInfoJSONObject.getString("name") %>' key="sign-out-from-x" />
			</a>
		</div>

	<%
	}
	%>

	<div>
		<a href="?cmd=finish">
			<liferay-ui:message key="complete-sign-out" />
		</a>
	</div>
</noscript>

<aui:script use="aui-base,aui-io-request-deprecated,aui-template-deprecated">
	var confirmLogout = function() {
		return confirm('<liferay-ui:message key="leaving-this-window-might-leave-logout-unfinished" />');
	};

	var eventHandlers = [];

	var detachHandlers = function() {
		(new A.EventHandle(eventHandlers)).detach();
	};

	eventHandlers.push(
		A.getWin().on(
			'beforeunload',
			function(event) {
				event.preventDefault('<liferay-ui:message key="leaving-this-window-might-leave-logout-unfinished" />');
			}
		)
	);

	if (Liferay.SPA && Liferay.SPA.app) {
		eventHandlers.push(
			Liferay.on(
				'beforeNavigate',
				function(event) {
					if (!confirmLogout()) {
						event.originalEvent.preventDefault();
					}
					else {
						SAML.SLO.clearFinishTimeout();
					}
				}
			)
		);

		Liferay.once('endNavigate', detachHandlers);
	}

	A.one('#samlCompleteSignOutLink').on('click', detachHandlers);

	var MAP_ENTITY_STATUS = {
		2: {
			cssClass: 'portlet-msg-success-label',
			retry: false,
			title: '<%= UnicodeLanguageUtil.get(request, "single-sign-out-completed-successfully") %>'
		},
		3: {
			cssClass: 'portlet-msg-error-label',
			retry: true,
			title: '<%= UnicodeLanguageUtil.get(request, "single-sign-out-request-failed") %>'
		},
		4: {
			cssClass: 'portlet-msg-no-support-label',
			retry: false,
			title: '<%= UnicodeLanguageUtil.get(request, "this-service-provider-does-not-support-single-sign-out") %>'
		},
		5: {
			cssClass: 'portlet-msg-timed-out-label',
			retry: true,
			title: '<%= UnicodeLanguageUtil.get(request, "single-sign-out-request-timed-out") %>'
		},
		defaultStatus: {
			cssClass: 'portlet-msg-progress-label',
			retry: false,
			title: '<%= UnicodeLanguageUtil.get(request, "single-sign-out-in-progress") %>'
		}
	};

	var SAML = Liferay.namespace('SAML');

	var TPL_SAML_ENTITY = new A.Template(
		'<tpl for="items">',
			'<div class="saml-sp" id="samlSp{$i}">',
				'<span class="portlet-msg-progress-label saml-sp-label">{name}</span>',
				'<a class="hide saml-sp-retry" data-entityId="{entityId}" href="javascript:;"><%= UnicodeLanguageUtil.get(request, "retry") %></a>',
				'<iframe class="hide-accessible" src="?cmd=logout&entityId={entityId}"></iframe>',
			'</div>',
		'</tpl>'
	);

	SAML.SLO = {
		init: function(items) {
			var instance = this;

			var entities = instance._entities;
			var entityStatus = instance._entityStatus;

			items.forEach(
				function(item, index, collection) {
					var entityId = item.entityId;

					entities[entityId] = 'samlSp' + index;
					entityStatus[entityId] = 0;
				}
			);

			var outputNode = A.one('#samlSloResults');

			TPL_SAML_ENTITY.render(
				{
					items: items
				},
				outputNode
			);

			outputNode.delegate(
				'click',
				function(event) {
					instance.retryLogout(event.currentTarget.attr('data-entityId'));
				},
				'.saml-sp-retry'
			);

			instance._completeSignOut = A.one('#samlSloCompleteSignOut');

			instance.checkStatus();
		},

		checkStatus: function() {
			var instance = this;

			A.io.request(
				'?cmd=status',
				{
					dataType: 'JSON',
					on: {
						success: function(event) {
							var logoutPending = false;

							this.get('responseData.samlSloRequestInfos').forEach(
								function(item, index, collection) {
									logoutPending |= item.status < 2;

									instance.updateStatus(item);
								}
							);

							if (logoutPending) {
								setTimeout(A.bind('checkStatus', instance), 1000);
							}
							else {
								instance._completeSignOut.show();

								instance.finishTimeout = setTimeout(A.bind('finishLogout', instance), 5000);
							}
						}
					}
				}
			);
		},

		clearFinishTimeout: function() {
			var instance = this;

			clearTimeout(instance.finishTimeout);
		},

		finishLogout: function() {
			detachHandlers();

			location.href = '?cmd=finish';
		},

		retryLogout: function(entityId) {
			var instance = this;

			var entityNode = A.one('#' + instance._entities[entityId]);

			if (entityNode) {
				var defaultStatus = MAP_ENTITY_STATUS.defaultStatus;

				entityNode.one('.saml-sp-label').attr(
					{
						className: 'saml-sp-label ' + defaultStatus.cssClass,
						title: defaultStatus.title
					}
				);

				entityNode.one('.saml-sp-retry').hide();

				entityNode.one('iframe').set('src', '?cmd=logout&entityId=' + entityId);

				instance.checkStatus();
			}
		},

		updateStatus: function(samlSloRequestInfo) {
			var instance = this;

			var infoStatus = samlSloRequestInfo.status;

			var entityStatus = instance._entityStatus;

			var entityId = samlSloRequestInfo.entityId;

			var status = entityStatus[entityId];

			if (status != infoStatus) {
				entityStatus[entityId] = infoStatus;

				var entityNode = A.one('#' + instance._entities[entityId]);

				var statusDetails = MAP_ENTITY_STATUS[infoStatus] || MAP_ENTITY_STATUS.defaultStatus;

				entityNode.one('.saml-sp-label').attr(
					{
						className: 'saml-sp-label ' + statusDetails.cssClass,
						title: statusDetails.title
					}
				);

				entityNode.one('.saml-sp-retry').toggle(statusDetails.retry);
			}
		},

		_entities: {},
		_entityStatus: {}
	};

	Liferay.SAML.SLO.init(<%= samlSloRequestInfosJSONArray %>);
</aui:script>