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
	errorMessage: initialErrorMessage = '',
	portletNamespace,
	portletResource,
	publishButtonLabel,
	redirect,
	saveButtonLabel,
	title,
	workflowActionPublish,
	workflowActionSaveDraft,
	workflowPending = false,
}) {
	const [importFiles, setImportFiles] = useState([]);
	const [errorMessage, setErrorMessage] = useState(initialErrorMessage);
	const [publishButtonDisabled, setPublishButtonDisabled] = useState(
		workflowPending
	);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState();
	const [workflowAction, setWorkflowAction] = useState(workflowActionPublish);

	const inputFileRef = useRef();

	useEffect(() => {
		setSaveButtonDisabled(!importFiles.length);
		setPublishButtonDisabled(workflowPending || !importFiles.length);
	}, [importFiles, workflowPending]);

	const handleOnCloseError = () => {
		setErrorMessage('');
	};

	const handleOnSave = () => {
		setWorkflowAction(workflowActionSaveDraft);
	};

	return (
		<>
			<Toolbar
				cancelURL={redirect}
				onSave={handleOnSave}
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
					<input
						defaultValue={redirect}
						name={`${portletNamespace}redirect`}
						type="hidden"
					/>

					<input
						defaultValue={portletResource}
						name={`${portletNamespace}portletResource`}
						type="hidden"
					/>

					<input
						name={`${portletNamespace}workflowAction`}
						type="hidden"
						value={workflowAction}
					/>

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
								data-testid="filesInput"
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
	errorMessage: PropTypes.string,
	portletNamespace: PropTypes.string.isRequired,
	portletResource: PropTypes.string.isRequired,
	publishButtonLabel: PropTypes.string.isRequired,
	redirect: PropTypes.string.isRequired,
	saveButtonLabel: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
	workflowActionPublish: PropTypes.number.isRequired,
	workflowActionSaveDraft: PropTypes.number.isRequired,
	workflowPending: PropTypes.bool,
};
