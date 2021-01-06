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

import ClayModal, {useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

import ChangeTrackingRenderView from './ChangeTrackingRenderView';

const ChangeTrackingDiscardView = ({
	ctEntriesJSONArray,
	spritemap,
	typeNames,
	userInfo,
}) => {
	const [delta, setDelta] = useState(20);
	const [page, setPage] = useState(1);
	const [viewEntry, setViewEntry] = useState(null);

	/* eslint-disable no-unused-vars */
	const {observer, onClose} = useModal({
		onClose: () => setViewEntry(null),
	});

	const ctEntries = ctEntriesJSONArray.slice(0);

	for (let i = 0; i < ctEntries.length; i++) {
		const entry = ctEntries[i];

		const entryUserInfo = userInfo[entry.userId.toString()];

		entry.portraitURL = entryUserInfo.portraitURL;
		entry.userName = entryUserInfo.userName;

		entry.typeName = typeNames[entry.modelClassNameId.toString()];
	}

	ctEntries.sort((a, b) => {
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

	const filterDisplayEntries = (entries) => {
		if (entries.length > 5) {
			return entries.slice(delta * (page - 1), delta * page);
		}

		return entries;
	};

	const getUserPortrait = (entry) => {
		if (entry.portraitURL) {
			return (
				<span data-tooltip-align="top" title={entry.userName}>
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
			);
		}

		let userPortraitCss =
			'sticker sticker-circle sticker-light user-icon-color-';

		userPortraitCss += entry.userId % 10;

		return (
			<span data-tooltip-align="top" title={entry.userName}>
				<span className={userPortraitCss}>
					<span className="inline-item">
						<svg className="lexicon-icon">
							<use href={spritemap + '#user'} />
						</svg>
					</span>
				</span>
			</span>
		);
	};

	const getTableRows = () => {
		const rows = [];

		let currentTypeName = '';

		const entries = filterDisplayEntries(ctEntries);

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

			rows.push(
				<ClayTable.Row
					className="cursor-pointer"
					onClick={() => setViewEntry(entry)}
				>
					<ClayTable.Cell>{getUserPortrait(entry)}</ClayTable.Cell>
					<ClayTable.Cell>
						<div className="publication-name">{entry.title}</div>
						<div className="publication-description">
							{entry.description}
						</div>
					</ClayTable.Cell>
				</ClayTable.Row>
			);
		}

		return rows;
	};

	const renderPagination = () => {
		if (ctEntries.length <= 5) {
			return '';
		}

		return (
			<ClayPaginationBarWithBasicItems
				activeDelta={delta}
				activePage={page}
				deltas={[4, 8, 20, 40, 60].map((size) => ({
					label: size,
				}))}
				ellipsisBuffer={3}
				onDeltaChange={(newDelta) => {
					setDelta(newDelta);
					setPage(1);
				}}
				onPageChange={(newPage) => setPage(newPage)}
				totalItems={ctEntries.length}
			/>
		);
	};

	const renderViewModal = () => {
		if (!viewEntry) {
			return '';
		}

		return (
			<ClayModal
				className="publications-modal"
				observer={observer}
				size="full-screen"
				spritemap={spritemap}
			>
				<ClayModal.Header>
					<div className="autofit-row">
						<div className="autofit-col publications-discard-user-portrait">
							{getUserPortrait(viewEntry)}
						</div>
						<div className="autofit-col">
							<div className="modal-title">{viewEntry.title}</div>
							<div className="modal-description">
								{viewEntry.description}
							</div>
						</div>
					</div>
				</ClayModal.Header>
				<div className="publications-modal-body">
					<ChangeTrackingRenderView
						dataURL={viewEntry.dataURL}
						spritemap={spritemap}
					/>
				</div>
			</ClayModal>
		);
	};

	return (
		<>
			{renderViewModal()}

			<ClayTooltipProvider>
				<ClayTable className="publications-table" hover>
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
					<ClayTable.Body>{getTableRows()}</ClayTable.Body>
				</ClayTable>
			</ClayTooltipProvider>

			{renderPagination()}
		</>
	);
};

export default function (props) {
	return <ChangeTrackingDiscardView {...props} />;
}
