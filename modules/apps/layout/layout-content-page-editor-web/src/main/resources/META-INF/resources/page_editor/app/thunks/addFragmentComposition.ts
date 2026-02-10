/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import {State} from '../../types/State';
import addFragmentCompositionAction from '../actions/addFragmentComposition';
import updateNetwork from '../actions/updateNetwork';
import FragmentService from '../services/FragmentService';

type Props = {
	description?: string;
	fileEntryId?: number | string;
	fragmentCollectionId: string;
	itemId: string;
	name: string;
	saveInlineContent?: boolean;
	saveMappingConfiguration?: boolean;
};

export default function addFragmentComposition({
	description,
	fileEntryId,
	fragmentCollectionId,
	itemId,
	name,
	saveInlineContent,
	saveMappingConfiguration,
}: Props) {
	return (
		dispatch: (
			action: ReturnType<
				typeof updateNetwork | typeof addFragmentCompositionAction
			>
		) => void,
		getState: () => State
	) => {
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
		}).then(({fragmentComposition, url, valid}) => {
			if (!valid || !fragmentComposition) {
				openToast({
					message: Liferay.Language.get(
						'the-composition-cannot-be-created-because-some-fragment-references-are-missing-reimport-the-fragments-and-try-again'
					),
					title: Liferay.Language.get('error'),
					type: 'danger',
				});

				return;
			}

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
