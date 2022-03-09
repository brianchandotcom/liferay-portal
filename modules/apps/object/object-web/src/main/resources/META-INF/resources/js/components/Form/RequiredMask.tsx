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

const RequiredMask: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	return (
		<>
			<span className="ml-1 reference-mark text-warning">
				<ClayIcon symbol="asterisk" />
			</span>

			<span className="sr-only">{Liferay.Language.get('mandatory')}</span>
		</>
	);
};

export default RequiredMask;
