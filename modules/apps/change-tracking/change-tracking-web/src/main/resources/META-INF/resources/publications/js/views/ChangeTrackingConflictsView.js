/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDatePicker from '@clayui/date-picker';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayModal, {useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayPanel from '@clayui/panel';
import ClayTimePicker from '@clayui/time-picker';
import {navigate, openConfirmModal} from 'frontend-js-web';
import React, {useState} from 'react';

import ChangeTrackingBaseScheduleView from './ChangeTrackingBaseScheduleView';
import ChangeTrackingRenderView from './ChangeTrackingRenderView';

class ChangeTrackingConflictsView extends ChangeTrackingBaseScheduleView {
	constructor(props) {
		super(props);

		const {
			hasUnapprovedChanges,
			learnLink,
			publishURL,
			redirect,
			resolvedConflicts,
			schedule,
			scheduleURL,
			showPageOverwriteWarning,
			spritemap,
			timeZone,
			unapprovedChangesAllowed,
			unresolvedConflicts,
		} = props;

		this.hasUnapprovedChanges = hasUnapprovedChanges;
		this.learnLink = learnLink;
		this.publishURL = publishURL;
		this.redirect = redirect;
		this.resolvedConflicts = resolvedConflicts;
		this.schedule = schedule;
		this.scheduleURL = scheduleURL;
		this.showPageOverwriteWarning = showPageOverwriteWarning;
		this.spritemap = spritemap;
		this.timeZone = timeZone;
		this.unapprovedChangesAllowed = unapprovedChangesAllowed;
		this.unresolvedConflicts = unresolvedConflicts;

		this.state = {
			date: null,
			dateError: '',
			formError: null,
			time: {
				hours: '--',
				minutes: '--',
			},
			timeError: '',
			validationError: null,
		};
	}

	handleSubmit() {
		if (!this.schedule) {
			submitForm(document.hrefFm, this.publishURL);

			return;
		}

		this.doSchedule(this.scheduleURL);
	}

	render() {
		return (
			<div className="sheet sheet-lg">
				<div className="sheet-header">
					<h2 className="sheet-title">
						{Liferay.Language.get('checking-changes')}
					</h2>

					{this.hasUnapprovedChanges && (
						<ClayAlert
							displayType="warning"
							spritemap={this.spritemap}
							title={
								this.unapprovedChangesAllowed
									? Liferay.Language.get(
											'this-publication-contains-unapproved-changes'
									  )
									: Liferay.Language.get(
											'this-publication-contains-unapproved-changes-that-must-be-approved-before-publishing'
									  )
							}
						/>
					)}

					{!!this.unresolvedConflicts.length && (
						<ClayAlert
							displayType="warning"
							spritemap={this.spritemap}
						>
							<span>
								{Liferay.Language.get(
									'this-publication-contains-conflicting-changes-that-must-be-manually-resolved-before-publishing'
								) + ' '}
							</span>

							<a href={this.learnLink.url}>
								{this.learnLink.message}
							</a>
						</ClayAlert>
					)}

					{(!this.hasUnapprovedChanges ||
						(this.hasUnapprovedChanges &&
							this.unapprovedChangesAllowed)) &&
						!this.unresolvedConflicts.length && (
							<ClayAlert
								displayType="success"
								spritemap={this.spritemap}
								title={Liferay.Language.get(
									'no-unresolved-conflicts-ready-to-publish'
								)}
							/>
						)}

					{this.showPageOverwriteWarning && (
						<ClayAlert
							displayType="info"
							spritemap={this.spritemap}
							title={Liferay.Language.get(
								"this-publication-contains-changes-to-a-content-page.-publishing-these-changes-will-fully-overwrite-the-page's-content-and-layout-in-production"
							)}
						/>
					)}
				</div>

				<div className="sheet-section">
					{!!this.unresolvedConflicts.length && (
						<ClayPanel
							collapsable
							defaultExpanded
							displayTitle={
								Liferay.Language.get(
									'needs-manual-resolution'
								) +
								' (' +
								this.unresolvedConflicts.length +
								')'
							}
							showCollapseIcon
							spritemap={this.spritemap}
						>
							<ClayPanel.Body>
								<ConflictsTable
									conflicts={this.unresolvedConflicts}
									spritemap={this.spritemap}
								/>
							</ClayPanel.Body>
						</ClayPanel>
					)}
				</div>

				<div className="sheet-section">
					{!!this.resolvedConflicts.length && (
						<ClayPanel
							collapsable
							displayTitle={
								Liferay.Language.get('automatically-resolved') +
								' (' +
								this.resolvedConflicts.length +
								')'
							}
							showCollapseIcon={true}
							spritemap={this.spritemap}
						>
							<ClayPanel.Body>
								<ConflictsTable
									conflicts={this.resolvedConflicts}
									spritemap={this.spritemap}
								/>
							</ClayPanel.Body>
						</ClayPanel>
					)}
				</div>

				{this.schedule && (
					<div className="sheet-section">
						<div className="sheet-subtitle">
							{Liferay.Language.get('schedule')}
						</div>

						<label>{Liferay.Language.get('date-and-time')}</label>

						<div className="input-group">
							<div className={this.getDateClassName()}>
								<div>
									<ClayDatePicker
										disabled={
											!!this.unresolvedConflicts.length
										}
										onValueChange={this.handleDateChange}
										placeholder="YYYY-MM-DD"
										spritemap={this.spritemap}
										timezone={this.timeZone}
										value={this.state.date}
										years={{
											end: new Date().getFullYear() + 1,
											start: new Date().getFullYear() - 1,
										}}
									/>

									{this.getDateHelpText()}
								</div>
							</div>

							<div className={this.getTimeClassName()}>
								<div>
									<ClayTimePicker
										disabled={
											!!this.unresolvedConflicts.length
										}
										onChange={this.handleTimeChange}
										spritemap={this.spritemap}
										timezone={this.timeZone}
										value={this.state.time}
									/>

									{this.getTimeHelpText()}
								</div>
							</div>
						</div>
					</div>
				)}

				{this.state.formError && (
					<ClayAlert
						displayType="danger"
						spritemap={this.spritemap}
						title={this.state.formError}
					/>
				)}

				<div className="sheet-footer sheet-footer-btn-block-sm-down">
					<div className="btn-group">
						<div className="btn-group-item">
							<button
								className={
									!!this.unresolvedConflicts.length ||
									(this.hasUnapprovedChanges &&
										!this.unapprovedChangesAllowed)
										? 'btn btn-primary disabled'
										: 'btn btn-primary'
								}
								onClick={() => this.handleSubmit()}
								type="button"
							>
								{this.schedule
									? Liferay.Language.get('schedule')
									: Liferay.Language.get('publish')}
							</button>
						</div>

						<div className="btn-group-item">
							<button
								className="btn btn-secondary"
								onClick={() => navigate(this.redirect)}
								type="button"
							>
								{Liferay.Language.get('cancel')}
							</button>
						</div>
					</div>
				</div>
			</div>
		);
	}
}

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
			(!conflict.actions || !conflict.actions.length)
		) {
			return '';
		}

		const buttons = [];

		if (conflict.actions) {
			for (let i = 0; i < conflict.actions.length; i++) {
				const action = conflict.actions[i];

				if (action.confirmationMessage) {
					buttons.push(
						<ClayButton
							displayType="secondary"
							onClick={() =>
								openConfirmModal({
									message: action.confirmationMessage,
									onConfirm: (isConfirmed) =>
										isConfirmed &&
										submitForm(
											document.hrefFm,
											action.href
										),
								})
							}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon
									spritemap={spritemap}
									symbol={action.symbol}
								/>
							</span>

							{action.label}
						</ClayButton>
					);
				}
				else {
					buttons.push(
						<a
							className="btn btn-secondary btn-sm"
							href={action.href}
						>
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
		if (!conflict.actions || !conflict.actions.length) {
			return '';
		}

		const firstAction = conflict.actions[0];

		if (conflict.actions.length === 1) {
			return (
				<ClayList.ItemField>
					{firstAction.confirmationMessage ? (
						<ClayButton
							displayType="secondary"
							onClick={() =>
								openConfirmModal({
									message: firstAction.confirmationMessage,
									onConfirm: (isConfirmed) =>
										isConfirmed &&
										submitForm(
											document.hrefFm,
											firstAction.href
										),
								})
							}
							small
						>
							<span className="inline-item inline-item-before">
								<ClayIcon
									spritemap={spritemap}
									symbol={firstAction.symbol}
								/>
							</span>

							{firstAction.label}
						</ClayButton>
					) : (
						<a
							className="btn btn-secondary btn-sm"
							href={firstAction.href}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon
									spritemap={spritemap}
									symbol={firstAction.symbol}
								/>
							</span>

							{firstAction.label}
						</a>
					)}
				</ClayList.ItemField>
			);
		}

		const items = [];

		for (let i = 0; i < conflict.actions.length; i++) {
			const action = conflict.actions[i];

			if (action.confirmationMessage) {
				items.push({
					label: action.label,
					onClick: () =>
						openConfirmModal({
							message: action.confirmationMessage,
							onConfirm: (isConfirmed) =>
								isConfirmed &&
								submitForm(document.hrefFm, action.href),
						}),
					symbolLeft: action.symbol,
				});
			}
			else {
				items.push({
					href: action.href,
					label: action.label,
					symbolLeft: action.symbol,
				});
			}
		}

		return (
			<ClayList.ItemField>
				<ClayButton.Group className="dropdown">
					{firstAction.confirmationMessage ? (
						<ClayButton
							displayType="secondary"
							onClick={() =>
								openConfirmModal({
									message: firstAction.confirmationMessage,
									onConfirm: (isConfirmed) =>
										isConfirmed &&
										submitForm(
											document.hrefFm,
											firstAction.href
										),
								})
							}
							small
						>
							<span className="inline-item inline-item-before">
								<ClayIcon
									spritemap={spritemap}
									symbol={firstAction.symbol}
								/>
							</span>

							{firstAction.label}
						</ClayButton>
					) : (
						<a
							className="btn btn-secondary btn-sm"
							href={firstAction.href}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon
									spritemap={spritemap}
									symbol={firstAction.symbol}
								/>
							</span>

							{firstAction.label}
						</a>
					)}

					<ClayDropDownWithItems
						alignmentPosition={Align.BottomLeft}
						items={items}
						spritemap={spritemap}
						trigger={
							<ClayButtonWithIcon
								displayType="secondary"
								small
								spritemap={spritemap}
								symbol="caret-bottom"
							/>
						}
					/>
				</ClayButton.Group>
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

				<div className="publications-modal-body">
					<ChangeTrackingRenderView
						dataURL={viewConflict.dataURL}
						showHeader={false}
						spritemap={spritemap}
					/>
				</div>
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
