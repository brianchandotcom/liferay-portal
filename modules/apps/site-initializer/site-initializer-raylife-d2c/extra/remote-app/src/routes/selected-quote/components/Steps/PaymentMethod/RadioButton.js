/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio} from '@clayui/form';
import classNames from 'classnames';

const RadioButton = ({children, onSelected, selected = false, value}) => (
	<div
		className={classNames(
			'align-self-center align-items-center border d-flex pay-card px-3 py-2 rounded-sm user-select-auto',
			{
				'bg-brand-primary-lighten-5  border-primary rounded-sm': selected,
				'border-white': !selected,
			}
		)}
		onClick={() => {
			onSelected(value);
		}}
	>
		<ClayRadio
			checked={selected}
			id={`radio-${value}`}
			name="radio"
			type="radio"
			value={value}
		/>

		{children}
	</div>
);

export default RadioButton;
