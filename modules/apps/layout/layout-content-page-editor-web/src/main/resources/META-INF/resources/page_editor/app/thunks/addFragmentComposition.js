/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast, sub} from 'frontend-js-web';

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
		}).then(({fragmentComposition, url}) => {
			dispatch(
				addFragmentCompositionAction({
					fragmentCollectionId,
					fragmentComposition,
				})
			);

			openToast({
				message: sub(
					Liferay.Language.get(
						'the-fragment-was-created-successfully.-you-can-view-it-in-x'
					),
					`<a href="${url}">${Liferay.Language.get('fragments')}</a>`
				),
				type: 'success',
			});
		});
	};
}
