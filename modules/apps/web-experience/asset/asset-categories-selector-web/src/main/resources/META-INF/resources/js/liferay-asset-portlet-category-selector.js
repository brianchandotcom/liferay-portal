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
						this.entryIdsArr = this.get('entryIds').split(',');

						this.treeView = new A.TreeView(
							{
								boundingBox: this.get('boundingBox'),
								children: [this.get('vocabularyRootNode')],
								io: {
									cfg: {
										data: this.formatRequestData.bind(this)
									},
									formatter: this.formatJSONResult.bind(this),
									url: this.get('url')
								}
							}
						).render();
					},

					clearEntries: function() {
						this.set('entries', {});
					},

					formatJSONResult: function(json) {
						var output = [];

						var type = 'check';

						if (json.length) {
							json.forEach(
								function(item, index) {
									var treeId = 'category' + item.categoryId;

									var newTreeNode = {
										after: {
											checkedChange: function(event) {
												if (event.newVal) {
													if (this.get('singleSelect')) {
														type = 'radio';
													}

													this.onCheckboxCheck(event);

												}
												else {
													this.onCheckboxUncheck(event);
												}

												Liferay.Util.getOpener().Liferay.fire(
													this.get('eventName'),
													{
														data: {
															items: this.get('entries')
														}
													}
												);
											}.bind(this)
										},
										checked: false,
										id: treeId,
										label: LString.escapeHTML(item.titleCurrentValue),
										leaf: !item.hasChildren,
										paginator: this.getPaginatorConfig(item),
										parentCategoryIds: item.parentCategoryIds,
										type: type,
										categoryId: item.categoryId
									};

									if (this.entryIdsArr.indexOf(item.categoryId) !== -1) {
										var entries = this.get('entries');
										entries[LString.escapeHTML(item.titleCurrentValue)] = item;
										newTreeNode.checked = true;
										this.set('entries', entries);
									}

									output.push(newTreeNode);

								}.bind(this)
							);
						}

						return output;
					},

					formatRequestData: function(treeNode) {
						var assetId = this.getTreeNodeAssetId(treeNode);

						var assetType = this.getTreeNodeAssetType(treeNode);

						var url = this.get('url');

						if (Lang.isValue(assetId) && assetType === 'category') {
							var urlGetSubCategories = url;

							urlGetSubCategories = Liferay.Util.addParams(this.get('namespace') + 'categoryId=' + assetId, urlGetSubCategories);
							var io = treeNode.get('io');

							io.url = urlGetSubCategories;

							treeNode.set('io', io);
						}
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

					onCheckboxCheck: function(event) {
						var currentTarget = event.currentTarget;

						var assetId;

						var entryMatchKey;

						var entries = this.get('entries');

						var entry;

						if (A.instanceOf(currentTarget, A.Node)) {
							assetId = currentTarget.attr('data-categoryId');

							entryMatchKey = currentTarget.val();
						}
						else {
							assetId = this.getTreeNodeAssetId(currentTarget);

							entryMatchKey = currentTarget.get('label');
						}

						entry = {
							categoryId: assetId
						};

						entry.value = entryMatchKey;

						entry.value = LString.unescapeHTML(entry.value);

						entry[0] = LString.unescapeHTML(entry.value);

						entries[entryMatchKey] = entry;

						this.set('entries', entries);
					},

					onCheckboxUncheck: function(event) {
						var currentTarget = event.currentTarget;

						var entryMatchKey;

						var entries = this.get('entries');

						if (A.instanceOf(currentTarget, A.Node)) {
							assetId = currentTarget.attr('data-categoryId');
							entryMatchKey = currentTarget.val();
						}
						else {
							assetId = this.getTreeNodeAssetId(currentTarget);
							entryMatchKey = currentTarget.get('label');
						}

						entries[entryMatchKey].unchecked = true;

						this.set('entries', entries);

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