/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {PAGE_TYPES} from '../../config/constants/pageTypes';
import {config} from '../../config/index';
import {useDispatch, useSelector} from '../../store/index';
import multipleUndo from '../../thunks/multipleUndo';
import {ACTION_TYPE_LABELS} from './actionTypeLabels';

export default function UndoHistory() {
	const dispatch = useDispatch();
	const store = useSelector((state) => state);
	const undoHistory = useSelector((state) => state.undoHistory);

	const [items, setItems] = useState([]);

	const [enableDiscard, setEnableDiscard] = useState(false);

	const {network} = store;

	useEffect(() => {
		const isConversionPage = config.pageType === PAGE_TYPES.conversion;

		setEnableDiscard(network.lastFetch || config.draft || isConversionPage);
	}, [network.lastFetch]);

	const isSelectedAction = (index) => index === 0;

	const handleDiscardDraft = (event) => {
		if (
			!confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
				)
			)
		) {
			event.preventDefault();
		}
		else {
			fetch(config.discardDraftURL, {
				method: 'POST',
			}).then(() => {
				window.location = config.discardDraftRedirectURL;
			});
		}
	};

	useEffect(() => {
		if (undoHistory) {
			setItems(
				undoHistory.map((undoHistoryItem, index) => {
					return {
						disabled: isSelectedAction(index),
						label: ACTION_TYPE_LABELS[undoHistoryItem.type],
						onClick: (event) => {
							event.preventDefault();

							dispatch(
								multipleUndo({
									numberOfActions: index,
									store,
								})
							);
						},
						symbolRight: isSelectedAction(index) ? 'check' : '',
					};
				})
			);
		}
	}, [dispatch, store, undoHistory]);

	return (
		<>
			<ClayDropDownWithItems
				alignmentPosition={Align.BottomRight}
				className="mr-3"
				footerContent={
					<ClayButton
						aria-label={Liferay.Language.get('original')}
						className="dropdown-item"
						displayType="unstyled"
						onClick={handleDiscardDraft}
					>
						{Liferay.Language.get('original')}
					</ClayButton>
				}
				items={items}
				searchable={false}
				trigger={
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('undo-history')}
						className="btn-monospaced"
						disabled={
							!enableDiscard &&
							(!undoHistory || !undoHistory.length)
						}
						displayType="secondary"
						small
						symbol="time"
						title={Liferay.Language.get('undo-history')}
					/>
				}
			/>
		</>
	);
}
