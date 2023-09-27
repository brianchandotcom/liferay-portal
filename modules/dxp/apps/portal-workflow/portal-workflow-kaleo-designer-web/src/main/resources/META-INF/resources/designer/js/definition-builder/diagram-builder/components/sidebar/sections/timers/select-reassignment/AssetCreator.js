/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

const AssetCreator = ({actionSectionsIndex, setActionSections}) => {
	useEffect(() => {
		setActionSections((currentSections) => {
			const updatedSections = [...currentSections];
			const updatedReassignment = {};

			updatedReassignment.actionType = 'reassignments';
			updatedReassignment.identifier =
				updatedSections[actionSectionsIndex].identifier;
			updatedReassignment.assignmentType = 'assetCreator';
			updatedSections[actionSectionsIndex] = updatedReassignment;

			return updatedSections;
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return null;
};

export default AssetCreator;
