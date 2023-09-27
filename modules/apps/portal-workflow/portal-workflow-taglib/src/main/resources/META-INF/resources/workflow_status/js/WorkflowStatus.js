/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ModelInfo from './components/model-info/ModelInfo';
import StatusLabel from './components/status-label/StatusLabel';

import '../css/main.scss';

export default function WorkflowStatus({
	id,
	idLabel,
	instanceId,
	showInstanceTracker,
	showStatusLabel,
	statusLabel,
	statusMessage,
	statusStyle,
	version,
	versionLabel,
}) {
	return (
		<>
			<ModelInfo label={idLabel} value={id} />

			<ModelInfo label={versionLabel} value={version} />

			{showStatusLabel && (
				<span className="mr-2 workflow-label">
					{`${statusLabel}: `}
				</span>
			)}

			<StatusLabel
				instanceId={instanceId}
				showInstanceTracker={showInstanceTracker}
				statusMessage={statusMessage}
				statusStyle={statusStyle}
			/>
		</>
	);
}
