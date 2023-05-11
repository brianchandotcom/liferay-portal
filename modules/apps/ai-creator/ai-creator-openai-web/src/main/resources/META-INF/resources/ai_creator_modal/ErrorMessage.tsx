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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import React from 'react';

export function ErrorMessage() {
	return (
		<ClayAlert
			className="c-mb-1"
			displayType="danger"
			title={Liferay.Language.get('error')}
			variant="stripe"
		>
			<span className="c-pr-2 d-inline-block">
				{Liferay.Language.get(
					'openai-is-experiencing-issues-on-their-servers'
				)}
			</span>

			<ClayButton
				className="btn-link text-underline"
				displayType="unstyled"
				type="submit"
			>
				{Liferay.Language.get('retry-your-request')}
			</ClayButton>
		</ClayAlert>
	);
}
