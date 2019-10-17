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
import React, {useState} from 'react';

export default () => {
	const [show, setShow] = useState(true);

	return (
		show && (
			<div className="autofit-row pl-4 pr-4">
				<div className="autofit-col-expand">
					<ClayAlert
						displayType="info"
						onClose={() => setShow(!show)}
						title={`${Liferay.Language.get('info')}:`}
					>
						{Liferay.Language.get(
							'the-widget-will-be-available-under-add-widgets-app-builder'
						)}
					</ClayAlert>
				</div>
			</div>
		)
	);
};
