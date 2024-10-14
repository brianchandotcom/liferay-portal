/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {useState} from 'react';

import useOnClickOutside from './hooks/useOnClickOutside';

export default function LockErrorIndicator() {
	const [openPopover, setOpenPopover] = useState(false);

	useOnClickOutside(['.lfr-lock-error-indicator'], () =>
		setOpenPopover(false)
	);

	return (
		<ClayPopover
			alignPosition="bottom-right"
			className="lfr-lock-error-indicator"
			header={Liferay.Language.get('autosave-error')}
			onShowChange={setOpenPopover}
			show={openPopover}
			trigger={
				<ClayIcon
					className="ml-2 mt-0"
					onClick={() => {
						setOpenPopover((open) => !open);
					}}
					small="true"
					symbol="exclamation-full"
				/>
			}
		>
			<div>
				{Liferay.Language.get(
					'there-was-an-issue-completing-the-autosave.-this-may-be-due-to-missing-mandatory-fields-or-server-problems.-please-check-and-try-again'
				)}
			</div>
		</ClayPopover>
	);
}
