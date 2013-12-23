AUI.add(
	'inline-editor-ckeditor',
	function(A) {
		var Lang = A.Lang;
		var PositionAlign = A.WidgetPositionAlign;

		var CKCONFIG = CKEDITOR.config;

		var ALIGN = 'align';

		var BOUNDING_BOX = 'boundingBox';

		var EDITOR = 'editor';

		var EDITOR_NAME = 'editorName';

		var EDITOR_PREFIX = 'editorPrefix';

		var EDITOR_SUFFIX = 'editorSuffix';

		var POINT_BL = PositionAlign.BL;

		var POINT_TL = PositionAlign.TL;

		var POINTS_WINDOW_CENTER = [POINT_TL, PositionAlign.TC];

		var VISIBLE = 'visible';

		var WIN = A.config.win;

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
							editor.on('saveContent', A.fn(0, 'save', instance))
						];

						instance.after('destroy', instance._destructor, instance);

						instance.after(['saveFailure', 'saveSuccess'], instance._updateNoticePosition, instance);
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

						if (!instance._scrollHandle) {
							instance._scrollHandle = A.getWin().on('scroll', instance._updateNoticePosition, instance);
						}
					},

					_destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'removeListener');

						if (instance._scrollHandle) {
							instance._scrollHandle.detach();
						}
					},

					_getAutoSaveTimeout: function() {
						var instance = this;

						var editor = instance.get(EDITOR);

						return editor.config.autoSaveTimeout;
					},

					_getCloseNoticeTimeout: function() {
						var instance = this;

						var editor = instance.get(EDITOR);

						return editor.config.closeNoticeTimeout;
					},

					_onEditorBlur: function() {
						var instance = this;

						instance.stopSaveTask();

						if (instance.isContentDirty()) {
							instance.save();
						}
					},

					_onEditorFocus: function() {
						var instance = this;

						var originalContentNode = A.one('#' + instance.get(EDITOR_NAME) + instance.get(EDITOR_SUFFIX));

						if (!originalContentNode.text()) {
							originalContentNode.text(this.get(EDITOR).getData());
						}

						var notice = instance.getEditNotice();

						if (notice.get(VISIBLE) && notice.get(BOUNDING_BOX).getData(EDITOR) !== instance.get(EDITOR_NAME)) {
							notice.hide();

							if (instance._scrollHandle) {
								instance._scrollHandle.detach();

								instance._scrollHandle = null;
							}
						}

						instance.startSaveTask();

						instance._attachScrollListener();

						instance.resetDirty();
					},

					_restoreContent: function() {
						var instance = this;

						var originalContentNode = A.one('#' + instance.get(EDITOR_NAME) + instance.get(EDITOR_SUFFIX));

						var originalContent = originalContentNode.text();

						instance.get(EDITOR).setData(originalContent);

						if (instance.isContentDirty()) {
							instance.save();
						}
					},

					_updateNoticePosition: function() {
						var instance = this;

						var notice = instance.getEditNotice();

						if (notice.get(VISIBLE)) {
							var editorToolbarNode = A.one(instance.get(EDITOR_PREFIX) + instance.get(EDITOR_NAME));

							var editorToolbarVisible = editorToolbarNode.getStyle('display') !== 'none';

							var align = {
								node: WIN,
								points: POINTS_WINDOW_CENTER
							};

							if (editorToolbarVisible) {
								var noticePosition = POINT_TL;
								var containerPostion = POINT_BL;

								if (Lang.toInt(editorToolbarNode.getStyle('top')) > instance.get('toolbarTopOffset')) {
									noticePosition = POINT_BL;
									containerPostion = POINT_TL;
								}

								align.node = editorToolbarNode;
								align.points = [noticePosition, containerPostion];
							}

							notice.set(ALIGN, align);
						}
					}
				}
			}
		);

		Liferay.CKEditorInline = CKEditorInline;
	},
	'',
	{
		requires: ['array-invoke', 'liferay-inline-editor-base', 'overlay', 'yui-later']
	}
);