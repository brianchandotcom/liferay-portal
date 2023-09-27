/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
					<ClayPanel
						key={timelineItem.id}
						style={{borderBottomColor: '#e7e7ed', marginBottom: 0}}
					>
						<ClayPanel.Body>
							<ClayLayout.ContentRow>
								<ClayLayout.ContentCol expand>
									<div>
										<span style={{paddingRight: '10px'}}>
											{timelineItem.name}
										</span>

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
