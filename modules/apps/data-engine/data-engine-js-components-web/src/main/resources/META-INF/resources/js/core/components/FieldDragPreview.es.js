/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useRef} from 'react';

const FieldDragPreview = ({className = 'ddm-drag dragging', containerRef}) => {
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
		<div className={className}>
			<div ref={ref} />
		</div>
	);
};

export default FieldDragPreview;
