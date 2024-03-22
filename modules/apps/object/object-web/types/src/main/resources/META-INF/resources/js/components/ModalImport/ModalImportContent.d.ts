/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';
import {FormEvent} from 'react';
import {ModalImportProperties} from '../ViewObjectDefinitions/ViewObjectDefinitions';
import {ModalImportKeys, TFile} from './ModalImport';
interface ModalImportContentProps extends ModalImportProperties {
	error?: API.ErrorDetails;
	externalReferenceCode: string;
	fileName: string;
	handleOnClose: () => void;
	handleSubmit: (value: FormEvent<HTMLFormElement>) => void;
	importedObjectDefinitions?: ObjectDefinition[];
	inputFile: File;
	modalImportKey: ModalImportKeys;
	name: string;
	nameMaxLength: string;
	portletNamespace: string;
	setError: (value?: API.ErrorDetails) => void;
	setExternalReferenceCode: (value: string) => void;
	setFile: (value: TFile) => void;
	setImportedObjectDefinitions: (value?: ObjectDefinition[]) => void;
	setName: (value: string) => void;
}
export declare function ModalImportContent({
	JSONInputId,
	error,
	externalReferenceCode,
	fileName,
	handleOnClose,
	handleSubmit,
	importedObjectDefinitions,
	inputFile,
	modalImportKey,
	name,
	nameMaxLength,
	portletNamespace,
	setError,
	setExternalReferenceCode,
	setFile,
	setImportedObjectDefinitions,
	setName,
}: ModalImportContentProps): JSX.Element;
export {};
