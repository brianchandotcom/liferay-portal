/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {openItemSelectorModal} from '@liferay/frontend-js-item-selector-web';
import classNames from 'classnames';
import {TranslationAdminSelector} from 'frontend-js-components-web';
import {fetch, objectToFormData, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

const HEADLESS_TAXONOMY_VOCABULARIES_BASE =
	'/o/headless-admin-taxonomy/v1.0/sites';

const VISIBILITY_TYPE_PUBLIC = 0;

function buildVocabulariesURL(currentSiteId) {
	const url = new URL(
		`${HEADLESS_TAXONOMY_VOCABULARIES_BASE}/${String(
			currentSiteId
		)}/taxonomy-vocabularies`,
		window.location.origin
	);

	url.searchParams.set(
		'filter',
		`visibilityType eq ${VISIBILITY_TYPE_PUBLIC}`
	);

	return url.toString();
}

function resolveScope(vocabulary, context) {
	if (vocabulary.assetLibraryKey) {
		const assetLibrary = context.assetLibrariesByKey.get(
			vocabulary.assetLibraryKey
		);

		return {
			scopeExternalReferenceCode:
				assetLibrary?.externalReferenceCode ??
				vocabulary.assetLibraryKey,
			scopeName: assetLibrary?.name ?? vocabulary.assetLibraryKey,
		};
	}

	if (String(vocabulary.siteId) === context.companyGroupId) {
		return {
			scopeExternalReferenceCode: context.companyExternalReferenceCode,
			scopeName: Liferay.Language.get('global'),
		};
	}

	return {
		scopeExternalReferenceCode: context.siteExternalReferenceCode,
		scopeName: context.siteName,
	};
}

function AssetVocabularyContextualSidebar({
	assetVocabulary,
	chooseAssetVocabularyProps,
	defaultLanguageId,
	hasModel,
	locales,
	localizedNames,
	namespace,
	numberOfCategories: initialNumberOfCategories,
	showAssetVocabularyLevel,
	siteName: initialSiteName,
	useCustomName,
}) {
	const [translations, setTranslations] = useState(localizedNames);
	const [customNameEnabled, setCustomNameEnabled] = useState(useCustomName);
	const [assetVocabularyLevelEnabled, setAssetVocabularyLevelEnabled] =
		useState(showAssetVocabularyLevel);
	const [selectedLocaleId, setSelectedLocaleId] = useState(defaultLanguageId);
	const [selectedVocabulary, setSelectedVocabulary] =
		useState(assetVocabulary);
	const [numberOfCategories, setNumberOfCategories] = useState(
		initialNumberOfCategories
	);
	const [siteName, setSiteName] = useState(initialSiteName);
	const [customName, setCustomName] = useState(
		translations[selectedLocaleId] || assetVocabulary.title
	);
	const [customNameInvalid, setCustomNameInvalid] = useState(false);
	const nameRef = useRef(null);

	const {
		assetLibraries,
		companyExternalReferenceCode,
		companyGroupId,
		currentSiteId,
		getAssetVocabularyDetailsURL,
		siteExternalReferenceCode,
		siteName: chooseAssetVocabularySiteName,
	} = chooseAssetVocabularyProps;

	useEffect(() => {
		if (!customNameEnabled) {
			return;
		}

		if (
			translations[selectedLocaleId] === undefined &&
			selectedLocaleId !== defaultLanguageId
		) {
			setCustomName('');
		}
		else {
			setCustomName(
				translations[selectedLocaleId] ?? assetVocabulary.title
			);
		}

		nameRef.current?.focus();
	}, [
		assetVocabulary.title,
		customNameEnabled,
		defaultLanguageId,
		selectedLocaleId,
		translations,
	]);

	useEffect(() => {
		const onFormSubmit = (event) => {
			if (!customName) {
				event.preventDefault();

				setCustomNameInvalid(true);
			}

			if (customName && translations[defaultLanguageId] === '') {
				setTranslations({
					...translations,
					[defaultLanguageId]: assetVocabulary.title,
				});
			}
		};

		const submitButton = document.querySelector('button[type=submit]');

		submitButton?.addEventListener('click', onFormSubmit);

		return () => {
			submitButton?.removeEventListener('click', onFormSubmit);
		};
	}, [assetVocabulary.title, customName, defaultLanguageId, translations]);

	const openChooseItemModal = () => {
		const scopeContext = {
			assetLibrariesByKey: new Map(
				(assetLibraries || []).map((assetLibrary) => [
					assetLibrary.externalReferenceCode,
					assetLibrary,
				])
			),
			companyExternalReferenceCode,
			companyGroupId: String(companyGroupId),
			siteExternalReferenceCode,
			siteName: chooseAssetVocabularySiteName,
		};

		const TitleCell = ({itemData}) => {
			const {scopeName} = resolveScope(itemData, scopeContext);

			return sub(
				Liferay.Language.get('x-group-x'),
				itemData.name,
				scopeName
			);
		};

		openItemSelectorModal({
			apiURL: buildVocabulariesURL(currentSiteId),
			fdsProps: {
				configInURLBehavior: 'OFF',
				customRenderers: {
					tableCell: [
						{
							component: TitleCell,
							name: 'titleWithScope',
							type: 'internal',
						},
					],
				},
				id: 'siteNavVocabularySelectorFDS',
				pagination: {
					deltas: [{label: 20}, {label: 50}],
					initialDelta: 20,
				},
				views: [
					{
						contentRenderer: 'table',
						label: '',
						name: 'list',
						schema: {
							fields: [
								{
									contentRenderer: 'titleWithScope',
									fieldName: 'name',
									label: Liferay.Language.get('title'),
								},
								{
									fieldName: 'creator.name',
									label: Liferay.Language.get('user'),
								},
								{
									contentRenderer: 'dateTime',
									fieldName: 'dateModified',
									label: Liferay.Language.get(
										'modified-date'
									),
									sortable: true,
								},
							],
						},
					},
				],
			},
			itemTypeLabel: Liferay.Language.get('vocabulary'),
			items: [],
			locator: {id: 'id', label: 'name', value: 'id'},
			multiSelect: false,
			onItemsChange: (selected) => {
				const [vocabulary] = selected;

				if (!vocabulary) {
					return;
				}

				const {scopeExternalReferenceCode} = resolveScope(
					vocabulary,
					scopeContext
				);

				const item = {
					externalReferenceCode: vocabulary.externalReferenceCode,
					scopeExternalReferenceCode,
					title: vocabulary.name,
					type: 'AssetVocabulary',
				};

				setSelectedVocabulary(item);

				fetch(getAssetVocabularyDetailsURL, {
					body: objectToFormData(Liferay.Util.ns(namespace, item)),
					method: 'POST',
				})
					.then((response) => response.json())
					.then((jsonResponse) => {
						setNumberOfCategories(jsonResponse.numberOfCategories);
						setSiteName(jsonResponse.siteName);
					})
					.catch(() => {});
			},
			size: 'lg',
			title: Liferay.Language.get('select-vocabulary'),
		});
	};

	return (
		<>
			<ClayForm.Group className="align-items-center d-flex mb-2">
				<ClayCheckbox
					checked={assetVocabularyLevelEnabled}
					label={Liferay.Language.get('display-name-as-first-level')}
					onChange={() =>
						setAssetVocabularyLevelEnabled(
							!assetVocabularyLevelEnabled
						)
					}
				/>

				<span
					className="mb-3 ml-1"
					data-tooltip-align="top"
					title={Liferay.Language.get('use-vocabulary-level-help')}
				>
					<ClayIcon symbol="question-circle" />
				</span>
			</ClayForm.Group>

			<ClayForm.Group className="align-items-center d-flex mb-2">
				<ClayCheckbox
					checked={customNameEnabled}
					label={Liferay.Language.get('use-custom-name')}
					onChange={() => setCustomNameEnabled(!customNameEnabled)}
				/>

				<span
					className="mb-3 ml-1"
					data-tooltip-align="top"
					title={Liferay.Language.get('use-custom-name-help')}
				>
					<ClayIcon symbol="question-circle" />
				</span>
			</ClayForm.Group>

			<ClayForm.Group
				className={classNames({'has-error': customNameInvalid})}
			>
				<label
					className={classNames({disabled: !customNameEnabled})}
					htmlFor={`${namespace}_nameInput`}
				>
					{Liferay.Language.get('name')}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							disabled={!customNameEnabled}
							id={`${namespace}_nameInput`}
							onChange={(event) => {
								setTranslations({
									...translations,
									[selectedLocaleId]: event.target.value,
								});

								setCustomName(event.target.value);
								setCustomNameInvalid(false);
							}}
							ref={nameRef}
							type="text"
							value={customName}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem
						className="site-navigation-language-selector"
						shrink
					>
						<TranslationAdminSelector
							activeLanguageIds={locales.map(
								(locale) => locale.id
							)}
							availableLocales={locales}
							defaultLanguageId={defaultLanguageId}
							onSelectedLanguageIdChange={setSelectedLocaleId}
							selectedLanguageId={selectedLocaleId}
							translations={translations}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				{customNameEnabled &&
					selectedLocaleId !== defaultLanguageId && (
						<div className="form-text">
							{translations[defaultLanguageId] ??
								assetVocabulary.title}
						</div>
					)}

				{customNameInvalid && (
					<ClayForm.FeedbackItem>
						{Liferay.Language.get('this-field-is-required')}
					</ClayForm.FeedbackItem>
				)}
			</ClayForm.Group>

			<ClayForm.Group
				className={classNames({
					'has-warning': !hasModel || !numberOfCategories,
				})}
			>
				<label htmlFor={`${namespace}_itemInput`}>
					{Liferay.Language.get('item')}
				</label>

				<ClayInput.Group className="site-navigation-item-selector">
					<ClayInput.GroupItem>
						<ClayInput
							className="text-secondary"
							id={`${namespace}_itemInput`}
							onChange={() => {}}
							onClick={openChooseItemModal}
							type="text"
							value={selectedVocabulary.title}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('change-item')}
							displayType="secondary"
							onClick={openChooseItemModal}
							symbol="change"
							title={Liferay.Language.get('change-item')}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				{!numberOfCategories && (
					<>
						<ClayAlert
							className="mt-1"
							displayType="warning"
							title={
								hasModel
									? Liferay.Language.get(
											'no-categories-inside'
										)
									: Liferay.Language.get('no-reference-found')
							}
							variant="feedback"
						/>

						<p className="small text-secondary">
							{hasModel
								? Liferay.Language.get(
										'vocabularies-without-categories-are-hidden-from-navigation-menus'
									)
								: Liferay.Language.get(
										'this-item-references-an-entity-that-is-missing-or-not-yet-available'
									)}
						</p>
					</>
				)}
			</ClayForm.Group>

			<ClayForm.Group className="pt-2">
				<div className="list-group">
					<p className="list-group-title">
						{Liferay.Language.get('type')}
					</p>

					<div className="d-flex">
						<ClayLabel displayType="secondary">
							{Liferay.Language.get('vocabulary')}
						</ClayLabel>

						<ClayLabel displayType="info">
							{Liferay.Language.get('dynamic')}
						</ClayLabel>
					</div>
				</div>
			</ClayForm.Group>

			<ClayForm.Group>
				<div className="list-group">
					<p className="list-group-title">
						{Liferay.Language.get('categories')}
					</p>

					<p className="list-group-text">{numberOfCategories}</p>
				</div>
			</ClayForm.Group>

			<ClayForm.Group>
				<div className="list-group">
					<p className="list-group-title">
						{Liferay.Language.get('site')}
					</p>

					<p className="list-group-text">{siteName}</p>
				</div>
			</ClayForm.Group>

			<FormValues
				localizedNames={translations}
				namespace={namespace}
				selectedVocabulary={selectedVocabulary}
				showAssetVocabularyLevel={assetVocabularyLevelEnabled}
				useCustomName={customNameEnabled}
			/>
		</>
	);
}

