AUI.add(
	'liferay-ddm-form-renderer-wizard',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var CSS_WIZARD_ITEMS = '.';

		var TPL_WIZARD_ITEM = '<li class="{state}">' +
			'<div class="progress-bar-title">{title}</div>' +
			'<div class="divider"></div>' +
			'<div class="progress-bar-step">{number}</div>' +
		'</li>';

		var Wizard = A.Component.create(
			{
				ATTRS: {
					items: {
						value: []
					},

					itemsNodeList: {
						valueFn: '_valueItemsNodeList'
					}
				},

				EXTENDS: A.Widget,

				HTML_PARSER: {
					items: function(boundingBox) {
						var instance = this;

						var items = [];

						boundingBox.all('li').each(
							function(itemNode) {
								var title = itemNode.one('.progress-bar-title').text();

								var state = itemNode.attr('class');

								items.push(
									{
										state: state,
										title: title
									}
								);
							}
						);

						return items;
					},
					itemsNodeList: function(boundingBox) {
						var instance = this;

						return boundingBox.all('li');
					}
				},

				NAME: 'liferay-ddm-form-renderer-wizard',

				UI_ATTRS: ['items'],

				prototype: {
					CONTENT_TEMPLATE: '<ul class="multi-step-progress-bar"></ul>',

					activate: function(index) {
						var instance = this;

						instance._setState(index, 'active');
					},

					clear: function(index) {
						var instance = this;

						instance._setState(index, '');
					},

					complete: function(index) {
						var instance = this;

						instance._setState(index, 'complete');
					},

					_getItemsNodeList: function(items) {
						var instance = this;

						return new A.NodeList(
							AArray.map(
								items,
								function(item, index) {
									return A.Node.create(
										Lang.sub(
											TPL_WIZARD_ITEM,
											{
												state: item.state,
												number: index + 1,
												title: item.title
											}
										)
									);
								}
							)
						);
					},

					_setState: function(index, state) {
						var instance = this;

						var items = instance.get('items');

						items[index].state = state;

						instance.set('items', items);
					},

					_uiSetItems: function(val) {
						var instance = this;

						var contentBox = instance.get('contentBox');

						contentBox.empty();

						contentBox.append(instance._getItemsNodeList(val));
					},

					_valueItemsNodeList: function() {
						var instance = this;

						return instance._getItemsNodeList(instance.get('items'));
					}
				}
			}
		);

		Liferay.namespace('DDM.Renderer').Wizard = Wizard;
	},
	'',
	{
		requires: ['aui-component', 'aui-node', 'widget']
	}
);