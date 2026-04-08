/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
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
		labelText = isTranslated
			? Liferay.Language.get('customized')
			: Liferay.Language.get('not-customized');
	}
	else {
		labelText = isDefault
			? Liferay.Language.get('default')
			: isTranslated
				? Liferay.Language.get('translated')
				: Liferay.Language.get('not-translated');
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
			{labelText}
		</ClayLabel>
	);
};

export default AvailableLocaleLabel;
