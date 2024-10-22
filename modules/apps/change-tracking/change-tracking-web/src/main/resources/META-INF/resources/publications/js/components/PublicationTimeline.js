/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
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
	timelineDeleteURL,
	timelineEditURL,
	timelineItemsURL,
	viewTimelineHistoryURL,
	warningIcon,
}) => {
	const MAX_DROPDOWN_ITEMS_SHOWN = 5;
	const [timelineItems, setTimelineItems] = useState([]);
	const [loading, setLoading] = useState(true);

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

	const renderTimelineItemRow = (timelineItem) => {
		return (
			<ClayDropDown.Item key={timelineItem.id}>
				<ClayLayout.ContentRow className="c-mb-1">
					<ClayLayout.ContentCol expand>
						{Number(timelineClassPK) === 0 ? (
							<div className="text-weight-bold">
								{timelineItem.title}
							</div>
						) : null}

						<div className="align-items-center d-flex">
							<span className="c-pr-2">
								{timelineItem.ctCollectionName}
							</span>

							{Liferay.FeatureFlags['LPD-20556'] &&
							!!warningIcon &&
							timelineItem.ctCollectionStatus.code ===
								WORKFLOW_STATUS_DRAFT ? (
								<ClayIcon
									className={warningIcon.conflictIconClass}
									style={{fontSize: 'medium'}}
									symbol={warningIcon.conflictIconName}
								/>
							) : null}

							<WorkflowStatusLabel
								workflowStatus={
									timelineItem.ctCollectionStatus.code
								}
							/>
						</div>

						{timelineItem.statusMessage ? (
							<div className="text-secondary">
								{timelineItem.statusMessage}
							</div>
						) : null}
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
											timelineClassPK={
												timelineItem.modelClassPK
											}
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
											timelineItem.ctCollectionStatus
												.code ===
												WORKFLOW_STATUS_DRAFT &&
											!!timelineItem.actions.delete
												? createPortletURL(
														timelineDeleteURL,
														{
															ctCollectionId:
																timelineItem.ctCollectionId,
														}
													)
												: undefined
										}
										editURL={
											timelineItem.ctCollectionStatus
												.code ===
												WORKFLOW_STATUS_DRAFT &&
											!!timelineItem.actions.update
												? createPortletURL(
														timelineEditURL,
														{
															ctCollectionId:
																timelineItem.ctCollectionId,
														}
													)
												: undefined
										}
										namespace={namespace}
										revertURL={
											timelineItem.ctCollectionStatus
												.code ===
											WORKFLOW_STATUS_APPROVED
												? getRevertURL(
														timelineItem.ctCollectionId
													)
												: undefined
										}
										reviewURL={
											timelineItem.ctCollectionStatus
												.code !==
												WORKFLOW_STATUS_PENDING &&
											!!timelineItem.actions.get
												? getReviewURL(
														timelineItem.ctCollectionId
													)
												: undefined
										}
										timelineItem={
											timelineDeleteURL
												? timelineItem
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
	}, [timelineItemsURL]);

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
				<div className="publication-timeline">
					<ClayDropDown.ItemList className="c-mb-0">
						{Liferay.FeatureFlags['LPD-20556'] ? (
							<>
								{warningIcon ? (
									<ClayAlert
										displayType="warning"
										spritemap={spritemap}
										title={Liferay.Language.get('warning')}
									>
										{Liferay.Language.get(
											warningIcon.conflictIconLabel
										)}
									</ClayAlert>
								) : null}
								{timelineItems
									.slice(0, MAX_DROPDOWN_ITEMS_SHOWN)
									.map((timelineItem) =>
										renderTimelineItemRow(timelineItem)
									)}
							</>
						) : (
							timelineItems.map((timelineItem) =>
								renderTimelineItemRow(timelineItem)
							)
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
									Liferay.Util.openModal({
										buttons: [
											{
												label: Liferay.Language.get(
													'done'
												),
												onClick: ({processClose}) => {
													processClose();
												},
											},
										],
										id: `${namespace}publication-timeline-history-modal`,
										iframeBodyCssClass:
											'entity-history-modal',
										size: 'full-screen',
										title: Liferay.Language.get(
											'view-entity-modification-history'
										),
										url: viewTimelineHistoryURL,
									});
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
