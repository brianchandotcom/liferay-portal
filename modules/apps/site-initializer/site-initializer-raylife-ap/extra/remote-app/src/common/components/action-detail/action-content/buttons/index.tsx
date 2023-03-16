/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';

type ClaimDetailButtonsType = {
	buttonText: string;
	linkText: string;
};

const ActionButtons = ({buttonText, linkText}: ClaimDetailButtonsType) => (
	<div className="d-flex justify-content-between p-3">
		<ClayButton displayType="link">{linkText}</ClayButton>

		<div className="d-flex justify-content-end px-2">
			<ClayButton className="text-uppercase" displayType="primary">
				{buttonText}
			</ClayButton>
		</div>
	</div>
);

export default ActionButtons;
