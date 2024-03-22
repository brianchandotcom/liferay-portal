/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';
import {FormEvent} from 'react';
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
export declare function handleDefaultImport({
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
}: HandleDefaultImportProps): Promise<void>;
export declare function handleImport({
	importURL,
	item,
	onAfterImport,
	onClose,
	setError,
}: HandleImportProps): Promise<void>;
export declare function handleImportMultiplesObjectDefinitions({
	importURL,
	importedObjectDefinitions,
	onAfterImport,
	onClose,
	setError,
	setExistingObjectDefinitions,
	setModalImportKeyState,
	setWarningModalVisible,
}: HandleImportMultiplesObjectDefinitionsProps): Promise<void>;
export {};
