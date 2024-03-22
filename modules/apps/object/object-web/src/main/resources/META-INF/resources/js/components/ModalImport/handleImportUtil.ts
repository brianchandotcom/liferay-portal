/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import {FormEvent} from 'react';

import {FormDataJSONFormat, jsonToFormData} from '../../utils/formData';
import {ModalImportKeys} from './ModalImport';

interface HandleImportProps {
	importURL: string;
	item: FormData | ObjectDefinition[];
	onAfterImport?: () => void;
	onClose: () => void;
	setError: (
		value: React.SetStateAction<API.ErrorDetails | undefined>
	) => void;
}

interface HandleDefaultImportProps extends Omit<HandleImportProps, 'item'> {
	JSONInputId: string;
	apiURL: string;
	event: FormEvent<HTMLFormElement>;
	externalReferenceCode: string;
	importExtendedInfo: KeyValueObject;
	inputFile?: File | null;
	setImportFormData: (value: FormData) => void;
	setWarningModalVisible: (value: boolean) => void;
}

interface HandleImportMultiplesObjectDefinitionsProps
	extends Omit<HandleImportProps, 'item'> {
	importedObjectDefinitions: ObjectDefinition[];
	setExistingObjectDefinitions: (value: ObjectDefinition[]) => void;
	setModalImportKeyState: (value: ModalImportKeys) => void;
	setWarningModalVisible: (value: boolean) => void;
}

export async function handleDefaultImport({
	JSONInputId,
	apiURL,
	event,
	externalReferenceCode,
	importExtendedInfo,
	importURL,
	inputFile,
	onAfterImport,
	onClose,
	setError,
	setImportFormData,
	setWarningModalVisible,
}: HandleDefaultImportProps) {
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
		handleImport({
			importURL,
			item: newFormData,
			onAfterImport,
			onClose,
			setError,
		});
	}
	else {
		setImportFormData(newFormData);
		setWarningModalVisible(true);
	}
}

export async function handleImport({
	importURL,
	item,
	onAfterImport,
	onClose,
	setError,
}: HandleImportProps) {
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
}

export async function handleImportMultiplesObjectDefinitions({
	importURL,
	importedObjectDefinitions,
	onAfterImport,
	onClose,
	setError,
	setExistingObjectDefinitions,
	setModalImportKeyState,
	setWarningModalVisible,
}: HandleImportMultiplesObjectDefinitionsProps) {
	const {items} = await API.getAllObjectDefinitions();

	const objectDefinitionsMap = new Map<string, ObjectDefinition>(
		items.map((objectDefinition) => [
			objectDefinition.externalReferenceCode,
			objectDefinition,
		])
	);

	const existingObjectDefinitions: ObjectDefinition[] = [];

	importedObjectDefinitions.forEach((objectDefinition) => {
		if (objectDefinitionsMap.has(objectDefinition.externalReferenceCode)) {
			existingObjectDefinitions.push(
				objectDefinitionsMap.get(
					objectDefinition.externalReferenceCode
				) as ObjectDefinition
			);
		}
	});

	if (existingObjectDefinitions.length) {
		setExistingObjectDefinitions(existingObjectDefinitions);

		setModalImportKeyState('objectDefinitions');

		setWarningModalVisible(true);

		return;
	}

	handleImport({
		importURL,
		item: importedObjectDefinitions,
		onAfterImport,
		onClose,
		setError,
	});
}
