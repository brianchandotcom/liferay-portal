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

						instance._eventHandles = [];
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();

						instance._getDialog().destroy();
					},

					close: function() {
						var instance = this;

						instance._getDialog().hide();
					},

					open: function() {
						var instance = this;

						instance._getDialog().show();
					},

					_afterDialogVisibilityChange: function() {
						var instance = this;

						instance._dialog = null;
					},

					_getDialog: function() {
						var instance = this;

						var dialog = instance._dialog;

						if (!dialog) {
							var header = instance._getHeader();

							dialog = Liferay.Util.Window.getWindow(
								{
									dialog: {
										after: {
											visibleChange: A.bind(instance._afterDialogVisibilityChange, instance)
										},
										centered: true,
										cssClass: 'lfr-url-preview',
										destroyOnHide: true,
										draggable: false,
										headerContent: header,
										toolbars: false,
										width: instance.get('width')
									},
									id: instance.name,
									uri: instance.get('url')
								}
							);

							instance._dialog = dialog;

							instance._styleDialogMask();
						}

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
					},

					_styleDialogMask: function() {
						var instance = this;

						var dialogMask = instance._getDialog().get('maskNode');

						dialogMask.setStyle('opacity', 1);
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