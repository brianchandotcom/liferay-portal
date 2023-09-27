/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {usePrevious} from '@liferay/frontend-js-react-web';
import {isNullOrUndefined} from '@liferay/layout-js-components-web';
import React, {useCallback, useContext, useEffect} from 'react';

import {updateFragmentEntryLinkContent} from '../actions/index';
import FragmentService from '../services/FragmentService';
import InfoItemService from '../services/InfoItemService';
import LayoutService from '../services/LayoutService';
import isMappedToInfoItem from '../utils/editable_value/isMappedToInfoItem';
import isMappedToLayout from '../utils/editable_value/isMappedToLayout';
import isMappedToStructure from '../utils/editable_value/isMappedToStructure';
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

const useGetContent = (
	fragmentEntryLink = {},
	languageId,
	segmentsExperienceId
) => {
	const {
		collectionContent = {},
		content,
		editableValues,
		fragmentEntryLinkId,
	} = fragmentEntryLink;

	const collectionItemContext = useContext(CollectionItemContext);
	const dispatch = useDispatch();
	const fieldSets = fragmentEntryLink.configuration?.fieldSets;
	const toControlsId = useToControlsId();

	const collectionContentId = toControlsId(fragmentEntryLinkId);

	const {
		className: collectionItemClassName,
		classPK: collectionItemClassPK,
		externalReferenceCode: collectionItemExternalReferenceCode,
	} = collectionItemContext.collectionItem || {};
	const {collectionItemIndex} = collectionItemContext;

	const {
		className: displayPagePreviewItemClassName,
		classPK: displayPagePreviewItemClassPK,
		externalReferenceCode: displayPagePreviewItemExternalReferenceCode,
	} = useDisplayPagePreviewItem()?.data || {};

	const withinCollection = !isNullOrUndefined(
		collectionItemContext.collectionItem
	);

	const [
		itemClassName,
		itemClassPK,
		itemExternalReferenceCode,
	] = withinCollection
		? [
				collectionItemClassName,
				collectionItemClassPK,
				collectionItemExternalReferenceCode,
		  ]
		: [
				displayPagePreviewItemClassName,
				displayPagePreviewItemClassPK,
				displayPagePreviewItemExternalReferenceCode,
		  ];

	const previousEditableValues = usePrevious(editableValues);
	const previousLanguageId = usePrevious(languageId);
	const previousItemClassName = usePrevious(itemClassName);
	const previousItemClassPK = usePrevious(itemClassPK);
	const previousItemExternalReferenceCode = usePrevious(
		itemExternalReferenceCode
	);

	useEffect(() => {
		const hasLocalizable =
			fieldSets?.some((fieldSet) =>
				fieldSet.fields.some((field) => field.localizable)
			) ?? false;

		if (
			shouldRenderFragmentEntryLink({
				editableValues,
				hasLocalizable,
				itemClassName,
				itemClassPK,
				itemExternalReferenceCode,
				languageId,
				previousEditableValues,
				previousItemClassName,
				previousItemClassPK,
				previousItemExternalReferenceCode,
				previousLanguageId,
				withinCollection,
			})
		) {
			FragmentService.renderFragmentEntryLinkContent({
				fragmentEntryLinkId,
				itemClassName,
				itemClassPK,
				itemExternalReferenceCode,
				languageId,
				onNetworkStatus: dispatch,
				segmentsExperienceId,
			}).then(({content}) => {
				dispatch(
					updateFragmentEntryLinkContent({
						collectionContentId,
						content,
						fragmentEntryLinkId,
					})
				);
			});
		}
	}, [
		collectionContentId,
		dispatch,
		editableValues,
		fieldSets,
		fragmentEntryLinkId,
		itemClassName,
		itemClassPK,
		itemExternalReferenceCode,
		languageId,
		previousEditableValues,
		previousItemClassName,
		previousItemClassPK,
		previousItemExternalReferenceCode,
		previousLanguageId,
		segmentsExperienceId,
		withinCollection,
	]);

	return (
		(!isNullOrUndefined(collectionItemIndex)
			? collectionContent[collectionContentId]
			: null) || content
	);
};

const useWithinCollection = () => {
	const context = useContext(CollectionItemContext);

	return !isNullOrUndefined(context.collectionItem);
};

const shouldRenderFragmentEntryLink = ({
	editableValues,
	hasLocalizable,
	itemClassName,
	itemClassPK,
	itemExternalReferenceCode,
	languageId,
	previousEditableValues,
	previousItemClassName,
	previousItemClassPK,
	previousItemExternalReferenceCode,
	previousLanguageId,
	withinCollection,
}) => {

	// For normal fragments we need to render again if the change the locale
	// and the fragment have some localizable configuration fields

	if (hasLocalizable && previousLanguageId !== languageId) {
		return true;
	}

	// For fragments inside a collection, we need to render when previousItemClassName, previousItemClassPK or previousExternalReferenceCode
	// is undefined. This happens when the collection is render at the first time or when changing the "preview with"
	// to none. When setting the item to none the component is unmounted, which means that we cannot rely on
	// the usePrevious hook values. Also we need to render when editable values change

	if (
		withinCollection &&
		(isNullOrUndefined(previousItemClassName) ||
			(itemClassPK && isNullOrUndefined(previousItemClassPK)) ||
			(itemExternalReferenceCode &&
				isNullOrUndefined(previousItemExternalReferenceCode)) ||
			(!isNullOrUndefined(editableValues) &&
				previousEditableValues !== editableValues))
	) {
		return true;
	}

	//  For any other case we need to render when the className or classPK changes

	if (
		previousItemClassName !== itemClassName &&
		(previousItemClassPK !== itemClassPK ||
			previousItemExternalReferenceCode !== itemExternalReferenceCode)
	) {
		return true;
	}

	return false;
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
					...editable,
					...displayPagePreviewItem.data,
					fieldId: editable.mappedField,
					languageId: editable.languageId,
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

export {
	CollectionItemContext,
	CollectionItemContextProvider,
	useGetContent,
	useCollectionConfig,
	useCollectionItemIndex,
	useCustomCollectionSelectorURL,
	useParentToControlsId,
	useToControlsId,
	useWithinCollection,
	useGetFieldValue,
};
