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

import ClayLabel from '@clayui/label';
import React from 'react';

interface IRequiredLabelProps extends React.HTMLAttributes<HTMLElement> {
	required?: boolean;
}

const RequiredLabel: React.FC<IRequiredLabelProps> = ({
	className,
	required = false,
}) => {
	return (
		<>
			{required ? (
				<ClayLabel className={className} displayType="warning">
					{Liferay.Language.get('mandatory')}
				</ClayLabel>
			) : (
				<ClayLabel className={className} displayType="success">
					{Liferay.Language.get('optional')}
				</ClayLabel>
			)}
		</>
	);
};

export default RequiredLabel;
