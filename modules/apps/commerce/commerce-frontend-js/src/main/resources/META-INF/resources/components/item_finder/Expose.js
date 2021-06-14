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

export default function Expose({active, children, onClose}) {
	const content = React.useRef();

	React.useEffect(() => {
		const handleEscKey = (event) => event.key === 'Escape' && onClose();

		if (active) {
			window.addEventListener('keydown', handleEscKey);
		}
		else {
			window.removeEventListener('keydown', handleEscKey);
		}
	}, [active, onClose]);

	return (
		<div className={`expose mb-4 ${active ? 'is-open' : 'is-closed'}`}>
			<div className="expose__backdrop" onClick={onClose} />
			<div className="expose__content" ref={content}>
				{children}
			</div>
		</div>
	);
}
