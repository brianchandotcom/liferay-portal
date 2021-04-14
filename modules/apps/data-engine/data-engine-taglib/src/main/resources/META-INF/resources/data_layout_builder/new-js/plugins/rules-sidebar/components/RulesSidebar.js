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
import {
	useConfig,
	useForm,
	useFormState,
} from 'dynamic-data-mapping-form-renderer';
import React, {useState} from 'react';

import Sidebar from '../../../../js/components/sidebar/Sidebar.es';
import RuleEditorModal from '../../../components/rules/RuleEditorModal';
import RuleList from '../../../components/rules/RuleList';
import {EVENT_TYPES} from '../../../eventTypes';

function normalizeRule(rule) {
	const _rule = {...rule};

	_rule['logical-operator'] = _rule['logicalOperator'];
	delete _rule.logicalOperator;

	return _rule;
}

const RulesSidebar = ({title}) => {
	const {ruleSettings} = useConfig();
	const dispatch = useForm();
	const {dataLayout} = useFormState({schema: ['dataLayout']});

	const [rulesEditorState, setRulesEditorState] = useState({
		isVisible: false,
		rule: null,
	});
	const [keywords, setKeywords] = useState('');

	const toggleRulesEditorVisibility = (rule) => {
		setRulesEditorState((prevState) => ({
			isVisible: !prevState.isVisible,
			rule: rule && normalizeRule(rule),
		}));
	};

	const handleClickChangeRule = ({dataRule, loc, rule}) => {
		if (rule) {
			dispatch({
				payload: {
					loc,
					rule: dataRule,
				},
				type: EVENT_TYPES.RULE.CHANGE,
			});
		}
		else {
			dispatch({
				payload: dataRule,
				type: EVENT_TYPES.RULE.ADD,
			});
		}
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
					dataRules={dataLayout.dataRules}
					keywords={keywords}
					toggleRulesEditorVisibility={toggleRulesEditorVisibility}
				/>
			</Sidebar.Body>

			<RuleEditorModal
				functionsMetadata={ruleSettings.functionsMetadata}
				functionsURL={ruleSettings.functionsURL}
				isVisible={rulesEditorState.isVisible}
				onClick={handleClickChangeRule}
				onClose={() => toggleRulesEditorVisibility()}
				rule={rulesEditorState.rule}
			/>
		</Sidebar>
	);
};

export default RulesSidebar;
