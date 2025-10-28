/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import {useEffect, useReducer, useRef} from 'react';

export type StatusKey = 'COMPLETED' | 'FAILED' | 'STARTED';

type State = {
	downloadURL?: string;
	errorMessage?: string;
	progress: number;
	status: StatusKey;
};

type Action =
	| {payload: {progress: number}; type: 'STARTED'}
	| {payload: {downloadURL: string}; type: 'COMPLETED'}
	| {payload?: {errorMessage?: string}; type: 'FAILED'};

const initialState: State = {
	downloadURL: undefined,
	errorMessage: undefined,
	progress: 0,
	status: 'STARTED',
};

const POLL_INTERVAL = 1000;

function reducer(state: State, action: Action): State {
	switch (action.type) {
		case 'STARTED':
			return {progress: action.payload.progress, status: 'STARTED'};
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

					if (data.executeStatus === 'STARTED') {
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
							type: 'STARTED',
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
					type: 'FAILED',
				});
			}
		};

		startTask();

		return () => stopPolling();
	}, [importProcessId]);

	return state;
}
