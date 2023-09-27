/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import classNames from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
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
		<ManagementToolbar.Item
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
				title={Liferay.Language.get('toggle-info-panel')}
			/>
		</ManagementToolbar.Item>
	);
};

export default InfoPanelControl;
