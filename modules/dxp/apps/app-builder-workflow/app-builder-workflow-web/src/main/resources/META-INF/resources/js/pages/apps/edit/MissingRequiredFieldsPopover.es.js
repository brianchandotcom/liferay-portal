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
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import {sub} from 'app-builder-web/js/utils/lang.es';
import React from 'react';

export default function MissingRequiredFieldsPopover({
	dataObjectName,
	message = {},
	nativeField,
	onClick,
	setShowPopover,
	showPopover,
	triggerClassName,
}) {
	const props = {
		custom: {
			headerIcon: {
				className: 'mr-2 text-info',
				symbol: 'info-circle',
			},
			icon: {
				className: 'help-cursor info tooltip-popover-icon',
				symbol: 'info-circle',
			},
			message:
				message.custom ??
				sub(
					Liferay.Language.get(
						'this-form-view-does-not-contain-all-custom-required-fields-for-the-x-object'
					),
					[dataObjectName]
				),
		},
		native: {
			headerIcon: {
				className: 'mr-2 text-danger',
				symbol: 'exclamation-full',
			},
			icon: {
				className: 'error help-cursor tooltip-popover-icon',
				symbol: 'exclamation-full',
			},
			message:
				message.native ??
				sub(
					Liferay.Language.get(
						'this-form-view-must-include-all-native-required-fields'
					),
					[dataObjectName]
				),
		},
	};

	const propsType = nativeField ? 'native' : 'custom';

	return (
		<ClayPopover
			alignPosition="left"
			disableScroll
			header={
				<>
					<ClayIcon {...props[propsType].headerIcon} />

					<span>
						{Liferay.Language.get('missing-required-fields')}
					</span>
				</>
			}
			onMouseEnter={() => setShowPopover(true)}
			onMouseLeave={() => setShowPopover(false)}
			show={showPopover}
			trigger={
				<div className={triggerClassName ?? ''}>
					<ClayIcon
						fontSize="26px"
						onMouseEnter={() => setShowPopover(true)}
						onMouseLeave={() => setShowPopover(false)}
						onMouseOver={() => setShowPopover(true)}
						{...props[propsType].icon}
					/>
				</div>
			}
		>
			<>
				{props[propsType].message}

				<ClayButton
					className="mt-3"
					displayType="secondary"
					onClick={onClick}
				>
					<span className="text-secondary">
						{Liferay.Language.get('edit-form-view')}
					</span>
				</ClayButton>
			</>
		</ClayPopover>
	);
}
