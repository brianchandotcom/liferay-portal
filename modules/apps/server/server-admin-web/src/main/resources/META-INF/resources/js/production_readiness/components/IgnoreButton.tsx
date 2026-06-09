/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

interface Props {
	disabled?: boolean;
	ignored: boolean;
	onToggle: (next: boolean) => void;
	ruleKey: string;
}

const IgnoreButton: React.FC<Props> = ({
	disabled,
	ignored,
	onToggle,
	ruleKey,
}) => {
	const label = ignored
		? Liferay.Language.get('unignore')
		: Liferay.Language.get('ignore');

	return (
		<ClayButtonWithIcon
			aria-label={`${label}: ${ruleKey}`}
			className="ml-2"
			disabled={disabled}
			displayType="unstyled"
			onClick={() => onToggle(!ignored)}
			small
			symbol={ignored ? 'hidden' : 'view'}
			title={label}
		/>
	);
};

export default IgnoreButton;
