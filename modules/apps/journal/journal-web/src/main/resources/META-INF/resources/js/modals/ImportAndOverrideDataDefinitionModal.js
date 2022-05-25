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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

const ImportAndOverrideDataDefinitionModal = ({portletNamespace}) => {
	const [visible, setVisible] = useState(false);
	const [
		importAndOverrideStructureURL,
		setImportAndOverrideStructureURL,
	] = useState('');
	const inputFileRef = useRef();
	const importAndOverrideDataDefinitionModalComponentId = `${portletNamespace}importAndOverrideDataDefinitionModal`;
	const importAndOverrideDataDefinitionFormId = `${portletNamespace}importAndOverrideDataDefinitionForm`;
	const jsonFileInputId = `${portletNamespace}jsonFile`;
	const [{fileName, inputFile, inputFileValue}, setFile] = useState({
		fileName: '',
		inputFile: null,
		inputFileValue: '',
	});
	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			setFile({
				fileName: '',
				inputFile: null,
				inputFileValue: '',
			});
		},
	});

	useEffect(() => {
		Liferay.component(
			importAndOverrideDataDefinitionModalComponentId,
			{
				open: (importAndOverrideDDMStructureURL) => {
					setImportAndOverrideStructureURL(
						importAndOverrideDDMStructureURL
					);
					setVisible(true);
				},
			},
			{
				destroyOnNavigate: true,
			}
		);

		return () =>
			Liferay.destroyComponent(
				importAndOverrideDataDefinitionModalComponentId
			);
	}, [importAndOverrideDataDefinitionModalComponentId, setVisible]);

	return visible ? (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('import-structure')}
			</ClayModal.Header>

			<ClayModal.Body className="pt-0 px-0">
				<ClayForm
					action={importAndOverrideStructureURL}
					encType="multipart/form-data"
					id={importAndOverrideDataDefinitionFormId}
					method="POST"
				>
					<ClayAlert
						displayType="warning"
						title={`${Liferay.Language.get('warning')}:`}
						variant="stripe"
					>
						{Liferay.Language.get(
							'there-are-content-references-to-this-structure.-you-may-lose-data-if-a-field-name-is-renamed-or-removed'
						)}
					</ClayAlert>

					<ClayForm.Group className="pb-0 pt-5 px-4">
						<label htmlFor={jsonFileInputId}>
							{Liferay.Language.get('json-file')}
						</label>

						<ClayInput.Group>
							<ClayInput.GroupItem prepend>
								<ClayInput
									disabled
									id={jsonFileInputId}
									type="text"
									value={fileName}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem append shrink>
								<ClayButton
									displayType="secondary"
									onClick={() => inputFileRef.current.click()}
								>
									{Liferay.Language.get('select')}
								</ClayButton>
							</ClayInput.GroupItem>

							{inputFile && (
								<ClayInput.GroupItem shrink>
									<ClayButton
										displayType="secondary"
										onClick={() => {
											setFile({
												fileName: '',
												inputFile: null,
												inputFileValue: '',
											});
										}}
									>
										{Liferay.Language.get('clear')}
									</ClayButton>
								</ClayInput.GroupItem>
							)}
						</ClayInput.Group>
					</ClayForm.Group>

					<input
						className="d-none"
						name={jsonFileInputId}
						onChange={({target}) => {
							const [inputFile] = target.files;
							setFile({
								fileName: inputFile.name,
								inputFile,
								inputFileValue: target.value,
							});
						}}
						ref={inputFileRef}
						type="file"
						value={inputFileValue}
					/>
				</ClayForm>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!inputFile}
							form={importAndOverrideDataDefinitionFormId}
							type="submit"
						>
							{Liferay.Language.get('import')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	) : null;
};

ImportAndOverrideDataDefinitionModal.propTypes = {
	importAndOverrideDDMStructureURL: PropTypes.string,
	portletNamespace: PropTypes.string,
};

export default ImportAndOverrideDataDefinitionModal;
