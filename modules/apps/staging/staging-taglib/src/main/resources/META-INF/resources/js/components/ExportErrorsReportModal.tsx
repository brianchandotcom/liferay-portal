/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import Modal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import ClayProgressBar from '@clayui/progress-bar';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useReducer, useRef} from 'react';

type StatusKey = 'COMPLETED' | 'FAILED' | 'STARTED';

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

type State = {
	downloadURL?: string;
	errorMessage?: string;
	progress: number;
	status: StatusKey;
};

type Action =
	| {payload: {progress: number}; type: 'UPDATE_PROGRESS'}
	| {payload: {downloadURL: string}; type: 'COMPLETED'}
	| {payload?: {errorMessage?: string}; type: 'FAILED'};

const initialState: State = {
	downloadURL: undefined,
	errorMessage: undefined,
	progress: 0,
	status: 'STARTED',
};

function reducer(state: State, action: Action): State {
	switch (action.type) {
		case 'UPDATE_PROGRESS':
			return {...state, progress: action.payload.progress};
		case 'COMPLETED':
			return {
				downloadURL: action.payload.downloadURL,
				progress: 100,
				status: 'COMPLETED',
			};
		case 'FAILED':
			return {
				errorMessage: action.payload?.errorMessage,
				progress: state.progress,
				status: 'FAILED',
			};
		default:
			return state;
	}
}

function useBatchEngineExportTask(importProcessId: string) {
	const [state, dispatch] = useReducer(reducer, initialState);
	const pollingRef = useRef<number>();

	const stopPolling = () => {
		if (pollingRef.current) {
			clearInterval(pollingRef.current);
			pollingRef.current = undefined;
		}
	};

	useEffect(() => {
		const startPolling = (batchERC: string) => {
			const url = `/o/headless-batch-engine/v1.0/export-task/by-external-reference-code/${batchERC}`;
			const downloadURL = `${url}/content`;

			pollingRef.current = window.setInterval(async () => {
				try {
					const response = await fetch(url);

					if (!response.ok) {
						throw new Error();
					}

					const data = await response.json();

					if (data.executeStatus === 'STARTED') {
						dispatch({
							payload: {progress: data.progress ?? 0},
							type: 'UPDATE_PROGRESS',
						});
					}
					else if (data.executeStatus === 'COMPLETED') {
						dispatch({
							payload: {downloadURL},
							type: 'COMPLETED',
						});
						stopPolling();
					}
					else if (data.executeStatus === 'FAILED') {
						dispatch({
							payload: {errorMessage: data.errorMessage},
							type: 'FAILED',
						});
						stopPolling();
					}
				}
				catch (error: any) {
					dispatch({
						payload: {errorMessage: error.message},
						type: 'FAILED',
					});
					stopPolling();
				}
			}, 2000);
		};

		const startTask = async () => {
			try {
				const response = await fetch(
					`/o/export-import/v1.0/import-processes/${importProcessId}/report-entries/export-batch?contentType=CSV&fieldNames=errorMessage%2CmodelName%2Ctype%2CclassExternalReferenceCode%2Cstatus`,
					{
						method: 'POST',
					}
				);

				if (!response.ok) {
					throw new Error();
				}

				const {externalReferenceCode} = await response.json();

				startPolling(externalReferenceCode);
			}
			catch (error: any) {
				dispatch({
					payload: {errorMessage: error.message},
					type: 'FAILED',
				});
			}
		};

		startTask();

		return () => stopPolling();
	}, [importProcessId]);

	return state;
}

export function ExportErrorsReportModal({
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
				{Liferay.Language.get('export-errors-report')}
			</Modal.Header>

			<Modal.Body className="text-3 text-weight-semi-bold">
				<p className="mb-0">{currentMessage}</p>

				<p>{filename}</p>

				<ClayLabel displayType={STATUS_MAP[status].displayType}>
					{STATUS_MAP[status].label}
				</ClayLabel>

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

						<ClayLink
							button
							className={classNames(
								`btn-${STATUS_MAP[status].displayType}`,
								{
									disabled: status !== 'COMPLETED',
								}
							)}
							download={filename}
							href={downloadURL ?? '#'}
						>
							{Liferay.Language.get('download')}
						</ClayLink>
					</ClayButton.Group>
				}
			/>
		</Modal>
	);
}
