/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
