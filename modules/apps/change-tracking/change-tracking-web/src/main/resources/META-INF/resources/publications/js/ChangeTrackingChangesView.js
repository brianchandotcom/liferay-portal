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

import ClayAlert from '@clayui/alert';
import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import {fetch} from 'frontend-js-web';
import React from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';

class ChangeTrackingChangesView extends React.Component {
	constructor(props) {
		super(props);

		const {
			activeCTCollection,
			basePath,
			changes,
			contextView,
			ctCollectionId,
			discardURL,
			history,
			models,
			namespace,
			pathParam,
			renderCTEntryURL,
			renderDiffURL,
			rootDisplayClasses,
			siteNames,
			spritemap,
			typeNames,
			userInfo,
		} = props;

		this.activeCTCollection = activeCTCollection;
		this.basePath = basePath;
		this.changes = changes;
		this.contextView = contextView;
		this.ctCollectionId = ctCollectionId;
		this.discardURL = discardURL;
		this.history = history;
		this.models = models;
		this.namespace = namespace;
		this.renderCTEntryURL = renderCTEntryURL;
		this.renderDiffURL = renderDiffURL;
		this.rootDisplayClasses = rootDisplayClasses;
		this.siteNames = siteNames;
		this.spritemap = spritemap;
		this.typeNames = typeNames;
		this.userInfo = userInfo;

		this.globalSiteName = Liferay.Language.get('global');

		this._populateModelInfo();

		const pathState = this._getPathState(pathParam);

		const filterClass = pathState.filterClass;
		const nodeId = pathState.nodeId;
		const showHideable = pathState.showHideable;
		const viewType = pathState.viewType;

		const node = this._getNode(filterClass, nodeId, viewType);

		const breadcrumbItems = this._getBreadcrumbItems(
			node,
			filterClass,
			nodeId,
			viewType
		);

		if (pathParam) {
			const updatedPathParam = this._getPathParam(
				breadcrumbItems,
				filterClass,
				showHideable,
				viewType
			);

			if (pathParam !== updatedPathParam) {
				AUI().use('liferay-portlet-url', () => {
					this.history.replace(
						this.basePath +
							'&' +
							this.namespace +
							'path=' +
							updatedPathParam
					);
				});
			}
		}

		this.state = {
			ascending: true,
			breadcrumbItems,
			children: this._filterHideableNodes(node.children, showHideable),
			column: 'title',
			delta: 20,
			dropdown: '',
			filterClass,
			node,
			page: 1,
			renderInnerHTML: null,
			showHideable,
			sortDirectionClass: 'order-arrow-down-active',
			viewType,
		};

		this._updateRenderContent(node);
	}

	componentDidMount() {
		this.history.listen((location, action) => {
			if (action !== 'POP') {
				return;
			}

			const params = new URLSearchParams(location.search);

			const pathState = this._getPathState(
				params.get(this.namespace + 'path')
			);

			const filterClass = pathState.filterClass;
			const nodeId = pathState.nodeId;
			const showHideable = pathState.showHideable;
			const viewType = pathState.viewType;

			if (viewType === 'context' && this.contextView.errorMessage) {
				this.setState({
					renderInnerHTML: null,
					viewType,
				});

				return;
			}

			const node = this._getNode(filterClass, nodeId, viewType);

			const breadcrumbItems = this._getBreadcrumbItems(
				node,
				filterClass,
				nodeId,
				viewType
			);

			this.setState({
				breadcrumbItems,
				children: this._filterHideableNodes(
					node.children,
					showHideable
				),
				dropdown: '',
				filterClass,
				node,
				page: 1,
				renderInnerHTML: null,
				showHideable,
				viewType,
			});

			this._updateRenderContent(node);
		});
	}

	_clone(json) {
		const clone = {};

		const keys = Object.keys(json);

		for (let i = 0; i < keys.length; i++) {
			clone[keys[i]] = json[keys[i]];
		}

		return clone;
	}

