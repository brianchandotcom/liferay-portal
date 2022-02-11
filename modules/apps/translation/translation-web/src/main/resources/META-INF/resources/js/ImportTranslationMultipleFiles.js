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

import ClayButton from '@clayui/button';
import ClayList from '@clayui/list';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

const VALID_EXTENSIONS = '.xliff,.xlf,.zip';

export default function ImportTranslationMultipleFiles({
	saveDraftBtnId,
	submitBtnId,
	workflowPending = false,
}) {
	const [importFiles, setImportFiles] = useState([]);

	const inputFileRef = useRef();

	useEffect(() => {
		Liferay.Util.toggleDisabled('#' + saveDraftBtnId, !importFiles.length);
		Liferay.Util.toggleDisabled(
			'#' + submitBtnId,
			!importFiles.length || workflowPending
		);
	}, [importFiles, saveDraftBtnId, submitBtnId, workflowPending]);

	return (
		<div>
			<p className="h3">{Liferay.Language.get('import-files')}</p>

			<p className="text-secondary">
				{Liferay.Language.get('please-upload-your-translation-files')}
			</p>

			<div className="mt-4">
				<p className="h5">{Liferay.Language.get('file-upload')}</p>

				<input
					accept={VALID_EXTENSIONS}
					className="d-none"
					multiple
					name="file"
					onChange={(event) => {
						setImportFiles(Array.from(event.target?.files || []));
					}}
					ref={inputFileRef}
					type="file"
				/>

				<ClayButton.Group spaced>
					<ClayButton
						displayType="secondary"
						onClick={() => {
							inputFileRef.current.click();
						}}
						small
					>
						{importFiles.length
							? Liferay.Language.get('replace-files')
							: Liferay.Language.get('select-files')}
					</ClayButton>

					{Boolean(importFiles.length) && (
						<ClayButton
							displayType="secondary"
							onClick={() => {
								setImportFiles([]);
								inputFileRef.current.value = '';
							}}
							small
						>
							{Liferay.Language.get('remove-files')}
						</ClayButton>
					)}
				</ClayButton.Group>

				{importFiles.length ? (
					<ClayList className="list-group-no-bordered mt-3">
						{importFiles.map(({name}) => (
							<ClayList.Item key={name}>
								<ClayList.ItemTitle>{name}</ClayList.ItemTitle>
							</ClayList.Item>
						))}
					</ClayList>
				) : null}
			</div>
		</div>
	);
}

ImportTranslationMultipleFiles.prototypes = {
	saveDraftBtnId: PropTypes.string.isRequired,
	submitBtnId: PropTypes.string.isRequired,
	workflowPending: PropTypes.bool,
};
