AUI.add(
	'liferay-url-preview',
	function(A) {
		var Lang = A.Lang;

		var TPL_HEADER = '<a href="javascript:;">' + Liferay.Util.getLexiconIconTpl('angle-left') + '</a>' +
			'&nbsp; <span class="url-preview-title">{title}</span>';

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

				EXTENDS: A.Base,

				NAME: 'liferayurlpreview',

				NS: 'liferayurlpreview',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._id = A.guid();

						instance.bindUI();
					},

					bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.on('titleChange', A.bind('_onTitleChange', instance)),
							instance.on('urlChange', A.bind('_onUrlChange', instance))
						];
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
									headerContent: instance._getHeader(instance.get('title')),
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

					_getHeader: function(title) {
						var instance = this;

						var header = Lang.sub(
							TPL_HEADER,
								{
									title: title
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

					_onTitleChange: function(event) {
						var instance = this;

						var dialog = Liferay.Util.getWindow(instance._id);

						if (dialog) {
							dialog.set('headerContent', instance._getHeader(event.newVal));
						}
					},

					_onUrlChange: function(event) {
						var instance = this;

						var dialog = Liferay.Util.getWindow(instance._id);

						if (dialog) {
							dialog.iframe.set('uri', event.newVal);
						}
					}
				}
			}
		);

		Liferay.UrlPreview = UrlPreview;
	},
	'',
	{
		requires: ['aui-component', 'liferay-util-window']
	}
);