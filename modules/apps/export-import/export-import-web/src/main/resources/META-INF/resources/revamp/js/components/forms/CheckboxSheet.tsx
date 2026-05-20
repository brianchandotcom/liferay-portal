/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import {FormikFieldCheckbox} from './formik';

export default function CheckboxSheet({
	description,
	label,
	name,
	title,
}: {
	description: string;
	label: string;
	name: string;
	title: string;
}) {
	return (
		<ClayLayout.Sheet className="mt-4">
			<ClayLayout.SheetHeader className="mb-1">
				<div className="mb-2 sheet-title">{title}</div>
			</ClayLayout.SheetHeader>

			<FormikFieldCheckbox
				description={description}
				label={label}
				name={name}
			/>
		</ClayLayout.Sheet>
	);
}
