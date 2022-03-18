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

import ClayDropDown from '@clayui/drop-down';
import classNames from 'classnames';
import {memo, useState} from 'react';
import DrilldownMenuItems from './components/DrilldownMenuItems';

const DropDownWithDrillDown = ({
	alignmentPosition,
	className,
	containerElement,
	initialActiveMenu,
	menuElementAttrs,
	menuHeight,
	menuWidth,
	menus,
	offsetFn,
	trigger,
}) => {
	const [activeMenu, setActiveMenu] = useState(initialActiveMenu);
	const [direction, setDirection] = useState();
	const [history, setHistory] = useState([]);
	const [active, setActive] = useState(false);

	const menuIds = Object.keys(menus);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={alignmentPosition}
			className={className}
			containerElement={containerElement}
			hasRightSymbols
			menuElementAttrs={{
				...menuElementAttrs,
				className: classNames(
					menuElementAttrs?.className,
					'drilldown drop-down-menu-items p-0'
				),
			}}
			menuHeight={menuHeight}
			menuWidth={menuWidth}
			offsetFn={offsetFn}
			onActiveChange={setActive}
			trigger={trigger}
		>
			<div>
				{menuIds.map((menuKey) => {
					return (
						<DrilldownMenuItems
							active={activeMenu === menuKey}
							direction={direction}
							header={
								activeMenu === menuKey && !!history.length
									? history.slice(-1)[0].title
									: undefined
							}
							items={menus[menuKey]}
							key={menuKey}
							onBack={() => {
								const [parent] = history.slice(-1);

								setHistory(
									history.slice(0, history.length - 1)
								);

								setDirection('prev');

								setActiveMenu(parent.id);
							}}
							onForward={(title, childId) => {
								setHistory([
									...history,
									{id: activeMenu, title},
								]);

								setDirection('next');

								setActiveMenu(childId);
							}}
						/>
					);
				})}
			</div>
		</ClayDropDown>
	);
};

export default memo(DropDownWithDrillDown);
