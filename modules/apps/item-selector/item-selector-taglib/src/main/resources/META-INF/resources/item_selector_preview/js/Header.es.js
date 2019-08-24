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
import React from "react";
import PropTypes from "prop-types";

const Header = ({handleAdd, handleClose, headerTitle}) => (
	<div className="navigation-bar navigation-bar-light">
		<ClayButton
			borderless
			displayType="secondary"
			monospaced
			onClick={handleClose}
		>
			<ClayIcon symbol="angle-left"  />
		</ClayButton>

		<strong>{headerTitle}</strong>

		<ClayButton
			borderless
			displayType="secondary"
			monospaced
		>
			<ClayIcon symbol="pencil"  />
		</ClayButton>

		<ClayButton
			borderless
			displayType="secondary"
			monospaced
		>
			<ClayIcon symbol="info-panel-open"  />
		</ClayButton>

		<ClayButton
			displayType="primary"
			onClick={handleAdd}
		>
			{Liferay.Language.get('add')}
		</ClayButton>
	</div>
);

Header.propTypes = {
	handleAdd: PropTypes.func.isRequired,
	handleClose: PropTypes.func.isRequired,
	headerTitle: PropTypes.string.isRequired
};

export default Header;
