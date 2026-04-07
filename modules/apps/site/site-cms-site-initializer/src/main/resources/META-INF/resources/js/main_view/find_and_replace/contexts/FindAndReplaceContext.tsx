/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locale, openToast} from 'frontend-js-components-web';
import React, {
	Dispatch,
	SetStateAction,
	createContext,
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';

import {ISearchAssetObjectEntry} from '../../../common/types/AssetType';
import {StickerConfig} from '../../../common/types/StickerConfig';
import FindAndReplaceService from '../services/FindAndReplaceService';

export type View =
	| 'loading'
	| 'no-matches'
	| 'setup'
	| 'summary'
	| 'preview'
	| 'discard';

export type ReplaceItemField = {
	label: string;
	name: string;
	value?: string;
	value_i18n?: Partial<Record<Locale['id'], string>>;
};

export type RelatedItem = {
	externalReferenceCode: string;
	fields: ReplaceItemField[];
	label: string;
	name: string;
};

export type ReplaceItem = {
	className: string;
	externalReferenceCode: string;
	fields: ReplaceItemField[];
	id: string;
	related?: RelatedItem[];
	stickerClassName: string;
	stickerSymbol: string;
	title: string;
};

export type History = {
	hasApplied: boolean;
	hasAppliedAll: boolean;
	hasDiscarded: boolean;
	itemsCount: number;
};

export const FindAndReplaceContext = createContext<{
	apply: (itemId: string) => void;
	closeModal: () => void;
	dataSetId: string;
	discard: (itemId: string) => void;
	items: ReplaceItem[] | null;
	localeId: Locale['id'] | 'all';
	locales: Locale[];
	previewId: string | null;
	previousView: View | null;
	replacement: string;
	search: string;
	setHistory: (history: Partial<History>) => void;
	setItems: Dispatch<SetStateAction<ReplaceItem[] | null>>;
	setLocaleId: Dispatch<SetStateAction<Locale['id'] | 'all'>>;
	setPreviewId: Dispatch<SetStateAction<string | null>>;
	setPreviousView: Dispatch<SetStateAction<View | null>>;
	setReplacement: Dispatch<SetStateAction<string>>;
	setSearch: Dispatch<SetStateAction<string>>;
	setView: Dispatch<SetStateAction<View>>;
	view: View;
}>({
	apply: () => {},
	closeModal: () => {},
	dataSetId: '',
	discard: () => {},
	items: null,
	localeId: 'all',
	locales: [],
	previewId: null,
	previousView: null,
	replacement: '',
	search: '',
	setHistory: () => {},
	setItems: () => {},
	setLocaleId: () => {},
	setPreviewId: () => {},
	setPreviousView: () => {},
	setReplacement: () => {},
	setSearch: () => {},
	setView: () => {},
	view: 'loading',
});

type Props = {
	availableLocales: Locale[];
	children: React.ReactNode;
	closeModal: () => void;
	dataSetId: string;
	fdsItems: ISearchAssetObjectEntry[];
	setHistory: (history: Partial<History>) => void;
	stickerConfig: StickerConfig;
};

export function FindAndReplaceContextProvider({
	availableLocales,
	children,
	closeModal,
	dataSetId,
	fdsItems,
	setHistory,
	stickerConfig,
}: Props) {
	const [items, setItems] = useState<ReplaceItem[] | null>(null);

	const [locales, setLocales] = useState<Locale[]>([]);

	const [localeId, setLocaleId] = useState<Locale['id'] | 'all'>('all');

	const [replacement, setReplacement] = useState('');

	const [search, setSearch] = useState('');

	const [view, setView] = useState<View>('loading');

	const [previousView, setPreviousView] = useState<View | null>(null);

	const [previewId, setPreviewId] = useState<string | null>(null);

	const loadingRef = useRef(false);

	const removeItem = useCallback(
		(itemId: string) => {
			const isLast = items!.length === 1;
			const itemsCount = items!.length - 1;

			setHistory({itemsCount});

			setItems((prevItems) => {
				if (!prevItems) {
					return prevItems;
				}

				return prevItems.filter((item) => item.id !== itemId);
			});

			if (isLast) {
				closeModal();
			}
		},
		[closeModal, items, setHistory]
	);

	const apply = useCallback(
		(itemId: string) => {
			setHistory({hasApplied: true});

			removeItem(itemId);
		},
		[removeItem, setHistory]
	);

	const discard = useCallback(
		(itemId: string) => {
			setHistory({hasDiscarded: true});

			removeItem(itemId);
		},
		[removeItem, setHistory]
	);

	useEffect(() => {
		async function loadItems() {
			const response = await FindAndReplaceService.getReplaceItems({
				fdsItems,
				stickerConfig,
			});

			if (response.error) {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});

				closeModal();

				return;
			}

			const items = response.data ?? [];

			setItems(items);
			setHistory({itemsCount: items.length});
			setLocales(filterLocales(items, availableLocales));
			setView('setup');
		}

		if (items || loadingRef.current) {
			return;
		}

		loadingRef.current = true;

		loadItems();
	}, [
		availableLocales,
		closeModal,
		fdsItems,
		items,
		search,
		setHistory,
		stickerConfig,
	]);

	return (
		<FindAndReplaceContext.Provider
			value={{
				apply,
				closeModal,
				dataSetId,
				discard,
				items,
				localeId,
				locales,
				previewId,
				previousView,
				replacement,
				search,
				setHistory,
				setItems,
				setLocaleId,
				setPreviewId,
				setPreviousView,
				setReplacement,
				setSearch,
				setView,
				view,
			}}
		>
			{children}
		</FindAndReplaceContext.Provider>
	);
}

export function useOpenDiscard() {
	const {setPreviousView, setView, view} = useContext(FindAndReplaceContext);

	return useCallback(() => {
		setPreviousView(view);

		setView('discard');
	}, [setPreviousView, setView, view]);
}

export function useCancelDiscard() {
	const {previousView, setPreviousView, setView} = useContext(
		FindAndReplaceContext
	);

	return useCallback(() => {
		if (previousView) {
			setView(previousView);
		}

		setPreviousView(null);
	}, [previousView, setPreviousView, setView]);
}

function filterLocales(items: ReplaceItem[], availableLocales: Locale[]) {
	const localeIds = new Set<Locale['id']>();

	for (const item of items) {
		for (const field of item.fields) {
			if (!field.value_i18n) {
				continue;
			}

			for (const localeId of Object.keys(field.value_i18n)) {
				localeIds.add(localeId as Locale['id']);
			}
		}
	}

	return availableLocales.filter(({id}) => localeIds.has(id));
}
