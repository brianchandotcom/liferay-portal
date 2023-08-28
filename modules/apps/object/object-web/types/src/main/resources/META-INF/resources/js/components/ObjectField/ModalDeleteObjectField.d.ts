/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ModalDeleteObjectFieldProps {
	objectField: ObjectField;
	setModalVisibility: (value: boolean) => void;
	setObjectField: (values: ObjectField | null) => void;
	showDeletionNotAllowedModal: boolean;
}
export declare function ModalDeleteObjectField({
	objectField,
	setModalVisibility,
	setObjectField,
	showDeletionNotAllowedModal,
}: ModalDeleteObjectFieldProps): JSX.Element;
export {};
