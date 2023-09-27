/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import {useCallback, useEffect, useReducer} from 'react';

import {
	HEADERS,
	POLL_INTERVAL,
	PROCESS_COMPLETED,
	PROCESS_FAILED,
} from './constants';

const ERROR = 'ERROR';
const COMPLETED = 'COMPLETED';
const LOADING = 'LOADING';
const PROGRESS = 'PROGRESS';
const START_POLLING = 'START_POLLING';
const STOP_LOADING = 'DOWNLOADING';

const initialState = {
	contentType: null,
	errorMessage: null,
	externalReferenceCode: null,
	loading: false,
	percentage: 0,
	pollingIntervalId: null,
	ready: false,
};

const setError = (error, externalReferenceCode) => ({
	payload: {
		error: error ?? Liferay.Language.get('unexpected-error'),
		externalReferenceCode,
	},
	type: ERROR,
});

const setExternalReferenceCode = (contentType, externalReferenceCode) => ({
	payload: {contentType, externalReferenceCode},
	type: COMPLETED,
});

const setProgress = (contentType, percentage) => ({
	payload: {contentType, percentage},
	type: PROGRESS,
});

export async function startTask(formDataQuerySelector, formSubmitURL) {
	const mainFormData = document.querySelector(formDataQuerySelector);

	const formData = new FormData(mainFormData);

	const response = await fetch(formSubmitURL, {
		body: formData,
		headers: HEADERS,
		method: 'POST',
	});

	if (!response.ok) {
		throw new Error(response);
	}

	return await response.json();
}

export async function getTaskStatus({
	externalReferenceCode,
	onFail,
	onProgress,
	onSuccess,
	requestTaskStatus,
}) {
	try {
		const {
			contentType,
			errorMessage,
			executeStatus,
			processedItemsCount,
			totalItemsCount,
		} = await requestTaskStatus(externalReferenceCode);

		switch (executeStatus) {
			case PROCESS_FAILED:
				onFail(
					errorMessage || Liferay.Language.get('unexpected-error')
				);
				break;
			case PROCESS_COMPLETED:
				onSuccess(contentType);
				break;
			default:
				onProgress(
					contentType,
					totalItemsCount === 0
						? 0
						: Math.round(
								(processedItemsCount / totalItemsCount) * 100
						  )
				);
		}
	}
	catch (error) {
		onFail(Liferay.Language.get('unexpected-error'));
	}
}

const reducer = (state = initialState, {payload, type}) => {
	switch (type) {
		case STOP_LOADING:
			return {
				...state,
				loading: false,
			};
		case LOADING:
			return {
				...state,
				loading: true,
			};
		case ERROR:
			clearInterval(state.pollingIntervalId);

			return {
				...state,
				errorMessage: payload.error,
				externalReferenceCode: payload.externalReferenceCode,
				loading: false,
				pollingIntervalId: null,
			};
		case COMPLETED:
			clearInterval(state.pollingIntervalId);

			return {
				...state,
				contentType: payload.contentType,
				externalReferenceCode: payload.externalReferenceCode,
				loading: false,
				percentage: 100,
				pollingIntervalId: null,
				ready: true,
			};
		case PROGRESS:
			return {
				...state,
				contentType: payload.contentType,
				percentage: payload.percentage,
			};
		case START_POLLING:
			return {
				...state,
				pollingIntervalId: payload,
			};
		default:
			return state;
	}
};

const Poller = (
	formDataQuerySelector,
	formSubmitURL,
	requestTaskStatus,
	requestTaskFile
) => {
	const [state, dispatch] = useReducer(reducer, initialState);
	const isMounted = useIsMounted();

	const dispatchIfMounted = useCallback(
		(action) => {
			if (isMounted()) {
				dispatch(action);
			}
		},
		[dispatch, isMounted]
	);

	const downloadFile = useCallback(async () => {
		dispatchIfMounted({type: LOADING});

		try {
			requestTaskFile(state.externalReferenceCode);

			dispatchIfMounted({type: STOP_LOADING});
		}
		catch (error) {
			console.error(error);

			dispatchIfMounted(setError());
		}
	}, [dispatchIfMounted, requestTaskFile, state.externalReferenceCode]);

	useEffect(() => {
		let pollingIntervalId;

		async function callStartTask() {
			dispatchIfMounted({type: LOADING});

			try {
				const {error, externalReferenceCode} = await startTask(
					formDataQuerySelector,
					formSubmitURL
				);

				if (error) {
					dispatchIfMounted(setError(error));

					return;
				}

				pollingIntervalId = setInterval(
					() =>
						getTaskStatus({
							externalReferenceCode,
							onFail: (error) =>
								dispatchIfMounted(
									setError(error, externalReferenceCode)
								),
							onProgress: (contentType, percent) =>
								dispatchIfMounted(
									setProgress(contentType, percent)
								),
							onSuccess: (contentType) =>
								dispatchIfMounted(
									setExternalReferenceCode(
										contentType,
										externalReferenceCode
									)
								),
							requestTaskStatus,
						}),
					POLL_INTERVAL
				);

				dispatchIfMounted({
					payload: pollingIntervalId,
					type: START_POLLING,
				});
			}
			catch (error) {
				console.error(error);

				dispatchIfMounted(setError());
			}
		}

		callStartTask();

		return () => {
			if (pollingIntervalId) {
				clearInterval(pollingIntervalId);
			}
		};
	}, [
		dispatchIfMounted,
		formDataQuerySelector,
		formSubmitURL,
		isMounted,
		requestTaskStatus,
	]);

	return {
		contentType: state.contentType,
		downloadFile,
		errorMessage: state.errorMessage,
		externalReferenceCode: state.externalReferenceCode,
		loading: state.loading,
		percentage: state.percentage,
		ready: state.ready,
	};
};

export default Poller;
