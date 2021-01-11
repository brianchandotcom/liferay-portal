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
import React, {useEffect, useRef} from 'react';

const InfoPanelControl = ({disabled, infoPanelId, onInfoButtonClick}) => {
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
		<ClayManagementToolbar.Item>
			<ClayButtonWithIcon
				className="nav-link nav-link-monospaced"
				disabled={disabled}
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
