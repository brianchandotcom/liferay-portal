/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SetStateAction} from 'react';
import './ObjectDefinitionNodeFooter.scss';
interface ObjectDefinitionNodeFooterProps {
	handleSelectedNode: () => void;
	isLinkedObjectDefinition: boolean;
	setShowAllObjectFields: (value: boolean) => void;
	setShowModal: (value: SetStateAction<Partial<ModelBuilderModals>>) => void;
	showAllObjectFields: boolean;
}
export default function ObjectDefinitionNodeFooter({
	handleSelectedNode,
	isLinkedObjectDefinition,
	setShowAllObjectFields,
	setShowModal,
	showAllObjectFields,
}: ObjectDefinitionNodeFooterProps): JSX.Element;
export {};
