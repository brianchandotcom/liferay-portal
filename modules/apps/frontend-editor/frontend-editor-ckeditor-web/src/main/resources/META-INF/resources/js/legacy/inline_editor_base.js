/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-inline-editor-base',
	(A) => {
		const Lang = A.Lang;

		const isNumber = Lang.isNumber;
		const isString = Lang.isString;

		const BODY_CONTENT = 'bodyContent';

		const CSS_ERROR = 'alert alert-danger';

		const CSS_SUCCESS = 'alert alert-success';

		const EDITOR = 'editor';

		const EDITOR_NAME = 'editorName';

		const EDITOR_PREFIX = 'editorPrefix';

		const TPL_NOTICE =
			'<div class="alert alert-success lfr-editable-notice">' +
			'<span class="lfr-editable-notice-text yui3-widget-bd"></span>' +
			'<a class="lfr-editable-notice-close ml-3 yui3-widget-ft" href="javascript:void(0);" tabindex="0"></a>' +
			'</div>';

		function InlineEditorBase() {
			const instance = this;

			instance.publish('saveFailure', {
				defaultFn: instance._defSaveFailureFn,
			});

			instance.publish('saveSuccess', {
				defaultFn: instance._defSaveSuccessFn,
			});
		}

		InlineEditorBase.ATTRS = {
			autoSaveTimeout: {
				getter: '_getAutoSaveTimeout',
				validator: isNumber,
				value: 3000,
			},

			closeNoticeTimeout: {
				getter: '_getCloseNoticeTimeout',
				validator: isNumber,
				value: 8000,
			},

			editor: {
				validator: Lang.isObject,
			},

			editorName: {
				validator: isString,
			},

			editorPrefix: {
				validator: isString,
				value: '#cke_',
			},

			editorSuffix: {
				validator: isString,
				value: '_original',
			},

			namespace: {
				validator: isString,
			},

			saveURL: {
				validator: isString,
			},

			toolbarTopOffset: {
				validator: isNumber,
				value: 30,
			},
		};

		InlineEditorBase.prototype = {
			_attachCloseListener() {
				const instance = this;

				const notice = instance.getEditNotice();

				notice.footerNode.on('click', A.bind('hide', notice));
			},

			_closeNoticeFn() {
				const instance = this;

				instance.getEditNotice().hide();
			},

			_defSaveFailureFn() {
				const instance = this;

				instance.resetDirty();

				const notice = instance.getEditNotice();

				instance._editNoticeNode.replaceClass(CSS_SUCCESS, CSS_ERROR);

				notice.set(
					BODY_CONTENT,
					Liferay.Language.get('the-draft-was-not-saved-successfully')
				);

				notice.show();

				instance.closeNotice();
			},

			_defSaveSuccessFn(autosaved) {
				const instance = this;

				instance.resetDirty();

				const notice = instance.getEditNotice();

				instance._editNoticeNode.replaceClass(CSS_ERROR, CSS_SUCCESS);

				let message = Liferay.Language.get(
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
				const instance = this;

				if (instance.isContentDirty()) {
					instance.save(autosaved);
				}
			},

			closeNotice(delay) {
				const instance = this;

				let closeNoticeTask = instance._closeNoticeTask;

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
				const instance = this;

				instance.getEditNotice().destroy();

				if (instance._closeNoticeTask) {
					instance._closeNoticeTask.cancel();
				}

				if (instance._saveTask) {
					instance._saveTask.cancel();
				}
			},

			getEditNotice() {
				const instance = this;

				let editNotice = instance._editNotice;

				if (!editNotice) {
					const triggerNode = A.one(
						instance.get(EDITOR_PREFIX) + instance.get(EDITOR_NAME)
					);

					const editNoticeNode = A.Node.create(TPL_NOTICE);

					editNotice = new A.OverlayBase({
						contentBox: editNoticeNode,
						footerContent: Liferay.Language.get('close'),
						visible: false,
						zIndex: triggerNode.getStyle('zIndex') + 2,
					}).render();

					instance._editNoticeNode = editNoticeNode;
					instance._editNotice = editNotice;

					instance._attachCloseListener();
				}

				return editNotice;
			},

			save(autosaved) {
				const instance = this;

				const data = {
					content: instance.get(EDITOR).getData(),
				};

				const namespacedData = Liferay.Util.ns(
					instance.get('namespace'),
					data
				);

				Liferay.Util.fetch(instance.get('saveURL'), {
					body: Liferay.Util.objectToFormData(namespacedData),
					method: 'POST',
				})
					.then((response) => response.json())
					.then((response) => {
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
				const instance = this;

				let saveTask = instance._saveTask;

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
				const instance = this;

				const saveTask = instance._saveTask;

				if (saveTask) {
					saveTask.cancel();
				}

				return saveTask;
			},
		};

		Liferay.InlineEditorBase = InlineEditorBase;
	},
	'',
	{
		requires: ['aui-base', 'aui-overlay-base-deprecated'],
	}
);
