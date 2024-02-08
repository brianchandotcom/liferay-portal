/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {INFO_PANEL_MODE_MAP, INFO_PANEL_OPEN_EVENT} from '../utils/constants';
import GenericInfoPanel from './GenericInfoPanel';

function InfoPanelProvider({namespace, pathImage, selectLogoURL, spritemap}) {
	const [active, setActive] = useState(false);
	const [panelData, setPanelData] = useState({});

	const setInfoPanelActive = useCallback((active) => {
		if (!active) {
			setPanelData({});
		}

		setActive(active);
	}, []);

	const updatePanelView = useCallback(
		({data, mode, type}) => {
			if (mode === INFO_PANEL_MODE_MAP.click) {
				if (!active) {
					return;
				}
				mode = INFO_PANEL_MODE_MAP.view;
			}

			setPanelData({
				data,
				mode,
				type,
			});
			setInfoPanelActive(true);
		},
		[active, setInfoPanelActive]
	);

	useEffect(() => {
		Liferay.on(`${namespace}${INFO_PANEL_OPEN_EVENT}`, updatePanelView);

		return () => {
			Liferay.detach(
				`${namespace}${INFO_PANEL_OPEN_EVENT}`,
				updatePanelView
			);
		};
	}, [namespace, updatePanelView]);

	return (
		<div
			className={classNames(
				'contextual-sidebar',
				'org-char-info-panel',
				'info-panel',
				'sidenav-menu-slider',
				{
					'contextual-sidebar-visible': active,
				}
			)}
		>
			<div className="sidebar sidebar-light sidenav-menu">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('close')}
					className="btn-outline-borderless btn-outline-secondary d-flex sidenav-close"
					displayType="unstyled"
					onClick={() => {
						setInfoPanelActive(false);
					}}
					symbol="times"
				/>

				<div className="d-flex flex-column info-panel-content">
					{panelData.data && (
						<GenericInfoPanel
							closePanelViewHandler={() => {
								setInfoPanelActive(false);
							}}
							namespace={namespace}
							{...panelData}
							pathImage={pathImage}
							selectLogoURL={selectLogoURL}
							spritemap={spritemap}
							updatePanelViewHandler={updatePanelView}
						/>
					)}
				</div>
			</div>
		</div>
	);
}

InfoPanelProvider.propTypes = {
	namespace: PropTypes.string.isRequired,
	pathImage: PropTypes.string.isRequired,
	selectLogoURL: PropTypes.string.isRequired,
	spritemap: PropTypes.string.isRequired,
};

export default InfoPanelProvider;
