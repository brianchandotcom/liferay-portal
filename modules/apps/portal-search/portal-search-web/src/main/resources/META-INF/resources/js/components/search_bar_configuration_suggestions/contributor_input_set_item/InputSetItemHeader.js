/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

function InputSetItemHeader({children}) {
	return (
		<div className="c-mb-3 contributor-input-set-item-header-root w-100">
			{children}
		</div>
	);
}

function InputSetItemHeaderTitle({children}) {
	return (
		<h3
			className="c-mb-1 contributor-name sheet-subtitle text-secondary"
			style={{textTransform: 'none'}}
		>
			{children}
		</h3>
	);
}

function InputSetItemHeaderDescription({children}) {
	return (
		<div className="c-mb-0 contributor-description sheet-text text-3">
			{children}
		</div>
	);
}

InputSetItemHeader.Title = InputSetItemHeaderTitle;
InputSetItemHeader.Description = InputSetItemHeaderDescription;

export default InputSetItemHeader;