	_filterDisplayNodes(nodes) {
		const ascending = this.state.ascending;

		if (this._getColumn() === 'changeType') {
			nodes.sort((a, b) => {
				if (a.changeType < b.changeType) {
					if (ascending) {
						return -1;
					}

					return 1;
				}

				if (a.changeType > b.changeType) {
					if (ascending) {
						return 1;
					}

					return -1;
				}

				const typeNameA = a.typeName.toUpperCase();
				const typeNameB = b.typeName.toUpperCase();

				if (typeNameA < typeNameB) {
					return -1;
				}

				if (typeNameA > typeNameB) {
					return 1;
				}

				if (a.title < b.title) {
					return -1;
				}

				if (a.title > b.title) {
					return 1;
				}

				return 0;
			});
		}
		else if (this._getColumn() === 'site') {
			nodes.sort((a, b) => {
				if (
					a.siteName < b.siteName ||
					(a.siteName === this.globalSiteName &&
						b.siteName !== this.globalSiteName)
				) {
					if (ascending) {
						return -1;
					}

					return 1;
				}

				if (
					a.siteName > b.siteName ||
					(a.siteName !== this.globalSiteName &&
						b.siteName === this.globalSiteName)
				) {
					if (ascending) {
						return 1;
					}

					return -1;
				}

				const typeNameA = a.typeName.toUpperCase();
				const typeNameB = b.typeName.toUpperCase();

				if (typeNameA < typeNameB) {
					return -1;
				}

				if (typeNameA > typeNameB) {
					return 1;
				}

				if (a.title < b.title) {
					return -1;
				}

				if (a.title > b.title) {
					return 1;
				}

				return 0;
			});
		}
		else if (this._getColumn() === 'title') {
			nodes.sort((a, b) => {
				const typeNameA = a.typeName.toUpperCase();
				const typeNameB = b.typeName.toUpperCase();

				if (typeNameA < typeNameB) {
					return -1;
				}

				if (typeNameA > typeNameB) {
					return 1;
				}

				if (a.title < b.title) {
					if (ascending) {
						return -1;
					}

					return 1;
				}

				if (a.title > b.title) {
					if (ascending) {
						return 1;
					}

					return -1;
				}

				return 0;
			});
		}
		else if (this._getColumn() === 'user') {
			nodes.sort((a, b) => {
				if (a.userName < b.userName) {
					if (ascending) {
						return -1;
					}

					return 1;
				}

				if (a.userName > b.userName) {
					if (ascending) {
						return 1;
					}

					return -1;
				}

				const typeNameA = a.typeName.toUpperCase();
				const typeNameB = b.typeName.toUpperCase();

				if (typeNameA < typeNameB) {
					return -1;
				}

				if (typeNameA > typeNameB) {
					return 1;
				}

				if (a.title < b.title) {
					return -1;
				}

				if (a.title > b.title) {
					return 1;
				}

				return 0;
			});
		}
		else {
			nodes.sort((a, b) => {
				if (a.modifiedTime < b.modifiedTime) {
					if (ascending) {
						return -1;
					}

					return 1;
				}

				if (a.modifiedTime > b.modifiedTime) {
					if (ascending) {
						return 1;
					}

					return -1;
				}

				return 0;
			});
		}

		if (nodes.length > 5) {
			nodes = nodes.slice(
				this.state.delta * (this.state.page - 1),
				this.state.delta * this.state.page
			);
		}

		if (this._getColumn() === 'modifiedDate') {
			nodes.sort((a, b) => {
				const typeNameA = a.typeName.toUpperCase();
				const typeNameB = b.typeName.toUpperCase();

				if (typeNameA < typeNameB) {
					return -1;
				}

				if (typeNameA > typeNameB) {
					return 1;
				}

				if (a.modifiedTime < b.modifiedTime) {
					if (ascending) {
						return -1;
					}

					return 1;
				}

				if (a.modifiedTime > b.modifiedTime) {
					if (ascending) {
						return 1;
					}

					return -1;
				}

				return 0;
			});
		}

		return nodes;
	}

	_filterHideableNodes(nodes, showHideable) {
		if (!nodes || showHideable) {
			return nodes;
		}

		const filterNodes = [];

		for (let i = 0; i < nodes.length; i++) {
			const node = nodes[i];

			if (!node.hideable) {
				filterNodes.push(node);
			}
		}

		return filterNodes;
	}

	_format(key, args) {
		const SPLIT_REGEX = /({\d+})/g;

		const keyArray = key
			.split(SPLIT_REGEX)
			.filter((val) => val.length !== 0);

		for (let i = 0; i < args.length; i++) {
			const arg = args[i];

			const indexKey = `{${i}}`;

			let argIndex = keyArray.indexOf(indexKey);

			while (argIndex >= 0) {
				keyArray.splice(argIndex, 1, arg);

				argIndex = keyArray.indexOf(indexKey);
			}
		}

		return keyArray.join('');
	}

