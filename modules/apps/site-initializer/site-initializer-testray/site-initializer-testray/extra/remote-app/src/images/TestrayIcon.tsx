/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type TestrayIconProps = {
	className?: string;
	fill?: string;
};

const TestrayIcon: React.FC<TestrayIconProps> = ({
	className,
	fill = '#8b8db2',
}) => (
	<div className={className}>
		<svg
			height="35px"
			id="icon"
			version="1.1"
			viewBox="0 0 11.90625 15.901459"
			width="35px"
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
