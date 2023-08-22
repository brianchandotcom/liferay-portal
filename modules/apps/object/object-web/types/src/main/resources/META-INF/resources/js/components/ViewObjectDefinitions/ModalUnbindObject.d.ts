/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ModalUnbindObject.scss';
interface ModalUnbindObjectProps {
	baseResourceURL: string;
	onVisibilityChange: () => void;
	selectedObjectToUnbind?: ObjectDefinition;
}
export declare function ModalUnbindObject({
	baseResourceURL,
	onVisibilityChange,
	selectedObjectToUnbind,
}: ModalUnbindObjectProps): JSX.Element;
export {};
