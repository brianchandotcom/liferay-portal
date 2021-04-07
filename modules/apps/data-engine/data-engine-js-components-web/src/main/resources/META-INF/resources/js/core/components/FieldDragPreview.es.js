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

import React, {useEffect, useRef} from 'react';

const FieldDragPreview = ({containerRef}) => {
	const ref = useRef(null);

	/**
	 * This hack was needed to capture the field snapshot.
	 * Currently the Field is loaded lazily and the preview
	 * will look like a loading state field.
	 */
	useEffect(() => {

		/**
		 * It copies the width of the field and clone the DOM element
		 * to replace the ref inner FieldDragPreview
		 */
		const {width} = getComputedStyle(containerRef.current);
		const element = containerRef.current.cloneNode(true);
		const fieldDragPreviewRef = ref.current;

		fieldDragPreviewRef.parentElement.style.width = width;

		fieldDragPreviewRef.appendChild(element);

		return () => {
			fieldDragPreviewRef.remove();
		};
	}, [containerRef, ref]);

	return (
		<div className="ddm-drag dragging">
			<div ref={ref} />
		</div>
	);
};

export default FieldDragPreview;
