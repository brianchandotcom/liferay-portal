/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ModalDeleteObjectFieldProps {
	objectField: ObjectField;
	onAfterSubmit: () => void;
	setModalVisibility: (value: boolean) => void;
	setObjectField?: (values: ObjectField | null) => void;
}
export declare function ModalDeleteObjectField({
	objectField,
	onAfterSubmit,
	setModalVisibility,
	setObjectField,
}: ModalDeleteObjectFieldProps): JSX.Element;
export {};
