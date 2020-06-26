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
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayNavigationBar from '@clayui/navigation-bar';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import {fetch} from 'frontend-js-web';
import React from 'react';

class ChangeTrackingChangesView extends React.Component {
	constructor(props) {
		super(props);

		const {changes, contextView, spritemap} = props;

		this.changes = changes;
		this.contextView = contextView;
		this.filterClass = 'everything';
		this.nodeId = 0;
		this.spritemap = spritemap;

		this.state = {
			ascending: true,
			breadcrumbItems: this._getBreadcrumbItems(
				'everything',
				'changes',
				changes
			),
			column: 'title',
			delta: 20,
			navigation: 'changes',
			node: changes,
			page: 1,
			renderInnerHTML: null,
			sortDirectionClass: 'order-arrow-down-active',
		};
	}

	_getBreadcrumbItems(filterClass, navigation, node) {
		if (navigation === 'changes') {
			if (node.id === 0) {
				return [
					{
						active: true,
						label: node.title,
					},
				];
			}

			return [
				{
					label: this.changes.title,
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
		const homeBreadcrumbItem = {label: this.contextView.everything.title};

		if (filterClass === 'everything' && node.id === 0) {
			homeBreadcrumbItem.active = true;

			breadcrumbItems.push(homeBreadcrumbItem);

			return breadcrumbItems;
		}

		homeBreadcrumbItem.onClick = () =>
			this._handleNavigationUpdate({
				filterClass: 'everything',
				nodeId: 0,
			});

		let showParent = false;

		if (filterClass === 'everything') {
			showParent = true;
		}
		else {
			breadcrumbItems.push(homeBreadcrumbItem);

			let label = filterClass;

			if (label.includes('.')) {
				label = label.substring(
					label.lastIndexOf('.') + 1,
					label.length
				);
			}

			const rootDisplayClassBreadcrumb = {label};

			if (node.id === 0) {
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
						nodeId: parent.id,
					}),
			});
		}

		breadcrumbItems.push({
			active: true,
			label: node.title,
		});

		return breadcrumbItems;
	}

	_getChangesNode(nodeId) {
		if (nodeId === 0) {
			return this.changes;
		}

		for (let i = 0; i < this.changes.children.length; i++) {
			const child = this.changes.children[i];

			if (child.id === nodeId) {
				return child;
			}
		}

		return null;
	}

