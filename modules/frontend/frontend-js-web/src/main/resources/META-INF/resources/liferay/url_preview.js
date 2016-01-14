AUI.add(
	'liferay-url-preview',
	function(A) {
		var Lang = A.Lang;

		var TPL_HEADER = '<a href="#"><span class="glyphicon glyphicon-chevron-left icon-monospaced"></span></a> <span class="url-preview-title">{title}</span>';

		var UrlPreview = A.Component.create(
			{
				ATTRS: {
					title: {
						validator: Lang.isString
					},

					url: {
						validator: Lang.isString
					},

					width: {
						value: '900px'
					}
				},

				NAME: 'liferayurlpreview',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._id = A.guid();
						instance._eventHandles = [];
					},

					destructor: function() {
						var instance = this;

						var dialog = Liferay.Util.getWindow(instance._id);

						if (dialog) {
							dialog.destroy();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					close: function() {
						var instance = this;

						instance._getDialog().hide();
					},

					open: function() {
						var instance = this;

						instance._getDialog().show();
					},

					_getDialog: function() {
						var instance = this;

						var dialog = Liferay.Util.Window.getWindow(
							{
								dialog: {
									centered: true,
									cssClass: 'lfr-url-preview',
									destroyOnHide: true,
									draggable: false,
									headerContent: instance._getHeader(),
									toolbars: false,
									width: instance.get('width')
								},
								id: instance._id,
								uri: instance.get('url')
							}
						);

						dialog.get('maskNode').setStyle('opacity', 1);

						return dialog;
					},

					_getHeader: function() {
						var instance = this;

						var header = Lang.sub(
							TPL_HEADER,
								{
									title: instance.get('title')
								}
							);

						header = A.Node.create(header);

						var headerLink = header.one('a');

						instance._eventHandles.push(
							headerLink.on(
								'click',
								function(event) {
									event.preventDefault();
									instance.close();
								}
							)
						);

						return header;
					}
				}
			}
		);

		Liferay.UrlPreview = UrlPreview;
	},
	'',
	{
		requires: ['aui-component']
	}
);