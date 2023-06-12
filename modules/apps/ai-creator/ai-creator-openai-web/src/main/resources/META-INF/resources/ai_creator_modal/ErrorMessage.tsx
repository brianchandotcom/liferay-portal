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

/* eslint-disable @liferay/empty-line-between-elements */

import ClayButton from '@clayui/button';
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
				<span className="d-inline-block">{message}</span>{' '}
				<ClayButton
					className="btn-link text-underline"
					displayType="unstyled"
					type="submit"
				>
					{Liferay.Language.get('retry-your-request')}
				</ClayButton>
			</div>
		</div>
	);
}
