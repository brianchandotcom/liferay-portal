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

const Skeleton = ({align, count = 1, height, width, ...props}) => {
	return (
		<div {...props}>
			{[...new Array(count)].map((_, index) => (
				<div
					className={classNames(
						'rounded skeleton',
						{
							'ml-auto': align === 'right',
							'mr-auto': align === 'left',
							'mx-auto': align === 'center',
						},
						{
							'mt-3': index > 0,
						}
					)}
					key={index}
					style={{
						height: `${height}px`,
						width: `${width - index * 100}px`,
					}}
				/>
			))}
		</div>
	);
};

Skeleton.Rounded = ({height, width}) => {
	return (
		<div
			className="rounded-sm skeleton"
			style={{height: `${height}px`, width: `${width}px`}}
		/>
	);
};

Skeleton.Square = ({height, width}) => (
	<div
		className="skeleton"
		style={{height: `${height}px`, width: `${width}px`}}
	/>
);

export default Skeleton;
