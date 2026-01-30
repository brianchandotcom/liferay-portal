/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import validateFragmentCompositionAction from '../actions/validateFragmentComposition';
import FragmentService from '../services/FragmentService';

export default function validateFragmentComposition({
	itemId,
	saveInlineContent,
	saveMappingConfiguration,
}) {
	return (dispatch, getState) => {
		return FragmentService.validateFragmentComposition({
			itemId,
			onNetworkStatus: dispatch,
			saveInlineContent,
			saveMappingConfiguration,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then((response) => {
			dispatch(
				validateFragmentCompositionAction({
					invalidFragmentsCount: response.invalidFragmentsCount,
				})
			);

			return response;
		});
	};
}
