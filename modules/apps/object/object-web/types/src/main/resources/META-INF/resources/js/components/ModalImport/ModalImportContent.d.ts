/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ErrorDetails} from '@liferay/object-js-components-web/src/main/resources/META-INF/resources/utils/api';
import {FormEvent} from 'react';
import {ModalImportProperties} from '../ViewObjectDefinitions/ViewObjectDefinitions';
import {TFile} from './ModalImport';
interface ModalImportContentProps extends ModalImportProperties {
	error?: ErrorDetails;
	externalReferenceCode: string;
	fileName: string;
	handleOnClose: () => void;
	handleSubmit: (value: FormEvent<HTMLFormElement>) => void;
	inputFile: File;
	modalImportKey: string;
	name: string;
	nameMaxLength: string;
	portletNamespace: string;
	setError: (value?: ErrorDetails) => void;
	setExternalReferenceCode: (value: string) => void;
	setFile: (value: TFile) => void;
	setName: (value: string) => void;
}
export declare function ModalImportContent({
	JSONInputId,
	error,
	externalReferenceCode,
	fileName,
	handleOnClose,
	handleSubmit,
	inputFile,
	modalImportKey,
	name,
	nameMaxLength,
	portletNamespace,
	setError,
	setExternalReferenceCode,
	setFile,
	setName,
}: ModalImportContentProps): JSX.Element;
export {};
