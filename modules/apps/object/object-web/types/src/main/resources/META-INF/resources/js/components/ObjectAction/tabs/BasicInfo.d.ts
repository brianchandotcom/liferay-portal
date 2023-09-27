/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormError} from '@liferay/object-js-components-web';
import React from 'react';
interface BasicInfoProps {
	errors: FormError<ObjectAction & ObjectActionParameters>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	readOnly?: boolean;
	setValues: (values: Partial<ObjectAction>) => void;
	values: Partial<ObjectAction>;
}
export default function BasicInfo({
	errors,
	handleChange,
	isApproved,
	readOnly,
	setValues,
	values,
}: BasicInfoProps): JSX.Element;
export {};
