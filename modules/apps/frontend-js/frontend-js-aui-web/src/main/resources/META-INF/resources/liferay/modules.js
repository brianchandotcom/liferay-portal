/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
(function () {
	const LiferayAUI = Liferay.AUI;

	const COMBINE = LiferayAUI.getCombine();

	const PATH_EDITOR_CKEDITOR = LiferayAUI.getEditorCKEditorPath();

	const PATH_JAVASCRIPT = '/o/frontend-js-aui-web';

	window.YUI_config = {
		base:
			Liferay.ThemeDisplay.getCDNBaseURL() +
			Liferay.ThemeDisplay.getPathContext() +
			PATH_JAVASCRIPT +
			'/aui/',
		combine: COMBINE,
		comboBase: LiferayAUI.getComboPath(),
		filter: process.env.NODE_ENV === 'development' ? 'raw' : 'min',
		groups: {
			editor: {
				base: PATH_EDITOR_CKEDITOR,
				combine: COMBINE,
				modules: {
					'inline-editor-ckeditor': {
						path: 'ckeditor/main.js',
					},
				},
				root: PATH_EDITOR_CKEDITOR,
			},

			liferay: {
				base:
					Liferay.ThemeDisplay.getCDNBaseURL() +
					Liferay.ThemeDisplay.getPathContext() +
					PATH_JAVASCRIPT +
					'/liferay/',
				combine: COMBINE,
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-alert': {
						path: 'alert.js',
						requires: [
							'aui-alert',
							'aui-component',
							'event-mouseenter',
							'liferay-portlet-base',
							'timers',
						],
					},
					'liferay-auto-fields': {
						path: 'auto_fields.js',
						requires: [
							'aui-base',
							'aui-data-set-deprecated',
							'aui-parse-content',
							'base',
							'liferay-form',
							'liferay-menu',
							'liferay-portlet-base',
							'liferay-undo-manager',
							'sortable',
						],
					},
					'liferay-autocomplete-input': {
						path: 'autocomplete_input.js',
						requires: [
							'aui-base',
							'autocomplete',
							'autocomplete-filters',
							'autocomplete-highlighters',
						],
					},
					'liferay-dynamic-select': {
						path: 'dynamic_select.js',
						requires: ['aui-base'],
					},
					'liferay-form': {
						path: 'form.js',
						requires: ['aui-base', 'aui-form-validator'],
					},
					'liferay-fullscreen-source-editor': {
						path: 'fullscreen_source_editor.js',
						requires: ['liferay-source-editor'],
					},
					'liferay-icon': {
						path: 'icon.js',
						requires: ['aui-base'],
					},
					'liferay-inline-editor-base': {
						path: 'inline_editor_base.js',
						requires: ['aui-base', 'aui-overlay-base-deprecated'],
					},
					'liferay-input-localized': {
						path: 'input_localized.js',
						requires: [
							'aui-base',
							'aui-component',
							'aui-event-input',
							'aui-palette',
							'aui-set',
						],
					},
					'liferay-input-move-boxes': {
						path: 'input_move_boxes.js',
						plugins: {
							'liferay-input-move-boxes-touch': {
								condition: {
									name: 'liferay-input-move-boxes-touch',
									test(A) {
										return (
											A.UA.touchEnabled && !!A.UA.mobile
										);
									},
									trigger: 'liferay-input-move-boxes',
								},
							},
						},
						requires: ['aui-base', 'aui-toolbar'],
					},
					'liferay-input-move-boxes-touch': {
						path: 'input_move_boxes_touch.js',
						requires: [
							'aui-base',
							'aui-template-deprecated',
							'liferay-input-move-boxes',
							'sortable',
						],
					},
					'liferay-language': {
						path: 'language.js',
					},
					'liferay-layout': {
						path: 'layout.js',
					},
					'liferay-layout-column': {
						path: 'layout_column.js',
						requires: ['aui-sortable-layout', 'dd'],
					},
					'liferay-logo-editor': {
						path: 'logo_editor.js',
						requires: ['aui-image-cropper', 'liferay-portlet-base'],
					},
					'liferay-logo-selector': {
						path: 'logo_selector.js',
						requires: ['aui-base'],
					},
					'liferay-menu': {
						path: 'menu.js',
						requires: ['aui-debounce', 'aui-node'],
					},
					'liferay-menu-filter': {
						path: 'menu_filter.js',
						requires: [
							'autocomplete-base',
							'autocomplete-filters',
							'autocomplete-highlighters',
						],
					},
					'liferay-menu-toggle': {
						path: 'menu_toggle.js',
						requires: [
							'aui-node',
							'event-outside',
							'event-tap',
							'liferay-menu-filter',
						],
					},
					'liferay-navigation': {
						path: 'navigation.js',
						requires: ['aui-component', 'event-mouseenter'],
					},
					'liferay-navigation-interaction': {
						path: 'navigation_interaction.js',
						plugins: {
							'liferay-navigation-interaction-touch': {
								condition: {
									name:
										'liferay-navigation-interaction-touch',
									test(A) {
										return A.UA.touchEnabled;
									},
									trigger: 'liferay-navigation-interaction',
								},
							},
						},
						requires: [
							'aui-base',
							'aui-component',
							'event-mouseenter',
							'node-focusmanager',
							'plugin',
						],
					},
					'liferay-navigation-interaction-touch': {
						path: 'navigation_interaction_touch.js',
						requires: [
							'event-tap',
							'event-touch',
							'liferay-navigation-interaction',
						],
					},
					'liferay-portlet-base': {
						path: 'portlet_base.js',
						requires: ['aui-base'],
					},
					'liferay-portlet-url': {
						path: 'portlet_url.js',
						requires: ['aui-base'],
					},
					'liferay-progress': {
						path: 'progress.js',
						requires: ['aui-progressbar'],
					},
					'liferay-search-container': {
						path: 'search_container.js',
						requires: ['aui-base', 'aui-datatable-core'],
					},
					'liferay-search-container-move': {
						path: 'search_container_move.js',
						requires: [
							'aui-component',
							'dd-constrain',
							'dd-delegate',
							'dd-drag',
							'dd-drop',
							'dd-proxy',
							'plugin',
						],
					},
					'liferay-search-container-select': {
						path: 'search_container_select.js',
						requires: ['aui-component', 'aui-url', 'plugin'],
					},
					'liferay-session': {
						path: 'session.js',
						requires: [
							'aui-base',
							'aui-component',
							'aui-timer',
							'cookie',
							'plugin',
						],
					},
					'liferay-source-editor': {
						path: 'source_editor.js',
						requires: ['aui-ace-editor'],
					},
					'liferay-store': {
						path: 'store.js',
					},
					'liferay-translation-manager': {
						path: 'translation_manager.js',
						requires: ['aui-base'],
					},
					'liferay-undo-manager': {
						path: 'undo_manager.js',
						requires: ['aui-data-set-deprecated', 'base'],
					},
					'liferay-upload': {
						path: 'upload.js',
						requires: [
							'aui-template-deprecated',
							'collection',
							'liferay-portlet-base',
							'uploader',
						],
					},
					'liferay-util-window': {
						path: 'util_window.js',
						requires: [
							'aui-dialog-iframe-deprecated',
							'aui-modal',
							'aui-url',
							'event-resize',
							'liferay-widget-zindex',
						],
					},
					'liferay-widget-zindex': {
						path: 'widget_zindex.js',
						requires: ['aui-modal', 'plugin'],
					},
				},
				root: PATH_JAVASCRIPT + '/liferay/',
			},
		},
		insertBefore: 'liferayAUICSS',
		lang: themeDisplay.getBCP47LanguageId(),
		root: PATH_JAVASCRIPT + '/aui/',
		useBrowserConsole: false,
	};
})();
