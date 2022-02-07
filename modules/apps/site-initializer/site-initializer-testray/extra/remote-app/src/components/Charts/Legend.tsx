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

const LegendContainer: React.FC = ({children}) => (
	<div className="legend-container">{children}</div>
);

type LegendItemProps = {
	color: string;
	label: string;
	percent: number | string;
	value: string;
};

const LegendItem: React.FC<LegendItemProps> = ({
	color,
	label,
	percent,
	value,
}) => (
	<div className="legend-item">
		<div className="legend-item-key">
			<div
				className="failed legend-color-block"
				style={{backgroundColor: color}}
			/>
		</div>

		<div className="legend-item-value">
			<div className="legend-item-numbers">
				<span className="primary">{value}</span>

				<span className="ml-2">({percent}%)</span>
			</div>

			<div className="legend-item-label">{label}</div>
		</div>
	</div>
);

export default LegendContainer;

export {LegendItem};
