/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React from 'react';

interface AvailableLocaleLabelProps {
	isDefault?: boolean;
	isSubmitLabel: boolean;
	isTranslated?: boolean;
}
type DisplayType =
	| 'danger'
	| 'info'
	| 'secondary'
	| 'success'
	| 'unstyled'
	| 'warning';

const AvailableLocaleLabel = ({
	isDefault,
	isSubmitLabel,
	isTranslated,
}: AvailableLocaleLabelProps) => {
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
			displayType={
				classNames({
					info: isDefault && !isSubmitLabel,
					success: isTranslated,
					warning:
						(!isDefault && !isTranslated) ||
						(!isTranslated && isSubmitLabel),
				}) as DisplayType
			}
		>
			{Liferay.Language.get(labelText)}
		</ClayLabel>
	);
};

export default AvailableLocaleLabel;
