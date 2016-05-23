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

		var STR_BLANK = '';

		var STR_DOT = '.';

		var STR_RENDER_CONTROLS = 'renderControls';

		var STR_RENDER_SIDEBAR = 'renderSidebar';

		var STR_SRC_NODE = 'srcNode';

		var TPL_CLOSE = '<button class="close image-viewer-base-control image-viewer-close lfr-item-viewer-close" type="button"><span class="' + CSS_ICON_MONOSPACED + '">' + Liferay.Util.getLexiconIconTpl('angle-left') + '</span><span class="lfr-item-viewer-close-text truncate-text">{0}</span></button>';

		var TPL_EDIT_DIALOG_TITLE = '{edit} {title} ({copy})';

		var TPL_EDIT_ICON = '<a class="lfr-item-viewer-icon-info-link" href="{editItemURL}" style="right: 60px;"><span class="' + CSS_ICON_MONOSPACED + ' lfr-item-viewer-icon-info">' + Liferay.Util.getLexiconIconTpl('pencil') + '</span></a>';

		var TPL_INFO_ICON = '<a class="lfr-item-viewer-icon-info-link" data-content=".image-viewer-focused" data-target=".image-viewer-sidenav" data-toggle="sidenav" data-type="fixed-push" href=""><span class="' + CSS_ICON_MONOSPACED + ' lfr-item-viewer-icon-info">' + Liferay.Util.getLexiconIconTpl('info-circle') + '</span></a>';

		var TPL_INFO_TAB_BODY = '<div class="{className} fade in tab-pane" id="{tabId}">{content}</div>';

		var TPL_INFO_TAB_BODY_CONTENT = '<dt class="{dtClassName}">{dt}</dt><dd class="{ddClassName}">{dd}</dd>';

		var TPL_INFO_TAB_TITLE = '<li class="{className}"><a aria-expanded="false" data-toggle="tab" href="#{tabId}">{tabTitle}</a></li>';

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

					editItemURL: {
						validator: Lang.isString
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

					uploadItemURL: {
						validator: Lang.isString
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
						'<span class="' + CSS_ICON_MONOSPACED + '">' + Liferay.Util.getLexiconIconTpl('angle-left') + '</span>' +
					'</a>',

					TPL_CONTROL_RIGHT: '<a class="' + CSS_FOOTER_CONTROL + ' ' + CSS_FOOTER_CONTROL_RIGHT_BASE + ' ' + CSS_FOOTER_CONTROL_RIGHT + '" href="javascript:;">' +
						'<span class="' + CSS_ICON_MONOSPACED + '">' + Liferay.Util.getLexiconIconTpl('angle-right') + '</span>' +
					'</a>',

					TPL_IMAGE_CONTAINER: '<div class="' + CSS_IMAGE_CONTAINER + '">' +
						'<p class="fade preview-timeout-message text-muted">' + Liferay.Language.get('preview-image-is-taking-longer-than-expected') + '</p>' +
					'</div>',

					TPL_SIDENAV: '<div class="closed image-viewer-sidenav sidenav-fixed sidenav-menu-slider sidenav-right">' +
						'<div class="image-viewer-sidenav-menu sidebar sidebar-default sidenav-menu">' +
							'<div class="sidebar-header">' +
								'<a class="' + CSS_ICON_MONOSPACED + ' image-viewer-sidenav-close sidenav-close visible-xs" href="">' + Liferay.Util.getLexiconIconTpl('times') + '</a>' +
								'<h4 class="image-viewer-sidenav-header">' +
									'<ul class="nav nav-tabs nav-tabs-default"></ul>' +
								'</h4>' +
							'</div>' +
							'<div class="image-viewer-sidenav-body sidebar-body">' +
								'<div class="tab-content"></div>' +
							'</div>' +
						'</div>' +
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

					renderUI: function() {
						var instance = this;

						LiferayItemViewer.superclass.renderUI.apply(this, arguments);

						if (instance.get(STR_RENDER_CONTROLS) && instance.get(STR_RENDER_SIDEBAR)) {
							instance._renderSidenav();
						}
					},

					appendNewLink: function(imageData) {
						var instance = this;

						var links = instance.get('links');

						var linkContainer = links.last().ancestor();

						var newLinkContainer = linkContainer.clone();

						var newLink = newLinkContainer.one('.item-preview');

						newLink.setAttribute('data-href', imageData.file.url);
						newLink.setAttribute('data-title', imageData.file.title);
						newLink.setAttribute('data-value', imageData.file.url);
						newLink.setAttribute('data-url', imageData.file.url);

						newLink.all('[style]').each(
							function(node) {
								var styleAttr = node.getAttribute('style');

								if (styleAttr) {
									styleAttr = styleAttr.replace(/\burl\s*\(\s*["']?http:\/\/((?:[^"'\r\n\/,]+)\/?)+["']?\s*\)/i, 'url("' + imageData.file.url + '")');

									node.setAttribute('style', styleAttr);
								}
							}
						);

						newLink.setData('metadata', JSON.stringify(instance._getUploadFileMetadata(imageData.file)));

						linkContainer.placeAfter(newLinkContainer);

						links.push(newLink);

						instance.updateCurrentImage(imageData, newLink);

						instance.set('links', links);

						instance.set('currentIndex', links.size() - 1);
					},

					updateCurrentImage: function(imageData, link) {
						var instance = this;

						link = link || instance.get('links').item(instance.get('currentIndex'));

						var imageURL = imageData.file.url;

						var image = instance._getCurrentImage();

						image.attr('src', imageURL);

						var returnType = link.attr('data-returnType');

						if (returnType === 'com.liferay.item.selector.criteria.URLItemSelectorReturnType') {
							link.setData('value', imageURL);
						}
						else {
							var imageValue = {
								fileEntryId: imageData.file.fileEntryId,
								groupId: imageData.file.groupId,
								title: imageData.file.title,
								type: imageData.file.type,
								url: imageURL,
								uuid: imageData.file.uuid
							};

							link.setData('value', JSON.stringify(imageValue));
						}
					},

					_afterBindUI: function() {
						var instance = this;

						instance._eventHandles = instance._eventHandles.concat(instance._displacedMethodHandles);

						instance._bindSidebarEvents();
					},

					_afterGetCurrentImage: function(event) {
						var instance = this;

						var retVal;

						if (instance._showPreviewError) {
							retVal = new Do.AlterReturn(
								'Return error message node',
								instance.get(STR_SRC_NODE).one(STR_DOT + CSS_PREVIEW_TIMEOUT_MESSAGE)
							);
						}

						return retVal;
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

						if (instance.get(STR_RENDER_CONTROLS) && metadata) {
							instance._populateImageMetadata(image, metadata);
						}
					},

					_beforeOnClickControl: function(event) {
						event.stopImmediatePropagation();
					},

					_beforeSyncInfoUI: function() {
						var instance = this;

						var retVal;

						if (!instance.get(STR_RENDER_CONTROLS)) {
							retVal = new Do.Halt();
						}

						return retVal;
					},

					_bindSidebarEvents: function() {
						var instance = this;

						if (instance.get(STR_RENDER_CONTROLS) && instance.get(STR_RENDER_SIDEBAR)) {
							AUI.$('[data-toggle="sidenav"]').sideNavigation();
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

					_getUploadFileMetadata: function(file) {
						var instance = this;

						return {
							'groups': [
								{
									'data': [
										{
											'key': Liferay.Language.get('format'),
											'value': file.type
										},
										{
											'key': Liferay.Language.get('name'),
											'value': file.title
										}
									],
									'title': Liferay.Language.get('file-info')
								}
							]
						};
					},

					_onClickEditIcon: function(event) {
						var instance = this;

						event.preventDefault();

						var item = instance.get('links').item(instance.get('currentIndex'));

						var itemTitle = item.getAttribute('data-title');
						var itemURL = item.getAttribute('data-url');

						var editDialogTitle = Lang.sub(
							TPL_EDIT_DIALOG_TITLE,
							{
								copy: Liferay.Language.get('copy'),
								edit: Liferay.Language.get('edit'),
								title: itemTitle
							}
						);

						Liferay.Util.editEntity(
							{
								dialog: {
									destroyOnHide: true,
									zIndex: Liferay.zIndex.WINDOW + 100
								},
								id: instance.get('id'),
								stack: false,
								title: editDialogTitle,
								uri: instance.get('editItemURL'),
								urlParams: {
									entityURL: itemURL,
									saveFileName: itemTitle,
									saveParamName: 'imageSelectorFileName',
									saveURL: instance.get('uploadItemURL')
								}
							},
							A.bind('_onSaveEditSuccess', instance)
						);
					},

					_onClickInfoIcon: function(event) {
						var instance = this;

						instance._getImageInfoNodes().toggle();
					},

					_onSaveEditSuccess: function(event) {
						var instance = this;

						instance.appendNewLink(event.data);
					},

					_populateImageMetadata: function(image, metadata) {
						var instance = this;

						var imageViewer = image.ancestor('.image-viewer');

						var sidenavTabContent = imageViewer.one('.image-viewer-sidenav .tab-content');
						var sidenavTabList = imageViewer.one('.image-viewer-sidenav ul');

						sidenavTabContent.all('.tab-pane').remove();
						sidenavTabList.all('li').remove();

						metadata = JSON.parse(metadata);

						metadata.groups.forEach(
							function(group, index) {
								var groupId = A.guid();

								var tabTitleNode = A.Node.create(
									Lang.sub(
										TPL_INFO_TAB_TITLE,
										{
											className: index === 0 ? CSS_ACTIVE : STR_BLANK,
											tabId: groupId,
											tabTitle: group.title
										}
									)
								);

								sidenavTabList.append(tabTitleNode);

								var dataStr = group.data.reduce(
									function(previousValue, currentValue) {
										return previousValue + Lang.sub(
											TPL_INFO_TAB_BODY_CONTENT,
											{
												dd: currentValue.value,
												ddClassName: '',
												dt: currentValue.key,
												dtClassName: 'h5'
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

								sidenavTabContent.append(tabContentNode);
							}
						);
					},

					_renderControls: function() {
						var imageViewerBase = A.one(STR_DOT + CSS_IMAGE_VIEWER_BASE);
						var imageViewerClose = A.one('.image-viewer-close');

						this._closeEl = A.Node.create(this.TPL_CLOSE);

						if (imageViewerBase && !imageViewerClose) {
							imageViewerBase.prepend(this._closeEl);
						}
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
							var controlsContainer = A.Node.create('<div class="image-viewer-footer-controls"></div>');
							var infoEl = A.Node.create(instance.TPL_INFO);

							controlsContainer.append(instance.get('controlPrevious'));

							infoEl.selectable();

							controlsContainer.append(infoEl);

							instance._infoEl = infoEl;

							controlsContainer.append(instance.get('controlNext'));

							container.append(controlsContainer);

							if (instance.get(STR_RENDER_SIDEBAR)) {
								var infoIconEl = A.Node.create(TPL_INFO_ICON);

								container.append(infoIconEl);

								instance._infoIconEl = infoIconEl;
							}

							if (instance.get('editItemURL')) {
								var editIconEl = A.Node.create(
									Lang.sub(
										TPL_EDIT_ICON,
										{
											editItemURL: instance.get('editItemURL')
										}
									)
								);

								editIconEl.on('click', A.bind('_onClickEditIcon', instance));

								container.append(editIconEl);

								instance._editIconEl = editIconEl;
							}
						}
					},

					_renderSidenav: function() {
						var instance = this;

						var imageViewerSidenav = A.Node.create(instance.TPL_SIDENAV);

						instance.get('contentBox').prepend(imageViewerSidenav);
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

						var loadingIcon = instance.get(STR_SRC_NODE).one('.' + CSS_LOADING_ICON);

						instance._showPreviewError = true;

						if (loadingIcon) {
							loadingIcon.hide();
						}

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
		requires: ['aui-component', 'aui-image-viewer', 'liferay-portlet-url']
	}
);