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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

export function LoadingMessage() {
	return (
		<div className="c-p-6 text-center" role="alert">
			<ClayLoadingIndicator
				className="c-mb-5"
				displayType="primary"
				shape="squares"
				size="lg"
			/>

			<p className="c-mb-0">
				{Liferay.Language.get(
					'waiting-for-openai-to-create-the-content'
				)}
			</p>

			<p>{Liferay.Language.get('this-process-may-take-a-while')}</p>
		</div>
	);
}
