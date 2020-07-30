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

export default function PreviewInfoBar() {
	return (
		<div className="style-book-editor__page-preview-info-bar">
			<div className="align-items-center d-flex justify-content-center">
				{Liferay.Language.get('page-preview')}
			</div>
			<span>
				{Liferay.Language.get(
					'edit-the-style-book-using-the-sidebar-form.-you-can-preview-the-changes-instantly'
				)}
			</span>
		</div>
	);
}
