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
import {API, Input} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {FormEvent, useEffect, useRef, useState} from 'react';

import {openImportWarningModal} from '../utils/openImportWarningModal';
interface IProps {
	importObjectDefinitionURL: string;
	nameMaxLength: string;
	portletNamespace: string;
}

interface IFile {
	fileName?: string;
	inputFile?: File | null;
	inputFileValue?: string;
}

const ModalImportObjectDefinition: React.FC<IProps> = ({
	importObjectDefinitionURL,
	nameMaxLength,
	portletNamespace,
}) => {
	const [error, setError] = useState<string>('');
	const [externalReferenceCode, setExternalReferenceCode] = useState<string>(
		''
	);
	const [visible, setVisible] = useState(false);
	const inputFileRef = useRef() as React.MutableRefObject<HTMLInputElement>;
	const [name, setName] = useState('');
	const importObjectDefinitionModalComponentId = `${portletNamespace}importObjectDefinitionModal`;
	const importObjectDefinitionFormId = `${portletNamespace}importObjectDefinitionForm`;
	const nameInputId = `${portletNamespace}name`;
	const objectDefinitionJSONInputId = `${portletNamespace}objectDefinitionJSON`;
	const [{fileName, inputFile, inputFileValue}, setFile] = useState<IFile>(
		{}
	);
	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			setExternalReferenceCode('');
			setFile({
				fileName: '',
				inputFile: null,
				inputFileValue: '',
			});
			setName('');
		},
	});

	const handleImport = async (formData: FormData) => {
		try {
			await API.save(importObjectDefinitionURL, formData, 'POST');

			window.location.reload();
		}
		catch (error) {
			setError((error as Error).message);
		}
	};

	const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const formData = new FormData(event.currentTarget);
		const response = await fetch(
			`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${externalReferenceCode}`
		);

		if (response.status === 204) {
			handleImport(formData);
		}
		else {
			setVisible(false);
			openImportWarningModal({
				handleImport: () => handleImport(formData),
			});
		}
	};

	useEffect(() => {
		Liferay.component(
			importObjectDefinitionModalComponentId,
			{
				open: () => {
					setVisible(true);
				},
			},
			{
				destroyOnNavigate: true,
			}
		);

		return () =>
			Liferay.destroyComponent(importObjectDefinitionModalComponentId);
	}, [importObjectDefinitionModalComponentId, setVisible]);

	return visible ? (
		<ClayModal center observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('import-object')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm

					// @ts-ignore

					id={importObjectDefinitionFormId}
					onSubmit={handleSubmit}
				>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<ClayAlert
						displayType="info"
						title={`${Liferay.Language.get('info')}:`}
					>
						{Liferay.Language.get(
							'the-import-process-will-run-in-the-background-and-may-take-a-few-minutes'
						)}
					</ClayAlert>

					<ClayForm.Group>
						<label htmlFor={nameInputId}>
							{Liferay.Language.get('name')}
						</label>

						<ClayInput
							id={nameInputId}
							maxLength={Number(nameMaxLength)}
							name={nameInputId}
							onChange={(event) => setName(event.target.value)}
							type="text"
							value={name}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor={objectDefinitionJSONInputId}>
							{Liferay.Language.get('json-file')}
						</label>

						<ClayInput.Group>
							<ClayInput.GroupItem prepend>
								<ClayInput
									disabled
									id={objectDefinitionJSONInputId}
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
											setExternalReferenceCode('');
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

					{externalReferenceCode && (
						<Input
							disabled
							feedbackMessage={Liferay.Language.get(
								'internal-key-to-reference-the-object-definition'
							)}
							id="externalReferenceCode"
							label={Liferay.Language.get(
								'external-reference-code'
							)}
							name="externalReferenceCode"
							value={externalReferenceCode}
						/>
					)}

					<input
						className="d-none"
						name={objectDefinitionJSONInputId}
						onChange={({target}) => {
							const inputFile = target.files?.item(0);

							setFile({
								fileName: inputFile?.name,
								inputFile,
								inputFileValue: target.value,
							});

							const fileReader = new FileReader();

							fileReader.onload = () => {
								try {
									const objectDefinitionJSON = JSON.parse(
										fileReader.result as string
									) as {externalReferenceCode: string};

									setExternalReferenceCode(
										objectDefinitionJSON.externalReferenceCode
									);
								}
								catch (error) {
									setExternalReferenceCode('');
								}
							};
							fileReader.readAsText(inputFile!);
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
							disabled={!inputFile || !name}
							form={importObjectDefinitionFormId}
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

export default ModalImportObjectDefinition;
