/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import React from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const TOKEN_GROUPS = [
	{
		label: Liferay.Language.get('label-large'),
		large: true,
		withClose: false,
	},
	{
		label: Liferay.Language.get('label-small'),
		large: false,
		withClose: false,
	},
	{
		label: Liferay.Language.get('label-large-with-close'),
		large: true,
		withClose: true,
	},
	{
		label: Liferay.Language.get('label-small-with-close'),
		large: false,
		withClose: true,
	},
	{
		label: Liferay.Language.get('label-large-with-icon'),
		large: true,
		withClose: false,
		withIcon: true,
	},
	{
		label: Liferay.Language.get('label-small-with-icon'),
		large: false,
		withClose: false,
		withIcon: true,
	},
];

const LABEL_TYPES = ['', 'inverse-', 'tonal-', 'borderless-'];

const LABEL_VARIANTS = [
	'primary',
	'secondary',
	'success',
	'info',
	'warning',
	'danger',
	'dark',
	'light',
];

const LabelGuide = () => {
	return TOKEN_GROUPS.map((item) => (
		<TokenGroup group="labels" key={item.label} title={item.label}>
			{LABEL_TYPES.map((type) => (
				<ClayLayout.Col key={type} lg={3} md={4} sm={6}>
					{LABEL_VARIANTS.map((variant) => (
						<TokenItem
							key={`${type}-${variant}`}
							label={`label-${type}${variant}`}
							size="large"
						>
							<ClayLabel
								closeButtonProps={
									item.withClose ? {id: 'closeId'} : {}
								}
								displayType={`${type}${variant}`}
								large={item.large}
								withClose={item.withClose}
							>
								{item.withIcon ? (
									<>
										<span className="label-item label-item-before">
											<ClayIcon symbol="info-circle" />
										</span>
										<span className="label-item label-item-expand">
											Label
										</span>
									</>
								) : (
									'Label'
								)}
							</ClayLabel>
						</TokenItem>
					))}
				</ClayLayout.Col>
			))}
		</TokenGroup>
	));
};

export default LabelGuide;
