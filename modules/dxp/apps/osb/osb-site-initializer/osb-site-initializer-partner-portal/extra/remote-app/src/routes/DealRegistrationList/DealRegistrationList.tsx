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

import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import {Liferay} from '../../common/services/liferay';

const DealRegistrationList = () => {
	const siteURL = useLiferayNavigate();

	return (
		<div className="border-0 pb-3 pt-5 px-6 sheet">
			<h1>Partner Deal Registration</h1>

			<div className="bg-neutral-1 d-flex justify-content-end p-3 rounded">
				<ClayButton
					onClick={() =>
						Liferay.Util.navigate(
							`${siteURL}/${PRMPageRoute.CREATE_DEAL_REGISTRATION}`
						)
					}
				>
					Register New Deal
				</ClayButton>
			</div>
		</div>
	);
};
export default DealRegistrationList;
