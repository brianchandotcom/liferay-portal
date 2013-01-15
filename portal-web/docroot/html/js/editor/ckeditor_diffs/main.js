AUI.add(
	'inline-editor-ckeditor',
	function(A) {
		var PositionAlign = A.WidgetPositionAlign;

		var CKCONFIG = CKEDITOR.config;

		var ALIGN = 'align';

		var BODY_SCROLL_LISTENER = 'bodyScrollListener';

		var BOUNDING_BOX = 'boundingBox';

		var EDITOR = 'editor';

		var EDITOR_NAME = 'editorName';

		var EDITOR_PREFIX = 'editorPrefix';

		var EDITOR_SUFFIX = 'editorSuffix';

		var VISIBLE = 'visible';

		var CKEditorInline = A.Component.create(
			{
				AUGMENTS: [Liferay.InlineEditorBase],

				EXTENDS: A.Base,

				NAME: 'inline-editor-ckeditor',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var editor = instance.get(EDITOR);

						instance._eventHandles = [
							editor.on('blur', instance._onEditorBlur, instance),
							editor.on('focus', instance._onEditorFocus, instance),
							editor.on('restoreContent', instance._restoreContent, instance),
							editor.on('saveContent', instance.saveContent, instance, false)
						];

						instance.after('destroy', instance._destructor, instance);

						instance.after(['saveContentFailure', 'saveContentSuccess'], instance._updateNoticePosition, instance);
					},

					isContentDirty: function() {
						var instance = this;

						return instance.get(EDITOR).checkDirty();
					},

					resetDirty: function() {
						var instance = this;

						instance.get(EDITOR).resetDirty();
					},

					_attachScrollListener: function() {
						var instance = this;

						var notice = instance.getEditNotice();

						var noticeNode = notice.get(BOUNDING_BOX);

						if (!noticeNode.getData(BODY_SCROLL_LISTENER)) {
							var body = A.getBody();

							body.plug(A.Plugin.ScrollInfo);

							instance._scrollListener = body.scrollInfo.on('scroll', instance._updateNoticePosition, instance);

							noticeNode.setData(BODY_SCROLL_LISTENER, instance._scrollListener);
						}
					},

					_destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						A.getBody().unplug(A.Plugin.ScrollInfo);

						if (instance._scrollListener) {
							instance._scrollListener.detach();
						}
					},

					_getAutoSaveTimeout: function() {
						return CKCONFIG.autoSaveTimeout;
					},

					_getCloseNoticeTimeout: function() {
						return CKCONFIG.closeNoticeTimeout;
					},

					_onEditorBlur: function() {
						var instance = this;

						instance._saveContentTask.cancel();

						if (instance.isContentDirty()) {
							instance.saveContent();
						}
					},

					_onEditorFocus: function() {
						var instance = this;

						var originalContentNode = A.one('#' + instance.get(EDITOR_NAME) + instance.get(EDITOR_SUFFIX));

						if (!originalContentNode.text()) {
							originalContentNode.text(this.get(EDITOR).getData());
						}

						var notice = instance.getEditNotice();

						var noticeNode = notice.get(BOUNDING_BOX);

						if (notice.get(VISIBLE) && noticeNode.getData(EDITOR) !== instance.get(EDITOR_NAME)) {
							notice.hide();

							noticeNode.setData(BODY_SCROLL_LISTENER, null);

							if (instance._scrollListener) {
								instance._scrollListener.detach();
							}
						}

						instance.startSaveContentTask();

						instance._attachScrollListener();

						instance.resetDirty();
					},

					_restoreContent: function() {
						var instance = this;

						var originalContentNode = A.one('#' + instance.get(EDITOR_NAME) + instance.get(EDITOR_SUFFIX));

						var originalContent = originalContentNode.text();

						instance.get(EDITOR).setData(originalContent);

						if (instance.isContentDirty()) {
							instance.saveContent();
						}
					},

					_updateNoticePosition: function() {
						var instance = this;

						var notice = instance.getEditNotice();

						if (notice.get(VISIBLE)) {
							var editorToolbarNode = A.one(instance.get(EDITOR_PREFIX) + instance.get(EDITOR_NAME));

							var editorToolbarVisible = editorToolbarNode.getStyle('display') !== 'none';

							var alignNode;

							if (editorToolbarVisible) {
								var noticePosition = PositionAlign.TL;
								var containerPostion = PositionAlign.BL;

								if (parseInt(editorToolbarNode.getStyle('top'), 10) > instance.get('toolbarTopOffset')) {
									noticePosition = PositionAlign.BL;
									containerPostion = PositionAlign.TL;
								}

								alignNode = {
									node: editorToolbarNode,
									points: [noticePosition, containerPostion]
								};

								notice.set(ALIGN, alignNode);
							}
							else {
								notice.set(ALIGN, null);

								var viewport = A.DOM.viewportRegion();

								notice.set('xy', [(viewport.right - viewport.left) / 2, viewport.top]);
							}
						}
					}
				}
			}
		);

		Liferay.CKEditorInline = CKEditorInline;
	},
	'',
	{
		requires: ['array-invoke', 'liferay-inline-editor-base', 'node-scroll-info', 'overlay', 'yui-later']
	}
);