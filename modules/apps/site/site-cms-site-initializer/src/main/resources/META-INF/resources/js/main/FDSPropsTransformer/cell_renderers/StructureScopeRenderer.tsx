/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Badge from '@clayui/badge';
import React, {useEffect, useState} from 'react';

import SpaceService from '../../../common/services/SpaceService';
import {LogoColor} from '../../components/SpaceSticker';
import SpacesDisplay, {Space} from '../../components/SpacesDisplay';

interface ObjectDefinitionSetting {
	name: string;
	value: string;
}

const getSpaceExternalReferenceCodes = (
	objectDefinitionSettings: ObjectDefinitionSetting[]
) => {
	for (const objectDefinitionSetting of objectDefinitionSettings) {
		if (
			objectDefinitionSetting.name === 'acceptAllGroups' &&
			objectDefinitionSetting.value
		) {
			return [];
		}
		else if (
			objectDefinitionSetting.name ===
			'acceptedGroupExternalReferenceCodes'
		) {
			return objectDefinitionSetting.value.split(',');
		}
	}

	return [];
};

const StructureScopeRenderer = ({
	itemData,
}: {
	itemData: {objectDefinitionSettings: ObjectDefinitionSetting[]};
}) => {
	const [spaces, setSpaces] = useState<Space[]>([]);

	const fetchSpaces = async (spaceExternalReferenceCodes: string[]) => {
		if (spaceExternalReferenceCodes.length) {
			const spaces = await Promise.all(
				spaceExternalReferenceCodes.map(
					async (spaceExternalReferenceCode) => {
						const space = await SpaceService.getSpace({
							externalReferenceCode: spaceExternalReferenceCode,
						});

						return {
							logoColor: space.settings?.logoColor as LogoColor,
							name: space.name,
						};
					}
				)
			);

			setSpaces(spaces);
		}
	};

	const spaceExternalReferenceCodes = getSpaceExternalReferenceCodes(
		itemData.objectDefinitionSettings
	);

	useEffect(() => {
		fetchSpaces(spaceExternalReferenceCodes);
	}, [spaceExternalReferenceCodes]);

	if (spaces.length) {
		return <SpacesDisplay spaces={spaces} />;
	}

	return (
		<Badge
			className="badge-pill"
			displayType="secondary"
			label={Liferay.Language.get('all-spaces')}
		/>
	);
};

export default StructureScopeRenderer;
