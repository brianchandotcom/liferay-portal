/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {RefObject} from 'react';
import {ModalType} from '../constants/modalTypes';
import {MappingType} from '../types/MappingTypes';
import {ValidationError} from '../types/ValidationError';
interface Props {
	displayPageName: string;
	error: ValidationError;
	formRef: RefObject<HTMLFormElement>;
	mappingTypes: MappingType[];
	namespace: string;
	onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
	type: ModalType;
}
export default function DisplayPageModalForm({
	displayPageName,
	error: initialError,
	formRef,
	mappingTypes,
	namespace,
	onSubmit,
	type,
}: Props): JSX.Element;
export {};
