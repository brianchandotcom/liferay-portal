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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const MIN_HEIGHT = 100;

export default function PageStructureSidebarSection({
	children,
	resizable = false,
	size = 1,
}) {
	const [handlerElement, setHandlerElement] = useState(null);
	const [panelElement, setPanelElement] = useState(null);
	const [panelHeight, setPanelHeight] = useState();
	const [resizing, setResizing] = useState(false);

	useEffect(() => {
		if (!handlerElement || !panelElement) {
			return;
		}

		let initialHeight = 0;
		let initialY = 0;
		let maxHeight = 0;

		const handleResizeStart = (event) => {
			initialHeight = panelElement.getBoundingClientRect().height;
			initialY = event.clientY;

			maxHeight =
				initialHeight +
				(handlerElement?.getBoundingClientRect().height || 0) +
				(panelElement.previousSibling?.previousSibling?.getBoundingClientRect()
					.height || 0) -
				MIN_HEIGHT;

			document.body.addEventListener('mousemove', handleResize);
			document.body.addEventListener('mouseleave', handleResizeEnd);
			document.body.addEventListener('mouseup', handleResizeEnd);

			setResizing(true);
		};

		const handleResize = (event) => {
			const delta = event.clientY - initialY;

			setPanelHeight(
				Math.max(Math.min(maxHeight, initialHeight - delta), MIN_HEIGHT)
			);
		};

		const handleResizeEnd = () => {
			document.body.removeEventListener('mousemove', handleResize);
			document.body.removeEventListener('mouseleave', handleResizeEnd);
			document.body.removeEventListener('mouseup', handleResizeEnd);

			setResizing(false);
		};

		handlerElement.addEventListener('mousedown', handleResizeStart);

		return () => {
			handlerElement.removeEventListener('mousedown', handleResizeStart);
			handleResizeEnd();
		};
	}, [handlerElement, panelElement]);

	return (
		<>
			{resizable && (
				<div
					className={classNames(
						'page-editor__page-structure__section__resize-handler',
						{
							active: resizing,
						}
					)}
					ref={setHandlerElement}
				/>
			)}

			<div
				className={classNames('page-editor__page-structure__section', {
					resized: !!panelHeight,
				})}
				ref={setPanelElement}
				style={{flexGrow: panelHeight ? 0 : size, height: panelHeight}}
			>
				{children}
			</div>
		</>
	);
}

PageStructureSidebarSection.propTypes = {
	resizable: PropTypes.bool,
	size: PropTypes.number,
};
