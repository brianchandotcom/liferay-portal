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

import {
	useMovementText,
	useSetMovementText,
} from '../contexts/KeyboardMovementContext';

export default function KeyboardMovementText() {
	const text = useMovementText();
	const setText = useSetMovementText();

	useEffect(() => {
		const timeout = setTimeout(() => setText(''), 1000);

		return () => {
			clearTimeout(timeout);
		};
	}, [text, setText]);

	return text ? (
		<span aria-live="assertive" className="sr-only">
			{text}
		</span>
	) : null;
}
