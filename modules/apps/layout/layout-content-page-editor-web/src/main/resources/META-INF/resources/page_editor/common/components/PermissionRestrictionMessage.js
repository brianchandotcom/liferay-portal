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

import ClayIcon from '@clayui/icon';
import React from 'react';

export function PermissionRestrictionMessage() {
	return (
		<div className="alert alert-secondary align-items-baseline bg-light d-flex page-editor___permissions-message">
			<span className="alert-indicator flex-shrink-0 mr-2">
				<ClayIcon symbol="password-policies" />
			</span>

			{Liferay.Language.get(
				'this-content-cannot-be-displayed-due-to-permission-restrictions'
			)}
		</div>
	);
}
