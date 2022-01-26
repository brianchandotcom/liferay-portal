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

import ActivationKeysInputs from './Inputs';
import ActivationKeysSkeleton from './Skeleton';

const ActivationKeysLayout = ({children}) => {
	return (
		<div>
			<h1 className="m-0 py-4">Activation Keys</h1>

			{children}
		</div>
	);
};

ActivationKeysLayout.Inputs = ActivationKeysInputs;
ActivationKeysLayout.Skeleton = ActivationKeysSkeleton;

export default ActivationKeysLayout;
