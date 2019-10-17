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
	'inline-editor-ckeditor',
	A => {
		var Lang = A.Lang;
		var PositionAlign = A.WidgetPositionAlign;

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

		var CKEditorInline = A.Component.create({
			AUGMENTS: [Liferay.InlineEditorBase],

			EXTENDS: A.Base,

			NAME: 'inline-editor-ckeditor',

			prototype: {
				_attachScrollListener() {
					var instance = this;

					if (!instance._scrollHandle) {
						instance._scrollHandle = A.getWin().on(
							'scroll',
							instance._updateNoticePosition,
							instance
						);
					}
				},

				_destructor() {
					var instance = this;

					A.Array.invoke(instance._eventHandles, 'removeListener');

					if (instance._scrollHandle) {
						instance._scrollHandle.detach();
					}
				},

				_getAutoSaveTimeout() {
					var instance = this;

					var editor = instance.get(EDITOR);

					return editor.config.autoSaveTimeout;
				},

				_getCloseNoticeTimeout() {
					var instance = this;

					var editor = instance.get(EDITOR);

					return editor.config.closeNoticeTimeout;
				},

				_onEditorBlur() {
					var instance = this;

					instance.stopSaveTask();

					if (instance.isContentDirty()) {
						instance.save();
					}
				},

				_onEditorFocus() {
					var instance = this;

					var originalContentNode = A.one(
						'#' +
							instance.get(EDITOR_NAME) +
							instance.get(EDITOR_SUFFIX)
					);

					if (!originalContentNode.text()) {
						originalContentNode.text(this.get(EDITOR).getData());
					}

					var notice = instance.getEditNotice();

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
					var instance = this;

					var originalContentNode = A.one(
						'#' +
							instance.get(EDITOR_NAME) +
							instance.get(EDITOR_SUFFIX)
					);

					var originalContent = originalContentNode.text();

					instance.get(EDITOR).setData(originalContent);

					if (instance.isContentDirty()) {
						instance.save();
					}
				},

				_updateNoticePosition() {
					var instance = this;

					var notice = instance.getEditNotice();

					if (notice.get(VISIBLE)) {
						var editorToolbarNode = A.one(
							instance.get(EDITOR_PREFIX) +
								instance.get(EDITOR_NAME)
						);

						var editorToolbarVisible =
							editorToolbarNode.getStyle('display') !== 'none';

						var align = {
							node: WIN,
							points: POINTS_WINDOW_CENTER
						};

						if (editorToolbarVisible) {
							var noticePosition = POINT_TL;
							var containerPostion = POINT_BL;

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
					var instance = this;

					var editor = instance.get(EDITOR);

					instance._eventHandles = [
						editor.on('blur', instance._onEditorBlur, instance),
						editor.on('focus', instance._onEditorFocus, instance),
						editor.on(
							'restoreContent',
							instance._restoreContent,
							instance
						),
						editor.on('saveContent', A.fn(0, 'save', instance))
					];

					instance.after('destroy', instance._destructor, instance);

					instance.after(
						['saveFailure', 'saveSuccess'],
						instance._updateNoticePosition,
						instance
					);

					A.one('#' + instance.get(EDITOR_NAME)).delegate(
						'click',
						event => {
							if (event.shiftKey) {
								var clone = event.currentTarget.clone();

								A.getBody().append(clone);

								clone.simulate('click');
							}
						},
						'a'
					);
				},

				isContentDirty() {
					var instance = this;

					return instance.get(EDITOR).checkDirty();
				},

				resetDirty() {
					var instance = this;

					instance.get(EDITOR).resetDirty();
				}
			}
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
			'yui-later'
		]
	}
);
