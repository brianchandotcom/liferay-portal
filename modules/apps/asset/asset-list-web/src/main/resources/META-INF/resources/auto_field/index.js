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
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {
	AssetTagsSelector,
	AssetVocabularyCategoriesSelector
} from 'asset-taglib';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

const DEFAULT_RULE = {
	queryContains: true,
	type: 'assetTags'
};

const QUERY_AND_OPERATOR_OPTIONS = [
	{
		label: Liferay.Language.get('all'),
		value: true
	},
	{
		label: Liferay.Language.get('any'),
		value: false
	}
];

const QUERY_CONTAINS_OPTIONS = [
	{
		label: Liferay.Language.get('contains'),
		value: true
	},
	{
		label: Liferay.Language.get('does-not-contain'),
		value: false
	}
];

const RULE_TYPE_OPTIONS = [
	{
		label: Liferay.Language.get('categories'),
		value: 'assetCategories'
	},
	{
		label: Liferay.Language.get('keywords'),
		value: 'keywords'
	},
	{
		label: Liferay.Language.get('tags'),
		value: 'assetTags'
	}
];

function AssetCategories({
	categorySelectorURL,
	groupIds,
	index,
	namespace,
	rule,
	vocabularyIds
}) {
	const [selectedItems, setSelectedItems] = useState(
		rule.selectedItems || []
	);

	return (
		<ClayForm.Group>
			<AssetVocabularyCategoriesSelector
				categoryIds={rule.queryValues ? rule.queryValues : ''}
				eventName={`${namespace}selectCategory`}
				groupIds={groupIds}
				inputName={`${namespace}queryCategoryIds${index}`}
				onSelectedItemsChange={setSelectedItems}
				portletURL={categorySelectorURL}
				selectedItems={selectedItems}
				sourceItemsVocabularyIds={vocabularyIds}
			/>
		</ClayForm.Group>
	);
}

function AssetTags({groupIds, index, namespace, rule, tagSelectorURL}) {
	const [inputValue, setInputValue] = useState();
	const [selectedItems, setSelectedItems] = useState(rule.selectedItems);

	return (
		<ClayForm.Group>
			<AssetTagsSelector
				eventName={`${namespace}selectTag`}
				groupIds={groupIds}
				inputName={`${namespace}queryTagNames${index}`}
				inputValue={inputValue}
				onInputValueChange={setInputValue}
				onSelectedItemsChange={setSelectedItems}
				portletURL={tagSelectorURL}
				selectedItems={selectedItems}
				showSelectButton={true}
				tagNames={rule.queryValues ? rule.queryValues : ''}
			/>
		</ClayForm.Group>
	);
}

function Keywords({index, namespace, onChange, rule}) {
	return (
		<ClayForm.Group>
			<label htmlFor={`${namespace}keywords${index}`}>
				{Liferay.Language.get('keywords')}
			</label>

			<ClayInput
				className="asset-query-keywords"
				data-index={index}
				data-item-index={index}
				data-property="queryValues"
				id={`${namespace}keywords${index}`}
				name={`${namespace}keywords${index}`}
				onChange={onChange}
				type="text"
				value={rule.queryValues}
			/>
		</ClayForm.Group>
	);
}

function Rule({
	categorySelectorURL,
	groupIds,
	index,
	namespace,
	onDeleteRule,
	onRuleChange,
	rule,
	tagSelectorURL,
	vocabularyIds
}) {
	return (
		<>
			<div className="panel panel-default">
				<div className="panel-body">
					<ClayForm.Group>
						<ClaySelectWithOption
							data-index={index}
							data-property="queryContains"
							id={`${namespace}queryContains${index}`}
							name={`${namespace}queryContains${index}`}
							onChange={onRuleChange}
							options={QUERY_CONTAINS_OPTIONS}
							title={Liferay.Language.get('query-contains')}
							value={rule.queryContains}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<ClaySelectWithOption
							data-index={index}
							data-property="queryAndOperator"
							id={`${namespace}queryAndOperator${index}`}
							name={`${namespace}queryAndOperator${index}`}
							onChange={onRuleChange}
							options={QUERY_AND_OPERATOR_OPTIONS}
							title={Liferay.Language.get('and-operator')}
							value={rule.queryAndOperator}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label
							className="control-label"
							htmlFor={`${namespace}queryName${index}`}
						>
							{Liferay.Language.get('of-the-following')}
						</label>
					</ClayForm.Group>

					<ClayForm.Group>
						<ClaySelectWithOption
							data-index={index}
							data-property="type"
							id={`${namespace}queryName${index}`}
							name={`${namespace}queryName${index}`}
							onChange={onRuleChange}
							options={RULE_TYPE_OPTIONS}
							value={rule.type}
						/>
					</ClayForm.Group>

					{rule.type === 'assetCategories' && (
						<AssetCategories
							categorySelectorURL={categorySelectorURL}
							groupIds={groupIds}
							index={index}
							namespace={namespace}
							rule={rule}
							vocabularyIds={vocabularyIds}
						/>
					)}

					{rule.type === 'assetTags' && (
						<AssetTags
							groupIds={groupIds}
							index={index}
							namespace={namespace}
							rule={rule}
							tagSelectorURL={tagSelectorURL}
						/>
					)}

					{rule.type === 'keywords' && (
						<Keywords
							index={index}
							namespace={namespace}
							onChange={onRuleChange}
							rule={rule}
						/>
					)}

					<div className="timeline-increment">
						<span className="timeline-icon"></span>
					</div>
				</div>
			</div>

			<div className="container-trash">
				<ClayButton
					className="condition-card-delete"
					data-index={index}
					monospaced
					onClick={onDeleteRule}
					small
				>
					<ClayIcon symbol="trash" />
				</ClayButton>
			</div>
		</>
	);
}

