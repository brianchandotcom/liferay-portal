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

<%@ include file="/html/portal/api/jsonws/init.jsp" %>

<%
String contextPath = request.getParameter("contextPath");

String signature = ParamUtil.getString(request, "signature");

if (contextPath == null) {
	contextPath = ContextPathUtil.getContextPath(application);
}

List<JSONWebServiceActionMapping> jsonWebServiceActionMappings = JSONWebServiceActionsManagerUtil.getJSONWebServiceActionMappings(contextPath);
%>

<aui:input cssClass="lfr-api-service-search" label="" name="serviceSearch" placeholder="search" />

<div class="services" id="services">

	<%
	LinkedHashMap<String, LinkedHashSet> jsonWebServiceClasses = new LinkedHashMap<String, LinkedHashSet>();

	for (JSONWebServiceActionMapping jsonWebServiceActionMapping : jsonWebServiceActionMappings) {
		Class<?> actionClass = jsonWebServiceActionMapping.getActionClass();

		String actionClassName = actionClass.getSimpleName();

		if (actionClassName.endsWith("ServiceUtil")) {
			actionClassName = actionClassName.substring(0, actionClassName.length() - 11);
		}

		LinkedHashSet<JSONWebServiceActionMapping> jsonWebServiceMappings = jsonWebServiceClasses.get(actionClassName);

		if (Validator.isNull(jsonWebServiceMappings)) {
			jsonWebServiceMappings = new LinkedHashSet<JSONWebServiceActionMapping>();

			jsonWebServiceClasses.put(actionClassName, jsonWebServiceMappings);
		}

		jsonWebServiceMappings.add(jsonWebServiceActionMapping);
	}


	for (String jsonWebServiceClassName : jsonWebServiceClasses.keySet()) {
		LinkedHashSet<JSONWebServiceActionMapping> jsonWebServiceMappings = jsonWebServiceClasses.get(jsonWebServiceClassName);
	%>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='<%= "apiService" + jsonWebServiceClassName + "Panel" %>' persistState="<%= true %>" title="<%= jsonWebServiceClassName %>">
		<ul class="lfr-component">

			<%
			for (JSONWebServiceActionMapping jsonWebServiceActionMapping : jsonWebServiceMappings) {
				String path = jsonWebServiceActionMapping.getPath();

				int pos = path.lastIndexOf(CharPool.SLASH);

				path = path.substring(pos + 1);

				String serviceSignature = jsonWebServiceActionMapping.getSignature();
			%>
				<li class="lfr-api-signature <%= (serviceSignature.equals(signature)) ? "selected" : StringPool.BLANK %>">
					<a class="method-name lfr-api-service-result" data-metaData="<%= jsonWebServiceClassName %>" href="?signature=<%= serviceSignature %>">
						<%= path %>
					</a>
				</li>
			<%
			}
			%>

			</ul>
		</liferay-ui:panel>
	
	<%
	}
	%>

</div>

<div class="no-matches aui-helper-hidden" id="noMatches">
	<liferay-ui:message key="there-are-no-services-matching-that-phrase" />
</div>

<aui:script use="aui-base,autocomplete-base,autocomplete-filters,autocomplete-highlighters">
	var Lang = A.Lang;

	var AArray = A.Array;

	var ServiceFilter = A.Component.create(
		{
			AUGMENTS: [A.AutoCompleteBase],
			EXTENDS: A.Base,
			NAME: 'servicefilter',
			prototype: {
				initializer: function() {
					var instance = this;

					instance._bindUIACBase();
					instance._syncUIACBase();
				}
			}
		}
	);

	var noMatchMessage = A.one('#noMatches');
	var services = A.one('#services');

	var servicesClone = services.clone();

	var results = [];

	servicesClone.all('.lfr-api-service-result').each(
		function(item, index, collection) {
			results.push(
				{
					node: item,
					el: item._node,
					text: Lang.trim(item.text())
				}
			);
		}
	);

	var replaceRE = new RegExp('[-_\\s\\W]', 'g');

	var cache = {};

	var serviceSearch = A.one('#serviceSearch');

	var filter = new ServiceFilter(
		{
			inputNode: serviceSearch,
			minQueryLength: 0,
			queryDelay: 0,
			resultFilters: function(query, results) {
				query = query.toLowerCase().replace(replaceRE, '');

				return AArray.filter(
					results,
					function(item, index, collection) {
						var node = item.raw.node;
						var guid = node.guid();

						var text = cache[guid];

						if (!text) {
							text = (node.attr('data-metaData') + '/' + item.text);
							text = text.toLowerCase().replace(replaceRE, '');

							cache[guid] = text;
						}

						return text.indexOf(query) !== -1;
					}
				);
			},
			resultHighlighter: function(query, results) {
				var cachedResults = cache[query];

				if (!cachedResults) {
					var queryChars = AArray.dedupe(query.toLowerCase().split(''));

					cachedResults = AArray.map(
						results,
						function(item, index, collection) {
							return A.Highlight.all(item.text, queryChars);
						}
					);

					cache[query] = cachedResults;
				}

				return cachedResults;
			},
			resultTextLocator: 'text',
			source: results
		}
	);

	var servicesParent = services.ancestor();

	var trackedNodes;

	filter.on(
		'results',
		A.debounce(
			function(event) {
				var query = event.query;
				var results = event.results;

				var resultsLength = results.length;

				servicesClone.remove();
				services.remove();

				if (!trackedNodes) {
					trackedNodes = servicesClone.all('.lfr-panel, .lfr-api-signature');
				}

				trackedNodes.hide();

				var activeServiceNode = services;

				if (query) {
					AArray.each(
						results,
						function(item, index, collection) {
							var raw = item.raw;
							var el = raw.el;
							var node = raw.node;
							var serviceNode = raw.serviceNode;

							if (!serviceNode) {
								serviceNode = node.ancestorsByClassName('lfr-panel');

								raw.serviceNode = serviceNode;
							}

							if (node.hasClass('method-name')) {
								var signatureNode = raw.signatureNode;

								if (!signatureNode) {
									signatureNode = node.ancestorsByClassName('lfr-api-signature');

									raw.signatureNode = signatureNode;
								}

								signatureNode.show();

								var parentEl = el.parentNode;

								parentEl.removeChild(el);

								el.innerHTML = item.highlighted;

								parentEl.appendChild(el);
							}

							node.show();
							serviceNode.show();
						}
					);

					noMatchMessage.toggle(!resultsLength);

					activeServiceNode = servicesClone;
				}

				servicesParent.append(activeServiceNode);
			},
			50
		)
	);

	Liferay.Util.focusFormField(serviceSearch);
</aui:script>