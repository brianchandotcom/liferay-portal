/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

AUI.add(
	'liferay-kaleo-designer-autocomplete-util',
	(A) => {
		var AArray = A.Array;

		var AutoCompleteUtil = {
			_INSTANCES: [],

			create(
				portletNamespace,
				inputNode,
				url,
				requestTemplate,
				resultTextLocator,
				selectFn
			) {
				var instance = this;

				if (!inputNode.ac) {
					inputNode.plug(A.Plugin.AutoComplete, {
						activateFirstItem: true,
						maxResults: 20,
						on: {
							select: selectFn,
						},
						requestTemplate:
							requestTemplate ||
							'&' + portletNamespace + 'keywords={query}',
						resultHighlighter: 'wordMatch',
						resultTextLocator: resultTextLocator || 'name',
						source: url,
					});

					instance._INSTANCES.push(inputNode.ac);
				}

				return inputNode.ac;
			},

			destroyAll() {
				var instance = this;

				var INSTANCES = instance._INSTANCES;

				AArray.invoke(INSTANCES, 'destroy');

				INSTANCES.length = 0;
			},
		};

		Liferay.KaleoDesignerAutoCompleteUtil = AutoCompleteUtil;
	},
	'',
	{
		requires: ['autocomplete', 'autocomplete-highlighters', 'datasource'],
	}
);