	_getBreadcrumbItems(node, filterClass, nodeId, viewType) {
		if (viewType === 'changes') {
			if (nodeId === 0) {
				return [
					{
						active: true,
						label: Liferay.Language.get('home'),
					},
				];
			}

			return [
				{
					label: Liferay.Language.get('home'),
					onClick: () =>
						this._handleNavigationUpdate({
							nodeId: 0,
						}),
				},
				{
					active: true,
					label: node.title,
					modelClassNameId: node.modelClassNameId,
					modelClassPK: node.modelClassPK,
				},
			];
		}

		const breadcrumbItems = [];
		const homeBreadcrumbItem = {label: Liferay.Language.get('home')};

		if (filterClass === 'everything' && nodeId === 0) {
			homeBreadcrumbItem.active = true;

			breadcrumbItems.push(homeBreadcrumbItem);

			return breadcrumbItems;
		}

		homeBreadcrumbItem.onClick = () =>
			this._handleNavigationUpdate({
				filterClass: 'everything',
				nodeId: 0,
			});

		breadcrumbItems.push(homeBreadcrumbItem);

		let showParent = false;

		if (filterClass === 'everything') {
			showParent = true;
		}
		else {
			let label = filterClass;

			if (label.includes('.')) {
				label = label.substring(
					label.lastIndexOf('.') + 1,
					label.length
				);
			}

			const rootDisplayClassBreadcrumb = {label};

			if (nodeId === 0) {
				rootDisplayClassBreadcrumb.active = true;

				breadcrumbItems.push(rootDisplayClassBreadcrumb);

				return breadcrumbItems;
			}

			rootDisplayClassBreadcrumb.onClick = () =>
				this._handleNavigationUpdate({
					filterClass,
					nodeId: 0,
				});

			breadcrumbItems.push(rootDisplayClassBreadcrumb);
		}

		if (!node.parents) {
			return null;
		}

		for (let i = 0; i < node.parents.length; i++) {
			const parent = node.parents[i];

			if (parent.typeName === filterClass) {
				showParent = true;
			}

			if (!showParent) {
				continue;
			}

			breadcrumbItems.push({
				label: parent.title,
				modelClassNameId: parent.modelClassNameId,
				modelClassPK: parent.modelClassPK,
				onClick: () =>
					this._handleNavigationUpdate({
						filterClass,
						nodeId: parent.nodeId,
					}),
			});
		}

		breadcrumbItems.push({
			active: true,
			label: node.title,
			modelClassNameId: node.modelClassNameId,
			modelClassPK: node.modelClassPK,
		});

		return breadcrumbItems;
	}

	_getColumn() {
		if (this.state.viewType === 'context') {
			return 'title';
		}

		return this.state.column;
	}

	_getDiscardURL(node) {
		const portletURL = Liferay.PortletURL.createURL(this.discardURL);

		portletURL.setParameter('modelClassNameId', node.modelClassNameId);
		portletURL.setParameter('modelClassPK', node.modelClassPK);

		return portletURL.toString();
	}

	_getDropdown(node) {
		let dropdownItems = node.dropdownItems;

		if (!dropdownItems) {
			dropdownItems = [];
		}
		else {
			dropdownItems = dropdownItems.slice(0);
		}

		if (this.activeCTCollection) {
			dropdownItems.push({
				href: this._getDiscardURL(node),
				label: Liferay.Language.get('discard'),
				symbolLeft: 'times-circle',
			});
		}

		if (dropdownItems.length === 0) {
			return;
		}

		return (
			<div className="autofit-col">
				<ClayDropDownWithItems
					alignmentPosition={Align.BottomLeft}
					items={dropdownItems}
					spritemap={this.spritemap}
					trigger={
						<ClayButtonWithIcon
							displayType="unstyled"
							small
							spritemap={this.spritemap}
							symbol="ellipsis-v"
						/>
					}
				/>
			</div>
		);
	}

	_getModels(nodes) {
		if (!nodes) {
			return [];
		}

		const models = [];

		for (let i = 0; i < nodes.length; i++) {
			const node = nodes[i];

			let modelKey = node;
			let nodeId = node;

			if (typeof node === 'object') {
				modelKey = node.modelKey;
				nodeId = node.nodeId;
			}

			const model = this._clone(this.models[modelKey.toString()]);

			model.nodeId = nodeId;

			models.push(model);
		}

		return models;
	}

