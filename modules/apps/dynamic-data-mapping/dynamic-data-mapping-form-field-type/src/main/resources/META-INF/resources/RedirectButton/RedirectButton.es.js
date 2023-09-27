/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
