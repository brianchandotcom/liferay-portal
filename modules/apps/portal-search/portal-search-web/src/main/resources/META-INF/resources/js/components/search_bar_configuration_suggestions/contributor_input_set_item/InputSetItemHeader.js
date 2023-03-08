/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React from 'react';

function InputSetItemHeader({children}) {
	return (
		<div className="contributor-input-set-item-header-root">{children}</div>
	);
}

function InputSetItemHeaderTitle({children}) {
	return <h3 className="contributor-name sheet-subtitle">{children}</h3>;
}

function InputSetItemHeaderDescription({children}) {
	return <div className="contributor-description sheet-text">{children}</div>;
}

InputSetItemHeader.Title = InputSetItemHeaderTitle;
InputSetItemHeader.Description = InputSetItemHeaderDescription;

export default InputSetItemHeader;
