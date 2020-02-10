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

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useState} from 'react';

import {ConfigContext} from '../config/index';
import {useDispatch} from '../store/index';
import updateItemConfig from '../thunks/updateItemConfig';
import AllowedFragmentSelector from './AllowedFragmentSelector';

const ManageAllowedFragmentModal = ({item, observer, onClose}) => {
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();

	const [allowNewFragmentEntries, setAllowNewFragmentEntries] = useState(
		true
	);
	const [selectedFragments, setSelectedFragments] = useState(new Set([]));
	const [loading, setLoading] = useState();

	const handleSaveClick = () => {
		setLoading(true);

		dispatch(
			updateItemConfig({
				config,
				itemConfig: {
					allowNewFragmentEntries,
					fragmentEntryKeys: [...selectedFragments]
				},
				itemId: item.itemId,
				segmentsExperienceId: 0
			})
		).then(() => {
			setLoading(false);
			onClose();
		});
	};

	const onSelectedFragment = useCallback(
		({allowNewFragmentEntries, selectedFragments}) => {
			setAllowNewFragmentEntries(allowNewFragmentEntries);
			setSelectedFragments(selectedFragments);
		},
		[]
	);

	return (
		<ClayModal
			className="fragments-editor__allowed-fragment__modal"
			observer={observer}
			size="md"
		>
			<ClayModal.Header>
				{Liferay.Language.get('allowed-fragments')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="m-4 small text-secondary">
					{Liferay.Language.get(
						'specify-which-fragments-a-page-author-is-allowed-to-use-within-the-drop-zone-when-creating-a-page-from-this-master'
					)}
				</p>
				<AllowedFragmentSelector
					onSelectedFragment={onSelectedFragment}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={handleSaveClick}
						>
							{loading && (
								<span className="inline-item inline-item-before">
									<span
										aria-hidden="true"
										className="loading-animation"
									></span>
								</span>
							)}
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			></ClayModal.Footer>
		</ClayModal>
	);
};

ManageAllowedFragmentModal.propTypes = {
	observer: PropTypes.object.isRequired,
	onClose: PropTypes.func.isRequired
};

export default ManageAllowedFragmentModal;
