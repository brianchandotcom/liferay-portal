/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useField} from 'formik';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {RequestResult} from '../../../common/services/ApiHelper';
import FileSelector, {FileSelectorStatus} from '../../FileSelector';
import {FormikWrapper} from './FormikWrapper';

interface ValidationResponse {
	errorMessages: string[];
	success: boolean;
	tempFilePath: string;
}

export function FormikFieldFileSelector({
	'aria-describedby': ariaDescribedby,
	'aria-labelledby': ariaLabelledby,
	name,
	progress,
	uploadRequest,
	validExtensions,
	...otherProps
}: {
	'aria-describedby'?: string;
	'aria-labelledby'?: string;
	'name': string;
	'progress'?: number;
	'uploadRequest': (
		file: File,
		signal?: AbortSignal
	) => Promise<RequestResult<ValidationResponse>>;
	'validExtensions'?: string;
}) {
	const STATUS_CHANGE_DELAY = 1000;

	const [field, meta, helpers] = useField(name);
	const {setError, setTouched, setValue} = helpers;

	const [status, setStatus] = useState<FileSelectorStatus>(
		field.value ? 'STABLE_SUCCESS' : 'IDLE'
	);
	const [selectedFile, setSelectedFile] = useState<File | undefined>(
		field.value instanceof File ? field.value : undefined
	);
	const [serverError, setServerError] = useState<string | undefined>();

	const abortControllerRef = useRef<AbortController | null>(null);
	const isMountedRef = useRef(true);
	const uploadTimeoutRef = useRef<NodeJS.Timeout | null>(null);

	useEffect(() => {
		return () => {
			isMountedRef.current = false;

			if (abortControllerRef.current) {
				abortControllerRef.current.abort();
			}

			if (uploadTimeoutRef.current) {
				clearTimeout(uploadTimeoutRef.current);
			}
		};
	}, []);

	useEffect(() => {
		let timeout: NodeJS.Timeout;

		if (status === 'SUCCESS') {
			timeout = setTimeout(() => {
				if (isMountedRef.current) {
					setStatus('STABLE_SUCCESS');
				}
			}, STATUS_CHANGE_DELAY);
		}

		return () => clearTimeout(timeout);
	}, [status]);

	const validate = useCallback(
		(value: any) => {
			if (
				status === 'UPLOADING' ||
				status === 'VALIDATING' ||
				status === 'SUCCESS'
			) {
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
		const error = validate(field.value);

		if (error !== meta.error) {
			setError(error);
		}
	}, [validate, field.value, setError, meta.error]);

	const handleUpload = async (file: File) => {
		if (abortControllerRef.current) {
			abortControllerRef.current.abort();
		}

		if (uploadTimeoutRef.current) {
			clearTimeout(uploadTimeoutRef.current);
		}

		const controller = new AbortController();

		abortControllerRef.current = controller;

		setStatus('UPLOADING');
		setServerError(undefined);
		setSelectedFile(file);
		setValue(undefined);

		try {
			const result = await uploadRequest(file, controller.signal);

			if (!isMountedRef.current || controller.signal.aborted) {
				return;
			}

			if (result.error === 'Aborted') {
				return;
			}

			if (result.error || !result.data || result.data.success === false) {
				const errorMsg =
					result.data?.errorMessages?.[0] ||
					result.error ||
					Liferay.Language.get('an-unexpected-error-occurred');

				setServerError(errorMsg);
				setStatus('ERROR');
				setSelectedFile(undefined);
				setValue(undefined);
				setTouched(true);

				return;
			}

			setStatus('VALIDATING');

			uploadTimeoutRef.current = setTimeout(() => {
				if (isMountedRef.current && !controller.signal.aborted) {
					setStatus('SUCCESS');
					setValue(file);
					setTouched(true);
				}
			}, STATUS_CHANGE_DELAY);
		}
		catch (error: any) {
			if (!isMountedRef.current || controller.signal.aborted) {
				return;
			}

			if (error.name === 'AbortError') {
				return;
			}

			setServerError(
				error.message ||
					Liferay.Language.get('an-unexpected-error-occurred')
			);
			setStatus('ERROR');
			setSelectedFile(undefined);
			setValue(undefined);
			setTouched(true);
		}
	};

	const handleRejection = (error: string) => {
		setServerError(error);
		setStatus('ERROR');
		setSelectedFile(undefined);
		setValue(undefined);
		setTouched(true);
	};

	const handleRemove = () => {
		if (abortControllerRef.current) {
			abortControllerRef.current.abort();
			abortControllerRef.current = null;
		}

		if (uploadTimeoutRef.current) {
			clearTimeout(uploadTimeoutRef.current);
			uploadTimeoutRef.current = null;
		}

		setStatus('IDLE');
		setSelectedFile(undefined);
		setValue(undefined);
		setServerError(undefined);
		setTouched(true);
	};

	return (
		<FormikWrapper name={name} validate={validate}>
			{({errorMessage}) => {
				const isActualError =
					errorMessage && (status === 'IDLE' || status === 'ERROR');

				return (
					<FileSelector
						{...otherProps}
						aria-describedby={
							[
								ariaDescribedby,
								isActualError && `${name}-error-message`,
							]
								.filter(Boolean)
								.join(' ') || undefined
						}
						aria-labelledby={ariaLabelledby}
						error={errorMessage}
						file={selectedFile}
						handleRejection={handleRejection}
						handleUpload={handleUpload}
						name={name}
						onRemove={handleRemove}
						progress={progress}
						status={status}
						validExtensions={validExtensions}
					/>
				);
			}}
		</FormikWrapper>
	);
}
