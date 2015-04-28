AUI.add(
	'liferay-item-viewer',
	function(A) {
		var Lang = A.Lang;

		var CSS_FOOTER_BUTTONS = A.getClassName('image', 'viewer', 'footer', 'buttons');

		var CSS_FOOTER_CONTROL = A.getClassName('image', 'viewer', 'footer', 'control');

		var CSS_FOOTER_CONTROL_LEFT = A.getClassName('image', 'viewer', 'footer', 'control', 'left');

		var CSS_FOOTER_CONTROL_RIGHT = A.getClassName('image', 'viewer', 'footer', 'control', 'rigth');

		var CSS_IMAGE_CONTAINER = A.getClassName('image', 'viewer', 'base', 'image', 'container');

		var CSS_IMAGE_CURRENT_CONTAINER = A.getClassName('image', 'viewer', 'base', 'current', 'image');

		var CSS_IMAGE_INFO = A.getClassName('image', 'viewer', 'base', 'image', 'info');

		var CSS_LOADING_ICON = A.getClassName('image', 'viewer', 'base', 'loading', 'icon');

		var EVENT_CURRENT_IMAGE = 'itemSelectorDialog:currentImage';

		var EVENT_TOGGLE_BUTTON = 'itemSelectorDialog:toggleButton';

		var TPL_CLOSE = '<button class="close image-viewer-base-control image-viewer-close lfr-item-viewer" type="button"><span class="glyphicon glyphicon-chevron-left"></span><h4>{0}</h4></button>';

		var TPL_CONTROL_LEFT = '<a href="#" class="' + CSS_FOOTER_CONTROL + ' ' + CSS_FOOTER_CONTROL_LEFT + '"><span class="glyphicon glyphicon-chevron-left"></span></a>';

		var TPL_CONTROL_RIGHT = '<a href="#" class="' + CSS_FOOTER_CONTROL + ' ' + CSS_FOOTER_CONTROL_RIGHT + '"><span class="glyphicon glyphicon-chevron-right"></span></a>';

		var TPL_FOOTER_BUTTONS = '<div class="' + CSS_FOOTER_BUTTONS + '"><span class="glyphicon glyphicon-info-sign"></span></div>';

		var TPL_IMAGE_CONTAINER = '<div class="' + CSS_IMAGE_CONTAINER + '"> <div class="' + CSS_IMAGE_INFO + '"></div>' +
			'<span class="glyphicon glyphicon-time ' + CSS_LOADING_ICON + '"></span></div>';

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

					height: {
						value: '75%'
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
					initializer: function() {
						var instance = this;

						instance.TPL_IMAGE_CONTAINER = TPL_IMAGE_CONTAINER;
					},

					renderUI: function() {
						var instance = this;

						A.ImageViewer.superclass.renderUI.apply(instance, arguments);

						instance._renderFooter();

						instance._fire(
							EVENT_TOGGLE_BUTTON,
							{
								disabled: true
							}
						);
					},

					bindUI: function() {
						var instance = this;

						LiferayItemViewer.superclass.bindUI.apply(instance, arguments);

						instance._eventHandles = [
							instance.footerNode.delegate('click', instance._onControlsClick, '.' + CSS_FOOTER_CONTROL, instance),
							instance._footerButtons.delegate('click', instance._onInfoClick, '.glyphicon-info-sign', instance),
							Liferay.after('showTab', instance._syncImageInfoUI, instance),
							A.after(instance._afterUIVisible, instance, '_uiSetVisible')
						];
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_afterUIVisible: function() {
						var instance = this;

						instance._fire(
							EVENT_TOGGLE_BUTTON,
							{
								disabled: !instance.get('visible')
							}
						);
					},

					_fire: function(event, details) {
						Liferay.Util.getOpener().Liferay.fire(event, details);
					},

					_onControlsClick: function(event) {
						var instance = this;

						event.preventDefault();
						event.stopImmediatePropagation();

						if (event.currentTarget.hasClass(CSS_FOOTER_CONTROL_LEFT)) {
							instance.prev();
						}
						else if (event.currentTarget.hasClass(CSS_FOOTER_CONTROL_RIGHT)) {
							instance.next();
						}
					},

					_onInfoClick: function(event) {
						var instance = this;

						instance.get('contentBox').all('.' + CSS_IMAGE_CONTAINER + ' .' + CSS_IMAGE_INFO).toggleClass('show-info');
					},

					_renderControls: function() {
						var instance = this;

						var body = A.one('body');

						var btnCloseTemplate = Lang.sub(TPL_CLOSE, [instance.get('btnCloseCaption')]);

						instance._closeEl = A.Node.create(btnCloseTemplate);
						body.append(instance._closeEl);
					},

					_renderFooter: function() {
						var instance = this;

						var container = A.Node.create(instance.TPL_FOOTER_CONTENT);

						instance.setStdModContent('footer', container);

						instance._captionEl = A.Node.create(instance.TPL_CAPTION);
						instance._captionEl.selectable();
						container.append(instance._captionEl);

						var renderControls = instance.get('renderControls');

						if (renderControls) {
							container.append(A.Node.create(TPL_CONTROL_LEFT));
						}

						instance._infoEl = A.Node.create(instance.TPL_INFO);
						instance._infoEl.selectable();
						container.append(instance._infoEl);

						if (renderControls) {
							container.append(A.Node.create(TPL_CONTROL_RIGHT));
						}

						instance._footerButtons = A.Node.create(TPL_FOOTER_BUTTONS);
						container.append(instance._footerButtons);
					},

					_showCurrentImage: function() {
						var instance = this;

						A.ImageViewer.superclass._showCurrentImage.apply(instance, arguments);

						instance._syncCaptionUI();
						instance._syncInfoUI();
						instance._syncImageInfoUI();

						var link = instance.get('links').item(instance.get('currentIndex'));

						instance._fire(
							EVENT_CURRENT_IMAGE,
							{
								url: link.getData('url') || link.getAttribute('href')
							}
						);
					},

					_syncImageInfoUI: function(event) {
						var instance = this;

						var link = instance.get('links').item(instance.get('currentIndex'));

						var infoHTML = link.siblings('.image-info') ? link.siblings('.image-info').html() : '';

						var imageInfoNode = instance.get('contentBox').one('.' + CSS_IMAGE_CURRENT_CONTAINER + ' .' + CSS_IMAGE_INFO);

						if (imageInfoNode && infoHTML) {
							imageInfoNode.html(infoHTML);
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