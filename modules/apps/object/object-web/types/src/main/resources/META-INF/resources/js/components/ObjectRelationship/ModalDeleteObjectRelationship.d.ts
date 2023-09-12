/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ModalDeleteObjectRelationshipProps {
	handleOnClose: () => void;
	objectRelationship: ObjectRelationship;
	setObjectRelationship: (value: ObjectRelationship | null) => void;
}
export declare function ModalDeleteObjectRelationship({
	handleOnClose,
	objectRelationship,
	setObjectRelationship,
}: ModalDeleteObjectRelationshipProps): JSX.Element;
export {};
