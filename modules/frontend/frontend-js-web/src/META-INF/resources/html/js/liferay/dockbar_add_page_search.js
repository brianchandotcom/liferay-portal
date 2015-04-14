AUI.add(
	'liferay-dockbar-add-page-search',
	function(A) {
		var AArray = A.Array;

		var AddPageSearch = function() {
		};

		AddPageSearch.prototype = {
			initializer: function(config) {
				var instance = this;

				var pageSearch = new Liferay.SearchFilter(
					{
						inputNode: instance.get('inputNode')
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
						function(item, index) {
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

		Liferay.Dockbar.AddPageSearch = AddPageSearch;
	},
	'',
	{
		requires: ['aui-base', 'liferay-dockbar', 'liferay-search-filter']
	}
);