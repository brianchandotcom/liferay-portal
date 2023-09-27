/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {MouseEventHandler, useState} from 'react';

export function Element({helpText, label, onClick}: IProps) {
	const [showPreview, setShowPreview] = useState(false);

	return (
		<ClayButton
			borderless
			className="lfr-objects__code-editor-sidebar-element-button"
			displayType="unstyled"
			key={label}
			onClick={onClick}
			small
		>
			<div className="lfr-objects__code-editor-sidebar-element-label-container">
				<span className="lfr-objects__code-editor-sidebar-element-label">
					{label}
				</span>
			</div>

			<div className="lfr-objects__code-editor-sidebar-element-popover-container">
				{helpText !== '' && (
					<ClayPopover
						alignPosition="left"
						disableScroll
						header={label}
						onShowChange={setShowPreview}
						show={showPreview}
						trigger={
							<ClayIcon
								className="lfr-objects__code-editor-sidebar-element-preview-icon"
								onBlur={() => setShowPreview(false)}
								onFocus={() => setShowPreview(true)}
								onMouseLeave={() => setShowPreview(false)}
								onMouseOver={() => setShowPreview(true)}
								symbol="info-panel-closed"
							/>
						}
					>
						<div dangerouslySetInnerHTML={{__html: helpText}} />
					</ClayPopover>
				)}
			</div>
		</ClayButton>
	);
}

interface IProps {
	helpText: string;
	label: string;
	onClick?: MouseEventHandler;
}
