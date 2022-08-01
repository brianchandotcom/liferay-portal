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

type ForwardIconProps = {
	className?: string;
	fill?: string;
};

const ForwardIcon: React.FC<ForwardIconProps> = ({
	className,
	fill = '#8b8db2',
}) => {
	return (
		<div className={className}>
			<svg
				height="20px"
				id="forwardicon"
				version="1.1"
				viewBox="0 0 30 30"
				width="20px"
				xmlns="http://www.w3.org/2000/svg"
			>
				<g id="g32" transform="translate(372.38631,445.19658)"></g>

				<g id="g34" transform="translate(372.38631,445.19658)"></g>

				<g id="g36" transform="translate(372.38631,445.19658)"></g>

				<g id="g38" transform="translate(372.38631,445.19658)"></g>

				<g id="g40" transform="translate(372.38631,445.19658)"></g>

				<g id="g42" transform="translate(372.38631,445.19658)"></g>

				<g id="g44" transform="translate(372.38631,445.19658)"></g>

				<g id="g46" transform="translate(372.38631,445.19658)"></g>

				<g id="g48" transform="translate(372.38631,445.19658)"></g>

				<g id="g50" transform="translate(372.38631,445.19658)"></g>

				<g id="g52" transform="translate(372.38631,445.19658)"></g>

				<g id="g54" transform="translate(372.38631,445.19658)"></g>

				<g id="g56" transform="translate(372.38631,445.19658)"></g>

				<g id="g58" transform="translate(372.38631,445.19658)"></g>

				<g id="g60" transform="translate(372.38631,445.19658)"></g>

				<path
					d="m 3.1226013,0.49453325 c -0.5090067,0 -1.0181408,0.19202615 -1.4024021,0.57628795 -0.76852379,0.7685227 -0.76852379,2.0362818 0,2.8048045 L 12.844798,14.999948 1.7201991,26.124548 c -0.76852367,0.768522 -0.76852367,2.036003 0,2.804527 0.7685228,0.768522 2.0362817,0.768522 2.8048044,0 L 17.051729,16.402349 c 0.768523,-0.768522 0.768523,-2.036003 0,-2.804527 L 4.5250035,1.0708212 C 4.140742,0.6865594 3.6316081,0.49453325 3.1226013,0.49453325 Z"
					fill={fill}
					id="path875"
				/>

				<path
					d="m 14.350952,0.49453325 c -0.509006,0 -1.018141,0.19202615 -1.402401,0.57628795 -0.768524,0.7685227 -0.768524,2.0362818 0,2.8048045 l 11.124597,11.1243223 -11.124597,11.1246 c -0.768524,0.768522 -0.768524,2.036003 0,2.804527 0.768522,0.768522 2.036003,0.768522 2.804527,0 L 28.279801,16.402349 c 0.768524,-0.768522 0.768524,-2.036003 0,-2.804527 L 15.753078,1.0708212 C 15.368817,0.6865594 14.859959,0.49453325 14.350952,0.49453325 Z"
					fill={fill}
					id="path881"
				/>
			</svg>
		</div>
	);
};

export default ForwardIcon;
