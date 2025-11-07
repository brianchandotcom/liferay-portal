/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

const RegenerateButton: React.FC<{onClick: () => void}> = ({onClick}) => {
	return (
		<div className="d-flex justify-content-end mb-2">
			<ClayButton displayType="secondary" onClick={onClick}>
				<ClayIcon className="mr-2" symbol="reload" />

				{Liferay.Language.get('regenerate')}
			</ClayButton>
		</div>
	);
};

export default RegenerateButton;
