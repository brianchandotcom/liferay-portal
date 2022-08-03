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

import ClayMultiStepNav from '@clayui/multi-step-nav';

type Props = {
	className: any;
	visit: number;
};

const SiteMapCard = ({visit, ...props}: Props) => {
	const steps = [
		{
			active: visit === 0,
			complete: visit > 0,
			title: 'Goals',
		},
		{
			active: visit === 1,
			complete: visit > 1,
			title: 'Activities',
		},
		{
			active: visit === 2,
			complete: visit > 2,
			title: 'Review',
		},
	];

	return (
		<ClayMultiStepNav {...props}>
			{steps.map(({active, complete, title}, i) => (
				<ClayMultiStepNav.Item
					active={active}
					complete={complete}
					expand={i !== steps.length}
					key={i}
				>
					<ClayMultiStepNav.Title>{title}</ClayMultiStepNav.Title>
					<ClayMultiStepNav.Indicator complete={complete} />
				</ClayMultiStepNav.Item>
			))}
		</ClayMultiStepNav>
	);
};

export default SiteMapCard;