	_getNode(filterClass, nodeId, viewType) {
		if (viewType === 'changes') {
			if (nodeId === 0) {
				return {children: this._getModels(this.changes)};
			}

			return this._clone(this.models[nodeId.toString()]);
		}
		else if (filterClass !== 'everything' && nodeId === 0) {
			return {
				children: this._getModels(
					this.contextView[filterClass].children
				),
			};
		}

		const rootNode = this.contextView.everything;

		if (nodeId === 0) {
			return {children: this._getModels(rootNode.children)};
		}

		if (!rootNode.parents) {
			rootNode.parents = [];

			for (let i = 0; i < rootNode.children.length; i++) {
				rootNode.children[i].parents = [];
			}
		}

		const stack = [rootNode];

		while (stack.length > 0) {
			const element = stack.pop();

			if (element.nodeId === nodeId) {
				const entry = this._clone(
					this.models[element.modelKey.toString()]
				);

				entry.children = this._getModels(element.children);
				entry.nodeId = nodeId;
				entry.parents = element.parents;

				return entry;
			}
			else if (!element.children) {
				continue;
			}

			for (let i = 0; i < element.children.length; i++) {
				const child = element.children[i];

				if (!child.parents) {
					const parents = element.parents.slice(0);

					const model = this.models[element.modelKey.toString()];

					parents.push({
						modelClassNameId: model.modelClassNameId,
						modelClassPK: model.modelClassPK,
						nodeId: element.nodeId,
						title: model.title,
						typeName: model.typeName,
					});

					child.parents = parents;
				}

				stack.push(child);
			}
		}

		return null;
	}

	_getPathParam(breadcrumbItems, filterClass, showHideable, viewType) {
		let path = showHideable.toString() + '/' + viewType + '/' + filterClass;

		if (breadcrumbItems && breadcrumbItems.length > 0) {
			let tree = '';

			for (let i = 0; i < breadcrumbItems.length; i++) {
				const breadcrumbItem = breadcrumbItems[i];

				if (breadcrumbItem.modelClassNameId) {
					tree +=
						'/' +
						breadcrumbItem.modelClassNameId.toString() +
						'-' +
						breadcrumbItem.modelClassPK.toString();
				}
			}

			path += tree;
		}

		return path;
	}

	_getPortraitURL(node) {
		return this.userInfo[node.userId.toString()].portraitURL;
	}

	_getRenderURL(node) {
		if (node.ctEntryId) {
			const portletURL = Liferay.PortletURL.createURL(this.renderDiffURL);

			portletURL.setParameter('ctEntryId', node.ctEntryId);

			return portletURL.toString();
		}

		const portletURL = Liferay.PortletURL.createURL(this.renderCTEntryURL);

		portletURL.setParameter('modelClassNameId', node.modelClassNameId);
		portletURL.setParameter('modelClassPK', node.modelClassPK);

		return portletURL.toString();
	}

	_getRootDisplayOptions() {
		const rootDisplayOptions = [];

		rootDisplayOptions.push(
			<ClayRadio
				label={Liferay.Language.get('everything')}
				value="everything"
			/>
		);

		for (let i = 0; i < this.rootDisplayClasses.length; i++) {
			const className = this.rootDisplayClasses[i];

			if (
				!this.state.showHideable &&
				this.contextView[className].hideable
			) {
				continue;
			}

			let label = className;

			if (label.includes('.')) {
				label = label.substring(
					label.lastIndexOf('.') + 1,
					label.length
				);
			}

			rootDisplayOptions.push(
				<ClayRadio label={label} value={className} />
			);
		}

		return rootDisplayOptions;
	}

	_getPathState(pathParam) {
		if (!pathParam) {
			return {
				filterClass: 'everything',
				nodeId: 0,
				showHideable: false,
				viewType: 'changes',
			};
		}

		const parts = pathParam.split('/');

		const path = [];

		if (parts.length > 3) {
			for (let i = 3; i < parts.length; i++) {
				const part = parts[i];

				const keys = part.split('-');

				path.push({
					modelClassNameId: keys[0],
					modelClassPK: keys[1],
				});
			}
		}

		const pathState = {
			filterClass: parts[2],
			nodeId: 0,
			showHideable: parts[0] === 'true',
			viewType: parts[1],
		};

		if (
			pathState.filterClass !== 'everything' &&
			!this.contextView[pathState.filterClass]
		) {
			pathState.filterClass = 'everything';
		}
		else if (pathState.viewType === 'changes' && path.length > 0) {
			const modelClassNameId = path[0].modelClassNameId;
			const modelClassPK = path[0].modelClassPK;

			for (let i = 0; i < this.changes.length; i++) {
				const modelKey = this.changes[i];

				const model = this.models[modelKey.toString()];

				if (
					modelClassNameId === model.modelClassNameId &&
					modelClassPK === model.modelClassPK
				) {
					pathState.nodeId = modelKey;
				}
			}
		}
		else if (pathState.viewType === 'context') {
			let contextNode = this.contextView.everything;

			if (pathState.filterClass !== 'everything') {
				contextNode = this.contextView[pathState.filterClass];
			}

			for (let i = 0; i < path.length; i++) {
				if (!contextNode.children) {
					break;
				}

				const sessionNode = path[i];

				for (let j = 0; j < contextNode.children.length; j++) {
					const child = contextNode.children[j];

					const model = this.models[child.modelKey.toString()];

					if (
						model.modelClassNameId ===
							sessionNode.modelClassNameId &&
						model.modelClassPK === sessionNode.modelClassPK
					) {
						if (pathState.filterClass !== 'everything' && i === 0) {
							const stack = [this.contextView.everything];

							while (stack.length > 0) {
								const element = stack.pop();

								if (element.nodeId === child.nodeId) {
									contextNode = element;

									break;
								}
								else if (!element.children) {
									continue;
								}

								for (
									let i = 0;
									i < element.children.length;
									i++
								) {
									stack.push(element.children[i]);
								}
							}
						}
						else {
							contextNode = child;
						}

						pathState.nodeId = contextNode.nodeId;

						break;
					}
				}
			}
		}

		return pathState;
	}

