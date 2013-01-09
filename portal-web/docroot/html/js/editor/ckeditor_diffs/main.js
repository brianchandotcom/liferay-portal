AUI.add(
	'inline-editor-ckeditor',
	function(A) {
		var PositionAlign = A.WidgetPositionAlign,

			CKCONFIG = CKEDITOR.config,

			ALIGN = 'align',
			BODY_SCROLL_LISTENER = 'bodyScrollListener',
			BOUNDING_BOX = 'boundingBox',
			EDITOR = 'editor',
			EDITOR_NAME = 'editorName',
			EDITOR_PREFIX = 'editorPrefix',
			EDITOR_SUFFIX = 'editorSuffix',
			VISIBLE = 'visible';

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
							editor.on('blur', A.bind(instance._onEditorBlur, instance)),
							editor.on('focus', A.bind(instance._onEditorFocus, instance)),
							editor.on('restoreContent', A.bind(instance._restoreContent, instance)),
							editor.on('saveContent', A.bind(instance.saveContent, instance, false))
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

							instance._scrollListener = body.scrollInfo.on('scroll', A.bind(instance._updateNoticePosition, instance));

							noticeNode.setData(BODY_SCROLL_LISTENER, instance._scrollListener);
						}
					},

					_destructor: function() {
						var instance = this;

						debugger;

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
							notice.set(VISIBLE, false);

							noticeNode.setData(BODY_SCROLL_LISTENER, null);

							if (instance._scrollListener) {
								instance._scrollListener.detach();
							}
						}

						instance._setNoticeEditor();

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

					_setNoticeEditor: function() {
						var instance = this;

						var notice = instance.getEditNotice();

						var noticeNode = notice.get(BOUNDING_BOX);

						noticeNode.setData(EDITOR, instance.get(EDITOR_NAME));
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
		requires: ['array-invoke', 'yui-later', 'overlay', 'node-scroll-info', 'liferay-inline-editor-base']
	}
);