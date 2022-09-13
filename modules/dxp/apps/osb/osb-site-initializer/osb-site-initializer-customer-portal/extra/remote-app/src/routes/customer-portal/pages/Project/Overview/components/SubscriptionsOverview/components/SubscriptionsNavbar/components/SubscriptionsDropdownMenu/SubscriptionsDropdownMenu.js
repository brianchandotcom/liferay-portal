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
import {useState} from 'react';
import i18n from '../../../../../../../../../../../common/I18n';

const SubscriptionsDropdownMenu = ({
	selectedSubscriptionGroup,
	setSelectedSubscriptionGroup,
	subscriptionGroups,
}) => {
	const [active, setActive] = useState(false);

	return (
		<div className="align-items-center d-flex mt-4 pb-3">
			<h6 className="mr-2 my-auto">{i18n.translate('type')}:</h6>

			<ClayDropDown
				active={active}
				closeOnClickOutside
				menuElementAttrs={{
					className: 'subscription-group-filter',
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="font-weight-semi-bold shadow-none text-brand-primary"
						displayType="unstyled"
					>
						{selectedSubscriptionGroup}

						<ClayIcon symbol="caret-bottom" />
					</ClayButton>
				}
			>
				{subscriptionGroups.map((subscriptionGroup) => (
					<ClayDropDown.Item
						key={subscriptionGroup.name}
						onClick={(event) => {
							setSelectedSubscriptionGroup(event.target.value);
							setActive(false);
						}}
						symbolRight={
							subscriptionGroup.name === selectedSubscriptionGroup
								? 'check'
								: ''
						}
						value={subscriptionGroup.name}
					>
						{subscriptionGroup.name}
					</ClayDropDown.Item>
				))}
			</ClayDropDown>
		</div>
	);
};

export default SubscriptionsDropdownMenu;
