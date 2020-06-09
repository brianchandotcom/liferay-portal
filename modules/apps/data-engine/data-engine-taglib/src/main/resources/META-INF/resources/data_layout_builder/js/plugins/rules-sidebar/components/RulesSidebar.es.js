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
import React, {useState} from 'react';

import RuleEditorModal from '../../../components/rules/RuleEditorModal.es';
import RuleList from '../../../components/rules/RuleList.es';
import Sidebar from '../../../components/sidebar/Sidebar.es';

export default function ({title}) {
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
					keywords={keywords}
					toggleRulesEditorVisibility={toggleRulesEditorVisibility}
				/>
			</Sidebar.Body>

			<RuleEditorModal
				isVisible={rulesEditorState.isVisible}
				onClose={() => toggleRulesEditorVisibility()}
				rule={rulesEditorState.rule}
			/>
		</Sidebar>
	);
}
