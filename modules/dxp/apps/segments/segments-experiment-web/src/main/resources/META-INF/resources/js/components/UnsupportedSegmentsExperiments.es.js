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
import FlaskIllustration from './FlaskIllustration.es';

export default function UnsupportedSegmentsExperiments() {
	return (
		<div className="p-3 d-flex flex-column align-items-center">
			<FlaskIllustration />

			<h4 className="text-center text-dark">
				{Liferay.Language.get(
					'ab-test-is-available-only-for-content-pages'
				)}
			</h4>
		</div>
	);
}
