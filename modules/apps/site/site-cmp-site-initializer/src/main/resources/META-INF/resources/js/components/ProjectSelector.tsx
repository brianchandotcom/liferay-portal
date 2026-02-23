/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FieldPicker} from '@liferay/site-cms-site-initializer';
import React from 'react';

export default function ProjectSelector({
	items,
}: {
	items: {
		label: string;
		value: string;
	}[];
}) {
	if (!items.length) {
		return null;
	}

	const defaultProject = items[0];

	return (
		<>
			<FieldPicker
				defaultSelectedKey={defaultProject.value}
				disabled
				items={items}
				label={Liferay.Language.get('projects')}
				name=""
				required
			/>

			<input
				name="r_cmpProjectToCMPTasks_c_cmpProjectId"
				type="hidden"
				value={defaultProject.value}
			/>
		</>
	);
}
