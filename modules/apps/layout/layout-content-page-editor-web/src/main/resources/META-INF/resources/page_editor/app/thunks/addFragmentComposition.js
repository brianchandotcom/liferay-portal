/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import addFragmentCompositionAction from '../actions/addFragmentComposition';
import FragmentService from '../services/FragmentService';

export default function addFragmentComposition({
	description,
	fileEntryId,
	fragmentCollectionId,
	itemId,
	name,
	saveInlineContent,
	saveMappingConfiguration,
}) {
	return (dispatch, getState) => {
		return FragmentService.addFragmentComposition({
			description,
			fileEntryId,
			fragmentCollectionId,
			itemId,
			name,
			onNetworkStatus: dispatch,
			saveInlineContent,
			saveMappingConfiguration,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then(({fragmentComposition, invalidFragmentsCount, url}) => {
			if (invalidFragmentsCount > 0) {
				openToast({
					message: Liferay.Language.get(
						'the-composition-cannot-be-created-because-some-fragment-references-are-missing-reimport-the-fragments-and-try-again'
					),
					title: Liferay.Language.get('error'),
					type: 'error',
				});

				throw new Error('Invalid fragment composition');
			}
			else {
				dispatch(
					addFragmentCompositionAction({
						fragmentCollectionId,
						fragmentComposition,
						invalidFragmentsCount,
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
			}
		});
	};
}
