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
import ClayPopover from '@clayui/popover';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const noop = () => {};

export const Button = ({label, onClick = noop, tooltip}) => {
	const [showPreview, setShowPreview] = useState(false);

	return (
		<ClayButton
			borderless
			className="ddm_template_editor__App-sidebar-button font-weight-semi-bold my-1 py-0 text-left text-truncate w-100"
			displayType="unstyled"
			key={label}
			onClick={onClick}
			small
		>
			{label}
			<ClayPopover
				alignPosition="left"
				disableScroll
				header={label}
				show={showPreview}
				trigger={
					<ClayIcon
						className="preview-icon"
						onBlur={() => setShowPreview(false)}
						onFocus={() => setShowPreview(true)}
						onMouseLeave={() => setShowPreview(false)}
						onMouseOver={() => setShowPreview(true)}
						symbol="info-circle-open"
						tabIndex="0"
					/>
				}
			>
				<div dangerouslySetInnerHTML={{__html: tooltip}} />
			</ClayPopover>
		</ClayButton>
	);
};

Button.propTypes = {
	label: PropTypes.string.isRequired,
	onClick: PropTypes.func,
	tooltip: PropTypes.string.isRequired,
};
