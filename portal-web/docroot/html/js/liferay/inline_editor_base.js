AUI.add(
	'liferay-inline-editor-base',
	function(A) {
		var Lang = A.Lang;

		var TPL_NOTICE =
			'<div class="lfr-notice-inline-edit" id="{id}">' +
				'<span class="lfr-notice-inline-edit-text"></span>' +
				'<span class="lfr-notice-inline-edit-close" tab-index="0">{close}</span>' +
			'</div>';

		var BOUNDING_BOX = 'boundingBox';

		var EDITOR = 'editor';

		var EDITOR_NAME = 'editorName';

		var EDITOR_PREFIX = 'editorPrefix';

		var EDITOR_SUFFIX = 'editorSuffix';

		var NOTICE_INSTANCE = 'noticeInstance';

		var RESPONSE_DATA = 'responseData';

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

				var editNotice = instance._editNotice;

				if (!editNotice) {
					var triggerNode = A.one(instance.get(EDITOR_PREFIX) + instance.get(EDITOR_NAME));

					var inlineEditorNoticeId = A.guid();

					editNotice = new A.Overlay(
						{
							bodyContent: Lang.sub(
								TPL_NOTICE,
								{
									close: Liferay.Language.get('close'),
									id: inlineEditorNoticeId
								}
							),
							visible: false,
							zIndex: triggerNode.getStyle('zIndex') + 2
						}
					).render();

					var editNoticeBoundingBox = editNotice.get('boundingBox');

					instance._editNoticeNode = editNoticeBoundingBox.one('#' + inlineEditorNoticeId);

					instance._editNoticeTextNode = editNoticeBoundingBox.one('.lfr-notice-inline-edit-text');

					instance._editNotice = editNotice;

					instance._attachCloseListener();
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

				var closeNoticeTask = instance._closeNoticeTask;

				if (closeNoticeTask) {
					closeNoticeTask.cancel();
				}

				closeNoticeTask = A.later(instance.get('closeNoticeTimeout'), instance, instance._closeNoticeFn);

				instance._closeNoticeTask = closeNoticeTask;

				return closeNoticeTask;
			},

			startSaveContentTask: function() {
				var instance = this;

				instance._saveContentTask = A.later(instance.get('autoSaveTimeout'), instance, instance._saveContentFn, [true], true);

				return instance._saveContentTask;
			},

			_attachCloseListener: function() {
				var instance = this;

				var notice = instance.getEditNotice();

				var boundingBox = notice.get(BOUNDING_BOX);

				boundingBox.one('.lfr-notice-inline-edit-close').on('click', A.bind(notice.hide, notice));
			},

			_closeNoticeFn: function() {
				var instance = this;

				instance.getEditNotice().hide();
			},

			_defaultSaveContentFailure: function(responseData, autosaved) {
				var instance = this;

				instance.resetDirty();

				var notice = instance.getEditNotice();

				var boundingBox = notice.get(BOUNDING_BOX);

				instance._editNoticeNode.replaceClass('lfr-notice-inline-edit', 'lfr-notice-inline-edit-error');

				instance._editNoticeTextNode.html(Liferay.Language.get('the-draft-was-not-saved-successfully'));

				notice.show();

				instance.startCloseNoticeTask();
			},

			_defaultSaveContentSuccess: function(responseData, autosaved) {
				var instance = this;

				instance.resetDirty();

				var notice = instance.getEditNotice();
				
				instance._editNoticeNode.replaceClass('lfr-notice-inline-edit-error', 'lfr-notice-inline-edit');

				var message = Liferay.Language.get('the-draft-was-saved-successfully-at-x');

				if (autosaved) {
					message = Liferay.Language.get('the-draft-was-autosaved-successfully-at-x');
				}

				message = Lang.sub(
					message,
					[new Date().toLocaleTimeString()]
				);

				instance._editNoticeTextNode.html(message);

				notice.show();

				instance.startCloseNoticeTask();
			},

			_saveContentFn: function(autosaved) {
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