AUI.add(
	'liferay-dockbar-add-page-search',
	function(A) {
		var AArray = A.Array;

		var Dockbar = Liferay.Dockbar;

		var AddSearch = Dockbar.AddSearch;

		var AddPageSearch = function() {
		};

		AddPageSearch.prototype = {
			initializer: function(config) {
				var instance = this;

				var pageSearch = new AddSearch(
					{
						inputNode: instance.get('inputNode'),
						source: instance.get('searchData')
					}
				);

				instance._search = pageSearch;

				instance._bindUIAPS();
			},

			_bindUIAPS: function() {
				var instance = this;

				instance._search.on('results', instance._udpateList, instance);

				instance.get('inputNode').on('keydown', instance._onSearchInputKeyDown, instance);
			},

			_onSearchInputKeyDown: function(event) {
				if (event.isKey('ENTER')) {
					event.halt();
				}
			},

			_udpateList: function(event) {
				var instance = this;

				var query = event.query;

				instance.get('nodes').toggle(!query || query === '*');

				if (query) {
					AArray.each(
						event.results,
						function(item, index, collection) {
							item.raw.node.show();
						}
					);
				}
			}
		};

		AddPageSearch.ATTRS = {
			inputNode: {
				setter: A.one
			},

			nodes: {
				getter: '_getNodes',
				readOnly: true
			},

			searchData: {
				getter: '_getSearchData',
				readOnly: true
			}
		};

		Dockbar.AddPageSearch = AddPageSearch;
	},
	'',
	{
		requires: ['aui-base', 'liferay-dockbar', 'liferay-dockbar-add-search']
	}
);