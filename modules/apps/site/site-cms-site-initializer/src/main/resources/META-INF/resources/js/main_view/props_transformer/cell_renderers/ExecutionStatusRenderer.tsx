/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import cx from 'classnames';
import React from 'react';

const ExecutionStatusRenderer = ({value}: {value: string}) => {
	return (
		<span
			className={cx(
				'label',
				value === 'completed' && 'label-success',
				value === 'failed' && 'label-danger'
			)}
		>
			<span className="label-item label-item-expand">
				{Liferay.Language.get(value)}
			</span>
		</span>
	);
};

export default ExecutionStatusRenderer;
