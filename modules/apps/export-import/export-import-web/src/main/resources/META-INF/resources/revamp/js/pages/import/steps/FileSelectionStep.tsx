/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useFormikContext} from 'formik';
import React, {useEffect, useRef} from 'react';

import SectionHeader from '../../../components/SectionHeader';
import {FormikFieldText} from '../../../components/forms/formik';
import {FormikFieldFileSelector} from '../../../components/forms/formik/FormikFieldFileSelector';
import {ABORTED_ERROR} from '../../../services/ApiHelper';
import ImportUploadService from '../../../services/ImportUploadService';
import {uploadImportFile} from '../../../services/uploadImportFile';
import {ImportPreview} from '../../../types/exportImportPreview';
import {UploadProgress} from '../../../types/importUpload';

function isSameFile(file1: File, file2: File) {
	return (
		file1.lastModified === file2.lastModified &&
		file1.name === file2.name &&
		file1.size === file2.size
	);
}

export default function FileSelectionStep({
	importUploadAPIURL,
	setImportPreview,
}: {
	importUploadAPIURL: string;
	setImportPreview: (importPreview?: ImportPreview) => void;
}) {
	const {setFieldTouched, setFieldValue, values} = useFormikContext<{
		fileSelector?: File;
		importUploadId?: string;
		name: string;
	}>();
	const autoFilledFileRef = useRef<File | undefined>(undefined);
	const importUploadIdRef = useRef<string | undefined>(undefined);
	const interruptedUploadRef = useRef<
		{file: File; importUploadId: string} | undefined
	>(undefined);

	useEffect(() => {
		const currentFile = values.fileSelector;

		if (currentFile === autoFilledFileRef.current) {
			return;
		}

		autoFilledFileRef.current = currentFile;

		if (currentFile instanceof File && !values.name) {
			setFieldValue('name', currentFile.name.replace(/\.lar$/i, ''));
		}

		if (!currentFile) {
			if (importUploadIdRef.current) {
				ImportUploadService.deleteImportUpload(
					importUploadAPIURL,
					importUploadIdRef.current
				);

				importUploadIdRef.current = undefined;
			}

			setFieldValue('contentSelection', undefined);
			setFieldTouched('contentSelection', false, false);
			setFieldValue('importUploadId', undefined);
			setFieldValue('siteExternalReferenceCodes', []);
			setFieldTouched('siteExternalReferenceCodes', false, false);
			setFieldValue('spaceExternalReferenceCodes', []);
			setFieldTouched('spaceExternalReferenceCodes', false, false);
			setImportPreview(undefined);
		}
	}, [
		importUploadAPIURL,
		values.fileSelector,
		values.name,
		setFieldTouched,
		setFieldValue,
		setImportPreview,
	]);

	const handleUpload = async (
		file: File,
		onProgress: (progress: UploadProgress) => void,
		signal?: AbortSignal
	) => {
		const interruptedUpload = interruptedUploadRef.current;

		const result = await uploadImportFile({
			file,
			importUploadAPIURL,
			importUploadId:
				interruptedUpload && isSameFile(interruptedUpload.file, file)
					? interruptedUpload.importUploadId
					: undefined,
			onImportUpload: (importUploadId) => {
				interruptedUploadRef.current = {file, importUploadId};
			},
			onProgress,
			signal,
		});

		if (result.error !== null && result.error !== ABORTED_ERROR) {
			return result;
		}

		interruptedUploadRef.current = undefined;

		if (result.data) {
			importUploadIdRef.current = result.data.importUploadId;

			setFieldValue('importUploadId', result.data.importUploadId);
			setImportPreview(result.data.importPreview);
		}

		return result;
	};

	return (
		<>
			<SectionHeader title={Liferay.Language.get('import-details')} />

			<FormikFieldText
				label={Liferay.Language.get('name')}
				name="name"
				required
			/>

			<SectionHeader
				id="fileSelector-label"
				subtitle={Liferay.Language.get(
					'select-and-upload-your-prepared-file'
				)}
				subtitleId="fileSelector-description"
				title={Liferay.Language.get('file-upload')}
			/>

			<FormikFieldFileSelector
				aria-describedby="fileSelector-description"
				aria-labelledby="fileSelector-label"
				name="fileSelector"
				uploadRequest={handleUpload}
				validExtensions=".lar"
			/>
		</>
	);
}
