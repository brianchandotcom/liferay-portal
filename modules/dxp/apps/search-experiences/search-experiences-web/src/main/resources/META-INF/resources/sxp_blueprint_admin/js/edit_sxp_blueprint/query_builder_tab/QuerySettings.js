/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import ClaySticker from '@clayui/sticker';
import React, {useState} from 'react';

import SelectTypes from './SelectTypes';

function QuerySettings({
	applyIndexerClauses,
	clauseContributorsList,
	frameworkConfig,
	onApplyIndexerClausesChange,
	onChangeClauseContributorsVisibility,
	onChangeIndexerClausesVisibility,
	onFrameworkConfigChange,
	searchableTypes,
}) {
	const [selectAllTypes, setSelectAllTypes] = useState(
		frameworkConfig.searchableAssetTypes?.length === 0
	);
	const [enableAllContributors, setEnableAllContributors] = useState(
		frameworkConfig.clauseContributorsIncludes?.length ===
			clauseContributorsList.length
	);

	const _handleApplyIndexerClausesChange = () => {
		onApplyIndexerClausesChange(!applyIndexerClauses);
	};

	const _handleEnableAllContributorsChange = (enable) => {
		setEnableAllContributors(enable);

		if (enable) {
			onFrameworkConfigChange({
				clauseContributorsExcludes: [],
				clauseContributorsIncludes: clauseContributorsList,
			});

			onChangeClauseContributorsVisibility(false);
		}
	};

	const _handleSelectAllTypesChange = (selectAll) => {
		setSelectAllTypes(selectAll);

		onFrameworkConfigChange({
			searchableAssetTypes: [],
		});
	};

	return (
		<div className="query-settings">
			<ClayLayout.Row className="configuration-header" justify="between">
				<ClayLayout.Col size={12}>
					{Liferay.Language.get('query-settings')}
				</ClayLayout.Col>
			</ClayLayout.Row>

			<div className="sheet">
				<ClayPanel.Group flush small>
					<ClayPanel
						className="searchable-types"
						collapsable
						defaultExpanded
						displayTitle={Liferay.Language.get('searchable-types')}
						displayType="unstyled"
						showCollapseIcon
					>
						<ClayPanel.Body>
							<ClayRadioGroup
								onSelectedValueChange={
									_handleSelectAllTypesChange
								}
								selectedValue={selectAllTypes}
							>
								<ClayRadio
									label={Liferay.Language.get(
										'all-searchable-types'
									)}
									value={true}
								/>

								<ClayRadio
									label={Liferay.Language.get(
										'selected-types'
									)}
									value={false}
								/>
							</ClayRadioGroup>

							{!selectAllTypes && (
								<>
									<div className="sheet-text">
										{Liferay.Language.get(
											'select-the-searchable-types-description'
										)}
									</div>

									<SelectTypes
										onFrameworkConfigChange={
											onFrameworkConfigChange
										}
										searchableTypes={searchableTypes}
										selectedTypes={
											frameworkConfig.searchableAssetTypes
										}
									/>
								</>
							)}
						</ClayPanel.Body>
					</ClayPanel>

					<ClayPanel
						collapsable
						defaultExpanded
						displayTitle={
							<ClayPanel.Title>
								<span className="panel-title">
									{Liferay.Language.get(
										'search-framework-indexer-clauses'
									)}
								</span>

								<ClaySticker
									borderless
									displayType="secondary"
									monospaced
									onClick={(event) => {
										event.stopPropagation();
										onChangeIndexerClausesVisibility();
									}}
									size="md"
								>
									<ClayIcon symbol="question-circle" />
								</ClaySticker>
							</ClayPanel.Title>
						}
						displayType="unstyled"
						showCollapseIcon
					>
						<ClayPanel.Body>
							<ClayToggle
								label={
									applyIndexerClauses
										? Liferay.Language.get('on')
										: Liferay.Language.get('off')
								}
								onToggle={_handleApplyIndexerClausesChange}
								toggled={!!applyIndexerClauses}
							/>

							{!applyIndexerClauses && (
								<div className="has-warning">
									<ClayForm.FeedbackItem>
										<ClayForm.FeedbackIndicator symbol="warning-full" />

										{Liferay.Language.get('warning-colon')}

										<span className="warning-text">
											{Liferay.Language.get(
												'search-framework-indexer-clauses-warning'
											)}
										</span>
									</ClayForm.FeedbackItem>
								</div>
							)}
						</ClayPanel.Body>
					</ClayPanel>

					<ClayPanel
						collapsable
						defaultExpanded
						displayTitle={
							<ClayPanel.Title>
								<span className="panel-title">
									{Liferay.Language.get(
										'search-framework-query-contributors'
									)}
								</span>
							</ClayPanel.Title>
						}
						displayType="unstyled"
						showCollapseIcon
					>
						<ClayPanel.Body>
							<ClayRadioGroup
								onSelectedValueChange={
									_handleEnableAllContributorsChange
								}
								selectedValue={enableAllContributors}
							>
								<ClayRadio
									label={Liferay.Language.get('enable-all')}
									value={true}
								/>

								<ClayRadio
									label={Liferay.Language.get(
										'action.CUSTOMIZE'
									)}
									value={false}
								/>
							</ClayRadioGroup>

							{!enableAllContributors && (
								<>
									<div className="has-warning">
										<ClayForm.FeedbackItem>
											<ClayForm.FeedbackIndicator symbol="warning-full" />

											{Liferay.Language.get(
												'warning-colon'
											)}

											<span className="warning-text">
												{Liferay.Language.get(
													'search-framework-query-contributors-warning'
												)}
											</span>
										</ClayForm.FeedbackItem>
									</div>

									<ClayButton
										displayType="secondary"
										onClick={() =>
											onChangeClauseContributorsVisibility(
												true
											)
										}
										small
									>
										{Liferay.Language.get(
											'customize-contributors'
										)}
									</ClayButton>
								</>
							)}
						</ClayPanel.Body>
					</ClayPanel>
				</ClayPanel.Group>
			</div>
		</div>
	);
}

export default React.memo(QuerySettings);
