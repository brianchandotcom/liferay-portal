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
import {ClayInput} from '@clayui/form';
import React, {useEffect, useRef, useState} from 'react';

const VALID_EXTENSIONS = '.xliff,.xlf,.zip';

export default function ImportTranslation({
	saveDraftBtnId,
	submitBtnId,
	workflowPending = false,
}) {
	const [importFile, setImportFile] = useState();

	const inputFileRef = useRef();

	useEffect(() => {
		Liferay.Util.toggleDisabled('#' + saveDraftBtnId, !importFile);
		Liferay.Util.toggleDisabled(
			'#' + submitBtnId,
			!importFile || workflowPending
		);
	}, [importFile, saveDraftBtnId, submitBtnId, workflowPending]);

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
					onChange={(event) => {
						setImportFile(event.target.files[0]);
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
			</div>
		</div>
	);
}
