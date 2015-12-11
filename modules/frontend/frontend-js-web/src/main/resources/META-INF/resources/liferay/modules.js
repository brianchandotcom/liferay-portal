;(function() {
	var LiferayAUI = Liferay.AUI;

	var COMBINE = LiferayAUI.getCombine();

	var CORE_MODULES = YUI.Env.core;

	var INPUT_EL = document.createElement('input');

	var PATH_EDITOR_CKEDITOR = LiferayAUI.getEditorCKEditorPath();

	var PATH_JAVASCRIPT = LiferayAUI.getJavaScriptRootPath();

	var SUPPORTS_INPUT_SELECTION = typeof INPUT_EL.selectionStart === 'number' && typeof INPUT_EL.selectionEnd === 'number';

	var testHistory = function(A) {
		var WIN = A.config.win;

		var HISTORY = WIN.history;

		return HISTORY &&
				HISTORY.pushState &&
				HISTORY.replaceState &&
				('onpopstate' in WIN || A.UA.gecko >= 2);
	};

	window.YUI_config = {
		base: PATH_JAVASCRIPT + '/aui/',
		combine: COMBINE,
		comboBase: LiferayAUI.getComboPath(),
		filter: Liferay.AUI.getFilter(),
		groups: {
			editor: {
				base: PATH_EDITOR_CKEDITOR,
				combine: COMBINE,
				modules: {
					'inline-editor-ckeditor': {
						path: 'ckeditor/main.js'
					}
				},
				root: PATH_EDITOR_CKEDITOR
			},

			liferay: {
				base: PATH_JAVASCRIPT + '/liferay/',
				combine: COMBINE,
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-alert': {
						path: 'alert.js',
						requires: [
							'aui-alert',
							'event-mouseenter',
							'liferay-portlet-base',
							'timers'
						]
					},
					'liferay-asset-addon-entry-selector': {
						path: 'asset_addon_entry_selector.js',
						requires: [
							'aui-component',
							'liferay-portlet-base',
							'liferay-util-window'
						]
					},
					'liferay-asset-categories-selector': {
						path: 'asset_categories_selector.js',
						requires: [
							'aui-tree',
							'liferay-asset-tags-selector'
						]
					},
					'liferay-asset-tags-selector': {
						path: 'asset_tags_selector.js',
						requires: [
							'array-extras',
							'async-queue',
							'aui-autocomplete-deprecated',
							'aui-io-plugin-deprecated',
							'aui-io-request',
							'aui-live-search-deprecated',
							'aui-modal',
							'aui-template-deprecated',
							'aui-textboxlist-deprecated',
							'datasource-cache',
							'liferay-service-datasource'
						]
					},
					'liferay-auto-fields': {
						path: 'auto_fields.js',
						requires: [
							'aui-base',
							'aui-data-set-deprecated',
							'aui-io-request',
							'aui-parse-content',
							'base',
							'liferay-undo-manager',
							'sortable'
						]
					},
					'liferay-autocomplete-input': {
						path: 'autocomplete_input.js',
						requires: [
							'aui-base',
							'autocomplete',
							'autocomplete-filters',
							'autocomplete-highlighters'
						]
					},
					'liferay-autocomplete-input-caretindex': {
						condition: {
							name: 'liferay-autocomplete-input-caretindex',
							test: function() {
								return SUPPORTS_INPUT_SELECTION;
							},
							trigger: 'liferay-autocomplete-textarea'
						},
						path: 'autocomplete_input_caretindex.js',
						requires: [
							'liferay-autocomplete-textarea'
						]
					},
					'liferay-autocomplete-input-caretindex-sel': {
						condition: {
							name: 'liferay-autocomplete-input-caretindex-sel',
							test: function() {
								return !SUPPORTS_INPUT_SELECTION;
							},
							trigger: 'liferay-autocomplete-textarea'
						},
						path: 'autocomplete_input_caretindex_sel.js',
						requires: [
							'liferay-autocomplete-textarea'
						]
					},
					'liferay-autocomplete-input-caretoffset': {
						condition: {
							name: 'liferay-autocomplete-input-caretoffset',
							test: function(A) {
								return !(A.UA.ie && A.UA.ie < 9);
							},
							trigger: 'liferay-autocomplete-textarea'
						},
						path: 'autocomplete_input_caretoffset.js',
						requires: [
							'liferay-autocomplete-textarea'
						]
					},
					'liferay-autocomplete-input-caretoffset-sel': {
						condition: {
							name: 'liferay-autocomplete-input-caretoffset-sel',
							test: function(A) {
								return A.UA.ie && A.UA.ie < 9;
							},
							trigger: 'liferay-autocomplete-textarea'
						},
						path: 'autocomplete_input_caretoffset_sel.js',
						requires: [
							'liferay-autocomplete-textarea'
						]
					},
					'liferay-autocomplete-textarea': {
						path: 'autocomplete_textarea.js',
						requires: [
							'liferay-autocomplete-input'
						]
					},
					'liferay-browser-selectors': {
						path: 'browser_selectors.js',
						requires: ['yui-base']
					},
					'liferay-control-panel': {
						path: 'control_panel.js',
						requires: [
							'aui-live-search-deprecated',
							'aui-overlay-context-panel-deprecated',
							'event-mouseenter',
							'liferay-message',
							'liferay-panel-search',
							'liferay-portlet-base',
							'liferay-store',
							'node-focusmanager',
							'transition'
						]
					},
					'liferay-cover-cropper': {
						path: 'cover_cropper.js',
						requires: [
							'aui-base',
							'dd-constrain',
							'dd-drag',
							'liferay-crop-region',
							'plugin'
						]
					},
					'liferay-crop-region': {
						path: 'crop_region.js',
						requires: [
							'aui-base'
						]
					},
					'liferay-ddm-form': {
						path: 'ddm_form.js',
						requires: [
							'aui-base',
							'aui-datatype',
							'aui-io-request',
							'aui-parse-content',
							'liferay-item-selector-dialog',
							'liferay-map-base',
							'liferay-translation-manager'
						]
					},
					'liferay-diff-version-comparator': {
						path: 'diff_version_comparator.js',
						requires: [
							'aui-io-request',
							'autocomplete-base',
							'autocomplete-filters',
							'liferay-portlet-base'
						]
					},
					'liferay-dynamic-select': {
						path: 'dynamic_select.js',
						requires: [
							'aui-base'
						]
					},
					'liferay-editor-image-uploader': {
						path: 'editor_image_uploader.js',
						requires: [
							'aui-alert',
							'aui-base',
							'aui-progressbar',
							'uploader'
						]
					},
					'liferay-form': {
						path: 'form.js',
						requires: [
							'aui-base',
							'aui-form-validator'
						]
					},
					'liferay-form-placeholders': {
						condition: {
							name: 'liferay-form-placeholders',
							test: function(A) {
								return !('placeholder' in INPUT_EL);
							},
							trigger: 'liferay-form'
						},
						path: 'form_placeholders.js',
						requires: [
							'liferay-form',
							'plugin'
						]
					},
					'liferay-fullscreen-source-editor': {
						path: 'fullscreen_source_editor.js',
						requires: [
							'liferay-source-editor'
						]
					},
					'liferay-history': {
						path: 'history.js',
						requires: [
							'history-hash',
							'querystring-parse-simple'
						]
					},
					'liferay-history-html5': {
						condition: {
							name: 'liferay-history-html5',
							test: testHistory,
							trigger: 'liferay-history'
						},
						path: 'history_html5.js',
						requires: [
							'history-html5',
							'liferay-history',
							'querystring-stringify-simple'
						]
					},
					'liferay-history-manager': {
						path: 'history_manager.js',
						requires: [
							'liferay-history'
						]
					},
					'liferay-hudcrumbs': {
						path: 'hudcrumbs.js',
						requires: [
							'aui-base',
							'aui-debounce',
							'event-resize'
						]
					},
					'liferay-icon': {
						path: 'icon.js',
						requires: [
							'aui-base'
						]
					},
					'liferay-image-selector': {
						path: 'image_selector.js',
						requires: [
							'aui-base',
							'liferay-item-selector-dialog',
							'liferay-portlet-base',
							'uploader'
						]
					},
					'liferay-inline-editor-base': {
						path: 'inline_editor_base.js',
						requires: [
							'aui-base',
							'aui-overlay-base-deprecated'
						]
					},
					'liferay-input-localized': {
						path: 'input_localized.js',
						requires: [
							'aui-base',
							'aui-component',
							'aui-event-input',
							'aui-palette',
							'aui-set',
							'portal-available-languages'
						]
					},
					'liferay-input-move-boxes': {
						path: 'input_move_boxes.js',
						plugins: {
							'liferay-input-move-boxes-touch': {
								condition: {
									name: 'liferay-input-move-boxes-touch',
									trigger: 'liferay-input-move-boxes',
									ua: 'touchMobile'
								}
							}
						},
						requires: [
							'aui-base',
							'aui-toolbar'
						]
					},
					'liferay-input-move-boxes-touch': {
						path: 'input_move_boxes_touch.js',
						requires: [
							'aui-base',
							'aui-template-deprecated',
							'liferay-input-move-boxes',
							'sortable'
						]
					},
					'liferay-item-selector-dialog': {
						path: 'item_selector_dialog.js',
						requires: [
							'aui-component'
						]
					},
					'liferay-item-selector-repository-entry-browser': {
						path: 'item_selector_repository_entry_browser.js',
						requires: [
							'liferay-item-selector-uploader',
							'liferay-item-viewer',
							'liferay-notice',
							'liferay-portlet-base',
							'liferay-storage-formatter'
						]
					},
					'liferay-item-selector-uploader': {
						path: 'item_selector_uploader.js',
						requires: [
							'aui-base',
							'aui-progressbar',
							'liferay-portlet-base',
							'uploader'
						]
					},
					'liferay-item-selector-url': {
						path: 'item_selector_url.js',
						requires: [
							'liferay-item-viewer',
							'liferay-portlet-base'
						]
					},
					'liferay-item-viewer': {
						path: 'item_viewer.js',
						requires: [
							'aui-image-viewer'
						]
					},
					'liferay-language': {
						path: 'language.js'
					},
					'liferay-layout': {
						path: 'layout.js'
					},
					'liferay-layout-column': {
						path: 'layout_column.js',
						requires: [
							'aui-sortable-layout',
							'dd'
						]
					},
					'liferay-layout-customization-settings': {
						path: 'layout_customization_settings.js',
						requires: [
							'aui-base',
							'aui-io-request',
							'aui-overlay-mask-deprecated',
							'liferay-portlet-base'
						]
					},
					'liferay-layout-freeform': {
						path: 'layout_freeform.js',
						requires: [
							'liferay-layout-column',
							'resize'
						]
					},
					'liferay-layouts-tree': {
						path: 'layouts_tree.js',
						requires: [
							'aui-tree-view'
						]
					},
					'liferay-layouts-tree-check-content-display-page': {
						path: 'layouts_tree_check_content_display_page.js',
						requires: [
							'aui-component',
							'plugin'
						]
					},
					'liferay-layouts-tree-node-task': {
						path: 'layouts_tree_node_task.js',
						requires: [
							'aui-tree-node'
						]
					},
					'liferay-layouts-tree-selectable': {
						path: 'layouts_tree_selectable.js',
						requires: [
							'liferay-layouts-tree-node-task'
						]
					},
					'liferay-layouts-tree-state': {
						path: 'layouts_tree_state.js',
						requires: [
							'aui-base',
							'aui-io-request',
							'liferay-store'
						]
					},
					'liferay-list-view': {
						path: 'list_view.js',
						requires: [
							'aui-base',
							'transition'
						]
					},
					'liferay-logo-editor': {
						path: 'logo_editor.js',
						requires: [
							'aui-image-cropper',
							'aui-io-request',
							'liferay-crop-region',
							'liferay-portlet-base',
							'liferay-storage-formatter'
						]
					},
					'liferay-logo-selector': {
						path: 'logo_selector.js',
						requires: [
							'aui-base'
						]
					},
					'liferay-management-bar': {
						path: 'management_bar.js',
						requires: [
							'aui-component',
							'liferay-portlet-base'
						]
					},
					'liferay-map-base': {
						path: 'map_base.js',
						requires: [
							'aui-base'
						]
					},
					'liferay-map-google': {
						path: 'map_google.js',
						requires: [
							'liferay-map-base'
						]
					},
					'liferay-map-openstreet': {
						path: 'map_openstreet.js',
						requires: [
							'jsonp',
							'liferay-map-base',
							'timers'
						]
					},
					'liferay-menu': {
						path: 'menu.js',
						requires: [
							'aui-debounce',
							'aui-node'
						]
					},
					'liferay-menu-filter': {
						path: 'menu_filter.js',
						requires: [
							'autocomplete-base',
							'autocomplete-filters',
							'autocomplete-highlighters'
						]
					},
					'liferay-menu-toggle': {
						path: 'menu_toggle.js',
						requires: [
							'aui-node',
							'event-outside',
							'event-tap',
							'liferay-menu-filter',
							'liferay-store'
						]
					},
					'liferay-message': {
						path: 'message.js',
						requires: [
							'aui-base',
							'liferay-store'
						]
					},
					'liferay-navigation': {
						path: 'navigation.js',
						plugins: {
							'liferay-navigation-touch': {
								condition: {
									name: 'liferay-navigation-touch',
									trigger: 'liferay-navigation',
									ua: 'touch'
								}
							}
						},
						requires: [
							'aui-component',
							'event-mouseenter'
						]
					},
					'liferay-navigation-interaction': {
						path: 'navigation_interaction.js',
						plugins: {
							'liferay-navigation-interaction-touch': {
								condition: {
									name: 'liferay-navigation-interaction-touch',
									trigger: 'liferay-navigation-interaction',
									ua: 'touch'
								}
							}
						},
						requires: [
							'aui-base',
							'aui-component',
							'event-mouseenter',
							'node-focusmanager',
							'plugin'
						]
					},
					'liferay-navigation-interaction-touch': {
						path: 'navigation_interaction_touch.js',
						requires: [
							'event-tap',
							'event-touch',
							'liferay-navigation-interaction'
						]
					},
					'liferay-navigation-touch': {
						path: 'navigation_touch.js',
						requires: [
							'event-touch',
							'liferay-navigation'
						]
					},
					'liferay-node': {
						path: 'node.js',
						requires: [
							'dom-base'
						]
					},
					'liferay-notice': {
						path: 'notice.js',
						requires: [
							'aui-base',
							'transition'
						]
					},
					'liferay-pagination': {
						path: 'pagination.js',
						requires: [
							'aui-pagination'
						]
					},
					'liferay-panel-search': {
						path: 'panel_search.js',
						requires: [
							'aui-base',
							'liferay-search-filter'
						]
					},
					'liferay-poller': {
						path: 'poller.js',
						requires: [
							'aui-base',
							'io',
							'json'
						]
					},
					'liferay-portlet-base': {
						path: 'portlet_base.js',
						requires: [
							'aui-base',
							'liferay-node'
						]
					},
					'liferay-portlet-url': {
						path: 'portlet_url.js',
						requires: [
							'aui-base',
							'aui-io-request',
							'querystring-stringify-simple'
						]
					},
					'liferay-preview': {
						path: 'preview.js',
						requires: [
							'aui-base',
							'aui-overlay-mask-deprecated',
							'aui-toolbar',
							'liferay-widget-zindex'
						]
					},
					'liferay-progress': {
						path: 'progress.js',
						requires: [
							'aui-progressbar'
						]
					},
					'liferay-ratings': {
						path: 'ratings.js',
						requires: [
							'aui-io-request',
							'aui-rating'
						]
					},
					'liferay-resize-rtl': {
						condition: {
							test: function(A) {
								return document.documentElement.dir === 'rtl';
							},
							trigger: 'resize-base'
						},
						path: 'resize_rtl.js'
					},
					'liferay-restore-entry': {
						path: 'restore_entry.js',
						requires: [
							'aui-io-plugin-deprecated',
							'aui-io-request',
							'aui-modal',
							'liferay-portlet-base'
						]
					},
					'liferay-search-container': {
						path: 'search_container.js',
						requires: [
							'aui-base',
							'aui-datatable-core'
						]
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
							'plugin'
						]
					},
					'liferay-search-container-select': {
						path: 'search_container_select.js',
						requires: [
							'aui-component',
							'plugin'
						]
					},
					'liferay-search-filter': {
						path: 'search_filter.js',
						requires: [
							'aui-base',
							'autocomplete-base',
							'autocomplete-filters'
						]
					},
					'liferay-service-datasource': {
						path: 'service_datasource.js',
						requires: [
							'aui-base',
							'datasource-local'
						]
					},
					'liferay-session': {
						path: 'session.js',
						requires: [
							'aui-io-request',
							'aui-timer',
							'cookie',
							'liferay-alert'
						]
					},
					'liferay-sidebar-panel': {
						path: 'sidebar_panel.js',
						requires: [
							'aui-base',
							'aui-io-request',
							'liferay-portlet-base'
						]
					},
					'liferay-sign-in-modal': {
						path: 'sign_in_modal.js',
						requires: [
							'aui-base',
							'aui-component',
							'aui-io-request',
							'aui-parse-content',
							'liferay-form',
							'liferay-portlet-url',
							'liferay-util-window',
							'plugin'
						]
					},
					'liferay-social-bookmarks': {
						path: 'social_bookmarks.js',
						requires: [
							'aui-component',
							'aui-node'
						]
					},
					'liferay-source-editor': {
						path: 'source_editor.js',
						requires: [
							'aui-ace-editor'
						]
					},
					'liferay-storage-formatter': {
						path: 'storage_formatter.js',
						requires: [
							'aui-base',
							'datatype-number-format'
						]
					},
					'liferay-store': {
						path: 'store.js',
						requires: [
							'aui-io-request'
						]
					},
					'liferay-surface': {
						condition: {
							name: 'liferay-surface',
							test: testHistory
						},
						path: 'surface.js',
						requires: [
							'aui-surface-app',
							'aui-surface-base',
							'aui-surface-screen-html',
							'json',
							'liferay-portlet-url'
						]
					},
					'liferay-surface-app': {
						condition: {
							name: 'liferay-surface-app',
							test: testHistory
						},
						path: 'surface_app.js',
						requires: [
							'liferay-surface'
						]
					},
					'liferay-toggler-interaction': {
						path: 'toggler_interaction.js',
						requires: [
							'liferay-toggler-key-filter'
						]
					},
					'liferay-toggler-key-filter': {
						path: 'toggler_key_filter.js',
						requires: [
							'aui-event-base'
						]
					},
					'liferay-token-list': {
						path: 'token_list.js',
						requires: [
							'aui-base',
							'aui-template-deprecated'
						]
					},
					'liferay-translation-manager': {
						path: 'translation_manager.js',
						requires: [
							'aui-base'
						]
					},
					'liferay-undo-manager': {
						path: 'undo_manager.js',
						requires: [
							'aui-data-set-deprecated',
							'base'
						]
					},
					'liferay-upload': {
						path: 'upload.js',
						requires: [
							'aui-io-request',
							'aui-template-deprecated',
							'collection',
							'liferay-portlet-base',
							'liferay-storage-formatter',
							'uploader'
						]
					},
					'liferay-util-window': {
						path: 'util_window.js',
						requires: [
							'aui-dialog-iframe-deprecated',
							'aui-modal',
							'event-resize',
							'liferay-widget-zindex'
						]
					},
					'liferay-widget-size-animation-plugin': {
						path: 'widget_size_animation_plugin.js',
						requires: [
							'anim-easing',
							'plugin',
							'widget'
						]
					},
					'liferay-widget-zindex': {
						path: 'widget_zindex.js',
						requires: [
							'aui-modal',
							'plugin'
						]
					},
					'liferay-xml-formatter': {
						path: 'xml_formatter.js',
						requires: [
							'aui-base'
						]
					}
				},
				root: PATH_JAVASCRIPT + '/liferay/'
			},

			misc: {
				base: PATH_JAVASCRIPT + '/misc/',
				combine: COMBINE,
				modules: {
					'swfobject': {
						path: '/swfobject.js'
					},
					'swfupload': {
						path: '/swfupload/swfupload.js'
					}
				},
				root: PATH_JAVASCRIPT + '/misc/'
			},

			portal: {
				base: PATH_JAVASCRIPT + '/liferay/',
				combine: false,
				modules: {
					'portal-available-languages': {
						path: LiferayAUI.getAvailableLangPath(),
						requires: [
							'liferay-language'
						]
					}
				},
				root: PATH_JAVASCRIPT + '/liferay/'
			}
		},
		lang: themeDisplay.getBCP47LanguageId(),
		root: PATH_JAVASCRIPT + '/aui/',
		useBrowserConsole: false
	};

	CORE_MODULES.push('liferay-browser-selectors');
})();