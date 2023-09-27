/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			'--size': `${backgroundSize * 1.25}px`,
			'fontSize': `${height}px`,
			'left': `${relativeX + width / 2}px`,
			'top': `${relativeY + height / 2}px`,
		};
	}, [containerRef, target]);

	return (
		<p
			className={classNames('pin-foreground', {
				highlighted,
				mapped: target._mapped,
			})}
			style={style}
		>
			<span className="pin-foreground-label">{target.textContent}</span>
		</p>
	);
}
