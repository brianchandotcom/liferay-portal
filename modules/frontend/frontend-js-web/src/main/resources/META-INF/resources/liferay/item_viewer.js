AUI.add(
	'liferay-item-viewer',
	function(A) {
		var Do = A.Do;

		var Lang = A.Lang;

		var CSS_ACTIVE = 'active';

		var CSS_CAPTION = A.getClassName('image', 'viewer', 'caption');

		var CSS_ICON_MONOSPACED = 'icon-monospaced';

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

		var CSS_PREVIEW_TIMEOUT_MESSAGE = 'preview-timeout-message';

		var CSS_SIDENAV_CONTAINER = 'sidenav-container';

		var CSS_SIDENAV_MENU_SLIDER = 'sidenav-menu-slider';

		var STR_BLANK = '';

		var STR_DATA_METADATA_RENDERED = 'data-metadata-rendered';

		var STR_DOT = '.';

		var STR_RENDER_CONTROLS = 'renderControls';

		var STR_RENDER_SIDEBAR = 'renderSidebar';

		var STR_SRC_NODE = 'srcNode';

		var TPL_CLOSE = '<button class="close image-viewer-base-control image-viewer-close lfr-item-viewer-close" type="button"><span class="glyphicon glyphicon-chevron-left ' + CSS_ICON_MONOSPACED + '"></span><span>{0}</span></button>';

		var TPL_INFO_ICON = '<span class="glyphicon glyphicon-info-sign lfr-item-viewer-icon-info"></span>';

		var TPL_INFO_TAB_BODY = '<div class="{className} fade in tab-pane" id="{tabId}">{content}</div>';

		var TPL_INFO_TAB_BODY_CONTENT = '<h5>{h5}</h5><p>{p}</p>';

		var TPL_INFO_TAB_TITLE = '<li class="{className} col-xs-{columnSize}"><a aria-expanded="false" data-toggle="tab" href="#{tabId}">{tabTitle}</a></li>';

		var bootstrapColumns = 12;

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

					previewTimeout: {
						validator: Lang.isNumber,
						value: 5000
					},

					renderControls: {
						validator: Lang.isBoolean,
						value: true
					},

					renderSidebar: {
						validator: Lang.isBoolean,
						value: true
					},

					showPlayer: {
						value: false
					},

					zIndex: {
						value: 5
					}
				},

				EXTENDS: A.ImageViewer,

				NAME: 'image-viewer',

				NS: 'lfr-item-viewer',

				prototype: {
					TPL_CAPTION: '<p class="' + CSS_CAPTION + '"></p>',

					TPL_CONTROL_LEFT: '<a class="' + CSS_FOOTER_CONTROL + ' ' + CSS_FOOTER_CONTROL_LEFT_BASE + ' ' + CSS_FOOTER_CONTROL_LEFT + '" href="javascript:;">' +
						'<span class="glyphicon glyphicon-chevron-left ' + CSS_ICON_MONOSPACED + '"></span>' +
					'</a>',

					TPL_CONTROL_RIGHT: '<a class="' + CSS_FOOTER_CONTROL + ' ' + CSS_FOOTER_CONTROL_RIGHT_BASE + ' ' + CSS_FOOTER_CONTROL_RIGHT + '" href="javascript:;">' +
						'<span class="glyphicon glyphicon-chevron-right ' + CSS_ICON_MONOSPACED + '"></span>' +
					'</a>',

					TPL_IMAGE_CONTAINER: '<div class="closed ' + CSS_IMAGE_CONTAINER + ' ' + CSS_SIDENAV_CONTAINER + ' sidenav-right">' +
						'<div class="' + CSS_SIDENAV_MENU_SLIDER + '">' +
							'<div class="' + CSS_IMAGE_INFO + ' sidebar sidebar-inverse sidebar-menu">' +
								'<div class="sidebar-header"><ul class="nav nav-tabs product-menu-tabs"></ul></div>' +
								'<div class="sidebar-body"><div class="tab-content"></div></div>' +
							'</div>' +
						'</div>' +
						'<span class="glyphicon glyphicon-time text-muted ' + CSS_LOADING_ICON + '"></span>' +
						'<p class="fade preview-timeout-message text-muted">' + Liferay.Language.get('preview-image-is-taking-longer-than-expected') + '</p>' +
					'</div>',

					initializer: function() {
						var instance = this;

						instance.TPL_CLOSE = Lang.sub(
							TPL_CLOSE,
							[instance.get('btnCloseCaption')]
						);

						instance._displacedMethodHandles = [
							instance.after('visibleChange', instance._destroyPreviewTimer, instance),
							Do.after('_afterBindUI', instance, 'bindUI', instance),
							Do.after('_afterGetCurrentImage', instance, '_getCurrentImage', instance),
							Do.after('_afterShow', instance, 'show', instance),
							Do.after('_afterShowCurrentImage', instance, '_showCurrentImage', instance),
							Do.after('_bindSidebarEvents', instance, '_afterLinksChange', instance),
							Do.before('_beforeOnClickControl', instance, '_onClickControl', instance),
							Do.before('_beforeSyncInfoUI', instance, '_syncInfoUI', instance)
						];
					},

					updateCurrentImage: function(imageData) {
						var instance = this;

						var imageUrl = imageData.file.url;

						var image = instance._getCurrentImage();

						image.attr('src', imageUrl);

						var link = instance.get('links').item(instance.get('currentIndex'));

						var imageValue = {
							fileEntryId: imageData.file.fileEntryId,
							groupId: imageData.file.groupId,
							title: imageData.file.title,
							url: imageUrl,
							uuid: imageData.file.uuid
						};

						link.setData('value', JSON.stringify(imageValue));
					},

					_afterBindUI: function() {
						var instance = this;

						instance._eventHandles = instance._eventHandles.concat(instance._displacedMethodHandles);

						instance._bindSidebarEvents();
					},

					_afterGetCurrentImage: function(event) {
						var instance = this;

						if (instance._showPreviewError) {
							return new Do.AlterReturn(
								'Return error message node',
								instance.get(STR_SRC_NODE).one(STR_DOT + CSS_PREVIEW_TIMEOUT_MESSAGE)
							);
						}
					},

					_afterShow: function() {
						var instance = this;

						instance._showPreviewError = false;

						instance._timer = A.later(
							instance.get('previewTimeout'),
							instance,
							instance._showPreviewErrorMessage
						);
					},

					_afterShowCurrentImage: function() {
						var instance = this;

						var link = instance.get('links').item(instance.get('currentIndex'));

						var metadata = link.getData('metadata');

						var image = instance._getCurrentImage();

						if (metadata && !image.attr(STR_DATA_METADATA_RENDERED)) {
							instance._populateImageMetadata(image, metadata);

							image.attr(STR_DATA_METADATA_RENDERED, true);
						}
					},

					_beforeOnClickControl: function(event) {
						event.stopImmediatePropagation();
					},

					_beforeSyncInfoUI: function() {
						var instance = this;

						if (!instance.get(STR_RENDER_CONTROLS)) {
							return new Do.Halt();
						}
					},

					_bindSidebarEvents: function() {
						var instance = this;

						if (instance.get(STR_RENDER_SIDEBAR)) {
							var sidebarNode = AUI.$(STR_DOT + CSS_SIDENAV_CONTAINER);

							sidebarNode.sideNavigation(
								{
									content: sidebarNode.find('.image-viewer-base-image'),
									equalHeight: false,
									position: 'right',
									toggler: instance._infoIconEl.getDOMNode(),
									useDelegate: false,
									width: '300px'
								}
							);
						}
					},

					_destroyPreviewTimer: function() {
						var instance = this;

						if (instance._timer) {
							instance._timer.cancel();
							instance._timer = null;
						}
					},

					_getImageInfoNodes: function() {
						var instance = this;

						if (!instance._imageInfoNodes) {
							instance._imageInfoNodes = instance.get(STR_SRC_NODE).all(STR_DOT + CSS_IMAGE_INFO);
						}

						return instance._imageInfoNodes;
					},

					_onClickInfoIcon: function(event) {
						var instance = this;

						instance._getImageInfoNodes().toggle();
					},

					_populateImageMetadata: function(image, metadata) {
						var instance = this;

						var imageInfoNode = image.siblings(STR_DOT + CSS_SIDENAV_MENU_SLIDER).one(STR_DOT + CSS_IMAGE_INFO);

						var imageInfoNodeTabContent = imageInfoNode.one('.tab-content');
						var imageInfoNodeTabList = imageInfoNode.one('ul');

						metadata = JSON.parse(metadata);

						var tabsCount = metadata.groups.length;

						metadata.groups.forEach(
							function(group, index) {
								var groupId = A.guid();

								var tabTitleNode = A.Node.create(
									Lang.sub(
										TPL_INFO_TAB_TITLE,
										{
											className: index === 0 ? CSS_ACTIVE : STR_BLANK,
											columnSize: bootstrapColumns / tabsCount,
											tabId: groupId,
											tabTitle: group.title
										}
									)
								);

								imageInfoNodeTabList.append(tabTitleNode);

								var dataStr = group.data.reduce(
									function(previousValue, currentValue) {
										return previousValue + Lang.sub(
											TPL_INFO_TAB_BODY_CONTENT,
												{
													h5: currentValue.key,
													p: currentValue.value
												}
											);
									},
									STR_BLANK
								);

								var tabContentNode = A.Node.create(
									Lang.sub(
										TPL_INFO_TAB_BODY,
										{
											className: index === 0 ? CSS_ACTIVE : STR_BLANK,
											content: dataStr,
											tabId: groupId
										}
									)
								);

								imageInfoNodeTabContent.append(tabContentNode);
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

						if (instance.get(STR_RENDER_SIDEBAR)) {
							var infoIconEl = A.Node.create(TPL_INFO_ICON);

							container.append(infoIconEl);

							instance._infoIconEl = infoIconEl;
						}
					},

					_setLinks: function(val) {
						var instance = this;

						var links;

						if (val instanceof A.NodeList) {
							links = val;
						}
						else if (A.Lang.isString(val)) {
							links = A.all(val);
						}
						else {
							links = new A.NodeList([val]);
						}

						var sources = [];

						links.each(
							function(item, index) {
								sources.push(item.attr('href') || item.attr('data-href'));
							}
						);

						if (sources.length) {
							instance.set('sources', sources);
						}

						return links;
					},

					_showPreviewErrorMessage: function() {
						var instance = this;

						instance._showPreviewError = true;

						instance.get(STR_SRC_NODE).one('.' + CSS_LOADING_ICON).hide();

						instance.fire('animate');
					},

					_syncCaptionUI: function() {
						var instance = this;

						var links = instance.get('links');

						var link = links.item(instance.get('currentIndex'));

						var caption = link.attr('title') || link.attr('data-title');

						instance._captionEl.set('text', caption);
					}
				}
			}
		);

		A.LiferayItemViewer = LiferayItemViewer;
	},
	'',
	{
		requires: ['aui-component', 'aui-image-viewer']
	}
);