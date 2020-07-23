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

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import React from 'react';

class ChangeTrackingDiscardView extends React.Component {
	constructor(props) {
		super(props);

		const {ctEntries, diffURL, spritemap, typeNames, userInfo} = props;

		this.ctEntries = ctEntries;
		this.diffURL = diffURL;
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
			page: 1,
			sortDirectionClass: 'order-arrow-down-active',
		};

		AUI().use('liferay-portlet-url', () => {
			for (let i = 0; i < this.ctEntries.length; i++) {
				const entry = this.ctEntries[i];

				entry.diffURL = this._getDiffURL(entry);
			}

			this.setState({
				showDiff: true,
			});
		});
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

	_getDiffURL(entry) {
		const portletURL = Liferay.PortletURL.createURL(this.diffURL);

		portletURL.setParameter('ctEntryId', entry.ctEntryId);

		return (
			"javascript:Liferay.Util.openWindow({dialog: {destroyOnHide: true}, title: '" +
			entry.title +
			"', uri: '" +
			portletURL.toString() +
			"'});"
		);
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
						<ClayTable.Cell colSpan={3}>
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
					<div className="change-list-name">{entry.title}</div>
					<div className="change-list-description">
						{entry.description}
					</div>
				</ClayTable.Cell>
			);

			if (this.state.showDiff) {
				cells.push(
					<ClayTable.Cell>
						<a
							className="btn btn-secondary btn-sm"
							href={entry.diffURL}
							type="button"
						>
							{Liferay.Language.get('view')}
						</a>
					</ClayTable.Cell>
				);
			}
			else {
				cells.push(<ClayTable.Cell />);
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

	render() {
		return (
			<>
				<ClayTable className="change-lists-table" hover={false}>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell style={{width: '5%'}}>
								{Liferay.Language.get('user')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell style={{width: '90%'}}>
								{Liferay.Language.get('change')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell style={{width: '5%'}} />
						</ClayTable.Row>
					</ClayTable.Head>
					<ClayTable.Body>{this._getTableRows()}</ClayTable.Body>
				</ClayTable>

				{this._renderPagination()}
			</>
		);
	}
}

export default function (props) {
	return <ChangeTrackingDiscardView {...props} />;
}
