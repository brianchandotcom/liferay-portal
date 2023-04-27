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
import {ButtonWithIcon} from '@clayui/core';
import {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';

import i18n from '../../../../../../common/I18n';
import {Button, ButtonDropDown} from '../../../../../../common/components';

import {
	STATUS_TAG_TYPES,
	STATUS_TAG_TYPE_NAMES,
} from '../../../../utils/constants';
import PopoverIcon from '../../DXPCloud/components/PopoverIcon';

export default function getActivationStatusCardLayout(
	lxcEnvironment,
	project,
	onNotActivatedClick,
	onInProgressClick,
	userAccount
) {
	return {
		[STATUS_TAG_TYPE_NAMES.active]: {
			buttonLink: (
				<div className="d-flex flex-column">
					<a
						className="font-weight-semi-bold m-0 p-0 text-brand-primary text-paragraph"
						href={`https://${lxcEnvironment?.projectId}.lxc.liferay.com`}
						rel="noopener noreferrer"
						target="_blank"
					>
						<PopoverIcon
							symbol="question-circle-full"
							title="link-only-accessible-to-current-product-users-permissions-and-roles-are-managed-separately-within-each-product"
						/>

						{i18n.translate('go-to-liferay-experience-cloud')}

						<ClayIcon className="ml-1" symbol="order-arrow-right" />
					</a>

					<a
						className="font-weight-semi-bold m-0 p-0 text-brand-primary text-paragraph"
						href={`https://${lxcEnvironment?.projectId}-uat.lxc.liferay.com`}
						rel="noopener noreferrer"
						target="_blank"
					>
						<PopoverIcon
							symbol="question-circle-full"
							title="link-only-accessible-to-current-product-users-permissions-and-roles-are-managed-separately-within-each-product"
						/>

						{i18n.translate('go-to-uat')}

						<ClayIcon className="ml-1" symbol="order-arrow-right" />
					</a>

					<a
						className="font-weight-semi-bold m-0 p-0 text-brand-primary text-paragraph"
						href={`https://analytics.liferay.com/workspace/${project.acWorkspaceGroupId}/sites`}
						rel="noopener noreferrer"
						target="_blank"
					>
						<PopoverIcon
							symbol="question-circle-full"
							title="link-only-accessible-to-current-product-users-permissions-and-roles-are-managed-separately-within-each-product"
						/>

						{i18n.translate('go-to-analytics-cloud-workspace')}

						<ClayIcon className="ml-1" symbol="order-arrow-right" />
					</a>
				</div>
			),
			id: STATUS_TAG_TYPES.active,
			subtitle: i18n.translate(
				'your-experience-cloud-project-is-being-set-up-and-will-be-available-soon'
			),
			title: i18n.translate('liferay-experience-cloud-activation'),
		},
		[STATUS_TAG_TYPE_NAMES.inProgress]: {
			dropdownIcon: (userAccount.isStaff ||
				userAccount.isProvisioning) && (
				<ButtonDropDown
					align={Align.BottomRight}
					customDropDownButton={
						<ButtonWithIcon
							displayType="null"
							small
							symbol="caret-bottom"
						/>
					}
					items={[
						{
							label: i18n.translate('set-to-active'),
							onClick: () => onInProgressClick(),
						},
					]}
					menuElementAttrs={{
						className: 'p-0 cp-activation-key-icon rounded-xs',
					}}
				/>
			),
			id: STATUS_TAG_TYPES.inProgress,
			subtitle: i18n.translate(
				'your-experience-cloud-project-is-being-set-up-and-will-be-available-soon'
			),
			title: i18n.translate('liferay-experience-cloud-activation'),
		},
		[STATUS_TAG_TYPE_NAMES.notActivated]: {
			buttonLink: userAccount.isAdmin && (
				<Button
					appendIcon="order-arrow-right"
					className="btn btn-link font-weight-semi-bold p-0 text-brand-primary text-paragraph"
					displayType="link"
					onClick={() => onNotActivatedClick()}
				>
					{i18n.translate('finish-activation')}
				</Button>
			),
			id: STATUS_TAG_TYPES.notActivated,
			subtitle: i18n.translate(
				'almost-there-setup-experience-cloud-by-finishing-the-activation-form'
			),
			title: i18n.translate('liferay-experience-cloud-activation'),
		},
	};
}
