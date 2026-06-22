/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import React from 'react';

import {ElementVariation} from './elementVariationsReducer';

interface Props {
	audiences: Array<{label: string; value: string}>;
	elementVariations: ElementVariation[];
	onEditElementVariation: (key: string) => void;
}

export default function ElementVariationsList({
	audiences,
	elementVariations,
	onEditElementVariation,
}: Props) {
	const groupedElementVariations = elementVariations.reduce(
		(groupedElementVariations, elementVariation) => {
			const targetElementVariations =
				groupedElementVariations[elementVariation.targetElement] ?? [];

			targetElementVariations.push(elementVariation);

			groupedElementVariations[elementVariation.targetElement] =
				targetElementVariations;

			return groupedElementVariations;
		},
		{} as Record<string, ElementVariation[]>
	);

	return (
		<>
			{Object.entries(groupedElementVariations).map(
				([targetElement, targetElementVariations]) => (
					<ClayList className="mx-3" key={targetElement}>
						{[
							<ClayList.Header key="header">
								{targetElement}
							</ClayList.Header>,
							...targetElementVariations.map(
								(elementVariation) => (
									<ClayList.Item
										flex
										key={elementVariation.key}
									>
										<ClayList.ItemField expand>
											<ClayList.ItemTitle>
												{elementVariation.name}
											</ClayList.ItemTitle>

											<ClayList.ItemText>
												{audiences.find(
													(audience) =>
														audience.value ===
														elementVariation.audienceEntryERC
												)?.label ?? ''}
											</ClayList.ItemText>

											<div>
												{elementVariation.html ? (
													<ClayLabel displayType="info">
														{Liferay.Language.get(
															'html'
														)}
													</ClayLabel>
												) : null}

												{elementVariation.js ? (
													<ClayLabel displayType="info">
														{Liferay.Language.get(
															'javascript'
														)}
													</ClayLabel>
												) : null}

												{elementVariation.hide ? (
													<ClayLabel displayType="success">
														{Liferay.Language.get(
															'hide-element'
														)}
													</ClayLabel>
												) : null}
											</div>
										</ClayList.ItemField>

										<ClayList.ItemField>
											<ClayButtonWithIcon
												aria-label={Liferay.Language.get(
													'edit'
												)}
												borderless
												displayType="secondary"
												onClick={() =>
													onEditElementVariation(
														elementVariation.key
													)
												}
												symbol="pencil"
											/>
										</ClayList.ItemField>
									</ClayList.Item>
								)
							),
						]}
					</ClayList>
				)
			)}
		</>
	);
}
