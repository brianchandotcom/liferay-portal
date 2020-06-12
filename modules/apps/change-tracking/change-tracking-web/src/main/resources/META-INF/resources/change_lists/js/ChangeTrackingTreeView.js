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

import ClayBreadcrumb from '@clayui/breadcrumb';
import {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import {fetch} from 'frontend-js-web';
import React from 'react';

class ChangeTrackingTreeView extends React.Component {
	constructor(props) {
		super(props);

		const {displayTitles, spritemap, tree} = props;

		this.displayTitles = displayTitles;
		this.filterClass = 'everything';
		this.nodeId = 0;
		this.spritemap = spritemap;
		this.tree = tree;

		this.state = {
			breadcrumbItems: this._getBreadcrumbItems(
				'everything',
				tree.everything
			),
			delta: 20,
			node: tree.everything,
			page: 1,
			renderInnerHTML: null,
			title: tree.everything.title,
		};
	}

	_getBreadcrumbItems(filterClass, node) {
		const breadcrumbItems = [];
		const homeBreadcrumbItem = {label: this.tree.everything.title};

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

	_getRootDisplayClassNode(filterClass) {
		const nodeIds = this.tree[filterClass];

		const children = [];

		const stack = [this.tree.everything];

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

	_getNode(filterClass, nodeId) {
		if (filterClass !== 'everything' && nodeId === 0) {
			return this._getRootDisplayClassNode(filterClass);
		}

		const rootNode = this.tree.everything;

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

	_getDisplayNodes(delta, nodes, page) {
		if (nodes.length <= 5) {
			return nodes;
		}

		return nodes.slice(delta * (page - 1), delta * page);
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
						<ClayTable.Cell colSpan={2}>
							{node.typeName}
						</ClayTable.Cell>
					</ClayTable.Row>
				);
			}

			let descriptionMarkup = '';

			if (node.description) {
				descriptionMarkup = (
					<div className="change-list-description">
						{node.description}
					</div>
				);
			}

			rows.push(
				<ClayTable.Row>
					<ClayTable.Cell>
						<button
							className="change-row-button"
							onClick={() =>
								this._handleNavigationUpdate({
									filterClass: this.filterClass,
									nodeId: node.id,
								})
							}
						>
							<div className="change-list-name">{node.title}</div>

							{descriptionMarkup}
						</button>
					</ClayTable.Cell>
				</ClayTable.Row>
			);
		}

		return rows;
	}

	_handleDeltaChange(delta) {
		this.setState({
			delta,
			page: 1,
		});
	}

	_handlePageChange(page) {
		this.setState({
			page,
		});
	}

	_handleNavigationUpdate(json) {
		this.filterClass = json.filterClass;
		this.nodeId = json.nodeId;

		const node = this._getNode(this.filterClass, this.nodeId);

		this.setState({
			breadcrumbItems: this._getBreadcrumbItems(this.filterClass, node),
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

	_renderTable() {
		const nodes = this.state.node.children;

		if (!nodes || nodes.length === 0) {
			return '';
		}

		return (
			<div>
				<ClayTable className="change-lists-table" hover={false}>
					<ClayTable.Body>
						{this._getTableRows(
							this._getDisplayNodes(
								this.state.delta,
								nodes,
								this.state.page
							)
						)}
					</ClayTable.Body>
				</ClayTable>

				{this._renderPagination(nodes)}
			</div>
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
				deltas={[5, 10, 20, 30, 50, 75, 100].map((size) => ({
					label: size,
				}))}
				ellipsisBuffer={3}
				onDeltaChange={(delta) => this._handleDeltaChange(delta)}
				onPageChange={(page) => this._handlePageChange(page)}
				totalItems={nodes.length}
			/>
		);
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

	_getRootDisplayOptions() {
		const rootDisplayOptions = [];

		rootDisplayOptions.push(
			<ClayRadio
				label={this.displayTitles.everything}
				value="everything"
			/>
		);

		for (let i = 0; i < this.tree.rootDisplayClasses.length; i++) {
			const className = this.tree.rootDisplayClasses[i];

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

	render() {
		return (
			<div>
				<ClayBreadcrumb
					ellipsisBuffer={1}
					items={this.state.breadcrumbItems}
					spritemap={this.spritemap}
				/>

				<div className="row">
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

					<div className="col-md-9">
						{this._renderEntry()}
						{this._renderTable()}
					</div>
				</div>
			</div>
		);
	}
}

export default function (props) {
	return <ChangeTrackingTreeView {...props} />;
}
