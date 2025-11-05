/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import {useEffect, useReducer, useRef} from 'react';

export enum Status {
	COMPLETED = 'COMPLETED',
	FAILED = 'FAILED',
	STARTED = 'STARTED',
}

type State = {
	downloadURL?: string;
	errorMessage?: string;
	progress: number;
	status: Status;
};

type Action =
	| {payload: {progress: number}; type: Status.STARTED}
	| {payload: {downloadURL: string}; type: Status.COMPLETED}
	| {payload?: {errorMessage?: string}; type: Status.FAILED};

const initialState: State = {
	downloadURL: undefined,
	errorMessage: undefined,
	progress: 0,
	status: Status.STARTED,
};

const POLL_INTERVAL = 1000;

function reducer(state: State, action: Action): State {
	switch (action.type) {
		case Status.STARTED:
			return {progress: action.payload.progress, status: Status.STARTED};
		case Status.COMPLETED:
			return {
				downloadURL: action.payload.downloadURL,
				progress: 100,
				status: Status.COMPLETED,
			};
		case Status.FAILED:
			return {
				errorMessage: action.payload?.errorMessage,
				progress: state.progress,
				status: Status.FAILED,
			};
		default:
			return state;
	}
}

export function useBatchEngineExportTask(importProcessId: string) {
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

					if (data.executeStatus === Status.STARTED) {
						const {processedItemsCount, totalItemsCount} = data;

						const progress =
							totalItemsCount === 0
								? 0
								: Math.floor(
										(processedItemsCount /
											totalItemsCount) *
											100
									);

						dispatch({
							payload: {progress},
							type: Status.STARTED,
						});
					}
					else if (data.executeStatus === Status.COMPLETED) {
						dispatch({
							payload: {downloadURL},
							type: Status.COMPLETED,
						});
						stopPolling();
					}
					else if (data.executeStatus === Status.FAILED) {
						dispatch({
							payload: {errorMessage: data.errorMessage},
							type: Status.FAILED,
						});
						stopPolling();
					}
				}
				catch (error: any) {
					dispatch({
						payload: {errorMessage: error.message},
						type: Status.FAILED,
					});
					stopPolling();
				}
			}, POLL_INTERVAL);
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
					type: Status.FAILED,
				});
			}
		};

		startTask();

		return () => stopPolling();
	}, [importProcessId]);

	return state;
}
