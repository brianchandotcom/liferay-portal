/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {API} from '@liferay/object-js-components-web';
import './ModalImportFailed.scss';
interface ModalImportFailedProps {
	error: API.ErrorDetails;
	handleOnclose: () => void;
	importedObjectDefinitions: ObjectDefinition[];
}
export declare function ModalImportFailed({
	error,
	handleOnclose,
	importedObjectDefinitions,
}: ModalImportFailedProps): JSX.Element;
export {};
