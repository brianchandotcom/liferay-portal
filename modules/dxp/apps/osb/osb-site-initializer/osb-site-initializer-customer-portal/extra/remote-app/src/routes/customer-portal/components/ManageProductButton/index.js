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

const ManageProductButton = ({activatedLink, activatedTitle}) => (
	<a href={activatedLink} rel="noopener noreferrer" target="_blank">
		<Button
			appendIcon="shortcut"
			className="align-items-stretch btn cp-manager-product-button d-flex mr-3 p-2 text-neutral-10"
			displayType="secudary"
		>
			<p className="font-weight-semi-bold h6 m-0 pl-1">
				{activatedTitle}
			</p>
		</Button>
	</a>
);

export default ManageProductButton;
