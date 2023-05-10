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
import {getProductDescription, getProductName} from '../../index';

const EnvironmentTypeColumn = ({activationKey}) => {
	return (
		<div>
			<p className="font-weight-bold m-0 text-neutral-10">
				{getProductName(activationKey)}
			</p>

			<p className="font-weight-normal m-0 text-neutral-7 text-paragraph-sm">
				{getProductDescription(activationKey?.complimentary)}
			</p>
		</div>
	);
};

export {EnvironmentTypeColumn};
