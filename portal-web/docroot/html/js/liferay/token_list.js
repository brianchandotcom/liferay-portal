AUI().add(
	'liferay-token-list',
	function(A) {
		var Lang = A.Lang;

		var BUFFER = [];

		var TPL_TOKEN = A.Template(
			'<tpl for=".">',
				'<span class="lfr-token" data-fieldValues="{fieldValues}" data-clearFields="{clearFields}">',
					'<span class="lfr-token-text">{text:this.getTokenText}</span>',
					'<a class="icon icon-close lfr-token-close" href="javascript:;"></a>',
				'</span>',
			'</tpl>',
			{
				getTokenText: function(str, values) {
					if ('html' in values) {
						str = values.html;
					}
					else {
						str = A.Escape.html(str);
					}

					return str;
				}
			}
		);

		var TokenList = A.Component.create(
			{
				ATTRS: {
					children: {
						validator: Lang.isArray,
						value: []
					},
					cssClass: {
						value: 'lfr-token-list'
					}
				},

				NAME: 'liferaytokenlist',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._buffer = [];

						instance._addTokenTask = A.debounce(instance._addToken, 100);
					},

					renderUI: function() {
						var instance = this;

						instance.add(instance.get('children'));
					},

					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						boundingBox.delegate('click', instance._onClick, '.lfr-token-close', instance);

						instance.publish(
							'close',
							{
								defaultFn: A.bind('_defCloseFn', instance)
							}
						);
					},

					add: function(token) {
						var instance = this;

						if (token) {
							var buffer = instance._buffer;

							if (Lang.isArray(token)) {
								instance._buffer = buffer.concat(token);
							}
							else {
								buffer.push(token);
							}

							instance._addTokenTask();
						}
					},

					_addToken: function() {
						var instance = this;

						var buffer = instance._buffer;

						instance.get('contentBox').append(TPL_TOKEN.parse(buffer));

						buffer.length = 0;
					},

					_defCloseFn: function(event) {
						var instance = this;

						event.item.remove();
					},

					_onClick: function(event) {
						var instance = this;

						instance.fire(
							'close',
							{
								item: event.currentTarget.ancestor('.lfr-token')
							}
						);
					}
				}
			}
		);

		Liferay.TokenList = TokenList;
	},
	'',
	{
		requires: ['aui-base', 'aui-template-deprecated', 'escape']
	}
);