AUI.add(
	'ckeditor-inline',
	function(A) {
		var Lang = A.Lang,
			PositionAlign = A.WidgetPositionAlign,

			WIN = A.config.win,

			SELECTOR_NOTICE_INLINE_EDIT_TEXT = '.lfr-notice-inline-edit-text',

			TPL_NOTICE =
				'<div id={id} class="lfr-notice-inline-edit">' +
					'<span class="lfr-notice-inline-edit-text"></span>' +
					'<span class="lfr-notice-inline-edit-close" tab-index="0">{close}</span>' +
				'</div>',

			ALIGN = 'align',
			BODY_SCROLL_LISTENER = 'bodyScrollListener',
			BOUNDING_BOX = 'boundingBox',
			CKEDITOR_PREFIX = 'ckeditorPrefix',
			CKEDITOR_SUFFIX = 'ckeditorSuffix',
			EDITOR = 'editor',
			EDITOR_NAME = 'editorName',
			NOTICE_INSTANCE = 'noticeInstance',
			RESPONSE_DATA = 'responseData',
			VISIBLE = 'visible';

		var CKEditorInline = A.Component.create(
			{
				ATTRS: {
					ckeditorPrefix: {
						validator: Lang.isString,
						value: '#cke_'
					},

					ckeditorSuffix: {
						validator: Lang.isString,
						value: '_original'
					},

					editor: {
						validator: Lang.isObject
					},

					editorName: {
						validator: Lang.isString
					},

					toolbarTopOffset: {
						validator: Lang.isNumber,
						value: 30
					},

					saveURL: {
						validator: Lang.isString
					}
				},

				EXTENDS: A.Base,

				NAME: 'ckeditor-inline',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var editor = instance.get(EDITOR);

						instance._eventHandles = [
							editor.on('blur', A.bind(instance._onEditorBlur, instance)),

							editor.on('focus', A.bind(instance._onEditorFocus, instance)),

							editor.on('saveContent', A.bind(instance._saveContent, instance, false)),

							editor.on('restoreContent', A.bind(instance._restoreContent, instance))
						];
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						A.one('body').unplug(A.Plugin.ScrollInfo);

						if (instance._scrollListener) {
							instance._scrollListener.detach();
						}

						instance._getEditNotice().destroy();

						WIN.clearTimeout(instance._closeTimer);

						WIN.clearInterval(instance._saveInterval);
					},

					_afterSaveContentFailure: function(responseData, autosaved) {
						var instance = this;

						instance.get(EDITOR).resetDirty();

						var notice = instance._getEditNotice();

						var boundingBox = notice.get(BOUNDING_BOX);

						A.one('#' + instance._inlineEditNoticeId).replaceClass('lfr-notice-inline-edit', 'lfr-notice-inline-edit-error');

						boundingBox.one(SELECTOR_NOTICE_INLINE_EDIT_TEXT).html(Liferay.Language.get('the-draft-was-not-saved-successfully'));

						notice.show();

						instance._updateNoticePosition();

						instance._addCloseNoticeListener();
					},

					_afterSaveContentSuccess: function(responseData, autosaved) {
						var instance = this;

						instance.get(EDITOR).resetDirty();

						var notice = instance._getEditNotice();
						
						A.one('#' + instance._inlineEditNoticeId).replaceClass('lfr-notice-inline-edit-error', 'lfr-notice-inline-edit');

						var message = Liferay.Language.get('the-draft-was-saved-successfully-at-x');

						if (autosaved) {
							message = Liferay.Language.get('the-draft-was-autosaved-successfully-at-x');
						}

						message = Lang.sub(
							message,
							[
								new Date().toLocaleTimeString()
							]
						);

						notice.get(BOUNDING_BOX).one(SELECTOR_NOTICE_INLINE_EDIT_TEXT).html(message);

						notice.show();

						instance._updateNoticePosition();

						instance._addCloseNoticeListener();
					},

					_attachScrollListener: function() {
						var instance = this;

						var notice = instance._getEditNotice();

						var noticeNode = notice.get(BOUNDING_BOX);

						if (!noticeNode.getData(BODY_SCROLL_LISTENER)) {
							var body = A.one('body');

							body.plug(A.Plugin.ScrollInfo);

							instance._scrollListener = body.scrollInfo.on('scroll', A.bind(instance._updateNoticePosition, instance));

							noticeNode.setData(BODY_SCROLL_LISTENER, instance._scrollListener);
						}
					},

					_attachCloseListener: function() {
						var instance = this;

						var notice = instance._getEditNotice();

						var boundingBox = notice.get(BOUNDING_BOX);

						boundingBox.one('.lfr-notice-inline-edit-close').on('click', A.bind(notice.hide, notice));
					},

					_addCloseNoticeListener: function() {
						var instance = this;

						WIN.clearTimeout(instance._closeTimer);

						instance._closeTimer = WIN.setTimeout(
							function() {
								var notice = instance._getEditNotice();

								notice.hide();
							},
							CKEDITOR.config.closeNoticeTimeout
						);
					},

					_getEditNotice: function() {
						var instance = this;

						var triggerNode = A.one(instance.get(CKEDITOR_PREFIX) + instance.get(EDITOR_NAME));

						var editNotice;

						if (!instance._inlineEditNoticeId) {
							instance._inlineEditNoticeId = A.guid();

							editNotice = new A.Overlay(
								{
									bodyContent: Lang.sub(
										TPL_NOTICE,
										{
											close: Liferay.Language.get('close'),
											id: instance._inlineEditNoticeId
										}
									),
									visible: false,
									zIndex: triggerNode.getStyle('zIndex') + 2
								}
							).render();

							A.one('#' + instance._inlineEditNoticeId).setData(NOTICE_INSTANCE, editNotice);

							instance._attachCloseListener();
						}
						else {
							editNotice = A.one('#' + instance._inlineEditNoticeId).getData(NOTICE_INSTANCE);
						}

						return editNotice;
					},

					_onEditorBlur: function() {
						var instance = this;

						WIN.clearInterval(instance._saveInterval);

						instance._saveContent();
					},

					_onEditorFocus: function() {
						var instance = this;

						var originalContentNode = A.one('#' + instance.get(EDITOR_NAME) + instance.get(CKEDITOR_SUFFIX));

						if (!originalContentNode.text()) {
							originalContentNode.text(this.get(EDITOR).getData());
						}

						var notice = instance._getEditNotice();

						var noticeNode = notice.get(BOUNDING_BOX);

						if (notice.get(VISIBLE) && noticeNode.getData(EDITOR) !== instance.get(EDITOR_NAME)) {
							notice.set(VISIBLE, false);

							noticeNode.setData(BODY_SCROLL_LISTENER, null);

							if (instance._scrollListener) {
								instance._scrollListener.detach();
							}
						}

						instance._setNoticeEditor();

						instance._setSaveTimer();

						instance._attachScrollListener();

						instance.get(EDITOR).resetDirty();
					},

					_restoreContent: function() {
						var instance = this;

						var originalContentNode = A.one('#' + instance.get(EDITOR_NAME) + instance.get(CKEDITOR_SUFFIX));

						var originalContent = originalContentNode.text();

						instance.get(EDITOR).setData(originalContent);

						instance._saveContent();
					},

					_saveContent: function(autosaved) {
						var instance = this;

						if (instance.get(EDITOR).checkDirty()) {
							A.io.request(
								instance.get('saveURL'),
								{
									after: {
										failure: function() {
											var responseData = this.get(RESPONSE_DATA);

											instance._afterSaveContentFailure(responseData, autosaved);
										},
										success: function() {
											var responseData = this.get(RESPONSE_DATA);

											if (responseData.success) {
												instance._afterSaveContentSuccess(responseData, autosaved);
											}
											else {
												instance._afterSaveContentFailure(responseData, autosaved);
											}
										}
									},
									data: {
										content: instance.get(EDITOR).getData()
									},
									dataType: 'json'
								}
							);
						}
					},

					_setNoticeEditor: function() {
						var instance = this;

						var notice = instance._getEditNotice();

						var noticeNode = notice.get(BOUNDING_BOX);

						noticeNode.setData(EDITOR, instance.get(EDITOR_NAME));
					},

					_setSaveTimer: function() {
						var instance = this;

						WIN.clearInterval(instance._saveInterval);

						instance._saveInterval = WIN.setInterval(
							A.bind(instance._saveContent, instance, true),
							CKEDITOR.config.autoSaveTimeout
						);
					},

					_updateNoticePosition: function() {
						var instance = this;

						var notice = instance._getEditNotice();

						if (notice.get(VISIBLE)) {
							var editorToolbarNode = A.one(instance.get(CKEDITOR_PREFIX) + instance.get(EDITOR_NAME));

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
		requires: ['aui-base', 'overlay', 'node-scroll-info']
	}
);