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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayPopover from '@clayui/popover';
import React, {ReactNode, useState} from 'react';

export function GlobalCETOrderHelpIcon({buttonId, children, title}: IProps) {
	const [show, setShow] = useState(false);

	return (
		<div className="align-items-center d-flex">
			<div>{Liferay.Language.get('order')}</div>

			<ClayPopover
				alignPosition="top"
				closeOnClickOutside
				header={title}
				onShowChange={setShow}
				show={show}
				trigger={
					<ClayButtonWithIcon
						aria-expanded={show}
						aria-haspopup="true"
						aria-label={Liferay.Language.get('show-help')}
						className="h-auto mb-1 ml-1 text-secondary w-auto"
						displayType="unstyled"
						id={buttonId}
						small
						symbol="info-circle"
					/>
				}
			>
				{children}
			</ClayPopover>
		</div>
	);
}

interface IProps {
	buttonId: string;
	children: ReactNode;
	title: ReactNode;
}
