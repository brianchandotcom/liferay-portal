/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React from 'react';

export default function Field({children, errors, id, label, name}) {
	const errorMessage = errors[name];

	return (
		<ClayForm.Group className={errorMessage ? 'has-error' : null}>
			<label htmlFor={id}>
				{label}

				<span className="reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			</label>

			{children}

			{errorMessage && (
				<div className="form-feedback-item">
					<span className="form-feedback-indicator mr-1">
						<ClayIcon symbol="exclamation-full" />
					</span>

					{errorMessage}
				</div>
			)}
		</ClayForm.Group>
	);
}
