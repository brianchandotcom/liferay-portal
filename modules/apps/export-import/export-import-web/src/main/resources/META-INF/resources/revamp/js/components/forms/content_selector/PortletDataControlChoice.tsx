/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classnames from 'classnames';
import React from 'react';

import {PortletDataControl} from '../../../utils/mockPortletDataHandlerSections';
import FieldSelectWithOption from '../FieldSelectWithOption';

interface PortletDataControlChoiceProps {
	className?: string;
	control: NonNullable<PortletDataControl>;
	onChange: (fieldId: string, value: string) => void;
	value: string;
}

export default function PortletDataControlChoice({
	className = 'ml-1',
	control,
	onChange,
	value,
}: PortletDataControlChoiceProps) {
	return (
		<FieldSelectWithOption
			className="w-auto"
			formGroupProps={{className: classnames('mb-0', className)}}
			label={control.label}
			name={control.name}
			onChange={(event) => {
				onChange(control.name, event.target.value);
			}}
			options={control.options || []}
			value={value}
		/>
	);
}
