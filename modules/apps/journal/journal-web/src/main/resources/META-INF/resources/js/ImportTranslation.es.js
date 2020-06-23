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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {usePrevious} from 'frontend-js-react-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

const VALID_EXTENSIONS = '.xliff,.xlf';

export default function ImportTranslation({
	articleResourcePrimKey,
	saveDraftBtnId,
	submitBtnId,
}) {
	const [hasError, setHasError] = useState();
	const [importFile, setImportFile] = useState();

	const inputFileRef = useRef();

	const previousFile = usePrevious(importFile);

	useEffect(() => {
		Liferay.Util.toggleDisabled('#' + saveDraftBtnId, !importFile);
		Liferay.Util.toggleDisabled('#' + submitBtnId, !importFile);

		if (importFile && window.FileReader) {
			const reader = new FileReader();

			reader.addEventListener('loadend', (event) => {
				parseFile(importFile.name, event.target.result);
			});

			reader.readAsText(importFile);
		}
	}, [importFile, parseFile, saveDraftBtnId, submitBtnId]);

	const parseFile = useCallback(
		(filename, fileData) => {
			try {
				const xmlDoc = new DOMParser().parseFromString(
					fileData,
					'text/xml'
				);

				const fileElement = xmlDoc.getElementsByTagName('file')[0];

				const fileId = fileElement.getAttribute('id');

				const id = fileId.substring(fileId.indexOf(':') + 1);

				const validFile = id === articleResourcePrimKey;

				setHasError(!validFile);

				if (!validFile) {
					setImportFile(null);
				}
			}
			catch (_error) {
				setHasError(true);
			}
		},
		[articleResourcePrimKey]
	);

	return (
		<div>
			<h3 className="h4">{Liferay.Language.get('import-file')}</h3>
			<p>
				<span className="text-secondary">
					{Liferay.Language.get(
						'please-upload-your-translation-file'
					)}
				</span>
			</p>

			<div className="mb-5 mt-4">
				<p className="h5">{Liferay.Language.get('file-upload')}</p>

				<ClayInput
					accept={VALID_EXTENSIONS}
					className="d-none"
					name="file"
					onChange={(e) => {
						setImportFile(e.target.files[0]);
					}}
					ref={inputFileRef}
					type="file"
				/>

				{!importFile && (
					<ClayButton
						displayType="secondary"
						onClick={() => {
							inputFileRef.current.click();
						}}
					>
						{Liferay.Language.get('select-file')}
					</ClayButton>
				)}

				{importFile && (
					<>
						<strong>{importFile.name}</strong>

						<ClayButtonWithIcon
							displayType="unstyled"
							onClick={() => {
								setImportFile(null);
							}}
							symbol="times-circle"
							title={Liferay.Language.get('delete')}
						/>
					</>
				)}

				{hasError && (
					<ClayForm.FeedbackGroup className="has-error">
						<ClayForm.FeedbackItem>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							<strong>{Liferay.Language.get('error')}: </strong>

							{Liferay.Util.sub(
								Liferay.Language.get(
									'the-translation-file-x-does-not-correspond-to-this-web-content'
								),
								previousFile ? previousFile.name : ''
							)}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</div>
		</div>
	);
}
