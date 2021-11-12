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

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useCallback, useEffect, useReducer} from 'react';

import {
	exportAPI,
	getExportFileURL,
	getPollingExportStatusProcess,
} from '../api';
import {POLLING_EXPORT_STATUS_TIMEOUT} from '../constants';

const ERROR = 'ERROR';
const COMPLETED = 'COMPLETED';
const LOADING = 'LOADING';
const PROGRESS = 'PROGRESS';
const START_POLLING = 'START_POLLING';

const initialState = {
	contentType: null,
	errorMessage: null,
	exportFileURL: null,
	loading: false,
	percentage: 0,
	pollingIntervalId: null,
};

const setError = (error) => ({
	payload: error ?? Liferay.Language.get('unexpected-error'),
	type: ERROR,
});

const setExportFileURL = async (contentType, taskId) => {
	const exportFileURL = await getExportFileURL(taskId);

	return {
		payload: {contentType, exportFileURL},
		type: COMPLETED,
	};
};

const setProgress = (contentType, percentage) => ({
	payload: {contentType, percentage},
	type: PROGRESS,
});

const reducer = (state = initialState, {payload, type}) => {
	switch (type) {
		case LOADING:
			return {
				...state,
				loading: true,
			};
		case ERROR:
			clearInterval(state.pollingIntervalId);

			return {
				...state,
				errorMessage: payload,
				loading: false,
			};
		case COMPLETED:
			clearInterval(state.pollingIntervalId);

			return {
				...state,
				contentType: payload.contentType,
				exportFileURL: payload.exportFileURL,
				loading: false,
				percentage: 100,
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

const usePollingExport = (formDataQuerySelector, formSubmitURL) => {
	const isMounted = useIsMounted();
	const [state, dispatch] = useReducer(reducer, initialState);

	const dispatchIfMounted = useCallback(
		async (action) => {
			if (isMounted()) {
				dispatch(await action);
			}
		},
		[dispatch, isMounted]
	);

	useEffect(() => {
		let pollingIntervalId;

		async function callAPI() {
			dispatchIfMounted({type: LOADING});

			try {
				const {error, exportTaskId} = await exportAPI(
					formDataQuerySelector,
					formSubmitURL
				);

				if (error) {
					dispatchIfMounted(setError(error));
				}

				pollingIntervalId = setInterval(
					() =>
						getPollingExportStatusProcess({
							onFail: (error) =>
								dispatchIfMounted(setError(error)),
							onProgress: (contentType, percent) =>
								dispatchIfMounted(
									setProgress(contentType, percent)
								),
							onSuccess: (contentType) =>
								dispatchIfMounted(
									setExportFileURL(contentType, exportTaskId)
								),
							taskId: exportTaskId,
						}),
					POLLING_EXPORT_STATUS_TIMEOUT
				);

				dispatchIfMounted({
					payload: pollingIntervalId,
					type: START_POLLING,
				});
			}
			catch (error) {
				dispatchIfMounted(setError());
			}
		}

		callAPI();

		return () => {
			if (pollingIntervalId) {
				clearInterval(pollingIntervalId);
			}
		};
	}, [dispatchIfMounted, formDataQuerySelector, formSubmitURL, isMounted]);

	return {
		contentType: state.contentType,
		errorMessage: state.errorMessage,
		exportFileURL: state.exportFileURL,
		loading: state.loading,
		percentage: state.percentage,
	};
};

export default usePollingExport;
