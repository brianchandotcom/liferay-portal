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

import {sub} from '../../utils/utils.es';

export default function EmptyPlaceholder() {
	return (
		<div className="empty-contributors mb-0 p-5 rounded taglib-empty-result-message">
			<div className="taglib-empty-result-message-header" />
			<div className="sheet-text text-center">
				<h1 className="mb-3 mt-4">
					{sub(Liferay.Language.get('no-x-yet'), [
						Liferay.Language.get('conditions')
					])}
				</h1>
				<p>{Liferay.Language.get('empty-conditions-message')}</p>
			</div>
		</div>
	);
}
