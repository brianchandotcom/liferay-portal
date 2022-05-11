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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import Toolbar from './Toolbar';

const VALID_EXTENSIONS = '.xliff,.xlf,.zip';

export default function ImportTranslation({
	cancelURL,
	errorMessage: initialErrorMessage = '',
	publishButtonLabel,
	saveButtonLabel,
	title,
	workflowPending = false,
}) {
	const [importFiles, setImportFiles] = useState([]);
	const [errorMessage, setErrorMessage] = useState(initialErrorMessage);
	const [publishButtonDisabled, setPublishButtonDisabled] = useState(
		workflowPending
	);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState();

	const inputFileRef = useRef();

	useEffect(() => {
		setSaveButtonDisabled(!importFiles.length);
		setPublishButtonDisabled(workflowPending || !importFiles.length);
	}, [importFiles, workflowPending]);

	const handleOnCloseError = () => {
		setErrorMessage('');
	};

	return (
		<>
			<Toolbar
				cancelURL={cancelURL}
				publishButtonDisabled={publishButtonDisabled}
				publishButtonLabel={publishButtonLabel}
				saveButtonDisabled={saveButtonDisabled}
				saveButtonLabel={saveButtonLabel}
				title={title}
			/>

			<ClayLayout.ContainerFluid className="container-view">
				<ClayLayout.Sheet
					className="translation-import-body-form"
					size="lg"
				>
					{errorMessage && (
						<ClayAlert
							displayType="danger"
							hideCloseIcon={false}
							onClose={handleOnCloseError}
							title={`${Liferay.Language.get('error')}:`}
						>
							{errorMessage}
						</ClayAlert>
					)}

					<div>
						<p className="h3">
							{Liferay.Language.get('import-files')}
						</p>

						<p className="text-secondary">
							{Liferay.Language.get(
								'please-upload-your-translation-files'
							)}
						</p>

						<div className="mt-4">
							<p className="h5">
								{Liferay.Language.get('file-upload')}
							</p>

							<input
								accept={VALID_EXTENSIONS}
								className="d-none"
								multiple
								name="file"
								onChange={(event) => {
									setImportFiles(
										Array.from(event.target?.files || [])
									);
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
											<ClayList.ItemTitle>
												{name}
											</ClayList.ItemTitle>
										</ClayList.Item>
									))}
								</ClayList>
							) : null}
						</div>
					</div>
				</ClayLayout.Sheet>
			</ClayLayout.ContainerFluid>
		</>
	);
}

ImportTranslation.propTypes = {
	cancelURL: PropTypes.string.isRequired,
	errorMessage: PropTypes.string,
	publishButtonLabel: PropTypes.string.isRequired,
	saveButtonLabel: PropTypes.string.isRequired,
	saveDraftBtnId: PropTypes.string.isRequired,
	submitBtnId: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
	workflowPending: PropTypes.bool,
};
