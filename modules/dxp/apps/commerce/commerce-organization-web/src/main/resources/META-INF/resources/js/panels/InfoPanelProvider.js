/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

function InfoPanelProvider(props) {
	const {children, onClose, onOpen} = props;
	const [active, setActive] = useState(false);

	const setInfoPanelActive = useCallback(
		(open) => {
			setActive(open);

			if (!open && onClose) {
				onClose();
			}
			if (open && onOpen) {
				onOpen();
			}
		},
		[onClose, onOpen]
	);

	useEffect(() => {
		setInfoPanelActive(props.active);
	}, [props.active, setInfoPanelActive]);

	return (
		<div
			className={classNames(
				'contextual-sidebar',
				'org-char-info-panel',
				'info-panel',
				'sidenav-menu-slider',
				{
					active,
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

				<div className="info-panel-content">{children}</div>
			</div>
		</div>
	);
}

InfoPanelProvider.defaultProps = {
	active: false,
	mode: 'view',
};

InfoPanelProvider.propTypes = {
	active: PropTypes.bool,
	data: PropTypes.object.isRequired,
	mode: PropTypes.oneOf(['edit', 'view']),
	onClose: PropTypes.func,
	onOpen: PropTypes.func,
	type: PropTypes.oneOf(['account', 'organization', 'user']).isRequired,
};

export default InfoPanelProvider;
