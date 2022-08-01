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

type TestrayIconProps = {
	className: string;
	fill?: string;
};

const TestrayIcon: React.FC<TestrayIconProps> = ({
	className,
	fill = '#8b8db2',
}) => (
	<div className={className}>
		<svg
			height="40px"
			id="icon"
			version="1.1"
			viewBox="0 0 11.90625 15.901459"
			width="40px"
			xmlns="http://www.w3.org/2000/svg"
			xmlnsXlink="http://www.w3.org/1999/xlink"
		>
			<g id="layer1" transform="translate(-74.462037,-74.251437)">
				<g
					fillRule="nonzero"
					id="Group"
					transform="matrix(0.26458333,0,0,0.26458333,74.462037,74.251437)"
				>
					<circle
						cx="22.5"
						cy="55.700001"
						fill={fill}
						id="Oval"
						r="4.4000001"
					/>

					<polygon
						fill={fill}
						id="Shape"
						points="33.3,41.3 45,34.6 45,9 33.3,9 "
					/>

					<polygon
						fill={fill}
						id="polygon10"
						points="11.7,18 0,18 0,34.6 11.7,41.3 "
					/>

					<polygon
						fill={fill}
						id="polygon12"
						points="28.4,0 16.7,0 16.7,44.2 22.5,47.6 28.4,44.2 "
					/>
				</g>
			</g>
		</svg>
	</div>
);

export default TestrayIcon;
