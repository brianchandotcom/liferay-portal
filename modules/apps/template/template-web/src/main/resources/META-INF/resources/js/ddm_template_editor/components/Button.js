/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const noop = () => {};

export function Button({label, onClick = noop, tooltip}) {
	const [showPreview, setShowPreview] = useState(false);

	return (
		<ClayButton
			borderless
			className="ddm_template_editor__App-sidebar-button font-weight-semi-bold my-1 py-0 text-left text-truncate w-100"
			displayType="unstyled"
			key={label}
			onClick={onClick}
			size="sm"
		>
			{label}

			<ClayPopover
				alignPosition="left"
				disableScroll
				header={label}
				onShowChange={setShowPreview}
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
}

Button.propTypes = {
	label: PropTypes.string.isRequired,
	onClick: PropTypes.func,
	tooltip: PropTypes.string.isRequired,
};
