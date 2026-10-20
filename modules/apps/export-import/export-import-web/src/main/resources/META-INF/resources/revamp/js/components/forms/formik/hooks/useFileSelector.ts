/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useField} from 'formik';
import {useCallback, useEffect, useRef, useState} from 'react';

import {ABORTED_ERROR, RequestResult} from '../../../../services/ApiHelper';
import {UploadProgress} from '../../../../types/importUpload';
import {FileSelectorStatus} from '../../../FileSelector';

const STATUS_CHANGE_DELAY = 1000;

const TRANSIENT_STATUSES: FileSelectorStatus[] = [
	'SUCCESS',
	'UPLOADING',
	'VALIDATING',
];

export type UploadRequest<T> = (
	file: File,
	onProgress: (progress: UploadProgress) => void,
	signal?: AbortSignal
) => Promise<RequestResult<T>>;

export function useFileSelector<T>(
	name: string,
	uploadRequest: UploadRequest<T>
) {
	const [field, meta, helpers] = useField(name);

	const {setError, setTouched, setValue} = helpers;

	const [status, setStatus] = useState<FileSelectorStatus>(
		field.value ? 'STABLE_SUCCESS' : 'IDLE'
	);

	const [progress, setProgress] = useState<number>();

	const [selectedFile, setSelectedFile] = useState<File | undefined>(
		field.value instanceof File ? field.value : undefined
	);

	const [serverError, setServerError] = useState<string | undefined>();

	const abortControllerRef = useRef<AbortController | null>(null);
	const isMountedRef = useRef(true);

	const handleRejection = useCallback(
		(message: string) => {
			setServerError(message);
			setStatus('ERROR');
			setSelectedFile(undefined);
			setValue(undefined);
			setTouched(true);
		},
		[setTouched, setValue]
	);

	const handleRemove = () => {
		abortControllerRef.current?.abort();
		abortControllerRef.current = null;

		setStatus('IDLE');
		setProgress(undefined);
		setSelectedFile(undefined);
		setValue(undefined);
		setServerError(undefined);
		setTouched(true);
	};

	const handleUpload = async (file: File) => {
		abortControllerRef.current?.abort();

		const controller = new AbortController();
		abortControllerRef.current = controller;

		const isCurrentUpload = () =>
			isMountedRef.current && !controller.signal.aborted;

		setStatus('UPLOADING');
		setProgress(0);
		setServerError(undefined);
		setSelectedFile(file);
		setValue(undefined);

		const handleProgress = ({percent, phase}: UploadProgress) => {
			if (!isCurrentUpload()) {
				return;
			}

			setProgress(percent);
			setStatus(phase);
		};

		try {
			const result = await uploadRequest(
				file,
				handleProgress,
				controller.signal
			);

			if (!isCurrentUpload() || result.error === ABORTED_ERROR) {
				return;
			}

			if (result.error || !result.data) {
				handleRejection(
					result.error ||
						Liferay.Language.get('an-unexpected-error-occurred')
				);

				return;
			}

			setStatus('SUCCESS');
			setValue(file);
			setTouched(true);
		}
		catch (error: any) {
			if (!isCurrentUpload() || error.name === 'AbortError') {
				return;
			}

			handleRejection(
				error.message ||
					Liferay.Language.get('an-unexpected-error-occurred')
			);
		}
	};

	const validate = useCallback(
		(value: any) => {
			if (TRANSIENT_STATUSES.includes(status)) {
				return undefined;
			}

			if (serverError) {
				return serverError;
			}

			if (!value) {
				return Liferay.Language.get('this-field-is-required');
			}

			return undefined;
		},
		[status, serverError]
	);

	useEffect(() => {
		return () => {
			isMountedRef.current = false;
			abortControllerRef.current?.abort();
		};
	}, []);

	useEffect(() => {
		if (status === 'SUCCESS') {
			const timeout = setTimeout(() => {
				if (isMountedRef.current) {
					setStatus('STABLE_SUCCESS');
				}
			}, STATUS_CHANGE_DELAY);

			return () => clearTimeout(timeout);
		}
	}, [status]);

	useEffect(() => {
		const error = validate(field.value);

		if (error !== meta.error) {
			setError(error);
		}
	}, [validate, field.value, setError, meta.error]);

	return {
		handleRejection,
		handleRemove,
		handleUpload,
		progress,
		selectedFile,
		status,
		validate,
	};
}
