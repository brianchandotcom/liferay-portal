AUI.add(
	'liferay-item-viewer',
	function(A) {
		var Lang = A.Lang;

		var CSS_FOOTER_BUTTONS = A.getClassName('image', 'viewer', 'footer', 'buttons');

		var CSS_FOOTER_CONTROL = A.getClassName('image', 'viewer', 'footer', 'control');

		var CSS_FOOTER_CONTROL_LEFT = A.getClassName('image', 'viewer', 'footer', 'control', 'left');

		var CSS_FOOTER_CONTROL_LEFT_BASE = A.getClassName('image', 'viewer', 'base', 'control', 'left');

		var CSS_FOOTER_CONTROL_RIGHT = A.getClassName('image', 'viewer', 'footer', 'control', 'right');

		var CSS_FOOTER_CONTROL_RIGHT_BASE = A.getClassName('image', 'viewer', 'base', 'control', 'right');

		var CSS_IMAGE_CONTAINER = A.getClassName('image', 'viewer', 'base', 'image', 'container');

		var CSS_IMAGE_CURRENT_CONTAINER = A.getClassName('image', 'viewer', 'base', 'current', 'image');

		var CSS_IMAGE_INFO = A.getClassName('image', 'viewer', 'base', 'image', 'info');

		var CSS_LOADING_ICON = A.getClassName('image', 'viewer', 'base', 'loading', 'icon');

		var STR_RENDER_CONTROLS = 'renderControls';

		var TPL_CLOSE = '<button class="close image-viewer-base-control image-viewer-close lfr-item-viewer-close" type="button"><span class="glyphicon glyphicon-chevron-left"></span><span>{0}</span></button>';

		var LiferayItemViewer = A.Component.create(
			{
				ATTRS: {
					btnCloseCaption: {
						validator: Lang.isString,
						value: ''
					},

					circular: {
						value: true
					},

					infoTemplate: {
						value: '{current} of {total}'
					},

					playing: {
						value: false
					},

					renderControls: {
						validator: Lang.isBoolean,
						value: true
					},

					showPlayer: {
						value: false
					},

					zIndex: {
						value: 1
					}
				},

				EXTENDS: A.ImageViewer,

				NAME: 'image-viewer',

				NS: 'lfr-item-viewer',

				prototype: {
					TPL_CONTROL_LEFT: '<a href="#" class="' + CSS_FOOTER_CONTROL + ' ' + CSS_FOOTER_CONTROL_LEFT_BASE + ' ' + CSS_FOOTER_CONTROL_LEFT + '"><span class="glyphicon glyphicon-chevron-left"></span></a>',

					TPL_CONTROL_RIGHT: '<a href="#" class="' + CSS_FOOTER_CONTROL + ' ' + CSS_FOOTER_CONTROL_RIGHT_BASE + ' ' + CSS_FOOTER_CONTROL_RIGHT + '"><span class="glyphicon glyphicon-chevron-right"></span></a>',

					TPL_IMAGE_CONTAINER: '<div class="' + CSS_IMAGE_CONTAINER + '"> <div class="' + CSS_IMAGE_INFO + '"></div><span class="glyphicon glyphicon-time ' + CSS_LOADING_ICON + '"></span></div>',

					initializer: function() {
						var instance = this;

						instance.TPL_CLOSE = Lang.sub(
							TPL_CLOSE,
							[instance.get('btnCloseCaption')]
						);
					},

					_onClickControl: function(event) {
						var instance = this;

						event.stopImmediatePropagation();

						LiferayItemViewer.superclass._onClickControl.apply(instance, arguments);
					},

					_renderFooter: function() {
						var instance = this;

						var container = A.Node.create(instance.TPL_FOOTER_CONTENT);

						instance.setStdModContent('footer', container);

						instance._captionEl = A.Node.create(instance.TPL_CAPTION);
						instance._captionEl.selectable();
						container.append(instance._captionEl);

						if (instance.get(STR_RENDER_CONTROLS)) {
							container.append(instance.get('controlPrevious'));

							instance._infoEl = A.Node.create(instance.TPL_INFO);
							instance._infoEl.selectable();
							container.append(instance._infoEl);

							container.append(instance.get('controlNext'));
						}
					},

					_syncInfoUI: function() {
						var instance = this;

						if (instance.get(STR_RENDER_CONTROLS)) {
							LiferayItemViewer.superclass._syncInfoUI.apply(instance, arguments);
						}
					}
				}
			}
		);

		A.LiferayItemViewer = LiferayItemViewer;
	},
	'',
	{
		requires: ['aui-image-viewer']
	}
);