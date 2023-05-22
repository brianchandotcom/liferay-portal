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

import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React from 'react';

const AvailableLocaleLabel = ({isDefault, isSubmitLabel, isTranslated}) => {
	let labelText = '';

	if (isSubmitLabel) {
		labelText = isTranslated ? 'customized' : 'not-customized';
	}
	else {
		labelText = isDefault
			? 'default'
			: isTranslated
			? 'translated'
			: 'not-translated';
	}

	return (
		<ClayLabel
			displayType={classNames({
				info: isDefault && !isSubmitLabel,
				success: isTranslated,
				warning:
					(!isDefault && !isTranslated) ||
					(!isTranslated && isSubmitLabel),
			})}
		>
			{Liferay.Language.get(labelText)}
		</ClayLabel>
	);
};

export default AvailableLocaleLabel;
