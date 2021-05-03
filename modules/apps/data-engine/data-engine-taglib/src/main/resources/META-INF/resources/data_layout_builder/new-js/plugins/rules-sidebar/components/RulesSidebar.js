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
import {useForm, useFormState} from 'data-engine-js-components-web';
import React, {useState} from 'react';

import Sidebar from '../../../../js/components/sidebar/Sidebar.es';
import RuleEditorModal from '../../../components/rules/RuleEditorModal';
import RuleList from '../../../components/rules/RuleList';
import {EVENT_TYPES} from '../../../eventTypes';

const RulesSidebar = ({title}) => {
	const dispatch = useForm();
	const {dataLayout} = useFormState({schema: ['dataLayout']});
	const [keywords, setKeywords] = useState('');
	const [loc, setLoc] = useState(-1);
	const [showModal, setShowModal] = useState(false);

	const onAddRule = () => {
		setLoc(-1);
		setShowModal(true);
	};

	const onDeleteRule = (loc) => {
		dispatch({
			payload: loc,
			type: EVENT_TYPES.RULE.DELETE,
		});
	};

	const onEditRule = (loc) => {
		setLoc(loc);
		setShowModal(true);
	};

	const onSaveRule = (rule) => {
		if (loc <= -1) {
			dispatch({
				payload: rule,
				type: EVENT_TYPES.RULE.ADD,
			});
		}
		else {
			dispatch({
				payload: {
					loc,
					rule,
				},
				type: EVENT_TYPES.RULE.CHANGE,
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
							<Sidebar.SearchInput onSearch={setKeywords} />
						</ClayForm>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol className="ml-2">
						<ClayButtonWithIcon
							displayType="primary"
							onClick={onAddRule}
							symbol="plus"
						/>
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			</Sidebar.Header>
			<Sidebar.Body>
				<RuleList
					keywords={keywords}
					onAddRule={onAddRule}
					onDeleteRule={onDeleteRule}
					onEditRule={onEditRule}
					rules={dataLayout.dataRules}
				/>
			</Sidebar.Body>

			<RuleEditorModal
				onClose={() => setShowModal(false)}
				onSaveRule={onSaveRule}
				rule={dataLayout.dataRules?.[loc]}
				showModal={showModal}
			/>
		</Sidebar>
	);
};

export default RulesSidebar;
