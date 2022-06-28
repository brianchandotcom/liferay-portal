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

import ClayCard from '@clayui/card';

const CardTitleDescription = ({code, compressed, name}) => {
	return (
		<ClayCard.Description
			className="text-neutral-7"
			displayType="title"
			tag={compressed ? 'h4' : 'h3'}
			title={name}
		>
			{name}

			{compressed && (
				<div className="font-weight-lighter subtitle text-neutral-5 text-paragraph text-uppercase">
					{code}
				</div>
			)}
		</ClayCard.Description>
	);
};

export default CardTitleDescription;
