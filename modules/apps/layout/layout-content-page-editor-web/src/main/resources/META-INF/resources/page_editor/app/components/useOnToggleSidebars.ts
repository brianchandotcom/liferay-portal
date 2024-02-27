/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useDispatch, useSelector} from '../contexts/StoreContext';
import switchSidebarPanel from '../thunks/switchSidebarPanel';

export default function useOnToggleSidebars() {
	const dispatch = useDispatch();
	const sidebarHidden = useSelector((state) => state.sidebar.hidden);

	return () =>
		dispatch(
			switchSidebarPanel({
				hidden: !sidebarHidden,
			})
		);
}
