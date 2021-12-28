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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayManagementToolbar from '@clayui/management-toolbar';
import classNames from 'classnames';
import React, {useContext, useEffect, useRef} from 'react';

import FeatureFlagContext from './FeatureFlagContext';

const InfoPanelControl = ({infoPanelId, onInfoButtonClick, separator}) => {
	const {showDesignImprovements} = useContext(FeatureFlagContext);
	const infoButtonRef = useRef();

	useEffect(() => {
		if (!infoPanelId) {
			return;
		}

		const infoButton = infoButtonRef.current;

		if (infoButton) {
			Liferay.SideNavigation.initialize(infoButton, {
				container: `#${infoPanelId}`,
				position: 'right',
				type: 'relative',
				typeMobile: 'fixed',
				width: '320px',
			});
		}

		return () => {
			Liferay.SideNavigation.destroy(infoButton);
		};
	}, [infoButtonRef, infoPanelId]);

	return (
		<ClayManagementToolbar.Item
			className={
				showDesignImprovements &&
				classNames('d-none d-md-flex', {
					'management-bar-separator-left': separator,
				})
			}
		>
			<ClayButtonWithIcon
				className="nav-link nav-link-monospaced"
				displayType="unstyled"
				id={infoPanelId && `${infoPanelId}_trigger`}
				onClick={onInfoButtonClick}
				ref={infoButtonRef}
				symbol="info-circle-open"
			/>
		</ClayManagementToolbar.Item>
	);
};

export default InfoPanelControl;
