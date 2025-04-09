/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate, sub} from 'frontend-js-web';
import React, {ReactElement, useEffect, useState} from 'react';

import {
	displayCreateSuccessToast,
	displayEditSuccessToast,
	displayErrorToast,
} from '../../util/ToastUtil';
import CategorizationContentContainer from '../components/CategorizationContentContainer';
import CategorizationManagementToolbar from '../components/CategorizationManagementToolbar';
import CategoryService from '../services/CategoryService';
import EditCategoryGeneralInfoTab from './components/EditCategoryGeneralInfoTab';

interface Props {
	backURL: string | URL;
	categoryByCategoryIdApiUrl: string;
	categoryByVocabularyIdApiUrl: string;
	categoryId: number;
	defaultLanguageId: string;
	isCreateNew: boolean;
	locales: any[];
	spritemap: string;
	vocabularyId: number;
}

const EditCategoryPage = ({
	backURL,
	categoryByCategoryIdApiUrl,
	categoryByVocabularyIdApiUrl,
	categoryId,
	defaultLanguageId,
	isCreateNew,
	locales,
	spritemap,
}: Props) => {
	const [category, setCategory] = useState<TaxonomyCategory>({
		name: '',
		name_i18n: {
			[defaultLanguageId]: '',
		},
	});
	const [nameInputError, setNameInputError] = useState<string>('');
	const [title, setTitle] = useState<string>('');

	useEffect(() => {
		const fetchInitialData = async () => {
			if (isCreateNew) {
				return;
			}
			else {
				try {
					const fetchedData = await CategoryService.getCategory(
						categoryByCategoryIdApiUrl,
						categoryId
					);

					setTitle(fetchedData.name);
					setCategory(fetchedData);
				}
				catch (error) {
					console.error(error);

					navigate(backURL);
				}
			}
		};

		void fetchInitialData();

		return () => {
			resetForm();
		};

		// eslint-disable-next-line react-compiler/react-compiler
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	function resetForm() {
		setCategory({
			name: '',
			name_i18n: {
				[defaultLanguageId]: '',
			},
		});
		setNameInputError('');
		setTitle('');
	}

	async function handleSave() {
		if (nameInputError !== '') {
			return;
		}

		try {
			if (isCreateNew) {
				await CategoryService.createCategory(
					categoryByVocabularyIdApiUrl,
					category
				);

				navigate(backURL);
				displayCreateSuccessToast(category.name);
			}
			else {
				await CategoryService.updateCategory(
					categoryByCategoryIdApiUrl,
					category
				);

				navigate(backURL);
				displayEditSuccessToast(category.name);
			}
		}
		catch (error) {
			console.error(error);

			displayErrorToast();
		}
	}

	async function handleSaveAndAddAnother() {
		if (nameInputError !== '') {
			return;
		}

		try {
			await CategoryService.createCategory(
				categoryByVocabularyIdApiUrl,
				category
			);
		}
		catch (error) {
			console.error(error);

			displayErrorToast();
		}

		window.location.reload();

		displayCreateSuccessToast(category.name);
	}

	const createMainContentMap = () => {
		const NAVIGATION_TABS = {
			GENERAL: 'general',
			IMAGES: 'images',
			PROPERTIES: 'properties',
		};

		const mainContentMap = new Map<string, ReactElement>();

		mainContentMap.set(
			NAVIGATION_TABS.GENERAL,
			<EditCategoryGeneralInfoTab
				category={category}
				defaultLanguageId={defaultLanguageId}
				locales={locales}
				nameInputError={nameInputError}
				setCategory={setCategory}
				setNameInputError={setNameInputError}
				spritemap={spritemap}
			/>
		);
		mainContentMap.set(
			NAVIGATION_TABS.IMAGES,
			<div>Images Tab Placeholder Content</div>
		);
		mainContentMap.set(
			NAVIGATION_TABS.PROPERTIES,
			<div>Properties Tab Placeholder Content</div>
		);

		return mainContentMap;
	};

	return (
		<div className="categorization-section">
			<div className="d-flex edit-vocabulary flex-column">
				<CategorizationManagementToolbar
					backURL={backURL}
					handleSave={handleSave}
					handleSaveAndAddAnother={
						isCreateNew ? handleSaveAndAddAnother : undefined
					}
					showSaveAndAddAnotherButton={isCreateNew}
					title={
						isCreateNew
							? Liferay.Language.get('new-category')
							: sub(Liferay.Language.get('edit-x'), title)
					}
				/>

				<CategorizationContentContainer
					mainContentMap={createMainContentMap()}
				/>
			</div>
		</div>
	);
};
export default EditCategoryPage;
