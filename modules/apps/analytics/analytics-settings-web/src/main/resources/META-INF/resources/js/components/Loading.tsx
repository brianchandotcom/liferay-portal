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
import React from 'react';

interface ILoadingProps extends React.HTMLAttributes<HTMLSpanElement> {
	absolute?: boolean;
	inline?: boolean;
}

const Loading: React.FC<ILoadingProps> = ({
	absolute = false,
	className,
	inline = false,
	style,
}) => {
	return (
		<span
			className={classNames(className, {
				'inline-item inline-item-before': inline,
			})}
			data-testid="loading"
			style={style}
		>
			<span
				aria-hidden="true"
				className={classNames('loading-animation', {
					'loading-absolute': absolute,
				})}
			/>
		</span>
	);
};

export default Loading;