function AutoField({
	categorySelectorURL,
	groupIds,
	namespace,
	rules,
	tagSelectorURL,
	vocabularyIds
}) {
	const [currentRules, setCurrentRules] = useState(rules);

	const handleAddRule = useCallback(() => {
		setCurrentRules([...currentRules, DEFAULT_RULE]);
	}, [currentRules]);

	const handleDeleteRule = useCallback(
		event => {
			const index = parseInt(event.currentTarget.dataset.index, 10);

			setCurrentRules([
				...currentRules.slice(0, index),
				...currentRules.slice(index + 1, currentRules.length)
			]);
		},

		[currentRules]
	);

	const handleRuleChange = useCallback(
		event => {
			const index = parseInt(event.currentTarget.dataset.index, 10);
			const property = event.currentTarget.dataset.property;

			const rule =
				property === 'type'
					? {queryContains: true}
					: currentRules[index];

			setCurrentRules([
				...currentRules.slice(0, index),
				{
					...rule,
					[property]: event.currentTarget.value
				},
				...currentRules.slice(index + 1, currentRules.length)
			]);
		},
		[currentRules]
	);

	return (
		<>
			<ClayInput
				name={`${namespace}queryLogicIndexes`}
				type="hidden"
				value={Object.keys(currentRules).toString()}
			/>

			<ul className="timeline">
				<li className="timeline-item">
					<div className="flex-container panel-body">
						<div className="timeline-increment">
							<span className="timeline-icon"></span>
						</div>
					</div>
				</li>

				{currentRules.map((rule, index) => (
					<li className="timeline-item" key={index}>
						<Rule
							categorySelectorURL={categorySelectorURL}
							groupIds={groupIds}
							index={index}
							namespace={namespace}
							onDeleteRule={handleDeleteRule}
							onRuleChange={handleRuleChange}
							rule={rule}
							tagSelectorURL={tagSelectorURL}
							vocabularyIds={vocabularyIds}
						/>
					</li>
				))}
			</ul>

			<div className="addbutton-timeline-item">
				<div className="add-condition timeline-increment-icon">
					<ClayButton
						className="form-builder-rule-add-condition form-builder-timeline-add-item"
						monospaced
						onClick={handleAddRule}
						small
					>
						<ClayIcon symbol="plus" />
					</ClayButton>
				</div>
			</div>
		</>
	);
}

AutoField.propTypes = {
	categorySelectorURL: PropTypes.string,
	groupIds: PropTypes.arrayOf(PropTypes.string),
	namespace: PropTypes.string,
	rules: PropTypes.arrayOf(
		PropTypes.shape({
			queryAndOperator: PropTypes.bool,
			queryContains: PropTypes.bool,
			queryValues: PropTypes.string,
			selectedItems: PropTypes.oneOfType([
				PropTypes.string,
				PropTypes.arrayOf(
					PropTypes.shape({
						label: PropTypes.string,
						value: PropTypes.oneOfType([
							PropTypes.number,
							PropTypes.string
						])
					})
				)
			]),
			type: PropTypes.string
		})
	),
	tagSelectorURL: PropTypes.string,
	vocabularyIds: PropTypes.arrayOf(PropTypes.string)
};

export default function(props) {
	return <AutoField {...props} />;
}
