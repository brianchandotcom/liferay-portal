AUI.add(
	'liferay-kaleo-designer-autocomplete-util',
	function(A) {
		var AArray = A.Array;

		var AutoCompleteUtil = {
			create: function(portletNamespace, inputNode, url, requestTemplate, resultTextLocator, selectFn) {
				var instance = this;

				if (!inputNode.ac) {
					inputNode.plug(
						A.Plugin.AutoComplete,
						{
							activateFirstItem: true,
							maxResults: 20,
							on: {
								select: selectFn
							},
							requestTemplate: requestTemplate || '&' + portletNamespace + 'keywords={query}',
							resultHighlighter: 'wordMatch',
							resultTextLocator: resultTextLocator || 'name',
							source: url
						}
					);

					instance._INSTANCES.push(inputNode.ac);
				}

				return inputNode.ac;
			},

			destroyAll: function() {
				var instance = this;

				var INSTANCES = instance._INSTANCES;

				AArray.invoke(INSTANCES, 'destroy');

				INSTANCES.length = 0;
			},

			_INSTANCES: []
		};

		Liferay.KaleoDesignerAutoCompleteUtil = AutoCompleteUtil;
	},
	'',
	{
		requires: ['autocomplete', 'autocomplete-highlighters', 'datasource']
	}
);