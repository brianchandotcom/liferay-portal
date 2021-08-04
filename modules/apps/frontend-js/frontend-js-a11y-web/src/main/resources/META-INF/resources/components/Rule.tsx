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

import './Rule.scss';

import ClayBadge from '@clayui/badge';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import React from 'react';

import type {ImpactValue} from 'axe-core';

interface IRule extends React.ButtonHTMLAttributes<HTMLButtonElement> {
	quantity?: number;
	ruleSubtext?: ImpactValue;
	ruleText?: React.ReactNode;
	ruleTitle?: React.ReactNode;
}

function Rule({
	quantity,
	ruleSubtext,
	ruleText,
	ruleTitle,
	...otherProps
}: IRule) {
	return (
		<button
			className="list-group-item list-group-item-action list-group-item-flex list-group-item-flush"
			role="tab"
			{...otherProps}
		>
			<ClayList.ItemField className="a11y-panel--rule-list-item" expand>
				{ruleTitle && (
					<ClayList.ItemTitle>{ruleTitle}</ClayList.ItemTitle>
				)}
				{ruleSubtext && (
					<ClayList.ItemText className="text-capitalize" subtext>
						{ruleSubtext}
					</ClayList.ItemText>
				)}
				{ruleText && <ClayList.ItemText>{ruleText}</ClayList.ItemText>}
			</ClayList.ItemField>
			{quantity && (
				<ClayList.ItemField className="align-self-center">
					<ClayLayout.ContentSection>
						<ClayBadge displayType="info" label={quantity} />
					</ClayLayout.ContentSection>
				</ClayList.ItemField>
			)}
			<ClayList.ItemField className="align-self-center">
				<ClayLayout.ContentSection>
					<ClayIcon symbol="angle-right-small" />
				</ClayLayout.ContentSection>
			</ClayList.ItemField>
		</button>
	);
}

export default Rule;
