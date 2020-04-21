/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useMemo, useState} from 'react';

import Portal from '../portal/Portal.es';
import ChildLink from '../router/ChildLink.es';

const HeaderKebab = ({kebabItems = []}) => {
	const [active, setActive] = useState(false);

	const container = useMemo(() => {
		const nav = document.querySelector(
			'.user-control-group ul.control-menu-nav'
		);

		return nav ? nav.lastElementChild : null;
	}, []);

	if (!kebabItems.length) {
		return null;
	}

	return (
		<Portal
			className="control-menu-nav-item"
			container={container}
			elementId="headerKebab"
			position="before"
		>
			<div className="control-menu-icon">
				<ClayDropDown
					active={active}
					onActiveChange={setActive}
					trigger={
						<ClayButton
							className="component-action"
							data-testid="headerKebabButton"
							displayType="unstyled"
							monospaced
						>
							<ClayIcon symbol="ellipsis-v" />
						</ClayButton>
					}
				>
					{kebabItems.map((kebabItem, index) => (
						<HeaderKebab.Item {...kebabItem} key={index} />
					))}
				</ClayDropDown>
			</div>
		</Portal>
	);
};

const Item = ({action = () => {}, label, link}) => {
	const DropDownItem = link ? ChildLink : ClayButton;
	const props = link ? {to: link} : {onClick: action};

	return (
		<ClayDropDown.ItemList>
			<li>
				<DropDownItem
					className="dropdown-item"
					data-testid="headerKebabItem"
					{...props}
				>
					{label}
				</DropDownItem>
			</li>
		</ClayDropDown.ItemList>
	);
};

HeaderKebab.Item = Item;

export default HeaderKebab;
