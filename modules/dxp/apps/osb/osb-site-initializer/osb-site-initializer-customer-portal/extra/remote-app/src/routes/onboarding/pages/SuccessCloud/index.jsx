/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../common/I18n';
import {Button} from '../../../../common/components';
import Layout from '../../../../common/containers/setup-forms/Layout';
import {PRODUCT_TYPES} from '../../../customer-portal/utils/constants/productTypes';

const successTexts = {
	[PRODUCT_TYPES.analyticsCloud]: {
		helper: i18n.translate(
			'we-ll-need-a-few-details-to-finish-building-your-analytics-cloud-workspace'
		),
		paragraph: i18n.translate(
			'thank-you-for-submitting-this-request-your-analytics-cloud-workspace-will-be-provisioned-in-1-2-business-days-an-email-will-be-sent-once-your-workspace-is-ready'
		),
		title: i18n.translate('set-up-analytics-cloud'),
	},
	[PRODUCT_TYPES.dxpCloud]: {
		helper: i18n.translate(
			'we-ll-need-a-few-details-to-finish-building-your-lxc-sm-environment'
		),
		paragraph: i18n.translate(
			'thank-you-for-submitting-this-request-your-lxc-sm-project-will-be-provisioned-in-2-3-business-days-at-that-time-lxc-sm-administrators-will-receive-several-onboarding-emails-giving-them-access-to-all-the-lxc-sm-environments-and-tools-included-in-your-subscription'
		),
		title: i18n.translate('set-up-lxc-sm'),
	},
};

const SuccessCloud = ({handlePage, productType}) => {
	return (
		<Layout
			footerProps={{
				middleButton: (
					<Button displayType="primary" onClick={handlePage}>
						{i18n.translate('done')}
					</Button>
				),
			}}
			headerProps={{
				helper: successTexts[productType].helper,
				title: successTexts[productType].title,
			}}
		>
			<div className="container font-weight-bold pl-6 pr-6 pt-9 text-center">
				{successTexts[productType].paragraph}
			</div>
		</Layout>
	);
};

export default SuccessCloud;
