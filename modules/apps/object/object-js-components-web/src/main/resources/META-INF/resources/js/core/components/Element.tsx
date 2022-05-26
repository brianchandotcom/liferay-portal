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
import React, {MouseEventHandler, useState} from 'react';

export function Element({label, onClick, tooltip}: IProps) {
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
				{tooltip !== '' && (
					<ClayPopover
						alignPosition="left"
						disableScroll
						header={label}
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
						<div dangerouslySetInnerHTML={{__html: tooltip}} />
					</ClayPopover>
				)}
			</div>
		</ClayButton>
	);
}

interface IProps {
	label: string;
	onClick?: MouseEventHandler;
	tooltip: string;
}
