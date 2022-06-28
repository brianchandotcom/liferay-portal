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
	'liferay-asset-addon-entry-selector',
	(A) => {
		const Lang = A.Lang;

		const NAME = 'assetaddonentryselector';

		const STR_ASSET_ADDON_ENTRIES = 'assetAddonEntries';

		const STR_BLANK = '';

		const STR_CHECKED = 'checked';

		const STR_CLICK = 'click';

		const STR_DATA_KEY = 'data-key';

		const STR_INPUT = 'input';

		const STR_SELECTED_ASSET_ADDON_ENTRIES = 'selectedAssetAddonEntries';

		const TPL_SELECT_LIST =
			'<ul class="list-inline list-unstyled row">{entries}</ul>';

		const TPL_STR_SELECTED_ASSET_ADDON_ENTRY =
			'<li class="col-md-6 form-check form-check-card">' +
			'<label class="form-check-label">' +
			'<input {checked} class="form-check-input sr-only" data-key={key} data-label={label} type="checkbox">' +
			'<div class="card card-horizontal card-type-directory">' +
			'<div class="card-body">' +
			'<div class="card-row">' +
			'<div class="autofit-col">' +
			'<span class="sticker">' +
			Liferay.Util.getLexiconIconTpl('{icon}') +
			'</span>' +
			'</div>' +
			'<div class="autofit-col autofit-col-expand autofit-col-gutters">' +
			'<section class="autofit-section">' +
			'<h3 class="card-title" title="{label}">' +
			'<span class="text-truncate-inline">' +
			'<span class="text-truncate">{label}</span>' +
			'</span>' +
			'</h3>' +
			'</section>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</label>' +
			'</li>';

		const TPL_SUMMARY_ASSET_ADDON_ENTRY =
			'<li class="col-md-4 list-entry" data-key="{key}" data-label="{label}">' +
			'<div class="card card-horizontal card-type-directory">' +
			'<div class="card-body">' +
			'<div class="card-row">' +
			'<div class="autofit-col autofit-col-expand autofit-col-gutters">' +
			'<section class="autofit-section">' +
			'<h3 class="card-title" title="{label}">' +
			'<span class="text-truncate-inline">' +
			'<span class="text-truncate">{label}</span>' +
			'</span>' +
			'</h3>' +
			'</section>' +
			'</div>' +
			'<div class="autofit-col">' +
			'<a class="remove-button" href="javascript:void(0);">' +
			Liferay.Util.getLexiconIconTpl('times') +
			'</a>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</li>';

		const AssetAddonEntrySelector = A.Component.create({
			ATTRS: {
				assetAddonEntries: {
					setter: '_setAssetAddonEntries',
					validator: Array.isArray,
				},

				dialogTitle: {
					validator: Lang.isString,
					value: Liferay.Language.get('select-entries'),
				},

				selectedAssetAddonEntries: {
					validator: Array.isArray,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME,

			prototype: {
				_bindUI() {
					const instance = this;

					instance._eventHandles = [
						instance.after(
							'selectedAssetAddonEntriesChange',
							instance._syncUI,
							instance
						),
						instance
							.one('.select-button')
							.on(STR_CLICK, instance._onSelectClick, instance),
						instance
							.one('.selected-entries')
							.delegate(
								STR_CLICK,
								instance._onSummaryItemRemove,
								'.remove-button',
								instance
							),
					];
				},

				_getSelectDialog() {
					const instance = this;

					let dialog = instance._dialog;

					if (!dialog) {
						const dialogConfig = {
							'autoHeightRatio': 0.5,
							'toolbars.footer': instance._getSelectDialogFooterToolbar(),
							'width': 540,
						};

						dialog = Liferay.Util.getTop().Liferay.Util.Window.getWindow(
							{
								dialog: dialogConfig,
								id: instance._dialogId,
								title: instance.get('dialogTitle'),
							}
						);

						dialog.setStdModContent(
							'body',
							instance._selectDialogContent
						);

						instance._dialog = dialog;
					}

					return dialog;
				},

				_getSelectDialogContent() {
					const instance = this;

					const selectedAssetAddonEntries = instance.get(
						STR_SELECTED_ASSET_ADDON_ENTRIES
					);

					const entriesContent = instance
						.get(STR_ASSET_ADDON_ENTRIES)
						.reduce((previousValue, currentValue) => {
							currentValue.checked =
								selectedAssetAddonEntries.indexOf(
									currentValue.key
								) !== -1
									? STR_CHECKED
									: STR_BLANK;

							return (
								previousValue +
								Lang.sub(
									TPL_STR_SELECTED_ASSET_ADDON_ENTRY,
									currentValue
								)
							);
						}, STR_BLANK);

					const content = Lang.sub(TPL_SELECT_LIST, {
						entries: entriesContent,
					});

					return A.Node.create(content);
				},

				_getSelectDialogFooterToolbar() {
					const instance = this;

					const footerToolbar = [
						{
							label: Liferay.Language.get('cancel'),
							on: {
								click: A.bind('_hideSelectDialog', instance),
							},
						},
						{
							cssClass: 'btn-primary',
							label: Liferay.Language.get('done'),
							on: {
								click: A.bind(
									'_updateSelectedEntries',
									instance
								),
							},
						},
					];

					return footerToolbar;
				},

				_hideSelectDialog() {
					const instance = this;

					instance._getSelectDialog().hide();
				},

				_onSelectClick() {
					const instance = this;

					instance._showSelectDialog();
				},

				_onSummaryItemRemove(event) {
					const instance = this;

					let selectedAssetAddonEntries = instance.get(
						STR_SELECTED_ASSET_ADDON_ENTRIES
					);

					const removedItem = event.currentTarget
						.ancestor('.list-entry')
						.attr(STR_DATA_KEY);

					selectedAssetAddonEntries = selectedAssetAddonEntries.filter(
						(item) => {
							return item !== removedItem;
						}
					);

					instance.set(
						STR_SELECTED_ASSET_ADDON_ENTRIES,
						selectedAssetAddonEntries
					);
				},

				_setAssetAddonEntries(val) {
					const instance = this;

					const entriesMap = {};

					val.forEach((item) => {
						entriesMap[item.key] = item;
					});

					instance._entriesMap = entriesMap;
				},

				_showSelectDialog() {
					const instance = this;

					instance._syncUI();
					instance._getSelectDialog().show();
				},

				_syncUI() {
					const instance = this;

					instance.get(STR_ASSET_ADDON_ENTRIES);

					const selectedAssetAddonEntries = instance.get(
						STR_SELECTED_ASSET_ADDON_ENTRIES
					);

					const selectedAssetAddonEntriesNode = instance.one(
						'.selected-entries'
					);

					selectedAssetAddonEntriesNode.empty();

					instance._selectDialogContent
						.all(STR_INPUT)
						.attr(STR_CHECKED, false);

					selectedAssetAddonEntries.forEach((item) => {
						selectedAssetAddonEntriesNode.append(
							Lang.sub(
								TPL_SUMMARY_ASSET_ADDON_ENTRY,
								instance._entriesMap[item]
							)
						);

						instance._selectDialogContent
							.one('input[data-key="' + item + '"]')
							.attr(STR_CHECKED, true);
					});

					instance
						.one(STR_INPUT)
						.val(selectedAssetAddonEntries.join(','));
				},

				_updateSelectedEntries() {
					const instance = this;

					const dialog = instance._getSelectDialog();

					const selectedAssetAddonEntries = [];

					dialog.bodyNode.all('input:checked').each((item) => {
						selectedAssetAddonEntries.push(item.attr(STR_DATA_KEY));
					});

					instance.set(
						STR_SELECTED_ASSET_ADDON_ENTRIES,
						selectedAssetAddonEntries
					);

					instance._hideSelectDialog();
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					instance._dialogId = A.guid();
					instance._selectDialogContent = instance._getSelectDialogContent();

					instance._bindUI();
				},
			},
		});

		Liferay.AssetAddonEntrySelector = AssetAddonEntrySelector;
	},
	'',
	{
		requires: [
			'aui-component',
			'liferay-portlet-base',
			'liferay-util-window',
		],
	}
);
