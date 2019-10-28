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
	headerTitle,
	infoButtonRef
}) => (
	<div className="navbar navigation-bar navigation-bar-light">
		<div className="container-fluid header">
			<nav class="navbar navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
				<div class="container-fluid container-fluid-max-xl">
					<ul class="navbar-nav">
						<li class="nav-item">
							<ClayButton
								borderless
								displayType="secondary"
								monospaced
								onClick={handleClickClose}
							>
								<ClayIcon symbol="angle-left" />
							</ClayButton>
						</li>
						<li class="nav-item d-none d-sm-inline-flex">
							<strong>{headerTitle} </strong>
						</li>
					</ul>
				</div>
			</nav>

			<nav class="navbar  navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
				<div class="container-fluid container-fluid-max-xl">
					<ul class="navbar-nav">
						<li class="nav-item btn-group-item">
							<ClayButton
								borderless
								displayType="secondary"
								monospaced
								onClick={handleClickEdit}
							>
								<ClayIcon symbol="pencil" />
							</ClayButton>
						</li>
						<li class="nav-item btn-group-item">
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
						<li class="nav-item">
							<ClayButton
								displayType="primary"
								onClick={handleClickDone}
							>
								{Liferay.Language.get('done')}
							</ClayButton>
						</li>
					</ul>
				</div>
			</nav>
		</div>
	</div>
);

Header.propTypes = {
	handleClickClose: PropTypes.func.isRequired,
	handleClickDone: PropTypes.func.isRequired,
	headerTitle: PropTypes.string.isRequired
};

export default Header;