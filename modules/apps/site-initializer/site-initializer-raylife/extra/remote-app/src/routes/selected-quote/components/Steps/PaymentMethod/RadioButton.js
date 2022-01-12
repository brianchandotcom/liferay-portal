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
