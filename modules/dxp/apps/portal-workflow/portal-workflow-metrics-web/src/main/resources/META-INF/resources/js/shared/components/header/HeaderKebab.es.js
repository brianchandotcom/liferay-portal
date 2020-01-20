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
import React, {useMemo, useState} from 'react';

import Icon from '../Icon.es';
import Portal from '../portal/Portal.es';
import {ChildLink} from '../router/routerWrapper.es';

const HeaderKebab = ({kebabItems = []}) => {
	const [active, setActive] = useState(false);

	const container = useMemo(
		() =>
			document.querySelector('.user-control-group div.control-menu-icon'),
		[]
	);

	if (!kebabItems.length) {
		return null;
	}

	return (
		<Portal container={container} replace>
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
						<Icon iconName="ellipsis-v" />
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
