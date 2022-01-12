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

import ClayIcon from '@clayui/icon';

const WarningBadge = ({children, ...props}) => {
	return (
		<div
			{...props}
			className="alert alert-danger ml-3 mr-3 p-sm-2 text-danger text-paragraph-sm"
		>
			<ClayIcon symbol="exclamation-full" />

			{children}
		</div>
	);
};

export default WarningBadge;
