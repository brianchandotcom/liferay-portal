/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {VersionRow} from './VersionRow';

interface RevisionHistoryProps {
	setWorkflowDefinitionVersions: React.Dispatch<
		React.SetStateAction<WorkflowDefinitionVersion[]>
	>;
	version: number;
	workflowDefinitionVersions: WorkflowDefinitionVersion[];
}

export function RevisionHistory({
	setWorkflowDefinitionVersions,
	version,
	workflowDefinitionVersions,
}: RevisionHistoryProps) {
	const otherVersions = [];

	if (!Liferay.FeatureFlags['LPD-29635']) {
		for (let i = version - 1; i > 0; i--) {
			otherVersions.push({versionNumber: i});
		}
	}

	const showWorkflowDefinitionVersion = () => {
		if (Liferay.FeatureFlags['LPD-29635']) {
			return workflowDefinitionVersions.length + 1;
		}

		return version;
	};

	return (
		<>
			<div className="info-group">
				<label>
					{Liferay.Language.get('current-version')}:{' '}

					{showWorkflowDefinitionVersion()}
				</label>

				<div className="sheet-subtitle" />
			</div>

			{Liferay.FeatureFlags['LPD-29635']
				? workflowDefinitionVersions.map(
						({creatorName, dateCreated, versionNumber}) => (
							<VersionRow
								creatorName={creatorName}
								dateCreated={dateCreated}
								key={dateCreated}
								setWorkflowDefinitionVersions={
									setWorkflowDefinitionVersions
								}
								versionNumber={parseInt(versionNumber, 10)}
							/>
						)
					)
				: otherVersions.map(({versionNumber}, index) => (
						<VersionRow
							creatorName=""
							dateCreated=""
							key={index}
							setWorkflowDefinitionVersions={
								setWorkflowDefinitionVersions
							}
							versionNumber={versionNumber}
						/>
					))}
		</>
	);
}
