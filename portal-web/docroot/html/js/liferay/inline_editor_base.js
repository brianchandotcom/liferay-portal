AUI.add(
	'liferay-inline-editor-base',
	function(A) {
		var Lang = A.Lang;
		var isNumber = Lang.isNumber;
		var isString = Lang.isString;

		var BODY_CONTENT = 'bodyContent';

		var CSS_SUCCESS = 'alert alert-success';

		var CSS_ERROR = 'alert alert-error';

		var EDITOR = 'editor';

		var EDITOR_NAME = 'editorName';

		var EDITOR_PREFIX = 'editorPrefix';

		var EDITOR_SUFFIX = 'editorSuffix';

		var NOTICE_INSTANCE = 'noticeInstance';

		var RESPONSE_DATA = 'responseData';

		var TPL_NOTICE =
			'<div class="alert alert-success lfr-editable-notice">' +
				'<span class="lfr-editable-notice-text yui3-widget-bd"></span>' +
				'<a class="lfr-editable-notice-close yui3-widget-ft" href="javascript:;" tabindex="0"></a>' +
			'</div>';

		function InlineEditorBase(config) {
			var instance = this;

			instance.publish(
				'saveFailure',
				{
					defaultFn: instance._defSaveFailureFn
				}
			);

			instance.publish(
				'saveSuccess',
				{
					defaultFn: instance._defSaveSuccessFn
				}
			);
		}

		InlineEditorBase.ATTRS = {
			autoSaveTimeout: {
				getter: '_getAutoSaveTimeout',
				validator: isNumber,
				value: 3000
			},

			closeNoticeTimeout: {
				getter: '_getCloseNoticeTimeout',
				validator: isNumber,
				value: 8000
			},

			editorPrefix: {
				validator: isString,
				value: '#cke_'
			},

			editorSuffix: {
				validator: isString,
				value: '_original'
			},

			editor: {
				validator: Lang.isObject
			},

			editorName: {
				validator: isString
			},

			toolbarTopOffset: {
				validator: isNumber,
				value: 30
			},

			saveURL: {
				validator: isString
			}
		};

		InlineEditorBase.prototype = {
			destructor: function() {
				var instance = this;

				instance.getEditNotice().destroy();

				if (instance._closeNoticeTask) {
					instance._closeNoticeTask.cancel();
				}

				if (instance._saveTask) {
					instance._saveTask.cancel();
				}
			},

			closeNotice: function(delay) {
				var instance = this;

				var closeNoticeTask = instance._closeNoticeTask;

				if (!closeNoticeTask) {
					closeNoticeTask = A.debounce(instance._closeNoticeFn, instance.get('closeNoticeTimeout'), instance);

					instance._closeNoticeTask = closeNoticeTask;
				}

				if (Lang.isNumber(delay)) {
					closeNoticeTask.delay(delay);
				}
				else {
					closeNoticeTask();
				}
			},

			getEditNotice: function() {
				var instance = this;

				var editNotice = instance._editNotice;

				if (!editNotice) {
					var triggerNode = A.one(instance.get(EDITOR_PREFIX) + instance.get(EDITOR_NAME));

					var inlineEditorNoticeId = A.guid();

					var editNoticeNode = A.Node.create(TPL_NOTICE);

					editNotice = new A.OverlayBase(
						{
							contentBox: editNoticeNode,
							footerContent: Liferay.Language.get('close'),
							visible: false,
							zIndex: triggerNode.getStyle('zIndex') + 2
						}
					).render();

					instance._editNoticeNode = editNoticeNode;
					instance._editNotice = editNotice;

					instance._attachCloseListener();
				}

				return editNotice;
			},

			save: function(autosaved) {
				var instance = this;

				A.io.request(
					instance.get('saveURL'),
					{
						after: {
							failure: function() {
								var responseData = this.get(RESPONSE_DATA);

								instance.fire('saveFailure', responseData, autosaved);
							},
							success: function() {
								var responseData = this.get(RESPONSE_DATA);

								if (responseData.success) {
									instance.fire('saveSuccess', responseData, autosaved);
								}
								else {
									instance.fire('saveFailure', responseData, autosaved);
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

			startSaveTask: function() {
				var instance = this;

				var saveTask = instance._saveTask;

				if (saveTask) {
					saveTask.cancel();
				}

				saveTask = A.later(instance.get('autoSaveTimeout'), instance, instance._saveFn, [true], true);

				instance._saveTask = saveTask;

				return saveTask;
			},

			stopSaveTask: function() {
				var instance = this;

				var saveTask = instance._saveTask;

				if (saveTask) {
					saveTask.cancel();
				}

				return saveTask;
			},

			_attachCloseListener: function() {
				var instance = this;

				var notice = instance.getEditNotice();

				notice.footerNode.on('click', A.bind('hide', notice));
			},

			_closeNoticeFn: function() {
				var instance = this;

				instance.getEditNotice().hide();
			},

			_defSaveFailureFn: function(responseData, autosaved) {
				var instance = this;

				instance.resetDirty();

				var notice = instance.getEditNotice();

				instance._editNoticeNode.replaceClass(CSS_SUCCESS, CSS_ERROR);

				notice.set(BODY_CONTENT, Liferay.Language.get('the-draft-was-not-saved-successfully'));

				notice.show();

				instance.closeNotice();
			},

			_defSaveSuccessFn: function(responseData, autosaved) {
				var instance = this;

				instance.resetDirty();

				var notice = instance.getEditNotice();

				instance._editNoticeNode.replaceClass(CSS_ERROR, CSS_SUCCESS);

				var message = Liferay.Language.get('the-draft-was-saved-successfully-at-x');

				if (autosaved) {
					message = Liferay.Language.get('the-draft-was-autosaved-successfully-at-x');
				}

				message = Lang.sub(message, [new Date().toLocaleTimeString()]);

				notice.set(BODY_CONTENT, message);

				notice.show();

				instance.closeNotice();
			},

			_saveFn: function(autosaved) {
				var instance = this;

				if (instance.isContentDirty()) {
					instance.save(autosaved);
				}
			}
		};

		Liferay.InlineEditorBase = InlineEditorBase;
	},
	'',
	{
		requires: ['aui-base', 'aui-overlay-base-deprecated']
	}
);