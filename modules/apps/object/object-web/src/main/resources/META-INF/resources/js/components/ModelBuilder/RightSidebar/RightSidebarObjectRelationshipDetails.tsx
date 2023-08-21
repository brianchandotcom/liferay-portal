/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import {Edge, Node, isEdge, isNode} from 'react-flow-renderer';

import './RightSidebarObjectRelationshipDetails.scss';

import {API, Input, SingleSelect} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';

import {firstLetterUppercase} from '../../../utils/string';
import {TDeletionType} from '../../ObjectRelationship/EditRelationship';
import {useObjectRelationshipForm} from '../../ObjectRelationship/ObjectRelationshipFormBase';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {ObjectDefinitionNodeData, ObjectRelationshipEdgeData} from '../types';

interface RightSidebarObjectRelationshipDetailsProps {
	deletionTypes: TDeletionType[];
}

export function RightSidebarObjectRelationshipDetails({
	deletionTypes,
}: RightSidebarObjectRelationshipDetailsProps) {
	const [{elements}] = useFolderContext();
	const [readOnly, setReadOnly] = useState(true);

	const selectedEdge = elements.find((element) => {
		if (isEdge(element)) {
			return (element as Edge<ObjectRelationshipEdgeData>).data
				?.edgeSelected;
		}
	}) as Edge<ObjectRelationshipEdgeData>;

	const {setValues, values} = useObjectRelationshipForm({
		initialValues: {
			id: 0,
			label: {},
			name: '',
		},
		onSubmit: () => {},
		parameterRequired: false,
	});

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedEdge) {
				const selectedObjectRelationshipResponse = (await API.getRelationship(
					selectedEdge.data!.objectRelationshipId
				)) as ObjectRelationship;

				setValues(selectedObjectRelationshipResponse);

				const nodeObjectDefinition1 = elements.find(
					(element) =>
						isNode(element) &&
						element.id ===
							selectedObjectRelationshipResponse.objectDefinitionId1.toString()
				);

				if (nodeObjectDefinition1) {
					const readOnly =
						!(nodeObjectDefinition1 as Node<
							ObjectDefinitionNodeData
						>).data?.hasObjectDefinitionUpdateResourcePermission ||
						selectedObjectRelationshipResponse.reverse;

					setReadOnly(readOnly);
				}
			}
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedEdge]);

	return (
		<>
			<div className="lfr-objects__model-builder-right-sidebar-relationship-title-container">
				<div className="lfr-objects__model-builder-right-sidebar-relationship-title">
					<span>
						{sub(
							Liferay.Language.get('x-details'),
							Liferay.Language.get('relationship')
						)}
					</span>
				</div>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('delete-relationship')}
					displayType="secondary"
					symbol="trash"
					title={Liferay.Language.get('delete-relationship')}
				/>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-relationship-content">
				<InputLocalized
					disableFlag={readOnly}
					disabled={readOnly}
					error=""
					label={Liferay.Language.get('label')}
					onChange={() => {}}
					required
					translations={values.label as LocalizedValue<string>}
				/>

				<Input
					disabled
					error=""
					label={Liferay.Language.get('name')}
					onChange={() => {}}
					required
					value={values.name}
				/>

				<Input
					disabled
					error=""
					label={
						values.type === 'manyToMany'
							? Liferay.Language.get('many-records-of')
							: Liferay.Language.get('one-record-of')
					}
					onChange={() => {}}
					required
					value={values.name}
				/>

				<Input
					disabled
					error=""
					label={Liferay.Language.get('many-records-of')}
					onChange={() => {}}
					required
					value={values.objectDefinitionName2}
				/>

				<SingleSelect
					disabled={readOnly}
					label={Liferay.Language.get('deletion-type')}
					onChange={() => {}}
					options={deletionTypes}
					required
					value={
						values.deletionType &&
						firstLetterUppercase(values.deletionType)
					}
				/>
			</div>
		</>
	);
}
