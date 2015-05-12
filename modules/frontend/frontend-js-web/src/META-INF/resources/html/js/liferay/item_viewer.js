AUI.add(
	'liferay-item-viewer',
	function(A) {
		var Lang = A.Lang;

		var CSS_IMAGE_VIEWER = A.getClassName('image', 'viewer');

		var CSS_IMAGE_VIEWER_BASE = A.getClassName(CSS_IMAGE_VIEWER, 'base');

		var CSS_IMAGE_VIEWER_FOOTER = A.getClassName(CSS_IMAGE_VIEWER, 'footer');

		var CSS_FOOTER_CONTROL = A.getClassName(CSS_IMAGE_VIEWER_FOOTER, 'control');

		var CSS_FOOTER_CONTROL_LEFT = A.getClassName(CSS_IMAGE_VIEWER_FOOTER, 'control', 'left');

		var CSS_FOOTER_CONTROL_LEFT_BASE = A.getClassName(CSS_IMAGE_VIEWER_BASE, 'control', 'left');

		var CSS_FOOTER_CONTROL_RIGHT = A.getClassName(CSS_IMAGE_VIEWER_FOOTER, 'control', 'right');

		var CSS_FOOTER_CONTROL_RIGHT_BASE = A.getClassName(CSS_IMAGE_VIEWER_BASE, 'control', 'right');

		var CSS_IMAGE_CONTAINER = A.getClassName(CSS_IMAGE_VIEWER_BASE, 'image', 'container');

		var CSS_IMAGE_INFO = A.getClassName(CSS_IMAGE_VIEWER_BASE, 'image', 'info');

		var CSS_LOADING_ICON = A.getClassName(CSS_IMAGE_VIEWER_BASE, 'loading', 'icon');

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
					TPL_CONTROL_LEFT: '<a href="#" class="' + CSS_FOOTER_CONTROL + ' ' + CSS_FOOTER_CONTROL_LEFT_BASE + ' ' + CSS_FOOTER_CONTROL_LEFT + '">' +
						'<span class="glyphicon glyphicon-chevron-left"></span>' +
					'</a>',

					TPL_CONTROL_RIGHT: '<a href="#" class="' + CSS_FOOTER_CONTROL + ' ' + CSS_FOOTER_CONTROL_RIGHT_BASE + ' ' + CSS_FOOTER_CONTROL_RIGHT + '">' +
						'<span class="glyphicon glyphicon-chevron-right"></span>' +
					'</a>',

					TPL_IMAGE_CONTAINER: '<div class="' + CSS_IMAGE_CONTAINER + '">' +
						'<div class="' + CSS_IMAGE_INFO + '"></div>' +
						'<span class="glyphicon glyphicon-time ' + CSS_LOADING_ICON + '"></span>' +
					'</div>',

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

						var captionEl = A.Node.create(instance.TPL_CAPTION);

						captionEl.selectable();

						container.append(captionEl);

						instance._captionEl = captionEl;

						if (instance.get(STR_RENDER_CONTROLS)) {
							container.append(instance.get('controlPrevious'));

							var infoEl = A.Node.create(instance.TPL_INFO);

							infoEl.selectable();

							container.append(infoEl);

							instance._infoEl = infoEl;

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