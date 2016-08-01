AUI.add(
	'liferay-asset-portlet-category-selector',
	function(A) {
		var Lang = A.Lang;

		var LString = Lang.String;

		var NAME = 'assetPortletCategorySelector';

		var STR_START = 'start';

		var AssetPortletCategorySelector = A.Component.create(
			{
				ATTRS: {
					boundingBox: {
						validator: Lang.isString
					},

					entries: {
						validator: Lang.isObject
					},

					entryIds: {
						validator: Lang.isString
					},

					eventName: {
						validator: Lang.isString
					},

					namespace: {
						validator: Lang.isString
					},

					singleSelect: {
						validator: Lang.isBoolean,
						value: false
					},

					url: {
						validator: Lang.isString
					},

					vocabularyRootNode: {
						validator: Lang.isObject
					}
				},

				NAME: NAME,

				prototype: {
					renderUI: function() {
						var instance = this;

						instance.entryIdsArr = instance.get('entryIds').split(',');

						instance.treeView = new A.TreeView(
							{
								boundingBox: instance.get('boundingBox'),
								children: instance.get('vocabularyRootNode'),
								io: {
									cfg: {
										data: instance.formatRequestData.bind(instance)
									},
									formatter: instance.formatJSONResult.bind(instance),
									url: instance.getVocabularyURL(instance)
								}
							}
						).render();
					},

					clearEntries: function() {
						var instance = this;

						instance.set('entries', {});
					},

					formatJSONResult: function(json) {
						var instance = this;

						var output = [];

						var type = 'check';

						if (instance.get('singleSelect')) {
							type = 'radio';
						}

						if (json.length) {
							json.forEach(
								function(item, index) {
									var treeId = 'category' + item.categoryId;

									var newTreeNode = {
										after: {
											checkedChange: function(event) {
												if (event.newVal) {
													instance.onCheckboxCheck(event);
												}
												else {
													instance.onCheckboxUncheck(event);
												}

												Liferay.Util.getOpener().Liferay.fire(
													instance.get('eventName'),
													{
														data: {
															items: instance.get('entries')
														}
													}
												);
											}
										},
										categoryId: item.categoryId,
										checked: false,
										id: treeId,
										label: LString.escapeHTML(item.titleCurrentValue),
										leaf: !item.hasChildren,
										paginator: instance.getPaginatorConfig(item),
										parentCategoryIds: item.parentCategoryIds,
										type: type
									};

									if (instance.entryIdsArr.indexOf(item.categoryId) !== -1) {
										var entries = instance.get('entries');

										entries[LString.escapeHTML(item.titleCurrentValue)] = item;

										newTreeNode.checked = true;

										instance.set('entries', entries);
									}

									output.push(newTreeNode);
								}
							);
						}

						return output;
					},

					formatRequestData: function(treeNode) {
						var instance = this;

						var io = treeNode.get('io');

						io.url = instance.getVocabularyURL(treeNode);
					},

					getPaginatorConfig: function(item) {
						var paginatorConfig = {
							offsetParam: STR_START
						};

						var maxEntries = 'maxEntries';

						if (maxEntries > 0) {
							paginatorConfig.limit = maxEntries;
							paginatorConfig.moreResultsLabel = 'moreResultsLabel';
							paginatorConfig.total = item.childrenCount;
						}
						else {
							paginatorConfig.end = -1;
							paginatorConfig.start = -1;
						}

						paginatorConfig.total = item.childrenCount;

						return paginatorConfig;
					},

					getTreeNodeAssetId: function(treeNode) {
						var treeId = treeNode.get('id');

						var match = treeId.match(/(\d+)$/);

						return match ? match[1] : null;
					},

					getTreeNodeAssetType: function(treeNode) {
						var treeId = treeNode.get('id');

						var match = treeId.match(/^(vocabulary|category)/);

						return match ? match[1] : null;
					},

					getVocabularyURL: function(treeNode) {
						var instance = this;

						var assetId = instance.getTreeNodeAssetId(treeNode);

						var assetType = instance.getTreeNodeAssetType(treeNode);

						var getSubCategoriesURL = instance.get('url');

						if (Lang.isValue(assetId)) {
							getSubCategoriesURL = Liferay.Util.addParams(instance.get('namespace') + assetType + 'Id=' + assetId, getSubCategoriesURL);
						}

						return getSubCategoriesURL;
					},

					onCheckboxCheck: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var assetId;

						var entryMatchKey;

						if (A.instanceOf(currentTarget, A.Node)) {
							assetId = currentTarget.attr('data-categoryId');

							entryMatchKey = currentTarget.val();
						}
						else {
							assetId = instance.getTreeNodeAssetId(currentTarget);

							entryMatchKey = currentTarget.get('label');
						}

						var entry = {
							categoryId: assetId,
							value: LString.unescapeHTML(entryMatchKey)
						};

						entry[0] = LString.unescapeHTML(entry.value);

						var entries = instance.get('entries');

						entries[entryMatchKey] = entry;

						instance.set('entries', entries);
					},

					onCheckboxUncheck: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var entryMatchKey;

						if (A.instanceOf(currentTarget, A.Node)) {
							entryMatchKey = currentTarget.val();
						}
						else {
							entryMatchKey = currentTarget.get('label');
						}

						var entries = instance.get('entries');

						entries[entryMatchKey].unchecked = true;

						instance.set('entries', entries);
					}
				}
			}
		);

		Liferay.AssetPortletCategorySelector = AssetPortletCategorySelector;
	},
	'',
	{
		requires: ['aui-tree-view']
	}
);