/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-inline-editor-base',
	A => {
		var Lang = A.Lang;

		var isNumber = Lang.isNumber;
		var isString = Lang.isString;

		var BODY_CONTENT = 'bodyContent';

		var CSS_ERROR = 'alert alert-danger';

		var CSS_SUCCESS = 'alert alert-success';

		var EDITOR = 'editor';

		var EDITOR_NAME = 'editorName';

		var EDITOR_PREFIX = 'editorPrefix';

		var TPL_NOTICE =
			'<div class="alert alert-success lfr-editable-notice">' +
			'<span class="lfr-editable-notice-text yui3-widget-bd"></span>' +
			'<a class="lfr-editable-notice-close yui3-widget-ft" href="javascript:;" tabindex="0"></a>' +
			'</div>';

		function InlineEditorBase() {
			var instance = this;

			instance.publish('saveFailure', {
				defaultFn: instance._defSaveFailureFn
			});

			instance.publish('saveSuccess', {
				defaultFn: instance._defSaveSuccessFn
			});
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

			editor: {
				validator: Lang.isObject
			},

			editorName: {
				validator: isString
			},

			editorPrefix: {
				validator: isString,
				value: '#cke_'
			},

			editorSuffix: {
				validator: isString,
				value: '_original'
			},

			namespace: {
				validator: isString
			},

			saveURL: {
				validator: isString
			},

			toolbarTopOffset: {
				validator: isNumber,
				value: 30
			}
		};

		InlineEditorBase.prototype = {
			_attachCloseListener() {
				var instance = this;

				var notice = instance.getEditNotice();

				notice.footerNode.on('click', A.bind('hide', notice));
			},

			_closeNoticeFn() {
				var instance = this;

				instance.getEditNotice().hide();
			},

			_defSaveFailureFn() {
				var instance = this;

				instance.resetDirty();

				var notice = instance.getEditNotice();

				instance._editNoticeNode.replaceClass(CSS_SUCCESS, CSS_ERROR);

				notice.set(
					BODY_CONTENT,
					Liferay.Language.get('the-draft-was-not-saved-successfully')
				);

				notice.show();

				instance.closeNotice();
			},

			_defSaveSuccessFn(autosaved) {
				var instance = this;

				instance.resetDirty();

				var notice = instance.getEditNotice();

				instance._editNoticeNode.replaceClass(CSS_ERROR, CSS_SUCCESS);

				var message = Liferay.Language.get(
					'the-draft-was-saved-successfully-at-x'
				);

				if (autosaved) {
					message = Liferay.Language.get(
						'the-draft-was-autosaved-successfully-at-x'
					);
				}

				message = Lang.sub(message, [new Date().toLocaleTimeString()]);

				notice.set(BODY_CONTENT, message);

				notice.show();

				instance.closeNotice();
			},

			_saveFn(autosaved) {
				var instance = this;

				if (instance.isContentDirty()) {
					instance.save(autosaved);
				}
			},

			closeNotice(delay) {
				var instance = this;

				var closeNoticeTask = instance._closeNoticeTask;

				if (!closeNoticeTask) {
					closeNoticeTask = A.debounce(
						instance._closeNoticeFn,
						instance.get('closeNoticeTimeout'),
						instance
					);

					instance._closeNoticeTask = closeNoticeTask;
				}

				if (Lang.isNumber(delay)) {
					closeNoticeTask.delay(delay);
				}
				else {
					closeNoticeTask();
				}
			},

			destructor() {
				var instance = this;

				instance.getEditNotice().destroy();

				if (instance._closeNoticeTask) {
					instance._closeNoticeTask.cancel();
				}

				if (instance._saveTask) {
					instance._saveTask.cancel();
				}
			},

			getEditNotice() {
				var instance = this;

				var editNotice = instance._editNotice;

				if (!editNotice) {
					var triggerNode = A.one(
						instance.get(EDITOR_PREFIX) + instance.get(EDITOR_NAME)
					);

					var editNoticeNode = A.Node.create(TPL_NOTICE);

					editNotice = new A.OverlayBase({
						contentBox: editNoticeNode,
						footerContent: Liferay.Language.get('close'),
						visible: false,
						zIndex: triggerNode.getStyle('zIndex') + 2
					}).render();

					instance._editNoticeNode = editNoticeNode;
					instance._editNotice = editNotice;

					instance._attachCloseListener();
				}

				return editNotice;
			},

			save(autosaved) {
				var instance = this;

				var data = {
					content: instance.get(EDITOR).getData()
				};

				var namespacedData = Liferay.Util.ns(
					instance.get('namespace'),
					data
				);

				Liferay.Util.fetch(instance.get('saveURL'), {
					body: Liferay.Util.objectToFormData(namespacedData),
					method: 'POST'
				})
					.then(response => response.json())
					.then(response => {
						if (response) {
							instance.fire('saveSuccess', autosaved);
						}
						else {
							instance.fire('saveFailure');
						}
					})
					.catch(() => instance.fire('saveFailure'));
			},

			startSaveTask() {
				var instance = this;

				var saveTask = instance._saveTask;

				if (saveTask) {
					saveTask.cancel();
				}

				saveTask = A.later(
					instance.get('autoSaveTimeout'),
					instance,
					instance._saveFn,
					[true],
					true
				);

				instance._saveTask = saveTask;

				return saveTask;
			},

			stopSaveTask() {
				var instance = this;

				var saveTask = instance._saveTask;

				if (saveTask) {
					saveTask.cancel();
				}

				return saveTask;
			}
		};

		Liferay.InlineEditorBase = InlineEditorBase;
	},
	'',
	{
		requires: ['aui-base', 'aui-overlay-base-deprecated']
	}
);
