/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import ClayList from '@clayui/list';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import FeatureFlagToggle from './FeatureFlagToggle';

export type TFeatureFlags = {
	companyId: number;
	dependenciesFulfilled: boolean;
	dependencyKeys: Array<string>;
	description: string;
	enabled: boolean;
	key: string;
	title: string;
};

interface IFeatureFlagListProps {
	featureFlags: Array<TFeatureFlags>;
}

const FeatureFlagList: React.FC<IFeatureFlagListProps> = ({featureFlags}) => {
	const [items, setItems] = useState<TFeatureFlags[]>(featureFlags);

	const displayFeatureFlag = (items: TFeatureFlags[]) =>
		items.map(
			({
				companyId,
				dependenciesFulfilled,
				dependencyKeys,
				description,
				enabled,
				key,
				title,
			}: TFeatureFlags) => {
				const dependency = dependencyKeys.map((dep: string) => dep);

				return (
					<ClayList.Item flex key={key}>
						<ClayList.ItemField expand>
							<ClayList.ItemTitle>
								{title}{' '}

								<span className="text-muted"> ({key})</span>
							</ClayList.ItemTitle>

							<ClayList.ItemText>{description}</ClayList.ItemText>

							{!!dependencyKeys.length && (
								<ClayList.ItemText>
									{sub(
										Liferay.Language.get('dependencies-x'),
										[(dependency as unknown) as string]
									)}
								</ClayList.ItemText>
							)}
						</ClayList.ItemField>

						<ClayList.ItemField>
							<FeatureFlagToggle
								ariaDescribedBy={title}
								companyId={companyId}
								disabled={!dependenciesFulfilled}
								featureFlagKey={key}
								inputName={key}
								labelOff={Liferay.Language.get('disabled')}
								labelOn={Liferay.Language.get('enabled')}
								onItemsChange={(newItems) => {
									setItems((items) =>
										items.map((item) => {
											const newItem = newItems.find(
												(newItem) => {
													return (
														newItem.key === item.key
													);
												}
											);

											if (newItem) {
												return newItem;
											}

											return item;
										})
									);
								}}
								toggled={enabled}
							/>
						</ClayList.ItemField>
					</ClayList.Item>
				);
			}
		);

	return (
		<>
			{!items.length && (
				<ClayEmptyState
					description={Liferay.Language.get(
						'no-feature-flags-were-found'
					)}
					imgProps={{
						alt: Liferay.Language.get(
							'no-feature-flags-were-found'
						),
					}}
					imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.gif`}
					imgSrcReducedMotion={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/empty_state_reduced_motion.gif`}
				/>
			)}

			{!!items.length && <ClayList>{displayFeatureFlag(items)}</ClayList>}
		</>
	);
};

export default FeatureFlagList;
