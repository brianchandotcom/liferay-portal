/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {API, Input} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {FormEvent, useEffect, useRef, useState} from 'react';

import {FormDataJSONFormat, jsonToFormData} from '../utils/formData';
import {ModalImportWarning} from './ModalImportWarning';
interface ModalImportObjectDefinitionProps {
	importObjectDefinitionURL: string;
	nameMaxLength: string;
	portletNamespace: string;
}

type TFile = {
	fileName?: string;
	inputFile?: File | null;
};

export default function ModalImportObjectDefinition({
	importObjectDefinitionURL,
	nameMaxLength,
	portletNamespace,
}: ModalImportObjectDefinitionProps) {
	const [error, setError] = useState<string>('');
	const [externalReferenceCode, setExternalReferenceCode] = useState<string>(
		''
	);
	const [importFormData, setImportFormData] = useState<FormData>();
	const [visible, setVisible] = useState(false);
	const [warningModalVisible, setWarningModalVisible] = useState(false);
	const inputFileRef = useRef() as React.MutableRefObject<HTMLInputElement>;
	const [name, setName] = useState('');
	const importObjectDefinitionModalComponentId = `${portletNamespace}importObjectDefinitionModal`;
	const importObjectDefinitionFormId = `${portletNamespace}importObjectDefinitionForm`;
	const nameInputId = `${portletNamespace}name`;
	const objectDefinitionJSONInputId = `${portletNamespace}objectDefinitionJSON`;
	const [{fileName, inputFile}, setFile] = useState<TFile>({});

	const warningModalBody: string[] = [
		Liferay.Language.get(
			'there-is-an-object-definition-with-the-same-external-reference-code-as-the-imported-one'
		),
		Liferay.Language.get(
			'before-importing-the-new-object-definition-you-may-want-to-back-up-its-entries-to-prevent-data-loss'
		),
		Liferay.Language.get('do-you-want-to-proceed-with-the-import-process'),
	];

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			setExternalReferenceCode('');
			setFile({
				fileName: '',
				inputFile: null,
			});
			setName('');
			setImportFormData(undefined);
		},
	});

	const handleImport = async (formData: FormData) => {
		try {
			await API.save({
				item: formData,
				method: 'POST',
				url: importObjectDefinitionURL,
			});

			window.location.reload();
		}
		catch (error) {
			setError((error as Error).message);
		}
	};

	const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const formData = new FormData(event.currentTarget);
		const formDataObject: FormDataJSONFormat = {};
		formData.forEach((value, key) => {
			if (key.includes('objectDefinitionJSON')) {
				formDataObject[key] = inputFile as File;

				return;
			}

			formDataObject[key] = value;

			return;
		});

		const newFormData = jsonToFormData(formDataObject);

		const response = await fetch(
			`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${externalReferenceCode}`
		);

		if (response.status === 204) {
			handleImport(newFormData);
		}
		else {
			setImportFormData(newFormData);
			setVisible(false);
			setWarningModalVisible(true);
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
				{Liferay.Language.get('import-object-definition')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm
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
						{Liferay.FeatureFlags['LPS-148856'] &&
							Liferay.Language.get(
								'the-object-definition-will-be-imported-to-the-uncategorized-folder'
							)}
						&nbsp;
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
								'unique-key-for-referencing-the-object-definition'
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

							if (inputFile) {
								setFile({
									fileName: inputFile?.name,
									inputFile,
								});

								const fileReader = new FileReader();

								fileReader.readAsText(inputFile);

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
							}
						}}
						ref={inputFileRef}
						type="file"
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
	) : warningModalVisible ? (
		<ModalImportWarning
			handleImport={() => handleImport(importFormData as FormData)}
			header={Liferay.Language.get('update-existing-object-definition')}
			onClose={() => {
				setWarningModalVisible(false);
				setImportFormData(undefined);
			}}
			paragraphs={warningModalBody}
		/>
	) : null;
}
