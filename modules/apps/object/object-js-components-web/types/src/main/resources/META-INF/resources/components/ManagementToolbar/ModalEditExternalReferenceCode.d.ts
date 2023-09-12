/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Entity} from './index';
interface ModalEditExternalReferenceCodeProps {
	externalReferenceCode: string;
	handleOnClose: () => void;
	helpMessage: string;
	onExternalReferenceCodeChange?: (value: string) => void;
	onGetEntity: () => Promise<Entity>;
	saveURL: string;
	setExternalReferenceCode: (value: string) => void;
}
export declare function ModalEditExternalReferenceCode({
	externalReferenceCode,
	handleOnClose,
	helpMessage,
	onExternalReferenceCodeChange,
	onGetEntity,
	saveURL,
	setExternalReferenceCode,
}: ModalEditExternalReferenceCodeProps): JSX.Element;
export {};
