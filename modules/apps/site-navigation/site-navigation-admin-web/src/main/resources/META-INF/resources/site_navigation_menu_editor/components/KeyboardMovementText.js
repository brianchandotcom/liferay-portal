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

import React, {useEffect} from 'react';

export default function KeyboardMovementText({setText, text}) {
	useEffect(() => {
		const handler = setTimeout(() => {
			setText(null);
		}, 500);

		return () => clearTimeout(handler);
	}, [text, setText]);

	return text ? (
		<span
			aria-live="assertive"
			aria-relevant="additions"
			className="sr-only"
			role="log"
		>
			{text}
		</span>
	) : null;
}
