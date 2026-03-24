/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useField, useFormikContext} from 'formik';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {RequestResult} from '../../../common/services/ApiHelper';
import FileSelector, {FileSelectorStatus} from '../../FileSelector';
import {FormikWrapper} from './FormikWrapper';

interface LarValidationResponse {
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
	) => Promise<RequestResult<LarValidationResponse>>;
	'validExtensions'?: string;
}) {
	const [field, , helpers] = useField(name);
	const {setFieldTouched} = useFormikContext();

	const [status, setStatus] = useState<FileSelectorStatus>(
		field.value ? 'STABLE_SUCCESS' : 'IDLE'
	);
	const [currentFile, setCurrentFile] = useState<File | undefined>(
		field.value instanceof File ? field.value : undefined
	);
	const [currentError, setCurrentError] = useState<string | undefined>();

	const abortControllerRef = useRef<AbortController | null>(null);

	const {setError, setValue} = helpers;

	useEffect(() => {
		return () => {
			if (abortControllerRef.current) {
				abortControllerRef.current.abort();
			}
		};
	}, []);

	useEffect(() => {
		let timeoutId: any;

		if (status === 'SUCCESS') {
			timeoutId = setTimeout(() => {
				setStatus('STABLE_SUCCESS');
			}, 1500);
		}

		return () => {
			if (timeoutId) {
				clearTimeout(timeoutId);
			}
		};
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

			if (currentError) {
				return currentError;
			}

			if (!value) {
				return Liferay.Language.get('this-field-is-required');
			}

			return undefined;
		},
		[status, currentError]
	);

	useEffect(() => {
		setError(validate(field.value));
	}, [validate, field.value, setError]);

	const handleUpload = async (file: File) => {
		if (abortControllerRef.current) {
			abortControllerRef.current.abort();
		}

		abortControllerRef.current = new AbortController();

		setStatus('UPLOADING');
		setCurrentError(undefined);
		setCurrentFile(file);
		setValue(undefined);

		try {
			const result = await uploadRequest(
				file,
				abortControllerRef.current.signal
			);

			if (result.error === 'Aborted') {
				return;
			}

			if (result.error || !result.data || result.data.success === false) {
				const errorMsg =
					result.data?.errorMessages?.[0] ||
					result.error ||
					Liferay.Language.get('an-unexpected-error-occurred');

				setCurrentError(errorMsg);
				setStatus('ERROR');
				setCurrentFile(undefined);
				setValue(undefined);
				setFieldTouched(name, true);

				return;
			}

			setStatus('VALIDATING');

			setTimeout(() => {
				setStatus('SUCCESS');
				setValue(file);
				setFieldTouched(name, true);
			}, 600);
		}
		catch (error: any) {
			if (error.name === 'AbortError') {
				return;
			}

			setCurrentError(
				error.message ||
					Liferay.Language.get('an-unexpected-error-occurred')
			);
			setStatus('ERROR');
			setCurrentFile(undefined);
			setValue(undefined);
			setFieldTouched(name, true);
		}
	};

	const handleRejection = (error: string) => {
		setCurrentError(error);
		setStatus('ERROR');
		setCurrentFile(undefined);
		setValue(undefined);
		setFieldTouched(name, true);
	};

	const handleRemove = () => {
		if (abortControllerRef.current) {
			abortControllerRef.current.abort();
			abortControllerRef.current = null;
		}

		setStatus('IDLE');
		setCurrentFile(undefined);
		setValue(undefined);
		setCurrentError(undefined);
		setFieldTouched(name, true);
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
						file={currentFile}
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
