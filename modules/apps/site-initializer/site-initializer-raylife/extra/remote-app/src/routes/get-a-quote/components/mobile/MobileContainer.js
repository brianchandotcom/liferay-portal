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

const MobileContainer = ({
	activeMobileSubSection = {},
	children,
	isMobile,
	mobileSubSection = {},
}) => {
	const {hideInputLabel, title} = mobileSubSection;
	const visible = title === activeMobileSubSection.title;

	if (!isMobile) {
		return children;
	}

	if (!visible) {
		return null;
	}

	return (
		<div className="flex flex-column">
			<h3 className="mx-auto text-dark">{mobileSubSection.title}</h3>

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