	_getTableHead() {
		if (this.state.viewType === 'context') {
			return '';
		}

		return (
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell>
						{Liferay.Language.get('user')}
					</ClayTable.Cell>
					<ClayTable.Cell headingCell>
						{Liferay.Language.get('site')}
					</ClayTable.Cell>
					<ClayTable.Cell className="table-cell-expand" headingCell>
						{Liferay.Language.get('title')}
					</ClayTable.Cell>

					<ClayTable.Cell
						className="table-cell-expand-smallest"
						headingCell
					>
						{Liferay.Language.get('change-type')}
					</ClayTable.Cell>
					<ClayTable.Cell
						className="table-cell-expand-smallest"
						headingCell
					>
						{Liferay.Language.get('last-modified')}
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>
		);
	}

	_getTableRows(nodes) {
		const rows = [];

		if (!nodes) {
			return rows;
		}

		let currentTypeName = '';

		for (let i = 0; i < nodes.length; i++) {
			const node = nodes[i];

			if (node.typeName !== currentTypeName) {
				currentTypeName = node.typeName;

				rows.push(
					<ClayTable.Row divider>
						<ClayTable.Cell
							colSpan={this.state.viewType === 'changes' ? 5 : 1}
						>
							{node.typeName}
						</ClayTable.Cell>
					</ClayTable.Row>
				);
			}

			const cells = [];

			if (this.state.viewType === 'context') {
				let descriptionMarkup = '';

				if (node.description) {
					descriptionMarkup = (
						<div className="publication-description">
							{node.description}
						</div>
					);
				}

				cells.push(
					<ClayTable.Cell>
						<div className="publication-name">{node.title}</div>

						{descriptionMarkup}
					</ClayTable.Cell>
				);
			}
			else {
				const portraitURL = this._getPortraitURL(node);

				if (portraitURL) {
					cells.push(
						<ClayTable.Cell>
							<span
								className="lfr-portal-tooltip"
								title={node.userName}
							>
								<span className="rounded-circle sticker sticker-primary">
									<span className="sticker-overlay">
										<img
											alt="thumbnail"
											className="img-fluid"
											src={portraitURL}
										/>
									</span>
								</span>
							</span>
						</ClayTable.Cell>
					);
				}
				else {
					let userPortraitCss =
						'sticker sticker-circle sticker-light user-icon-color-';

					userPortraitCss += node.userId % 10;

					cells.push(
						<ClayTable.Cell>
							<span
								className="lfr-portal-tooltip"
								title={node.userName}
							>
								<span className={userPortraitCss}>
									<span className="inline-item">
										<svg className="lexicon-icon">
											<use
												href={this.spritemap + '#user'}
											/>
										</svg>
									</span>
								</span>
							</span>
						</ClayTable.Cell>
					);
				}

				cells.push(<ClayTable.Cell>{node.siteName}</ClayTable.Cell>);

				cells.push(
					<ClayTable.Cell className="publication-name table-cell-expand">
						{node.title}
					</ClayTable.Cell>
				);

				cells.push(
					<ClayTable.Cell className="table-cell-expand-smallest">
						{node.changeTypeLabel}
					</ClayTable.Cell>
				);

				cells.push(
					<ClayTable.Cell className="table-cell-expand-smallest">
						{this._format(Liferay.Language.get('x-ago'), [
							node.timeDescription,
						])}
					</ClayTable.Cell>
				);
			}

			rows.push(
				<ClayTable.Row
					className="cursor-pointer"
					onClick={() =>
						this._handleNavigationUpdate({
							nodeId: node.nodeId,
						})
					}
				>
					{cells}
				</ClayTable.Row>
			);
		}

		return rows;
	}