	_getDisplayNodes(ascending, column, delta, nodes, page) {
		let displayNodes = nodes.slice(0);

		if (column === 'title') {
			displayNodes.sort((a, b) => {
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
			displayNodes.sort((a, b) => {
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
			displayNodes = displayNodes.slice(delta * (page - 1), delta * page);
		}

		if (column === 'modifiedDate') {
			displayNodes.sort((a, b) => {
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

		return displayNodes;
	}

	_getColumn() {
		if (this.state.navigation === 'contextView') {
			return 'title';
		}

		return this.state.column;
	}

	_getNode(filterClass, navigation, nodeId) {
		if (navigation === 'changes') {
			return this._getChangesNode(nodeId);
		}
		else if (filterClass !== 'everything' && nodeId === 0) {
			return this._getRootDisplayClassNode(filterClass);
		}

		const rootNode = this.contextView.everything;

		if (nodeId === 0) {
			return rootNode;
		}

		if (!rootNode.parents) {
			rootNode.parents = [];
		}

		const stack = [rootNode];

		while (stack.length > 0) {
			const element = stack.pop();

			if (element.id === nodeId) {
				return element;
			}
			else if (!element.children) {
				continue;
			}

			for (let i = 0; i < element.children.length; i++) {
				const child = element.children[i];

				if (!child.parents) {
					const parents = element.parents.slice(0);

					parents.push({
						id: element.id,
						title: element.title,
						typeName: element.typeName,
					});

					child.parents = parents;
				}

				stack.push(child);
			}
		}

		return null;
	}

	_getRootDisplayClassNode(filterClass) {
		const nodeIds = this.contextView[filterClass];

		const children = [];

		const stack = [this.contextView.everything];

		while (stack.length > 0) {
			const element = stack.pop();

			if (!element.children) {
				continue;
			}

			for (let i = 0; i < element.children.length; i++) {
				const child = element.children[i];

				if (nodeIds.includes(child.id)) {
					children.push(child);

					continue;
				}

				stack.push(child);
			}
		}

		return {children, id: 0, title: filterClass};
	}

	_getRootDisplayOptions() {
		const rootDisplayOptions = [];

		rootDisplayOptions.push(
			<ClayRadio
				label={Liferay.Language.get('everything')}
				value="everything"
			/>
		);

		for (let i = 0; i < this.contextView.rootDisplayClasses.length; i++) {
			const className = this.contextView.rootDisplayClasses[i];

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
							colSpan={
								this.state.navigation === 'changes' ? 3 : 1
							}
						>
							{node.typeName}
						</ClayTable.Cell>
					</ClayTable.Row>
				);
			}

			const cells = [];

			if (this.state.navigation === 'changes') {
				if (node.portraitURL) {
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
											src={node.portraitURL}
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
								nodeId: node.id,
							})
						}
					>
						<div className="change-list-name">{node.title}</div>

						{descriptionMarkup}
					</button>
				</ClayTable.Cell>
			);

			if (this.state.navigation === 'changes') {
				cells.push(
					<ClayTable.Cell>{node.timeDescription}</ClayTable.Cell>
				);
			}

			rows.push(<ClayTable.Row>{cells}</ClayTable.Row>);
		}

		return rows;
	}

	_handleDeltaChange(delta) {
		this.setState({
			delta,
			page: 1,
		});
	}

	_handleNavigationUpdate(json) {
		let navigation = json.navigation;

		if (!navigation) {
			navigation = this.state.navigation;
		}

		if (navigation === 'contextView' && this.contextView.errorMessage) {
			this.setState({
				navigation,
				renderInnerHTML: null,
			});

			return;
		}

		if (json.filterClass) {
			this.filterClass = json.filterClass;
		}

		this.nodeId = json.nodeId;

		const node = this._getNode(this.filterClass, navigation, this.nodeId);

		this.setState({
			breadcrumbItems: this._getBreadcrumbItems(
				this.filterClass,
				navigation,
				node
			),
			navigation,
			node,
			page: 1,
			renderInnerHTML: null,
			title: node.description ? node.description : node.title,
		});

		if (node.renderURL) {
			fetch(node.renderURL)
				.then((response) => response.text())
				.then((text) => {
					this.setState({
						renderInnerHTML: {__html: text},
					});
				});
		}
	}

	_handlePageChange(page) {
		this.setState({
			page,
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
		if (this.state.renderInnerHTML == null) {
			return '';
		}

		let dropdown = '';

		if (
			this.state.node.dropdownItems &&
			this.state.node.dropdownItems.length > 0
		) {
			dropdown = (
				<div className="autofit-col">
					<ClayDropDownWithItems
						alignmentPosition={Align.BottomLeft}
						items={this.state.node.dropdownItems}
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

		return (
			<div className="sheet">
				<h2 className="autofit-row sheet-title">
					<div className="autofit-col autofit-col-expand">
						<span className="heading-text">{this.state.title}</span>
					</div>

					{dropdown}
				</h2>

				<div
					className="sheet-section"
					dangerouslySetInnerHTML={this.state.renderInnerHTML}
				/>
			</div>
		);
	}

	_renderMainContent() {
		if (
			this.state.navigation === 'contextView' &&
			this.contextView.errorMessage
		) {
			return (
				<ClayAlert displayType="danger">
					{this.contextView.errorMessage}
				</ClayAlert>
			);
		}

		return (
			<>
				{this._renderManagementToolbar()}

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
								this.state.navigation === 'changes'
									? 'col-md-12'
									: 'col-md-9'
							}
						>
							{this._renderEntry()}
							{this._renderTable()}
						</div>
					</div>
				</div>
			</>
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

		if (this.state.navigation === 'changes') {
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
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>
		);
	}

	_renderPagination(nodes) {
		if (nodes.length <= 5) {
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
				totalItems={nodes.length}
			/>
		);
	}

	_renderPanel() {
		if (this.state.navigation === 'changes') {
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
							selectedValue={this.filterClass}
						>
							{this._getRootDisplayOptions()}
						</ClayRadioGroup>
					</div>
				</div>
			</div>
		);
	}

	_renderTable() {
		const nodes = this.state.node.children;

		if (!nodes || nodes.length === 0) {
			return '';
		}

		return (
			<>
				<ClayTable className="change-lists-table" hover={false}>
					{this._getTableHead()}

					<ClayTable.Body>
						{this._getTableRows(
							this._getDisplayNodes(
								this.state.ascending,
								this._getColumn(),
								this.state.delta,
								nodes,
								this.state.page
							)
						)}
					</ClayTable.Body>
				</ClayTable>

				{this._renderPagination(nodes)}
			</>
		);
	}

	_getTableHead() {
		if (this.state.navigation === 'contextView') {
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

	render() {
		const items = [];

		items.push(
			<ClayNavigationBar.Item
				active={this.state.navigation === 'changes'}
				onClick={() =>
					this._handleNavigationUpdate({
						filterClass: 'everything',
						navigation: 'changes',
						nodeId: 0,
					})
				}
			>
				<ClayLink className="nav-link" displayType="unstyled">
					{Liferay.Language.get('changes')}
				</ClayLink>
			</ClayNavigationBar.Item>
		);

		if (this.contextView) {
			items.push(
				<ClayNavigationBar.Item
					active={this.state.navigation === 'contextView'}
					onClick={() =>
						this._handleNavigationUpdate({
							navigation: 'contextView',
							nodeId: 0,
						})
					}
				>
					<ClayLink className="nav-link" displayType="unstyled">
						{Liferay.Language.get('context-view')}
					</ClayLink>
				</ClayNavigationBar.Item>
			);
		}

		return (
			<>
				<ClayNavigationBar
					className="navigation-bar"
					triggerLabel={Liferay.Language.get(this.state.navigation)}
				>
					{items}
				</ClayNavigationBar>

				{this._renderMainContent()}
			</>
		);
	}
}

export default function (props) {
	return <ChangeTrackingChangesView {...props} />;
}
