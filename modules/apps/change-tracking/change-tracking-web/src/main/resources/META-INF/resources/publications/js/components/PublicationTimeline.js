/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import TimelineDropdownMenu from './TimelineDropdownMenu';
import {WorkflowStatusLabel} from './WorkflowStatusLabel';

const PublicationTimeline = ({
	namespace,
	navigate,
	spritemap,
	timelineClassNameId,
	timelineClassPK,
	timelineEditURL,
	timelineItemsURL,
}) => {
	const [timelineItems, setTimelineItems] = useState([]);
	const [loading, setLoading] = useState(true);

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
										<span style={{paddingRight: '10px'}}>
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
													timelineClassPK
												}
												timelineEditURL={
													timelineEditURL
												}
												timelineItem={timelineItem}
											/>
										</ClayDropDown>
									) : null}
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
};

export default PublicationTimeline;