	_getViewTypes() {
		if (!this.contextView) {
			return '';
		}

		const items = [
			{
				active: this.state.viewType === 'changes',
				label: Liferay.Language.get('changes'),
				onClick: () =>
					this._handleNavigationUpdate({
						filterClass: 'everything',
						nodeId: 0,
						viewType: 'changes',
					}),
				symbolLeft: 'list',
			},
			{
				active: this.state.viewType === 'context',
				label: Liferay.Language.get('context'),
				onClick: () =>
					this._handleNavigationUpdate({
						filterClass: 'everything',
						nodeId: 0,
						viewType: 'context',
					}),
				symbolLeft: 'pages-tree',
			},
		];

		return (
			<ClayManagementToolbar.Item
				className="lfr-portal-tooltip"
				title={Liferay.Language.get('display-style')}
			>
				<ClayDropDownWithItems
					alignmentPosition={Align.BottomLeft}
					items={items}
					spritemap={this.spritemap}
					trigger={
						<ClayButton
							className="nav-link nav-link-monospaced"
							displayType="unstyled"
						>
							<ClayIcon
								spritemap={this.spritemap}
								symbol={
									this.state.viewType === 'changes'
										? 'list'
										: 'pages-tree'
								}
							/>
						</ClayButton>
					}
				/>
			</ClayManagementToolbar.Item>
		);
	}

	_handleDeltaChange(delta) {
		this.setState({
			delta,
			page: 1,
		});
	}

	_handleNavigationUpdate(json) {
		let filterClass = json.filterClass;

		if (!filterClass) {
			filterClass = this.state.filterClass;
		}

		const nodeId = json.nodeId;

		let showHideable = this.state.showHideable;

		if (Object.prototype.hasOwnProperty.call(json, 'showHideable')) {
			showHideable = json.showHideable;
		}

		let viewType = json.viewType;

		if (!viewType) {
			viewType = this.state.viewType;
		}

		if (viewType === 'context' && this.contextView.errorMessage) {
			this.setState({
				renderInnerHTML: null,
				viewType,
			});

			return;
		}

		const node = this._getNode(filterClass, nodeId, viewType);

		const breadcrumbItems = this._getBreadcrumbItems(
			node,
			filterClass,
			nodeId,
			viewType
		);

		this.setState({
			breadcrumbItems,
			children: this._filterHideableNodes(node.children, showHideable),
			dropdown: '',
			filterClass,
			node,
			page: 1,
			renderInnerHTML: null,
			showHideable,
			viewType,
		});

		this._updateRenderContent(node);

		this._pushState(breadcrumbItems, filterClass, showHideable, viewType);
	}

	_handlePageChange(page) {
		this.setState({
			page,
		});
	}

	_handleShowHideableToggle(showHideable) {
		if (!showHideable) {
			if (
				this.state.viewType === 'context' &&
				this.contextView[this.state.filterClass].hideable
			) {
				this._handleNavigationUpdate({
					filterClass: 'everything',
					nodeId: 0,
					showHideable,
				});

				return;
			}
			else if (this.state.node.hideable) {
				this._handleNavigationUpdate({
					nodeId: 0,
					showHideable,
				});

				return;
			}
		}

		this.setState({
			children: this._filterHideableNodes(
				this.state.node.children,
				showHideable
			),
			showHideable,
		});

		this._pushState(
			this.state.breadcrumbItems,
			this.state.filterClass,
			showHideable,
			this.state.viewType
		);
	}

	_handleSortColumnChange(column) {
		this.setState({
			column,
		});
	}

	_handleSortDirectionChange() {
		if (this.state.ascending) {
			this.setState({
				ascending: false,
				sortDirectionClass: 'order-arrow-up-active',
			});

			return;
		}

		this.setState({
			ascending: true,
			sortDirectionClass: 'order-arrow-down-active',
		});
	}

