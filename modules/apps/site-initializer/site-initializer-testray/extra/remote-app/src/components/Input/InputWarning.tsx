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
import ClayLabel from '@clayui/label';
import React from 'react';

const InputWarning: React.FC = ({children}) => {
	return (
		<ClayLabel className="label-tonal-danger mt-1 mx-0 p-0 rounded w-100">
			<div className="align-items-center badge d-flex m-0 warning">
				<span className="inline-item inline-item-before">
					<ClayIcon
						className="c-ml-2 c-mr-2"
						symbol="exclamation-full"
					/>
				</span>

				<span className="font-weight-normal text-paragraph">
					{children}
				</span>
			</div>
		</ClayLabel>
	);
};

export default InputWarning;
