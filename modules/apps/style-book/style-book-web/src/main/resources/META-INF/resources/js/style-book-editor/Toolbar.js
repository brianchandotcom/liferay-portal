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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {fetch, objectToFormData} from 'frontend-js-web';
import React from 'react';

import {config} from './config';

export default function Toolbar() {
	const onClick = () => {
		const body = objectToFormData({
			[`${config.namespace}styleBookEntryId`]: config.styleBookEntryId,
		});

		fetch(config.publishURL, {body, method: 'POST'});
	};

	return (
		<div className="p-3 style-book-editor__toolbar">
			<div>
				<ClayIcon
					className="mt-0 style-book-editor__status-icon"
					symbol="check-circle"
				/>
				<span className="ml-1 style-book-editor__status-text">
					Draft Saved
				</span>
			</div>
			<ClayButton displayType="primary" onClick={onClick} small>
				Publish
			</ClayButton>
		</div>
	);
}
