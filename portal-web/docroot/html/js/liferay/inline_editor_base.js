AUI.add(
	'liferay-inline-editor-base',
	function(A) {
		var Lang = A.Lang,

			SELECTOR_NOTICE_INLINE_EDIT_TEXT = '.lfr-notice-inline-edit-text',

			TPL_NOTICE =
				'<div id={id} class="lfr-notice-inline-edit">' +
					'<span class="lfr-notice-inline-edit-text"></span>' +
					'<span class="lfr-notice-inline-edit-close" tab-index="0">{close}</span>' +
				'</div>',

			BOUNDING_BOX = 'boundingBox',
			EDITOR = 'editor',
			EDITOR_NAME = 'editorName',
			EDITOR_PREFIX = 'editorPrefix',
			EDITOR_SUFFIX = 'editorSuffix',
			NOTICE_INSTANCE = 'noticeInstance',
			RESPONSE_DATA = 'responseData';

		function InlineEditorBase(config) {
			var instance = this;

			instance.publish(
				'saveContentFailure',
				{
					defaultFn: instance._defaultSaveContentFailure
				}
			);

			instance.publish(
				'saveContentSuccess',
				{
					defaultFn: instance._defaultSaveContentSuccess
				}
			);
		}

		InlineEditorBase.ATTRS = {
			autoSaveTimeout: {
				getter: '_getAutoSaveTimeout',
				validator: Lang.isNumber,
				value: 3000
			},

			closeNoticeTimeout: {
				getter: '_getCloseNoticeTimeout',
				validator: Lang.isNumber,
				value: 8000
			},

			editorPrefix: {
				validator: Lang.isString,
				value: '#cke_'
			},

			editorSuffix: {
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
		};

		InlineEditorBase.prototype = {
			destructor: function() {
				var instance = this;

				instance.getEditNotice().destroy();

				if (instance._closeNoticeTask) {
					instance._closeNoticeTask.cancel();
				}

				if (instance._saveContentTask) {
					instance._saveContentTask.cancel();
				}
			},

			getEditNotice: function() {
				var instance = this;

				var triggerNode = A.one(instance.get(EDITOR_PREFIX) + instance.get(EDITOR_NAME));

				var editNotice;

				if (!instance._inlineEditorNoticeId) {
					instance._inlineEditorNoticeId = A.guid();

					editNotice = new A.Overlay(
						{
							bodyContent: Lang.sub(
								TPL_NOTICE,
								{
									close: Liferay.Language.get('close'),
									id: instance._inlineEditorNoticeId
								}
							),
							visible: false,
							zIndex: triggerNode.getStyle('zIndex') + 2
						}
					).render();

					A.one('#' + instance._inlineEditorNoticeId).setData(NOTICE_INSTANCE, editNotice);

					instance._attachCloseListener();
				}
				else {
					editNotice = A.one('#' + instance._inlineEditorNoticeId).getData(NOTICE_INSTANCE);
				}

				return editNotice;
			},

			saveContent: function(autosaved) {
				var instance = this;

				A.io.request(
					instance.get('saveURL'),
					{
						after: {
							failure: function() {
								var responseData = this.get(RESPONSE_DATA);

								instance.fire('saveContentFailure', responseData, autosaved);
							},
							success: function() {
								var responseData = this.get(RESPONSE_DATA);

								if (responseData.success) {
									instance.fire('saveContentSuccess', responseData, autosaved);
								}
								else {
									instance.fire('saveContentFailure', responseData, autosaved);
								}
							}
						},
						data: {
							content: instance.get(EDITOR).getData()
						},
						dataType: 'json'
					}
				);
			},

			startCloseNoticeTask: function() {
				var instance = this;

				if (instance._closeNoticeTask) {
					instance._closeNoticeTask.cancel();
				}

				instance._closeNoticeTask = A.later(instance.get('closeNoticeTimeout'), instance, instance._closeNoticeTaskImpl);

				return instance._closeNoticeTask;
			},

			startSaveContentTask: function() {
				var instance = this;

				instance._saveContentTask = A.later(instance.get('autoSaveTimeout'), instance, instance._saveContentTaskImpl, [true], true);

				return instance._saveContentTask;
			},

			_attachCloseListener: function() {
				var instance = this;

				var notice = instance.getEditNotice();

				var boundingBox = notice.get(BOUNDING_BOX);

				boundingBox.one('.lfr-notice-inline-edit-close').on('click', A.bind(notice.hide, notice));
			},

			_closeNoticeTaskImpl: function() {
				var instance = this;

				instance.getEditNotice().hide();
			},

			_defaultSaveContentFailure: function(responseData, autosaved) {
				var instance = this;

				instance.resetDirty();

				var notice = instance.getEditNotice();

				var boundingBox = notice.get(BOUNDING_BOX);

				A.one('#' + instance._inlineEditorNoticeId).replaceClass('lfr-notice-inline-edit', 'lfr-notice-inline-edit-error');

				boundingBox.one(SELECTOR_NOTICE_INLINE_EDIT_TEXT).html(Liferay.Language.get('the-draft-was-not-saved-successfully'));

				notice.show();

				instance.startCloseNoticeTask();
			},

			_defaultSaveContentSuccess: function(responseData, autosaved) {
				var instance = this;

				instance.resetDirty();

				var notice = instance.getEditNotice();
				
				A.one('#' + instance._inlineEditorNoticeId).replaceClass('lfr-notice-inline-edit-error', 'lfr-notice-inline-edit');

				var message = Liferay.Language.get('the-draft-was-saved-successfully-at-x');

				if (autosaved) {
					message = Liferay.Language.get('the-draft-was-autosaved-successfully-at-x');
				}

				message = Lang.sub(
					message,
					[new Date().toLocaleTimeString()]
				);

				notice.get(BOUNDING_BOX).one(SELECTOR_NOTICE_INLINE_EDIT_TEXT).html(message);

				notice.show();

				instance.startCloseNoticeTask();
			},

			_saveContentTaskImpl: function(autosaved) {
				var instance = this;

				if (instance.isContentDirty()) {
					instance.saveContent(autosaved);
				}
			}
		};

		Liferay.InlineEditorBase = InlineEditorBase;
	},
	'',
	{
		requires: ['aui-base', 'overlay']
	}
);