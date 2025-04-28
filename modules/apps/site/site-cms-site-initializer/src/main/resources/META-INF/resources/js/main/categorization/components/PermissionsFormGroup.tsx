/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import React from 'react';

import CategorizationPermissionsTable from './CategorizationPermissionsTable';

function PermissionsFormGroup({onChange, ...props}: {onChange: Function}) {
	return (
		<ClayForm.Group
			className="c-gap-4 d-flex flex-column p-4"
			data-testid="categorization-permissions-form-group"
		>
			<div className="d-flex">
				<div className="autofit-col autofit-col-expand form-title">
					{Liferay.Language.get('permissions')}
				</div>
			</div>

			<CategorizationPermissionsTable onChange={onChange} {...props} />
		</ClayForm.Group>
	);
}

export default PermissionsFormGroup;
