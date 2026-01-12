/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useCallback} from 'react';

function DSRComments({digitalSalesRoomId}: {digitalSalesRoomId: number}) {
	const handleClick = useCallback(() => {
		console.error(`digitalSalesRoomId: ${digitalSalesRoomId}`);
	}, [digitalSalesRoomId]);

	return (
		<ClayButtonWithIcon
			aria-label={Liferay.Language.get('comments')}
			displayType="link"
			onClick={handleClick}
			size="xs"
			symbol="comments"
		/>
	);
}

export default DSRComments;
