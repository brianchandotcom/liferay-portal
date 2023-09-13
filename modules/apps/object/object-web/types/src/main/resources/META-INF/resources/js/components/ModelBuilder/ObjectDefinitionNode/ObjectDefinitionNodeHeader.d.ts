/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ObjectDefinitionNodeHeader.scss';
import {DropDownItems} from '../types';
interface ObjectDefinitionNodeHeaderProps {
	dropDownItems: DropDownItems[];
	isLinkedObjectDefinition: boolean;
	objectDefinitionLabel: string;
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	system: boolean;
}
export default function ObjectDefinitionNodeHeader({
	dropDownItems,
	isLinkedObjectDefinition,
	objectDefinitionLabel,
	status,
	system,
}: ObjectDefinitionNodeHeaderProps): JSX.Element;
export {};
