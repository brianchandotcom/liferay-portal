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

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `import {AssetTagsSelector} from 'asset-taglib'`
 */
AUI.add(
	'liferay-asset-tags-selector',
	(A) => {
		const Lang = A.Lang;

		const AArray = A.Array;

		const LString = Lang.String;

		const CSS_INPUT_NODE = 'lfr-tag-selector-input';

		const CSS_NO_MATCHES = 'no-matches';

		const CSS_POPUP = 'lfr-tag-selector-popup';

		const CSS_TAGS_LIST = 'lfr-tags-selector-list';

		const MAP_INVALID_CHARACTERS = AArray.hash([
			'"',
			'#',
			'%',
			'&',
			'*',
			'+',
			',',
			'/',
			':',
			';',
			'<',
			'=',
			'>',
			'?',
			'@',
			'[',
			"'",
			'\\',
			'\n',
			'\r',
			']',
			'`',
			'{',
			'|',
			'}',
			'~',
		]);

		const NAME = 'tagselector';

		const STR_BLANK = '';

		const TPL_CHECKED = ' checked="checked" ';

		const TPL_LOADING = '<div class="loading-animation" />';

		const TPL_SEARCH_FORM =
			'<form action="javascript:void(0);" class="form-search lfr-tag-selector-search">' +
			'<input class="form-control lfr-tag-selector-input search-query" placeholder="{0}" type="text" />' +
			'</form>';

		const TPL_TAG = new A.Template(
			'<div class="lfr-tag-selector-tags {[(!values.tags || !values.tags.length) ? "',
			CSS_NO_MATCHES,
			'" : "',
			STR_BLANK,
			'" ]}">',
			'<tpl for="tags">',
			'<label class="checkbox" title="{name}"><input {checked} type="checkbox" value="{name}" /> <span class="lfr-tag-text">{name}</span></label>',
			'</tpl>',

			'<div class="lfr-tag-message">{message}</div>',
			'</div>'
		);

		const TPL_TAGS_CONTAINER = '<div class="' + CSS_TAGS_LIST + '"></div>';

		/**
		 * OPTIONS
		 *
		 * Required
		 * className (string): The class name of the current asset.
		 * curEntries (string): The current tags.
		 * instanceVar {string}: The instance variable for this class.
		 * hiddenInput {string}: The hidden input used to pass in the current tags.
		 * textInput {string}: The text input for users to add tags.
		 * summarySpan {string}: The summary span to show the current tags.
		 *
		 * Optional
		 * focus {boolean}: Whether the text input should be focused.
		 * portalModelResource {boolean}: Whether the asset model is on the portal level.
		 */

		const AssetTagsSelector = A.Component.create({
			ATTRS: {
				allowAddEntry: {
					value: true,
				},

				allowAnyEntry: {
					value: true,
				},

				className: {
					value: null,
				},

				curEntries: {
					setter(value) {
						if (Lang.isString(value)) {
							value = value.split(',');
						}

						return value;
					},
					value: '',
				},

				dataSource: {
					valueFn() {
						const instance = this;

						return instance._getTagsDataSource();
					},
				},

				groupIds: {
					setter: '_setGroupIds',
					validator: Lang.isString,
				},

				guid: {
					value: '',
				},

				hiddenInput: {
					setter(value) {
						const instance = this;

						return A.one(value + instance.get('guid'));
					},
				},

				instanceVar: {
					value: '',
				},

				matchKey: {
					value: 'value',
				},

				portalModelResource: {
					value: false,
				},

				schema: {
					value: {
						resultFields: ['text', 'value'],
					},
				},
			},

			EXTENDS: A.TextboxList,

			NAME,

			prototype: {
				_addEntries() {
					const instance = this;

					const text = LString.escapeHTML(instance.inputNode.val());

					if (text) {
						if (text.indexOf(',') > -1) {
							const items = text.split(',');

							items.forEach((item) => {
								instance.entries.add(item, {});
							});
						}
						else {
							instance.entries.add(text, {});
						}
					}

					Liferay.Util.focusFormField(instance.inputNode);
				},

				_bindTagsSelector() {
					const instance = this;

					const form = instance.inputNode.get('form');

					instance._submitFormListener = A.Do.before(
						instance._addEntries,
						form,
						'submit',
						instance
					);

					instance
						.get('boundingBox')
						.on('keypress', instance._onKeyPress, instance);
				},

				_getEntries(callback) {
					const instance = this;

					Liferay.Service(
						'/assettag/get-groups-tags',
						{
							groupIds: instance.get('groupIds'),
						},
						callback
					);
				},

				_getPopup() {
					const instance = this;

					if (!instance._popup) {
						const popup = Liferay.Util.getTop().Liferay.Util.Window.getWindow(
							{
								dialog: {
									cssClass: CSS_POPUP,
									hideClass: 'hide-accessible sr-only',
									width: 600,
								},
							}
						);

						const bodyNode = popup.bodyNode;

						bodyNode.html(STR_BLANK);

						const searchForm = A.Node.create(
							Lang.sub(TPL_SEARCH_FORM, [
								Liferay.Language.get('search'),
							])
						);

						bodyNode.append(searchForm);

						const searchField = searchForm.one('input');

						const entriesNode = A.Node.create(TPL_TAGS_CONTAINER);

						bodyNode.append(entriesNode);

						popup.searchField = searchField;
						popup.entriesNode = entriesNode;

						instance._popup = popup;

						instance._initSearch();

						const onCheckboxClick = A.bind(
							'_onCheckboxClick',
							instance
						);

						entriesNode.delegate(
							'click',
							onCheckboxClick,
							'input[type=checkbox]'
						);
					}

					return instance._popup;
				},

				_getTagsDataSource() {
					const instance = this;

					const AssetTagSearch = Liferay.Service.bind(
						'/assettag/search'
					);

					AssetTagSearch._serviceQueryCache = {};

					const serviceQueryCache = AssetTagSearch._serviceQueryCache;

					const dataSource = new Liferay.Service.DataSource({
						on: {
							request(event) {
								let term = decodeURIComponent(event.request);

								const key = term;

								if (term === '*') {
									term = STR_BLANK;
								}

								let serviceQueryObj = serviceQueryCache[key];

								if (!serviceQueryObj) {
									serviceQueryObj = {
										end: 20,
										groupIds: instance.get('groupIds'),
										name: '%' + term + '%',
										start: 0,
										tagProperties: STR_BLANK,
									};

									serviceQueryCache[key] = serviceQueryObj;
								}

								event.request = serviceQueryObj;
							},
						},
						source: AssetTagSearch,
					}).plug(A.Plugin.DataSourceCache, {
						max: 500,
					});

					return dataSource;
				},

				_initSearch() {
					const instance = this;

					const popup = instance._popup;

					popup.liveSearch = new A.LiveSearch({
						after: {
							search() {
								const fieldsets = popup.entriesNode.all(
									'fieldset'
								);

								fieldsets.each((item) => {
									const visibleEntries = item.one(
										'label:not(.hide)'
									);

									let action = 'addClass';

									if (visibleEntries) {
										action = 'removeClass';
									}

									item[action](CSS_NO_MATCHES);
								});
							},
						},
						data(node) {
							const value = node.attr('title');

							return value.toLowerCase();
						},
						input: popup.searchField,
						nodes: '.' + CSS_TAGS_LIST + ' label',
					});
				},

				_namespace(name) {
					const instance = this;

					return (
						instance.get('instanceVar') +
						name +
						instance.get('guid')
					);
				},

				_onAddEntryClick(event) {
					const instance = this;

					event.domEvent.preventDefault();

					instance._addEntries();
				},

				_onCheckboxClick(event) {
					const instance = this;

					const checkbox = event.currentTarget;
					const checked = checkbox.get('checked');
					const value = checkbox.val();

					let action = 'remove';

					if (checked) {
						action = 'add';
					}

					instance[action](value);
				},

				_onKeyPress(event) {
					const instance = this;

					const charCode = event.charCode;

					if (!A.UA.gecko || event._event.charCode) {
						if (Number(charCode) === 44) {
							event.preventDefault();

							instance._addEntries();
						}
						else if (
							MAP_INVALID_CHARACTERS[
								String.fromCharCode(charCode)
							]
						) {
							event.halt();
						}
					}
				},

				_renderIcons() {
					const instance = this;

					const contentBox = instance.get('contentBox');

					const buttonGroup = [
						{
							label: Liferay.Language.get('select'),
							on: {
								click: A.bind('_showSelectPopup', instance),
							},
							title: Liferay.Language.get('select-tags'),
						},
					];

					if (instance.get('allowAddEntry')) {
						buttonGroup.unshift({
							label: Liferay.Language.get('add'),
							on: {
								click: A.bind('_onAddEntryClick', instance),
							},
							title: Liferay.Language.get('add-tags'),
						});
					}

					instance.icons = new A.Toolbar({
						children: [buttonGroup],
					}).render(contentBox);

					const iconsBoundingBox = instance.icons.get('boundingBox');

					instance.entryHolder.placeAfter(iconsBoundingBox);
				},

				_renderTemplate(data) {
					const instance = this;

					const popup = instance._popup;

					TPL_TAG.render(
						{
							checked: data.checked,
							message: Liferay.Language.get('no-tags-were-found'),
							name: data.name,
							tags: data,
						},
						popup.entriesNode
					);

					popup.searchField.val('');

					popup.liveSearch.get('nodes').refresh();

					popup.liveSearch.refreshIndex();
				},

				_setGroupIds(value) {
					return value.split(',');
				},

				_showPopup(event) {
					const instance = this;

					event.domEvent.preventDefault();

					const popup = instance._getPopup();

					popup.entriesNode.append(A.Node.create(TPL_LOADING));

					popup.show();
				},

				_showSelectPopup(event) {
					const instance = this;

					instance._showPopup(event);

					instance._popup.titleNode.html(
						Liferay.Language.get('tags')
					);

					instance._getEntries((entries) => {
						instance._updateSelectList(entries);
					});
				},

				_updateHiddenInput(event) {
					const instance = this;

					const hiddenInput = instance.get('hiddenInput');

					hiddenInput.val(instance.entries.keys.join());

					const popup = instance._popup;

					if (popup && popup.get('visible')) {
						const checkbox = popup.bodyNode.one(
							'input[value=' + event.attrName + ']'
						);

						if (checkbox) {
							let checked = false;

							if (event.type === 'dataset:add') {
								checked = true;
							}

							checkbox.attr('checked', checked);
						}
					}
				},

				_updateSelectList(data) {
					const instance = this;

					for (let i = 0; i < data.length; i++) {
						const tag = data[i];

						tag.checked =
							instance.entries.indexOfKey(tag.name) > -1
								? TPL_CHECKED
								: STR_BLANK;
					}

					instance._renderTemplate(data);
				},

				addEntries() {
					const instance = this;

					instance._addEntries();
				},

				bindUI() {
					const instance = this;

					AssetTagsSelector.superclass.bindUI.apply(
						instance,
						arguments
					);

					instance._bindTagsSelector();

					const entries = instance.entries;

					entries.after('add', instance._updateHiddenInput, instance);
					entries.after(
						'remove',
						instance._updateHiddenInput,
						instance
					);
				},

				renderUI() {
					const instance = this;

					AssetTagsSelector.superclass.renderUI.apply(
						instance,
						arguments
					);

					instance._renderIcons();

					instance.inputNode.addClass(CSS_INPUT_NODE);

					instance._overlayAlign.node = instance.entryHolder;
				},

				syncUI() {
					const instance = this;

					AssetTagsSelector.superclass.syncUI.apply(
						instance,
						arguments
					);

					const curEntries = instance.get('curEntries');

					curEntries.forEach(instance.add, instance);
				},
			},
		});

		Liferay.AssetTagsSelector = AssetTagsSelector;
	},
	'',
	{
		requires: [
			'array-extras',
			'async-queue',
			'aui-autocomplete-deprecated',
			'aui-io-plugin-deprecated',
			'aui-live-search-deprecated',
			'aui-template-deprecated',
			'aui-textboxlist',
			'datasource-cache',
			'liferay-service-datasource',
			'liferay-util-window',
		],
	}
);
