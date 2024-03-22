/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal, {useModal} from '@clayui/modal';
import {API} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {FormEvent, useEffect, useState} from 'react';

import {FormDataJSONFormat, jsonToFormData} from '../../utils/formData';
import {ModalImportContent} from './ModalImportContent';
import {ModalImportWarning} from './ModalImportWarning';

export type ModalImportKeys =
	| 'listTypeDefinition'
	| 'objectDefinition'
	| 'objectDefinitions'
	| 'objectFolder';
interface ModalImportProps {
	JSONInputId: string;
	apiURL: string;
	handleOnClose?: () => void;
	importExtendedInfo?: {
		key: string;
		value: string;
	};
	importURL: string;
	modalImportKey: ModalImportKeys;
	nameMaxLength: string;
	onAfterImport?: () => void;
	portletNamespace: string;
	showModal?: boolean;
}

export type TFile = {
	fileName?: string;
	inputFile?: File | null;
};

export default function ModalImport({
	JSONInputId,
	apiURL,
	handleOnClose,
	importExtendedInfo,
	importURL,
	modalImportKey,
	nameMaxLength,
	onAfterImport,
	portletNamespace,
	showModal,
}: ModalImportProps) {
	const [
		alreadyImportedObjectDefinitions,
		setAlreadyImportedObjectDefinitions,
	] = useState<ObjectDefinition[]>([]);
	const [error, setError] = useState<API.ErrorDetails>();
	const [externalReferenceCode, setExternalReferenceCode] = useState<string>(
		''
	);
	const [importedObjectDefinitions, setImportedObjectDefinitions] = useState<
		ObjectDefinition[]
	>();
	const [{fileName, inputFile}, setFile] = useState<TFile>({});
	const [importFormData, setImportFormData] = useState<FormData>();
	const importModalComponentId = `${portletNamespace}importModal`;
	const [modalImportKeyState, setModalImportKeyState] = useState(
		modalImportKey
	);
	const [name, setName] = useState('');
	const [visible, setVisible] = useState(showModal ?? false);
	const [warningModalVisible, setWarningModalVisible] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			setError(undefined);
			setExternalReferenceCode('');
			setFile({
				fileName: '',
				inputFile: null,
			});
			setName('');
			setWarningModalVisible(false);

			if (handleOnClose) {
				handleOnClose();
			}

			if (
				error &&
				error?.message !== '' &&
				!error?.type?.includes('ObjectFolderNameException') &&
				modalImportKey === 'objectFolder'
			) {
				window.location.reload();
			}
		},
	});

	const handleImport = async (item: FormData | ObjectDefinition[]) => {
		try {
			await API.save({
				item,
				method: 'POST',
				url: importURL,
			});

			if (onAfterImport) {
				onAfterImport();

				onClose();
			}
			else {
				window.location.reload();
			}
		}
		catch (error) {
			setError(error as API.ErrorDetails);
		}
	};

	const handleDefaultImport = async (event: FormEvent<HTMLFormElement>) => {
		const formData = new FormData(event.currentTarget);
		const formDataObject: FormDataJSONFormat = {};

		formData.forEach((value, key) => {
			if (key.includes(JSONInputId)) {
				formDataObject[key] = inputFile as File;

				return;
			}

			formDataObject[key] = value;

			return;
		});

		if (importExtendedInfo) {
			formDataObject[importExtendedInfo.key] = importExtendedInfo.value;
		}

		const newFormData = jsonToFormData(formDataObject);

		const response = await fetch(`${apiURL}${externalReferenceCode}`);

		if (response.status === 204 || response.status === 404) {
			handleImport(newFormData);
		}
		else {
			setImportFormData(newFormData);
			setWarningModalVisible(true);
		}
	};

	const handleImportMultiplesObjectDefinitions = async (
		importedObjectDefinitions: ObjectDefinition[]
	) => {
		const {items} = await API.getAllObjectDefinitions();

		const objectDefinitionsMap = new Map<string, ObjectDefinition>(
			items.map((objectDefinition) => [
				objectDefinition.externalReferenceCode,
				objectDefinition,
			])
		);

		const newAlreadyImportedObjectDefinitions: ObjectDefinition[] = [];

		importedObjectDefinitions.forEach((objectDefinition) => {
			if (
				objectDefinitionsMap.has(objectDefinition.externalReferenceCode)
			) {
				newAlreadyImportedObjectDefinitions.push(
					objectDefinitionsMap.get(
						objectDefinition.externalReferenceCode
					) as ObjectDefinition
				);
			}
		});

		if (newAlreadyImportedObjectDefinitions.length) {
			setAlreadyImportedObjectDefinitions(
				newAlreadyImportedObjectDefinitions
			);

			setModalImportKeyState('objectDefinitions');

			setWarningModalVisible(true);

			return;
		}

		handleImport(importedObjectDefinitions);
	};

	const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		if (
			Liferay.FeatureFlags['LPS-187142'] &&
			importedObjectDefinitions
		) {
			handleImportMultiplesObjectDefinitions(importedObjectDefinitions);

			return;
		}

		handleDefaultImport(event);
	};

	useEffect(() => {
		Liferay.component(
			importModalComponentId,
			{
				open: () => {
					setVisible(true);
				},
			},
			{
				destroyOnNavigate: true,
			}
		);

		return () => Liferay.destroyComponent(importModalComponentId);
	}, [importModalComponentId, setVisible]);

	return visible ? (
		<ClayModal
			center
			observer={observer}
			status={warningModalVisible ? 'warning' : undefined}
		>
			{warningModalVisible ? (
				<ModalImportWarning
					alreadyImportedObjectDefinitions={
						alreadyImportedObjectDefinitions
					}
					errorMessage={error?.message ?? ''}
					handleImport={() =>
						handleImport(importFormData as FormData)
					}
					handleOnClose={onClose}
					modalImportKey={modalImportKeyState}
				/>
			) : (
				<ModalImportContent
					JSONInputId={JSONInputId}
					apiURL={apiURL}
					error={error}
					externalReferenceCode={externalReferenceCode}
					fileName={fileName as string}
					handleOnClose={onClose}
					handleSubmit={handleSubmit}
					importURL={importURL}
					importedObjectDefinitions={importedObjectDefinitions}
					inputFile={inputFile as File}
					modalImportKey={modalImportKeyState}
					name={name}
					nameMaxLength={nameMaxLength}
					portletNamespace={portletNamespace}
					setError={setError}
					setExternalReferenceCode={setExternalReferenceCode}
					setFile={setFile}
					setImportedObjectDefinitions={setImportedObjectDefinitions}
					setName={setName}
				/>
			)}
		</ClayModal>
	) : null;
}
