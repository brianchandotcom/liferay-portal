AUI.add(
	'liferay-menu-filter',
	function(A) {
		var Lang = A.Lang;
		var AArray = A.Array;
		var AEvent = A.Event;
		var Util = Liferay.Util;

		var CSS_HIDDEN = 'hidden';

		var STR_EMPTY = '';

		var TPL_INPUT_FILTER = '<li class="btn-toolbar search-panel">' +
			'<div class="control-group">' +
				'<input class="field focus menu-item-filter search-query span12" placeholder="{placeholder}" type="text">' +
			'</div>' +
		'</li>';

		var MenuFilter = A.Component.create(
			{
				NAME: 'menufilter',

				EXTENDS: A.Base,

				AUGMENTS: A.AutoCompleteBase,

				ATTRS: {
					content: {
						setter: A.one
					},

					inputNode: {
						validator: Lang.isString,
						value: '.menu-item-filter'
					},

					strings: {
						validator: Lang.isObject,
						value: {
							placeholder: 'Search'
						}
					}
				},

				prototype: {
					initializer: function() {
						var instance = this;

						instance._renderUI();
						instance._bindUIACBase();
						instance._syncUIACBase();
					},

					reset: function() {
						var instance = this;

						instance.get('inputNode').val(STR_EMPTY);

						instance._menuItems.removeClass(CSS_HIDDEN);
					},

					_renderUI: function() {
						var instance = this;

						var node = instance.get('content');

						var menuItems = node.all('li');

						node.prepend(
							Lang.sub(
								TPL_INPUT_FILTER,
								{
									placeholder: instance.get('strings').placeholder
								}
							)
						);

						instance._menuItems = menuItems;

						instance.on('results', instance._filterMenu, instance);
					},

					_filterMenu: function(event) {
						var instance = this;

						instance._menuItems.addClass(CSS_HIDDEN);

						AArray.each(
							event.results,
							function(result) {
								result.raw.node.removeClass(CSS_HIDDEN);
							}
						);
					}
				}
			}
		);

		Liferay.MenuFilter = MenuFilter;
	},
	'',
	{
		requires: ['aui-node', 'autocomplete-base', 'autocomplete-filters']
	}
);