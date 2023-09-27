/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
