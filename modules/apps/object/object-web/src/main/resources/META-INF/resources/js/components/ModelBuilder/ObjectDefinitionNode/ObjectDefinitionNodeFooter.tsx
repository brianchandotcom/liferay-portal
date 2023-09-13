/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {sub} from 'frontend-js-web';
import React from 'react';

import './ObjectDefinitionNodeFooter.scss';

interface ObjectDefinitionNodeFooterProps {
	isLinkedObjectDefinition: boolean;
	setShowAllObjectFields: (value: boolean) => void;
	showAllObjectFields: boolean;
}

export default function ObjectDefinitionNodeFooter({
	isLinkedObjectDefinition,
	setShowAllObjectFields,
	showAllObjectFields,
}: ObjectDefinitionNodeFooterProps) {
	return (
		<>
			<div className="lfr-objects__model-builder-node-button-container">
				{!isLinkedObjectDefinition && (
					<DropDown
						alignmentPosition={4}
						trigger={
							<ClayButton displayType="secondary">
								<span>
									{sub(
										Liferay.Language.get('x-or-x'),
										Liferay.Language.get('add-field'),
										Liferay.Language.get('relationship')
									)}
								</span>
							</ClayButton>
						}
					>
						<DropDown.ItemList>
							<DropDown.Item>
								<ClayIcon
									className="c-mr-3 text-4"
									symbol="custom-field"
								/>

								{Liferay.Language.get('add-field')}
							</DropDown.Item>

							<DropDown.Item>
								<ClayIcon
									className="c-mr-3 text-4"
									symbol="nodes"
								/>

								{sub(
									Liferay.Language.get('add-x'),
									Liferay.Language.get('relationship')
								)}
							</DropDown.Item>
						</DropDown.ItemList>
					</DropDown>
				)}
			</div>

			<div className="lfr-objects__model-builder-node-show-all-fields-container">
				<ClayButton
					className="lfr-objects__model-builder-node-show-all-fields-button"
					displayType="unstyled"
					onClick={() => {
						setShowAllObjectFields(!showAllObjectFields);
					}}
				>
					{showAllObjectFields
						? sub(
								Liferay.Language.get('hide-x'),
								Liferay.Language.get('fields')
						  )
						: sub(
								Liferay.Language.get('show-all-x'),
								Liferay.Language.get('fields')
						  )}

					<ClayIcon
						className="c-pt-1 text-4"
						symbol={
							showAllObjectFields
								? 'angle-up-small'
								: 'angle-down-small'
						}
					/>
				</ClayButton>
			</div>
		</>
	);
}
