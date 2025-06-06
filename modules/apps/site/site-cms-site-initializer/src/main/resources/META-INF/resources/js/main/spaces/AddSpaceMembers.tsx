/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export interface AddSpaceMembersProps {
	assetLibraryId: string;
}

const AddSpaceMembers = ({assetLibraryId}: AddSpaceMembersProps) => {
	return <h1>Hello World - {assetLibraryId}</h1>;
};

export default AddSpaceMembers;
