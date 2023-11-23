/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Entity} from './index';
interface ModalEditObjectDefinitionExternalReferenceCodeProps {
	handleOnClose: () => void;
	helpMessage: string;
	objectDefinitionExternalReferenceCode: string;
	onGetEntity: () => Promise<Entity>;
	onObjectDefinitionExternalReferenceCodeChange?: (value: string) => void;
	saveURL: string;
	setObjectDefinitionExternalReferenceCode?: (value: string) => void;
}
export declare function ModalEditObjectDefinitionExternalReferenceCode({
	handleOnClose,
	helpMessage,
	objectDefinitionExternalReferenceCode,
	onGetEntity,
	onObjectDefinitionExternalReferenceCodeChange,
	saveURL,
	setObjectDefinitionExternalReferenceCode,
}: ModalEditObjectDefinitionExternalReferenceCodeProps): JSX.Element;
export {};
