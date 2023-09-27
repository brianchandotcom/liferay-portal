/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/empty-line-between-elements */

import ClayIcon from '@clayui/icon';
import React from 'react';

interface Props {
	message: string;
}

export function ErrorMessage({message}: Props) {
	return (
		<div
			className="alert alert-danger alert-dismissible alert-fluid c-mb-1"
			role="alert"
		>
			<div className="c-px-4 c-py-3">
				<span className="alert-indicator">
					<ClayIcon symbol="exclamation-full" />
				</span>
				<strong className="lead">
					{Liferay.Language.get('error')}
				</strong>{' '}
				<span
					className="d-inline-block"
					dangerouslySetInnerHTML={{
						__html: message,
					}}
				/>
			</div>
		</div>
	);
}
