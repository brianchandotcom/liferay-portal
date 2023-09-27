/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import persistActiveView from '../../thunks/persistActiveView';
import ViewsContext from '../../views/ViewsContext';

function ActiveViewSelector({views}) {
	const {appURL, id, portletId} = useContext(FrontendDataSetContext);
	const [{activeView}, viewsDispatch] = useContext(ViewsContext);

	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButtonWithIcon
					className="nav-link nav-link-monospaced"
					displayType="unstyled"
					symbol={activeView.thumbnail}
					title={Liferay.Language.get('show-view-options')}
				/>
			}
		>
			<ClayDropDown.ItemList>
				{views.map(({label, name, thumbnail}) => (
					<ClayDropDown.Item
						key={name}
						onClick={(event) => {
							event.preventDefault();
							setActive(false);
							viewsDispatch(
								persistActiveView({
									activeViewName: name,
									appURL,
									id,
									portletId,
								})
							);
						}}
					>
						<ClayIcon className="mr-3" symbol={thumbnail} />

						{label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

export default ActiveViewSelector;
