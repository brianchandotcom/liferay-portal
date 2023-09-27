/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ModalType} from '../constants/modalTypes';
import {MappingType} from '../types/MappingTypes';
interface Props {
	displayPageName: string;
	formSubmitURL: string;
	mappingTypes: MappingType[];
	namespace: string;
	onClose: () => void;
	title: string;
	type: ModalType;
	warningMessage: string;
}
export default function DisplayPageModal({
	displayPageName,
	formSubmitURL,
	mappingTypes,
	namespace,
	onClose,
	title,
	type,
	warningMessage,
}: Props): JSX.Element;
export {};
