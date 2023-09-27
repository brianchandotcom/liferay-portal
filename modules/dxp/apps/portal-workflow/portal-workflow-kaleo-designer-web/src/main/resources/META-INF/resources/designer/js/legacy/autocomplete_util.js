/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-kaleo-designer-autocomplete-util',
	(A) => {
		const AArray = A.Array;

		const AutoCompleteUtil = {
			_INSTANCES: [],

			create(
				portletNamespace,
				inputNode,
				url,
				requestTemplate,
				resultTextLocator,
				selectFn
			) {
				const instance = this;

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
				const instance = this;

				const INSTANCES = instance._INSTANCES;

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