	_populateModelInfo() {
		const keys = Object.keys(this.models);

		for (let i = 0; i < keys.length; i++) {
			const model = this.models[keys[i]];

			if (model.groupId) {
				model.siteName = this.siteNames[model.groupId.toString()];
			}
			else {
				model.siteName = this.globalSiteName;
			}

			model.typeName = this.typeNames[model.modelClassNameId.toString()];

			if (model.ctEntryId) {
				model.changeTypeLabel = Liferay.Language.get('modified');

				if (model.changeType === 'added') {
					model.changeTypeLabel = Liferay.Language.get('added');
				}
				else if (model.changeType === 'deleted') {
					model.changeTypeLabel = Liferay.Language.get('deleted');
				}

				model.userName = this.userInfo[
					model.userId.toString()
				].userName;

				if (model.siteName === this.globalSiteName) {
					let key = Liferay.Language.get('x-modified-a-x-x-ago');

					if (model.changeType === 'added') {
						key = Liferay.Language.get('x-added-a-x-x-ago');
					}
					else if (model.changeType === 'deleted') {
						key = Liferay.Language.get('x-deleted-a-x-x-ago');
					}

					model.description = this._format(key, [
						model.userName,
						model.typeName,
						model.timeDescription,
					]);
				}
				else {
					let key = Liferay.Language.get('x-modified-a-x-in-x-x-ago');

					if (model.changeType === 'added') {
						key = Liferay.Language.get('x-added-a-x-in-x-x-ago');
					}
					else if (model.changeType === 'deleted') {
						key = Liferay.Language.get('x-deleted-a-x-in-x-x-ago');
					}

					model.description = this._format(key, [
						model.userName,
						model.typeName,
						model.siteName,
						model.timeDescription,
					]);
				}
			}
		}

		for (let i = 0; i < this.rootDisplayClasses.length; i++) {
			const rootDisplayClassInfo = this.contextView[
				this.rootDisplayClasses[i]
			];

			let hideable = true;

			for (let i = 0; i < rootDisplayClassInfo.children.length; i++) {
				const model = this.models[
					rootDisplayClassInfo.children[i].modelKey.toString()
				];

				if (!model.hideable) {
					hideable = false;
				}
			}

			rootDisplayClassInfo.hideable = hideable;
		}
	}

	_pushState(breadcrumbItems, filterClass, showHideable, viewType) {
		const pathParam = this._getPathParam(
			breadcrumbItems,
			filterClass,
			showHideable,
			viewType
		);

		this.history.push(
			this.basePath + '&' + this.namespace + 'path=' + pathParam
		);
	}

	_renderEntry() {
		if (this.state.renderInnerHTML === null) {
			return '';
		}

		return (
			<div className="sheet">
				<h2 className="autofit-row sheet-title">
					<div className="autofit-col autofit-col-expand">
						<span className="heading-text">
							{this.state.node.description
								? this.state.node.description
								: this.state.node.title}
						</span>
					</div>

					{this.state.dropdown}
				</h2>

				<div
					className="sheet-section"
					dangerouslySetInnerHTML={this.state.renderInnerHTML}
				/>
			</div>
		);
	}

	_renderManagementToolbar() {
		let items = [];

		if (this.state.viewType === 'changes') {
			items = [
				{
					active: this._getColumn() === 'changeType',
					label: Liferay.Language.get('change-type'),
					onClick: () => this._handleSortColumnChange('changeType'),
				},
				{
					active: this._getColumn() === 'modifiedDate',
					label: Liferay.Language.get('modified-date'),
					onClick: () => this._handleSortColumnChange('modifiedDate'),
				},
				{
					active: this._getColumn() === 'site',
					label: Liferay.Language.get('site'),
					onClick: () => this._handleSortColumnChange('site'),
				},
				{
					active: this._getColumn() === 'user',
					label: Liferay.Language.get('user'),
					onClick: () => this._handleSortColumnChange('user'),
				},
			];
		}

		items.push({
			active: this._getColumn() === 'title',
			label: Liferay.Language.get('title'),
			onClick: () => this._handleSortColumnChange('title'),
		});

		items.sort((a, b) => {
			if (a.label < b.label) {
				return -1;
			}

			return 1;
		});

		const dropdownItems = [
			{
				items,
				label: Liferay.Language.get('order-by'),
				type: 'group',
			},
		];

		return (
			<ClayManagementToolbar>
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<ClayDropDownWithItems
							items={dropdownItems}
							spritemap={this.spritemap}
							trigger={
								<ClayButton
									className="nav-link"
									displayType="unstyled"
								>
									<span className="navbar-breakpoint-down-d-none">
										<span className="navbar-text-truncate">
											{Liferay.Language.get(
												'filter-and-order'
											)}
										</span>

										<ClayIcon
											className="inline-item inline-item-after"
											spritemap={this.spritemap}
											symbol="caret-bottom"
										/>
									</span>
									<span className="navbar-breakpoint-d-none">
										<ClayIcon
											spritemap={this.spritemap}
											symbol="filter"
										/>
									</span>
								</ClayButton>
							}
						/>
					</ClayManagementToolbar.Item>

					<ClayManagementToolbar.Item
						className="lfr-portal-tooltip"
						title={Liferay.Language.get('reverse-sort-direction')}
					>
						<ClayButton
							className={this.state.sortDirectionClass}
							displayType="unstyled"
							onClick={() => this._handleSortDirectionChange()}
						>
							<ClayIcon
								spritemap={this.spritemap}
								symbol="order-arrow"
							/>
						</ClayButton>
					</ClayManagementToolbar.Item>

					<ClayManagementToolbar.Item className="nav-item-expand" />

					<ClayManagementToolbar.Item className="simple-toggle-switch-reverse">
						<ClayToggle
							label={Liferay.Language.get('show-all-items')}
							onToggle={(showHideable) =>
								this._handleShowHideableToggle(showHideable)
							}
							toggled={this.state.showHideable}
						/>
					</ClayManagementToolbar.Item>

					{this._getViewTypes()}
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>
		);
	}

