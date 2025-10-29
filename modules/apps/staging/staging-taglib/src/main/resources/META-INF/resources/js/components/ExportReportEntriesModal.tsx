/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import Modal from '@clayui/modal';
import ClayProgressBar from '@clayui/progress-bar';
import classNames from 'classnames';
import React from 'react';

import {
	StatusKey,
	useBatchEngineExportTask,
} from '../hooks/useBatchEngineExportTask';
import {downloadFile} from '../utils/downloadFile';

import type {Observer} from '@clayui/modal/lib/types';

type Status = {
	displayType: 'success' | 'info' | 'danger';
	label: string;
	message: string;
};

const STATUS_MAP: Record<StatusKey, Status> = {
	COMPLETED: {
		displayType: 'success',
		label: Liferay.Language.get('completed'),
		message: Liferay.Language.get(
			'your-file-has-been-generated-and-is-ready-to-download'
		),
	},
	FAILED: {
		displayType: 'danger',
		label: Liferay.Language.get('failed'),
		message: Liferay.Language.get(
			'an-unexpected-error-happened-while-creating-the-file'
		),
	},
	STARTED: {
		displayType: 'info',
		label: Liferay.Language.get('running'),
		message: Liferay.Language.get('errors-report-file-is-being-created'),
	},
};

export function ExportReportEntriesModal({
	filename,
	importProcessId,
	observer,
	onOpenChange,
}: {
	filename: string;
	importProcessId: string;
	observer: Observer;
	onOpenChange: (value: boolean) => void;
}) {
	const {downloadURL, errorMessage, progress, status} =
		useBatchEngineExportTask(importProcessId);

	const currentMessage =
		status === 'FAILED' && errorMessage
			? errorMessage
			: STATUS_MAP[status].message;

	return (
		<Modal
			disableAutoClose
			observer={observer}
			status={STATUS_MAP[status].displayType}
		>
			<Modal.Header>
				{Liferay.Language.get('export-report-entries')}
			</Modal.Header>

			<Modal.Body className="text-3 text-weight-semi-bold">
				<div role="status">
					<p className="mb-0">{currentMessage}</p>

					<p>{filename}</p>

					<ClayLabel displayType={STATUS_MAP[status].displayType}>
						{STATUS_MAP[status].label}
					</ClayLabel>
				</div>

				<ClayProgressBar value={progress} />
			</Modal.Body>

			<Modal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onOpenChange(false)}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							className={classNames(
								`btn-${STATUS_MAP[status].displayType}`
							)}
							disabled={status !== 'COMPLETED'}
							onClick={() => {
								if (!downloadURL) {
									return;
								}

								downloadFile(downloadURL, filename);
							}}
						>
							{status === 'STARTED' && (
								<span className="inline-item inline-item-before">
									<ClayLoadingIndicator size="xs" />
								</span>
							)}

							{Liferay.Language.get('download')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</Modal>
	);
}
