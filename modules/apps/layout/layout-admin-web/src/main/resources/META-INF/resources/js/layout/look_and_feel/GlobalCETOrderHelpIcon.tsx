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

import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {ReactNode, useState} from 'react';

export function GlobalCETOrderHelpIcon({buttonId, children, title}: IProps) {
	const [show, setShow] = useState(false);

	return (
		<div className="align-items-center d-flex">
			<div>{Liferay.Language.get('order[ranking(n)]')}</div>

			<ClayPopover
				alignPosition="top"
				className="position-fixed"
				disableScroll
				header={title}
				id={buttonId}
				onShowChange={setShow}
				role="tooltip"
				show={show}
				trigger={
					<span
						aria-describedby={buttonId}
						className="c-ml-1 d-block"
						onBlur={() => setShow(false)}
						onFocus={() => setShow(true)}
						onMouseEnter={() => setShow(true)}
						onMouseLeave={() => setShow(false)}
						tabIndex={0}
					>
						<ClayIcon
							className="text-secondary"
							symbol="info-circle"
						/>
					</span>
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
