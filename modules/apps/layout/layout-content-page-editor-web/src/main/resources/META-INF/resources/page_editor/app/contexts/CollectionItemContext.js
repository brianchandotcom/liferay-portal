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

import React, {useCallback, useContext, useEffect} from 'react';

import {updateFragmentEntryLinkContent} from '../actions/index';
import FragmentService from '../services/FragmentService';
import InfoItemService from '../services/InfoItemService';
import LayoutService from '../services/LayoutService';
import isMappedToInfoItem from '../utils/editable-value/isMappedToInfoItem';
import isMappedToLayout from '../utils/editable-value/isMappedToLayout';
import isMappedToStructure from '../utils/editable-value/isMappedToStructure';
import isNullOrUndefined from '../utils/isNullOrUndefined';
import {useDisplayPagePreviewItem} from './DisplayPagePreviewItemContext';
import {useDispatch} from './StoreContext';

const defaultFromControlsId = (itemId) => itemId;
const defaultToControlsId = (controlId) => controlId;

export const INITIAL_STATE = {
	collectionConfig: null,
	collectionId: null,
	collectionItem: null,
	collectionItemIndex: null,
	customCollectionSelectorURL: null,
	fromControlsId: defaultFromControlsId,
	parentToControlsId: defaultToControlsId,
	setCollectionItemContent: () => null,
	toControlsId: defaultToControlsId,
};

const CollectionItemContext = React.createContext(INITIAL_STATE);

const CollectionItemContextProvider = CollectionItemContext.Provider;

const useCollectionItemIndex = () => {
	const context = useContext(CollectionItemContext);

	return context.collectionItemIndex;
};

const useCustomCollectionSelectorURL = () => {
	const context = useContext(CollectionItemContext);

	return context.customCollectionSelectorURL;
};

const useParentToControlsId = () => {
	const context = useContext(CollectionItemContext);

	return context.parentToControlsId;
};

const useToControlsId = () => {
	const context = useContext(CollectionItemContext);

	return context.toControlsId || defaultToControlsId;
};

const useCollectionConfig = () => {
	const context = useContext(CollectionItemContext);

	return context.collectionConfig;
};

const useGetContent = (fragmentEntryLink, languageId, segmentsExperienceId) => {
	const collectionItemContext = useContext(CollectionItemContext);
	const dispatch = useDispatch();
	const fieldSets = fragmentEntryLink.configuration?.fieldSets;
	const toControlsId = useToControlsId();

	const collectionContentId = toControlsId(
		fragmentEntryLink.fragmentEntryLinkId
	);

	const {className: collectionItemClassName, classPK: collectionItemClassPK} =
		collectionItemContext.collectionItem || {};
	const {collectionItemIndex} = collectionItemContext;

	useEffect(() => {
		const hasLocalizable =
			fieldSets?.some((fieldSet) =>
				fieldSet.fields.some((field) => field.localizable)
			) ?? false;

		if (
			(!isNullOrUndefined(collectionItemIndex) &&
				!isNullOrUndefined(collectionItemClassName) &&
				!isNullOrUndefined(collectionItemClassPK)) ||
			hasLocalizable
		) {
			FragmentService.renderFragmentEntryLinkContent({
				collectionItemClassName,
				collectionItemClassPK,
				fragmentEntryLinkId: fragmentEntryLink.fragmentEntryLinkId,
				languageId,
				onNetworkStatus: dispatch,
				segmentsExperienceId,
			}).then(({content}) => {
				dispatch(
					updateFragmentEntryLinkContent({
						collectionContentId,
						content,
						fragmentEntryLinkId:
							fragmentEntryLink.fragmentEntryLinkId,
					})
				);
			});
		}
	}, [
		collectionContentId,
		collectionItemClassName,
		collectionItemClassPK,
		collectionItemIndex,
		dispatch,
		fieldSets,
		fragmentEntryLink.editableValues,
		fragmentEntryLink.fragmentEntryLinkId,
		languageId,
		segmentsExperienceId,
	]);

	if (!isNullOrUndefined(collectionItemIndex)) {
		const collectionContent = fragmentEntryLink.collectionContent || {};

		return (
			collectionContent[collectionContentId] || fragmentEntryLink.content
		);
	}

	return fragmentEntryLink.content;
};

const useGetFieldValue = () => {
	const {collectionItem} = useContext(CollectionItemContext);
	const displayPagePreviewItem = useDisplayPagePreviewItem();

	const getFromServer = useCallback(
		(editable) => {
			if (isMappedToInfoItem(editable)) {
				return InfoItemService.getInfoItemFieldValue({
					...editable,
					onNetworkStatus: () => {},
				}).then((response) => {
					if (!response || !Object.keys(response).length) {
						throw new Error('Field value does not exist');
					}

					const {fieldValue = ''} = response;

					return fieldValue;
				});
			}

			if (isMappedToLayout(editable)) {
				return LayoutService.getLayoutFriendlyURL(editable.layout).then(
					(response) => response.friendlyURL || ''
				);
			}

			if (isMappedToStructure(editable) && displayPagePreviewItem) {
				return InfoItemService.getInfoItemFieldValue({
					...displayPagePreviewItem.data,
					fieldId: editable.mappedField,
					onNetworkStatus: () => {},
				}).then((response) => {
					if (!response || !Object.keys(response).length) {
						throw new Error('Field value does not exist');
					}

					const {fieldValue = ''} = response;

					return fieldValue;
				});
			}

			return Promise.resolve(editable?.defaultValue || editable);
		},
		[displayPagePreviewItem]
	);

	const getFromCollectionItem = useCallback(
		({collectionFieldId}) =>
			!isNullOrUndefined(collectionItem[collectionFieldId])
				? Promise.resolve(collectionItem[collectionFieldId])
				: Promise.reject(),
		[collectionItem]
	);

	if (collectionItem) {
		return getFromCollectionItem;
	}
	else {
		return getFromServer;
	}
};

const useRenderFragmentContent = () => {
	const collectionItemContext = useContext(CollectionItemContext);

	const {className: collectionItemClassName, classPK: collectionItemClassPK} =
		collectionItemContext.collectionItem || {};
	const {collectionItemIndex} = collectionItemContext;

	return useCallback(
		({fragmentEntryLinkId, onNetworkStatus, segmentsExperienceId}) => {
			return FragmentService.renderFragmentEntryLinkContent({
				collectionItemClassName,
				collectionItemClassPK,
				fragmentEntryLinkId,
				onNetworkStatus,
				segmentsExperienceId,
			}).then(({content}) => {
				return {
					collectionItemIndex,
					content,
				};
			});
		},
		[collectionItemClassName, collectionItemClassPK, collectionItemIndex]
	);
};

export {
	CollectionItemContext,
	CollectionItemContextProvider,
	useRenderFragmentContent,
	useGetContent,
	useCollectionConfig,
	useCollectionItemIndex,
	useCustomCollectionSelectorURL,
	useParentToControlsId,
	useToControlsId,
	useGetFieldValue,
};
