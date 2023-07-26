/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addFragmentCompositionAction from '../actions/addFragmentComposition';
import FragmentService from '../services/FragmentService';

export default function addFragmentComposition({
	description,
	fragmentCollectionId,
	itemId,
	name,
	previewImageURL,
	saveInlineContent,
	saveMappingConfiguration,
}) {
	return (dispatch, getState) => {
		return FragmentService.addFragmentComposition({
			description,
			fragmentCollectionId,
			itemId,
			name,
			onNetworkStatus: dispatch,
			previewImageURL,
			saveInlineContent,
			saveMappingConfiguration,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then((fragmentComposition) => {
			dispatch(
				addFragmentCompositionAction({
					fragmentCollectionId,
					fragmentComposition,
				})
			);
		});
	};
}
