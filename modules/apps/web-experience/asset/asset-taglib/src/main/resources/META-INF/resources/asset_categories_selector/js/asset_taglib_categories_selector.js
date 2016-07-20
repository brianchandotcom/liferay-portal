AUI.add(
	'liferay-asset-taglib-categories-selector',
	function(A) {
		var Lang = A.Lang;

		var LString = Lang.String;

		var BOUNDING_BOX = 'boundingBox';

		var EMPTY_FN = Lang.emptyFn;

		var ID = 'id';

		var NAME = 'categoriesselector';

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

		var AssetTaglibCategoriesSelector = A.Component.create(
			{
				ATTRS: {
					categoryIds: {
						setter: function(value) {
							if (Lang.isString(value)) {
								value = value.split(',');
							}

							return value;
						},
						validator: '_isValidEntries',
						value: []
					},

					categoryTitles: {
						setter: function(value) {
							if (Lang.isString(value)) {
								value = value.split('_CATEGORY_');
							}

							return value;
						},
						value: []
					},

					label: {
						validator: '_isValidString',
						value: Liferay.Language.get('select')
					},

					labelNode: {
						setter: function(value) {
							return A.one(value) || A.Attribute.INVALID_VALUE;
						},
						value: null
					},

					maxEntries: {
						validator: Lang.isNumber,
						value: -1
					},

					moreResultsLabel: {
						validator: '_isValidString',
						value: Liferay.Language.get('load-more-results')
					},

					singleSelect: {
						validator: Lang.isBoolean,
						value: false
					},

					title: {
						validator: '_isValidString',
						value: Liferay.Language.get('select-categories')
					},

					vocabularyIds: {
						validator: '_isValidString',
						value: null
					}
				},

				EXTENDS: Liferay.AssetTaglibTagsSelector,

				NAME: NAME,

				prototype: {
					TREEVIEWS: {},
					UI_EVENTS: {},

					renderUI: function() {
						var instance = this;

						AssetTaglibCategoriesSelector.superclass.constructor.superclass.renderUI.apply(instance, arguments);

						instance._renderIcons();

						instance.inputContainer.addClass('hide-accessible');

						instance._applyARIARoles();
					},

					bindUI: function() {
						var instance = this;

						AssetTaglibCategoriesSelector.superclass.bindUI.apply(instance, arguments);
					},

					syncUI: function() {
						var instance = this;

						AssetTaglibCategoriesSelector.superclass.constructor.superclass.syncUI.apply(instance, arguments);

						instance.entries.getKey = function(obj) {
							return obj.categoryId;
						};

						var categoryTitles = instance.get('categoryTitles');

						var categoryIds = instance.get('categoryIds');

						categoryIds.forEach(
							function(item, index) {
								var entry = {
									categoryId: item
								};

								entry.value = LString.unescapeHTML(categoryTitles[index]);

								instance.add(entry.value);
							}
						);
					},

					_afterTBLFocusedChange: EMPTY_FN,

					_applyARIARoles: function() {
						var instance = this;

						var labelNode = instance.get('labelNode');

						if (labelNode) {
							var boundingBox = instance.get(BOUNDING_BOX);

							boundingBox.attr('aria-labelledby', labelNode.attr(ID));

							labelNode.attr('for', boundingBox.attr(ID));
						}
					},

					_bindTagsSelector: EMPTY_FN,

					_isValidEntries: function(value) {
						return (Lang.isString(value) && value !== '') || Lang.isArray(value);
					},

					_isValidString: function(value) {
						return Lang.isString(value) && value.length;
					},

					_onBoundingBoxClick: EMPTY_FN,

					_renderIcons: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						if (instance.get('portletURL')) {
							instance.icons = new A.Toolbar(
								{
									children: [
										{
											label: instance.get('label'),
											on: {
												click: function(event) {
													event.data = event.data ? event.data : {};

													instance._showSelectPopup.call(instance, event);
												}
											},
											title: instance.get('title')
										}
									]
								}
							).render(contentBox);
						}

						var iconsBoundingBox = instance.icons.get(BOUNDING_BOX);

						instance.entryHolder.placeAfter(iconsBoundingBox);
					},

					_showSelectPopup: function(event) {
						var instance = this;

						event.domEvent.preventDefault();

						instance.set('categoryIds', instance.entries.keys);

						var uri = Lang.sub(
							decodeURIComponent(instance.get('portletURL')),
							{
								selectedCategories: instance.get('categoryIds'),
								singleSelect: instance.get('singleSelect'),
								vocabularyIds: instance.get('vocabularyIds')
							}
						);

						var itemSelectorDialog = new A.LiferayItemSelectorDialog(
							{
								eventName: instance.get('eventName'),
								on: {
									selectedItemChange: function(event) {
										var data = event.newVal;

										if (data && data.items) {
											for (var key in data.items) {
												var found = false;

												instance.entries.each(
													function(item) {
														if (key === item[0]) {
															found = true;
														}

														if (key === item[0] && data.items[key].unchecked) {
															instance.entries.remove(item);
														}
													}
												);

												data.items[key][0] = key;

												if (!found && !data.items[key].unchecked) {
													instance.entries.add(data.items[key]);
												}
											}
										}

										instance.set('categoryIds', instance.entries.keys);

										instance._updateInputHidden();
									}
								},
								'strings.add': Liferay.Language.get('done'),
								title: Liferay.Language.get('categories'),
								url: uri
							}
						);

						itemSelectorDialog.open();
					},

					_updateInputHidden: function() {
						var instance = this;

						var hiddenInput = instance.get('hiddenInput');

						hiddenInput.val(instance.entries.keys.join(','));
					}
				}
			}
		);

		Liferay.AssetTaglibCategoriesSelector = AssetTaglibCategoriesSelector;
	},
	'',
	{
		requires: ['aui-tree', 'liferay-asset-taglib-tags-selector']
	}
);