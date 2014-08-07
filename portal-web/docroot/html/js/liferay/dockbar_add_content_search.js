AUI.add(
	'liferay-dockbar-add-content-search',
	function(A) {
		var AddContentSearch = function() {
		};

		AddContentSearch.prototype = {
			initializer: function(config) {
				var instance = this;

				var contentSearch = new Liferay.SearchFilter(
					{
						inputNode: instance.get('inputNode')
					}
				);

				instance._search = contentSearch;

				instance._bindUISearch();
			},

			_bindUISearch: function() {
				var instance = this;

				instance._eventHandles = instance._eventHandles || [];

				instance._eventHandles.push(
					instance._search.after('query', instance._refreshContentList, instance),
					instance.get('inputNode').on('keydown', instance._onSearchInputKeyDown, instance)
				);
			},

			_onSearchInputKeyDown: function(event) {
				if (event.isKey('ENTER')) {
					event.halt();
				}
			}
		};

		Liferay.Dockbar.AddContentSearch = AddContentSearch;
	},
	'',
	{
		requires: ['aui-base', 'liferay-dockbar', 'liferay-search-filter']
	}
);