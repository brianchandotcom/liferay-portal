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

import getCN from 'classnames';
import React from 'react';

const TabItem = ({active, name, setCurrentTab, tabKey}) => {
	const className = getCN(active && 'active', 'nav-link');
	const onClick = () => {
		setCurrentTab(tabKey);
	};

	return (
		<li className="nav-item">
			<span
				aria-expanded={active}
				className={className}
				data-testid="tabItemSpan"
				onClick={onClick}
				role="tab"
			>
				{name}
			</span>
		</li>
	);
};

const Tabs = ({currentTab, setCurrentTab}) => {
	const tabs = [
		{name: Liferay.Language.get('overdue'), tabKey: 'overdue'},
		{name: Liferay.Language.get('on-time'), tabKey: 'onTime'},
		{name: Liferay.Language.get('total'), tabKey: 'total'}
	];

	return (
		<div className="border-bottom">
			<ul className="ml-3 mt-2 nav nav-underline pl-1" role="tablist">
				{tabs.map(({name, tabKey}, index) => (
					<TabItem
						active={currentTab === tabKey}
						key={index}
						name={name}
						setCurrentTab={setCurrentTab}
						tabKey={tabKey}
					/>
				))}
			</ul>
		</div>
	);
};

export default Tabs;
