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
	'liferay-fullscreen-source-editor',
	(A) => {
		var Lang = A.Lang;

		var CONTENT_TEMPLATE =
			'<div class="lfr-fullscreen-source-editor-header row">' +
			'<div class="col-6">' +
			'<button class="btn btn-secondary btn-sm float-right lfr-portal-tooltip" data-title="{iconMoonTooltip}" id="switchTheme" type="button">' +
			'<svg class="lexicon-icon lexicon-icon-moon" focusable="false" role="img">' +
			'<use href="{pathThemeImages}/lexicon/icons.svg#moon" />' +
			'</svg>' +
			'</button>' +
			'</div>' +
			'<div class="col-6 layout-selector text-right">' +
			'<div class="btn-group" role="group">' +
			'<button class="btn btn-secondary btn-sm" data-layout="vertical">' +
			'<svg class="lexicon-icon lexicon-icon-columns" focusable="false" role="img">' +
			'<use href="{pathThemeImages}/lexicon/icons.svg#columns" />' +
			'</svg>' +
			'</button>' +
			'<button class="btn btn-secondary btn-sm" data-layout="horizontal">' +
			'<svg class="lexicon-icon lexicon-icon-cards" focusable="false" role="img">' +
			'<use href="{pathThemeImages}/lexicon/icons.svg#cards" />' +
			'</svg>' +
			'</button>' +
			'<button class="btn btn-secondary btn-sm" data-layout="simple">' +
			'<svg class="lexicon-icon lexicon-icon-expand" focusable="false" role="img">' +
			'<use href="{pathThemeImages}/lexicon/icons.svg#expand" />' +
			'</svg>' +
			'</button>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'<div class="lfr-fullscreen-source-editor-content">' +
			'<div class="source-panel">' +
			'<div class="source-html"></div>' +
			'</div>' +
			'<div class="preview-panel"></div>' +
			'<div class="panel-splitter"></div>' +
			'</div>';

		var CSS_PREVIEW_PANEL = '.preview-panel';

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CLICK = 'click';

		var STR_DOT = '.';

		var STR_LAYOUT = 'layout';

		var STR_VALUE = 'value';

		var LiferayFullScreenSourceEditor = A.Component.create({
			ATTRS: {
				aceOptions: {
					validator: Lang.isObject,
					value: {
						fontSize: 13,
						showInvisibles: false,
						showPrintMargin: false,
					},
				},

				dataProcessor: {
					validator: Lang.isObject,
				},

				layout: {
					validator: Lang.isString,
					value: 'vertical',
				},

				previewCssClass: {
					validator: Lang.isString,
					value: '',
				},

				previewDelay: {
					validator: Lang.isNumber,
					value: 100,
				},

				value: {
					getter: '_getValue',
					validator: Lang.isString,
					value: '',
				},
			},

			CSS_PREFIX: 'lfr-fullscreen-source-editor',

			EXTENDS: A.Widget,

			NAME: 'liferayfullscreensourceeditor',

			NS: 'liferayfullscreensourceeditor',

			prototype: {
				_getHtml(val) {
					var instance = this;

					var dataProcessor = instance.get('dataProcessor');

					if (dataProcessor && dataProcessor.toHtml) {
						val = dataProcessor.toHtml(val);
					}

					return val;
				},

				_getValue(val) {
					var instance = this;

					return instance._editor
						? instance._editor.get(STR_VALUE)
						: val;
				},

				_onEditorChange(event) {
					var instance = this;

					instance._previewPanel.html(
						instance._getHtml(event.newVal)
					);
				},

				_onLayoutChange(event) {
					var instance = this;

					instance
						.get(STR_BOUNDING_BOX)
						.one(STR_DOT + instance.getClassName('content'))
						.replaceClass(event.prevVal, event.newVal);

					instance.resizeEditor();
				},

				_onLayoutClick(event) {
					var instance = this;

					instance.set(
						STR_LAYOUT,
						event.currentTarget.attr('data-layout')
					);
				},

				_onPreviewLink(event) {
					event.currentTarget.attr('target', '_blank');
				},

				_onValueChange(event) {
					var instance = this;

					instance._editor.set(STR_VALUE, event.newVal);
				},

				_switchTheme() {
					var instance = this;

					instance._editor.switchTheme();
				},

				CONTENT_TEMPLATE: Lang.sub(CONTENT_TEMPLATE, {
					iconMoonTooltip: Liferay.Language.get('dark-theme'),
					pathThemeImages: themeDisplay.getPathThemeImages(),
				}),

				bindUI() {
					var instance = this;

					var boundingBox = instance.get(STR_BOUNDING_BOX);

					var onChangeTask = A.debounce(
						'_onEditorChange',
						instance.get('previewDelay'),
						instance
					);

					instance._eventHandles = [
						instance._editor.on('change', onChangeTask),
						instance.on('layoutChange', instance._onLayoutChange),
						instance.on('valueChange', instance._onValueChange),
						instance._editorSwitchTheme.on(
							'click',
							instance._switchTheme,
							instance
						),
						boundingBox
							.one(STR_DOT + instance.getClassName('header'))
							.delegate(
								STR_CLICK,
								instance._onLayoutClick,
								'[data-layout]',
								instance
							),
						boundingBox
							.one(CSS_PREVIEW_PANEL)
							.delegate(
								STR_CLICK,
								instance._onPreviewLink,
								'a',
								instance
							),
					];
				},

				destructor() {
					var instance = this;

					var sourceEditor = instance._editor;

					if (sourceEditor) {
						sourceEditor.destroy();
					}

					new A.EventHandle(instance._eventHandles).detach();
				},

				renderUI() {
					var instance = this;

					var boundingBox = instance.get(STR_BOUNDING_BOX);

					boundingBox
						.one(STR_DOT + instance.getClassName('content'))
						.addClass(instance.get(STR_LAYOUT));

					instance._editorSwitchTheme = boundingBox.one(
						'#switchTheme'
					);

					instance._editor = new A.LiferaySourceEditor({
						aceOptions: instance.get('aceOptions'),
						boundingBox: boundingBox.one('.source-html'),
						height: '100%',
						mode: 'html',
						on: {
							themeSwitched(event) {
								var editorSwitchTheme =
									instance._editorSwitchTheme;

								var nextTheme =
									event.themes[event.nextThemeIndex];

								editorSwitchTheme
									.one('.lexicon-icon')
									.replace(nextTheme.icon);

								editorSwitchTheme.setAttribute(
									'data-title',
									nextTheme.tooltip
								);
							},
						},
						value: instance.get(STR_VALUE),
					}).render();

					instance._previewPanel = boundingBox.one(CSS_PREVIEW_PANEL);

					instance._previewPanel.html(
						instance._getHtml(instance.get(STR_VALUE))
					);
					instance._previewPanel.addClass(
						instance.get('previewCssClass')
					);
				},

				resizeEditor() {
					var instance = this;

					instance._editor.getEditor().resize();
				},
			},
		});

		A.LiferayFullScreenSourceEditor = LiferayFullScreenSourceEditor;
	},
	'',
	{
		requires: ['liferay-source-editor'],
	}
);
