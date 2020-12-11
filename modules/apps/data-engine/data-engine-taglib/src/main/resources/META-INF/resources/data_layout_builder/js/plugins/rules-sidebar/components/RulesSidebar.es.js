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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React, {useContext, useState} from 'react';

import AppContext from '../../../AppContext.es';
import {
	ADD_DATA_LAYOUT_RULE,
	UPDATE_DATA_LAYOUT_RULE,
} from '../../../actions.es';
import RuleEditorModal from '../../../components/rules/RuleEditorModal.es';
import RuleList from '../../../components/rules/RuleList.es';
import Sidebar from '../../../components/sidebar/Sidebar.es';
import DataLayoutBuilderContext from '../../../data-layout-builder/DataLayoutBuilderContext.es';

export const DataEngineRulesSidebar = ({title}) => {
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const {pages} = dataLayoutBuilder.getStore();

	const [
		{
			config: {
				ruleSettings: {functionsMetadata, functionsURL},
			},
			dataLayout: {dataRules},
		},
		dispatch,
	] = useContext(AppContext);

	return (
		<RulesSidebar
			dataRules={dataRules}
			functionsMetadata={functionsMetadata}
			functionsURL={functionsURL}
			onClick={({dataRule, loc, rule}) => {
				dispatch({
					payload: {
						dataRule,
						loc,
					},
					type: rule ? UPDATE_DATA_LAYOUT_RULE : ADD_DATA_LAYOUT_RULE,
				});
			}}
			pages={pages}
			title={title}
		/>
	);
};

export const RulesSidebar = ({
	dataRules,
	functionsMetadata,
	functionsURL,
	onClick,
	pages,
	title,
}) => {
	const [rulesEditorState, setRulesEditorState] = useState({
		isVisible: false,
		rule: null,
	});
	const [keywords, setKeywords] = useState('');

	const toggleRulesEditorVisibility = (rule) => {
		if (rule) {
			rule['logical-operator'] = rule['logicalOperator'];
			delete rule.logicalOperator;
		}

		setRulesEditorState((prevState) => ({
			isVisible: !prevState.isVisible,
			rule,
		}));
	};

	return (
		<Sidebar>
			<Sidebar.Header>
				<Sidebar.Title title={title} />

				<ClayLayout.ContentRow className="sidebar-section">
					<ClayLayout.ContentCol expand>
						<ClayForm onSubmit={(event) => event.preventDefault()}>
							<Sidebar.SearchInput
								onSearch={(keywords) => setKeywords(keywords)}
							/>
						</ClayForm>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol className="ml-2">
						<ClayButtonWithIcon
							displayType="primary"
							onClick={() => toggleRulesEditorVisibility()}
							symbol="plus"
						/>
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			</Sidebar.Header>
			<Sidebar.Body>
				<RuleList
					dataRules={dataRules}
					keywords={keywords}
					toggleRulesEditorVisibility={toggleRulesEditorVisibility}
				/>
			</Sidebar.Body>

			<RuleEditorModal
				functionsMetadata={functionsMetadata}
				functionsURL={functionsURL}
				isVisible={rulesEditorState.isVisible}
				onClick={onClick}
				onClose={() => toggleRulesEditorVisibility()}
				pages={pages}
				rule={rulesEditorState.rule}
			/>
		</Sidebar>
	);
};
