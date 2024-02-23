/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import {createPortletURL, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import TimelineDropdownMenu from './TimelineDropdownMenu';
import {
	WORKFLOW_STATUS_APPROVED,
	WORKFLOW_STATUS_DRAFT,
	WORKFLOW_STATUS_PENDING,
	WorkflowStatusLabel,
} from './WorkflowStatusLabel';

const PublicationTimeline = ({timelineItemsURL}) => {
	const [timelineItems, setTimelineItems] = useState([]);
	const [itemsFetched, setItemsFetched] = useState(false);

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
				p_p_id:
					'com_liferay_change_tracking_web_portlet_PublicationsPortlet',
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
				setItemsFetched(true);
			});
	}, [timelineItemsURL]);

	if (!itemsFetched) {
		return (
			<>
				<ClayLoadingIndicator displayType="secondary" size="sm" />
			</>
		);
	}
	else {
		if (timelineItems && !!timelineItems.length) {
			return (
				<div className="publication-timeline">
					{timelineItems.map((timelineItem) => (
						<ClayPanel
							key={timelineItem.id}
							style={{
								borderBottomColor: '#e7e7ed',
								marginBottom: 0,
							}}
						>
							<ClayPanel.Body>
								<ClayLayout.ContentRow>
									<ClayLayout.ContentCol expand>
										<div>
											<span
												style={{paddingRight: '10px'}}
											>
												{timelineItem.name}
											</span>

											<WorkflowStatusLabel
												workflowStatus={
													timelineItem.status.code
												}
											/>
										</div>

										<div className="text-secondary">
											{timelineItem.description}
										</div>

										<div className="text-secondary">
											{timelineItem.statusMessage}
										</div>
									</ClayLayout.ContentCol>

									<ClayLayout.ContentCol>
										<TimelineDropdownMenu
											deleteURL={
												timelineItem.status.code ===
												WORKFLOW_STATUS_DRAFT
													? timelineItem.actions
															.delete.href
													: undefined
											}
											editURL={
												timelineItem.status.code ===
												WORKFLOW_STATUS_DRAFT
													? getEditURL(
															timelineItem.id
													  )
													: undefined
											}
											revertURL={
												timelineItem.status.code ===
												WORKFLOW_STATUS_APPROVED
													? getRevertURL(
															timelineItem.id
													  )
													: undefined
											}
											reviewURL={
												timelineItem.status.code !==
												WORKFLOW_STATUS_PENDING
													? getReviewURL(
															timelineItem.id
													  )
													: undefined
											}
										/>
									</ClayLayout.ContentCol>
								</ClayLayout.ContentRow>
							</ClayPanel.Body>
						</ClayPanel>
					))}
				</div>
			);
		}

		return (
			<div className="publication-timeline timeline">
				{Liferay.Language.get('no-publications-were-found')}
			</div>
		);
	}
};

export default PublicationTimeline;
