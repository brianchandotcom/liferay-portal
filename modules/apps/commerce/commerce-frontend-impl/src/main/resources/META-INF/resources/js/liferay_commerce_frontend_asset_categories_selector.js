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
	'liferay-commerce-frontend-asset-categories-selector',
	(A) => {
		const Lang = A.Lang;

		const LString = Lang.String;

		const BOUNDING_BOX = 'boundingBox';

		const EMPTY_FN = Lang.emptyFn;

		const ID = 'id';

		const NAME = 'categoriesselector';

		/**
		 * OPTIONS
		 *
		 * Required
		 * categoryIds (string): The ids of the current categories.
		 * categoryTitles (string): The names of the current categories.
		 * hiddenInput {string}: The hidden input used to pass in the current categories.
		 * instanceVar {string}: The instance variable for this class.
		 * labelNode {String|A.Node}: The node of the label element for this selector.
		 * title {String}: The title of the button element for this selector.
		 * vocabularyIds (string): The ids of the vocabularies.
		 *
		 * Optional
		 * maxEntries {Number}: The maximum number of entries that will be loaded. The default value is -1, which will load all categories.
		 * moreResultsLabel {String}: The localized label for link "Load more results".
		 */

		const AssetTaglibCategoriesSelector = A.Component.create({
			ATTRS: {
				categoryIds: {
					setter(value) {
						if (Lang.isString(value)) {
							value = value.split(',');
						}

						return value;
					},
					validator: '_isValidEntries',
					value: [],
				},

				categoryTitles: {
					setter(value) {
						if (Lang.isString(value)) {
							value = value.split('_CATEGORY_');
						}

						return value;
					},
					value: [],
				},

				label: {
					validator: '_isValidString',
					value: Liferay.Language.get('select'),
				},

				labelNode: {
					setter(value) {
						return A.one(value) || A.Attribute.INVALID_VALUE;
					},
					value: null,
				},

				maxEntries: {
					validator: Lang.isNumber,
					value: -1,
				},

				moreResultsLabel: {
					validator: '_isValidString',
					value: Liferay.Language.get('load-more-results'),
				},

				singleSelect: {
					validator: Lang.isBoolean,
					value: false,
				},

				title: {
					validator: '_isValidString',
					value: Liferay.Language.get('select-categories'),
				},

				vocabularyIds: {
					validator: '_isValidString',
					value: null,
				},
			},

			EXTENDS: Liferay.AssetTaglibTagsSelector,

			NAME,

			prototype: {
				_afterTBLFocusedChange: EMPTY_FN,

				_applyARIARoles() {
					const instance = this;

					const labelNode = instance.get('labelNode');

					if (labelNode) {
						const boundingBox = instance.get(BOUNDING_BOX);

						boundingBox.attr('aria-labelledby', labelNode.attr(ID));

						labelNode.attr('for', boundingBox.attr(ID));
					}
				},

				_bindTagsSelector: EMPTY_FN,

				_isValidEntries(value) {
					return (
						(Lang.isString(value) && value !== '') ||
						Lang.isArray(value)
					);
				},

				_isValidString(value) {
					return Lang.isString(value) && value.length;
				},

				_onBoundingBoxClick: EMPTY_FN,

				_renderIcons() {
					const instance = this;

					const contentBox = instance.get('contentBox');

					if (instance.get('portletURL')) {
						instance.icons = new A.Toolbar({
							children: [
								{
									label: instance.get('label'),
									on: {
										click(event) {
											event.data = event.data
												? event.data
												: {};

											instance._showSelectPopup(event);
										},
									},
									title: instance.get('title'),
								},
							],
						}).render(contentBox);
					}

					const iconsBoundingBox = instance.icons.get(BOUNDING_BOX);

					instance.entryHolder.placeAfter(iconsBoundingBox);
				},

				_showSelectPopup(event) {
					const instance = this;

					event.domEvent.preventDefault();

					instance.set('categoryIds', instance.entries.keys);

					const uri = Lang.sub(
						decodeURIComponent(instance.get('portletURL')),
						{
							selectedCategories: instance.get('categoryIds'),
							singleSelect: instance.get('singleSelect'),
							vocabularyIds: instance.get('vocabularyIds'),
						}
					);

					const openerWindow = Liferay.Util.getOpener();

					openerWindow.Liferay.Util.openSelectionModal({
						buttonAddLabel: Liferay.Language.get('add'),
						multiple: true,
						onSelect: (selectedItems) => {
							if (selectedItems) {
								for (const key in selectedItems) {
									let found = false;

									instance.entries.each((item) => {
										if (key === item.categoryId) {
											found = true;
										}

										if (
											key === item.categoryId &&
											selectedItems[key].unchecked
										) {
											instance.entries.remove(item);
										}
									});

									selectedItems[key][0] = key;

									if (
										!found &&
										!selectedItems[key].unchecked
									) {
										instance.entries.add(
											selectedItems[key]
										);
									}
								}
							}

							instance.set('categoryIds', instance.entries.keys);

							instance._updateInputHidden();
						},
						selectEventName: instance.get('eventName'),
						title: Liferay.Language.get('select-categories'),
						url: uri,
					});
				},

				_updateInputHidden() {
					const instance = this;

					const hiddenInput = instance.get('hiddenInput');

					hiddenInput.val(instance.entries.keys.join(','));
				},

				TREEVIEWS: {},
				UI_EVENTS: {},

				bindUI() {
					const instance = this;

					AssetTaglibCategoriesSelector.superclass.bindUI.apply(
						instance,
						arguments
					);
				},

				renderUI() {
					const instance = this;

					AssetTaglibCategoriesSelector.superclass.constructor.superclass.renderUI.apply(
						instance,
						arguments
					);

					instance._renderIcons();

					instance.inputContainer.addClass('hide-accessible sr-only');

					instance._applyARIARoles();
				},

				syncUI() {
					const instance = this;

					AssetTaglibCategoriesSelector.superclass.constructor.superclass.syncUI.apply(
						instance,
						arguments
					);

					instance.entries.getKey = function (object) {
						return object.categoryId;
					};

					const categoryTitles = instance.get('categoryTitles');

					const categoryIds = instance.get('categoryIds');

					categoryIds.forEach((item, index) => {
						const entry = {
							categoryId: item,
							value: LString.unescapeHTML(categoryTitles[index]),
						};

						instance.entries.add(entry);
					});
				},
			},
		});

		Liferay.AssetTaglibCategoriesSelector = AssetTaglibCategoriesSelector;
	},
	'',
	{
		requires: ['aui-tree', 'liferay-commerce-frontend-asset-tag-selector'],
	}
);
