/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {useDispatch, useSelector} from '../../../app/contexts/StoreContext';
import deleteRule from '../../../app/thunks/deleteRule';
import RulesModal from './RulesModal';

export default function RulesList() {
	const [modalVisible, setModalVisible] = useState(false);
	const [editingRule, setEditingRule] = useState(null);

	const layoutData = useSelector((state) => state.layoutData);
	const dispatch = useDispatch();

	const {rules = []} = layoutData;

	const onCreateRule = () => setModalVisible(true);

	const onDeleteRule = (rule) => {
		dispatch(
			deleteRule({
				ruleId: rule.id,
			})
		);
	};

	const onEditRule = (rule) => {
		setEditingRule(rule);

		setModalVisible(true);
	};

	return (
		<>
			<ClayButton
				className="w-100"
				displayType="secondary"
				onClick={onCreateRule}
				size="sm"
			>
				<ClayIcon className="mr-2" symbol="plus" />

				{Liferay.Language.get('new-rule')}
			</ClayButton>

			<ClayList className="pt-3">
				{rules.map((rule) => (
					<Rule
						key={rule.id}
						onDelete={onDeleteRule}
						onEdit={onEditRule}
						rule={rule}
					/>
				))}
			</ClayList>

			{modalVisible && (
				<RulesModal
					editingRule={editingRule}
					onCloseModal={() => {
						setEditingRule(null);

						setModalVisible(false);
					}}
				/>
			)}
		</>
	);
}

function Rule({onDelete, onEdit, rule}) {
	const [hovered, setHovered] = useState(false);

	return (
		<ClayList.Item
			className={classNames({active: hovered})}
			key={rule.id}
			onMouseLeave={() => setHovered(false)}
			onMouseOver={() => setHovered(true)}
		>
			<ClayList.ItemField expand>
				<ClayList.ItemTitle className="align-items-center d-flex">
					<span className="flex-grow-1">{rule.name}</span>

					<ClayDropDown
						onMouseOver={(event) => event.stopPropagation()}
						trigger={
							<ClayButtonWithIcon
								aria-label={sub(
									Liferay.Language.get('view-x-options'),
									rule.name
								)}
								borderless
								displayType="secondary"
								size="sm"
								symbol="ellipsis-v"
								title={sub(
									Liferay.Language.get('view-x-options'),
									rule.name
								)}
							/>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Item onClick={() => onEdit(rule)}>
								<ClayIcon className="mr-2" symbol="pencil" />

								{Liferay.Language.get('edit')}
							</ClayDropDown.Item>

							<ClayDropDown.Item onClick={() => onDelete(rule)}>
								<ClayIcon className="mr-2" symbol="trash" />

								{Liferay.Language.get('delete')}
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ClayList.ItemTitle>
			</ClayList.ItemField>
		</ClayList.Item>
	);
}
