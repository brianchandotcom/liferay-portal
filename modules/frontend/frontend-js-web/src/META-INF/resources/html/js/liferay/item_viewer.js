AUI.add(
	'liferay-item-viewer',
	function(A) {
		var Lang = A.Lang;

		var CSS_ACTIVE = 'active';

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

		var STR_DOT = '.';

		var STR_RENDER_CONTROLS = 'renderControls';

		var TPL_CLOSE = '<button class="close image-viewer-base-control image-viewer-close lfr-item-viewer-close" type="button"><span class="glyphicon glyphicon-chevron-left"></span><span>{0}</span></button>';

		var TPL_INFO_ICON = '<span class="glyphicon glyphicon-info-sign lfr-item-viewer-icon-info"></span>';

		var TPL_INFO_LAYER = '<div class="tab-group"><ul class="nav nav-tabs"></ul><div class="tab-content"></div></div>';

		var TPL_INFO_LAYER_TAB_TITLE = '<li class="{className}"><a href="#{tabId}" data-toggle="tab" aria-expanded="false">{tabTitle}</a></li>';

		var TPL_INFO_LAYER_TAB_SECTION = '<div id="{tabId}" class="{className} fade in tab-pane"><dl>{content}</dl></div>';

		var TPL_INFO_LAYER_TAB_SECTION_CONTENT = '<dt>{dt}</dt><dd>{dd}</dd>';

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
						'<div class="' + CSS_IMAGE_INFO + ' hide"></div>' +
						'<span class="glyphicon glyphicon-time ' + CSS_LOADING_ICON + '"></span>' +
					'</div>',

					initializer: function() {
						var instance = this;

						instance.TPL_CLOSE = Lang.sub(
							TPL_CLOSE,
							[instance.get('btnCloseCaption')]
						);
					},

					bindUI: function() {
						var instance = this;

						LiferayItemViewer.superclass.bindUI.apply(this, arguments);

						instance._eventHandles.push(
							instance._infoIconEl.on('click', instance._onClickInfoIcon, instance),
							A.Do.after(instance._afterShowCurrentImage, instance, '_showCurrentImage', instance)
						);
					},

					_afterShowCurrentImage: function() {
						var instance = this;

						var link = instance.get('links').item(instance.get('currentIndex'));

						var metadata = link.getData('metadata');

						var image = this._getCurrentImage();

						if (!image.getData('metadata-rendered') && metadata) {
							instance._populateImageMetadata(image, metadata);

							image.setData('metadata-rendered', true);
						}
					},

					_getImageInfoNodes: function() {
						var instance = this;

						if (!instance._imageInfoNodes) {
							instance._imageInfoNodes = instance.get('srcNode').all(STR_DOT + CSS_IMAGE_INFO);
						}

						return instance._imageInfoNodes;
					},

					_onClickControl: function(event) {
						var instance = this;

						event.stopImmediatePropagation();

						LiferayItemViewer.superclass._onClickControl.apply(instance, arguments);
					},

					_onClickInfoIcon: function(event) {
						var instance = this;

						instance._getImageInfoNodes().toggle();
					},

					_populateImageMetadata: function(image, metadata) {
						var imageInfoNode = image.siblings(STR_DOT + CSS_IMAGE_INFO);

						imageInfoNode.setHTML(A.Node.create(TPL_INFO_LAYER));

						metadata = JSON.parse(metadata);

						metadata.groups.forEach(
							function(tab, index) {
								var tabId = A.guid();

								var tabTitleNode = A.Node.create(
									Lang.sub(
										TPL_INFO_LAYER_TAB_TITLE,
										{
											className: index === 0 ? CSS_ACTIVE : '',
											tabId: tabId,
											tabTitle: tab.title
										}
									)
								);

								imageInfoNode.one('ul').append(tabTitleNode);

								var dataStr = '';

								tab.data.forEach(
									function(data) {
										dataStr += Lang.sub(
											TPL_INFO_LAYER_TAB_SECTION_CONTENT,
											{
												dd: data.value,
												dt: data.key
											}
										);
									}
								);

								var tabContentNode = A.Node.create(
									Lang.sub(
										TPL_INFO_LAYER_TAB_SECTION,
										{
											className: index === 0 ? CSS_ACTIVE : '',
											content: dataStr,
											tabId: tabId
										}
									)
								);

								imageInfoNode.one(STR_DOT + 'tab-content').append(tabContentNode);
							}
						);
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

						var infoIconEl = A.Node.create(TPL_INFO_ICON);

						container.append(infoIconEl);

						instance._infoIconEl = infoIconEl;
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