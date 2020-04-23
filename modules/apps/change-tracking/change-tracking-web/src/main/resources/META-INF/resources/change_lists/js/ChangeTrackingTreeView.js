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
import ClayTable from '@clayui/table';
import {fetch} from 'frontend-js-web';
import React from 'react';

class Component extends React.Component {
	constructor(props) {
		super(props);

		const {headerTitles, spritemap, tree} = props;

		this.headerTitles = headerTitles;
		this.spritemap = spritemap;
		this.tree = tree;

		this.state = {
			breadcrumbs: [
				{
					active: true,
					label: tree.title,
				},
			],
			nodeTitle: null,
			renderInnerHTML: null,
			tableRows: this._getTableRows(tree.children),
		};
	}

	_getNode(nodeId) {
		if (!this.tree.breadcrumbs) {
			this.tree.breadcrumbs = [];
		}

		if (nodeId === 0) {
			return this.tree;
		}

		const stack = [this.tree];

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

				if (!child.breadcrumbs) {
					const breadcrumbs = element.breadcrumbs.slice(0);

					breadcrumbs.push({
						label: element.title,
						onClick: () => this._updateNode(element.id),
					});

					child.breadcrumbs = breadcrumbs;
				}

				stack.push(child);
			}
		}

		return null;
	}

	_getTableRows(nodes) {
		const rows = [];

		if (!nodes) {
			return rows;
		}

		for (let i = 0; i < nodes.length; i++) {
			const child = nodes[i];

			let descriptionMarkup = '';

			if (child.description) {
				descriptionMarkup = (
					<div className="change-list-description" value={child.id}>
						{child.description}
					</div>
				);
			}

			rows.push(
				<ClayTable.Row>
					<ClayTable.Cell>
						<button
							className="change-row-button"
							onClick={this._handleRowClick.bind(this)}
							value={child.id}
						>
							<div className="change-list-name" value={child.id}>
								{child.title}
							</div>

							{descriptionMarkup}
						</button>
					</ClayTable.Cell>
					<ClayTable.Cell>
						<ClayDropDownWithItems
							alignmentPosition={Align.BottomLeft}
							items={child.dropdownItems}
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
					</ClayTable.Cell>
				</ClayTable.Row>
			);
		}

		return rows;
	}

	_handleRowClick(event) {
		this._updateNode(Number(event.target.getAttribute('value')));
	}

	_updateNode(nodeId) {
		const node = this._getNode(nodeId);

		const breadcrumbs = node.breadcrumbs.slice(0);

		breadcrumbs.push({
			active: true,
			label: node.title,
		});

		this.setState({
			breadcrumbs,
			nodeTitle: node.title,
			renderInnerHTML: null,
			tableRows: this._getTableRows(node.children),
		});

		if (node.renderURL) {
			fetch(node.renderURL)
				.then(response => response.text())
				.then(text => {
					this.setState({
						renderInnerHTML: {__html: text},
					});
				});
		}
	}

	render() {
		let renderEntry = '';

		if (this.state.renderInnerHTML != null) {
			renderEntry = (
				<div className="sheet">
					<div className="sheet-header">
						<h2 className="sheet-title">{this.state.nodeTitle}</h2>
					</div>
					<div
						className="sheet-section"
						dangerouslySetInnerHTML={this.state.renderInnerHTML}
					/>
				</div>
			);
		}

		let table = '';

		if (this.state.tableRows && (this.state.tableRows.length > 0)) {
			table = (
				<ClayTable className="change-lists-table" hover={false}>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell style={{width: '99%'}}>
								{this.headerTitles.change}
							</ClayTable.Cell>
							<ClayTable.Cell headingCell style={{width: '1%'}} />
						</ClayTable.Row>
					</ClayTable.Head>
					<ClayTable.Body>{this.state.tableRows}</ClayTable.Body>
				</ClayTable>
			)
		}

		return (
			<div>
				<ClayBreadcrumb
					ellipsisBuffer={1}
					items={this.state.breadcrumbs}
					spritemap={this.spritemap}
				/>

				{renderEntry}

				{table}
			</div>
		);
	}
}

export default function(props) {
	return <Component {...props} />;
}
