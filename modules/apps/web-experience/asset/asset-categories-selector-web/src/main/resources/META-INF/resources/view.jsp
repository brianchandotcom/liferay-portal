<%--
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
--%>

<%@ include file="/init.jsp" %>

<div class="container-fluid-1280">
	<div class="lfr-categories-selector-list lfr-tags-selector-list" id="<portlet:namespace />listCategories">
	</div>
</div>

<portlet:resourceURL id="getCategories" var="resourceURL">
	<portlet:param name="vocabularyId" value="<%= String.valueOf(assetCategoriesSelectorDisplayContext.getVocabularyId()) %>" />
</portlet:resourceURL>

<aui:script use="aui-tree-view">

	var Lang = A.Lang;

	var LString = Lang.String;

	var categorySelector = function () {
		this.entries = {};

		this.singleSelect = '<%= request.getParameter("singleSelect") %>';

		this.entryIds = '<%= request.getParameter("selectedCategories") %>';

		this.entryIds = this.entryIds.split(',');

		this.STR_START = 'start';

		this.url = '<%= resourceURL %>';

		this.vocabularyRootNode = {
			alwaysShowHitArea: true,
			id: 'vocabulary<%= assetCategoriesSelectorDisplayContext.getVocabularyId() %>',
			label: '<%= assetCategoriesSelectorDisplayContext.getVocabularyTitle() %>',
			leaf: false,
			type: 'io',
			expanded: true
		};

		this.render();
	};

	categorySelector.prototype = {

		clearEntries: function() {
			this.entries = {};
		},

		formatJSONResult: function(json) {
			var output = [];

			var type = 'check';

			json.forEach(
				function(item, index) {
					var treeId = 'category' + item.categoryId;

					var newTreeNode = {
						after: {
							checkedChange: function(event) {
								var instance = this;
								if (event.newVal) {
									if (this.singleSelect) {
										type = 'radio';
									}

									this.onCheckboxCheck(event);

									Liferay.Util.getOpener().Liferay.fire(
										'<%= HtmlUtil.escapeJS(assetCategoriesSelectorDisplayContext.getEventName()) %>',
										{
											data: {
												items: this.entries
											}
										}
									);
								}
								else {
									this.onCheckboxUncheck(event);
								}
							}.bind(this)
						},
						checked: false,
						id: treeId,
						label: LString.escapeHTML(item.titleCurrentValue),
						leaf: !item.hasChildren,
						paginator: this.getPaginatorConfig(item),
						type: type
					};

					if (this.entryIds.indexOf(item.categoryId) !== -1) {
						this.entries[item.categoryId] = item;
						newTreeNode.checked = true;
						newTreeNode.expanded = true;
					}

					output.push(newTreeNode);
				}.bind(this)
			);
			return output;
		},

		formatRequestData: function(treeNode) {
			var assetId = this.getTreeNodeAssetId(treeNode);

			var assetType = this.getTreeNodeAssetType(treeNode);

			if (Lang.isValue(assetId) && assetType === 'category') {
				var urlGetSubCategories = this.url;
				urlGetSubCategories = Liferay.Util.addParams('<portlet:namespace />categoryId=' + assetId, this.url);
				var io = treeNode.get('io');
				io.url = urlGetSubCategories;
				treeNode.set('io', io);
			}
		},

		getPaginatorConfig: function(item) {
			var paginatorConfig = {
				offsetParam: this.STR_START
			};

			var maxEntries = "maxEntries";

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

			if (A.instanceOf(currentTarget, A.Node)) {
				assetId = currentTarget.attr('data-categoryId');

				entryMatchKey = currentTarget.val();
			}
			else {
				assetId = this.getTreeNodeAssetId(currentTarget);

				entryMatchKey = currentTarget.get('label');
			}

			var entry = {
				categoryId: assetId
			};

			entry.value = entryMatchKey;

			entry.value = LString.unescapeHTML(entry.value);

			this.entries[assetId] = entry;
		},

		onCheckboxUncheck: function(event) {
			var currentTarget = event.currentTarget;

			var assetId;

			if (A.instanceOf(currentTarget, A.Node)) {
				assetId = currentTarget.attr('data-categoryId');
			}
			else {
				assetId = this.getTreeNodeAssetId(currentTarget);
			}

			delete this.entries[assetId];
		},

		render: function() {
			new A.TreeView(
				{
					boundingBox: '#<portlet:namespace />listCategories',
					children: [this.vocabularyRootNode],
					io: {
						cfg: {
							data: this.formatRequestData.bind(this),
						},
						formatter: this.formatJSONResult.bind(this),
						url: this.url
					}
				}
			).render();
		}
	};

	var instanceCategorySelector = new categorySelector();
</aui:script>