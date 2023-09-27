/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ModalAddObjectRelationship.scss';
interface ModalAddObjectRelationshipProps {
	baseResourceURL: string;
	handleOnClose: () => void;
	hasDefinedObjectDefinitionTarget?: boolean;
	objectDefinitionExternalReferenceCode1: string;
	objectDefinitionExternalReferenceCode2?: string;
	objectRelationshipParameterRequired: boolean;
	onAfterSubmit?: (objectRelationshipId: number) => void;
	reload?: boolean;
}
export declare function ModalAddObjectRelationship({
	baseResourceURL,
	handleOnClose,
	hasDefinedObjectDefinitionTarget,
	objectDefinitionExternalReferenceCode1,
	objectDefinitionExternalReferenceCode2,
	objectRelationshipParameterRequired,
	onAfterSubmit,
	reload,
}: ModalAddObjectRelationshipProps): JSX.Element;
export {};
