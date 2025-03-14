/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {TreeView} from '@clayui/core';
import {ClayCheckbox, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import getCN from 'classnames';
import {LearnMessage, LearnResourcesContext} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

const CONFIGURATION = {
	headers: new Headers({
		'Accept': 'application/json',
		'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
		'Content-Type': 'application/json',
	}),
	method: 'GET',
};

const SELECT_OPTIONS = {
	ALL: '', //  When 'vocabularyIds' is equal to this, 'All Vocabularies' has been selected.
	SELECT: 'SELECT',
};

/**
 * Converts a stringified list of IDs into an array of IDs. This separates
 * values by commas and filters out empty values.
 * @param {string} idsString The list of IDs as a string
 * @return {Array} Array of IDs as strings
 */
const convertToIDArray = (idsString) =>
	idsString.split(',').filter((id) => id !== '');

function SiteRow({name, onSelect, vocabularies}) {
	const _handleSelect =
		(select = true) =>
		(event) => {
			event.preventDefault();

			onSelect(vocabularies, select);
		};

	return (
		<div
			className="autofit-row"
			style={{marginLeft: vocabularies.length ? '0' : '-24px'}}
		>
			<div className="autofit-col">
				<ClayButton
					className="component-expander"
					data-toggle="collapse"
					displayType="unstyled"
					monospaced
					tabIndex="-1"
				>
					<span className="c-inner" tabIndex="-2">
						<ClayIcon symbol="angle-down" />

						<ClayIcon
							className="component-expanded-d-none"
							symbol="angle-right"
						/>
					</span>
				</ClayButton>
			</div>

			<div className={getCN('autofit-col')}>{name}</div>

			<div className="autofit-col autofit-col-expand" />

			{!!vocabularies.length && (
				<div className="autofit-col">
					<span className="autofit-row">
						<span className="autofit-col c-mr-1">
							<ClayButton
								className="quick-action-item"
								displayType="secondary"
								onClick={_handleSelect()}
								small
							>
								<span className="c-inner" tabIndex="-2">
									{Liferay.Language.get('select-all')}
								</span>
							</ClayButton>
						</span>

						<span className="autofit-col">
							<ClayButton
								className="quick-action-item"
								displayType="secondary"
								onClick={_handleSelect(false)}
								small
							>
								<span className="c-inner" tabIndex="-2">
									{Liferay.Language.get('deselect-all')}
								</span>
							</ClayButton>
						</span>
					</span>
				</div>
			)}
		</div>
	);
}

function VocabularyTree({
	loading,
	selectedKeys,
	setSelectedKeys,
	vocabularyTree,
}) {
	const _handleSelect = (list, add = true) => {
		const newList = new Set(selectedKeys);

		list.forEach(({erc}) => {
			if (add) {
				newList.add(erc);
			}
			else {
				newList.delete(erc);
			}
		});

		setSelectedKeys(newList);
	};

	const _handleToggle = (item) => {
		_handleSelect([item], !selectedKeys.has(item.erc));
	};

	if (loading || vocabularyTree === null) {
		return <ClayLoadingIndicator displayType="secondary" size="sm" />;
	}

	return vocabularyTree.length ? (
		<TreeView
			defaultItems={vocabularyTree}
			nestedKey="children"
			onSelectionChange={setSelectedKeys}
			selectedKeys={selectedKeys}
			selectionHydrationMode="render-first"
			selectionMode="multiple"
			showExpanderOnHover={false}
		>
			{(item) => (
				<TreeView.Item key={item.id}>
					<div className="treeview-link-site-row">
						<SiteRow
							name={
								item.descriptiveName_i18n?.[
									Liferay.ThemeDisplay.getLanguageId()
								] || item.descriptiveName
							}
							onSelect={_handleSelect}
							vocabularies={item.children}
						/>
					</div>

					{item.children?.length ? (
						<TreeView.Group items={item.children}>
							{({erc, name}) => (
								<TreeView.Item
									key={erc}
									style={{cursor: 'unset'}}
								>
									<ClayCheckbox
										checked={selectedKeys.has(erc)}
										onChange={() => _handleToggle(item)}
									/>

									{name}
								</TreeView.Item>
							)}
						</TreeView.Group>
					) : (
						<TreeView.Group>
							<TreeView.Item>
								{Liferay.Language.get('no-vocabularies')}
							</TreeView.Item>
						</TreeView.Group>
					)}
				</TreeView.Item>
			)}
		</TreeView>
	) : (
		<span className="c-mb-0 sheet-text text-3">
			{Liferay.Language.get(
				'an-error-has-occurred-and-we-were-unable-to-load-the-results'
			)}
		</span>
	);
}

function SelectVocabularies({
	initialSelectedVocabularyERCs = SELECT_OPTIONS.ALL,
	namespace = '',
	vocabularyERCsInputName = '',
}) {
	const initialSelectedERCsRef = useRef(
		new Set(
			initialSelectedVocabularyERCs === SELECT_OPTIONS.ALL
				? []
				: convertToIDArray(initialSelectedVocabularyERCs)
		)
	);

	const [selection, setSelection] = useState(
		initialSelectedVocabularyERCs === SELECT_OPTIONS.ALL
			? SELECT_OPTIONS.ALL
			: SELECT_OPTIONS.SELECT
	);
	const [selectedKeys, setSelectedKeys] = useState(
		new Set(
			initialSelectedVocabularyERCs === SELECT_OPTIONS.ALL
				? []
				: convertToIDArray(initialSelectedVocabularyERCs)
		)
	);
	const [vocabularyTree, setVocabularyTree] = useState(null);
	const [hiddenSelectedVocabularies, setHiddenSelectedVocabularies] =
		useState([]);
	const [vocabularyTreeLoading, setVocabularyTreeLoading] = useState(false);

	useEffect(() => {
		if (selection === SELECT_OPTIONS.SELECT) {
			_handleFetchVocabularyTree();
		}
	}, []); //eslint-disable-line

	const _handleFetchVocabularyTree = () => {
		setVocabularyTreeLoading(true);

		fetch(`/api/jsonws/invoke`, {
			body: new URLSearchParams({
				cmd: JSON.stringify({
					'/group/get-user-sites-groups': {},
				}),
				p_auth: Liferay.authToken,
			}),
			headers: new Headers({
				'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
				'Content-Type':
					'application/x-www-form-urlencoded;charset=UTF-8',
			}),
			method: 'POST',
		})
			.then((response) => response.json())
			.then((items) => {

				// Filter out results that are not a site.

				const itemsFilteredForSites = items.filter(({site}) => !!site);

				Promise.all(
					itemsFilteredForSites.map((site) =>
						fetch(
							`/o/headless-admin-taxonomy/v1.0/sites/${site.groupId}/taxonomy-vocabularies?page=0&pageSize=0`,
							CONFIGURATION
						).then((response) => response.json())
					)
				)
					.then((responses) => {
						const ercs = [];

						setVocabularyTree(
							responses.map((response, index) => ({
								...itemsFilteredForSites[index],
								children: (response?.items || [])
									.filter(({siteId}) => {

										// Filter out global vocabularies for
										// non-global sites.

										const isGlobalSite =
											itemsFilteredForSites[index]
												.groupId ===
											Liferay.ThemeDisplay.getCompanyGroupId();

										if (
											!isGlobalSite &&
											siteId?.toString() ===
												Liferay.ThemeDisplay.getCompanyGroupId()
										) {
											return false;
										}

										return true;
									})
									.map(
										({externalReferenceCode, id, name}) => {
											const erc = `${itemsFilteredForSites[index].externalReferenceCode}&&${externalReferenceCode}`;

											ercs.push(erc); // Collect ERCs to allow removal of hidden selected vocabularies

											return {
												erc,
												id: id.toString(),
												name,
											};
										}
									),
							}))
						);

						setHiddenSelectedVocabularies(
							Array.from(initialSelectedERCsRef.current).filter(
								(erc) => !ercs.includes(erc)
							)
						);
					})
					.catch(() => setVocabularyTree([]));
			})
			.catch(() => setVocabularyTree([]))
			.finally(() => setVocabularyTreeLoading(false));
	};

	const _handleRemoveHiddenVocabularies = () => {
		const newList = new Set(selectedKeys);

		hiddenSelectedVocabularies.forEach((erc) => {
			newList.delete(erc);
		});

		setSelectedKeys(newList);
	};

	const _handleSelectionChange = (value) => {
		setSelection(value);

		if (value === SELECT_OPTIONS.SELECT && !vocabularyTree) {
			_handleFetchVocabularyTree();
		}
	};

	const _isHiddenVocabulariesRemoved = () =>
		Array.from(selectedKeys).some((erc) =>
			hiddenSelectedVocabularies.includes(erc)
		);

	return (
		<div className="select-vocabularies">
			<label>{Liferay.Language.get('select-vocabularies')}</label>

			<input
				hidden
				id={`${namespace}${vocabularyERCsInputName}`}
				name={`${namespace}${vocabularyERCsInputName}`}
				readOnly
				value={
					selection === SELECT_OPTIONS.ALL
						? SELECT_OPTIONS.ALL
						: Array.from(selectedKeys).toString()
				}
			/>

			<div className="c-mb-3 c-mt-2 sheet-text text-3">
				<span>
					{Liferay.Language.get(
						'select-vocabularies-configuration-description'
					)}
				</span>

				<LearnMessage
					className="c-ml-1"
					resource="portal-search-web"
					resourceKey="tag-and-category-facet"
				/>
			</div>

			<ClayRadioGroup onChange={_handleSelectionChange} value={selection}>
				<ClayRadio
					label={Liferay.Language.get('all-vocabularies')}
					value={SELECT_OPTIONS.ALL}
				/>

				<ClayRadio
					label={Liferay.Language.get('select-vocabularies')}
					value={SELECT_OPTIONS.SELECT}
				/>
			</ClayRadioGroup>

			{selection === SELECT_OPTIONS.SELECT &&
				!!hiddenSelectedVocabularies.length && (
					<ClayAlert
						displayType="info"
						style={{opacity: 1}}
						title={`${Liferay.Language.get('info')}:`}
					>
						{Liferay.Language.get(
							'select-vocabularies-configuration-alert'
						)}

						<LearnMessage
							className="c-ml-1"
							resource="portal-search-web"
							resourceKey="tag-and-category-facet"
						/>

						{_isHiddenVocabulariesRemoved() && (
							<ClayAlert.Footer>
								<ClayButton
									alert
									onClick={_handleRemoveHiddenVocabularies}
								>
									{Liferay.Language.get(
										'remove-hidden-vocabularies'
									)}
								</ClayButton>
							</ClayAlert.Footer>
						)}
					</ClayAlert>
				)}

			{selection === SELECT_OPTIONS.SELECT && (
				<VocabularyTree
					loading={vocabularyTreeLoading}
					selectedKeys={selectedKeys}
					setSelectedKeys={setSelectedKeys}
					vocabularyTree={vocabularyTree}
				/>
			)}
		</div>
	);
}

export default function ({
	initialSelectedVocabularyERCs,
	learnResources,
	namespace,
	vocabularyERCsInputName,
}) {
	return (
		<LearnResourcesContext.Provider value={learnResources}>
			<SelectVocabularies
				initialSelectedVocabularyERCs={initialSelectedVocabularyERCs}
				namespace={namespace}
				vocabularyERCsInputName={vocabularyERCsInputName}
			/>
		</LearnResourcesContext.Provider>
	);
}
