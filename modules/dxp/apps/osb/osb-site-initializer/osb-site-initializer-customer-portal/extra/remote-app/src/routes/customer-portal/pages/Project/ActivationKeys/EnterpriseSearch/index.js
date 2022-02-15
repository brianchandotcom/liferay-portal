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

import ActivationKeysLayout from '../../../../layouts/ActivationKeysLayout';

const EnterpriseSearch = ({accountKey, sessionId}) => {
	return (
		<ActivationKeysLayout>
			<ActivationKeysLayout.Inputs
				accountKey={accountKey}
				productKey="enterprise-search"
				productTitle="Enterprise Search"
				sessionId={sessionId}
			/>
		</ActivationKeysLayout>
	);
};

export default EnterpriseSearch;
