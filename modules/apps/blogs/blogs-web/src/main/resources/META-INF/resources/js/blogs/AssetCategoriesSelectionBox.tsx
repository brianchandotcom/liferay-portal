/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayDualListBox} from '@clayui/form';
import {normalizeFriendlyURL} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

declare module 'frontend-js-web' {
	export function normalizeFriendlyURL(text: string): string;
}

type TCategory = {
	label: string;
	value: string;
};

export default function AssetCategoriesSelectionBox({
	availableCategories: initialAvailableCategories = [],
	currentCategories: initialCurrentCategories = [],
	disabled: initialDisabled,
	inputAddon = '',
	portletNamespace,
}: {
	availableCategories?: Array<TCategory>;
	currentCategories?: Array<TCategory>;
	disabled: boolean;
	inputAddon?: string;
	portletNamespace: string;
}) {
	const [categories, setCategories] = useState([
		initialAvailableCategories,
		initialCurrentCategories,
	]);
	const [disabled, setDisabled] = useState(initialDisabled);
	const [_, currentCategories] = categories;

	const friendlyURLInputRef = useRef(
		document.getElementById(`${portletNamespace}urlTitle`)
	);

	useEffect(() => {
		const friendlyURLInput = friendlyURLInputRef.current;

		if (friendlyURLInput) {
			const mutationObserver = new MutationObserver((mutations) => {
				mutations.forEach((mutation) => {
					if (
						mutation.type === 'attributes' &&
						mutation.attributeName === 'disabled'
					) {
						const targetInput = mutation.target as HTMLFormElement;
						setDisabled(targetInput.disabled);
					}
				});
			});

			mutationObserver.observe(friendlyURLInput, {
				attributeFilter: ['disabled'],
				attributes: true,
			});

			return () => {
				mutationObserver.disconnect();
			};
		}
	}, []);

	const inputAddonNodeRef = useRef(
		document.querySelector(
			`[for="${portletNamespace}urlTitle"] + .form-text`
		)
	);

	useEffect(() => {
		if (inputAddonNodeRef.current) {
			const inputAddonElement = inputAddonNodeRef.current as HTMLElement;

			inputAddonElement.innerText =
				inputAddon +
				(disabled
					? ''
					: currentCategories
							.map(
								(category) =>
									`${normalizeFriendlyURL(category.label)}/`
							)
							.join(''));
		}
	}, [inputAddon, inputAddonNodeRef, currentCategories, disabled]);

	const articleCategoriesWrapperRef = useRef(
		document.getElementById(`${portletNamespace}categorization`)
	);

	useEffect(() => {
		const articleCategoriesWrapper = articleCategoriesWrapperRef.current;

		if (articleCategoriesWrapper) {
			const mutationObserver = new MutationObserver((mutations) => {
				mutations.forEach((mutation) => {
					if (
						mutation.type !== 'childList' ||
						![
							...mutation.addedNodes,
							...mutation.removedNodes,
						].some(
							(node) =>
								node instanceof HTMLInputElement &&
								node.name.includes(
									`${portletNamespace}assetCategoryIds`
								)
						)
					) {
						return;
					}

					const labels = articleCategoriesWrapper.querySelectorAll(
						'.label-item-expand[id*="clay-id-"]'
					);

					if (!labels) {
						return;
					}

					const articleCategories = Array.from(labels).map((node) => {
						const regex = /clay-id-\d+-label-(\d+)-span/;
						const match = node.id.match(regex);

						return {
							label: node.textContent ?? '',
							value: match?.[1] ?? '',
						};
					});

					setCategories(([_, actualCurrentCategories]) => {
						const currentCategories =
							actualCurrentCategories.filter(
								(actualCurrentCategory) =>
									articleCategories.some(
										(articleCategory) =>
											actualCurrentCategory.value ===
											articleCategory.value
									)
							);

						const avialableCategories = articleCategories.filter(
							(articleCategory) =>
								!currentCategories.some(
									(currentCategory) =>
										articleCategory.value ===
										currentCategory.value
								)
						);

						return [avialableCategories, currentCategories];
					});
				});
			});

			mutationObserver.observe(articleCategoriesWrapper, {
				attributeFilter: ['value'],
				attributes: true,
				childList: true,
				subtree: true,
			});

			return () => {
				mutationObserver.disconnect();
			};
		}
	}, [portletNamespace]);

	return (
		<>
			<ClayDualListBox
				items={categories}
				left={{
					id: `${portletNamespace}available`,
					label: Liferay.Language.get('available'),
				}}
				onItemsChange={setCategories}
				right={{
					id: `${portletNamespace}current`,
					label: Liferay.Language.get('current'),
				}}
				size={3}
			/>

			<input
				name={`${portletNamespace}friendlyURLAssetCategoryIds`}
				type="hidden"
				value={currentCategories.map(({value}) => value).join(',')}
			/>
		</>
	);
}
