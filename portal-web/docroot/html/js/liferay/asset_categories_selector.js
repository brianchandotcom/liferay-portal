AUI().add(
	'liferay-asset-categories-selector',
	function(A) {
		var Lang = A.Lang;

		var BOUNDING_BOX = 'boundingBox';

		var CSS_TAGS_LIST = 'lfr-categories-selector-list';

		var EMPTY_FN = Lang.emptyFn;

		var ID = 'id';

		var NAME = 'categoriesselector';

		var STR_EXPANDED = 'expanded';

		var STR_PREV_EXPANDED = '_LFR_prevExpanded';

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
		 * vocabularyIds (string): The ids of the vocabularies.
		 *
		 * Optional
		 * portalModelResource {boolean}: Whether the asset model is on the portal level.
		 */

		var AssetCategoriesSelector = A.Component.create(
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
						value: ''
					},
					curEntryIds: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value)) {
								value = value.split(',');
							}

							return value;
						},
						value: ''
					},
					labelNode: {
						setter: function(value) {
							return A.one(value) || A.Attribute.INVALID_VALUE;
						},
						value: null
					},
					singleSelect: {
						validator: Lang.isBoolean,
						value: false
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

				EXTENDS: Liferay.AssetTagsSelector,

				NAME: NAME,

				prototype: {
					UI_EVENTS: {},
					TREEVIEWS: {},

					renderUI: function() {
						var instance = this;

						AssetCategoriesSelector.superclass.constructor.superclass.renderUI.apply(instance, arguments);

						instance._renderIcons();

						instance.inputContainer.addClass('aui-helper-hidden-accessible');

						instance._applyARIARoles();
					},

					bindUI: function() {
						var instance = this;

						AssetCategoriesSelector.superclass.bindUI.apply(instance, arguments);
					},

					syncUI: function() {
						var instance = this;

						AssetCategoriesSelector.superclass.constructor.superclass.syncUI.apply(instance, arguments);

						var matchKey = instance.get('matchKey');

						instance.entries.getKey = function(obj) {
							return obj.categoryId;
						};

						var curEntries = instance.get('curEntries');
						var curEntryIds = instance.get('curEntryIds');

						A.each(
							curEntryIds,
							function(item, index, collection) {
								var entry = {
									categoryId: item
								};

								entry[matchKey] = curEntries[index];

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

					_formatJSONResult: function(json) {
						var instance = this;

						var output = [];

						var type = 'check';

						if (instance.get('singleSelect')) {
							type = 'radio';
						}

						A.each(
							json,
							function(item, index, collection) {
								var checked = false;
								var treeId = 'category' + item.categoryId;

								if (instance.entries.findIndexBy('categoryId', item.categoryId) > -1) {
									checked = true;
								}

								var newTreeNode = {
									after: {
										checkedChange: A.bind(instance._onCheckedChange, instance)
									},
									checked: checked,
									id: treeId,
									label: Liferay.Util.escapeHTML(item.titleCurrentValue),
									leaf: !item.hasChildren,
									type: type
								};

								output.push(newTreeNode);
							}
						);

						return output;
					},

					_formatRequestData: function(treeNode) {
						var instance = this;

						var data = {};
						var assetId = instance._getTreeNodeAssetId(treeNode);
						var assetType = instance._getTreeNodeAssetType(treeNode);

						if (Lang.isValue(assetId)) {
							if (assetType == 'category') {
								data.categoryId = assetId;
							}
							else {
								data.vocabularyId = assetId;
							}
						}

						return data;
					},

					_getEntries: function(className, callback) {
						var instance = this;

						var portalModelResource = instance.get('portalModelResource');

						var groupIds = [];

						var vocabularyIds = instance.get('vocabularyIds');

						var serviceParameterTypesGetVocabularies = [
							'[J'
						];

						var serviceParameterTypesGetGroupVocabularies = [
							'[J',
							'java.lang.String'
						];

						if (vocabularyIds.length > 0) {
							Liferay.Service.Asset.AssetVocabulary.getVocabularies(
								{
									vocabularyIds: vocabularyIds,
									serviceParameterTypes: A.JSON.stringify(serviceParameterTypesGetVocabularies)
								},
								callback
							);
						}
						else {
							if (!portalModelResource && (themeDisplay.getParentGroupId() != themeDisplay.getCompanyGroupId())) {
								groupIds.push(themeDisplay.getParentGroupId());
							}

							groupIds.push(themeDisplay.getCompanyGroupId());

							Liferay.Service.Asset.AssetVocabulary.getGroupsVocabularies(
								{
									groupIds: groupIds,
									className: className,
									serviceParameterTypes: A.JSON.stringify(serviceParameterTypesGetGroupVocabularies)
								},
								callback
							);
						}
					},

					_getTreeNodeAssetId: function(treeNode) {
						var treeId = treeNode.get(ID);
						var match = treeId.match(/(\d+)$/);

						return (match ? match[1] : null);
					},

					_getTreeNodeAssetType: function(treeNode) {
						var treeId = treeNode.get(ID);
						var match = treeId.match(/^(vocabulary|category)/);

						return (match ? match[1] : null);
					},

					_initSearch: function() {
						var instance = this;

						var popup = instance._popup;

						var options = {
							after: {
								search: function(event) {
									var results = event.liveSearch.results;

									var searchValue = event.currentTarget.get('searchValue');

									A.each(
										results,
										function(item, index, collection) {
											var widget = A.Widget.getByNode(item.node);

											widget.eachParent(
												function(parent) {
													if (searchValue) {
														parent.get(BOUNDING_BOX).show();

														if (!(STR_PREV_EXPANDED in parent)) {
															parent[STR_PREV_EXPANDED] = parent.get(STR_EXPANDED);
														}

														parent.set(STR_EXPANDED, true);
													}
													else if (STR_PREV_EXPANDED in parent) {
														parent.set(STR_EXPANDED, parent[STR_PREV_EXPANDED]);

														delete parent[STR_PREV_EXPANDED];
													}
												}
											);
										}
									);
								}
							},
							data: function(node) {
								return node.one('.aui-tree-label').html();
							},
							input: popup.searchField.get('node'),
							nodes: popup.entriesNode.all('.aui-tree-node')
						};

						if (popup.liveSearch) {
							popup.liveSearch.destroy();
						}

						popup.liveSearch = new A.LiveSearch(options);
					},

					_onBoundingBoxClick: EMPTY_FN,

					_onCheckboxCheck: function(event) {
						var instance = this;

						var treeNode = event.currentTarget;
						var assetId = instance._getTreeNodeAssetId(treeNode);
						var matchKey = instance.get('matchKey');

						var entry = {
							categoryId: assetId
						};

						entry[matchKey] = treeNode.get('label');

						instance.entries.add(entry);
					},

					_onCheckedChange: function(event) {
						var intance = this;

						if (event.newVal) {
							intance._onCheckboxCheck(event);
						}
						else {
							intance._onCheckboxUncheck(event);
						}
					},

					_onCheckboxUncheck: function(event) {
						var instance = this;

						var treeNode = event.currentTarget;
						var assetId = instance._getTreeNodeAssetId(treeNode);

						instance.entries.removeKey(assetId);
					},

					_renderIcons: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						instance.icons = new A.Toolbar(
							{
								children: [
									{
										handler: {
											context: instance,
											fn: instance._showSelectPopup
										},
										icon: 'search',
										label: Liferay.Language.get('select')
									}
								]
							}
						).render(contentBox);

						var iconsBoundingBox = instance.icons.get(BOUNDING_BOX);

						instance.entryHolder.placeAfter(iconsBoundingBox);
					},

					_showSelectPopup: function(event) {
						var instance = this;

						instance._showPopup(event);

						var popup = instance._popup;

						popup.set('title', Liferay.Language.get('categories'));

						popup.entriesNode.addClass(CSS_TAGS_LIST);

						var className = instance.get('className');

						instance._getEntries(
							className,
							function(entries) {
								popup.entriesNode.empty();

								A.each(entries, instance._vocabulariesIterator, instance);

								A.each(
									instance.TREEVIEWS,
									function(item, index, collection) {
										item.expandAll();
									}
								);
							}
						);

						if (instance._bindSearchHandle) {
							instance._bindSearchHandle.detach();
						}

						var searchField = popup.searchField.get(BOUNDING_BOX);

						instance._bindSearchHandle = searchField.on('focus', A.bind(instance._initSearch, instance));
					},

					_vocabulariesIterator: function(item, index, collection) {
						var instance = this;

						var popup = instance._popup;
						var vocabularyTitle = Liferay.Util.escapeHTML(item.titleCurrentValue);
						var vocabularyId = item.vocabularyId;

						if (item.groupId == themeDisplay.getCompanyGroupId()) {
							vocabularyTitle += ' (' + Liferay.Language.get('global') + ')';
						}

						var treeId = 'vocabulary' + vocabularyId;

						var vocabularyRootNode = {
							alwaysShowHitArea: true,
							id: treeId,
							label: vocabularyTitle,
							leaf: false,
							type: 'io'
						};

						instance.TREEVIEWS[vocabularyId] = new A.TreeView(
							{
								children: [vocabularyRootNode],
								io: {
									cfg: {
										data: A.bind(instance._formatRequestData, instance)
									},
									formatter: A.bind(instance._formatJSONResult, instance),
									url: themeDisplay.getPathMain() + '/asset/get_categories'
								},
								paginator: {
									limit: 50,
									offsetParam: 'start'
								}
							}
						).render(popup.entriesNode);
					}
				}
			}
		);

		Liferay.AssetCategoriesSelector = AssetCategoriesSelector;
	},
	'',
	{
		requires: ['aui-tree', 'liferay-asset-tags-selector']
	}
);