/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {navigate} from 'frontend-js-web';
import PropTypes from 'prop-types';
import {useEffect, useRef} from 'react';

import {
	useEditableProcessorClickPosition,
	useEditableProcessorUniqueId,
	useSetEditableProcessorUniqueId,
} from '../../contexts/EditableProcessorContext';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import updateEditableValues from '../../thunks/updateEditableValues';

export default function FragmentContentProcessor({
	editables,
	fragmentEntryLinkId,
}) {
	const dispatch = useDispatch();
	const editableProcessorClickPosition = useEditableProcessorClickPosition();
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const languageId = useSelector(selectLanguageId);
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
	const isMounted = useIsMounted();
	const isNavigatingRef = useRef(false);

	const editable = editables.find(
		(editable) => editableProcessorUniqueId === editable.itemId
	);

	const editableCollectionItemId = editable ? editable.itemId : '';
	const editableValues = useSelectorCallback(
		(state) =>
			state.fragmentEntryLinks[fragmentEntryLinkId] &&
			state.fragmentEntryLinks[fragmentEntryLinkId].editableValues,
		[fragmentEntryLinkId]
	);

	useEffect(() => {
		const onBeforeNavigate = async (event) => {
			if (!editable) {
				return;
			}

			if (isNavigatingRef.current) {
				isNavigatingRef.current = false;

				return;
			}

			event.originalEvent.preventDefault();

			isNavigatingRef.current = true;

			const editableValue =
				editableValues[editable.editableValueNamespace][
					editable.editableId
				];

			await editable.processor.destroyEditor(
				editable.element,
				editableValue.config,
				true
			);

			navigate(event.path);
		};

		Liferay.on('beforeNavigate', onBeforeNavigate);

		return () => {
			Liferay.detach('beforeNavigate', onBeforeNavigate);
		};
	}, [editable, editableValues]);

	useEffect(() => {
		if (
			!editable ||
			!editableValues ||
			editableCollectionItemId !== editableProcessorUniqueId
		) {
			return;
		}

		const editableValue =
			editableValues[editable.editableValueNamespace][
				editable.editableId
			];

		editable.processor.createEditor(
			editable.element,
			(value, config = {}) => {
				const defaultValue =
					editableValue.defaultValue?.replace(/\s+/g, ' ').trim() ??
					'';
				const previousValue = editableValue[languageId];

				if (
					previousValue === value ||
					(!previousValue && value === defaultValue)
				) {
					return Promise.resolve();
				}

				const editableConfig = {
					...(editableValue.config || {}),
					...config,
				};

				return dispatch(
					updateEditableValues({
						editableValues: {
							...editableValues,
							[editable.editableValueNamespace]: {
								...editableValues[
									editable.editableValueNamespace
								],
								[editable.editableId]: {
									...editableValue,
									config: editableConfig,
									[languageId]: value,
								},
							},
						},
						fragmentEntryLinkId,
					})
				);
			},
			async () => {
				if (editableCollectionItemId === editableProcessorUniqueId) {
					setEditableProcessorUniqueId(null);
				}

				if (!isMounted()) {
					return;
				}

				await Promise.resolve(
					editable.processor.destroyEditor(
						editable.element,
						editableValue.config
					)
				);
			},
			editableProcessorClickPosition,
			editableValue[languageId] || editableValue.defaultValue || ''
		);
	}, [
		dispatch,
		editable,
		editableCollectionItemId,
		editableProcessorClickPosition,
		editableProcessorUniqueId,
		editableValues,
		fragmentEntryLinkId,
		isMounted,
		languageId,
		setEditableProcessorUniqueId,
	]);

	return null;
}

FragmentContentProcessor.propTypes = {
	fragmentEntryLinkId: PropTypes.string.isRequired,
};
