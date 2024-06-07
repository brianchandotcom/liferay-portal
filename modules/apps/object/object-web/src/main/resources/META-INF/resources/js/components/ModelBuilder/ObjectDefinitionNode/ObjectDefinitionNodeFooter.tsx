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
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

interface ObjectDefinitionNodeFooterProps {
	externalReferenceCode: string;
	handleSelectObjectDefinitionNode: () => void;
	isLinkedObjectDefinition: boolean;
	showAllObjectFields: boolean;
}

export default function ObjectDefinitionNodeFooter({
	externalReferenceCode,
	handleSelectObjectDefinitionNode,
	isLinkedObjectDefinition,
	showAllObjectFields,
}: ObjectDefinitionNodeFooterProps) {
	const [_, dispatch] = useObjectFolderContext();

	return (
		<>
			<div className="lfr-objects__model-builder-node-button-container">
				{!isLinkedObjectDefinition && (
					<DropDown
						alignmentPosition={4}
						trigger={
							<ClayButton
								aria-labelledby={sub(
									Liferay.Language.get('x-or-x'),
									Liferay.Language.get('add-field'),
									Liferay.Language.get('relationship')
								)}
								displayType="secondary"
								onClick={() =>
									handleSelectObjectDefinitionNode()
								}
								size="sm"
							>
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
							<DropDown.Item
								onClick={() =>
									dispatch({
										payload: {
											updatedModelBuilderModals: {
												addObjectField: true,
											},
										},
										type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
									})
								}
							>
								<ClayIcon
									className="c-mr-3 text-4"
									symbol="custom-field"
								/>

								{Liferay.Language.get('add-field')}
							</DropDown.Item>

							<DropDown.Item
								onClick={() => {
									dispatch({
										payload: {
											updatedModelBuilderModals: {
												addObjectRelationship: true,
											},
										},
										type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
									});
								}}
							>
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
					aria-labelledby={
						showAllObjectFields
							? sub(
									Liferay.Language.get('hide-x'),
									Liferay.Language.get('fields')
								)
							: sub(
									Liferay.Language.get('show-all-x'),
									Liferay.Language.get('fields')
								)
					}
					className="lfr-objects__model-builder-node-show-all-fields-button"
					displayType="unstyled"
					onClick={() => {
						dispatch({
							payload: {
								objectDefinitionExternalReferenceCode:
									externalReferenceCode,
								showAllObjectFields,
							},
							type: TYPES.SET_SHOW_ALL_OBJECT_FIELDS,
						});
					}}
					size="sm"
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
