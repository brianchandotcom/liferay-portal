/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import ClayPopover from '@clayui/popover';
import React, {useState} from 'react';

import Icon from '../../Icon';

function LearnInputWrapper(props) {
	const messageDetails = window.learnResources[props.field]?.['en_US'];
	const [openPopover, setOpenPopover] = useState(false);

	return (
		<>
			{props.children}
			{messageDetails && (
				<ClayPopover
					closeOnClickOutside
					onShowChange={setOpenPopover}
					show={openPopover}
					trigger={
						<button className="btn-unstyled ml-2">
							<Icon symbol="question-circle" />
						</button>
					}
				>
					<ClayLink
						href={messageDetails.url}
						rel="noopener noreferrer"
						target="_blank"
					>
						{messageDetails.message}
					</ClayLink>
				</ClayPopover>
			)}
		</>
	);
}

function learnSwaggerUIPlugin() {
	return {
		wrapComponents: {
			JsonSchema_string: (Original) => (props) => {
				return (
					<LearnInputWrapper field={props.description}>
						<Original {...props} />
					</LearnInputWrapper>
				);
			},
		},
	};
}

export default learnSwaggerUIPlugin;
