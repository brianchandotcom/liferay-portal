/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

import {useDispatch, useSelector} from '../../../../app/contexts/StoreContext';
import selectWidgetFragmentEntryLinks from '../../../../app/selectors/selectWidgetFragmentEntryLinks';
import updateSetsOrder from '../../../../app/thunks/updateSetsOrder';
import {TABS_IDS} from '../../config/constants/tabsIds';
import {Tabs} from './Tabs';

export function ReorderSetsModal({onCloseModal}) {
	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	const dispatch = useDispatch();

	const widgetFragmentEntryLinks = useSelector(
		selectWidgetFragmentEntryLinks
	);

	const [lists, setLists] = useState({
		[TABS_IDS.fragments]: null,
		[TABS_IDS.widgets]: null,
	});

	const updateLists = useCallback(
		(listId, newItems) =>
			setLists({...lists, [listId]: newItems.map(({id}) => id)}),
		[lists, setLists]
	);

	const onSaveButtonClick = () => {
		const orderedFragments = lists[TABS_IDS.fragments];
		const orderedWidgets = lists[TABS_IDS.widgets];

		if (!orderedFragments && !orderedWidgets) {
			return;
		}

		dispatch(
			updateSetsOrder({
				fragments: orderedFragments,
				widgetFragmentEntryLinks,
				widgets: orderedWidgets,
			})
		);

		onClose();
	};

	return (
		<ClayModal
			className="page-editor__reorder-set-modal"
			containerProps={{className: 'cadmin'}}
			observer={observer}
		>
			<ClayModal.Header>
				{Liferay.Language.get('reorder-sets')}
			</ClayModal.Header>

			<ClayModal.Body className="p-0">
				<p className="m-0 p-4 text-secondary">
					{Liferay.Language.get(
						'fragments-and-widgets-sets-can-be-ordered-to-give-you-easy-access-to-the-ones-you-use-the-most'
					)}
				</p>

				<Tabs updateLists={updateLists} />
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={onSaveButtonClick}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

ReorderSetsModal.propTypes = {
	onCloseModal: PropTypes.func.isRequired,
};
