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
import {useEffect} from 'react';
import useForceValidation from '../../hooks/useForceValidation';

const MobileContainer = ({
	activeMobileSubSection = {},
	children,
	isMobile,
	mobileSubSection = {},
	hasAddress = '',
}) => {
	const forceValidation = useForceValidation();
	const {hideInputLabel, title} = mobileSubSection;
	const visible = title === activeMobileSubSection.title;

	useEffect(() => {
		if (isMobile && visible) {
			forceValidation();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [isMobile, visible]);

	if (!isMobile) {
		return children;
	}

	if (!visible) {
		return null;
	}

	return (
		<div className="flex flex-column">
			<h2 className="mx-auto text-center text-dark">
				{`${mobileSubSection.title} ${hasAddress}`}
			</h2>

			<div
				className={classNames('mt-4', {
					'hide-input-label': hideInputLabel,
				})}
			>
				{children}
			</div>
		</div>
	);
};

export default MobileContainer;
