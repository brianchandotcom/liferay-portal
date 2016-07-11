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
		 * className {String}: The class name of the current asset.
		 * curEntryIds (string): The ids of the current categories.
		 * curEntries (string): The names of the current categories.
		 * hiddenInput {string}: The hidden input used to pass in the current categories.
		 * instanceVar {string}: The instance variable for this class.
		 * labelNode {String|A.Node}: The node of the label element for this selector.
		 * title {String}: The title of the button element for this selector.
		 * vocabularyIds (string): The ids of the vocabularies.
		 * vocabularyGroupIds (string): The groupIds of the vocabularies.
		 *
		 * Optional
		 * maxEntries {Number}: The maximum number of entries that will be loaded. The default value is -1, which will load all categories.
		 * moreResultsLabel {String}: The localized label for link "Load more results".
		 * portalModelResource {boolean}: Whether the asset model is on the portal level.
		 */

		var AssetTaglibCategoriesSelector = A.Component.create(
			{
				ATTRS: {
					curEntries: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value)) {
								value = value.split('_CATEGORY_');
							}

							return value;
						},
						value: []
					},

					curEntryIds: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value)) {
								value = value.split(',');
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

					vocabularyGroupIds: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value) && value) {
								value = value.split(',');
							}

							return value;
						},
						value: []
					},

					vocabularyIds: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value) && value) {
								value = value.split(',');
							}

							return value;
						},
						value: []
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

						var matchKey = instance.get('matchKey');

						instance.entries.getKey = function(obj) {
							return obj.categoryId;
						};

						var curEntries = instance.get('curEntries');
						var curEntryIds = instance.get('curEntryIds');

						curEntryIds.forEach(
							function(item, index) {
								var entry = {
									categoryId: item
								};

								entry[matchKey] = curEntries[index];

								entry.value = LString.unescapeHTML(entry.value);

								instance.entries.add(entry);
							}
						);
					},

					_afterTBLFocusedChange: EMPTY_FN,

					_applyARIARoles: function() {
						var instance = this;

						var boundingBox = instance.get(BOUNDING_BOX);
						var labelNode = instance.get('labelNode');

						if (labelNode) {
							boundingBox.attr('aria-labelledby', labelNode.attr(ID));

							labelNode.attr('for', boundingBox.attr(ID));
						}
					},

					_bindTagsSelector: EMPTY_FN,

					_getEntries: function(className, callback) {
						var instance = this;

						var portalModelResource = instance.get('portalModelResource');

						var groupIds = [];

						var vocabularyIds = instance.get('vocabularyIds');

						if (vocabularyIds.length > 0) {
							Liferay.Service(
								{
									'$vocabularies = /assetvocabulary/get-vocabularies': {
										vocabularyIds: vocabularyIds,
										'$childrenCount = /assetcategory/get-vocabulary-root-categories-count': {
											'@groupId': '$vocabularies.groupId',
											'@vocabularyId': '$vocabularies.vocabularyId'
										},

										'$group[descriptiveName] = /group/get-group': {
											'@groupId': '$vocabularies.groupId'
										}
									}
								},
								callback
							);
						}
						else {
							if (!portalModelResource && themeDisplay.getSiteGroupId() != themeDisplay.getCompanyGroupId()) {
								groupIds.push(themeDisplay.getSiteGroupId());
							}

							groupIds.push(themeDisplay.getCompanyGroupId());

							Liferay.Service(
								{
									'$vocabularies = /assetvocabulary/get-groups-vocabularies': {
										className: className,
										groupIds: groupIds,
										'$childrenCount = /assetcategory/get-vocabulary-root-categories-count': {
											'groupId': '$vocabularies.groupId',
											'@vocabularyId': '$vocabularies.vocabularyId'
										},

										'$group[descriptiveName] = /group/get-group': {
											'@groupId': '$vocabularies.groupId'
										}
									}
								},
								callback
							);
						}
					},

					_isValidString: function(value) {
						var instance = this;

						return Lang.isString(value) && value.length;
					},

					_onBoundingBoxClick: EMPTY_FN,

					_onSelectChange: function(event) {
						var instance = this;

						instance._clearEntries();

						instance._onCheckboxCheck(event);
					},

					_renderIcons: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var vocabularyId = contentBox.getAttribute('data-vocabulary-id');

						instance.icons = new A.Toolbar(
							{
								children: [
									{
										label: instance.get('label'),
										on: {
											click: function(event) {
												event.data = event.data ? event.data : {};
												event.data.vocabularyId = vocabularyId;
												instance._showSelectPopup.call(instance, event);
											}
										},
										title: instance.get('title')
									}
								]
							}
						).render(contentBox);

						var iconsBoundingBox = instance.icons.get(BOUNDING_BOX);

						instance.entryHolder.placeAfter(iconsBoundingBox);
					},

					_showSelectPopup: function(event) {
						var instance = this;

						event.domEvent.preventDefault();

						var uri = Lang.sub(
							decodeURIComponent(instance.get('portletURL')),
							{
								selectedCategories: instance.get('curEntryIds'),
								singleSelect: instance.get('singleSelect'),
								vocabularyId: event.data.vocabularyId
							}
						);

						var itemSelectorDialog = new A.LiferayItemSelectorDialog(
							{
								eventName: instance.get('namespace') + 'selectCategory',
								on: {
									selectedItemChange: function(event) {
										var selectedCategories = event.newVal;

										if (selectedCategories) {
											instance.entries.each(
												function(item) {
													instance.entries.remove(item);
												}
											);

											for (var key in selectedCategories.items) {
												instance.add(selectedCategories.items[key].value || selectedCategories.items[key].name);
											}

											instance.set('curEntryIds', Object.keys(selectedCategories.items).join(','));

											instance._updateInputHidden(selectedCategories.items);
										}
									}
								},
								'strings.add': Liferay.Language.get('done'),
								title: Liferay.Language.get('categories'),
								url: uri
							}
						);

						itemSelectorDialog.open();
					},

					_updateInputHidden: function(selectedCategories) {
						var instance = this;

						var hiddenInput = instance.get('hiddenInput');

						hiddenInput.val(Object.keys(selectedCategories).join(','));
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