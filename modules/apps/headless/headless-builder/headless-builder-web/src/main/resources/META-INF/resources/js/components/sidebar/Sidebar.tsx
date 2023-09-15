/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	Dispatch,
	SetStateAction,
	useContext,
	useEffect,
	useState,
} from 'react';

import {EditSchemaContext} from '../EditAPIApplicationContext';
import {fetchJSON} from '../utils/fetchUtil';
import SidebarBody from './SidebarBody';
import SidebarHeader from './SidebarHeader';

interface SidebarProps {
	mainObjectDefinitionERC: string;
	objectDefinitions?: ObjectDefinitionsRelationshipTree;
	schemaUIData: APISchemaUIData;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
}

export default function Sidebar({
	mainObjectDefinitionERC,
	objectDefinitions,
	schemaUIData,
	setSchemaUIData,
}: SidebarProps) {
	const {objectDefinitionBasePath, setFetchedSchemaData} = useContext(
		EditSchemaContext
	);

	const [navHistory, setNavHistory] = useState<AddedObjectDefinition[][]>([
		[],
	]);
	const [onBackClick, setOnBackClick] = useState(() => () => {});
	const [searchKeyword, setSearchKeyword] = useState('');
	const [viewRelatedObjects, setViewRelatedObjects] = useState(false);

	useEffect(() => {
		fetchJSON<ObjectDefinition>({
			input: objectDefinitionBasePath + mainObjectDefinitionERC,
		}).then((mainObjectResult) => {
			setFetchedSchemaData((previous) => ({
				...previous,
				objectDefinitions: {
					...previous.objectDefinitions,
					definition: mainObjectResult,
				},
			}));
			setNavHistory([[mainObjectResult]]);
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="sidebar">
			{objectDefinitions?.definition && (
				<>
					<SidebarHeader
						navHistory={navHistory}
						onBackClick={onBackClick}
						setNavHistory={setNavHistory}
						setSearchKeyword={setSearchKeyword}
						setViewRelatedObjects={setViewRelatedObjects}
						viewRelatedObjects={viewRelatedObjects}
					/>

					<SidebarBody
						fectchedObjectDefinitions={objectDefinitions}
						navHistory={navHistory}
						schemaUIData={schemaUIData}
						searchKeyword={searchKeyword}
						setNavHistory={setNavHistory}
						setOnBackClick={setOnBackClick}
						setSchemaUIData={setSchemaUIData}
						viewRelatedObjects={viewRelatedObjects}
					/>
				</>
			)}
		</div>
	);
}
