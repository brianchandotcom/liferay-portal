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
import {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayModal, {useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayPanel from '@clayui/panel';
import React, {useState} from 'react';

const ChangeTrackingConflictsView = ({
	publishURL,
	resolvedConflicts,
	spritemap,
	unresolvedConflicts,
}) => {
	return (
		<div className="sheet sheet-lg">
			<div className="sheet-header">
				<h2 className="sheet-title">
					{Liferay.Language.get('conflicting-changes')}
				</h2>

				{unresolvedConflicts.length > 0 && (
					<ClayAlert
						displayType="warning"
						spritemap={spritemap}
						title={Liferay.Language.get(
							'this-publication-contains-conflicting-changes-that-must-be-manually-resolved-before-publishing'
						)}
					/>
				)}

				{unresolvedConflicts.length == 0 && (
					<ClayAlert
						displayType="success"
						spritemap={spritemap}
						title={Liferay.Language.get(
							'no-unresolved-conflicts-ready-to-publish'
						)}
					/>
				)}
			</div>

			<div className="sheet-section">
				{unresolvedConflicts.length > 0 && (
					<ClayPanel
						collapsable
						defaultExpanded
						displayTitle={
							Liferay.Language.get('needs-manual-resolution') +
							' (' +
							unresolvedConflicts.length +
							')'
						}
						showCollapseIcon={true}
						spritemap={spritemap}
					>
						<ClayPanel.Body>
							<ConflictsTable
								conflicts={unresolvedConflicts}
								spritemap={spritemap}
							/>
						</ClayPanel.Body>
					</ClayPanel>
				)}
			</div>
			<div className="sheet-section">
				{resolvedConflicts.length > 0 && (
					<ClayPanel
						collapsable
						displayTitle={
							Liferay.Language.get('automatically-resolved') +
							' (' +
							resolvedConflicts.length +
							')'
						}
						showCollapseIcon={true}
						spritemap={spritemap}
					>
						<ClayPanel.Body>
							<ConflictsTable
								conflicts={resolvedConflicts}
								spritemap={spritemap}
							/>
						</ClayPanel.Body>
					</ClayPanel>
				)}
			</div>
			<div className="sheet-footer sheet-footer-btn-block-sm-down">
				<div className="btn-group">
					<div className="btn-group-item">
						<button className={unresolvedConflicts.length > 0 ? "btn btn-primary disabled"  : "btn btn-primary" } onClick={() => submitForm(document.hrefFm, publishURL)} type="button">
							{Liferay.Language.get('publish')}
						</button>
					</div>
					<div className="btn-group-item">
						<button className="btn btn-secondary" type="button">
							{Liferay.Language.get('cancel')}
						</button>
					</div>
				</div>
			</div>
		</div>
	);
};

const ConflictsTable = ({conflicts, spritemap}) => {
	const [delta, setDelta] = useState(20);
	const [page, setPage] = useState(1);
	const [viewConflict, setViewConflict] = useState(null);

	/* eslint-disable no-unused-vars */
	const {observer, onClose} = useModal({
		onClose: () => setViewConflict(null),
	});

	const getAlertFooter = (conflict) => {
		if (
			!conflict.dismissURL &&
			(!conflict.actions || conflict.actions.length === 0)
		) {
			return '';
		}

		const buttons = [];

		if (conflict.actions) {
			for (let i = 0; i < conflict.actions.length; i++) {
				const action = conflict.actions[i];

				buttons.push(
					<a className="btn btn-secondary btn-sm" href={action.href}>
						<span className="inline-item inline-item-before">
							<ClayIcon
								spritemap={spritemap}
								symbol={action.symbol}
							/>
						</span>

						{action.label}
					</a>
				);
			}
		}

		if (conflict.dismissURL) {
			buttons.push(
				<a
					className="btn btn-secondary btn-sm"
					href={conflict.dismissURL}
				>
					{Liferay.Language.get('dismiss')}
				</a>
			);
		}

		return (
			<ClayAlert.Footer>
				<div className="btn-group-sm" role="group">
					{buttons}
				</div>
			</ClayAlert.Footer>
		);
	};

	const getDismissAction = (conflict) => {
		if (!conflict.dismissURL) {
			return '';
		}

		return (
			<ClayList.ItemField>
				<a
					className="btn btn-secondary btn-sm"
					href={conflict.dismissURL}
				>
					{Liferay.Language.get('dismiss')}
				</a>
			</ClayList.ItemField>
		);
	};

	const getDropdownMenu = (conflict) => {
		if (!conflict.actions) {
			return '';
		}

		const items = [];

		for (let i = 0; i < conflict.actions.length; i++) {
			const action = conflict.actions[i];

			items.push({
				href: action.href,
				label: action.label,
				symbolLeft: action.symbol,
			});
		}

		return (
			<ClayList.ItemField>
				<ClayDropDownWithItems
					alignmentPosition={Align.BottomLeft}
					items={items}
					spritemap={spritemap}
					trigger={
						<ClayButtonWithIcon
							displayType="unstyled"
							small
							spritemap={spritemap}
							symbol="ellipsis-v"
						/>
					}
				/>
			</ClayList.ItemField>
		);
	};

	const getQuickActionMenu = (conflict) => {
		if (!conflict.actions) {
			return '';
		}

		const items = [];

		for (let i = 0; i < conflict.actions.length; i++) {
			const action = conflict.actions[i];

			items.push(
				<ClayList.QuickActionMenu.Item
					className="lfr-portal-tooltip"
					href={action.href}
					spritemap={spritemap}
					symbol={action.symbol}
					title={action.label}
				/>
			);
		}

		return (
			<ClayList.ItemField>
				<ClayList.QuickActionMenu>{items}</ClayList.QuickActionMenu>
			</ClayList.ItemField>
		);
	};

	const getListItems = () => {
		const items = [];

		let filteredConflicts = conflicts.slice(0);

		if (filteredConflicts.length > 5) {
			filteredConflicts = filteredConflicts.slice(
				delta * (page - 1),
				delta * page
			);
		}

		for (let i = 0; i < filteredConflicts.length; i++) {
			const conflict = filteredConflicts[i];

			items.push(
				<ClayList.Item flex>
					<ClayList.ItemField expand>
						<a onClick={() => setViewConflict(conflict)}>
							<ClayList.ItemText
								className="conflicts-description-text"
								subtext
							>
								{conflict.description}
							</ClayList.ItemText>
							<ClayList.ItemTitle>
								{conflict.title}
							</ClayList.ItemTitle>
							<ClayList.ItemText
								className={
									'conflicts-' + conflict.alertType + '-text'
								}
							>
								<strong>
									{conflict.conflictDescription + ': '}
								</strong>
								{conflict.conflictResolution}
							</ClayList.ItemText>
						</a>
					</ClayList.ItemField>

					{getDismissAction(conflict)}

					{getQuickActionMenu(conflict)}

					{getDropdownMenu(conflict)}
				</ClayList.Item>
			);
		}

		return items;
	};

	const handleDeltaChange = (delta) => {
		setDelta(delta);
		setPage(1);
	};

	const renderPagination = () => {
		if (conflicts.length <= 5) {
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
				onDeltaChange={(delta) => handleDeltaChange(delta)}
				onPageChange={(page) => setPage(page)}
				totalItems={conflicts.length}
			/>
		);
	};

	const renderViewModal = () => {
		if (!viewConflict) {
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
						<div className="autofit-col">
							<div className="modal-title">
								{viewConflict.title}
							</div>
							<div className="modal-description">
								{viewConflict.description}
							</div>
						</div>
					</div>
				</ClayModal.Header>
				<ClayModal.Header
					className="publications-conflicts-header"
					withTitle={false}
				>
					<ClayAlert
						displayType={viewConflict.alertType}
						spritemap={spritemap}
						title={viewConflict.conflictDescription + ':'}
					>
						{viewConflict.conflictResolution}

						{getAlertFooter(viewConflict)}
					</ClayAlert>
				</ClayModal.Header>
				<ClayModal.Body url={viewConflict.viewURL}></ClayModal.Body>
			</ClayModal>
		);
	};

	return (
		<>
			{renderViewModal()}

			<ClayList showQuickActionsOnHover>{getListItems()}</ClayList>

			{renderPagination()}
		</>
	);
};

export default function (props) {
	return <ChangeTrackingConflictsView {...props} />;
}
