;(function() {
	var LiferayAUI = Liferay.AUI;
	var Browser = Liferay.Browser;

	var COMBINE = LiferayAUI.getCombine();

	var CORE_MODULES = YUI.Env.core;

	var INPUT_EL = document.createElement('input');

	var PATH_JAVASCRIPT = LiferayAUI.getJavaScriptRootPath();

	var SUPPORTS_INPUT_SELECTION = ((typeof INPUT_EL.selectionStart === 'number') && (typeof INPUT_EL.selectionEnd === 'number'));

	var testHistory = function(A) {
		var WIN = A.config.win;

		var HISTORY = WIN.history;

		return (HISTORY &&
				HISTORY.pushState &&
				HISTORY.replaceState &&
				('onpopstate' in WIN || A.UA.gecko >= 2));
	};

	window.YUI_config = {
		base: PATH_JAVASCRIPT + '/aui/',
		combine: COMBINE,
		comboBase: LiferayAUI.getComboPath(),
		filter: Liferay.AUI.getFilter(),
		groups: {
			editor: {
				base: PATH_JAVASCRIPT + '/editor/',
				combine: COMBINE,
				modules: {
					'inline-editor-ckeditor': {
						path: 'ckeditor/main.js'
					}
				},
				root: PATH_JAVASCRIPT + '/editor/'
			},

			liferay: {
				base: PATH_JAVASCRIPT + '/liferay/',
				combine: COMBINE,
				modules: {
					'liferay-app-view-move': {
						path: 'app_view_move.js',
						plugins: {
							'liferay-app-view-move-touch': {
								condition: {
									name: 'liferay-app-view-move-touch',
									trigger: 'liferay-app-view-move',
									ua: 'touch'
								}
							}
						},
						requires: [
							'aui-base',
							'dd-constrain',
							'dd-delegate',
							'dd-drag',
							'dd-drop',
							'dd-proxy',
							'liferay-history-manager',
							'liferay-portlet-base',
							'liferay-util-list-fields'
						]
					},
					'liferay-app-view-move-touch': {
						path: 'app_view_move_touch.js',
						requires: [
							'aui-base'
						]
					},
					'liferay-app-view-select': {
						path: 'app_view_select.js',
						requires: [
							'liferay-app-view-move',
							'liferay-history-manager',
							'liferay-portlet-base',
							'liferay-util-list-fields'
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
								return (A.UA.ie && A.UA.ie < 9);
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
					'liferay-dockbar': {
						path: 'dockbar.js',
						requires: [
							'aui-node',
							'event-touch'
						]
					},
					'liferay-dockbar-add-application': {
						path: 'dockbar_add_application.js',
						requires: [
							'aui-io-request',
							'event-key',
							'event-mouseenter',
							'liferay-dockbar',
							'liferay-dockbar-add-base',
							'liferay-panel-search',
							'liferay-portlet-base',
							'liferay-toggler-interaction'
						]
					},
					'liferay-dockbar-add-base': {
						path: 'dockbar_add_base.js',
						requires: [
							'anim',
							'aui-base',
							'liferay-dockbar',
							'liferay-layout',
							'transition'
						]
					},
					'liferay-dockbar-add-content': {
						path: 'dockbar_add_content.js',
						requires: [
							'aui-io-request',
							'event-mouseenter',
							'liferay-dockbar',
							'liferay-dockbar-add-content-preview',
							'liferay-dockbar-add-content-search',
							'liferay-portlet-base'
						]
					},
					'liferay-dockbar-add-content-preview': {
						path: 'dockbar_add_content_preview.js',
						requires: [
							'aui-debounce',
							'aui-io-request',
							'event-mouseenter'
						]
					},
					'liferay-dockbar-add-content-search': {
						path: 'dockbar_add_content_search.js',
						requires: [
							'aui-base',
							'liferay-dockbar',
							'liferay-search-filter'
						]
					},
					'liferay-dockbar-add-page': {
						path: 'dockbar_add_page.js',
						requires: [
							'aui-loading-mask-deprecated',
							'aui-parse-content',
							'aui-toggler-delegate',
							'liferay-dockbar',
							'liferay-dockbar-add-base',
							'liferay-dockbar-add-page-search',
							'liferay-portlet-base',
							'liferay-toggler-key-filter'
						]
					},
					'liferay-dockbar-add-page-search': {
						path: 'dockbar_add_page_search.js',
						requires: [
							'aui-base',
							'liferay-dockbar',
							'liferay-search-filter'
						]
					},
					'liferay-dockbar-device-preview': {
						path: 'dockbar_device_preview.js',
						requires: [
							'aui-dialog-iframe-deprecated',
							'aui-event-input',
							'aui-modal',
							'liferay-portlet-base',
							'liferay-util-window',
							'liferay-widget-size-animation-plugin'
						]
					},
					'liferay-dockbar-keyboard-interaction': {
						path: 'dockbar_keyboard_interaction.js',
						requires: [
							'node-focusmanager',
							'plugin'
						]
					},
					'liferay-dockbar-portlet-dd': {
						condition: {
							name: 'liferay-dockbar-portlet-dd',
							test: function(A) {
								return !A.UA.mobile;
							},
							trigger: ['liferay-dockbar-add-application', 'liferay-dockbar-add-content']
						},
						path: 'dockbar_portlet_dd.js',
						requires: [
							'aui-base',
							'dd',
							'liferay-dockbar',
							'liferay-layout',
							'liferay-layout-column',
							'liferay-layout-freeform',
							'liferay-portlet-base'
						]
					},
					'liferay-dockbar-underlay': {
						path: 'dockbar_underlay.js',
						requires: [
							'aui-button',
							'aui-io-plugin-deprecated',
							'aui-overlay-manager-deprecated'
						]
					},
					'liferay-dynamic-select': {
						path: 'dynamic_select.js',
						requires: [
							'aui-base'
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
							'aui-base'
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
					'liferay-look-and-feel': {
						path: 'look_and_feel.js',
						requires: [
							'aui-color-picker-popover',
							'aui-io-plugin-deprecated',
							'aui-io-request',
							'aui-modal',
							'aui-tabview'
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
							'aui-datatable-core',
							'event-mouseenter'
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
							'liferay-notice'
						]
					},
					'liferay-sign-in-modal': {
						path: 'sign_in_modal.js',
						requires: [
							'aui-base',
							'aui-component',
							'aui-io-request',
							'aui-parse-content',
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
					'liferay-util-list-fields': {
						path: 'util_list_fields.js',
						requires: [
							'aui-base'
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

	if (Browser.isIe() && Browser.getMajorVersion() < 9) {
		CORE_MODULES.push('aui-base-html5-shiv');
	}

	CORE_MODULES.push('liferay-browser-selectors');
})();