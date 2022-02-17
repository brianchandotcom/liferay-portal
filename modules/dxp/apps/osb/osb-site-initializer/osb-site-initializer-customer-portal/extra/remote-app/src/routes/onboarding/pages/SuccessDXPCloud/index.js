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

import {Button} from '../../../../common/components';
import Layout from '../../../../common/containers/setup-forms/Layout';
import {PAGE_ROUTER_TYPES} from '../../../../common/utils/constants';

const SuccessDXPCloud = ({project}) => {
	const onClickDone = () => {
		window.location.href = PAGE_ROUTER_TYPES.project(project.accountKey);
	};

	return (
		<Layout
			footerProps={{
				middleButton: (
					<Button displayType="primary" onClick={onClickDone}>
						Done
					</Button>
				),
			}}
			headerProps={{
				helper:
					'We’ll need a few details to finish building your DXP environment(s).',
				title: 'Set up DXP Cloud',
			}}
		>
			<div className="container font-weight-bold pl-6 pr-6 pt-9 text-center">
				Thank you for submitting this request! Your DXP Cloud project
				will be provisioned in 2-3 business days. At that time, DXP
				Cloud Administrators will receive several onboarding emails,
				giving them access to all the DXP Cloud environments and tools
				included in your subscription.
			</div>
		</Layout>
	);
};

export default SuccessDXPCloud;
