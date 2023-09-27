/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import AccountMenuContent from './AccountMenuContent';
import OrganizationMenuContent from './OrganizationMenuContent';
import UserMenuContent from './UserMenuContent';

const CONTENT = {
	account: AccountMenuContent,
	organization: OrganizationMenuContent,
	user: UserMenuContent,
};

export default function MenuProvider({
	alignElementRef,
	data: propData,
	parentData,
}) {
	const [active, setActive] = useState(false);
	const [data, setData] = useState(false);
	const menuRef = useRef(null);

	// The useEffect below force the component repositioning

	useEffect(() => {
		setActive(false);
		setData(propData);
	}, [propData]);

	useEffect(() => {
		if (data) {
			setActive(true);
		}
	}, [data]);

	const MenuContent = useMemo(() => data && CONTENT[data.type], [data]);

	const closeMenu = () => {
		setData(null);
		setActive(false);
	};

	return (
		<ClayDropDown.Menu
			active={active}
			alignElementRef={alignElementRef}
			onSetActive={closeMenu}
			ref={menuRef}
		>
			{MenuContent && (
				<MenuContent
					closeMenu={closeMenu}
					data={data}
					parentData={parentData}
				/>
			)}
		</ClayDropDown.Menu>
	);
}