AssetVocabularyContextualSidebar.propTypes = {
	assetVocabulary: PropTypes.shape({
		externalReferenceCode: PropTypes.string,
		scopeExternalReferenceCode: PropTypes.string,
		title: PropTypes.string,
		type: PropTypes.string,
	}).isRequired,
	chooseAssetVocabularyProps: PropTypes.shape({
		assetLibraries: PropTypes.arrayOf(
			PropTypes.shape({
				externalReferenceCode: PropTypes.string,
				id: PropTypes.string,
				name: PropTypes.string,
			})
		),
		companyExternalReferenceCode: PropTypes.string,
		companyGroupId: PropTypes.string,
		currentSiteId: PropTypes.string,
		getAssetVocabularyDetailsURL: PropTypes.string,
		siteExternalReferenceCode: PropTypes.string,
		siteName: PropTypes.string,
	}).isRequired,
	defaultLanguageId: PropTypes.string.isRequired,
	locales: PropTypes.array.isRequired,
	localizedNames: PropTypes.object.isRequired,
	namespace: PropTypes.string.isRequired,
	numberOfCategories: PropTypes.number.isRequired,
	showAssetVocabularyLevel: PropTypes.bool.isRequired,
	siteName: PropTypes.string.isRequired,
	useCustomName: PropTypes.bool.isRequired,
};

