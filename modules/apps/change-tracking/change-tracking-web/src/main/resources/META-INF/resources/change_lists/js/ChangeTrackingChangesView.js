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

class ChangeTrackingChangesView extends React.Component {
	constructor(props) {
		super(props);

		const {
			changes,
			contextView,
			discardURL,
			models,
			renderCTEntryURL,
			renderDiffURL,
			rootDisplayClasses,
			spritemap,
			typeNames,
			userInfo,
		} = props;

		this.changes = changes;
		this.contextView = contextView;
		this.discardURL = discardURL;
		this.models = models;
		this.renderCTEntryURL = renderCTEntryURL;
		this.renderDiffURL = renderDiffURL;
		this.rootDisplayClasses = rootDisplayClasses;
		this.spritemap = spritemap;
		this.typeNames = typeNames;
		this.userInfo = userInfo;

		const keys = Object.keys(this.models);

		for (let i = 0; i < keys.length; i++) {
			const model = this.models[keys[i]];

			if (!model.typeName) {
				model.typeName = this.typeNames[
					model.modelClassNameId.toString()
				];
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

		const node = this._getNode('everything', 0, 'changes');

		this.state = {
			ascending: true,
			breadcrumbItems: this._getBreadcrumbItems(
				node,
				'everything',
				0,
				'changes'
			),
			children: this._filterHideableNodes(node.children, false),
			column: 'title',
			delta: 20,
			dropdown: '',
			filterClass: 'everything',
			node,
			page: 1,
			renderInnerHTML: null,
			showHideable: false,
			sortDirectionClass: 'order-arrow-down-active',
			viewType: 'changes',
		};
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

		if (this._getColumn() === 'title') {
			nodes.sort((a, b) => {
				const titleA = a.title;
				const titleB = b.title;
				const typeNameA = a.typeName.toUpperCase();
				const typeNameB = b.typeName.toUpperCase();

				if (typeNameA < typeNameB) {
					return -1;
				}

				if (typeNameA > typeNameB) {
					return 1;
				}

				if (titleA < titleB) {
					if (ascending) {
						return -1;
					}

					return 1;
				}

				if (titleA > titleB) {
					if (ascending) {
						return 1;
					}

					return -1;
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

	_getTableHead() {
		if (this.state.viewType === 'context') {
			return '';
		}

		return (
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell style={{width: '5%'}}>
						{Liferay.Language.get('user')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '80%'}}>
						{Liferay.Language.get('change')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '15%'}}>
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
							colSpan={this.state.viewType === 'changes' ? 3 : 1}
						>
							{node.typeName}
						</ClayTable.Cell>
					</ClayTable.Row>
				);
			}

			const cells = [];

			if (this.state.viewType === 'changes') {
				const portraitURL = this._getPortraitURL(node);

				if (portraitURL) {
					cells.push(
						<ClayTable.Cell>
							<span
								className="lfr-portal-tooltip"
								title={this._getUserName(node)}
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
								title={this._getUserName(node)}
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
			}

			let descriptionMarkup = '';

			if (node.description) {
				descriptionMarkup = (
					<div className="change-list-description">
						{node.description}
					</div>
				);
			}

			cells.push(
				<ClayTable.Cell>
					<button
						className="change-row-button"
						onClick={() =>
							this._handleNavigationUpdate({
								nodeId: node.nodeId,
							})
						}
					>
						<div className="change-list-name">{node.title}</div>

						{descriptionMarkup}
					</button>
				</ClayTable.Cell>
			);

			if (this.state.viewType === 'changes') {
				cells.push(
					<ClayTable.Cell>{node.timeDescription}</ClayTable.Cell>
				);
			}

			rows.push(<ClayTable.Row>{cells}</ClayTable.Row>);
		}

		return rows;
	}

	_getUserName(node) {
		return this.userInfo[node.userId.toString()].userName;
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
						nodeId: 0,
						viewType: 'context',
					}),
				symbolLeft: 'pages-tree',
			},
		];

		return (
			<ClayManagementToolbar.Item expand>
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

		this.setState({
			breadcrumbItems: this._getBreadcrumbItems(
				node,
				filterClass,
				nodeId,
				viewType
			),
			children: this._filterHideableNodes(node.children, showHideable),
			dropdown: '',
			filterClass,
			node,
			page: 1,
			renderInnerHTML: null,
			showHideable,
			viewType,
		});

		AUI().use('liferay-portlet-url', () => {
			this._setDropdown(node);
		});

		if (nodeId > 0) {
			AUI().use('liferay-portlet-url', () => {
				fetch(this._getRenderURL(node))
					.then((response) => response.text())
					.then((text) => {
						this.setState({
							renderInnerHTML: {__html: text},
						});
					});
			});
		}
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
		const items = [
			{
				active: this._getColumn() === 'title',
				label: Liferay.Language.get('title'),
				onClick: () => this._handleSortColumnChange('title'),
			},
		];

		if (this.state.viewType === 'changes') {
			items.push({
				active: this._getColumn() === 'modifiedDate',
				label: Liferay.Language.get('modified-date'),
				onClick: () => this._handleSortColumnChange('modifiedDate'),
			});
		}

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

					<ClayManagementToolbar.Item>
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

					<ClayManagementToolbar.Item>
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
				<ClayTable className="change-lists-table" hover={false}>
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

	_setDropdown(node) {
		let dropdownItems = node.dropdownItems;

		if (!dropdownItems) {
			dropdownItems = [];
		}
		else {
			dropdownItems = dropdownItems.slice(0);
		}

		dropdownItems.push({
			href: this._getDiscardURL(node),
			label: Liferay.Language.get('discard'),
		});

		this.setState({
			dropdown: (
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
			),
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

					<div className="change-lists-changes-content row">
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

export default function (props) {
	return <ChangeTrackingChangesView {...props} />;
}
