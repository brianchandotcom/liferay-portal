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
import PropTypes from 'prop-types';
import React from 'react';

const Header = ({
	disabledAddButton = false,
	handleClickAdd,
	handleClickBack,
	handleClickEdit,
	headerTitle,
	infoButtonRef,
	showEditIcon,
	showInfoIcon
}) => (
	<div className="navbar navigation-bar navigation-bar-light">
		<div className="container-fluid header">
			<nav className="navbar navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
				<div className="container-fluid container-fluid-max-xl">
					<ul className="navbar-nav">
						<li className="nav-item">
							<ClayButton
								borderless
								displayType="secondary"
								monospaced
								onClick={handleClickBack}
							>
								<ClayIcon symbol="angle-left" />
							</ClayButton>
						</li>
						<li className="d-none d-sm-inline-flex nav-item">
							<strong>{headerTitle}</strong>
						</li>
					</ul>
				</div>
			</nav>

			<nav className="navbar navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
				<div className="container-fluid container-fluid-max-xl">
					<ul className="navbar-nav">
						{showEditIcon && (
							<li className="btn-group-item nav-item">
								<ClayButton
									borderless
									displayType="secondary"
									monospaced
									onClick={handleClickEdit}
								>
									<ClayIcon symbol="pencil" />
								</ClayButton>
							</li>
						)}
						{showInfoIcon && (
							<li className="btn-group-item nav-item">
								<ClayButton
									borderless
									displayType="secondary"
									id="infoButtonRef"
									monospaced
									ref={infoButtonRef}
								>
									<ClayIcon symbol="info-panel-open" />
								</ClayButton>
							</li>
						)}
						<li className="nav-item">
							<ClayButton
								disabled={disabledAddButton}
								displayType="primary"
								onClick={handleClickAdd}
							>
								{Liferay.Language.get('add')}
							</ClayButton>
						</li>
					</ul>
				</div>
			</nav>
		</div>
	</div>
);

Header.propTypes = {
	disabledAddButton: PropTypes.bool,
	handleClickAdd: PropTypes.func.isRequired,
	handleClickClose: PropTypes.func.isRequired,
	handleClickEdit: PropTypes.func,
	headerTitle: PropTypes.string.isRequired,
	showEditIcon: PropTypes.bool,
	showInfoIcon: PropTypes.bool
};

export default Header;
