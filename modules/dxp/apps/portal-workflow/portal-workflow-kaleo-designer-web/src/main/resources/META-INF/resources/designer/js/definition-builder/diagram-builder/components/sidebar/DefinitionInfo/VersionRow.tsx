/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import moment from 'moment';
import React, {useContext} from 'react';

import {DefinitionBuilderContext} from '../../../../DefinitionBuilderContext';
import {
	publishDefinitionRequest,
	retrieveDefinitionRequest,
	saveDefinitionRequest,
} from '../../../../util/fetchUtil';
import lang from '../../../../util/lang';

import './VersionRow.scss';

interface RetrieveWorkflowDefinitionResponseProps {
	active: boolean;
	content: string;
	title: string;
	title_i18n: Liferay.Language.FullyLocalizedValue<string>;
	version: string;
}

interface VersionRowProps {
	creatorName: string;
	dateCreated: string;
	setWorkflowDefinitionVersions: React.Dispatch<
		React.SetStateAction<WorkflowDefinitionVersion[]>
	>;
	versionNumber: number;
}

export function VersionRow({
	creatorName,
	dateCreated,
	setWorkflowDefinitionVersions,
	versionNumber,
}: VersionRowProps) {
	const {
		definitionName,
		setAlertMessage,
		setAlertType,
		setDefinitionName,
		setShowAlert,
		setVersion,
	} = useContext(DefinitionBuilderContext);

	const restoreSuccess = async (response: Response) => {
		const alertMessage = lang.sub(
			Liferay.Language.get('restored-to-revision-x'),
			[String(versionNumber)]
		);

		setAlertMessage(alertMessage);
		setAlertType('success');

		setShowAlert(true);

		const restoredWorkflowDefinition =
			(await response.json()) as WorkflowDefinition;

		setDefinitionName(restoredWorkflowDefinition.name);
		setVersion(parseInt(restoredWorkflowDefinition.version, 10));

		if (Liferay.FeatureFlags['LPD-29635']) {
			setWorkflowDefinitionVersions((prevValues) => [
				{
					creatorName: restoredWorkflowDefinition.creator
						?.name as string,
					dateCreated:
						restoredWorkflowDefinition.dateCreated as string,
					versionNumber: String(
						parseInt(restoredWorkflowDefinition.version, 10)
					),
				},
				...prevValues,
			]);
		}
	};

	const restoreFailed = () => {
		const alertMessage = Liferay.Language.get(
			'unable-to-restore-this-item'
		);

		setAlertMessage(alertMessage);
		setAlertType('danger');

		setShowAlert(true);
	};

	const restoreWorkflowDefinition = async (
		publishOrSaveWorkflowDefinitionRequest: (
			value: WorkflowDefinition
		) => Promise<Response>,
		requestBody: WorkflowDefinition
	) => {
		const publishOrSaveWorkflowDefinitionResponse =
			await publishOrSaveWorkflowDefinitionRequest(requestBody);

		if (!publishOrSaveWorkflowDefinitionResponse.ok) {
			restoreFailed();

			return;
		}

		restoreSuccess(publishOrSaveWorkflowDefinitionResponse);

		return;
	};

	const handleRestoreWorkflowDefinitionVersion = async () => {
		const retrieveWorkflowDefinitionResponse =
			await retrieveDefinitionRequest(definitionName, versionNumber);

		const {active, content, title, title_i18n, version} =
			(await retrieveWorkflowDefinitionResponse.json()) as RetrieveWorkflowDefinitionResponseProps;

		if (active) {
			await restoreWorkflowDefinition(publishDefinitionRequest, {
				active,
				content,
				name: definitionName,
				title,
				title_i18n,
				version,
			});
		}

		await restoreWorkflowDefinition(saveDefinitionRequest, {
			active,
			content,
			name: definitionName,
			title,
			title_i18n,
			version,
		});
	};

	return (
		<>
			<div className="lfr-workflow__version-row-container">
				{Liferay.FeatureFlags['LPD-29635'] ? (
					<div className="lfr-workflow__version-row-info-container">
						<label className="lfr-workflow__version-row-info-number">
							{Liferay.Language.get('version')} {versionNumber}
						</label>

						<span className="lfr-workflow__version-row-info-date-user">
							{moment(dateCreated).format(
								Liferay.Language.get('mmm-dd-yyyy-lt')
							)}{' '}

							by {creatorName}
						</span>
					</div>
				) : (
					<label className="text-secondary">
						{Liferay.Language.get('version')} {versionNumber}
					</label>
				)}

				<ClayButtonWithIcon
					aria-labelledby={Liferay.Language.get('restore')}
					className="lfr-workflow__version-row-restore-button"
					displayType="unstyled"
					onClick={() => handleRestoreWorkflowDefinitionVersion()}
					symbol="restore"
					title={Liferay.Language.get('restore')}
				/>
			</div>

			<div className="sheet-subtitle" />
		</>
	);
}