	_renderPagination() {
		if (this.state.children.length <= 5) {
			return '';
		}

		return (
			<ClayPaginationBarWithBasicItems
				activeDelta={this.state.delta}
				activePage={this.state.page}
				deltas={[4, 8, 20, 40, 60].map((size) => ({
					label: size,
				}))}
				ellipsisBuffer={3}
				onDeltaChange={(delta) => this._handleDeltaChange(delta)}
				onPageChange={(page) => this._handlePageChange(page)}
				totalItems={this.state.children.length}
			/>
		);
	}

	_renderPanel() {
		if (this.state.viewType === 'changes') {
			return '';
		}

		return (
			<div className="col-md-3">
				<div className="panel panel-secondary">
					<div className="panel-body">
						<ClayRadioGroup
							onSelectedValueChange={(filterClass) =>
								this._handleNavigationUpdate({
									filterClass,
									nodeId: 0,
								})
							}
							selectedValue={this.state.filterClass}
						>
							{this._getRootDisplayOptions()}
						</ClayRadioGroup>
					</div>
				</div>
			</div>
		);
	}

	_renderTable() {
		if (!this.state.children || this.state.children.length === 0) {
			if (
				this.state.node.children &&
				this.state.node.children.length > 0 &&
				this.state.viewType === 'changes'
			) {
				return (
					<div className="sheet taglib-empty-result-message">
						<div className="taglib-empty-result-message-header" />
						<div className="sheet-text text-center">
							{Liferay.Language.get(
								'there-are-no-changes-to-display-in-this-view'
							)}
						</div>
					</div>
				);
			}

			return '';
		}

		return (
			<>
				<ClayTable
					className="publications-table"
					headingNoWrap
					hover
					noWrap
				>
					{this._getTableHead()}

					<ClayTable.Body>
						{this._getTableRows(
							this._filterDisplayNodes(this.state.children)
						)}
					</ClayTable.Body>
				</ClayTable>

				{this._renderPagination()}
			</>
		);
	}

	_updateRenderContent(node) {
		if (!node.modelClassNameId) {
			return;
		}

		AUI().use('liferay-portlet-url', () => {
			fetch(this._getRenderURL(node))
				.then((response) => response.text())
				.then((text) => {
					this.setState({
						dropdown: this._getDropdown(node),
						renderInnerHTML: {__html: text},
					});
				});
		});
	}

	render() {
		let content;

		if (
			this.state.viewType === 'context' &&
			this.contextView.errorMessage
		) {
			content = (
				<ClayAlert displayType="danger">
					{this.contextView.errorMessage}
				</ClayAlert>
			);
		}
		else {
			content = (
				<div className="container-fluid container-fluid-max-xl">
					<ClayBreadcrumb
						ellipsisBuffer={1}
						items={this.state.breadcrumbItems}
						spritemap={this.spritemap}
					/>

					<div className="publications-changes-content row">
						{this._renderPanel()}

						<div
							className={
								this.state.viewType === 'changes'
									? 'col-md-12'
									: 'col-md-9'
							}
						>
							{this._renderEntry()}
							{this._renderTable()}
						</div>
					</div>
				</div>
			);
		}

		return (
			<>
				{this._renderManagementToolbar()}
				{content}
			</>
		);
	}
}

export default (appProps) => (
	<Router>
		<Route
			path="/"
			render={(props) => (
				<ChangeTrackingChangesView {...appProps} {...props} />
			)}
		/>
	</Router>
);
