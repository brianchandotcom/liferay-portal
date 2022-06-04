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
import {DropDown} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';
import i18n from '../../../../common/I18n';
import getKebabCase from '../../../../common/utils/getKebabCase';
import {SUBSCRIPTIONS_STATUS} from '../../utils/constants';

const SubscriptionsFilterByStatus = ({selectedStatus, setSelectedStatus}) => {
	const [active, setActive] = useState(false);

	const handleChange = (status) => {
		if (status === 'All') {
			return setSelectedStatus(
				selectedStatus.length ===
					Object.keys(SUBSCRIPTIONS_STATUS).length
					? []
					: [
							SUBSCRIPTIONS_STATUS.active,
							SUBSCRIPTIONS_STATUS.expired,
							SUBSCRIPTIONS_STATUS.future,
					  ]
			);
		}

		setSelectedStatus(
			selectedStatus.includes(status)
				? selectedStatus.filter((value) => status !== value)
				: [...selectedStatus, status]
		);
	};

	return (
		<div className="d-flex mr-5 mt-4">
			<h6 className="mr-2 my-auto">{i18n.translate('status')}:</h6>

			<DropDown
				active={active}
				closeOnClickOutside
				menuElementAttrs={{
					className: 'cp-subscription-status-filter',
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="font-weight-semi-bold shadow-none text-brand-primary"
						displayType="unstyled"
					>
						{`${
							selectedStatus.length ===
							Object.keys(SUBSCRIPTIONS_STATUS).length
								? i18n.translate('all')
								: selectedStatus.length === 0
								? i18n.translate('none')
								: selectedStatus.join(', ')
						}`}{' '}
						<></>
						<ClayIcon symbol="caret-bottom" />
					</ClayButton>
				}
			>
				<DropDown.Item
					onClick={() => handleChange(i18n.translate('all'))}
					symbolRight={
						selectedStatus.length ===
						Object.keys(SUBSCRIPTIONS_STATUS).length
							? 'check'
							: ''
					}
				>
					{i18n.translate('all')}
				</DropDown.Item>

				<DropDown.Item
					onClick={() => handleChange(SUBSCRIPTIONS_STATUS.active)}
					symbolRight={
						selectedStatus.includes(SUBSCRIPTIONS_STATUS.active)
							? 'check'
							: ''
					}
				>
					{i18n.translate(getKebabCase(SUBSCRIPTIONS_STATUS.active))}
				</DropDown.Item>

				<DropDown.Item
					onClick={() => handleChange(SUBSCRIPTIONS_STATUS.expired)}
					symbolRight={
						selectedStatus.includes(SUBSCRIPTIONS_STATUS.expired)
							? 'check'
							: ''
					}
				>
					{i18n.translate(getKebabCase(SUBSCRIPTIONS_STATUS.expired))}
				</DropDown.Item>

				<DropDown.Item
					onClick={() => handleChange(SUBSCRIPTIONS_STATUS.future)}
					symbolRight={
						selectedStatus.includes(SUBSCRIPTIONS_STATUS.future)
							? 'check'
							: ''
					}
				>
					{i18n.translate(getKebabCase(SUBSCRIPTIONS_STATUS.future))}
				</DropDown.Item>
			</DropDown>
		</div>
	);
};

export default SubscriptionsFilterByStatus;
