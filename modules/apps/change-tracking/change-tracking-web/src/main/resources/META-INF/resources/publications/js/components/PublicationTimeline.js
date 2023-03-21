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

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import React from 'react';

import TimelineDropdownMenu from './TimelineDropdownMenu';
import WorkflowStatusLabel from './WorkflowStatusLabel';

const PublicationTimeline = ({timelineItems}) => {
	if (timelineItems && !!timelineItems.length) {
		return (
			<div className="publication-timeline">
				{timelineItems.map((timelineItem) => (
					<ClayPanel key={timelineItem.id}>
						<ClayPanel.Body>
							<ClayLayout.ContentRow>
								<ClayLayout.ContentCol>
									<ClayIcon
										style={{
											color: '#6B6C7E',
											marginRight: '1em',
											marginTop: '0.25em',
										}}
										symbol="change-list"
									/>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol expand>
									<div>
										{timelineItem.name}

										<WorkflowStatusLabel
											workflowStatus={timelineItem.status}
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
											timelineItem.dropdownMenu.deleteURL
										}
										editURL={
											timelineItem.dropdownMenu.editURL
										}
										revertURL={
											timelineItem.dropdownMenu.revertURL
										}
										reviewURL={
											timelineItem.dropdownMenu.reviewURL
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
};

export default PublicationTimeline;
