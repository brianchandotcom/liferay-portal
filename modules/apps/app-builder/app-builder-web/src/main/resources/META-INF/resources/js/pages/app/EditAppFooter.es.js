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

import React from 'react';
import Button from '../../components/button/Button.es';

export default ({children, onCancel}) => {
	return (
		<div className="card-footer bg-transparent">
			<div className="autofit-row">
				<div className="col-md-4">
					<Button displayType="secondary" onClick={onCancel}>
						{Liferay.Language.get('cancel')}
					</Button>
				</div>
				<div className="col-md-4 offset-md-4 text-right">
					{children}
				</div>
			</div>
		</div>
	);
};