function FormValues({
	localizedNames,
	namespace,
	selectedVocabulary,
	showAssetVocabularyLevel,
	useCustomName,
}) {
	return (
		<>
			<input
				name={getFieldName(namespace, 'externalReferenceCode')}
				readOnly
				type="hidden"
				value={selectedVocabulary.externalReferenceCode || ''}
			/>
			<input
				name={getFieldName(namespace, 'scopeExternalReferenceCode')}
				readOnly
				type="hidden"
				value={selectedVocabulary.scopeExternalReferenceCode || ''}
			/>
			<input
				name={getFieldName(namespace, 'title')}
				readOnly
				type="hidden"
				value={selectedVocabulary.title || ''}
			/>
			<input
				name={getFieldName(namespace, 'type')}
				readOnly
				type="hidden"
				value={selectedVocabulary.type || ''}
			/>
			<input
				name={getFieldName(namespace, 'localizedNames')}
				readOnly
				type="hidden"
				value={useCustomName ? JSON.stringify(localizedNames) : '{}'}
			/>
			<input
				name={getFieldName(namespace, 'showAssetVocabularyLevel')}
				readOnly
				type="hidden"
				value={showAssetVocabularyLevel}
			/>
			<input
				name={getFieldName(namespace, 'useCustomName')}
				readOnly
				type="hidden"
				value={useCustomName}
			/>
		</>
	);
}

FormValues.propTypes = {
	localizedNames: PropTypes.object.isRequired,
	namespace: PropTypes.string.isRequired,
	selectedVocabulary: PropTypes.shape({
		externalReferenceCode: PropTypes.string,
		scopeExternalReferenceCode: PropTypes.string,
		title: PropTypes.string,
		type: PropTypes.string,
	}).isRequired,
	showAssetVocabularyLevel: PropTypes.bool,
	useCustomName: PropTypes.bool,
};

function getFieldName(namespace, fieldName) {
	return `${namespace}TypeSettingsProperties--${fieldName}--`;
}

export {AssetVocabularyContextualSidebar};
