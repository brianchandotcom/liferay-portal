/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useContext} from 'react';

import {DefinitionBuilderContext} from '../../../../DefinitionBuilderContext';
import {
	publishDefinitionRequest,
	retrieveDefinitionRequest,
	saveDefinitionRequest,
} from '../../../../util/fetchUtil';
import lang from '../../../../util/lang';
interface RetrieveWorkflowDefinitionResponseProps {
	active: boolean;
	content: string;
	title: string;
	title_i18n: Liferay.Language.FullyLocalizedValue<string>;
	version: string;
}

interface VersionRowProps {
	versionNumber: number;
}

export function VersionRow({versionNumber}: VersionRowProps) {
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

		const {name, version} = (await response.json()) as {
			name: string;
			version: string;
		};

		setDefinitionName(name);
		setVersion(parseInt(version, 10));
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
		<div className="info-group">
			<div className="version-row">
				<label className="text-secondary">
					{Liferay.Language.get('version')} {versionNumber}
				</label>

				<ClayButtonWithIcon
					aria-labelledby={Liferay.Language.get('restore')}
					className="text-secondary"
					displayType="unstyled"
					onClick={() => handleRestoreWorkflowDefinitionVersion()}
					symbol="restore"
					title={Liferay.Language.get('restore')}
				/>
			</div>

			<div className="sheet-subtitle" />
		</div>
	);
}
