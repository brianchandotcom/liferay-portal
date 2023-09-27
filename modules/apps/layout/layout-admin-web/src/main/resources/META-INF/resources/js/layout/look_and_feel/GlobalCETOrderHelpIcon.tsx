/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {ReactNode, useState} from 'react';

export function GlobalCETOrderHelpIcon({buttonId, children, title}: IProps) {
	const [show, setShow] = useState(false);

	return (
		<div className="align-items-center d-flex">
			<div>{Liferay.Language.get('order[ranking]')}</div>

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
