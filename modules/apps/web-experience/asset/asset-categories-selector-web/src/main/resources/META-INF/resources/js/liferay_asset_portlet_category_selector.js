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

						var rootNode = instance.get('vocabularyRootNode');

						rootNode[0].children.map(
							function(item) {
								item.after = {
									checkedChange: A.bind('onCheckedChange', instance)
								};

								return item;
							}
						);

						instance.treeView = new A.TreeView(
							{
								boundingBox: instance.get('boundingBox'),
								children: rootNode,
								io: {
									cfg: {
										data: instance.formatRequestData.bind(instance)
									},
									formatter: instance.formatJSONResult.bind(instance),
									url: instance.getVocabularyURL(instance)
								}
							}
						).render();

						instance.treeView.getChildren()[0].get('contentBox').addClass('hide');
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
											checkedChange: A.bind('onCheckedChange', instance)
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

						var assetId = instance.getTreeNodeAssetId(currentTarget);

						var assetType = instance.getTreeNodeAssetType(currentTarget);

						var entryMatchKey = currentTarget.get('label');

						var entry = {
							value: LString.unescapeHTML(entryMatchKey)
						};

						entry[assetType + 'Id'] = assetId;

						entry[0] = LString.unescapeHTML(entry.value);

						var entries = instance.get('entries');

						entries[entryMatchKey] = entry;

						instance.set('entries', entries);
					},

					onCheckboxUncheck: function(event) {
						var instance = this;

						if (!instance.get('singleSelect')) {
							var currentTarget = event.currentTarget;

							var entryMatchKey = currentTarget.get('label');

							var entries = instance.get('entries');

							entries[entryMatchKey].unchecked = true;

							instance.set('entries', entries);
						}
						else {
							instance.clearEntries();
						}
					},

					onCheckedChange: function(event) {
						var instance = this;

						if (event.newVal) {
							instance.onCheckboxCheck(event);
						}
						else {
							instance.onCheckboxUncheck(event);
						}

						Liferay.Util.getOpener().Liferay.fire(
							instance.get('eventName'),
							{
								data: A.Object.isEmpty(instance.get('entries')) ? '' : instance.get('entries')
							}
						);
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