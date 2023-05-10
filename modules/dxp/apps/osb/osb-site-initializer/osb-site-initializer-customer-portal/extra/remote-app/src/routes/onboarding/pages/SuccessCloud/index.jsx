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
