/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';
import {EdgeText, getEdgeCenter, getSmoothStepPath} from 'react-flow-renderer';

import MarkerEndDefinition, {markerEndId} from './MarkerEndDefinition';

export default function Edge({
	id,
	sourceX,
	sourceY,
	targetX,
	targetY,
	sourcePosition,
	targetPosition,
	source,
	style = {},
	data: {eventObserver, text},
}) {
	const [labelVisible, setLabelVisible] = useState(false);

	useEffect(() => {
		const subscription = eventObserver.subscribe(source, (visible) =>
			setLabelVisible(visible)
		);

		return () => eventObserver.unsubscribe(subscription);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const edgePath = getSmoothStepPath({
		sourcePosition,
		sourceX,
		sourceY,
		targetPosition,
		targetX,
		targetY,
	});

	const [edgeCenterX, edgeCenterY] = getEdgeCenter({
		sourceX,
		sourceY,
		targetX,
		targetY,
	});

	let labelPositionX = edgeCenterX;
	let labelPositionY = edgeCenterY;

	if (
		edgePath.indexOf(`${edgeCenterX}`) === -1 &&
		edgePath.indexOf(`${edgeCenterY}`) === -1
	) {
		const substring1 = edgePath.substring(0, edgePath.lastIndexOf(','));

		const substring2 = substring1.substring(0, substring1.lastIndexOf(','));

		const substring3 = substring2.substring(
			substring2.lastIndexOf(',') + 1
		);

		const index = substring3.indexOf(' ');

		const y = substring3.substring(0, index);

		const x = substring3.substring(index + 1);

		labelPositionX = parseFloat(x);
		labelPositionY = parseFloat(y);
	}

	let edgeStyle = style;

	if (labelVisible) {
		edgeStyle = {...style, stroke: '#a7a9bc', strokeWidth: 2};
	}

	return (
		<>
			<MarkerEndDefinition />

			<path
				className="react-flow__edge-path"
				d={edgePath}
				id={id}
				markerEnd={`url(#${markerEndId})`}
				style={edgeStyle}
			/>

			{labelVisible && (
				<EdgeText
					label={text.toUpperCase()}
					labelBgBorderRadius="13px"
					labelBgPadding={[8, 4]}
					labelBgStyle={{
						fill: '#6b6c7e',
					}}
					labelShowBg={true}
					labelStyle={{fill: '#ffffff'}}
					x={labelPositionX}
					y={labelPositionY}
				/>
			)}
		</>
	);
}
