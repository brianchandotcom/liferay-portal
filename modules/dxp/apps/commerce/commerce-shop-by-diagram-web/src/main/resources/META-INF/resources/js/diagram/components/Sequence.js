/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import classNames from 'classnames';
import React, {useMemo} from 'react';

export default function Sequence({containerRef, highlighted, target}) {
	const style = useMemo(() => {
		const {
			height,
			width,
			x: sequenceX,
			y: sequenceY,
		} = target.getBoundingClientRect();

		const {
			x: parentX,
			y: parentY,
		} = containerRef.current.getBoundingClientRect();

		const relativeX = sequenceX - parentX;
		const relativeY = sequenceY - parentY;

		const backgroundSize = Math.max(height, width) + 4;

		return {
			'--border-width': `${backgroundSize / 10}px`,
			fontSize: `${height}px`,
			height: `${backgroundSize}px`,
			left: `${relativeX}px`,
			top: `${relativeY}px`,
			width: `${backgroundSize}px`,
		};
	}, [containerRef, target]);

	return (
		<span
			className={classNames('pin-foreground', {
				highlighted,
				mapped: target._mapped,
			})}
			style={style}
		>
			{target.textContent}
		</span>
	);
}
