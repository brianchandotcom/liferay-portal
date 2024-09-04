/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {createPortletURL, fetch, getPortletId} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import TimelineDropdownMenu from './TimelineDropdownMenu';
import {
	WORKFLOW_STATUS_APPROVED,
	WORKFLOW_STATUS_DRAFT,
	WORKFLOW_STATUS_PENDING,
	WorkflowStatusLabel,
} from './WorkflowStatusLabel';

const PublicationTimeline = ({
	namespace,
	navigate,
	spritemap,
	timelineClassNameId,
	timelineClassPK,
	timelineEditURL,
	timelineItemsURL,
}) => {
	const MAX_DROPDOWN_ITEMS_SHOWN = 6;
	const [timelineItems, setTimelineItems] = useState([]);
	const [loading, setLoading] = useState(true);
	const [showModal, setShowModal] = useState(false);

	/* eslint-disable no-unused-vars */
	const {observer, onClose} = useModal({
		onClose: () => setShowModal(false),
	});

	const createMVCRenderCommandURL = (
		ctCollectionId,
		mvcRenderCommandName,
		additionalParams = {}
	) => {
		return createPortletURL(
			themeDisplay.getLayoutRelativeControlPanelURL(),
			{
				ctCollectionId,
				mvcRenderCommandName,
				p_p_id: getPortletId(namespace),
				...additionalParams,
			}
		).toString();
	};

	const getEditURL = (ctCollectionId) => {
		return createMVCRenderCommandURL(
			ctCollectionId,
			'/change_tracking/edit_ct_collection'
		);
	};

	const getRevertURL = (ctCollectionId) => {
		return createMVCRenderCommandURL(
			ctCollectionId,
			'/change_tracking/undo_ct_collection',
			{revert: true}
		);
	};

	const getReviewURL = (ctCollectionId) => {
		return createMVCRenderCommandURL(
			ctCollectionId,
			'/change_tracking/view_changes'
		);
	};

	const renderModal = () => {
		if (!showModal) {
			return '';
		}

		return (
			<ClayModal
				className="entity-history-modal"
				observer={observer}
				size="full-screen"
				spritemap={spritemap}
			>
				<ClayModal.Header>
					<div className="autofit-row">
						{Liferay.Language.get('view-all-history')}
					</div>
				</ClayModal.Header>

				<ClayModal.Body
					style={{borderTop: 0, marginTop: 0, paddingTop: 0}}
				>
					<FrontendDataSet
						creationMenu={null}
						id="PublicationTimelineEntityHistoryTable"
						items={timelineItems}
						itemsPerPage={10}
						namespace={namespace}
						selectedItemsKey="id"
						showManagementBar={false}
						showPagination={true}
						showSearch={false}
						views={[
							{
								contentRenderer: 'table',
								label: 'Table',
								name: 'table',
								schema: {
									fields: [
										{
											actionId: 'view',
											contentRenderer: 'actionLink',
											fieldName: 'name',
											label: Liferay.Language.get(
												'publication'
											),
											sortable: true,
										},
										timelineItems[0].ctEntryStatus
											? {
													contentRenderer: 'status',
													fieldName: 'ctEntryStatus',
													label: Liferay.Language.get(
														'status'
													),
													sortable: true,
												}
											: null,
										{
											fieldName: 'ctEntryUser',
											label: Liferay.Language.get('user'),
											sortable: true,
										},
										{
											fieldName: 'ctEntryChangeType',
											label: Liferay.Language.get(
												'changed'
											),
											sortable: true,
										},
										{
											contentRenderer: 'dateTime',
											fieldName: 'ctEntryDateModified',
											label: Liferay.Language.get(
												'last-modified'
											),
											sortable: true,
										},
									],
								},
								thumbnail: 'table',
							},
						]}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton
							aria-label={Liferay.Language.get('done')}
							displayType="primary"
							onClick={() => {
								onClose();
							}}
						>
							{Liferay.Language.get('done')}
						</ClayButton>
					}
				/>
			</ClayModal>
		);
	};

	const renderTimelineItemRow = (timelineItem) => {
		return (
			<ClayDropDown.Item key={timelineItem.id}>
				<ClayLayout.ContentRow className="c-mb-1">
					<ClayLayout.ContentCol expand>
						<div>
							<span className="c-pr-2">{timelineItem.name}</span>

							{timelineItem.ctEntryStatus ? (
								<WorkflowStatusLabel
									workflowStatus={
										timelineItem.ctEntryStatus.code
									}
								/>
							) : null}
						</div>

						<div className="text-secondary">
							{timelineItem.description}
						</div>

						<div className="text-secondary">
							{timelineItem.ctEntryStatusMessage}
						</div>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol>
						{Liferay.FeatureFlags['LPD-20556'] ? (
							<>
								{timelineItem.actions.get ? (
									<ClayDropDown
										alignmentPosition={Align.BottomLeft}
										renderMenuOnClick
										spritemap={spritemap}
										trigger={
											<ClayButtonWithIcon
												aria-label="timeline-actions"
												displayType="unstyled"
												size="sm"
												spritemap={spritemap}
												symbol="ellipsis-v"
											/>
										}
									>
										<TimelineDropdownMenu
											namespace={namespace}
											navigate={navigate}
											timelineClassNameId={
												timelineClassNameId
											}
											timelineClassPK={timelineClassPK}
											timelineEditURL={timelineEditURL}
											timelineItem={timelineItem}
										/>
									</ClayDropDown>
								) : null}
							</>
						) : (
							<>
								{timelineItem.actions ? (
									<TimelineDropdownMenu
										deleteURL={
											timelineItem.status.code ===
												WORKFLOW_STATUS_DRAFT &&
											!!timelineItem.actions.delete
												? timelineItem.actions.delete
														.href
												: undefined
										}
										editURL={
											timelineItem.status.code ===
												WORKFLOW_STATUS_DRAFT &&
											!!timelineItem.actions.update
												? getEditURL(timelineItem.id)
												: undefined
										}
										revertURL={
											timelineItem.status.code ===
											WORKFLOW_STATUS_APPROVED
												? getRevertURL(timelineItem.id)
												: undefined
										}
										reviewURL={
											timelineItem.status.code !==
												WORKFLOW_STATUS_PENDING &&
											!!timelineItem.actions.get
												? getReviewURL(timelineItem.id)
												: undefined
										}
									/>
								) : null}
							</>
						)}
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			</ClayDropDown.Item>
		);
	};

	useEffect(() => {
		if (!timelineItemsURL) {
			return;
		}

		fetch(timelineItemsURL)
			.then((response) => {
				return response.json();
			})
			.then((jsonResponse) => {
				setTimelineItems(jsonResponse.items);
				setLoading(false);
			});

		for (const timelineItem in timelineItems) {
			fetch(
				`/o/change-tracking-rest/v1.0/ct-collections/${timelineItem.id}/ct-entries/by-model-class-name-id/${timelineClassNameId}/by-model-class-pk/${timelineClassPK}`,
				{method: 'GET'}
			)
				.then((response) => {
					return response.json();
				})
				.then((jsonResponse) => {
					timelineItem['ctEntryChangeType'] = jsonResponse.changeType;
					timelineItem['ctEntryDateModified'] =
						jsonResponse.dateModified;
					timelineItem['ctEntryId'] = jsonResponse.id;
					timelineItem['ctEntryStatus'] = jsonResponse.status;
					timelineItem['ctEntryStatusMessage'] =
						jsonResponse.statusMessage;
					timelineItem['ctEntryUser'] = jsonResponse.ownerName;
				});
		}
	}, [timelineClassNameId, timelineClassPK, timelineItems, timelineItemsURL]);

	if (loading) {
		return (
			<>
				<ClayLoadingIndicator displayType="secondary" size="sm" />
			</>
		);
	}
	if (timelineItems && !!timelineItems.length) {
		return (
			<>
				{renderModal()}

				<div className="publication-timeline">
					<ClayDropDown.ItemList className="c-mb-0">
						{Liferay.FeatureFlags['LPD-20556']
							? timelineItems
									.slice(0, MAX_DROPDOWN_ITEMS_SHOWN)
									.map((timelineItem) =>
										renderTimelineItemRow(timelineItem)
									)
							: timelineItems.map((timelineItem) =>
									renderTimelineItemRow(timelineItem)
								)}
					</ClayDropDown.ItemList>

					{timelineItems.length > MAX_DROPDOWN_ITEMS_SHOWN &&
					Liferay.FeatureFlags['LPD-20556'] ? (
						<div className="dropdown-section" role="presentation">
							<ClayButton
								aria-label={Liferay.Language.get('view-more')}
								className="btn-block"
								displayType="secondary"
								onClick={() => {
									setShowModal(true);
								}}
							>
								{Liferay.Language.get('view-more')}
							</ClayButton>
						</div>
					) : null}
				</div>
			</>
		);
	}

	return (
		<div className="publication-timeline timeline">
			{Liferay.Language.get('no-publications-were-found')}
		</div>
	);
};

export default PublicationTimeline;
