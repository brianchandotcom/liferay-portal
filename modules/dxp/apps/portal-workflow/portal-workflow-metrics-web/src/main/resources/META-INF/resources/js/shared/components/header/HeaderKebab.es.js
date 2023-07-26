/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import React, {useState} from 'react';

import Portal from '../portal/Portal.es';
import ChildLink from '../router/ChildLink.es';

const HeaderKebab = ({kebabItems = []}) => {
	const [active, setActive] = useState(false);

	const nav = document.querySelector(
		'.user-control-group ul.control-menu-nav'
	);

	const navFirstElement = nav?.firstElementChild;
	const separator = nav?.querySelector('.control-menu-nav-item-separator');

	if (!kebabItems.length) {
		return null;
	}

	return (
		<Portal
			className="control-menu-nav-item"
			container={separator ? separator : navFirstElement}
			elementId="headerKebab"
			position="before"
		>
			<ClayDropDown
				active={active}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="component-action"
						displayType="unstyled"
						monospaced
						size="sm"
					>
						<ClayIcon symbol="ellipsis-v" />
					</ClayButton>
				}
			>
				{kebabItems.map((kebabItem, index) => (
					<HeaderKebab.Item {...kebabItem} key={index} />
				))}
			</ClayDropDown>
		</Portal>
	);
};

const Item = ({action = () => {}, label, link}) => {
	const DropDownItem = link ? ChildLink : ClayButton;
	const props = link ? {to: link} : {onClick: action};

	return (
		<ClayDropDown.ItemList>
			<ClayList.ItemText>
				<DropDownItem className="dropdown-item" {...props}>
					{label}
				</DropDownItem>
			</ClayList.ItemText>
		</ClayDropDown.ItemList>
	);
};

HeaderKebab.Item = Item;

export default HeaderKebab;
