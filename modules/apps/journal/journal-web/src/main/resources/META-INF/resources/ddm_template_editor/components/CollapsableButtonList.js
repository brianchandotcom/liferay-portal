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

import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React from 'react';

import {ButtonList} from './ButtonList';

export const CollapsableButtonList = ({items, label, onButtonClick}) => {
	return (
		<ClayPanel
			className="ddm_template_editor__CollapsableButtonList"
			collapsable
			defaultExpanded
			displayTitle={label}
			displayType="unstyled"
			showCollapseIcon={true}
		>
			<ButtonList items={items} onButtonClick={onButtonClick} />
		</ClayPanel>
	);
};

CollapsableButtonList.propTypes = {
	items: PropTypes.arrayOf(PropTypes.object),
	label: PropTypes.string.isRequired,
	onButtonClick: PropTypes.func.isRequired,
};
