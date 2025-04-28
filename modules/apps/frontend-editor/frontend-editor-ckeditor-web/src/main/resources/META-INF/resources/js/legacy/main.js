/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'inline-editor-ckeditor',
	(A) => {
		const Lang = A.Lang;
		const PositionAlign = A.WidgetPositionAlign;

		const ALIGN = 'align';

		const BOUNDING_BOX = 'boundingBox';

		const EDITOR = 'editor';

		const EDITOR_NAME = 'editorName';

		const EDITOR_PREFIX = 'editorPrefix';

		const EDITOR_SUFFIX = 'editorSuffix';

		const POINT_BL = PositionAlign.BL;

		const POINT_TL = PositionAlign.TL;

		const POINTS_WINDOW_CENTER = [POINT_TL, PositionAlign.TC];

		const VISIBLE = 'visible';

		const WIN = A.config.win;

		const CKEditorInline = A.Component.create({
			AUGMENTS: [Liferay.InlineEditorBase],

			EXTENDS: A.Base,

			NAME: 'inline-editor-ckeditor',

			prototype: {
				_attachScrollListener() {
					const instance = this;

					if (!instance._scrollHandle) {
						instance._scrollHandle = A.getWin().on(
							'scroll',
							instance._updateNoticePosition,
							instance
						);
					}
				},

				_destructor() {
					const instance = this;

					A.Array.invoke(instance._eventHandles, 'removeListener');

					if (instance._scrollHandle) {
						instance._scrollHandle.detach();
					}
				},

				_getAutoSaveTimeout() {
					const instance = this;

					const editor = instance.get(EDITOR);

					return editor.config.autoSaveTimeout;
				},

				_getCloseNoticeTimeout() {
					const instance = this;

					const editor = instance.get(EDITOR);

					return editor.config.closeNoticeTimeout;
				},

				_onEditorBlur() {
					const instance = this;

					instance.stopSaveTask();

					if (instance.isContentDirty()) {
						instance.save();
					}
				},

				_onEditorFocus() {
					const instance = this;

					const originalContentNode = A.one(
						'#' +
							instance.get(EDITOR_NAME) +
							instance.get(EDITOR_SUFFIX)
					);

					if (!originalContentNode.text()) {
						originalContentNode.text(this.get(EDITOR).getData());
					}

					const notice = instance.getEditNotice();

					if (
						notice.get(VISIBLE) &&
						notice.get(BOUNDING_BOX).getData(EDITOR) !==
							instance.get(EDITOR_NAME)
					) {
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

				_restoreContent() {
					const instance = this;

					const originalContentNode = A.one(
						'#' +
							instance.get(EDITOR_NAME) +
							instance.get(EDITOR_SUFFIX)
					);

					const originalContent = originalContentNode.text();

					instance.get(EDITOR).setData(originalContent);

					if (instance.isContentDirty()) {
						instance.save();
					}
				},

				_updateNoticePosition() {
					const instance = this;

					const notice = instance.getEditNotice();

					if (notice.get(VISIBLE)) {
						const editorToolbarNode = A.one(
							instance.get(EDITOR_PREFIX) +
								instance.get(EDITOR_NAME)
						);

						const editorToolbarVisible =
							editorToolbarNode.getStyle('display') !== 'none';

						const align = {
							node: WIN,
							points: POINTS_WINDOW_CENTER,
						};

						if (editorToolbarVisible) {
							let noticePosition = POINT_TL;
							let containerPostion = POINT_BL;

							if (
								Lang.toInt(editorToolbarNode.getStyle('top')) >
								instance.get('toolbarTopOffset')
							) {
								noticePosition = POINT_BL;
								containerPostion = POINT_TL;
							}

							align.node = editorToolbarNode;
							align.points = [noticePosition, containerPostion];
						}

						notice.set(ALIGN, align);
					}
				},

				initializer() {
					const instance = this;

					const editor = instance.get(EDITOR);

					instance._eventHandles = [
						editor.on('blur', instance._onEditorBlur, instance),
						editor.on('focus', instance._onEditorFocus, instance),
						editor.on(
							'restoreContent',
							instance._restoreContent,
							instance
						),
						editor.on('saveContent', A.fn(0, 'save', instance)),
					];

					instance.after('destroy', instance._destructor, instance);

					instance.after(
						['saveFailure', 'saveSuccess'],
						instance._updateNoticePosition,
						instance
					);

					A.one('#' + instance.get(EDITOR_NAME)).delegate(
						'click',
						(event) => {
							if (event.shiftKey) {
								const clone = event.currentTarget.clone();

								document.body.appendChild(clone);

								clone.simulate('click');
							}
						},
						'a'
					);
				},

				isContentDirty() {
					const instance = this;

					return instance.get(EDITOR).checkDirty();
				},

				resetDirty() {
					const instance = this;

					instance.get(EDITOR).resetDirty();
				},
			},
		});

		Liferay.CKEditorInline = CKEditorInline;
	},
	'',
	{
		requires: [
			'array-invoke',
			'liferay-inline-editor-base',
			'node-event-simulate',
			'overlay',
			'yui-later',
		],
	}
);
