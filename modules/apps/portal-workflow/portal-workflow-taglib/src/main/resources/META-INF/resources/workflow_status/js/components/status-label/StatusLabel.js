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

import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import ClayModal, {useModal} from '@clayui/modal';
import {ClayTooltipProvider} from '@clayui/tooltip';
import WorkflowInstanceTracker from '@liferay/portal-workflow-instance-tracker-web/js/components/WorkflowInstanceTracker';
import React, {useState} from 'react';

export default function StatusLabel({
	instanceId,
	showInstanceTracker,
	statusMessage,
	statusStyle,
}) {
	const [showInstanceTrackerModal, setShowInstanceTrackerModal] = useState(
		false
	);

	const {observer} = useModal({
		onClose: () => {
			setShowInstanceTrackerModal(false);
		},
	});

	if (!showInstanceTracker) {
		return <ClayLabel displayType={statusStyle}>{statusMessage}</ClayLabel>;
	}

	return (
		<>
			<ClayTooltipProvider>
				<ClayLink
					data-tooltip-align="bottom"
					onClick={() => setShowInstanceTrackerModal(true)}
					title={Liferay.Language.get('track-workflow')}
				>
					<ClayLabel displayType={statusStyle}>
						{statusMessage}
					</ClayLabel>
				</ClayLink>
			</ClayTooltipProvider>

			{showInstanceTrackerModal && (
				<ClayModal observer={observer} size="full-screen">
					<ClayModal.Header>
						{Liferay.Language.get('track-workflow')}
					</ClayModal.Header>

					<ClayModal.Body>
						<WorkflowInstanceTracker
							workflowInstanceId={instanceId}
						/>
					</ClayModal.Body>
				</ClayModal>
			)}
		</>
	);
}
