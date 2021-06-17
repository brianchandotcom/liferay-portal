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

import './RedirectButton.scss';

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const RedirectButton = ({
	buttonLabel,
	message,
	redirectURL,
	spritemap,
	title,
	...otherProps
}) => {
	return (
		<FieldBase {...otherProps}>
			<div className="redirect-button text-center">
				{title && <label>{title}</label>}
				{message && (
					<div
						className="sheet-text"
						dangerouslySetInnerHTML={{
							__html: message,
						}}
					/>
				)}
				<ClayButton
					onClick={() => {
						window.open(redirectURL);
					}}
				>
					{buttonLabel}
					<span className="inline-item inline-item-after">
						<ClayIcon spritemap={spritemap} symbol="shortcut" />
					</span>
				</ClayButton>
			</div>
		</FieldBase>
	);
};

export default RedirectButton;
