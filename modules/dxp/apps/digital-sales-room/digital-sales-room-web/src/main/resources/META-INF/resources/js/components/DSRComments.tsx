/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {SidePanel} from '@clayui/core';
import React, {useCallback, useRef, useState} from 'react';

import DSRCommentsPanel from './DSRCommentsPanel';

function DSRComments({digitalSalesRoomId}: {digitalSalesRoomId: number}) {
	const [open, setOpen] = useState(false);

	const ref = useRef(null);

	const handleClick = useCallback(() => {
		setOpen((prevState) => !prevState);
	}, []);

	return (
		<>
			<ClayButtonWithIcon
				aria-label={Liferay.Language.get('comments')}
				displayType="link"
				onClick={handleClick}
				size="xs"
				symbol="comments"
			/>
			<SidePanel
				containerRef={ref}
				id="dsr-comments-sidepanel"
				onOpenChange={setOpen}
				open={open}
			>
				<SidePanel.Header>
					<SidePanel.Title>
						{Liferay.Language.get('room-comments')}
					</SidePanel.Title>
				</SidePanel.Header>

				<SidePanel.Body>
					{open && (
						<DSRCommentsPanel
							roomId={digitalSalesRoomId}
						></DSRCommentsPanel>
					)}
				</SidePanel.Body>
			</SidePanel>
		</>
	);
}

export default DSRComments;
