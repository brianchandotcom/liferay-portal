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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayNavigationBar from '@clayui/navigation-bar';
import PropTypes from 'prop-types';
import React from 'react';

const Header = ({
	handleClickClose,
	handleClickDone,
	handleClickEdit,
	headerTitle
}) => (
	<div className="navbar navigation-bar navigation-bar-light">
		<div className="container-fluid header">
			<ClayNavigationBar>
				<ClayNavigationBar.Item>
					<ClayButton
						borderless
						displayType="secondary"
						monospaced
						onClick={handleClickClose}
					>
						<ClayIcon symbol="angle-left" />
					</ClayButton>
				</ClayNavigationBar.Item>

				<ClayNavigationBar.Item>
					<strong>{headerTitle} </strong>
				</ClayNavigationBar.Item>
			</ClayNavigationBar>

			<ClayNavigationBar>
				<ClayNavigationBar.Item className="btn-group-item">
					<ClayButton
						borderless
						displayType="secondary"
						monospaced
						onClick={handleClickEdit}
					>
						<ClayIcon symbol="pencil" />
					</ClayButton>
				</ClayNavigationBar.Item>

				<ClayNavigationBar.Item className="btn-group-item">
					<ClayButton borderless displayType="secondary" monospaced>
						<ClayIcon symbol="info-panel-open" />
					</ClayButton>
				</ClayNavigationBar.Item>

				<ClayNavigationBar.Item>
					<ClayButton displayType="primary" onClick={handleClickDone}>
						{Liferay.Language.get('done')}
					</ClayButton>
				</ClayNavigationBar.Item>
			</ClayNavigationBar>
		</div>
	</div>
);

Header.propTypes = {
	handleClickClose: PropTypes.func.isRequired,
	handleClickDone: PropTypes.func.isRequired,
	headerTitle: PropTypes.string.isRequired
};

export default Header;
