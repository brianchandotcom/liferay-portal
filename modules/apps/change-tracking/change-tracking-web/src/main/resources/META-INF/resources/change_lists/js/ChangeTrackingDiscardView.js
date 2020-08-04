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
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import {fetch} from 'frontend-js-web';
import React from 'react';

class ChangeTrackingDiscardView extends React.Component {
	constructor(props) {
		super(props);

		const {ctEntries, renderURL, spritemap, typeNames, userInfo} = props;

		this.ctEntries = ctEntries;
		this.renderURL = renderURL;
		this.spritemap = spritemap;
		this.typeNames = typeNames;
		this.userInfo = userInfo;

		for (let i = 0; i < this.ctEntries.length; i++) {
			const entry = this.ctEntries[i];

			const userInfo = this.userInfo[entry.userId.toString()];

			entry.portraitURL = userInfo.portraitURL;
			entry.userName = userInfo.userName;

			entry.typeName = this.typeNames[entry.modelClassNameId.toString()];
		}

		this.ctEntries.sort((a, b) => {
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
				return -1;
			}

			if (titleA > titleB) {
				return 1;
			}

			return 0;
		});

		this.state = {
			ascending: true,
			column: 'title',
			delta: 20,
			entry: null,
			page: 1,
			renderInnerHTML: null,
			sortDirectionClass: 'order-arrow-down-active',
		};
	}

	_filterDisplayEntries(entries) {
		if (entries.length > 5) {
			entries = entries.slice(
				this.state.delta * (this.state.page - 1),
				this.state.delta * this.state.page
			);
		}

		return entries;
	}

	_getRenderURL(entry) {
		const portletURL = Liferay.PortletURL.createURL(this.renderURL);

		portletURL.setParameter('ctEntryId', entry.ctEntryId);

		return portletURL.toString();
	}

	_getTableRows() {
		const rows = [];

		let currentTypeName = '';

		const entries = this._filterDisplayEntries(this.ctEntries);

		for (let i = 0; i < entries.length; i++) {
			const entry = entries[i];

			if (entry.typeName !== currentTypeName) {
				currentTypeName = entry.typeName;

				rows.push(
					<ClayTable.Row divider>
						<ClayTable.Cell colSpan={2}>
							{entry.typeName}
						</ClayTable.Cell>
					</ClayTable.Row>
				);
			}

			const cells = [];

			if (entry.portraitURL) {
				cells.push(
					<ClayTable.Cell>
						<span
							className="lfr-portal-tooltip"
							title={entry.userName}
						>
							<span className="rounded-circle sticker sticker-primary">
								<span className="sticker-overlay">
									<img
										alt="thumbnail"
										className="img-fluid"
										src={entry.portraitURL}
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

				userPortraitCss += entry.userId % 10;

				cells.push(
					<ClayTable.Cell>
						<span
							className="lfr-portal-tooltip"
							title={entry.userName}
						>
							<span className={userPortraitCss}>
								<span className="inline-item">
									<svg className="lexicon-icon">
										<use href={this.spritemap + '#user'} />
									</svg>
								</span>
							</span>
						</span>
					</ClayTable.Cell>
				);
			}

			cells.push(
				<ClayTable.Cell>
					<button
						className="change-row-button"
						onClick={() => this._handleNavigation(entry.ctEntryId)}
					>
						<div className="change-list-name">{entry.title}</div>
						<div className="change-list-description">
							{entry.description}
						</div>
					</button>
				</ClayTable.Cell>
			);

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

	_handleNavigation(entryId) {
		if (entryId === 0) {
			this.setState({
				entry: null,
				renderInnerHTML: null,
			});

			return;
		}

		for (let i = 0; i < this.ctEntries.length; i++) {
			const entry = this.ctEntries[i];

			if (entry.ctEntryId === entryId) {
				this.setState({
					entry,
				});

				AUI().use('liferay-portlet-url', () => {
					fetch(this._getRenderURL(entry))
						.then((response) => response.text())
						.then((text) => {
							this.setState({
								renderInnerHTML: {__html: text},
							});
						});
				});

				return;
			}
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

	_renderBreadcrumbs() {
		let items = [
			{
				active: true,
				label: Liferay.Language.get('home'),
			},
		];

		if (this.state.entry !== null) {
			items = [
				{
					label: Liferay.Language.get('home'),
					onClick: () => this._handleNavigation(0),
				},
				{
					active: true,
					label: this.state.entry.title,
				},
			];
		}

		return (
			<ClayBreadcrumb
				ellipsisBuffer={1}
				items={items}
				spritemap={this.spritemap}
			/>
		);
	}

	_renderEntry() {
		let content = '';

		if (this.state.renderInnerHTML !== null) {
			content = (
				<div
					className="sheet-section"
					dangerouslySetInnerHTML={this.state.renderInnerHTML}
				/>
			);
		}

		return (
			<div className="sheet">
				<h2 className="sheet-title">{this.state.entry.description}</h2>

				{content}
			</div>
		);
	}

	_renderPagination() {
		if (this.ctEntries.length <= 5) {
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
				totalItems={this.ctEntries.length}
			/>
		);
	}

	_renderTable() {
		return (
			<>
				<ClayTable className="change-lists-table" hover={false}>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell style={{width: '5%'}}>
								{Liferay.Language.get('user')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell style={{width: '95%'}}>
								{Liferay.Language.get('change')}
							</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>
					<ClayTable.Body>{this._getTableRows()}</ClayTable.Body>
				</ClayTable>

				{this._renderPagination()}
			</>
		);
	}

	render() {
		let content;

		if (this.state.entry === null) {
			content = this._renderTable();
		}
		else {
			content = this._renderEntry();
		}

		return (
			<>
				{this._renderBreadcrumbs()}
				{content}
			</>
		);
	}
}

export default function (props) {
	return <ChangeTrackingDiscardView {...props} />;
}
