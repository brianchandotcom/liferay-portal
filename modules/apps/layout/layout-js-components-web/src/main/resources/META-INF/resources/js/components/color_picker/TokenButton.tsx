/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {sub} from 'frontend-js-web';
import React, {useRef} from 'react';

type Props = {
	inherited?: boolean;
	label?: string;
	onClick: () => void;
	small?: boolean;
	triggerRef?: React.RefObject<HTMLButtonElement>;
	value?: string;
};

export default function TokenButton({
	inherited = false,
	label = '',
	onClick,
	small,
	triggerRef: externaltriggerRef,
	value = '#FFFFFF',
}: Props) {
	const internalTriggerRef = useRef<HTMLButtonElement>(null);

	const triggerRef = externaltriggerRef || internalTriggerRef;

	return (
		<ClayButton
			aria-label={sub(
				Liferay.Language.get('select-color.-color-selected-x'),
				label
			)}
			className="align-items-center border-0 d-flex font-weight-normal layout__color-picker__token-button text-body w-100"
			displayType="secondary"
			onClick={onClick}
			ref={triggerRef}
			size={small ? 'sm' : undefined}
		>
			<span
				className="layout__color-picker__token-button-splotch rounded-circle"
				style={{background: `${value}`}}
			/>

			<span className="text-truncate">{label}</span>

			{inherited ? (
				<span
					className="inherited"
					title={Liferay.Language.get('inherited-value')}
				></span>
			) : null}
		</ClayButton>
	);
}
